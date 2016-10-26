package it.luigi.acquedotto.app.progettotesi.persistenza;

import android.provider.BaseColumns;

/**
 * Created by luigi on 07/10/2016.
 */

public interface TabellaUtenti extends BaseColumns {
    String TABLE_NAME = "utenti";
    String USERNAME = "username";
    String PASSWORD = "password";
    String NOME_UTENTE = "nomeUtente";

    String[] COLUMNS = new String[]{
            _ID, USERNAME, PASSWORD, NOME_UTENTE
    };

}
