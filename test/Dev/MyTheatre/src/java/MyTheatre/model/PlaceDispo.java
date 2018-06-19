/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MyTheatre.model;

/**
 *
 * @author boutaleh
 */
public class PlaceDispo {

    private String nomC;
    private int nbPlacesDispo;

    public PlaceDispo(String nomC, int NbPlacesDispo) {

        this.nbPlacesDispo = NbPlacesDispo;
        this.nomC = nomC;

    }

    public String getNomC() {
        return nomC;
    }

    public int getNbPlacesDispo() {
        return nbPlacesDispo;
    }
}
