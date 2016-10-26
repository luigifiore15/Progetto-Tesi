package it.luigi.acquedotto.app.progettotesi.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.text.SimpleDateFormat;

import it.luigi.acquedotto.app.progettotesi.Applicazione;
import it.luigi.acquedotto.app.progettotesi.Costanti;
import it.luigi.acquedotto.app.progettotesi.R;
import it.luigi.acquedotto.app.progettotesi.modello.Attivita;
import it.luigi.acquedotto.app.progettotesi.modello.Modello;
import it.luigi.acquedotto.app.progettotesi.vista.AttivitaFragment;

public class AttivitaActivity extends AppCompatActivity implements LocationListener{

    private LocationManager manager;
    private static final String TAG = AttivitaActivity.class.getName();
    private Uri uriImage;

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onLocationChanged(Location location) {
        //Avviato al collegamento con il satellite GPS dopo la chiamata al requestsingleUpdate del manager
        AttivitaFragment attivitaFragment = this.getAttivitaFragment();
        Log.d(TAG, attivitaFragment.toString());
        double lat = location.getLatitude();
        double lon = location.getLongitude();
        double alt = location.getAltitude();
        long tempo = location.getTime();
        attivitaFragment.getTestoX().setText(Double.toString(lon));
        attivitaFragment.getTestoY().setText(Double.toString(lat));
        attivitaFragment.getTestoZ().setText(Double.toString(alt));
        attivitaFragment.setData(tempo);
        Modello modello = Applicazione.getIstance().getModello();
        Attivita attivita = (Attivita) modello.getBean(Costanti.ATTIVITA);
        attivita.setxLocation(lon);
        attivita.setyLocation(lat);
        attivita.setzLocation(alt);
        attivita.setT(new SimpleDateFormat("dd/MM/yyyy HH.mm.ss").format(tempo));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attivita);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //controllo se il GPS del dispositivo è acceso/abilitato
        manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if(!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            finestraAttivaGPS();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    public AttivitaFragment getAttivitaFragment(){
        return (AttivitaFragment) this.getSupportFragmentManager().findFragmentById(R.id.fragmentAttivita);
    }

    //////////SCHERMI//////
    /*public void finestraErrore(String messaggio){
        mostraMessaggio(messaggio, getString(R.string.stringaErrore));
    }*/

    public void finestraAttivaGPS() {
        //Mostra una finestra di dialogo con un messaggi e 2 pulsanti(SI/NO)
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("GPS non attivo");
        builder.setMessage("Attivare il GPS");
        builder.setPositiveButton(getString(R.string.stringaSi), Applicazione.getIstance().getControlloAttività().getAzioneAttivaGPS());
        builder.setNegativeButton(getString(R.string.stringaNo), null);
        AlertDialog dialog = builder.create();
    dialog.show();
}

    public void schermoAttivaGPS(){
        //Avvia un intent implicito che visualizza le impostazioni di attivazione del Servizio di Localizzazione
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivity(intent);
    }
    public void schermoFoto(){
        //Avvia un intent esplicito per la fotocamera
        Intent intent = new Intent(getApplicationContext(), CameraActivity.class);
        startActivity(intent);
    }

    public void schermoImmagine(){
        //avvia un intent esplicito per la visualizzazione dell'immagine
        Intent intent = new Intent(Applicazione.getIstance().getApplicationContext(), ImmagineActivity.class);
        startActivity(intent);
    }

    public LocationManager getManager() {
        return manager;
    }

}
