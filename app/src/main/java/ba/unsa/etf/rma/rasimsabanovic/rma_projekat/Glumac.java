package ba.unsa.etf.rma.rasimsabanovic.rma_projekat;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;

/**
 * Created by Rasim on 3/24/2017.
 */


public class Glumac implements Parcelable{

    public Glumac(final String ime, final String prezime, final int godina_rodjenja, final int godina_smrti, final String biografija, final String slika, final String mjesto_rodjenja, final String spol, final String imdb) {
        this.ime = ime;
        this.prezime = prezime;
        this.godina_rodjenja = godina_rodjenja;
        this.godina_smrti = godina_smrti;
        this.biografija = biografija;
        this.slika = slika;
        this.mjesto_rodjenja = mjesto_rodjenja;
        this.spol = spol;
        this.imdb = imdb;
    }

    @Override
    public int describeContents() {
        return 0;
    }


    @Override
    public void writeToParcel(Parcel out, int flags) {

        out.writeString(ime);
        out.writeString(prezime);
        out.writeInt(godina_rodjenja);
        out.writeInt(godina_smrti);
        out.writeString(biografija);
        out.writeString(slika);
        out.writeString(mjesto_rodjenja);
        out.writeString(spol);
        out.writeString(imdb);
        out.writeString(biografija);

    }

    public static final Parcelable.Creator<Glumac> CREATOR = new Parcelable.Creator<Glumac>() {
        public Glumac createFromParcel(Parcel ins) {
            return new Glumac(ins);
        }

        public Glumac[] newArray(int size) {
            return new Glumac[size];
        }
    };

    private Glumac(Parcel in) {
        ime = in.readString();
        prezime = in.readString();
        godina_rodjenja = in.readInt();
        godina_smrti = in.readInt();
        biografija = in.readString();
        slika = in.readString();
        mjesto_rodjenja = in.readString();
        spol = in.readString();
        imdb = in.readString();
        biografija = in.readString();
    }

    private String ime;

    public String getIme() {
        return ime;
    }

    public void setIme(final String ime) {
        this.ime = ime;
    }

    private  String prezime;

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(final String prezime) {
        this.prezime = prezime;
    }

    public int getGodina_rodjenja() {
        return godina_rodjenja;
    }

    public void setGodina_rodjenja(final int godina_rodjenja) {
        this.godina_rodjenja = godina_rodjenja;
    }

    private  int godina_rodjenja;

    private int godina_smrti;

    public int getGodina_smrti() {
        return godina_smrti;
    }

    public void setGodina_smrti(final int godina_smrti) {
        this.godina_smrti = godina_smrti;
    }

    private String biografija;

    public  String getBiografija() {
        return biografija;
    }

    public void setBiografija(final String biografija) {
        this.biografija = biografija;
    }

    private String slika;

    public String getSlika() {
        return slika;
    }

    public void setSlika(final String slika) {
        this.slika = slika;
    }

    private String mjesto_rodjenja;

    public String getMjesto_rodjenja() {
        return mjesto_rodjenja;
    }

    public void setMjesto_rodjenja(final String mjesto_rodjenja) {
        this.mjesto_rodjenja = mjesto_rodjenja;
    }

    private String spol;

    public String getSpol() {
        return spol;
    }

    public void setSpol(final String spol) {
        this.spol = spol;
    }

    private  String imdb;

    public String getImdb() {
        return imdb;
    }

    public void setImdb(final String imdb) {
        this.imdb = imdb;
    }
}
