package ba.unsa.etf.rma.rasimsabanovic.rma_projekat;

import android.app.Fragment;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DetaljiFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DetaljiFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetaljiFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private Glumac glumac;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public DetaljiFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DetaljiFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DetaljiFragment newInstance(String param1, String param2) {
        DetaljiFragment fragment = new DetaljiFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    public boolean DaLiJeUBazi(int id) {
        String[] kolone = new String[] { GlumciDBHelper.DATABASE_TMDB_ID };
        String where = GlumciDBHelper.DATABASE_TMDB_ID + "=" + id;
        GlumciDBHelper helper = new GlumciDBHelper(getActivity(), GlumciDBHelper.DATABASE_NAME, null, 1);
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor test = db.query(GlumciDBHelper.DATABASE_GLUMCI, kolone, where, null, null, null, null);
        if(test!=null && test.getCount() > 0)
            return true;
        return false;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getArguments().containsKey("glumac")) {

            glumac = getArguments().getParcelable("glumac");

            Bitmap b = BitmapFactory.decodeResource(getResources(), getResources().getIdentifier(
                    "ba.unsa.etf.rma.rasimsabanovic.rma_projekat:drawable/"+glumac.getSlika()
                    ,null,null));


            View v = getView();
            final ImageView slika = (ImageView) v.findViewById(R.id.imageView3);
            final TextView ime = (TextView) v.findViewById(R.id.textIme);
            final TextView god_rod = (TextView) v.findViewById(R.id.textViewRodjenje);
            final TextView god_smrt = (TextView) v.findViewById(R.id.textViewSmrt);
            final TextView mjesto = (TextView) v.findViewById(R.id.textViewMjesto);
            final TextView spol = (TextView) v.findViewById(R.id.textViewSpol);
            final TextView imdb = (TextView) v.findViewById(R.id.textViewImdb);
            final TextView bio = (TextView) v.findViewById(R.id.textViewBiografija);
            final Button dugme = (Button) v.findViewById(R.id.button);
            final Button dugmeBookmark = (Button) v.findViewById(R.id.buttonBookmark);
            final Button dugmeUnboomkark = (Button) v.findViewById(R.id.buttonUnBookmark);
            if (DaLiJeUBazi(glumac.getId())) {
                dugmeUnboomkark.setVisibility(View.VISIBLE);
                dugmeBookmark.setEnabled(false);
                dugmeUnboomkark.setEnabled(true);
            }
            else {
                dugmeUnboomkark.setVisibility(View.INVISIBLE);
                dugmeBookmark.setEnabled(true);
                dugmeUnboomkark.setEnabled(false);
            }
            imdb.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    String s = imdb.getText().toString().substring(5, imdb.getText().toString().length() - 1);
                    Uri uri = Uri.parse(s);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                }
            });

            dugmeUnboomkark.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    String where = GlumciDBHelper.DATABASE_TMDB_ID + "=" + String.valueOf(glumac.getId());

                    GlumciDBHelper helper = new GlumciDBHelper(getActivity(), GlumciDBHelper.DATABASE_NAME, null, 1);

                    SQLiteDatabase db = helper.getWritableDatabase();

                    db.delete(GlumciDBHelper.DATABASE_GLUMCI, where, null);

                    updateZanroviReziseri(glumac.getId());
                }
            });

            dugmeBookmark.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    ContentValues cv = new ContentValues();
                    cv.put(GlumciDBHelper.DATABASE_TMDB_ID, glumac.getId());
                    cv.put(GlumciDBHelper.DATABASE_IME, glumac.getIme());
                    cv.put(GlumciDBHelper.DATABASE_GODINA_RODJENJA, glumac.getGodina_rodjenja());
                    cv.put(GlumciDBHelper.DATABASE_GODINA_SMRTI, glumac.getGodina_smrti());
                    cv.put(GlumciDBHelper.DATABASE_BIOGRAFIJA, glumac.getBiografija());
                    cv.put(GlumciDBHelper.DATABASE_SLIKA, glumac.getSlika());
                    cv.put(GlumciDBHelper.DATABASE_RATING, glumac.getRating());
                    cv.put(GlumciDBHelper.DATABASE_MJESTO_RODJENJA, glumac.getMjesto_rodjenja());
                    cv.put(GlumciDBHelper.DATABASE_SPOL, glumac.getSpol());
                    cv.put(GlumciDBHelper.DATABASE_IMDB, glumac.getImdb());

                    GlumciDBHelper dbHelper = new GlumciDBHelper(getActivity(), GlumciDBHelper.DATABASE_NAME, null, 1);

                    SQLiteDatabase db = dbHelper.getWritableDatabase();

                    db.insert(GlumciDBHelper.DATABASE_GLUMCI, null, cv);

                    SaveState ss = (SaveState) getActivity().getApplication();

                    /*while (ss.getReziseri().size() == 0 || ss.getZanrovi().size() == 0) {
                        ss = (SaveState) getActivity().getApplication();
                    } */

                    String[] koloneZanrovi = new String[] {GlumciDBHelper.DATABASE_IME};
                    String[] koloneReziseri = new String[] {GlumciDBHelper.DATABASE_IME};

                    Cursor cursorZanrovi = db.query(GlumciDBHelper.DATABASE_ZANROVI, koloneZanrovi, null, null, null, null, null);
                    Cursor cursorReziseri = db.query(GlumciDBHelper.DATABASE_REZISERI, koloneReziseri, null, null, null, null, null);

                    ArrayList<String> zanrovi = new ArrayList<String>();
                    ArrayList<String> reziseri = new ArrayList<String>();

                    ArrayList<Integer> zanroviId = new ArrayList<Integer>();
                    ArrayList<Integer> reziseriId = new ArrayList<Integer>();

                    int indeksKoloneZanr = cursorZanrovi.getColumnIndexOrThrow(GlumciDBHelper.DATABASE_IME);
                    int indeksKoloneReziser = cursorReziseri.getColumnIndexOrThrow(GlumciDBHelper.DATABASE_IME);

                   // int indeksKoloneZanrId = cursorZanrovi.getColumnIndexOrThrow(GlumciDBHelper.DATABASE_ID);
                   // int indeksKoloneReziserId = cursorReziseri.getColumnIndexOrThrow(GlumciDBHelper.DATABASE_ID);

                    while (cursorZanrovi.moveToNext()) {
                        zanrovi.add(cursorZanrovi.getString(indeksKoloneZanr));
                        //zanroviId.add(cursorZanrovi.getInt(indeksKoloneZanrId));
                    }

                    while (cursorReziseri.moveToNext()) {
                        reziseri.add(cursorReziseri.getString(indeksKoloneReziser));
                       // reziseriId.add(cursorReziseri.getInt(indeksKoloneReziserId));
                    }

                    ArrayList<Zanr> zanroviServis = ss.getZanrovi();
                    ArrayList<Reziser> reziseriServis = ss.getReziseri();

                    for (int i = 0; i < zanroviServis.size(); i++) {
                        Boolean brejk = false;
                        for (int j = 0; j < zanrovi.size(); j++) {
                            if (zanroviServis.get(i).getIme().equals(zanrovi.get(j))) {
                                brejk = true;
                                break;
                            }
                        }
                        if (brejk) continue;

                        ContentValues cvZanr = new ContentValues();
                        cvZanr.put(GlumciDBHelper.DATABASE_IME, zanroviServis.get(i).getIme());
                        cvZanr.put(GlumciDBHelper.DATABASE_SLIKA, zanroviServis.get(i).getSlika());

                        db.insert(GlumciDBHelper.DATABASE_ZANROVI, null, cvZanr);
                    }

                    for (int i = 0; i < reziseriServis.size(); i++) {
                        Boolean brejk = false;
                        for (int j = 0; j < reziseri.size(); j++) {
                            if (reziseriServis.get(i).getIme().equals(reziseri.get(j))) {
                                brejk = true;
                                break;
                            }
                        }
                        if (brejk) continue;

                        ContentValues cvReziser = new ContentValues();
                        cvReziser.put(GlumciDBHelper.DATABASE_IME, reziseriServis.get(i).getIme());

                        db.insert(GlumciDBHelper.DATABASE_REZISERI, null, cvReziser);
                    }

                    koloneZanrovi = new String[] {GlumciDBHelper.DATABASE_ID, GlumciDBHelper.DATABASE_IME};
                    koloneReziseri = new String[] {GlumciDBHelper.DATABASE_ID, GlumciDBHelper.DATABASE_IME};

                    cursorZanrovi = db.query(GlumciDBHelper.DATABASE_ZANROVI, koloneZanrovi, null, null, null, null, null);
                    cursorReziseri = db.query(GlumciDBHelper.DATABASE_REZISERI, koloneReziseri, null, null, null, null, null);

                    zanrovi = new ArrayList<String>();
                    reziseri = new ArrayList<String>();

                    zanroviId = new ArrayList<Integer>();
                    reziseriId = new ArrayList<Integer>();

                    indeksKoloneZanr = cursorZanrovi.getColumnIndexOrThrow(GlumciDBHelper.DATABASE_IME);
                    indeksKoloneReziser = cursorReziseri.getColumnIndexOrThrow(GlumciDBHelper.DATABASE_IME);

                    int indeksKoloneZanrId = cursorZanrovi.getColumnIndexOrThrow(GlumciDBHelper.DATABASE_ID);
                    int indeksKoloneReziserId = cursorReziseri.getColumnIndexOrThrow(GlumciDBHelper.DATABASE_ID);

                    while (cursorZanrovi.moveToNext()) {
                        zanrovi.add(cursorZanrovi.getString(indeksKoloneZanr));
                        zanroviId.add(cursorZanrovi.getInt(indeksKoloneZanrId));
                    }

                    while (cursorReziseri.moveToNext()) {
                        reziseri.add(cursorReziseri.getString(indeksKoloneReziser));
                        reziseriId.add(cursorReziseri.getInt(indeksKoloneReziserId));
                    }

                    for (int i = 0; i < reziseriServis.size(); i++) {
                        for (int j = 0; j < reziseri.size(); j++) {
                            if (reziseriServis.get(i).getIme().equals(reziseri.get(j))) {
                                ContentValues cvReziserVeza = new ContentValues();
                                cvReziserVeza.put("glumac_id", glumac.getId());
                                cvReziserVeza.put("reziser_id", reziseriId.get(j));

                                db.insert(GlumciDBHelper.DATABASE_REZISERI_VEZE, null, cvReziserVeza);
                            }
                        }
                    }

                    for (int i = 0; i < zanroviServis.size(); i++) {
                        for (int j = 0; j < zanrovi.size(); j++) {
                            if (zanroviServis.get(i).getIme().equals(zanrovi.get(j))) {
                                ContentValues cvZanrVeza = new ContentValues();
                                cvZanrVeza.put("glumac_id", glumac.getId());
                                cvZanrVeza.put("zanr_id", zanroviId.get(j));

                                db.insert(GlumciDBHelper.DATABASE_ZANROVI_VEZE, null, cvZanrVeza);
                            }
                        }
                    }
                }
            });

            dugme.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT, bio.getText().toString());
                    sendIntent.setType("text/plain");
                    if (sendIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                        startActivity(Intent.createChooser(sendIntent, getResources().getText(R.string.app_name)));
                    }
                }
            });

            String imageUri = glumac.getSlika();

            Picasso.with(getActivity()).load(imageUri).resize(300, 300).centerInside().into(slika);

            ime.setText(glumac.getIme());

            god_rod.setText(getText(R.string.godina_rod) + Integer.toString(glumac.getGodina_rodjenja()));
            if (glumac.getGodina_smrti() != -1) {
                god_smrt.setVisibility(View.VISIBLE);
                god_smrt.setText(getText(R.string.godina_smrt) + Integer.toString(glumac.getGodina_smrti()));
            }
            mjesto.setText(getText(R.string.mjesto_rod) + glumac.getMjesto_rodjenja());
            if (glumac.getSpol().equals("M")) {
                spol.setText(getText(R.string.spol) + "Male");
                LinearLayout rl = (LinearLayout) getView().findViewById(R.id.lejaut);
                rl.setBackgroundColor(getResources().getColor(R.color.darkblue));
            } else if (glumac.getSpol().equals("F")) {
                spol.setText(getText(R.string.spol) +  "Female");
                LinearLayout rl = (LinearLayout) getView().findViewById(R.id.lejaut);
                rl.setBackgroundColor(getResources().getColor(R.color.purple));
            }
            else
                spol.setText(getText(R.string.spol) + "Other");



            imdb.setText(getText(R.string.imdb) + glumac.getImdb());
            bio.setText(getText(R.string.bio) + "\n" + glumac.getBiografija());
            bio.setMovementMethod(new ScrollingMovementMethod());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detalji, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    public void updateZanroviReziseri(int id) {
        String where = "glumac_id" + "=" + String.valueOf(id);

        GlumciDBHelper helper = new GlumciDBHelper(getActivity(), GlumciDBHelper.DATABASE_NAME, null, 1);

        SQLiteDatabase db = helper.getWritableDatabase();

        db.delete(GlumciDBHelper.DATABASE_ZANROVI_VEZE, where, null);
        db.delete(GlumciDBHelper.DATABASE_REZISERI_VEZE, where, null);

        String[] kolone1 = new String[] {GlumciDBHelper.DATABASE_ID};
        String[] koloneZanrovi = new String[] {"zanr_id"};
        String[] koloneReziseri = new String[] {"reziser_id"};

        Cursor cursor1 = db.query(GlumciDBHelper.DATABASE_ZANROVI, kolone1, null, null, null, null, null);
        Cursor cursor2 = db.query(GlumciDBHelper.DATABASE_REZISERI, kolone1, null, null, null, null, null);
        Cursor cursorZanrovi = db.query(GlumciDBHelper.DATABASE_ZANROVI_VEZE, koloneZanrovi, null, null, null, null, null);
        Cursor cursorReziseri = db.query(GlumciDBHelper.DATABASE_REZISERI_VEZE, koloneReziseri, null, null, null, null, null);

        ArrayList<Integer> zanrovi = new ArrayList<>();
        ArrayList<Integer> reziseri = new ArrayList<>();
        ArrayList<Integer> zanroviVeze = new ArrayList<>();
        ArrayList<Integer> reziseriVeze = new ArrayList<>();

        int kolonaZanr = cursor1.getColumnIndexOrThrow(GlumciDBHelper.DATABASE_ID);
        int kolonaReziser = cursor2.getColumnIndexOrThrow(GlumciDBHelper.DATABASE_ID);
        int kolonaZanrVeze = cursorZanrovi.getColumnIndexOrThrow("zanr_id");
        int kolonaReziserVeze = cursorReziseri.getColumnIndexOrThrow("reziser_id");

        while (cursor1.moveToNext()) {
            zanrovi.add(cursor1.getInt(kolonaZanr));
        }

        while (cursor2.moveToNext()) {
            reziseri.add(cursor2.getInt(kolonaReziser));
        }

        while (cursorZanrovi.moveToNext()) {
            zanroviVeze.add(cursorZanrovi.getInt(kolonaZanrVeze));
        }

        while (cursorReziseri.moveToNext()) {
            reziseriVeze.add(cursorReziseri.getInt(kolonaReziserVeze));
        }

        for (int i = 0; i < zanrovi.size(); i++) {
            if (!zanroviVeze.contains(zanrovi.get(i))) {
                String whereZanr = GlumciDBHelper.DATABASE_ID + "=" + String.valueOf(zanrovi.get(i));
                db.delete(GlumciDBHelper.DATABASE_ZANROVI, whereZanr, null);
            }
        }

        for (int i = 0; i < reziseri.size(); i++) {
            if (!reziseriVeze.contains(reziseri.get(i))) {
                String whereReziser = GlumciDBHelper.DATABASE_ID + "=" + String.valueOf(reziseri.get(i));
                db.delete(GlumciDBHelper.DATABASE_REZISERI, whereReziser, null);
            }
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
        void onFragmentInteraction(Uri uri);
    }
}
