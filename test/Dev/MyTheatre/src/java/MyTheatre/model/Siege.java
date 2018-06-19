/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MyTheatre.model;

import java.util.List;

/**
 *
 * @author mintsaj
 */
public class Siege {

    private int noRang;
    private int noPlace;
    private Categorie categorie;

    /*
     * Construit un siege à partir du numéro de rang et du numero de place fourni par ajax.
     * @param noPlaceSC le numéro de la place fourni par ajax
     * @param noRangSC   le rang fourni par AJAX
     */
    public Siege(int noRangSC, int noPlaceSC) {
        this.noRang = noRangSC;
        this.noPlace = (noPlaceSC <= 11) ? noPlaceSC - 1 : noPlaceSC - 3;

        if (noRangSC <= 19) {
            this.categorie = new Categorie("orchestre", 50);
        } else {
            if (noRangSC <= 29) {
                this.categorie = new Categorie("1er balcon", 40);
            } else {
                if (noRangSC <= 39) {
                    this.categorie = new Categorie("2nd balcon", 20);
                } else {
                    this.categorie = new Categorie("poulailler", 10);
                }
            }
        }
    }

    /**
     * Cree un siege a partir de norang et noplace comme definit dans la bd.
     * @param noRang numero de rang dans la bd
     * @param noPlace numero de place dans la bd
     * @param categorie categorie
     */
    public Siege(int noRang, int noPlace, Categorie categorie) {
        this.noRang = noRang;
        this.noPlace = noPlace;
        this.categorie = categorie;
    }
    
    

    /**
     * Renvoie le nombre de siege d'une catégorie donnee presents dans la liste
     * de sieges fournis en parametre
     *
     * @param listeSiege : la liste de sieges
     * @param categorie : la categorie
     * @return le nbre de sieges dans cette categorie
     */
    public static int nbSiegesChoisisPourUneCategorie(List<Siege> listeSiege, String categorie) {
        int resultat = 0;
        for (Siege siege : listeSiege) {
            if (siege.getCategorie().getNomC().equals(categorie)) {
                resultat += 1;
            }
        }
        return resultat;
    }

    public int getNoRang() {
        return noRang;
    }

    public int getNoPlace() {
        return noPlace;
    }

    public Categorie getCategorie() {
        return categorie;
    }

}
