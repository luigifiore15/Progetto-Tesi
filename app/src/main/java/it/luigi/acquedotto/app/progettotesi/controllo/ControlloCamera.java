package it.luigi.acquedotto.app.progettotesi.controllo;

import android.view.View;
import android.widget.ImageButton;

import it.luigi.acquedotto.app.progettotesi.Applicazione;
import it.luigi.acquedotto.app.progettotesi.activity.CameraActivity;

/**
 * Created by luigi on 20/10/2016.
 */

public class ControlloCamera {
    private ImageButton.OnClickListener azioneScattaFoto = new AzioneScattaFoto();
    private ImageButton.OnClickListener azioneEsci = new AcioneEsci();

    public ImageButton.OnClickListener getAzioneScattaFoto() {
        return azioneScattaFoto;
    }

    public ImageButton.OnClickListener getAzioneEsci() {
        return azioneEsci;
    }

    private class AzioneScattaFoto implements ImageButton.OnClickListener{
        //azione associata al pulsante Shutter, permette di catturare un immagine dalla fotocamera
        @Override
        public void onClick(View v) {
            CameraActivity cameraActivity = (CameraActivity) Applicazione.getIstance().getCurrentActivity();
            cameraActivity.scattaFoto();
        }
    }

    private class AcioneEsci implements ImageButton.OnClickListener{
        //Azione associata al pulsante X, permette la chiusura della fotocamera
        @Override
        public void onClick(View v) {
            CameraActivity cameraActivity = (CameraActivity) Applicazione.getIstance().getCurrentActivity();
            cameraActivity.finish();
        }
    }
}