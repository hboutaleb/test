package MySpectacle.DAO;

import MyTheatre.DAO.DAORecherche;
import MyTheatre.model.Representation;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import javax.sql.DataSource;
import m2pcci.im2ag.webcafes.model.dao.util.TestDataSource;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.BeforeClass;

public class RechercheDAOTest {

    private static DataSource dataSource;

    public RechercheDAOTest() {
    }

    @BeforeClass
    public static void setUpClass() throws IOException, SQLException {
        dataSource = new TestDataSource();
    }

    /**
     * Test of getSpectaclesSurCritereDate method, of class RechercheDAO.
     */
    @Test
    public void testGetSpectaclesSurCritereDate() throws Exception {
        System.out.println("getSpectaclesSurCritereDate");

        // Dans l'intervalle suivant on attend une seule representation pour "La flûte enchantée" 
        // On testera uniquement la date de la representation car c'est un parametre qui restera fixe à tout instant 
        // quelque soit les eneregistrements dans la base de donnée.
        LocalDateTime dateDebut = LocalDateTime.of(2000, 02, 20, 8, 00);
        LocalDateTime dateFin = LocalDateTime.of(2017, 02, 22, 8, 00);

        Timestamp expDate = Timestamp.valueOf("2017-02-21 08:30:00");

        List<Representation> result = DAORecherche.getSpectaclesSurCritereDate(dataSource, dateDebut, dateFin);
        Timestamp resultDate = result.get(0).getDateRep();
        assertEquals(expDate, resultDate);
    }
}
