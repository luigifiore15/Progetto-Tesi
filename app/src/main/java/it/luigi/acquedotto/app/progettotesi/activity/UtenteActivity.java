package it.luigi.acquedotto.app.progettotesi.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import it.luigi.acquedotto.app.progettotesi.Applicazione;
import it.luigi.acquedotto.app.progettotesi.Costanti;
import it.luigi.acquedotto.app.progettotesi.R;
import it.luigi.acquedotto.app.progettotesi.modello.Attivita;
import it.luigi.acquedotto.app.progettotesi.modello.Modello;

public class UtenteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_utente);
    }

    public void schermoAttivita(){
        Intent intent = new Intent(getApplicationContext(), AttivitaActivity.class);
        startActivity(intent);
    }

}
