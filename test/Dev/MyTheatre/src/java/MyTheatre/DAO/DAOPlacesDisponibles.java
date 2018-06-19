package MyTheatre.DAO;

import MyTheatre.model.ListePlacesDispo;
import MyTheatre.model.PlaceDispo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

public class DAOPlacesDisponibles {

    /**
     * retourne la liste des places disponible dans chaque catégorie pour une
     * réprésentation donnée
     *
     * @param dataSource
     * @param noSpec
     * @param dateRep
     * @return
     * @throws SQLException
     */
    public static ListePlacesDispo getPlacesDisponible(DataSource dataSource, String noSpec, LocalDateTime dateRep) throws SQLException {
        List<PlaceDispo> lesPlacesDispo = new ArrayList<>();

        Date dateRepCompatSQL = new Date(Date.from(dateRep.atZone(ZoneId.systemDefault()).toInstant()).getTime());
        try (Connection conn = dataSource.getConnection()) {

            //transformation d'un date au format localdatetime vers le format date            
            String query2 = "SELECT NOMC,COUNT(*) AS nbPlacesDispo "
                    + "from ( "
                    + "select NOMC,NOPLACE,NORANG "
                    + "from lessieges S "
                    + "join leszones Z ON (S.NOZONE = Z.NOZONE) "
                    + "MINUS "
                    + "select NOMC,T.NOPLACE,T.NORANG "
                    + "from lestickets T "
                    + "join lessieges S on(T.NOPLACE=S.NOPLACE AND T.NORANG=S.NORANG) "
                    + "join leszones Z ON (S.NOZONE = Z.NOZONE) "
                    + "where T.DATEREP = ? "
                    + "and T.NOSPEC= ?) "
                    + "group by NOMC ";

            try (PreparedStatement stmt = conn.prepareStatement(query2)) {
                stmt.setDate(1, dateRepCompatSQL);
                stmt.setString(2, noSpec);
                ResultSet rs2 = stmt.executeQuery();
                while (rs2.next()) {
                    String nomC = rs2.getString("nomC");
                    int nbPlacesDispo = rs2.getInt("nbPlacesDispo");

//                    lesSpectacles.add(new Spectacle(nom,resume,duree,dateRep1,genre,CategorieAge));
                    System.out.println("Valeurs de la BD: dans la categorie " + nomC + ", il y a " + nbPlacesDispo + " places dispo.");

                    lesPlacesDispo.add(new PlaceDispo(nomC, nbPlacesDispo));

                }

            }
            return new ListePlacesDispo(lesPlacesDispo);
        }

    }

}
