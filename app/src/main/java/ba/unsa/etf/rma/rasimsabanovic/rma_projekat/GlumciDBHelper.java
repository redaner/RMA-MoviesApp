package ba.unsa.etf.rma.rasimsabanovic.rma_projekat;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Created by Rasim on 10.06.2017..
 */

public class GlumciDBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "GlumciBaza.db";
    public static final String DATABASE_GLUMCI = "Glumci";
    public static final String DATABASE_REZISERI = "Reziseri";
    public static final String DATABASE_ZANROVI = "Zanrovi";
    public static final String DATABASE_REZISERI_VEZE = "GlumciReziseri";
    public static final String DATABASE_ZANROVI_VEZE = "GlumciZanrovi";
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_ID = "_id";
    public static final String DATABASE_TMDB_ID = "tmdb_id";
    public static final String DATABASE_IME = "ime";
    public static final String DATABASE_GODINA_RODJENJA = "godina_rodjenja";
    public static final String DATABASE_GODINA_SMRTI = "godina_smrti";
    public static final String DATABASE_BIOGRAFIJA = "biografija";
    public static final String DATABASE_SLIKA = "slika";
    public static final String DATABASE_RATING = "rating";
    public static final String DATABASE_MJESTO_RODJENJA = "mjesto_rodjenja";
    public static final String DATABASE_SPOL = "spol";
    public static final String DATABASE_IMDB = "imdb";

    private static final String GLUMCI_CREATE = "create table " +
            DATABASE_GLUMCI + "(" + DATABASE_ID +
            " integer primary key autoincrement, " +
            DATABASE_TMDB_ID + " integer not null, " +
            DATABASE_IME + " text not null, " +
            DATABASE_GODINA_RODJENJA + " integer not null, " +
            DATABASE_GODINA_SMRTI + " integer not null, " +
            DATABASE_BIOGRAFIJA  + " text not null, " +
            DATABASE_SLIKA + " text not null, " +
            DATABASE_RATING + " real not null, " +
            DATABASE_MJESTO_RODJENJA + " text not null, " +
            DATABASE_SPOL + " text not null, " +
            DATABASE_IMDB + " text not null);";

    private static final String REZISERI_CREATE = "create table " +
            DATABASE_REZISERI + "(" + DATABASE_ID +
            " integer primary key autoincrement, " +
            DATABASE_IME + " text not null);";

    private static final String ZANROVI_CREATE = "create table " +
            DATABASE_ZANROVI + "(" + DATABASE_ID +
            " integer primary key autoincrement, " +
            DATABASE_IME + " text not null, " +
            DATABASE_SLIKA + " text not null);";

    private static final String GLUMCI_REZISERI_CREATE = "create table " +
            DATABASE_REZISERI_VEZE + "(" + DATABASE_ID +
            " integer primary key autoincrement, " +
            "glumac_id" + " integer not null, " +
            "reziser_id" + " integer not null);";

    private static final String GLUMCI_ZANROVI_CREATE = "create table " +
            DATABASE_ZANROVI_VEZE + "(" + DATABASE_ID +
            " integer primary key autoincrement, " +
            "glumac_id" + " integer not null, " +
            "zanr_id" + " integer not null);";


    public GlumciDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(GLUMCI_CREATE);
        db.execSQL(REZISERI_CREATE);
        db.execSQL(ZANROVI_CREATE);
        db.execSQL(GLUMCI_ZANROVI_CREATE);
        db.execSQL(GLUMCI_REZISERI_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS" + DATABASE_GLUMCI);
        db.execSQL("DROP TABLE IF EXISTS" + DATABASE_REZISERI);
        db.execSQL("DROP TABLE IF EXISTS" + DATABASE_ZANROVI);
        db.execSQL("DROP TABLE IF EXISTS" + DATABASE_REZISERI_VEZE);
        db.execSQL("DROP TABLE IF EXISTS" + DATABASE_ZANROVI_VEZE);
        onCreate(db);
    }
}
