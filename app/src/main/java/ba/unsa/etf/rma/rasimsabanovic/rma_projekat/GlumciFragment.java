package ba.unsa.etf.rma.rasimsabanovic.rma_projekat;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
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
                ListView lv = null;
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
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getArguments().containsKey("lista_glumaca")) {

            SaveState s = (SaveState) getActivity().getApplication();
            glumci = s.getGlumci();

            GlumciAktivnost g = (GlumciAktivnost) getActivity();
            if (g != null) {
                g.setGlumci(glumci);
            }

            ListView lv = (ListView)getView().findViewById(R.id.listaGlumci);
            final Button pretraga = (Button)getView().findViewById(R.id.buttonPretraga);
            final EditText tekstPretrage = (EditText)getView().findViewById(R.id.editTextPrertraga);

            GlumacAdapter ga = new GlumacAdapter(getActivity(), R.layout.glumac_u_listi, glumci, getResources());
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
