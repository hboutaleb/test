package MyTheatre.model;

import java.util.Iterator;
import java.util.List;

public class ListePlacesDispo implements Iterable<PlaceDispo> {

    List<PlaceDispo> lesPlacesDispo;

    public ListePlacesDispo(List<PlaceDispo> lesPlacesDispo) {
        this.lesPlacesDispo = lesPlacesDispo;
    }

    @Override
    public Iterator<PlaceDispo> iterator() {
        return lesPlacesDispo.iterator();
    }

    public int size() {
        return lesPlacesDispo.size();
    }

    /**
     * le nombre total de places disponibles toutes catégories confondues
     * @return
     */
    public int getNbTotalPlacesDispo() {
        // calcul la somme des places dispo a partir des donnees de la BD
        int nbPlacesDispo = 0;
        for (PlaceDispo placeDispo : lesPlacesDispo) {
            nbPlacesDispo = nbPlacesDispo + placeDispo.getNbPlacesDispo();
        }
        return nbPlacesDispo;
    }

    /**
     * retourne le nombre de places disponilbes pour une catégrie donnée
     *
     * @param categorie la catégorie
     * @return le nombre de places disponibles.
     */
    public int getNbPlacesDispos(String categorie) {
        for (PlaceDispo placeDispo : lesPlacesDispo) {
            if (placeDispo.getNomC().equals(categorie)) {
                return placeDispo.getNbPlacesDispo();
            }
        }
        return 0;
    }
}
