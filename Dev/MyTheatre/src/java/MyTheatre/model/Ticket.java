/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MyTheatre.model;

import java.util.Date;

/**
 *
 * @author mintsaj
 */
public class Ticket {

    private int noSerie;
    private Date dateEmission;
    private Representation representation;
    private Siege siege;

    
    /**
     * 
     * @param noS
     * @param dateEm
     * @param rep
     * @param siege 
     */
    
    public Ticket(int noS, Date dateEm, Representation rep, Siege siege) {

        this.noSerie = noS ;
        this.dateEmission = dateEm ; 
        this.representation = rep ; 
        this.siege = siege ;
        
    }

    public int getNoSerie() {
        return noSerie;
    }

    public Date getDateEmission() {
        return dateEmission;
    }

    public Representation getRepresentation() {
        return representation;
    }

    public Siege getSiege() {
        return siege;
    }
    
    

}
