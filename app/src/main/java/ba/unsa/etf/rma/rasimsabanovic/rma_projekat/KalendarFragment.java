package ba.unsa.etf.rma.rasimsabanovic.rma_projekat;

import android.app.Fragment;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import android.provider.CalendarContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;
import java.util.TimeZone;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link KalendarFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link KalendarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class KalendarFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private String film;

    public KalendarFragment() {
        // Required empty public constructor
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment KalendarFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static KalendarFragment newInstance(String param1, String param2) {
        KalendarFragment fragment = new KalendarFragment();
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
        return inflater.inflate(R.layout.fragment_kalendar, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getArguments().containsKey("film")) {
            film = getArguments().getString("film");

            final TextView imeFilma = (TextView) getView().findViewById(R.id.textViewImeFilma);
            final DatePicker datum = (DatePicker) getView().findViewById(R.id.datePicker);
            final EditText komentar = (EditText) getView().findViewById(R.id.editTextKomentar);
            final Button zapamti = (Button) getView().findViewById(R.id.buttonZapamti);

            imeFilma.setText(film);

            if (!GlumciAktivnost.permisija) {
                zapamti.setEnabled(false);
            }
            else {
                zapamti.setEnabled(true);
            }

            zapamti.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Calendar beginTime = Calendar.getInstance();
                    beginTime.set(datum.getYear(), datum.getMonth(), datum.getDayOfMonth());

                    Calendar endTime = Calendar.getInstance();
                    endTime.set(datum.getYear(), datum.getMonth(), datum.getDayOfMonth());

                    TimeZone timeZone = TimeZone.getDefault();

                    String projection[] = {"_id", "calendar_displayName"};
                    Uri calendars;
                    calendars = Uri.parse("content://com.android.calendar/calendars");

                    ContentResolver contentResolver = getActivity().getContentResolver();
                    Cursor managedCursor = contentResolver.query(calendars, projection, null, null, null);

                    int kal_id;
                    int kolona_id = managedCursor.getColumnIndexOrThrow("_id");

                    if (managedCursor.getCount() > 0)  {
                        managedCursor.moveToFirst();
                        kal_id = managedCursor.getInt(kolona_id);
                        ContentValues values = new ContentValues();
                        values.put(CalendarContract.Events.EVENT_TIMEZONE, timeZone.getID());
                        values.put(CalendarContract.Events.CALENDAR_ID, kal_id);
                        values.put(CalendarContract.Events.DTSTART, beginTime.getTimeInMillis());
                        values.put(CalendarContract.Events.DTEND, endTime.getTimeInMillis());
                        values.put(CalendarContract.Events.TITLE, imeFilma.getText().toString());
                        values.put(CalendarContract.Events.DESCRIPTION, komentar.getText().toString());
                        try {
                            Uri uri = contentResolver.insert(CalendarContract.Events.CONTENT_URI, values);
                        }
                        catch (SecurityException e) {

                        }
                    }
                    else {
                        ContentValues values = new ContentValues();
                        values.put(CalendarContract.Calendars.CALENDAR_DISPLAY_NAME, "Prvi");
                        values.put(CalendarContract.Calendars.NAME,"Prvi");
                        Uri updateUri = CalendarContract.Calendars.CONTENT_URI;
                        try {
                            Uri uri = getActivity().getContentResolver().insert(updateUri, values);
                        }
                        catch (Exception e) {

                        }
                        ContentValues values1 = new ContentValues();
                        values.put(CalendarContract.Events.EVENT_TIMEZONE, timeZone.getID());
                        values.put(CalendarContract.Events.CALENDAR_ID, 1);
                        values.put(CalendarContract.Events.DTSTART, beginTime.getTimeInMillis());
                        values.put(CalendarContract.Events.DTEND, endTime.getTimeInMillis());
                        values.put(CalendarContract.Events.TITLE, imeFilma.getText().toString());
                        values.put(CalendarContract.Events.DESCRIPTION, komentar.getText().toString());
                        try {
                            Uri uri = contentResolver.insert(CalendarContract.Events.CONTENT_URI, values);
                        }
                        catch (SecurityException e) {

                        }
                    }

                    /*ContentValues values = new ContentValues();
                    values.put(CalendarContract.Events.EVENT_TIMEZONE, timeZone.getID());
                    values.put(CalendarContract.Events.CALENDAR_ID, 1);
                    values.put(CalendarContract.Events.DTSTART, beginTime.getTimeInMillis());
                    values.put(CalendarContract.Events.DTEND, endTime.getTimeInMillis());
                    values.put(CalendarContract.Events.TITLE, imeFilma.getText().toString());
                    values.put(CalendarContract.Events.DESCRIPTION, komentar.getText().toString());
                    try {
                        Uri uri = cr.insert(CalendarContract.Events.CONTENT_URI, values);
                    }
                    catch (SecurityException e) {

                    } */

                }
            });
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
