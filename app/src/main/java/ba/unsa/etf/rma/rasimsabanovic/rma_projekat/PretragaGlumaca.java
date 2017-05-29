package ba.unsa.etf.rma.rasimsabanovic.rma_projekat;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by Rasim on 16.05.2017..
 */

public class PretragaGlumaca extends IntentService {

    public final static int STATUS_RUNNING = 0;
    public final static int STATUS_FINISHED = 1;
    public final static int STATUS_ERROR = 2;

    public PretragaGlumaca() {
        super(null);
    }

    public PretragaGlumaca(String name) {
        super(name);
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder s = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                s.append(line + "\n");
            }
        }
        catch (IOException e) {

        }
        finally {
            try {
                is.close();
            }
            catch (IOException e) {

            }
        }

        return s.toString();
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        final ResultReceiver mReceiver = intent.getParcelableExtra("receiver");
        Bundle bundle = new Bundle();

        mReceiver.send(STATUS_RUNNING, Bundle.EMPTY);

        String query = intent.getStringExtra("query");
        String queryFinal = null;

        try {
            queryFinal = URLEncoder.encode(query, "utf-8");
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        String urlPretraga = "https://api.themoviedb.org/3/search/person?query=" + queryFinal + "&api_key=63cc2a196ebe1b1694b1bcf6c3822e1e";

        ArrayList<Glumac> glumci = new ArrayList<>();

        URL urlFinalniPretraga = null;
        try {
            urlFinalniPretraga = new URL(urlPretraga);
            HttpURLConnection urlConnectionPretraga = (HttpURLConnection) urlFinalniPretraga.openConnection();
            InputStream input = new BufferedInputStream(urlConnectionPretraga.getInputStream());

            String rezultat = convertStreamToString(input);

            JSONObject jo = new JSONObject(rezultat);

            JSONArray ja = jo.getJSONArray("results");

            for (int i = 0; i < ja.length(); i++) {
                JSONObject osoba = ja.getJSONObject(i);
                String id = osoba.getString("id");

                String urlCast = "https://api.themoviedb.org/3/person/" + id + "/combined_credits?api_key=63cc2a196ebe1b1694b1bcf6c3822e1e";

                URL urlFinalniCast = null;

                try {
                    urlFinalniCast = new URL(urlCast);
                    HttpURLConnection urlConnectionCast = (HttpURLConnection) urlFinalniCast.openConnection();
                    InputStream inputCast = new BufferedInputStream(urlConnectionCast.getInputStream());


                    String rezultatCast = convertStreamToString(inputCast);

                    JSONObject joCast = new JSONObject(rezultatCast);

                    JSONArray jaCast = joCast.getJSONArray("cast");

                    if (jaCast.length() == 0) continue;

                }
                catch (MalformedURLException e) {
                    System.out.print("s");
                }

                String ime = osoba.getString("name");

                String popularity = osoba.getString("popularity");

                String urlPerson = "https://api.themoviedb.org/3/person/" + id + "?api_key=63cc2a196ebe1b1694b1bcf6c3822e1e";

                URL urlFinalniPerson = null;

                try {
                    urlFinalniPerson = new URL(urlPerson);
                    HttpURLConnection urlConnectionPerson = (HttpURLConnection) urlFinalniPerson.openConnection();
                    InputStream inputPerson = new BufferedInputStream(urlConnectionPerson.getInputStream());

                    String rezultatPerson = convertStreamToString(inputPerson);

                    JSONObject joPerson = new JSONObject(rezultatPerson);

                    String placeOfBirth = joPerson.getString("place_of_birth");
                    String birthDate = joPerson.getString("birthday");
                    String deathDate = joPerson.getString("deathday");
                    String gender = joPerson.getString("gender");
                    String imdb = "http://www.imdb.com/name/" + joPerson.getString("imdb_id");
                    String biography = joPerson.getString("biography");
                    String slika = "http://image.tmdb.org/t/p/w185/" + joPerson.getString("profile_path");

                    glumci.add(new Glumac(Integer.parseInt(id), ime, Integer.parseInt(birthDate.substring(0, 4)), deathDate.equals("") ? -1 : Integer.parseInt(deathDate.substring(0, 4)),
                            biography, Double.parseDouble(popularity), slika, placeOfBirth, gender.equals("1") ? "F" : "M", imdb));




                }
                catch (MalformedURLException e) {
                    mReceiver.send(STATUS_ERROR, bundle);
                }
                catch (Exception e) {
                    mReceiver.send(STATUS_ERROR, bundle);
                }





            }


            bundle.putParcelableArrayList("glumci", glumci);
            mReceiver.send(STATUS_FINISHED, bundle);


        }
        catch (MalformedURLException e) {

        }
        catch (IOException e) {

        }
        catch (JSONException e) {

        }
    }
}
