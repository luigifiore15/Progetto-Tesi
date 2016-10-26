package it.luigi.acquedotto.app.progettotesi.controllo;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import it.luigi.acquedotto.app.progettotesi.Applicazione;
import it.luigi.acquedotto.app.progettotesi.Costanti;
import it.luigi.acquedotto.app.progettotesi.activity.UtenteActivity;
import it.luigi.acquedotto.app.progettotesi.modello.Attivita;
import it.luigi.acquedotto.app.progettotesi.modello.Modello;
import it.luigi.acquedotto.app.progettotesi.modello.Utente;
import it.luigi.acquedotto.app.progettotesi.persistenza.DAODatabaseAttivita;

/**
 * Created by luigi on 17/10/2016.
 */

public class ControlloUtente {
    private AdapterView.OnItemClickListener azioneVisualizzaAttivita = new AzioneVisualizzaAttivita();
    private Button.OnClickListener azioneAggiungi = new AzioneAggiungi();

    public Button.OnClickListener getAzioneAggiungi() {
        return azioneAggiungi;
    }

    public ListView.OnItemClickListener getAzioneVisualizzaAttivita() {
        return azioneVisualizzaAttivita;
    }

    private class AzioneAggiungi implements Button.OnClickListener{
        //azione associata al click del bottone AGGIUNGI ATTIVITA
        @Override
        public void onClick(View v) {
            Modello modello = Applicazione.getIstance().getModello();
            modello.saveBean(Costanti.ATTIVITA, null);
            UtenteActivity utenteActivity = (UtenteActivity) Applicazione.getIstance().getCurrentActivity();
            utenteActivity.schermoAttivita();
        }
    }

    private class AzioneVisualizzaAttivita implements AdapterView.OnItemClickListener {
        private final String TAG = AzioneVisualizzaAttivita.class.getName();

        //Azione associata al click su una voce della lista.
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Modello modello = Applicazione.getIstance().getModello();
            Utente utente = (Utente) modello.getBean(Costanti.UTENTE);
            DAODatabaseAttivita databaseAttivita = new DAODatabaseAttivita(Applicazione.getIstance().getApplicationContext());
            Attivita attivita = databaseAttivita.getListaAttivita(utente.getIdUtente()).get(position);
            modello.saveBean(Costanti.ATTIVITA, attivita);
            UtenteActivity utenteActivity = (UtenteActivity) Applicazione.getIstance().getCurrentActivity();
            utenteActivity.schermoAttivita();
            databaseAttivita.close();
            Log.d(TAG, "Chiuso Database Attivita");
        }
    }
}
