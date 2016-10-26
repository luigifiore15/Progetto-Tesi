package it.luigi.acquedotto.app.progettotesi.vista;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import it.luigi.acquedotto.app.progettotesi.Applicazione;
import it.luigi.acquedotto.app.progettotesi.Costanti;
import it.luigi.acquedotto.app.progettotesi.R;
import it.luigi.acquedotto.app.progettotesi.modello.Modello;
import it.luigi.acquedotto.app.progettotesi.modello.ModelloTabellaAttivita;
import it.luigi.acquedotto.app.progettotesi.modello.Utente;
import it.luigi.acquedotto.app.progettotesi.persistenza.DAODatabaseAttivita;

public class UtenteFragment extends Fragment {
    public static final String TAG = UtenteFragment.class.getName();

    private ListView listaAttivita;
    private Button bottoneAggiungiAttivita;
    private Utente utente;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Creazione del Fragment richiamando il file xml di layout
        View view = inflater.inflate(R.layout.fragment_utente, container, false);
        Modello modello = Applicazione.getIstance().getModello();
        utente = (Utente) modello.getBean(Costanti.UTENTE);
        //Creazione della lista attraverso i parametri dichiarati nel file xml di layout
        //Settaggio dell'azione al click sulla singola voce
        listaAttivita = (ListView) view.findViewById(R.id.listaAttivita);
        listaAttivita.setOnItemClickListener(Applicazione.getIstance().getControlloUtente().getAzioneVisualizzaAttivita());
        //Creazione del bottone attraverso l'ID dal file di layout
        //Settaggio dell'azione al click
        bottoneAggiungiAttivita = (Button) view.findViewById(R.id.bottoneAggiungiAttivita);
        bottoneAggiungiAttivita.setOnClickListener(Applicazione.getIstance().getControlloUtente().getAzioneAggiungi());
        this.aggiornaVista();
        return view;
    }

    //Procedura per il settaggio dell'Adapter della lista per la creazione degli elementi della stessa
    private void aggiornaVista() {
        this.listaAttivita.setAdapter(new ModelloTabellaAttivita());
    }

    @Override
    public void onResume() {
        super.onResume();
        this.aggiornaVista();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
