package MyTheatre.controller;

import MyTheatre.DAO.DAORecherche;
import MyTheatre.model.MessagesAppli;
import MyTheatre.model.Representation;
import MyTheatre.model.TypeMessagesAppli;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

@WebServlet(urlPatterns = {"/RechercheRepresentations"})

public class RechercheRepresentation extends HttpServlet {

    @Resource(name = "jdbc/ufrma")
    private DataSource datasource;

    /**
     * Code exécuté quand on fait une recherche de représentations
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        // recuperation des parametres
        request.setCharacterEncoding("UTF-8"); //pour gestion accent avec POST
        String dateDebut = request.getParameter("dateDebut");
        String dateFin = request.getParameter("dateFin");
        String heureDebut = request.getParameter("heureDebut");
        String heureFin = request.getParameter("heureFin");
        String catage = request.getParameter("catage");
        if (heureDebut.isEmpty()) {
            dateDebut += " 00:00";
        } else {
            dateDebut += " " + heureDebut;
        }

        if (heureFin.isEmpty()) {
            dateFin += " 00:00";
        } else {
            dateFin += " " + heureFin;
        }

        //conversion des dates
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dateDebutConv = LocalDateTime.parse(dateDebut, formatter);
        LocalDateTime dateFinConv = LocalDateTime.parse(dateFin, formatter);

        List<Representation> lesRepresentations;
        //interrogation bd
        try {
            if (catage.equals("sans catégorie")) {
                lesRepresentations = DAORecherche.getSpectaclesSurCritereDate(datasource, dateDebutConv, dateFinConv);
            } else {
                lesRepresentations = DAORecherche.getSpectaclesSurCritereAgeEtDate(datasource, dateDebutConv, dateFinConv, catage);
            }
            HttpSession session = request.getSession();
            session.setAttribute("lesRepresentations", lesRepresentations);

            if (lesRepresentations.isEmpty()) {
                request.getRequestDispatcher("/resultatDuneRechercheNulle.jsp").forward(request, response);
            } else {
                request.getRequestDispatcher("/propositionRepresentations.jsp").forward(request, response);
            }
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
