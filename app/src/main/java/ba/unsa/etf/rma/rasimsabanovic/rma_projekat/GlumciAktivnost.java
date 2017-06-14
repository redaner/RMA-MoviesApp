package ba.unsa.etf.rma.rasimsabanovic.rma_projekat;




import android.Manifest;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.facebook.stetho.Stetho;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import static ba.unsa.etf.rma.rasimsabanovic.rma_projekat.GlumciDBHelper.DATABASE_GLUMCI;
import static ba.unsa.etf.rma.rasimsabanovic.rma_projekat.GlumciDBHelper.DATABASE_IME;
import static ba.unsa.etf.rma.rasimsabanovic.rma_projekat.GlumciDBHelper.DATABASE_REZISERI;
import static ba.unsa.etf.rma.rasimsabanovic.rma_projekat.GlumciDBHelper.DATABASE_REZISERI_VEZE;
import static ba.unsa.etf.rma.rasimsabanovic.rma_projekat.GlumciDBHelper.DATABASE_ZANROVI;
import static ba.unsa.etf.rma.rasimsabanovic.rma_projekat.GlumciDBHelper.DATABASE_ZANROVI_VEZE;

public class GlumciAktivnost extends AppCompatActivity implements PretragaResultReceiver.Receiver, ButtonsFragment.buttonClick, GlumciFragment.onGlumacClick, GlumciFragment.OnFragmentInteractionListener
, ButtonsFragment.OnFragmentInteractionListener, DetaljiFragment.OnFragmentInteractionListener, ReziseriFragment.OnFragmentInteractionListener
    ,ZanroviFragment.OnFragmentInteractionListener, FilmoviFragment.OnFragmentInteractionListener, FilmoviFragment.onFilmClick,
        KalendarFragment.OnFragmentInteractionListener
{

    public final static int PERMISIJA = 1;

    public static Boolean permisija;

    PretragaResultReceiver mReceiver;

    public static boolean flag=false;
    private Boolean siri = false;

    public ArrayList<Glumac> getGlumci() {
        return glumci;
    }

    public void setGlumci(ArrayList<Glumac> glumci) {
        this.glumci = glumci;
    }

    public ArrayList<Reziser> getReziseri() {
        return reziseri;
    }

    public void setReziseri(ArrayList<Reziser> reziseri) {
        this.reziseri = reziseri;
    }

    public ArrayList<Zanr> getZanrovi() {
        return zanrovi;
    }

    public void setZanrovi(ArrayList<Zanr> zanrovi) {
        this.zanrovi = zanrovi;
    }

    ArrayList<Glumac> glumci = new ArrayList<>();
    ArrayList<Reziser> reziseri = new ArrayList<>();
    ArrayList<Zanr> zanrovi = new ArrayList<>();
    ArrayList<String> filmovi = new ArrayList<>();

    public ArrayList<String> getFilmovi() {
        return filmovi;
    }

    public void setFilmovi(ArrayList<String> filmovi) {
        this.filmovi = filmovi;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void click(int pos){
        FragmentManager fm = getFragmentManager();
        KalendarFragment df = new KalendarFragment();
        Bundle argumenti = new Bundle();
        argumenti.putString("film", filmovi.get(pos));
        df.setArguments(argumenti);
        if (!siri) {
            fm.beginTransaction().replace(R.id.liste, df).addToBackStack(null).commit();
        }
        else {
            fm.beginTransaction().replace(R.id.drugi, df).addToBackStack(null).commit();
        }
    }

    @Override
    public void onFragmentSetGlumci(ArrayList<Glumac> glumci) {
        this.glumci = glumci;
        SaveState s = (SaveState) getApplication();
        s.setGlumci(glumci);
    }

    @Override
    public void onFragmentSetFilmovi(ArrayList<String> filmovi) {
        this.filmovi = filmovi;
        SaveState s = (SaveState) getApplication();
        s.setFilmovi(filmovi);
    }

    @Override
    public void klik(int pos) {
        FragmentManager fm = getFragmentManager();
        DetaljiFragment df = new DetaljiFragment();
        Bundle argumenti = new Bundle();
        argumenti.putParcelable("glumac", glumci.get(pos));
        df.setArguments(argumenti);
        if (!siri) {
            fm.beginTransaction().replace(R.id.liste, df).addToBackStack(null).commit();
        }
        else {
            fm.beginTransaction().replace(R.id.drugi, df).addToBackStack(null).commit();
        }

        if (GlumciFragment.baza) {
            String[] koloneZanrovi = new String[] {"glumac_id", "zanr_id"};

            String whereZanrovi = "glumac_id = " + String.valueOf(glumci.get(pos).getId());

            GlumciDBHelper helper = new GlumciDBHelper(this, GlumciDBHelper.DATABASE_NAME, null, 1);

            SQLiteDatabase db = helper.getWritableDatabase();

            Cursor cursor = db.query(GlumciDBHelper.DATABASE_ZANROVI_VEZE, koloneZanrovi, whereZanrovi, null, null, null, null);

            ArrayList<Integer> ideviZanrova = new ArrayList<>();

            int kolonaZanr = cursor.getColumnIndexOrThrow("zanr_id");

            while (cursor.moveToNext()) {
                ideviZanrova.add(cursor.getInt(kolonaZanr));
            }
            ArrayList<Zanr> zanrovi1 = new ArrayList<>();

            for (int i = 0; i < ideviZanrova.size(); i++) {
                String[] koloneImenaZanrova = new String[] {GlumciDBHelper.DATABASE_IME};
                String whereZanrIme = GlumciDBHelper.DATABASE_ID + "=" + String.valueOf(ideviZanrova.get(i));
                Cursor cursorZanrovi = db.query(GlumciDBHelper.DATABASE_ZANROVI, koloneImenaZanrova, whereZanrIme, null, null, null, null);

                int kolonaZanrIme = cursorZanrovi.getColumnIndexOrThrow(DATABASE_IME);

                while (cursorZanrovi.moveToNext()) {
                    zanrovi1.add(new Zanr(cursorZanrovi.getString(kolonaZanrIme), null));
                }
            }

            String[] koloneReziseri = new String[] {"glumac_id", "reziser_id"};

            String whereReziseri = "glumac_id = " + String.valueOf(glumci.get(pos).getId());

            Cursor cursorReziseri = db.query(GlumciDBHelper.DATABASE_REZISERI_VEZE, koloneReziseri, whereReziseri, null, null, null, null);

            ArrayList<Integer> ideviRezisera = new ArrayList<>();

            int kolonaReziser = cursorReziseri.getColumnIndexOrThrow("reziser_id");

            while (cursorReziseri.moveToNext()) {
                ideviRezisera.add(cursorReziseri.getInt(kolonaReziser));
            }

            ArrayList<Reziser> reziseri1 = new ArrayList<>();

            for (int i = 0; i < ideviRezisera.size(); i++) {
                String[] koloneImenaRezisera = new String[] {GlumciDBHelper.DATABASE_IME};
                String whereReziserIme = GlumciDBHelper.DATABASE_ID + "=" + String.valueOf(ideviRezisera.get(i));
                Cursor cursorReziseriIme = db.query(GlumciDBHelper.DATABASE_REZISERI, koloneImenaRezisera, whereReziserIme, null, null, null, null);

                int kolonaReziserIme = cursorReziseriIme.getColumnIndexOrThrow(DATABASE_IME);

                while (cursorReziseriIme.moveToNext()) {
                    reziseri1.add(new Reziser(cursorReziseriIme.getString(kolonaReziserIme)));
                }
            }

            zanrovi = zanrovi1;
            reziseri = reziseri1;

            SaveState s = (SaveState) getApplication();
            s.setReziseri(reziseri);
            s.setZanrovi(zanrovi);

            return;
        }


        Intent intent = new Intent(Intent.ACTION_SYNC, null, this, ZanroviReziseri.class);
        mReceiver = new PretragaResultReceiver(new Handler());
        mReceiver.setReceiver(this);

        String tekst = String.valueOf(glumci.get(pos).getId());

        intent.putExtra("receiver", mReceiver);
        intent.putExtra("query", tekst);

        startService(intent);
    }

    @Override
    public void onReceiveResult(int resultCode, Bundle resultData) {
        switch (resultCode) {
            case PretragaGlumaca.STATUS_RUNNING:
                break;
            case PretragaGlumaca.STATUS_FINISHED:
                reziseri = resultData.getParcelableArrayList("reziseri");
                zanrovi = resultData.getParcelableArrayList("zanrovi");
                SaveState s = (SaveState) getApplication();
                s.setReziseri(reziseri);
                s.setZanrovi(zanrovi);
                break;
            case PretragaGlumaca.STATUS_ERROR:
                String error = resultData.getString(Intent.EXTRA_TEXT);
                //           Toast.makeText(getActivity(), error, Toast.LENGTH_LONG).show();
                break;
        }
    }


    @Override
    public void onGlumciClick() {

        FragmentManager fm = getFragmentManager();
        GlumciFragment gf = new GlumciFragment();
        Bundle argumenti= new Bundle();
        argumenti.putParcelableArrayList("lista_glumaca", glumci);
        gf.setArguments(argumenti);
        fm.beginTransaction().replace(R.id.liste, gf).addToBackStack(null).commit();

    }

    @Override
    public void onReziseriClick() {
        FragmentManager fm = getFragmentManager();

       // ReziseriFragment rf = (ReziseriFragment)fm.findFragmentById(R.id.liste);

        //if (rf == null) {
        ReziseriFragment rf = new ReziseriFragment();
        Bundle argumenti= new Bundle();
        argumenti.putParcelableArrayList("lista_rezisera", reziseri);
        rf.setArguments(argumenti);
        fm.beginTransaction().replace(R.id.liste, rf).addToBackStack(null).commit();
        //}
    }

    @Override
    public void onZanroviClick() {
        FragmentManager fm = getFragmentManager();
        ZanroviFragment rf = new ZanroviFragment();
        Bundle argumenti= new Bundle();
        argumenti.putParcelableArrayList("lista_zanrova", zanrovi);
        rf.setArguments(argumenti);
        fm.beginTransaction().replace(R.id.liste, rf).addToBackStack(null).commit();

    }

    @Override
    public void onFilmoviClick() {
        FragmentManager fm = getFragmentManager();
        FilmoviFragment ff = new FilmoviFragment();
        Bundle argumenti= new Bundle();
        argumenti.putStringArrayList("lista_filmova", filmovi);
        ff.setArguments(argumenti);
        fm.beginTransaction().replace(R.id.liste, ff).addToBackStack(null).commit();
    }

    @Override
    public void onBackPressed() {

        int count = getFragmentManager().getBackStackEntryCount();

        if (count == 0) {
            super.onBackPressed();

        } else {
            getFragmentManager().popBackStack();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    permisija = true;
                } else {

                    permisija = false;
                }
                return;
            }

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Stetho.initializeWithDefaults(this);
        setContentView(R.layout.activity_glumci_aktivnost);

        ActivityCompat.requestPermissions(GlumciAktivnost.this,
                new String[]{Manifest.permission.READ_CALENDAR, Manifest.permission.WRITE_CALENDAR},
                1);


      /*  if (savedInstanceState != null) {
            glumci = savedInstanceState.getParcelableArrayList("glumci");
            zanrovi = savedInstanceState.getParcelableArrayList("zanrovi");
            reziseri = savedInstanceState.getParcelableArrayList("reziseri");
        } */

        SaveState s = (SaveState) getApplication();
        glumci = s.getGlumci();


        //final ListView lista_glumci = (ListView)findViewById(R.id.listaGlumci);
        if (glumci == null) {
            glumci = new ArrayList<>();
        }
        if (reziseri == null) {
            reziseri = new ArrayList<>();
        }
        if (zanrovi == null) {
            zanrovi = new ArrayList<>();
        }





        FragmentManager fm = getFragmentManager();

        FrameLayout ldetalji = (FrameLayout) findViewById(R.id.drugi);

        if (ldetalji != null) {
            siri = true;
            DetaljiFragment df;

                df = new DetaljiFragment();
                Bundle argumenti = new Bundle();
            try {
                argumenti.putParcelable("glumac", glumci.get(0));
                df.setArguments(argumenti);
                fm.beginTransaction().replace(R.id.drugi, df).addToBackStack(null).commit();
            }
            catch (Exception e) {

            }


        }
        GlumciFragment gf;
        Bundle argumenti;

        gf = new GlumciFragment();

        final GlumciFragment gg = gf;

        final Button dugme_glumci = (Button) findViewById(R.id.button9);
        final Button dugme_ostalo = (Button) findViewById(R.id.button4);

        if (dugme_glumci != null && dugme_ostalo != null) {
            dugme_glumci.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentManager fm = getFragmentManager();
                    fm.beginTransaction().replace(R.id.liste, gg).commit();
                    DetaljiFragment df = new DetaljiFragment();
                    Bundle argumenti = new Bundle();
                    try {
                        argumenti.putParcelable("glumac", glumci.get(0));
                        df.setArguments(argumenti);
                        fm.beginTransaction().replace(R.id.drugi, df).addToBackStack(null).commit();
                    }
                    catch (Exception e) {

                    }
                }
            });

            dugme_ostalo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentManager fm = getFragmentManager();
                    ReziseriFragment rf = new ReziseriFragment();
                    Bundle argumenti = new Bundle();
                    argumenti.putParcelableArrayList("lista_rezisera", reziseri);
                    rf.setArguments(argumenti);
                    fm.beginTransaction().replace(R.id.liste, rf).commit();

                    ZanroviFragment zf = new ZanroviFragment();
                    Bundle argumenti1 = new Bundle();
                    argumenti1.putParcelableArrayList("lista_zanrova", zanrovi);
                    zf.setArguments(argumenti1);
                    fm.beginTransaction().replace(R.id.drugi, zf).commit();
                }
            });
        }
        argumenti = new Bundle();
        argumenti.putParcelableArrayList("lista_glumaca", glumci);
        gf.setArguments(argumenti);
        fm.beginTransaction().replace(R.id.liste, gf).addToBackStack(null).commit();

        if (savedInstanceState == null) {



            if (ldetalji != null) {
                siri = true;
                DetaljiFragment df;
                df = (DetaljiFragment) fm.findFragmentById(R.id.drugi);
                if (df == null) {
                    df = new DetaljiFragment();
                    argumenti = new Bundle();
                    try {
                        argumenti.putParcelable("glumac", glumci.get(0));
                        df.setArguments(argumenti);
                        fm.beginTransaction().replace(R.id.drugi, df).addToBackStack(null).commit();
                    }
                    catch (Exception e) {

                    }
                }

            }

            if (!siri) {
                ButtonsFragment bf = (ButtonsFragment) fm.findFragmentById(R.id.dugmad);
                if (bf == null) {
                    bf = new ButtonsFragment();
                    fm.beginTransaction().replace(R.id.dugmad, bf).commit();
                }
            }

             gf = (GlumciFragment) fm.findFragmentById(R.id.liste);
            if (gf == null) {
                gf = new GlumciFragment();
                argumenti = new Bundle();
                argumenti.putParcelableArrayList("lista_glumaca", glumci);
                gf.setArguments(argumenti);
                fm.beginTransaction().replace(R.id.liste, gf).addToBackStack(null).commit();
            } else {
                fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }
        }


    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putParcelableArrayList("glumci", glumci);
        savedInstanceState.putParcelableArrayList("zanrovi", zanrovi);
        savedInstanceState.putParcelableArrayList("reziseri", reziseri);
        super.onSaveInstanceState(savedInstanceState);
    }



}
