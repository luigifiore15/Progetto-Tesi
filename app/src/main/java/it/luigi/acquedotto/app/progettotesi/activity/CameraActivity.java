package it.luigi.acquedotto.app.progettotesi.activity;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Camera;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import it.luigi.acquedotto.app.progettotesi.Applicazione;
import it.luigi.acquedotto.app.progettotesi.Costanti;
import it.luigi.acquedotto.app.progettotesi.R;
import it.luigi.acquedotto.app.progettotesi.modello.Attivita;
import it.luigi.acquedotto.app.progettotesi.modello.Modello;
import it.luigi.acquedotto.app.progettotesi.vista.CameraPreview;

public class CameraActivity extends Activity {
    public static final String TAG = CameraActivity.class.getName();

    private Camera mCamera;
    private CameraPreview preview;
    private ImageButton bottoneScatta;
    private ImageButton bottoneChiudi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_camera);
        preview = new CameraPreview(this, (SurfaceView) findViewById(R.id.camera_preview));
        FrameLayout framePreview = (FrameLayout) findViewById(R.id.activity_camera);
        framePreview.addView(preview);
        preview.setKeepScreenOn(true);
        bottoneScatta = (ImageButton) findViewById(R.id.bottoneScatta);
        bottoneScatta.setOnClickListener(Applicazione.getIstance().getControlloCamera().getAzioneScattaFoto());
        bottoneChiudi = (ImageButton) findViewById(R.id.bottoneChiudi);
        bottoneChiudi.setOnClickListener(Applicazione.getIstance().getControlloCamera().getAzioneEsci());
    }


    //metodo di richiesta per l'acquisizione di un immagine dalla fotocamera
    public void scattaFoto(){
        mCamera.takePicture(null, null, jpegCallback);
    }

    @Override
    protected void onResume() {
        super.onResume();
        int numCams = Camera.getNumberOfCameras();
        if(numCams > 0){
            try{
                //apre la fotocamera, comincia la visualizzazione a schermo
                mCamera = Camera.open(0);
                Log.d(TAG, mCamera.toString());
                mCamera.startPreview();
                preview.setCamera(mCamera);
            }catch (Exception e){
                Log.d(TAG, "Errore: "+ e.getMessage());
            }
        }
    }

    @Override
    protected void onPause() {
        if(mCamera != null){
           //Blocca la visualizzazione a schermo e rilascia la telecamera.
            mCamera.stopPreview();
            preview.setCamera(null);
            mCamera.release();
            mCamera = null;
        }
        super.onPause();
    }

    private void resetCam(){
        mCamera.startPreview();
        preview.setCamera(mCamera);
    }

    private void refreshGallery(File file){
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        mediaScanIntent.setData(Uri.fromFile(file));
        sendBroadcast(mediaScanIntent);
    }

    Camera.PictureCallback jpegCallback = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            new SaveImageTask().execute(data);
            resetCam();
            Log.d(TAG, "OnPictureTaken - jpeg");
        }
    };

    private class SaveImageTask extends AsyncTask<byte[], Void, Void>{
        //Classe interna che gira su thread asincrono per la gestione del salvataggio della foto
        private final String TAG = SaveImageTask.class.getName();

        @Override
        protected Void doInBackground(byte[]... params) {
            Log.d(TAG, "SALVO L'immagine");
            FileOutputStream outputStream = null;
            try {
                File sdCard = Environment.getExternalStorageDirectory();
                File dir = new File(sdCard.getAbsolutePath()+File.separator+"Acquedotto Lucano");
                if (! dir.exists()){
                    Log.d(TAG, "Cartella non esiste");
                    if (! dir.mkdirs()){
                        Log.d(TAG,  "failed to create directory");
                        return null;
                    }
                }
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                String fileName = "IMG_" + timeStamp + ".jpg";
                File outFile = new File(dir, fileName);
                outputStream = new FileOutputStream(outFile);
                outputStream.write(params[0]);
                outputStream.flush();
                outputStream.close();
                refreshGallery(outFile);
                Modello modello = Applicazione.getIstance().getModello();
                //modello.saveBean(Costanti.FILE, outFile);
                Attivita attivita = (Attivita) modello.getBean(Costanti.ATTIVITA);
                attivita.setIdImmagine(Uri.fromFile(outFile).toString());
            }catch (FileNotFoundException e){

            }catch (IOException e){

            }
            return null;
        }
    }
}
