package MyTheatre.model;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Gestion des erreurs de l'application
 */
public class MessagesAppli {

    private String message;
    private TypeMessagesAppli type;

    public MessagesAppli(String message, TypeMessagesAppli type) {
        this.message = message;
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public TypeMessagesAppli getType() {
        return type;
    }

    /**
     * Créé un message pour un probleme avec la BD et l'ajoute dans l'objet
     * request
     * @param request
     * @param e SQL exception
     */
    public void ajouteProblemeBdDansRequest(HttpServletRequest request, SQLException e) {
        message = "Une erreur en rapport avec la base de donnée est survenue. Veuillez contacter le webmaster. (erreur " + e.getErrorCode() + ")";
        type = TypeMessagesAppli.erreur;
        request.setAttribute("message", this);
    }
}
