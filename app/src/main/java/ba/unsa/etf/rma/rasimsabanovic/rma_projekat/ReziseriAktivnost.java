package ba.unsa.etf.rma.rasimsabanovic.rma_projekat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class ReziseriAktivnost extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reziseri_aktivnost);
        Intent i = getIntent();
        final Button dugme_glumci = (Button)findViewById(R.id.buttonGlumci);
        final Button dugme_reziseri = (Button)findViewById(R.id.buttonReziseri);
        final Button dugme_zanrovi = (Button)findViewById(R.id.buttonZanrovi);
        final ArrayList<Reziser>  reziseri = i.getParcelableArrayListExtra("reziseri");
        final ArrayList<Glumac> glumci = i.getParcelableArrayListExtra("glumci");
        final ArrayList<Zanr> zanrovi = i.getParcelableArrayListExtra("zanrovi");
        final ListView lista_rezisera = (ListView)findViewById(R.id.listaReziseri);

        final ReziserAdapter adapter;
        adapter = new ReziserAdapter(this, R.layout.reziser_u_listi, reziseri, getResources());
        lista_rezisera.setAdapter(adapter);

        dugme_glumci.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ReziseriAktivnost.this, GlumciAktivnost.class);
                i.putExtra("glumci", glumci);
                i.putExtra("reziseri", reziseri);
                i.putExtra("zanrovi", zanrovi);
                ReziseriAktivnost.this.startActivity(i);
            }
        });

        dugme_zanrovi.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ReziseriAktivnost.this, ZanrAktivnost.class);
                i.putExtra("glumci", glumci);
                i.putExtra("reziseri", reziseri);
                i.putExtra("zanrovi", zanrovi);
                ReziseriAktivnost.this.startActivity(i);
            }
        });
    }
}
