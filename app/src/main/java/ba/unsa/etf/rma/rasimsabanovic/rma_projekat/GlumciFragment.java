package ba.unsa.etf.rma.rasimsabanovic.rma_projekat;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link GlumciFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link GlumciFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GlumciFragment extends Fragment implements PretragaResultReceiver.Receiver{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private onGlumacClick oic;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ArrayList<Glumac> glumci = new ArrayList<>();

    private OnFragmentInteractionListener mListener;

    private PretragaResultReceiver mReceiver;

    public static Boolean baza = false;

    private ListView lv;

    private GlumacAdapter ga;

    public GlumciFragment() {
        // Required empty public constructor
    }

    @Override
    public void onReceiveResult(int resultCode, Bundle resultData) {
        switch (resultCode) {
            case PretragaGlumaca.STATUS_RUNNING:
                break;
            case PretragaGlumaca.STATUS_FINISHED:
                glumci = resultData.getParcelableArrayList("glumci");
                GlumacAdapter ga = new GlumacAdapter(getActivity(), R.layout.glumac_u_listi, glumci, getResources());
                View v = getView();
                lv = null;
                if (v != null) {
                    lv = (ListView) getView().findViewById(R.id.listaGlumci);
                }
                OnFragmentInteractionListener listener = (OnFragmentInteractionListener) getActivity();
                listener.onFragmentSetGlumci(glumci);

                lv.setAdapter(ga);
                break;
            case PretragaGlumaca.STATUS_ERROR:
                String error = resultData.getString(Intent.EXTRA_TEXT);
     //           Toast.makeText(getActivity(), error, Toast.LENGTH_LONG).show();
                break;
        }
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GlumciFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GlumciFragment newInstance(String param1, String param2) {
        GlumciFragment fragment = new GlumciFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public interface onGlumacClick {
        public void klik(int pos);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_glumci, container, false);
        // Inflate the layout for this fragment

        return v;
    }

    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getArguments().containsKey("lista_glumaca")) {

            SaveState s = (SaveState) getActivity().getApplication();
            glumci = s.getGlumci();

            GlumciAktivnost g = (GlumciAktivnost) getActivity();
            if (g != null) {
                g.setGlumci(glumci);
            }

            lv = (ListView)getView().findViewById(R.id.listaGlumci);
            final Button pretraga = (Button)getView().findViewById(R.id.buttonPretraga);
            final EditText tekstPretrage = (EditText)getView().findViewById(R.id.editTextPrertraga);

            ga = new GlumacAdapter(getActivity(), R.layout.glumac_u_listi, glumci, getResources());
            ga.notifyDataSetChanged();
            lv.setAdapter(ga);
            ga.notifyDataSetChanged();

            try {
                oic = (onGlumacClick)getActivity();
            }
            catch (ClassCastException e) {
                throw new ClassCastException(getActivity().toString() + "treba implementirati onGlumacClick");

            }

            pretraga.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_SYNC, null, getActivity(), PretragaGlumaca.class);
                    mReceiver = new PretragaResultReceiver(new Handler());
                    mReceiver.setReceiver(GlumciFragment.this);

                    String tekst = tekstPretrage.getText().toString();

                    if (tekst.length() >= 6) {
                        if (tekst.substring(0, 6).equals("actor:")) {
                            baza = true;
                            String imeSrc = tekst.substring(6);
                            String[] kolone = new String[]{GlumciDBHelper.DATABASE_TMDB_ID, GlumciDBHelper.DATABASE_IME, GlumciDBHelper.DATABASE_GODINA_RODJENJA, GlumciDBHelper.DATABASE_GODINA_SMRTI,
                                                         GlumciDBHelper.DATABASE_BIOGRAFIJA, GlumciDBHelper.DATABASE_SLIKA, GlumciDBHelper.DATABASE_RATING, GlumciDBHelper.DATABASE_MJESTO_RODJENJA,
                                                         GlumciDBHelper.DATABASE_SPOL, GlumciDBHelper.DATABASE_IMDB};

                            String where = GlumciDBHelper.DATABASE_IME + " LIKE '%" + imeSrc + "%'";

                            GlumciDBHelper helper = new GlumciDBHelper(getActivity(), GlumciDBHelper.DATABASE_NAME, null, 1);

                            SQLiteDatabase db = helper.getWritableDatabase();

                            Cursor cursor = db.query(GlumciDBHelper.DATABASE_GLUMCI, kolone, where, null, null, null, null);

                            int tmdb_id = cursor.getColumnIndexOrThrow(GlumciDBHelper.DATABASE_TMDB_ID);
                            int ime = cursor.getColumnIndexOrThrow(GlumciDBHelper.DATABASE_IME);
                            int grod = cursor.getColumnIndexOrThrow(GlumciDBHelper.DATABASE_GODINA_RODJENJA);
                            int gsmrt = cursor.getColumnIndexOrThrow(GlumciDBHelper.DATABASE_GODINA_SMRTI);
                            int bio = cursor.getColumnIndexOrThrow(GlumciDBHelper.DATABASE_BIOGRAFIJA);
                            int slika = cursor.getColumnIndexOrThrow(GlumciDBHelper.DATABASE_SLIKA);
                            int rating = cursor.getColumnIndexOrThrow(GlumciDBHelper.DATABASE_RATING);
                            int mrod = cursor.getColumnIndexOrThrow(GlumciDBHelper.DATABASE_MJESTO_RODJENJA);
                            int spol = cursor.getColumnIndexOrThrow(GlumciDBHelper.DATABASE_SPOL);
                            int imdb = cursor.getColumnIndexOrThrow(GlumciDBHelper.DATABASE_IMDB);

                            glumci = new ArrayList<Glumac>();

                            while (cursor.moveToNext()) {
                                int _tmdb = cursor.getInt(tmdb_id);
                                String _ime = cursor.getString(ime);
                                int _grod = cursor.getInt(grod);
                                int _gsmrt = cursor.getInt(gsmrt);
                                String _bio = cursor.getString(bio);
                                String _slika = cursor.getString(slika);
                                Double _rating = cursor.getDouble(rating);
                                String _mrod = cursor.getString(mrod);
                                String _spol = cursor.getString(spol);
                                String _imdb = cursor.getString(imdb);

                                glumci.add(new Glumac(_tmdb, _ime, _grod, _gsmrt, _bio, _rating, _slika, _mrod, _spol, _imdb));
                            }

                        }
                        else if (tekst.substring(0, 9).equals("director:")) {
                            baza = true;
                            String imeRezisera = tekst.substring(9);
                            String[] koloneIdRezisera = new String[] {GlumciDBHelper.DATABASE_ID};
                            String whereIdRezisera = GlumciDBHelper.DATABASE_IME + " LIKE '%" + imeRezisera + "%'";
                            GlumciDBHelper helper = new GlumciDBHelper(getActivity(), GlumciDBHelper.DATABASE_NAME, null, 1);
                            SQLiteDatabase db = helper.getWritableDatabase();

                            Cursor cursorIdRezisera = db.query(GlumciDBHelper.DATABASE_REZISERI, koloneIdRezisera, whereIdRezisera, null, null, null, null);

                            ArrayList<Integer> listaIdevaRezisera = new ArrayList<Integer>();

                            int kolonaIdRezisera = cursorIdRezisera.getColumnIndexOrThrow(GlumciDBHelper.DATABASE_ID);

                            while (cursorIdRezisera.moveToNext()) {
                                listaIdevaRezisera.add(cursorIdRezisera.getInt(kolonaIdRezisera));
                            }

                            ArrayList<Integer> listaIdevaGlumaca = new ArrayList<Integer>();

                            for (int i = 0; i < listaIdevaRezisera.size(); i++) {
                                String[] koloneIdGlumaca = new String[] {"glumac_id"};
                                String whereIdGlumca = "reziser_id = " + String.valueOf(listaIdevaRezisera.get(i));

                                Cursor cursorIdGlumca = db.query(GlumciDBHelper.DATABASE_REZISERI_VEZE, koloneIdGlumaca, whereIdGlumca, null, null, null, null);

                                int kolonaIdGlumca = cursorIdGlumca.getColumnIndexOrThrow("glumac_id");

                                while (cursorIdGlumca.moveToNext()) {
                                    if (!listaIdevaGlumaca.contains(cursorIdGlumca.getInt(kolonaIdGlumca))) {
                                        listaIdevaGlumaca.add(cursorIdGlumca.getInt(kolonaIdGlumca));
                                    }
                                }
                            }

                            glumci = new ArrayList<Glumac>();

                            for (int i = 0; i < listaIdevaGlumaca.size(); i++) {

                                String[] kolone = new String[]{GlumciDBHelper.DATABASE_TMDB_ID, GlumciDBHelper.DATABASE_IME, GlumciDBHelper.DATABASE_GODINA_RODJENJA, GlumciDBHelper.DATABASE_GODINA_SMRTI,
                                        GlumciDBHelper.DATABASE_BIOGRAFIJA, GlumciDBHelper.DATABASE_SLIKA, GlumciDBHelper.DATABASE_RATING, GlumciDBHelper.DATABASE_MJESTO_RODJENJA,
                                        GlumciDBHelper.DATABASE_SPOL, GlumciDBHelper.DATABASE_IMDB};

                                String where = GlumciDBHelper.DATABASE_TMDB_ID + "=" + String.valueOf(listaIdevaGlumaca.get(i));

                                Cursor cursor1 = db.query(GlumciDBHelper.DATABASE_GLUMCI, kolone, where, null, null, null, null);

                                int tmdb_id = cursor1.getColumnIndexOrThrow(GlumciDBHelper.DATABASE_TMDB_ID);
                                int ime = cursor1.getColumnIndexOrThrow(GlumciDBHelper.DATABASE_IME);
                                int grod = cursor1.getColumnIndexOrThrow(GlumciDBHelper.DATABASE_GODINA_RODJENJA);
                                int gsmrt = cursor1.getColumnIndexOrThrow(GlumciDBHelper.DATABASE_GODINA_SMRTI);
                                int bio = cursor1.getColumnIndexOrThrow(GlumciDBHelper.DATABASE_BIOGRAFIJA);
                                int slika = cursor1.getColumnIndexOrThrow(GlumciDBHelper.DATABASE_SLIKA);
                                int rating = cursor1.getColumnIndexOrThrow(GlumciDBHelper.DATABASE_RATING);
                                int mrod = cursor1.getColumnIndexOrThrow(GlumciDBHelper.DATABASE_MJESTO_RODJENJA);
                                int spol = cursor1.getColumnIndexOrThrow(GlumciDBHelper.DATABASE_SPOL);
                                int imdb = cursor1.getColumnIndexOrThrow(GlumciDBHelper.DATABASE_IMDB);

                                while(cursor1.moveToNext()) {
                                    int _tmdb = cursor1.getInt(tmdb_id);
                                    String _ime = cursor1.getString(ime);
                                    int _grod = cursor1.getInt(grod);
                                    int _gsmrt = cursor1.getInt(gsmrt);
                                    String _bio = cursor1.getString(bio);
                                    String _slika = cursor1.getString(slika);
                                    Double _rating = cursor1.getDouble(rating);
                                    String _mrod = cursor1.getString(mrod);
                                    String _spol = cursor1.getString(spol);
                                    String _imdb = cursor1.getString(imdb);
                                    glumci.add(new Glumac(_tmdb, _ime, _grod, _gsmrt, _bio, _rating, _slika, _mrod, _spol, _imdb));
                                }

                            }
                        }
                        else baza = false;
                    }
                    else baza = false;

                    if (baza) {
                        ga = new GlumacAdapter(getActivity(), R.layout.glumac_u_listi, glumci, getResources());
                        v = getView();
                        lv = null;
                        if (v != null) {
                            lv = (ListView) getView().findViewById(R.id.listaGlumci);
                        }
                        OnFragmentInteractionListener listener = (OnFragmentInteractionListener) getActivity();
                        listener.onFragmentSetGlumci(glumci);

                        onActivityCreated(savedInstanceState);

                        lv.setAdapter(ga);
                        return;
                    }

                    baza = false;
                    intent.putExtra("receiver", mReceiver);
                    intent.putExtra("query", tekst);

                    getActivity().startService(intent);
                }
            });

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    oic.klik(position);
                }
            });
        }

    }



    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
          //  mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        //void onFragmentInteraction(Uri uri);
        void onFragmentSetGlumci(ArrayList<Glumac> g);
    }
}
