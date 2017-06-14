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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FilmoviFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FilmoviFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FilmoviFragment extends Fragment implements PretragaResultReceiver.Receiver {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private onFilmClick ofc;

    private ArrayList<String> filmovi;

    private ListView lv;

    private ArrayAdapter<String> adapter;

    private OnFragmentInteractionListener mListener;

    private PretragaResultReceiver mReceiver;

    @Override
    public void onReceiveResult(int resultCode, Bundle resultData) {
        switch (resultCode) {
            case PretragaFilmova.STATUS_RUNNING:
                break;
            case PretragaFilmova.STATUS_FINISHED:
                filmovi = resultData.getStringArrayList("filmovi");
                adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, filmovi);
                View v = getView();
                lv = null;
                if (v != null) {
                    lv = (ListView) getView().findViewById(R.id.listaFilmovi);
                }
                OnFragmentInteractionListener listener = (OnFragmentInteractionListener) getActivity();
                listener.onFragmentSetFilmovi(filmovi);

                lv.setAdapter(adapter);

                break;
            case PretragaFilmova.STATUS_ERROR:
                String error = resultData.getString(Intent.EXTRA_TEXT);
                //           Toast.makeText(getActivity(), error, Toast.LENGTH_LONG).show();
                break;
        }
    }

    public FilmoviFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FilmoviFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FilmoviFragment newInstance(String param1, String param2) {
        FilmoviFragment fragment = new FilmoviFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_filmovi, container, false);
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
    public void onActivityCreated(final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        SaveState s = (SaveState) getActivity().getApplication();
        filmovi = s.getFilmovi();

        GlumciAktivnost g = (GlumciAktivnost) getActivity();
        if (g != null) {
            g.setFilmovi(filmovi);
        }

        lv = (ListView)getView().findViewById(R.id.listaFilmovi);
        final Button pretraga = (Button)getView().findViewById(R.id.buttonPretraga);
        final EditText tekstPretrage = (EditText)getView().findViewById(R.id.editTextPrertraga);


        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, filmovi);
        adapter.notifyDataSetChanged();
        lv.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        try {
            ofc = (onFilmClick)getActivity();
        }
        catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString() + "treba implementirati onFilmClick");
        }


        pretraga.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SYNC, null, getActivity(), PretragaFilmova.class);
                mReceiver = new PretragaResultReceiver(new Handler());
                mReceiver.setReceiver(FilmoviFragment.this);

                String tekst = tekstPretrage.getText().toString();


                intent.putExtra("receiver", mReceiver);
                intent.putExtra("query", tekst);

                getActivity().startService(intent);
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ofc.click(position);
            }
        });

    }

    public interface onFilmClick {
        public void click(int pos);
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
        void onFragmentSetFilmovi(ArrayList<String> filmovi);
    }
}
