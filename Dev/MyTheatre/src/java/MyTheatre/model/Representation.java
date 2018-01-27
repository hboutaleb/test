package MyTheatre.model;

import java.sql.Timestamp;

/**
 * Représente une réprésentation
 */
public class Representation {

    /**
     * le spectacle associé à la représentation
     */
    private Spectacle spectacle;

    /**
     * la date et l'heure de la représentation
     */
    private Timestamp dateRep;
    /**
     * les places disponibles (par catégorie) pour cette représentation
     */
    ListePlacesDispo placesDispos;

    /**
     *
     * @param spectacle
     * @param dateRep
     * @param placesDispos
     */
    public Representation(Spectacle spectacle, Timestamp dateRep, ListePlacesDispo placesDispos) {
        this.spectacle = spectacle;
        this.dateRep = dateRep;
        this.placesDispos = placesDispos;
    }

    public Spectacle getSpectacle() {
        return spectacle;
    }

    public Timestamp getDateRep() {
        return dateRep;
    }

    public ListePlacesDispo getPlacesDispos() {
        return placesDispos;
    }

    public int getNbTotalPlacesDispo()  {
        return placesDispos.getNbTotalPlacesDispo();
    }
    
    public int getNbPlacesDispos(String categorie) {
         return placesDispos.getNbPlacesDispos(categorie);
    }
}
