package it.luigi.acquedotto.app.progettotesi.controllo;

import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.location.LocationManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import it.luigi.acquedotto.app.progettotesi.Applicazione;
import it.luigi.acquedotto.app.progettotesi.Costanti;
import it.luigi.acquedotto.app.progettotesi.activity.AttivitaActivity;
import it.luigi.acquedotto.app.progettotesi.modello.Attivita;
import it.luigi.acquedotto.app.progettotesi.modello.Modello;
import it.luigi.acquedotto.app.progettotesi.modello.Utente;
import it.luigi.acquedotto.app.progettotesi.persistenza.DAODatabaseAttivita;
import it.luigi.acquedotto.app.progettotesi.vista.AttivitaFragment;

import static android.R.attr.y;

/**
 * Created by luigi on 10/10/2016.
 */

public class ControlloAttività {
    public static final String TAG = ControlloAttività.class.getName();

    private DialogInterface.OnClickListener azioneAttivaGPS = new AzioneAttivaGPS();
    private Button.OnClickListener azioneFoto = new AzioneFoto();
    private Button.OnClickListener azioneLocalizza = new AzioneLocalizza();
    private Button.OnClickListener azioneSalva = new AzioneSalva();
    private ImageView.OnClickListener azioneApriImmagine = new AzioneApriImmagine();

    public DialogInterface.OnClickListener getAzioneAttivaGPS() {
        return azioneAttivaGPS;
    }

    public Button.OnClickListener getAzioneFoto() {
        return azioneFoto;
    }

    public Button.OnClickListener getAzioneLocalizza() {
        return azioneLocalizza;
    }

    public Button.OnClickListener getAzioneSalva() {
        return azioneSalva;
    }

    public ImageView.OnClickListener getAzioneApriImmagine() {
        return azioneApriImmagine;
    }

    private class AzioneAttivaGPS implements DialogInterface.OnClickListener {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            AttivitaActivity utenteActivity = (AttivitaActivity) Applicazione.getIstance().getCurrentActivity();
            utenteActivity.schermoAttivaGPS();
        }
    }

    private class AzioneFoto implements Button.OnClickListener {
        @Override
        public void onClick(View v) {
            AttivitaActivity utenteActivity = (AttivitaActivity) Applicazione.getIstance().getCurrentActivity();
            utenteActivity.schermoFoto();
        }
    }

    private class AzioneLocalizza implements Button.OnClickListener {
        //Azione associata al bottone di localizzazione. Controlla se il servizio di GPS è attivato
        //nel caso affermativo richiede un singolo aggiornamento al manager, altrimenti richiede se si
        //vuole attivare il GPS
        @Override
        public void onClick(View v) {
            AttivitaActivity utenteActivity = (AttivitaActivity) Applicazione.getIstance().getCurrentActivity();
            AttivitaFragment attivitaFragment = utenteActivity.getAttivitaFragment();
            LocationManager manager = utenteActivity.getManager();
            if (manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                try {
                    manager.requestSingleUpdate(LocationManager.GPS_PROVIDER, utenteActivity, null);
                }catch(SecurityException e){
                    e.printStackTrace();
                }
            }else {
                utenteActivity.finestraAttivaGPS();
            }
        }
    }

    private class AzioneSalva implements Button.OnClickListener{
        //prende tutte le informazioni riguardanti l'attivita corrente, controlla se L'ID unico è
        //uguale a 0, in caso affermativo inserisce un nuovo record nel database delle attivita,
        //altrimenti aggiorna il record precedente
        @Override
        public void onClick(View v) {
            AttivitaActivity utenteActivity = (AttivitaActivity) Applicazione.getIstance().getCurrentActivity();
            AttivitaFragment attivitaFragment = utenteActivity.getAttivitaFragment();
            Modello modello = Applicazione.getIstance().getModello();
            /*Utente utente = (Utente) modello.getBean(Costanti.UTENTE);
            int idUtente = Integer.parseInt(utente.getIdUtente());
            double x = 0.0;
            double y = 0.0;
            double z = 0.0;
            if(!attivitaFragment.getTestoX().getText().equals("") || attivitaFragment.getTestoX().getText() != null){
                x = Double.parseDouble(attivitaFragment.getTestoX().getText().toString());
            }
            if(!attivitaFragment.getTestoY().getText().equals("") || attivitaFragment.getTestoY().getText() != null) {
                y = Double.parseDouble(attivitaFragment.getTestoY().getText().toString());
            }
            if(!attivitaFragment.getTestoX().getText().equals("") || attivitaFragment.getTestoX().getText() != null) {
                z = Double.parseDouble(attivitaFragment.getTestoZ().getText().toString());
            }
            String tipologia = attivitaFragment.getSpinnerTipologia().getSelectedItem().toString();
            String rilevatore = attivitaFragment.getEditRilevatore().getText().toString();
            String note = attivitaFragment.getEditNote().getText().toString();
            String tempo = attivitaFragment.getData();
            String foto = attivitaFragment.getImageFoto().toString();
            String stato = "FALSE";
            //String stato = convalidaCampi(x, y, z, tempo, foto);*/
            Attivita attivita = (Attivita) modello.getBean(Costanti.ATTIVITA);
            String rilevatore;
            String tipologia;
            String note;
            double x = attivita.getxLocation();
            double y = attivita.getyLocation();
            double z = attivita.getzLocation();
            String tempo = attivita.getT();
            String foto = attivita.getIdImmagine();
            int idUtente = attivita.getIdUtente();
            String stato = "False";
            //if(attivita.getIdAttivita()==0){
                rilevatore = attivitaFragment.getEditRilevatore().getText().toString();
                note = attivitaFragment.getEditNote().getText().toString();
                tipologia = attivitaFragment.getSpinnerTipologia().getSelectedItem().toString();
           // }
            try{
                DAODatabaseAttivita dbAttivita = new DAODatabaseAttivita(utenteActivity);
                SQLiteDatabase database = dbAttivita.getWritableDatabase();
                if(attivita.getIdAttivita()==0) {
                    dbAttivita.inserisciAttivita(database, idUtente, x, y, z, tempo, foto, stato, tipologia, rilevatore, note);
                }else{
                    dbAttivita.aggiornaAttivita(database, attivita.getIdAttivita(), idUtente,  x, y, z, tempo, foto, stato, tipologia, rilevatore, note);
                }
                Log.d(TAG, "RECORD INSERITO");
            }catch(Exception e){
                e.printStackTrace();
            }
            utenteActivity.finish();
        }

        /*private String convalidaCampi(double x, double y, double z, String tempo, String rilevatore) {
            if (){
                return "FALSE";
            } if (y.equals("")||y == null){
                return "FALSE";
            } if (z.equals("")||z == null){
                return "FALSE";
            } if (tempo.equals("")||tempo == null){
                return "FALSE";
            } if (foto.equals("")||foto == null){
                return "FALSE";
            }
            return "TRUE";
        }*/
    }

    private class AzioneApriImmagine implements ImageView.OnClickListener{
        //Azione associata al click sull'immagine
        @Override
        public void onClick(View v) {
            AttivitaActivity attivitaActivity = (AttivitaActivity) Applicazione.getIstance().getCurrentActivity();
            attivitaActivity.schermoImmagine();
        }
    }
}
