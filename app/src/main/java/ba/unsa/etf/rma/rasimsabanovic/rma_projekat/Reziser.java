package ba.unsa.etf.rma.rasimsabanovic.rma_projekat;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Rasim on 3/24/2017.
 */

public class Reziser implements Parcelable {
    public Reziser(final String ime, final String prezime) {
        this.ime = ime;
        this.prezime = prezime;
    }

    @Override
    public int describeContents() {
        return 0;
    }


    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(ime);
        out.writeString(prezime);
    }

    public static final Parcelable.Creator<Reziser> CREATOR = new Parcelable.Creator<Reziser>() {
        public Reziser createFromParcel(Parcel ins) {
            return new Reziser(ins);
        }

        public Reziser[] newArray(int size) {
            return new Reziser[size];
        }
    };

    private Reziser(Parcel in) {
        ime = in.readString();
        prezime = in.readString();
    }

    private String ime;

    public String getIme() {
        return ime;
    }

    public void setIme(final String ime) {
        this.ime = ime;
    }

    private String prezime;

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(final String prezime) {
        this.prezime = prezime;
    }
}
