package ba.unsa.etf.rma.rasimsabanovic.rma_projekat;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
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

            imdb.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    String s = imdb.getText().toString().substring(6, imdb.getText().toString().length() - 1);
                    Uri uri = Uri.parse(s);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
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

            slika.setImageBitmap(b);
            ime.setText(glumac.getIme() + " " + glumac.getPrezime());

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
