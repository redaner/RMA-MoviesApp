package ba.unsa.etf.rma.rasimsabanovic.rma_projekat;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ButtonsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ButtonsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ButtonsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private buttonClick oic;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ButtonsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ButtonsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ButtonsFragment newInstance(String param1, String param2) {
        ButtonsFragment fragment = new ButtonsFragment();
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

    public interface buttonClick {
        public void onGlumciClick();
        public void onReziseriClick();
        public void onZanroviClick();
    }

    private Button dugme_glumci;
    private Button dugme_reziseri;
    private Button dugme_zanrovi;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        View v = getView();


        try {
            oic = (buttonClick)getActivity();

        }
        catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString() + "Treba implementirati buttonClick");
        }
        dugme_zanrovi.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                oic.onZanroviClick();
            }
        });

        dugme_reziseri.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                oic.onReziseriClick();
            }
        });

        dugme_glumci.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                oic.onGlumciClick();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_buttons, container, false);

        final Button dugme_glumci1 = (Button)v.findViewById(R.id.buttonGlumci);
        final Button dugme_reziseri1 = (Button)v.findViewById(R.id.buttonReziseri);
        final Button dugme_zanrovi1 = (Button)v.findViewById(R.id.buttonZanrovi);
        final ImageView ikona = (ImageView)v.findViewById(R.id.imageView5);


        dugme_glumci = dugme_glumci1;
        dugme_reziseri = dugme_reziseri1;
        dugme_zanrovi = dugme_zanrovi1;

        ikona.setImageResource(R.drawable.ikona);
        dugme_glumci.setText(R.string.glumci);
        dugme_reziseri.setText(R.string.reziseri);
        dugme_zanrovi.setText(R.string.zanrovi);

        return v;
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
