package it.luigi.acquedotto.app.progettotesi.persistenza;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

import it.luigi.acquedotto.app.progettotesi.modello.Attivita;

/**
 * Created by luigi on 11/10/2016.
 */

public class DAODatabaseAttivita extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Attivita.db";
    public static final int SCHEMA_VERSION = 1;

    public DAODatabaseAttivita(Context context){
        super(context, DATABASE_NAME, null, SCHEMA_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TabellaAttivita.TABLE_NAME + "(" + TabellaAttivita._ID + " integer primary key autoincrement, " + TabellaAttivita.ID_UTENTE + " integer not null," + TabellaAttivita.X + " real," + TabellaAttivita.Y + " real," + TabellaAttivita.Z + " real,"+ TabellaAttivita.TEMPO + " date,"+ TabellaAttivita.FOTO + " text,"+ TabellaAttivita.TIPOLOHIA + " text,"+ TabellaAttivita.RILEVATORE + " text,"+ TabellaAttivita.NOTE + " text,"+ TabellaAttivita.STATO + " integer)");
    }

    public void inserisciAttivita(SQLiteDatabase db, int idUtente, double x, double y, double z, String tempo, String foto, String stato, String tipologia, String rilevatore, String note){
        ContentValues values = new ContentValues();
        values.put(TabellaAttivita.ID_UTENTE, idUtente);
        values.put(TabellaAttivita.X, x);
        values.put(TabellaAttivita.Y, y);
        values.put(TabellaAttivita.Z, z);
        values.put(TabellaAttivita.TEMPO, tempo);
        values.put(TabellaAttivita.FOTO, foto);
        values.put(TabellaAttivita.STATO, stato);
        values.put(TabellaAttivita.TIPOLOHIA, tipologia);
        values.put(TabellaAttivita.RILEVATORE, rilevatore);
        values.put(TabellaAttivita.NOTE, note);
        db.insert(TabellaAttivita.TABLE_NAME, null, values);
    }

    public boolean aggiornaAttivita(SQLiteDatabase db, int idAttivita, int idUtente, double x, double y, double z, String tempo, String foto, String stato, String tipologia, String rilevatore, String note){
        ContentValues values = new ContentValues();
        values.put(TabellaAttivita.ID_UTENTE, idUtente);
        values.put(TabellaAttivita.X, x);
        values.put(TabellaAttivita.Y, y);
        values.put(TabellaAttivita.Z, z);
        values.put(TabellaAttivita.TEMPO, tempo);
        values.put(TabellaAttivita.FOTO, foto);
        values.put(TabellaAttivita.STATO, stato);
        values.put(TabellaAttivita.TIPOLOHIA, tipologia);
        values.put(TabellaAttivita.RILEVATORE, rilevatore);
        values.put(TabellaAttivita.NOTE, note);
        return db.update(TabellaAttivita.TABLE_NAME, values, TabellaAttivita._ID + "=" + idAttivita, null) >0;
    }

    public SQLiteDatabase open()throws DAOException{
        SQLiteDatabase database = this.getWritableDatabase();
        return database;
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public List<Attivita> getListaAttivita(String utente){
        List<Attivita> list = new ArrayList<Attivita>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query(TabellaAttivita.TABLE_NAME, TabellaAttivita.COLUMNS, TabellaAttivita.ID_UTENTE + " = '" + utente + "'", null, null, null, TabellaAttivita.TEMPO );
        c.move(0);
        try{
            while (c.moveToNext()){
                int idAttivita = c.getInt(c.getColumnIndex(TabellaAttivita._ID));
                int idUtente = c.getInt(c.getColumnIndex(TabellaAttivita.ID_UTENTE));
                double xLoc = c.getDouble(c.getColumnIndex(TabellaAttivita.X));
                double yLoc = c.getDouble(c.getColumnIndex(TabellaAttivita.Y));
                double zLoc = c.getDouble(c.getColumnIndex(TabellaAttivita.Z));
                String tempo = c.getString(c.getColumnIndex(TabellaAttivita.TEMPO));
                String foto = c.getString(c.getColumnIndex(TabellaAttivita.FOTO));
                String tipologia = c.getString(c.getColumnIndex(TabellaAttivita.TIPOLOHIA));
                String rilevatore = c.getString(c.getColumnIndex(TabellaAttivita.RILEVATORE));
                String note = c.getString(c.getColumnIndex(TabellaAttivita.NOTE));
                int intStato = c.getInt(c.getColumnIndex(TabellaAttivita.STATO));
                Attivita attivita = new Attivita(idAttivita, idUtente, tempo, xLoc, yLoc, zLoc, foto, rilevatore, note, tipologia);
                list.add(attivita);
            }
        }finally{
            c.close();
        }
        return list;
    }

    public List<Attivita> getAttivitaUtente(String utente){
        List<Attivita> list = new ArrayList<Attivita>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query(TabellaAttivita.TABLE_NAME, TabellaAttivita.COLUMNS, TabellaAttivita.ID_UTENTE + " = '" + utente + "'", null, null, null, TabellaAttivita.TEMPO );
        c.move(0);
        while (c.moveToNext()){
            int idAttivita = c.getInt(c.getColumnIndex(TabellaAttivita._ID));
            int idUtente = c.getInt(c.getColumnIndex(TabellaAttivita.ID_UTENTE));
            double xLoc = c.getDouble(c.getColumnIndex(TabellaAttivita.X));
            double yLoc = c.getDouble(c.getColumnIndex(TabellaAttivita.Y));
            double zLoc = c.getDouble(c.getColumnIndex(TabellaAttivita.Z));
            String tempo = c.getString(c.getColumnIndex(TabellaAttivita.TEMPO));
            String foto = c.getString(c.getColumnIndex(TabellaAttivita.FOTO));
            String tipologia = c.getString(c.getColumnIndex(TabellaAttivita.TIPOLOHIA));
            String rilevatore = c.getString(c.getColumnIndex(TabellaAttivita.RILEVATORE));
            String note = c.getString(c.getColumnIndex(TabellaAttivita.NOTE));
            int intStato = c.getInt(c.getColumnIndex(TabellaAttivita.STATO));
            Attivita attivita = new Attivita(idAttivita, idUtente, tempo, xLoc, yLoc, zLoc, foto, rilevatore, note, tipologia);
            list.add(attivita);
        }
        return list;
    }
}
