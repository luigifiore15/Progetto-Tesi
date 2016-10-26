package it.luigi.acquedotto.app.progettotesi.controllo;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import it.luigi.acquedotto.app.progettotesi.Applicazione;
import it.luigi.acquedotto.app.progettotesi.Costanti;
import it.luigi.acquedotto.app.progettotesi.activity.LoginActivity;
import it.luigi.acquedotto.app.progettotesi.modello.Modello;
import it.luigi.acquedotto.app.progettotesi.modello.Utente;
import it.luigi.acquedotto.app.progettotesi.persistenza.DAODatabaseAttivita;
import it.luigi.acquedotto.app.progettotesi.persistenza.DAOMockDatabaseUtente;
import it.luigi.acquedotto.app.progettotesi.vista.LoginFragment;

/**
 * Created by luigi on 07/10/2016.
 */

public class ControlloLogin {
    private final String TAG = ControlloLogin.class.getName();

    private final Button.OnClickListener azioneLogin = new AzioneLogin();

    public Button.OnClickListener getAzioneLogin() {
        return azioneLogin;
    }

    private class AzioneLogin implements Button.OnClickListener{
        @Override
        public void onClick(View v) {
            Log.d(TAG, "entro in controlloMenu");
            LoginActivity loginActivity = (LoginActivity) Applicazione.getIstance().getCurrentActivity();
            Log.d(TAG, "inizializzata loginactivity");
            LoginFragment loginFragment = loginActivity.getLoginFragment();
            Modello modello = Applicazione.getIstance().getModello();
            //Prelevo i campi Username e Password inseriti dall'utente
            String username = loginFragment.getUsername();
            String password = loginFragment.getPassword();
            //creo/apro un database degli utenti
            DAOMockDatabaseUtente dbUtenti = new DAOMockDatabaseUtente(loginActivity);
            //dbUtenti.stampaTutto();
            //Chiedo la restituzione di un utente con l'username e la password inseriti
            Utente utente = dbUtenti.getUtente(username, password);
            if(utente == null){
                //l'utente non è stato trovato, quindi segnalo un errore nel login
                Toast.makeText(loginActivity, "Login Errato", Toast.LENGTH_LONG).show();
            }else{
                //utente trovato, avvio l'attività per l'utente selezionato
                //DAODatabaseAttivita dbAttivita = new DAODatabaseAttivita(loginActivity);
                //utente.setListaAttivita(dbAttivita.getListaAttivita(utente.getIdUtente()));
                modello.saveBean(Costanti.UTENTE, utente);
                loginActivity.schermoLoggato();
            }
            //chiudo il database degli utenti
            dbUtenti.close();
            Log.d(TAG, "Database Utenti chiuso");
        }
    }
}
