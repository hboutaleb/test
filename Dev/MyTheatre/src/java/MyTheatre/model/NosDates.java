package MyTheatre.model;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class NosDates {

    /**
     * Converti une date timestamp en jour mois annee
     *
     * @param date une date ou timestamp class sql
     * @return la date "dd/mm/yyyy" en string
     */
    public static String dateAuFormatddmmyyy(Timestamp date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.format(date);
    }

    /**
     * Converti une date de type timestamp en temps heure minute
     *
     * @param date une date ou timestamp class sql
     * @return le temps "dd/mm/yyyy" en string
     */
    public static String tempsAuFormathhmm(Timestamp date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm");
        return dateFormat.format(date);
    }

    /**
     * Converti une date en format dd/MM/yyyy 'à' hh:mm
     *
     * @param date une date
     * @return un string
     */
    public static String dateEtTempsFormatddmmyyy_hhmm(Timestamp date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("'le' dd/MM/yyyy 'à' hh:mm");
        return dateFormat.format(date);
    }
}
