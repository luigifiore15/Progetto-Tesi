package it.luigi.acquedotto.app.progettotesi.vista;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import it.luigi.acquedotto.app.progettotesi.Applicazione;
import it.luigi.acquedotto.app.progettotesi.Costanti;
import it.luigi.acquedotto.app.progettotesi.R;
import it.luigi.acquedotto.app.progettotesi.modello.Attivita;
import it.luigi.acquedotto.app.progettotesi.modello.Modello;
import it.luigi.acquedotto.app.progettotesi.modello.Utente;


public class AttivitaFragment extends Fragment {
    public static final String TAG = AttivitaFragment.class.getName();

    private String data;
    private TextView testoX;
    private TextView testoY;
    private TextView testoZ;
    private EditText editRilevatore;
    private EditText editNote;
    private Spinner spinnerTipologia;
    private Button bottoneLocalizza;
    private Button bottoneFoto;
    private Button bottoneSalva;
    private ImageView imageFoto;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_attivita, container, false);
        testoX = (TextView) view.findViewById(R.id.testoX);
        testoY = (TextView) view.findViewById(R.id.testoY);
        testoZ = (TextView) view.findViewById(R.id.testoZ);
        editRilevatore = (EditText) view.findViewById(R.id.testoRilevatore);
        editNote = (EditText) view.findViewById(R.id.testoNote);
        spinnerTipologia = (Spinner) view.findViewById(R.id.comboTipologia);
        bottoneLocalizza = (Button) view.findViewById(R.id.bottoneLocalizza);
        bottoneLocalizza.setOnClickListener(Applicazione.getIstance().getControlloAttività().getAzioneLocalizza());
        bottoneFoto = (Button) view.findViewById(R.id.bottoneFoto);
        bottoneFoto.setOnClickListener(Applicazione.getIstance().getControlloAttività().getAzioneFoto());
        bottoneSalva = (Button) view.findViewById(R.id.bottoneSalva);
        bottoneSalva.setOnClickListener(Applicazione.getIstance().getControlloAttività().getAzioneSalva());
        imageFoto = (ImageView) view.findViewById(R.id.imageFoto);
        imageFoto.setClickable(true);
        imageFoto.setOnClickListener(Applicazione.getIstance().getControlloAttività().getAzioneApriImmagine());
        //inizializzaCampi();
        return view;
    }

    private void inizializzaCampi() {
        Modello modello = Applicazione.getIstance().getModello();
        Attivita attivita = (Attivita) modello.getBean(Costanti.ATTIVITA);
        Utente utente = (Utente) modello.getBean(Costanti.UTENTE);
        if(attivita == null){
            //Controllo sull'attivita salvata in memoria, nel caso essa è null ne creo una nuova
            //ed esco dalla procedura
            attivita = new Attivita(Integer.parseInt(utente.getIdUtente()));
            Log.d(TAG, "creata nuova attività: "+ attivita.getIdAttivita());
            modello.saveBean(Costanti.ATTIVITA, attivita);
            return;
        }
        //l'attivita caricata è gia inizializzata, quindi visualizzo le informazioni della stessa
        //nella schermata
        Log.d(TAG, "attivita n: " + attivita.getIdAttivita());
        testoX.setText(Double.toString(attivita.getxLocation()));
        testoY.setText(Double.toString(attivita.getyLocation()));
        testoZ.setText(Double.toString(attivita.getzLocation()));
        editRilevatore.setText(attivita.getRilevatore());
        editNote.setText(attivita.getNote());
        //controllo sull'id dell'immagine (una stringa che incorpora l'uri formattato)
        if(attivita.getIdImmagine() != null) {
            //se l'id non è NULLO, carico nell'ImageView l'immagine con quell'URI
            //e setto l'immagine come cliccabile
            imageFoto.setImageURI(Uri.parse(attivita.getIdImmagine()));
            imageFoto.setClickable(true);
        }else{
            //se l'id è nulla, setto l'ImageView come non cliccabile
            //Passo come URI null cosi resettare la visualizzazione
            imageFoto.setClickable(false);
            imageFoto.setImageURI(null);
        }
        if(attivita.getIdAttivita() == 0){
            //se l'id attivita è =, significa che ancora non è stata salvata quindi esco
            return;
        }
        if(attivita.getTipologia().equals("A")){
            spinnerTipologia.setSelection(0);
        }else if (attivita.getTipologia().equals("B")){
            spinnerTipologia.setSelection(1);
        }else if (attivita.getTipologia().equals("C")){
            spinnerTipologia.setSelection(2);
        }
    }

    public ImageView getImageFoto() {
        return imageFoto;
    }

    public TextView getTestoX() {
        return testoX;
    }

    public TextView getTestoY() {
        return testoY;
    }

    public TextView getTestoZ() {
        return testoZ;
    }

    public EditText getEditNote() {
        return editNote;
    }

    public EditText getEditRilevatore() {
        return editRilevatore;
    }

    public Spinner getSpinnerTipologia() {
        return spinnerTipologia;
    }

    public void setData(long sec){
        Date data = new Date(sec);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm.ss");
        String s = simpleDateFormat.format(data);
        this.data = s;
    }

    public String getData() {
        return data;
    }

    @Override
    public void onResume() {
        super.onResume();
        inizializzaCampi();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

}
