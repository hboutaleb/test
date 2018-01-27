package MyTheatre.controller;

import MyTheatre.DAO.DAODetailRepresentation;
import MyTheatre.model.MessagesAppli;
import MyTheatre.model.Representation;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

@WebServlet(name = "Recapitulatif", urlPatterns = {"/Recapitulatif"})
public class Recapitulatif extends HttpServlet {

    @Resource(name = "jdbc/ufrma")
    private DataSource datasource;

    /**
     * On passe par cette servlet uniquement quand on commande par lot (pas de ajax)
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // preparation des parametres
        String dateRep = request.getParameter("dateRep");
        String noSpec = request.getParameter("numS");
        String prixtotal=request.getParameter("prixTotal");
         HttpSession session = request.getSession();
        session.setAttribute("prixTotal", prixtotal);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dateRepConv = LocalDateTime.parse(dateRep, formatter);

        Map<String, Integer> placesDemandees = new HashMap<>();
        if (request.getParameter("orchestre") != null) {
            placesDemandees.put("orchestre", Integer.parseInt(request.getParameter("orchestre")));
        }
        if (request.getParameter("1er balcon") != null) {
            placesDemandees.put("1er balcon", Integer.parseInt(request.getParameter("1er balcon")));
        }
        if (request.getParameter("2nd balcon") != null) {
            placesDemandees.put("2nd balcon", Integer.parseInt(request.getParameter("2nd balcon")));
        }
        if (request.getParameter("poulailler") != null) {
            placesDemandees.put("poulailler", Integer.parseInt(request.getParameter("poulailler")));
        }
        //on fixe le mode d'achat dans la session pour la partie achat
        session.setAttribute("modeAchat", "parLot"); //parLot ou aLaCarte

        try {
            // stockage des infos sur la representation et places demandee en vue du paiement
            Representation representation = DAODetailRepresentation.getDetailRepresentation(datasource, dateRepConv, noSpec);
           
            session.setAttribute("representation", representation);
            session.setAttribute("placesDemandees", placesDemandees);
            //  on va au paiement
            request.setAttribute("noheader", true);
            request.getRequestDispatcher("/recapitulatifEtPaiement.jsp").forward(request, response);

        } catch (SQLException e) {
            MessagesAppli message = new MessagesAppli(null, null);
            message.ajouteProblemeBdDansRequest(request, e);
            request.getRequestDispatcher("/accueil.jsp").forward(request, response);
        }
    }

// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
