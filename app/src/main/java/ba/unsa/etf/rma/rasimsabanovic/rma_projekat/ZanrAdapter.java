package ba.unsa.etf.rma.rasimsabanovic.rma_projekat;

import android.content.Context;
import android.content.res.Resources;
import android.media.Image;
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

public class ZanrAdapter extends ArrayAdapter {

    private ArrayList<Zanr> zanrovi;
    Context c;
    Resources res;

    public ZanrAdapter(Context context, int resource, ArrayList<Zanr> zanrovi, Resources res) {
        super(context, resource, zanrovi);
        this.zanrovi = zanrovi;
        c = context;
        this.res = res;
    }

    public class Holder {
        TextView imeprezime;
        ImageView slika;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder h = new Holder();
        View rowView;
        LayoutInflater inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        rowView = inflater.inflate(R.layout.zanr_u_listi, null);
        h.imeprezime = (TextView) rowView.findViewById(R.id.textView2);
        h.imeprezime.setText(zanrovi.get(position).getIme());
        h.slika = (ImageView) rowView.findViewById(R.id.imageView2);
        h.slika.setImageResource(res.getIdentifier("ba.unsa.etf.rma.rasimsabanovic.rma_projekat:drawable/" + zanrovi.get(position).getSlika(), null, null));
        return rowView;
    }

}
