package it.luigi.acquedotto.app.progettotesi.modello;

import android.net.Uri;

import java.util.Date;

/**
 * Created by luigi on 06/10/2016.
 */

public class Attivita {
    private int idAttivita;
    private int idUtente;
    private String t;
    private double xLocation;
    private double yLocation;
    private double zLocation;
    private String idImmagine;
    private String rilevatore;
    private String note;
    private String tipologia;
    private boolean stato = false;

    public Attivita(int idUtente){
        this.idUtente = idUtente;
    }

    public Attivita(int idAttivita, int idUtente, String t, double xLocation, double yLocation, double zLocation, String idImmagine, String rilevatore, String note, String tipologia){
        this.idAttivita = idAttivita;
        this.idUtente = idUtente;
        this.t = t;
        this.xLocation = xLocation;
        this.yLocation = yLocation;
        this.zLocation = zLocation;
        this.idImmagine = idImmagine;
        this.rilevatore = rilevatore;
        this.note = note;
        this.tipologia = tipologia;
    }

    public int getIdUtente() {
        return idUtente;
    }

    public String getT() {
        return t;
    }

    public void setStato(boolean stato) {
        this.stato = stato;
    }

    public boolean isStato() {
        return stato;
    }

    public int getIdAttivita() {
        return idAttivita;
    }

    public String getIdImmagine() {
        return idImmagine;
    }

    public double getxLocation() {
        return xLocation;
    }

    public double getyLocation() {
        return yLocation;
    }

    public double getzLocation() {
        return zLocation;
    }

    public String getNote() {
        return note;
    }

    public String getRilevatore() {
        return rilevatore;
    }

    public String getTipologia() {
        return tipologia;
    }

    public void setIdImmagine(String idImmagine) {
        this.idImmagine = idImmagine;
    }

    public void setxLocation(double xLocation) {
        this.xLocation = xLocation;
    }

    public void setyLocation(double yLocation) {
        this.yLocation = yLocation;
    }

    public void setzLocation(double zLocation) {
        this.zLocation = zLocation;
    }

    public void setT(String t) {
        this.t = t;
    }

}
