/*
 * Copyright (C) 2017 Philippe GENOUD - Université Grenoble Alpes - Lab LIG-Steamer
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package MyTheatre.controller;

import MyTheatre.model.Siege;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

/**
 *
 * @author Philippe GENOUD - Université Grenoble Alpes - Lab LIG-Steamer
 */
@WebServlet(name = "AcheterPlacesCtrler", urlPatterns = {"/acheterPlaces"})
public class AchatPlacesAjax extends HttpServlet {

    @Resource(name = "jdbc/ufrma")
    DataSource ds;

    /**
     * On passe ici que quand on utilise AJAX
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String prixTotal = request.getParameter("prixTotal");
        String[] places = request.getParameterValues("place");

        int[] rangPlaces = new int[places.length];   // rang des places sur la carte
        int[] colPlaces = new int[places.length];   // colonne des places sur la carte
        List<Siege> lesSieges = new ArrayList<>();

        //creation liste des sieges demandees (stocke en session)
        for (int i = 0; i < places.length; i++) {
            String[] position = places[i].split("_");
            rangPlaces[i] = Integer.parseInt(position[0]);
            colPlaces[i] = Integer.parseInt(position[1]);

            Siege siege = new Siege(rangPlaces[i], colPlaces[i]);
            lesSieges.add(siege);
        }

        //creation nombre de siege demandees par categorie pour le recapitulatif (stocke en session)
        Map<String, Integer> placesDemandees = new HashMap<>();
        placesDemandees.put("orchestre", Siege.nbSiegesChoisisPourUneCategorie(lesSieges, "orchestre"));
        placesDemandees.put("1er balcon", Siege.nbSiegesChoisisPourUneCategorie(lesSieges, "1er balcon"));
        placesDemandees.put("2nd balcon", Siege.nbSiegesChoisisPourUneCategorie(lesSieges, "2nd balcon"));
        placesDemandees.put("poulailler", Siege.nbSiegesChoisisPourUneCategorie(lesSieges, "poulailler"));

        HttpSession session = request.getSession();
        session.setAttribute("lesSieges", lesSieges);
        session.setAttribute("placesDemandees", placesDemandees);
        session.setAttribute("prixTotal", prixTotal);

        request.setAttribute("noheader", true);
        request.getRequestDispatcher("recapitulatifEtPaiement.jsp").forward(request, response);

        // on fixe le mode d'achat "aLaCarte" pour la partie paiement 
        session.setAttribute("modeAchat", "aLaCarte"); //parLot ou aLaCarte

//        try {
//            String[] places = request.getParameterValues("place");
//            int[] rangPlaces= new int[places.length];   // rang des places sur la carte
//            int[] colPlaces = new int[places.length];   // colonne des places sur la carte
//            // rangPlace[i],colPlaces[i] --> position sur la carte de la ième place achetée
//            
//            for (int i = 0; i < places.length; i++) {
//                String[] position = places[i].split("_");
//                rangPlaces[i] = Integer.parseInt(position[0]);
//                colPlaces[i] = Integer.parseInt(position[1]);
//            }
//            HttpSession session = request.getSession();
//            Representation rep = (Representation) session.getAttribute("representation");
//            PlaceDAO.acheterPlace(ds,rep, rangPlaces, colPlaces);
//            request.getRequestDispatcher("achat.jsp").forward(request, response);
//        } catch (SQLException ex) {
//            throw new ServletException(ex.getMessage(), ex);
//        }
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
