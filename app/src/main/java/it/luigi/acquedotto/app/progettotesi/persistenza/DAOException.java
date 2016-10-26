package it.luigi.acquedotto.app.progettotesi.persistenza;

/**
 * Created by luigi on 06/10/2016.
 */

public class DAOException extends RuntimeException {
    public DAOException(){}

    public DAOException(String s){super(s);}

    public DAOException(Exception e){
        super(e);
    }
}
