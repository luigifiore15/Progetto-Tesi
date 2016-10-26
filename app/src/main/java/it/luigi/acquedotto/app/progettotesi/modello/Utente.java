package it.luigi.acquedotto.app.progettotesi.modello;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by luigi on 06/10/2016.
 */

public class Utente {
    private String nomeUtente;
    private String idUtente;
    private String username;
    private String password;
    private List<Attivita> listaAttivita = new ArrayList<Attivita>();

    public Utente(String nomeUtente, String idUtente, String username, String password){
        this.idUtente = idUtente;
        this.nomeUtente = nomeUtente;
        this.username = username;
        this.password = password;

    }

    public String getIdUtente() {
        return idUtente;
    }

    public String getNomeUtente() {
        return nomeUtente;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public List<Attivita> getListaAttivita() {
        return listaAttivita;
    }

    public void setListaAttivita(List<Attivita> listaAttivita) {
        this.listaAttivita = listaAttivita;
    }
}
