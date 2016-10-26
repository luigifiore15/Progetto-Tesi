package it.luigi.acquedotto.app.progettotesi.activity;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;

import it.luigi.acquedotto.app.progettotesi.R;
import it.luigi.acquedotto.app.progettotesi.vista.ImmagineFragment;


public class ImmagineActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_immagine);
    }

    public ImmagineFragment getImmagineFragment(){
        return (ImmagineFragment) this.getSupportFragmentManager().findFragmentById(R.id.fragmentImmagine);
    }
}
