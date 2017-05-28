package ba.unsa.etf.rma.rasimsabanovic.rma_projekat;

import android.app.Application;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Rasim on 28.05.2017..
 */

public class SaveState extends Application {

    private ArrayList<Glumac> glumci = new ArrayList<>();

    private ArrayList<Zanr> zanrovi = new ArrayList<>();

    private ArrayList<Reziser> reziseri = new ArrayList<>();

    public ArrayList<Zanr> getZanrovi() {
        return zanrovi;
    }

    public void setZanrovi(ArrayList<Zanr> zanrovi) {
        this.zanrovi = zanrovi;
    }

    public ArrayList<Reziser> getReziseri() {
        return reziseri;
    }

    public void setReziseri(ArrayList<Reziser> reziseri) {
        this.reziseri = reziseri;
    }

    public ArrayList<Glumac> getGlumci() {
        return glumci;
    }

    public void setGlumci(ArrayList<Glumac> glumci) {
        this.glumci = glumci;
    }
}
