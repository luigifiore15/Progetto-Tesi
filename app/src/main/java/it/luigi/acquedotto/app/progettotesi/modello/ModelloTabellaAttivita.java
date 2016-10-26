package it.luigi.acquedotto.app.progettotesi.modello;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CursorAdapter;
import android.widget.TextView;

import it.luigi.acquedotto.app.progettotesi.Applicazione;
import it.luigi.acquedotto.app.progettotesi.Costanti;
import it.luigi.acquedotto.app.progettotesi.R;
import it.luigi.acquedotto.app.progettotesi.persistenza.DAODatabaseAttivita;
import it.luigi.acquedotto.app.progettotesi.persistenza.TabellaAttivita;

/**
 * Created by luigi on 18/10/2016.
 */

public class ModelloTabellaAttivita extends BaseAdapter {
    private DAODatabaseAttivita databaseAttivita = new DAODatabaseAttivita(Applicazione.getIstance().getApplicationContext());
    private Utente utente;

    @Override
    public int getCount() {
        Modello modello = Applicazione.getIstance().getModello();
        utente = (Utente) modello.getBean(Costanti.UTENTE);
        return databaseAttivita.getListaAttivita(utente.getIdUtente()).size();
    }

    @Override
    public Object getItem(int position) {
        return databaseAttivita.getAttivitaUtente(utente.getIdUtente()).get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View viewRiciclabile, ViewGroup parent) {
        View riga;
        if (viewRiciclabile == null) {
            LayoutInflater inflater = (LayoutInflater) Applicazione.getIstance().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            riga = inflater.inflate(R.layout.riga_attivita, parent, false);
        }else {
            riga = viewRiciclabile;
        }
        Attivita attivita = databaseAttivita.getListaAttivita(utente.getIdUtente()).get(position);
        TextView testoData = (TextView) riga.findViewById(R.id.testoData);
        testoData.setText(attivita.getT());
        TextView testoTipologia = (TextView) riga.findViewById(R.id.testoTipologia);
        testoTipologia.setText(attivita.getTipologia());
        return riga;
    }
}
