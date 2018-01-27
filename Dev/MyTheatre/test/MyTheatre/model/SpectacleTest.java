package MyTheatre.model;

import java.sql.Timestamp;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;

public class SpectacleTest {
    
    public SpectacleTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    /**
     * Test of getNom method, of class Spectacle.
     */
    @Test
    public void testGetNom() {
        System.out.println("getNom");
        Spectacle instance = new Spectacle(1, "LaGalere_romaine", "resume tres court", 1,"Humour","tout public", null);
        String expResult = "LaGalere_romaine";
        String result = instance.getNom();
        assertEquals(expResult, result);
    }

    /**
     * Test of getResume method, of class Spectacle.
     */
    @Test
    public void testGetResume() {
        System.out.println("getResume");
        Spectacle instance = new Spectacle(1, "LaGalere_romaine", "resume tres court", 1,"Humour","tout public", null);
        String expResult = "resume tres court";
        String result = instance.getResume();
        assertEquals(expResult, result);
    }

    /**
     * Test of getDuree method, of class Spectacle.
     */
    @Test
    public void testGetDuree() {
        System.out.println("getDuree");
        Spectacle instance = new Spectacle(1, "LaGalere_romaine", "resume tres court", 1,"Humour","tout public", null);
        int expResult = 1;
        int result = instance.getDuree();
        assertEquals(expResult, result);
     
    }

    /**
     * Test of getTypegenre method, of class Spectacle.
     */
    @Test
    public void testGetGenre() {
        System.out.println("getGenre");
        Spectacle instance = new Spectacle(1, "LaGalere_romaine", "resume tres court", 1,"Humour","tout public", null);
        String expResult = "Humour";
        String result = instance.getGenre();
        assertEquals(expResult, result);
    }

    /**
     * Test of getNomct method, of class Spectacle.
     */
    @Test
    public void testGetNomct() {
        System.out.println("getNomct");
        Spectacle instance = new Spectacle(1, "LaGalere_romaine", "resume tres court", 1,"Humour","tout public", null);
        String expResult = "tout public";
        String result = instance.getCatAge();
        assertEquals(expResult, result);
    }
    
}