package MyTheatre.model;

import org.junit.Test;
import static org.junit.Assert.*;

public class CategorieTest {
    
    public CategorieTest() {
    }

    /**
     * Test of getNOMC method, of class Categorie.
     */
    @Test
    public void testGetNomC() {
        System.out.println("getNomC");
        Categorie instance = new Categorie("Poulailler",100);
        String expResult = "Poulailler";
        String result = instance.getNomC();
        assertEquals(expResult, result);
    }

    /**
     * Test of getPRIX method, of class Categorie.
     */
    @Test
    public void testGetPrix() {
        System.out.println("getPrix");
        Categorie instance = new Categorie("Poulailler",100);
        int expResult =100;
        int result = instance.getPrix();
        assertEquals(expResult, result);
    }
}
