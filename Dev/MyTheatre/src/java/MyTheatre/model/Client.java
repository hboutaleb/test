/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MyTheatre.model;

/**
 *
 * @author User1
 */
public class Client {
    private String EMAIL;
    private String NOM;
    private String PRENOM;
    
    public Client (String EMAIL,String NOM,String PRENOM){
    this.EMAIL=EMAIL;
    this.NOM=NOM;
    this.PRENOM=PRENOM;
    }

    public String getEMAIL() {
        return EMAIL;
    }

    public String getNOM() {
        return NOM;
    }

    public String getPRENOM() {
        return PRENOM;
    }
    
    
}
