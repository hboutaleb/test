package MyTheatre.DAO;

import MyTheatre.model.Categorie;
import MyTheatre.model.Photo;
import MyTheatre.model.Representation;
import MyTheatre.model.Spectacle;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

/**
 *
 * @author User1
 */
public class DAODetailRepresentation {

    /**
     * Retourne représentation avec tous ses détails :
     *
     * - places disponibles - spectacle avec son résumé
     *
     * @param dataSource a source de données
     * @param dateRep
     * @param noSpec
     *
     * @return la représentation détaillée ou null si il n'y a pas de
     * representation pour le spectacle et la date donnés.
     * @throws SQLException si problème avec la BD.
     */
    public static Representation getDetailRepresentation(DataSource dataSource, LocalDateTime dateRep, String noSpec) throws SQLException {

        Date dateRepCompatSQL = new Date(Date.from(dateRep.atZone(ZoneId.systemDefault()).toInstant()).getTime());
        try (Connection conn = dataSource.getConnection()) {

            //transformation d'un date au format localdatetime vers le format date            
            String query1 = "select S.noSpec, nomS, RESUME,daterep,duree,TYPEGENRE, NOM "
                    + "FROM LesRepresentations R "
                    + "join LesSpectacles S on (R.nospec = S.nospec) "
                    + "join GENRESPECTACLE G on (G.GENRE_ID = S.GENRE_ID) "
                    + "join CATAGE C on (S.CATAGEID=C.CATAGEID) "
                    + "where daterep = ? "
                    + "and R.nospec =?";

            try (PreparedStatement stmt = conn.prepareStatement(query1)) {
                stmt.setDate(1, dateRepCompatSQL);
                stmt.setString(2, noSpec);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    int no = rs.getInt("noSpec");
                    String nom = rs.getString("nomS");
                    String resume = rs.getString("RESUME");
                    Timestamp dateRep1 = rs.getTimestamp("dateRep");
                    int duree = rs.getInt("duree");
                    String genre = rs.getString("TYPEGENRE");
                    String CategorieAge = rs.getString("NOM");
                    
                    List<Photo> lesphotos = getLesPhotos(dataSource, no);
//                    lesSpectacles.add(new Spectacle(nom,resume,duree,dateRep1,genre,CategorieAge));
                    System.out.println("Valeurs de la BD: " + nom + " ");
                    return new Representation(
                            new Spectacle(no, nom, resume, duree, genre, CategorieAge, lesphotos),
                            dateRep1,
                            DAOPlacesDisponibles.getPlacesDisponible(dataSource, noSpec, dateRep)
                    );
                } else {
                    return null;
                }
            }
        }
    }

    public static List<Categorie> getDetailSpectaclePrixCat(DataSource dataSource) throws SQLException {
        List<Categorie> LesCategories = new ArrayList<>();
        try (Connection conn = dataSource.getConnection()) {

            //transformation d'un date au format localdatetime vers le format date            
            String query2 = "select * "
                    + "FROM LESCATEGORIES";

            try (PreparedStatement stmt = conn.prepareStatement(query2)) {
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    String nomc = rs.getString("NOMC");
                    int LePrix = rs.getInt("PRIX");
                    LesCategories.add(new Categorie(nomc, LePrix));
                    System.out.println("Valeurs de la BD: " + nomc + LePrix);
                }
                return LesCategories;
            }
        }
    }

    private static final String TROUVER_LESPHOTOS = 
            "select P.nophoto as nophoto, contenu "
            + "from lesphotosspectacle PS "
            + "join lesphotos P on (PS.nophoto = P.nophoto) "
            + "where nospec = ?";

    public static List<Photo> getLesPhotos(DataSource dataSource, int numS) throws SQLException {
        List<Photo> listephotos = new ArrayList<>();
        try (Connection conn = dataSource.getConnection()) {
            try (PreparedStatement stmt = conn.prepareStatement(TROUVER_LESPHOTOS)) {
                stmt.setInt(1, numS);
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {                                       
                    int nophoto = rs.getInt("nophoto");
                    String contenu = rs.getString("contenu");                    
                    listephotos.add(new Photo(nophoto, contenu));
                    System.out.println("Valeurs de la BD: nophoto=" + nophoto + " contenu="+ contenu);
                }                            
            }
        }
        return listephotos;
    }
  
}
