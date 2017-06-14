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
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by Rasim on 13.06.2017..
 */

public class PretragaFilmova extends IntentService {

    public final static int STATUS_RUNNING = 0;
    public final static int STATUS_FINISHED = 1;
    public final static int STATUS_ERROR = 2;

    public PretragaFilmova() {
        super(null);
    }

    public PretragaFilmova(String name) {
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

        String urlPretraga = "https://api.themoviedb.org/3/search/movie?query=" + queryFinal + "&api_key=63cc2a196ebe1b1694b1bcf6c3822e1e";

        ArrayList<String> filmovi = new ArrayList<>();

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

                String ime = film.getString("title");

                filmovi.add(ime);
            }

            bundle.putStringArrayList("filmovi", filmovi);
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
