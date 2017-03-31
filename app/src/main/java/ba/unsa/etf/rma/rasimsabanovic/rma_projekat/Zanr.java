package ba.unsa.etf.rma.rasimsabanovic.rma_projekat;

import android.graphics.Bitmap;
import android.media.Image;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Rasim on 3/24/2017.
 */

public class Zanr implements Parcelable {
    public Zanr(final String ime, final String slika) {
        this.ime = ime;
        this.slika = slika;
    }

    @Override
    public int describeContents() {
        return 0;
    }


    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(ime);
        out.writeString(slika);
    }

    public static final Parcelable.Creator<Zanr> CREATOR = new Parcelable.Creator<Zanr>() {
        public Zanr createFromParcel(Parcel ins) {
            return new Zanr(ins);
        }

        public Zanr[] newArray(int size) {
            return new Zanr[size];
        }
    };

    private Zanr(Parcel in) {
        ime = in.readString();
        slika = in.readString();
    }

    private String ime;

    public String getIme() {
        return ime;
    }

    public void setIme(final String ime) {
        this.ime = ime;
    }

    private String slika;

    public String getSlika() {
        return slika;
    }

    public void setSlika(final String slika) {
        this.slika = slika;
    }
}
