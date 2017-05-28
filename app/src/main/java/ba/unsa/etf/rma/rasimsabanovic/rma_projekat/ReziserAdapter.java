package ba.unsa.etf.rma.rasimsabanovic.rma_projekat;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Rasim on 3/25/2017.
 */

public class ReziserAdapter extends ArrayAdapter {

    private ArrayList<Reziser> reziseri;
    Context c;
    Resources res;

    public ReziserAdapter(Context context, int resource, ArrayList<Reziser> reziseri, Resources res) {
        super(context, resource, reziseri);
        this.reziseri = reziseri;
        c = context;
        this.res = res;
    }

    public class Holder {
        TextView imeprezime;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder h = new Holder();
        View rowView;
        LayoutInflater inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        rowView = inflater.inflate(R.layout.reziser_u_listi, null);
        h.imeprezime = (TextView) rowView.findViewById(R.id.textView2);
        h.imeprezime.setText(reziseri.get(position).getIme());
        return rowView;
    }

}
