package it.luigi.acquedotto.app.progettotesi.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import it.luigi.acquedotto.app.progettotesi.R;
import it.luigi.acquedotto.app.progettotesi.vista.LoginFragment;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public LoginFragment getLoginFragment(){
        return (LoginFragment) this.getSupportFragmentManager().findFragmentById(R.id.fragmentLogin);
    }

    //////////////Schermi/////////////
    public void schermoLoggato(){
        Intent intent = new Intent(getApplicationContext(), UtenteActivity.class);
        startActivity(intent);
    }
}
