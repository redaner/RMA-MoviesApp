package ba.unsa.etf.rma.rasimsabanovic.rma_projekat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.FileInputStream;

public class BiografijaAktivnost extends AppCompatActivity {
    public Glumac getGlumac() {
        return glumac;
    }

    public void setGlumac(final Glumac glumac) {
        this.glumac = glumac;
    }

    private Glumac glumac;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_biografija_aktivnost);


        glumac = getIntent().getParcelableExtra("glumac");
        Bitmap b = BitmapFactory.decodeResource(getResources(), getResources().getIdentifier(
                "ba.unsa.etf.rma.rasimsabanovic.rma_projekat:drawable/"+glumac.getSlika()
                ,null,null));



        final ImageView slika = (ImageView) findViewById(R.id.imageView3);
        final TextView ime = (TextView) findViewById(R.id.textIme);
        final TextView god_rod = (TextView) findViewById(R.id.textViewRodjenje);
        final TextView god_smrt = (TextView) findViewById(R.id.textViewSmrt);
        final TextView mjesto = (TextView) findViewById(R.id.textViewMjesto);
        final TextView spol = (TextView) findViewById(R.id.textViewSpol);
        final TextView imdb = (TextView) findViewById(R.id.textViewImdb);
        final TextView bio = (TextView) findViewById(R.id.textViewBiografija);
        final Button dugme = (Button) findViewById(R.id.button);

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
                if (sendIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(Intent.createChooser(sendIntent, getResources().getText(R.string.app_name)));
                }
            }
        });

        slika.setImageBitmap(b);
        //ime.setText(glumac.getIme() + " " + glumac.getPrezime());
        god_rod.setText("Year of birth: " + Integer.toString(glumac.getGodina_rodjenja()));
        if (glumac.getGodina_smrti() != -1) {
            god_smrt.setVisibility(View.VISIBLE);
            god_smrt.setText("   Year of death: " + Integer.toString(glumac.getGodina_smrti()));
        }
        mjesto.setText(glumac.getMjesto_rodjenja());
        if (glumac.getSpol().equals("M")) {
            spol.setText("Gender: Male");
            LinearLayout rl = (LinearLayout) findViewById(R.id.lejaut);
            rl.setBackgroundColor(getResources().getColor(R.color.darkblue));
        } else if (glumac.getSpol().equals("F")) {
            spol.setText("Gender: Female");
            LinearLayout rl = (LinearLayout) findViewById(R.id.lejaut);
            rl.setBackgroundColor(getResources().getColor(R.color.purple));
        }
        else
            spol.setText("Gender: Other");

        imdb.setText("IMDB: " + glumac.getImdb());
        bio.setText("Biography: \n" + glumac.getBiografija());
        bio.setMovementMethod(new ScrollingMovementMethod());


    }
}
