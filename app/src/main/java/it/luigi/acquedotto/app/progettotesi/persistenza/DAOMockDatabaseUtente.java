package it.luigi.acquedotto.app.progettotesi.persistenza;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.MessageFormat;

import it.luigi.acquedotto.app.progettotesi.modello.Utente;

/**
 * Created by luigi on 06/10/2016.
 */

public class DAOMockDatabaseUtente extends SQLiteOpenHelper {

    private final String TAG = DAOMockDatabaseUtente.class.getName();

    public static final String DATABASE_NAME = "ProgettoTesi.db";
    public static final int SCHEMA_VERSION = 2;

    public DAOMockDatabaseUtente(Context context){
        super(context, DATABASE_NAME, null, SCHEMA_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //String sql = "CREATE TABLE {0} ({1} INTEGER PRIMARY KEY AUTOINCREMENT, {2} TEXT NOT NULL, {3} TEXT NOT NULL, {4} TEXT NOT NULL);";
        //db.execSQL(MessageFormat.format(sql,TabellaUtenti.TABLE_NAME, TabellaUtenti._ID, TabellaUtenti.USERNAME, TabellaUtenti.PASSWORD, TabellaUtenti.NOME_UTENTE));
        db.execSQL("CREATE TABLE " + TabellaUtenti.TABLE_NAME + "(" + TabellaUtenti._ID + " integer primary key autoincrement, " + TabellaUtenti.USERNAME + " text not null," + TabellaUtenti.PASSWORD + " text not null," + TabellaUtenti.NOME_UTENTE + " text not null)");
        inserisciUtente(db, "mario.rossi", "0000", "Rossi Mario");
        inserisciUtente(db, "antonio.banchi", "1111", "Bianchi Antonio");
        inserisciUtente(db, "luca.verdi", "1234", "Verdi Luca");
    }

    private void inserisciUtente(SQLiteDatabase db, String username, String password, String nomeUtente) {
        ContentValues values = new ContentValues();
        values.put(TabellaUtenti.USERNAME, username);
        values.put(TabellaUtenti.PASSWORD, password);
        values.put(TabellaUtenti.NOME_UTENTE, nomeUtente);
        db.insert(TabellaUtenti.TABLE_NAME, null, values);
    }

    private Cursor selectUtente(String username, String password){
        Log.d(TAG, "entrato in selectUtente");
        SQLiteDatabase database = getReadableDatabase();
        Log.d(TAG, "creato database " + database.toString());
        return database.query(TabellaUtenti.TABLE_NAME, new String[]{TabellaUtenti._ID, TabellaUtenti.NOME_UTENTE, TabellaUtenti.PASSWORD, TabellaUtenti.USERNAME}, TabellaUtenti.USERNAME + " = '" + username + "' AND " + TabellaUtenti.PASSWORD + " = '" + password + "'", null, null, null, TabellaUtenti._ID);
    }

    private Cursor selectTutto(){
        Log.d(TAG, "entrato in selectUtente");
        SQLiteDatabase database = getReadableDatabase();
        Log.d(TAG, "creato database " + database.toString());
        return database.query(TabellaUtenti.TABLE_NAME, new String[]{TabellaUtenti._ID, TabellaUtenti.NOME_UTENTE, TabellaUtenti.PASSWORD, TabellaUtenti.USERNAME}, null, null, null, null, TabellaUtenti._ID);
    }
    public void stampaTutto(){
        Cursor c =  selectTutto();
        c.move(0);
        try {
            while(c.moveToNext()){
                String idUtente = c.getString(c.getColumnIndex(TabellaUtenti._ID));
                Log.d(TAG, "Id utente: " + idUtente);
                String username = c.getString(c.getColumnIndex(TabellaUtenti.USERNAME));
                Log.d(TAG, "Username: " + username);
                String password = c.getString(c.getColumnIndex(TabellaUtenti.PASSWORD));
                Log.d(TAG, "PassWord: " + password);
                String nomeUtente = c.getString(c.getColumnIndex(TabellaUtenti.NOME_UTENTE));
                Log.d(TAG, "Nome Utente: " + nomeUtente);
            }
        }finally{
            c.close();
        }
    }

    public Utente getUtente(String user, String pass){
        Utente utente = null;
        Cursor c = selectUtente(user, pass);
        try {
            if (c.getCount()==0) {
                return utente;
            }
            c.moveToFirst();
            String idUtente = c.getString(c.getColumnIndex(TabellaUtenti._ID));
            String username = c.getString(c.getColumnIndex(TabellaUtenti.USERNAME));
            String password = c.getString(c.getColumnIndex(TabellaUtenti.PASSWORD));
            String nomeUtente = c.getString(c.getColumnIndex(TabellaUtenti.NOME_UTENTE));
            utente = new Utente(nomeUtente, idUtente, username, password);
        }finally{
            c.close();
        }
        Log.d(TAG, "creato utente");
        return utente;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
