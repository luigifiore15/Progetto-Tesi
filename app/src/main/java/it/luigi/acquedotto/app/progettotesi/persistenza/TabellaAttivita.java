package it.luigi.acquedotto.app.progettotesi.persistenza;

import android.provider.BaseColumns;

/**
 * Created by luigi on 11/10/2016.
 */

public interface TabellaAttivita extends BaseColumns {
    String TABLE_NAME = "attivita";
    String ID_UTENTE = "idUtente";
    String X = "x";
    String Y = "Y";
    String Z = "Z";
    String TEMPO = "tempo";
    String FOTO = "foto";
    String STATO = "stato";
    String TIPOLOHIA = "tipolohia";
    String RILEVATORE = "rilevatore";
    String NOTE = "note";

    String[] COLUMNS = new String[]{
            _ID, ID_UTENTE, X, Y, Z, TEMPO, FOTO, STATO, TIPOLOHIA, RILEVATORE, NOTE
    };
}
