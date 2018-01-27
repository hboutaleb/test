package MyTheatre.Email;

import MyTheatre.model.Representation;
import MyTheatre.model.Client;
import MyTheatre.model.NosDates;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Map;
import javax.imageio.ImageIO;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

/**
 * Cette classe permet de générer un document PDF document correspondant à un
 * ticket pour une epreuve sportive en utilisant la librairie apache PDFBox
 * (https://pdfbox.apache.org/).
 *
 * @author Philippe Genoud (Université Grenoble Alpes - laboratoire LIG STeamer)
 */
public class ConfirmationPDF {

    /**
     * Crée en mémoire un document pdf correspondant à une confirmation de commande
     *
     * @param client le client
     * @param rep la representation
     * @param placeDemandees
     * @param montant
     * @param logoFileName le nom (chemin absolu) du fichier contenant l'image
     * logo à afficher en haut du ticket.
     * @return l'objet byte array contenant les données du fichier pdf
     * @throws IOException
     */
    public static byte[] createPDF_AsByteArray(Client client, Representation rep, Map<String, Integer> placeDemandees, String logoFileName, int montant) throws IOException {

        // Le tableau d'octets qui contiendra en mémoire le document pdf 
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try (PDDocument document = new PDDocument()) {
            // on utilise un try avec ressource pour être sûr que le document pdf est bien fermé

            PDPage page = new PDPage();
            document.addPage(page);

            try ( // Définition d'un objet contentStream destiné à "contenir" le document qui va être créée.
                    // On utilise un try avec ressources  pour s'assurer que l'objet contentStream sera bien fermé
                    PDPageContentStream contentStream = new PDPageContentStream(document, page)) {

                //--------------------------------------------------------------
                // lecture du fichier image du logo et écriture de celui-ci
                // dans le contentStream
                //--------------------------------------------------------------
                BufferedImage logo = ImageIO.read(new File(logoFileName));
                PDImageXObject logoImage = LosslessFactory.createFromImage(document, logo);
                contentStream.drawImage(logoImage, 12, 700);

                // création d'un nouvel objet font selectionnant l'une des polices de
                // base du PDF et association de celui-ci au contentStream
                PDFont font = PDType1Font.HELVETICA_BOLD;
                contentStream.setFont(font, 12);

                // affichage du nom du titulaire du billet
                contentStream.beginText();
                contentStream.newLineAtOffset(50, 680); // positionnement du curseur là où le texte doit être affiché
                contentStream.showText("Bonjour, Voici le récapitulatif de votre commande. A bientôt sur MyTheatre.");
                contentStream.endText();

                contentStream.beginText();
                contentStream.newLineAtOffset(50, 660); // positionnement du curseur là où le texte doit être affiché
                contentStream.showText("Vous tickets sont envoyés dans un deuxième email.");
                contentStream.endText();

                contentStream.beginText();
                contentStream.newLineAtOffset(140, 640);
                contentStream.showText("Nom du Client : " + client.getNOM());
                contentStream.endText();

                // affichage du prénom du titulaire du billet
                contentStream.beginText();
                contentStream.newLineAtOffset(140, 620);
                contentStream.showText("Prénom du Client : " + client.getPRENOM());
                contentStream.endText();

                // affichage du titre de la représentation
                contentStream.beginText();
                contentStream.newLineAtOffset(140, 600);
                contentStream.showText("Représentation : " + rep.getSpectacle().getNom());
                contentStream.endText();

                // affichage de la date de  la représentation
                Timestamp dateR = rep.getDateRep();
                contentStream.beginText();
                contentStream.newLineAtOffset(140, 580);
                contentStream.showText("Date : " + NosDates.dateEtTempsFormatddmmyyy_hhmm(dateR));
                contentStream.endText();

                // affichage du nombre total de places achetées
                int coordY = 560;
                for (String categorie : placeDemandees.keySet()) {
                    int nbPlaceDemandee = placeDemandees.get(categorie);
                    if (nbPlaceDemandee != 0) {
                        contentStream.beginText();
                        contentStream.newLineAtOffset(140, coordY);
                        contentStream.showText("Nombre de place(s) en " + categorie + " : " + nbPlaceDemandee);
                        contentStream.endText();
                        coordY -= 20;
                    }
                }

                // affichage du prix total
                //int montant = Integer.parseInt((String)"prixTotal");
                contentStream.beginText();
                contentStream.newLineAtOffset(140, coordY);
                contentStream.showText("Prix total : " + montant + " Euros");
                contentStream.endText();

                //--------------------------------------------------------------
                // Sauvegarde du document pdf dans l'objet ByteArrayOutputStream
                //--------------------------------------------------------------
                // avant toute chose l'objet ContentStream doit être fermé
                contentStream.close();

                document.save(out);
                return out.toByteArray();
            }
        }
    }
}
