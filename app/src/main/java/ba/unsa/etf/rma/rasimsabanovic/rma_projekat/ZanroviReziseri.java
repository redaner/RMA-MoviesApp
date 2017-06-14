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
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Rasim on 28.05.2017..
 */

public class ZanroviReziseri extends IntentService {

    public final static int STATUS_RUNNING = 0;
    public final static int STATUS_FINISHED = 1;
    public final static int STATUS_ERROR = 2;

    public ZanroviReziseri() {
        super(null);
    }

    public ZanroviReziseri(String name) {
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

        String urlPretraga = "https://api.themoviedb.org/3/discover/movie?api_key=63cc2a196ebe1b1694b1bcf6c3822e1e&sort_by=release_date.desc&with_cast=" + queryFinal;

        ArrayList<Reziser> reziseri = new ArrayList<>();
        ArrayList<Zanr> zanrovi = new ArrayList<>();
        ArrayList<String> tempZanrovi = new ArrayList<>();

        URL urlFinalniPretraga = null;
        try {
            urlFinalniPretraga = new URL(urlPretraga);
            HttpURLConnection urlConnectionPretraga = (HttpURLConnection) urlFinalniPretraga.openConnection();
            InputStream input = new BufferedInputStream(urlConnectionPretraga.getInputStream());

            String rezultat = convertStreamToString(input);

            JSONObject jo = new JSONObject(rezultat);

            JSONArray ja = jo.getJSONArray("results");

            for (int i = 0; i < ja.length(); i++) {
                JSONObject film = ja.getJSONObject(i);
                JSONArray genres = film.getJSONArray("genre_ids");
                if (genres.length() != 0) {
                    String zanr = genres.getString(0);
                    tempZanrovi.add(zanr);
                }

                String idFilma = film.getString("id");

                String urlCredits = "https://api.themoviedb.org/3/movie/" + idFilma + "/credits?api_key=63cc2a196ebe1b1694b1bcf6c3822e1e";

                URL urlFinalniCredits = null;

                try {
                    urlFinalniCredits = new URL(urlCredits);
                    HttpURLConnection urlConnectionCredits = (HttpURLConnection) urlFinalniCredits.openConnection();
                    InputStream inputCredits = new BufferedInputStream(urlConnectionCredits.getInputStream());


                    String rezultatCredits = convertStreamToString(inputCredits);

                    JSONObject joCredits = new JSONObject(rezultatCredits);

                    JSONArray jaCrew = joCredits.getJSONArray("crew");

                    for (int j = 0; j < jaCrew.length(); j++) {
                        JSONObject crewMember = jaCrew.getJSONObject(j);
                        String s = new String();
                        Boolean brejk = false;
                        if (crewMember.getString("job").equals("Director")) {
                            for (Reziser r : reziseri) {
                                if ((crewMember.getString("name")).equals(r.getIme())) {
                                    brejk = true;
                                    break;
                                }
                            }
                            if (brejk) continue;
                            reziseri.add(new Reziser(crewMember.getString("name")));
                        }
                    }
                }
                catch (MalformedURLException e) {
                    System.out.print("s");
                }
            }

         ArrayList<String> temp2Zanrovi = new ArrayList<>();



         for (String s : tempZanrovi) {
             Boolean brejk = false;
             for (String s2 : temp2Zanrovi) {
                 if (s.equals(s2)) {
                     brejk = true;
                     break;
                 }
             }
             if (brejk) continue;
             temp2Zanrovi.add(s);
         }

         for (int i = 0; i < temp2Zanrovi.size(); i++) {

             String urlZanr = " http://api.themoviedb.org/3/genre/" + temp2Zanrovi.get(i) + "?api_key=63cc2a196ebe1b1694b1bcf6c3822e1e";

             URL urlFinalniZanr = null;

             try {

                     urlFinalniZanr = new URL(urlZanr);
                     HttpURLConnection urlConnectionZanr = (HttpURLConnection) urlFinalniZanr.openConnection();
                     InputStream inputZanr = new BufferedInputStream(urlConnectionZanr.getInputStream());

                     String rezultatZanr = convertStreamToString(inputZanr);

                     JSONObject joZanr = new JSONObject(rezultatZanr);

                     String imeZanra = joZanr.getString("name");
                     zanrovi.add(new Zanr(imeZanra, "comedy"));

             }
             catch (Exception e) {

             }


         }
            ArrayList<Zanr> zanroviFinal = zanrovi;



            if (zanroviFinal.size() >= 7) {
                zanroviFinal = new ArrayList<>(zanroviFinal.subList(0, 7));
            }



            ArrayList<Reziser> reziseriFinal = new ArrayList<>();

            if (reziseri.size() >= 7) {
                reziseriFinal = new ArrayList<>(reziseri.subList(0, 7));
            }

            bundle.putParcelableArrayList("reziseri", reziseriFinal);
            bundle.putParcelableArrayList("zanrovi", zanroviFinal);
            mReceiver.send(STATUS_FINISHED, bundle);

        }
        catch (MalformedURLException e) {
            mReceiver.send(STATUS_ERROR, bundle);
        }
        catch (IOException e) {
            mReceiver.send(STATUS_ERROR, bundle);
        }
        catch (JSONException e) {
            mReceiver.send(STATUS_ERROR, bundle);
        }
    }

}
