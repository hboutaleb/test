package MyTheatre.DAO;

import MyTheatre.model.Categorie;
import MyTheatre.model.Client;
import MyTheatre.model.Place;
import MyTheatre.model.Representation;
import MyTheatre.model.Siege;
import MyTheatre.model.Ticket;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.json.Json;
import javax.json.stream.JsonGenerator;
import javax.sql.DataSource;

public class DAOAchat {

    static int noSerieAvantInsertion = 0;

    private static final String TROUVERS_PLACES_LIBRES = "select NOMC,NOPLACE,NORANG\n"
            + "from lessieges S\n"
            + "join leszones Z ON (S.NOZONE = Z.NOZONE)\n"
            + "where Z.nomc = ?\n"
            + "MINUS\n"
            + "select NOMC,T.NOPLACE,T.NORANG\n"
            + "from lestickets T\n"
            + "join lessieges S on(T.NOPLACE=S.NOPLACE AND T.NORANG=S.NORANG)\n"
            + "join leszones Z ON (S.NOZONE = Z.NOZONE)\n"
            + " where T.DATEREP = ?\n" // to_date(?, 'DD/MM/YYYY HH24:MI:SS') la date au format '09/03/2017 08:30:00'
            + "  and T.NOSPEC=? and Z.nomc = ?\n"
            + "order by NORANG";

    private static final String TROUVERS_NBEMAIL = "select count(*) as NBEMAIL\n"
            + "from lesclients\n "
            + "where EMAIL = ?";

    private static final String INSERT_TICKET = "INSERT INTO LESTICKETS (NOSERIE, NOSPEC, DATEREP, NOPLACE, NORANG, DATEEMISSION, NODOSSIER) "
            + "VALUES(NOSERIE_SEQ.nextval, ? , ?, ? , ?, ?, ?)";

    // creation un client
    private static final String INSERT_client = "INSERT INTO LESCLIENTS (EMAIL, NOM, PRENOM) "
            + "VALUES( ? , ?, ?)";

    private static final String TROUVERS_MAX_NODOSSIER = "select max(NODOSSIER)as maxND \n"
            + "from LESDOSSIERS \n";

    private static final String INSERT_Dossier = "INSERT INTO LESDOSSIERS (NODOSSIER, MONTANT, EMAIL) "
            + "VALUES(?, ? , ?)";

    private static final String TROUVER_MAX_NOSERIE = "select max(NOSERIE)as maxSerie \n"
            + "from LESTICKETS \n";

    private static final String DETRUIRE_TICKETS = "delete from LESTICKETS "
            + "where NOSERIE = ? ";
    private static final String DETRUIRE_Client = "delete from LESCLIENTS "
            + "where EMAIL = ? ";
    private static final String DETRUIRE_Dossier = "delete from LESDOSSIERS "
            + "where NODOSSIER = ? ";

    public static int creerclient(DataSource ds, Client client) throws SQLException {
        int premierAchat;
        try (Connection conn = ds.getConnection();
                PreparedStatement pstmtQuery = conn.prepareStatement(TROUVERS_NBEMAIL);
                PreparedStatement pstmtInsert = conn.prepareStatement(INSERT_client)) {
            pstmtQuery.setString(1, client.getEMAIL());
            ResultSet rs = pstmtQuery.executeQuery();
            premierAchat = 0;
            while (rs.next()) {
                premierAchat = rs.getInt("NBEMAIL");
            }
            if ((premierAchat == 0)) {
                pstmtInsert.setString(1, client.getEMAIL());
                pstmtInsert.setString(2, client.getNOM());
                pstmtInsert.setString(3, client.getPRENOM());
                pstmtInsert.executeUpdate();
            }
        }
        return premierAchat;
    }

    public synchronized static int creerdossier(DataSource ds, Client client, int montant) throws SQLException {

        try (Connection conn = ds.getConnection();
                PreparedStatement pstmtQuery = conn.prepareStatement(TROUVERS_MAX_NODOSSIER);
                PreparedStatement pstmtInsert = conn.prepareStatement(INSERT_Dossier)) {
            ResultSet rs = pstmtQuery.executeQuery();
            int noDossier = 0;
            rs.next();
            noDossier = rs.getInt("maxND") + 1;
            pstmtInsert.setInt(1, noDossier);

            pstmtInsert.setInt(2, montant);
            pstmtInsert.setString(3, client.getEMAIL());
            pstmtInsert.executeUpdate();
            return noDossier;
        }
    }

    /**
     * Detruit les tickets choisi "parLot" (sans ajax).
     *
     * @param ds dataset
     * @param placesDemandees les places demandees
     * @param client
     * @param noDossier
     * @param premierAchat
     * @throws SQLException
     */
    public synchronized static void detruireTicketsParLot(DataSource ds, Map<String, Integer> placesDemandees, Client client, int noDossier, int premierAchat) throws SQLException {
        //calcul nombre total de places demandee a partir de Map
        int nbTotalPlacesDemandees = 0;
        for (String categorie : placesDemandees.keySet()) {
            nbTotalPlacesDemandees += placesDemandees.get(categorie);
        }
        detruireTickets(ds, nbTotalPlacesDemandees, client, noDossier, premierAchat);
    }

    public synchronized static void detruireTickets(DataSource ds, int nbPlacesAEnlever, Client client, int noDossier, int premierAchat) throws SQLException {
        try (Connection conn = ds.getConnection();
                PreparedStatement pstmtQuery = conn.prepareStatement(DETRUIRE_TICKETS);
                PreparedStatement pstmtQuery2 = conn.prepareStatement(DETRUIRE_Client);
                PreparedStatement pstmtQuery3 = conn.prepareStatement(DETRUIRE_Dossier)) {

            for (int noSerie = 0; noSerie < nbPlacesAEnlever; noSerie++) {
                pstmtQuery.setInt(1, noSerie + noSerieAvantInsertion + 1);
                pstmtQuery.executeUpdate();
            }

            pstmtQuery3.setInt(1, noDossier);
            pstmtQuery3.executeUpdate();

            if (premierAchat == 0) {

                pstmtQuery2.setString(1, client.getEMAIL());
                pstmtQuery2.executeUpdate();
            }
        }
    }

    /**
     * Detruit les tickets choisi "a la carte" (avec ajax).
     *
     * @param ds dataset
     * @param lesSieges liste des sieges choisis
     * @param client
     * @param noDossier
     * @param premierAchat
     * @throws SQLException
     */
    public synchronized static void detruireTicketsALaCarte(DataSource ds, List<Siege> lesSieges, Client client, int noDossier, int premierAchat) throws SQLException {
        detruireTickets(ds, lesSieges.size(), client, noDossier, premierAchat);
    }

    /**
     * crée dans la BD des tickets correspondant aux places demandées pour une
     * représentation donnée.
     *
     * @param ds
     * @param rep
     * @param placesDemandees
     */
    public static List<Ticket> creerTickets(DataSource ds, int noDossier, Representation rep, Map<String, Integer> placesDemandees, List<Categorie> prixParCategorie) throws SQLException {
        List<Ticket> lesTickets = new ArrayList<>();
        try (Connection conn = ds.getConnection();
                PreparedStatement pstmtQuery = conn.prepareStatement(TROUVERS_PLACES_LIBRES);
                PreparedStatement pstmtInsert = conn.prepareStatement(INSERT_TICKET);
                PreparedStatement pstmtNoSerieTicketAvantInsertion = conn.prepareStatement(TROUVER_MAX_NOSERIE)) {
            int j = 0;
            //recuperation de noserie du dernier ticket avant insertion
            ResultSet rs2 = pstmtNoSerieTicketAvantInsertion.executeQuery();
            rs2.next();
            noSerieAvantInsertion = rs2.getInt("maxSerie");

            // insertions des tickets
            for (String categorie : placesDemandees.keySet()) {

                // cherhcer les places dispo pour la catégorie
                pstmtQuery.setString(1, categorie);
                pstmtQuery.setTimestamp(2, rep.getDateRep());
                pstmtQuery.setInt(3, rep.getSpectacle().getNumS());
                pstmtQuery.setString(4, categorie);

                // chercher les places dispo
                ResultSet rs = pstmtQuery.executeQuery();

                try {
                    conn.setAutoCommit(false);
                    // le nombre de place demandées pour la catégorie
                    int nbPlacesDemandees = placesDemandees.get(categorie);
                    for (int i = 0; i < nbPlacesDemandees; i++) {

                        pstmtInsert.setInt(1, rep.getSpectacle().getNumS());
                        pstmtInsert.setTimestamp(2, rep.getDateRep());
                        rs.next();
                        pstmtInsert.setInt(3, rs.getInt("NOPLACE"));
                        pstmtInsert.setInt(4, rs.getInt("NORANG"));
                        pstmtInsert.setDate(5, new Date(System.currentTimeMillis()));
                        pstmtInsert.setInt(6, noDossier);

                        pstmtInsert.executeUpdate();

                        // recuperation du prix de la categorie choisie
                        int prix = 0;
                        for (Categorie categorie2 : prixParCategorie) {
                            if (categorie2.getNomC().equals(categorie)) {
                                prix = categorie2.getPrix();
                            }
                        }
                        //creation du ticket
                        j++;
                        lesTickets.add(new Ticket(noSerieAvantInsertion + j, new Date(System.currentTimeMillis()), rep, new Siege(rs.getInt("NORANG"), rs.getInt("NOPLACE"), new Categorie(categorie, prix))));
                    }
                    conn.commit();
                    conn.setAutoCommit(true);
                } catch (SQLException ex) {
                    conn.rollback();
                    throw ex;
                }
            }
        }
        return lesTickets;
    }

    private static final String ACHETER_PLACE = "INSERT INTO LESTICKETS (NOSERIE, "
            + "NOSPEC, DATEREP, NOPLACE, NORANG, DATEEMISSION, NODOSSIER) "
            + "VALUES(NOSERIE_SEQ.nextval, ? , ?, ? , ?, ?, ?)";

    /**
     * Cree des tickets quand le client à utiliser ajax pour selectionner ses
     * propres sieges
     *
     * @param ds
     * @param noDossier un entier correspondant au dossier à utiliser pour cet
     * achat
     * @param rep la représentation
     * @param listeSieges une liste des sieges selectionnes par le client
     * @throws SQLException
     */
    public static List<Ticket> creerTicketsALaCarte(DataSource ds, int noDossier, Representation rep, List<Siege> listeSieges, List<Categorie> prixParCategorie) throws SQLException {
        List<Ticket> lesTickets = new ArrayList<>();
        int j = 0;
        int prix = 0;
        try (Connection conn = ds.getConnection()) {
            try (PreparedStatement pstmt = conn.prepareStatement(ACHETER_PLACE);
                    PreparedStatement pstmtNoSerieTicketAvantInsertion = conn.prepareStatement(TROUVER_MAX_NOSERIE);) {
                //recuperation de noserie du dernier ticket avant insertion
                ResultSet rs2 = pstmtNoSerieTicketAvantInsertion.executeQuery();
                rs2.next();
                noSerieAvantInsertion = rs2.getInt("maxSerie");
                conn.setAutoCommit(false);
                for (Siege siege : listeSieges) {
                    pstmt.setInt(1, rep.getSpectacle().getNumS());
                    pstmt.setTimestamp(2, rep.getDateRep());
                    pstmt.setInt(3, siege.getNoPlace());
                    pstmt.setInt(4, siege.getNoRang());
                    pstmt.setDate(5, new Date(System.currentTimeMillis()));
                    pstmt.setInt(6, noDossier);
                    System.out.println("Achat place numero" + siege.getNoPlace() + " rang:" + siege.getNoRang());
//                    System.out.println("La requête : " + String.format(ACHETER_PLACE2, rep.getSpectacle().getNumS(), noPlace, noRang, noDossier));
                    for (Categorie categorie2 : prixParCategorie) {
                        if (categorie2.getNomC().equals(siege.getCategorie().getNomC())) {
                            prix = categorie2.getPrix();
                        }
                    }
                    j++;
                    pstmt.addBatch();
                    lesTickets.add(new Ticket(noSerieAvantInsertion + j, new Date(System.currentTimeMillis()), rep, new Siege(siege.getNoRang(), siege.getNoPlace(), new Categorie(siege.getCategorie().getNomC(), prix))));
                }
                pstmt.executeBatch();
                conn.commit();

            } catch (SQLException ex) {
                conn.rollback();
                throw ex;
            } finally {
                conn.setAutoCommit(true);
            }
        }
        return lesTickets;
    }

    private static final String PLACES_VENDUES = "select NOPLACE, NORANG, NOZONE from LESTICKETS "
            + "NATURAL JOIN LESSIEGES WHERE NOSPEC = ? AND "
            + "DateREP = ?";

    private static List<Place> placesVendues(DataSource ds, Representation rep) throws SQLException {
        try (Connection conn = ds.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(PLACES_VENDUES)) {
            pstmt.setInt(1, rep.getSpectacle().getNumS());
            pstmt.setTimestamp(2, rep.getDateRep());
            ResultSet rs = pstmt.executeQuery();
            List<Place> places = new ArrayList<>();
            while (rs.next()) {
                places.add(new Place(rs.getInt("noplace"), rs.getInt("norang"), rs.getInt("nozone")));
            }
            return places;
        }
    }

    public static String placesVenduesAsJSON(DataSource ds, Representation rep) throws SQLException {
        List<Place> places = placesVendues(ds, rep);

        StringWriter sw = new StringWriter();
        JsonGenerator gen = Json.createGenerator(sw);
        gen.writeStartObject()
                .writeStartArray("bookings");
        for (Place p : places) {
            gen.writeStartObject()
                    .write("rang", p.getRangCarte())
                    .write("colonne", p.getColonneCarte())
                    .writeEnd();
        }
        gen.writeEnd()
                .writeEnd();
        gen.close();
        return sw.toString();

    }
}
