package it.luigi.acquedotto.app.progettotesi.controllo;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.File;
import java.net.URI;

import it.luigi.acquedotto.app.progettotesi.Applicazione;
import it.luigi.acquedotto.app.progettotesi.Costanti;
import it.luigi.acquedotto.app.progettotesi.activity.ImmagineActivity;
import it.luigi.acquedotto.app.progettotesi.modello.Attivita;
import it.luigi.acquedotto.app.progettotesi.modello.Modello;

/**
 * Created by luigi on 25/10/2016.
 */

public class ControlloImmagine {
    private ImageButton.OnClickListener azioneElimina = new AzioneElimina();

    public ImageButton.OnClickListener getAzioneElimina() {
        return azioneElimina;
    }

    private class AzioneElimina implements ImageButton.OnClickListener{
        //Azione associata al pulsante del CESTINO, permette l'eliminazione del file dal dispositivo
        //e la conseguente disassociazione dell'activity dalla stessa

        private final String TAG = AzioneElimina.class.getName();

        @Override
        public void onClick(View v) {
            ImmagineActivity immagineActivity = (ImmagineActivity) Applicazione.getIstance().getCurrentActivity();
            Modello modello = Applicazione.getIstance().getModello();
            Attivita attivita = (Attivita) modello.getBean(Costanti.ATTIVITA);
            File file = new File(URI.create(attivita.getIdImmagine()));
            if(file.exists()){
                if(file.delete()){
                    Toast.makeText(immagineActivity, "Immagine Eliminata", Toast.LENGTH_LONG);
                    Log.d(TAG, "Immagine eliminata");
                    Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                    intent.setData(Uri.parse(attivita.getIdImmagine()));
                    immagineActivity.sendBroadcast(intent);
                    attivita.setIdImmagine(null);
                    modello.saveBean(Costanti.ATTIVITA, attivita);
                    immagineActivity.finish();
                }else{
                    Log.d(TAG, "Immagine non eliminata");
                }
            }else{
                Log.d(TAG, "file non esiste");
            }
        }
    }
}
