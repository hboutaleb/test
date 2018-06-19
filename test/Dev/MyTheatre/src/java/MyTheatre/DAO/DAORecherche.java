package MyTheatre.DAO;

import MyTheatre.model.ListePlacesDispo;
import MyTheatre.model.PlaceDispo;
import MyTheatre.model.Representation;
import MyTheatre.model.Spectacle;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;

/**
 * DAO pour le recherche multi-cirtères de représentations
 */
public class DAORecherche {

    /**
     * à partir d'un resultset contenant noSpec, nomS, TypeGenre, Nom, dateRep
     * construit une liste d'objets model.Representation
     *
     * @param rs le resultset
     * @return la liste des representation contenues dans le ResultSet
     * @throws SQLException
     */
    private static List<Representation> creerListeRepresentations(DataSource dataSource, ResultSet rs) throws SQLException {
        // tableau associatif pour stocker les spectacles déjà créés, la clé étant le numéro du spectacle
        Map<Integer, Spectacle> spectaclesCrees = new HashMap<>();

        List<Representation> lesRepresentations = new ArrayList<>();
        while (rs.next()) {
            int noSpec = rs.getInt("noSpec");
            Spectacle spectacle;
            if (!spectaclesCrees.containsKey(noSpec)) {
                String nom = rs.getString("nomS");
                String genre = rs.getString("TYPEGENRE");
                String categorieAge = rs.getString("NOM");
                spectacle = new Spectacle(noSpec, nom, genre, categorieAge);
                spectaclesCrees.put(noSpec, spectacle);
            } else {
                spectacle = spectaclesCrees.get(noSpec);
            }

            Timestamp dateRep = rs.getTimestamp("dateRep");
            ListePlacesDispo listeplacesDispo = DAOPlacesDisponibles.getPlacesDisponible(dataSource, String.valueOf(noSpec), dateRep.toLocalDateTime());
            lesRepresentations.add(new Representation(spectacle, dateRep, listeplacesDispo));
        }
        return lesRepresentations;
    }

    /**
     * Retourne la liste des représentation sur une plage donnee . plage de date
     * seule ou plage de date+heure
     *
     * @param dataSource la source de données
     * @param dateDebut date+heure de debut type LocalDateTime
     * @param dateFin date+heure de fin type type LocalDateTime
     * @return la lsite des spectacles trouvés
     *
     * @throws SQLException si problème avec la BD.
     *
     */
    public static List<Representation> getSpectaclesSurCritereDate(DataSource dataSource, LocalDateTime dateDebut, LocalDateTime dateFin) throws SQLException {

        try (Connection conn = dataSource.getConnection()) {

            //transformation d'un date au format localdatetime vers le format date            
            Date dateDebutCompatSQL = new Date(Date.from(dateDebut.atZone(ZoneId.systemDefault()).toInstant()).getTime());
            Date dateFinCompatSQL = new Date(Date.from(dateFin.atZone(ZoneId.systemDefault()).toInstant()).getTime());

            String query = "select S.nospec as noSpec, nomS, dateRep, TYPEGENRE, NOM "
                    + "FROM LesRepresentations R "
                    + "join LesSpectacles S on (R.nospec = S.nospec) "
                    + "join GENRESPECTACLE G on (G.GENRE_ID = S.GENRE_ID) "
                    + "join CATAGE C on (S.CATAGEID=C.CATAGEID) "
                    + "where dateRep >= ? "
                    + "AND dateRep < ? "
                    + "order by dateRep";

            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, "2018-01-02 20:30:00");
                stmt.setString(2, "2018-05-02 20:30:00");
                ResultSet rs = stmt.executeQuery();
                return creerListeRepresentations(dataSource, rs);
            }
        }
    }

    public static List<Representation> getSpectaclesSurCritereAgeEtDate(DataSource dataSource, LocalDateTime dateDebut, LocalDateTime dateFin, String catage) throws SQLException {

        try (Connection conn = dataSource.getConnection()) {

            //transformation d'un date au format localdatetime vers le format date            
            Date dateDebutCompatSQL = new Date(Date.from(dateDebut.atZone(ZoneId.systemDefault()).toInstant()).getTime());
            Date dateFinCompatSQL = new Date(Date.from(dateFin.atZone(ZoneId.systemDefault()).toInstant()).getTime());

            String query = "select S.noSpec, nomS, dateRep, TYPEGENRE, NOM "
                    + "FROM LesRepresentations R "
                    + "join LesSpectacles S on (R.nospec = S.nospec) "
                    + "join GENRESPECTACLE G on (G.GENRE_ID = S.GENRE_ID) "
                    + "join CATAGE C on (S.CATAGEID=C.CATAGEID) "
                    + "where dateRep >= ? "
                    + "AND dateRep < ? "
                    + "AND NOM = ?";

            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setDate(1, dateDebutCompatSQL);
                stmt.setDate(2, dateFinCompatSQL);
                stmt.setString(3, catage);
                ResultSet rs = stmt.executeQuery();
                return creerListeRepresentations(dataSource, rs);
            }
        }
    }
}
