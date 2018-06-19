package MyTheatre.controller;

import MyTheatre.DAO.DAOAchat;
import MyTheatre.Email.ConfirmationPDF;
import MyTheatre.Email.TicketBillet;
import MyTheatre.Email.MailSender;
import MyTheatre.model.Categorie;
import MyTheatre.model.Client;
import MyTheatre.model.MessagesAppli;
import MyTheatre.model.Representation;
import MyTheatre.model.Ticket;
import MyTheatre.model.Siege;
import MyTheatre.model.TypeMessagesAppli;
import com.google.zxing.WriterException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

@WebServlet(name = "AchatTickets", urlPatterns = {"/acheter"})
public class AchatTickets extends HttpServlet {

    @Resource(name = "jdbc/ufrma")
    private DataSource datasource;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, WriterException, SQLException {

        /* Création d'un nouveau client */
        String destinataireNom = request.getParameter("nom");
        String destinatairePrenom = request.getParameter("prenom");
        String destinataireEmail = request.getParameter("email");
        Client client = new Client(destinataireEmail, destinataireNom, destinatairePrenom);

        HttpSession session = request.getSession();
        session.setAttribute("client", client);

        //recup informations depuis la session
        Representation rep = (Representation) session.getAttribute("representation");
        int montant = Integer.parseInt((String) session.getAttribute("prixTotal"));
        Map<String, Integer> placesDemandees = (Map<String, Integer>) session.getAttribute("placesDemandees");
        String modeAchat = (String) session.getAttribute("modeAchat"); //parLot ou aLaCarte
        List<Siege> listeSieges = (List<Siege>) session.getAttribute("lesSieges");

        //pour email
        boolean ticketEnvoye = false;

        try {
            session.setAttribute("premierAchat", DAOAchat.creerclient(datasource, client));
            session.setAttribute("noDossier", DAOAchat.creerdossier(datasource, client, montant));

            if (modeAchat.equals("parLot")) {
                List<Ticket> lesTickets = DAOAchat.creerTickets(datasource, (int) session.getAttribute("noDossier"), rep, placesDemandees, (List<Categorie>) session.getAttribute("ListeCategorie"));
                session.setAttribute("lesTickets", lesTickets);
            }
            if (modeAchat.equals("aLaCarte")) {
                //a corriger actuellement void
                List<Ticket> lesTickets = DAOAchat.creerTicketsALaCarte(datasource, (int) session.getAttribute("noDossier"), rep, listeSieges, (List<Categorie>) session.getAttribute("ListeCategorie"));
                session.setAttribute("lesTickets", lesTickets);
            }

            // Message informatif 
            MessagesAppli message = new MessagesAppli("Votre commande a été enregistrée. L'équipe de MyTheatre vous remercie.", TypeMessagesAppli.info);
            request.setAttribute("message", message);

            // envoie de l'email
            //------------------------------------------------------------------
            // construction du fichier pdf correspondant au ticket
            //------------------------------------------------------------------
            // récupération le chemin absolu de l'image du logo
            ServletContext cntx = getServletContext();
            String logoImagePath = cntx.getRealPath("/images/image1.png");
            String logoImagePath2 = cntx.getRealPath("/images/logo_1fois15.png");

            //PDF des billets et de la confirmation de commande
            byte[] ticketBillet = TicketBillet.createPDFTicket_AsByteArray(client, (List<Ticket>) session.getAttribute("lesTickets"), logoImagePath2);
            byte[] confirmationPDF = ConfirmationPDF.createPDF_AsByteArray(client, rep, placesDemandees, logoImagePath, montant);

            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", getInitParameter("smtp_server"));
            props.put("mail.smtp.port", getInitParameter("smtp_port"));

            Session mailSession = Session.getInstance(props,
                    new javax.mail.Authenticator() {
                        @Override
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(getInitParameter("mail_user_name"), getInitParameter("mail_user_passwd"));
                        }
                    });

            // création du message
            String messageBody = "Félicitation pour votre commande sur le site MyTheatre.\n"
                    + "Votre représentation : " + rep.getSpectacle().getNom() + ", aura lieu " + rep.getDateRep() + ".\n"
                    + "Veuillez trouver ci-joint, une confirmation de commande et les tickets relatifs à cette dernière.\n";
            MailSender.sendMailWithAttachedFile(mailSession,
                    getInitParameter("sender"),
                    destinataireEmail,
                    getInitParameter("title"),
                    messageBody,
                    "confirmation.pdf",
                    "ticketBillet.pdf",
                    confirmationPDF,
                    ticketBillet,
                    "application/pdf");
            ticketEnvoye = true;

            //------------
            request.getRequestDispatcher("/accueil.jsp").forward(request, response);

        } catch (SQLException ex) {
            if (ex.getErrorCode() == 17289) {  // 17289 : java.sql.SQLException: Ensemble de résultats après la dernière ligne
                MessagesAppli message = new MessagesAppli("Il n'y a plus de place disponible pour cette représentation. Vous pouvez éventuellement acheter des places pour cette représentation au guichet.\n Sinon choisissez une autre représentation.", TypeMessagesAppli.info);
                request.setAttribute("message", message);
                request.getRequestDispatcher("/propositionRepresentations.jsp").forward(request, response);
            } else {
                MessagesAppli message = new MessagesAppli("Votre achat a échoué suite à un problème avec la base de donnée. Les places ne sont pas réservées. Votre compte n'a pas été débité. Veuiller réessayer plus tard.", TypeMessagesAppli.erreur);
                request.setAttribute("message", message);
                request.getRequestDispatcher("/accueil.jsp").forward(request, response);
                log(ex.getMessage(), ex);
            }

        } catch (MessagingException ex) {
            // le ticket n'a pas été envoyé
            // un message d'erreur sera envoyé à l'utilisateur
            MessagesAppli message = new MessagesAppli("Il y a eu un problème lors de l'envoi de l'email. Votre commande a été annulée.", TypeMessagesAppli.erreur);
            request.setAttribute("message", message);
            try {
                if (modeAchat.equals("parLot")) {
                    DAOAchat.detruireTicketsParLot(datasource, placesDemandees, client, (int) session.getAttribute("noDossier"), (int) session.getAttribute("premierAchat"));
                }
                if (modeAchat.equals("aLaCarte")) {
                    DAOAchat.detruireTicketsALaCarte(datasource, listeSieges, client, (int) session.getAttribute("noDossier"), (int) session.getAttribute("premierAchat"));
                }
                request.getRequestDispatcher("accueil.jsp").forward(request, response);
            } catch (SQLException ex2) {
                MessagesAppli message2 = new MessagesAppli("Il y a eu un problème lors de l'envoi de l'email. Il y a aussi eu un probleme avec a base de donnée. Contacter le webmaster.", TypeMessagesAppli.erreur);
                request.setAttribute("message", message);
                request.getRequestDispatcher("/accueil.jsp").forward(request, response);
            }

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
        try {
            processRequest(request, response);
        } catch (WriterException | SQLException ex) {
            Logger.getLogger(AchatTickets.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            processRequest(request, response);
        } catch (WriterException | SQLException ex) {
            Logger.getLogger(AchatTickets.class.getName()).log(Level.SEVERE, null, ex);
        }
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
