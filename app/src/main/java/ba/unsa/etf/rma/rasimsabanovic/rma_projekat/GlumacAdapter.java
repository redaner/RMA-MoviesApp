package ba.unsa.etf.rma.rasimsabanovic.rma_projekat;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Rasim on 3/24/2017.
 */

public class GlumacAdapter extends ArrayAdapter {

    private ArrayList<Glumac> glumci;
    Context c;
    Resources res;

    public GlumacAdapter(Context context, int resource, ArrayList<Glumac> glumci, Resources res) {
        super(context, resource, glumci);
        this.glumci = glumci;
        c = context;
        this.res = res;
    }

    public class Holder {
        TextView imeprezime;
        ImageView sl;
        TextView godina;
        TextView mjesto;
        TextView rating;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        notifyDataSetChanged();
        Holder h = new Holder();
        View rowView;
        LayoutInflater inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        rowView = inflater.inflate(R.layout.glumac_u_listi, null);
        h.imeprezime = (TextView) rowView.findViewById(R.id.textView2);
        h.sl = (ImageView) rowView.findViewById(R.id.imageView);
        h.godina = (TextView) rowView.findViewById(R.id.textView3);
        h.mjesto = (TextView) rowView.findViewById(R.id.textView5);
        h.rating = (TextView) rowView.findViewById(R.id.textView4);

        h.imeprezime.setText(glumci.get(position).getIme());
        notifyDataSetChanged();
        String imageUri = glumci.get(position).getSlika();

        Picasso.with(getContext()).load(imageUri).into(h.sl);
        notifyDataSetChanged();

      /*  h.sl.setImageResource(res.getIdentifier(
                "ba.unsa.etf.rma.rasimsabanovic.rma_projekat:drawable/"+glumci.get(position).getSlika()
                ,null,null)); */
        h.godina.setText(Integer.toString(glumci.get(position).getGodina_rodjenja()));
        notifyDataSetChanged();
        h.mjesto.setText(glumci.get(position).getMjesto_rodjenja());
        notifyDataSetChanged();
        h.rating.setText(String.valueOf(glumci.get(position).getRating()));
        notifyDataSetChanged();

        return rowView;
    }

}
