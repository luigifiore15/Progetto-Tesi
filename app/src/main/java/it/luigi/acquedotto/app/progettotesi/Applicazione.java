package it.luigi.acquedotto.app.progettotesi;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.Log;

import it.luigi.acquedotto.app.progettotesi.controllo.ControlloCamera;
import it.luigi.acquedotto.app.progettotesi.controllo.ControlloImmagine;
import it.luigi.acquedotto.app.progettotesi.controllo.ControlloLogin;
import it.luigi.acquedotto.app.progettotesi.controllo.ControlloAttività;
import it.luigi.acquedotto.app.progettotesi.controllo.ControlloUtente;
import it.luigi.acquedotto.app.progettotesi.modello.Modello;

/**
 * Created by luigi on 06/10/2016.
 */

public class Applicazione extends Application {
    private final String TAG = Applicazione.class.getName();

    private static Applicazione singleton;

    public static Applicazione getIstance(){
        return singleton;
    }

    @Override
    public void onCreate() {
        Log.d(TAG, "creoApplicazione");
        singleton = (Applicazione) getApplicationContext();
        singleton.registerActivityLifecycleCallbacks(new GestioneAttività());
    }

    ///////////////////////////////////////////////
    private Activity currentActivity;

    private Modello modello = new Modello();
    private ControlloLogin controlloLogin = new ControlloLogin();
    private ControlloAttività controlloAttività = new ControlloAttività();
    private ControlloUtente controlloUtente = new ControlloUtente();
    private ControlloCamera controlloCamera = new ControlloCamera();
    private ControlloImmagine controlloImmagine = new ControlloImmagine();


    public Activity getCurrentActivity() {
        return currentActivity;
    }

    public Modello getModello() {
        return modello;
    }

    public ControlloLogin getControlloLogin() {
        return controlloLogin;
    }
    public ControlloAttività getControlloAttività() {
        return controlloAttività;
    }

    public ControlloImmagine getControlloImmagine() {
        return controlloImmagine;
    }

    public ControlloUtente getControlloUtente() {
        return controlloUtente;
    }
    public ControlloCamera getControlloCamera() {
        return controlloCamera;
    }

    ///////////////////////////////////////////////
    private class GestioneAttività implements ActivityLifecycleCallbacks{

        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

        }

        @Override
        public void onActivityStarted(Activity activity) {

        }

        @Override
        public void onActivityResumed(Activity activity) {
            currentActivity = activity;
        }

        @Override
        public void onActivityPaused(Activity activity) {

        }

        @Override
        public void onActivityStopped(Activity activity) {
            if (currentActivity==activity){
                currentActivity = null;
            }
        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

        }

        @Override
        public void onActivityDestroyed(Activity activity) {

        }
    }
}
