/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MyTheatre.Email;

import MyTheatre.model.Client;
import MyTheatre.model.NosDates;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import MyTheatre.model.Ticket;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Hashtable;
import java.util.List;
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
public class TicketBillet {

    // variable de classe pour calculer le numéro du ticket. 
    // cette variable compte le nombre de tickets qui ont été imprimés
    // variable de classe pour calculer le numéro du ticket. 
    // cette variable compte le nombre de tickets qui ont été imprimés
    private static int nbTickets = 0;
    private static Object session;

    public static BufferedImage createQRC(String codeText, int size) throws WriterException {
        String fileType = "png";
        Hashtable<EncodeHintType, ErrorCorrectionLevel> hintMap = new Hashtable<EncodeHintType, ErrorCorrectionLevel>();
        hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix byteMatrix = qrCodeWriter.encode(codeText, BarcodeFormat.QR_CODE, size, size, hintMap);
        int CrunchifyWidth = byteMatrix.getWidth();
        BufferedImage image = new BufferedImage(CrunchifyWidth, CrunchifyWidth,
                BufferedImage.TYPE_INT_RGB);
        image.createGraphics();

        Graphics2D graphics = (Graphics2D) image.getGraphics();
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, CrunchifyWidth, CrunchifyWidth);
        graphics.setColor(Color.BLACK);

        for (int i = 0; i < CrunchifyWidth; i++) {
            for (int j = 0; j < CrunchifyWidth; j++) {
                if (byteMatrix.get(i, j)) {
                    graphics.fillRect(i, j, 1, 1);
                }
            }
        }
        return image;

    }

    /**
     * Crée en mémoire un document pdf correspondant à un ticket électronique
     *
     * @param Tickets : liste des tickets
     * @param logoFilename le nom (chemin absolu) du fichier contenant l'image,
     * logo à afficher en haut du ticket.
     * @return l'objet byte array contenant les données du fichier pdf
     * @throws IOException
     * @throws com.google.zxing.WriterException
     */
    public static byte[] createPDFTicket_AsByteArray(Client client, List<Ticket> listeTickets, String logoFilename) throws IOException, WriterException {
        // Le tableau d'octets qui contiendra en mémoire le document pdf 
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        //calcul du nombre de pages a créer a raison de 6 tickets par page
        int nbPages = listeTickets.size() / 6;
        int reste = listeTickets.size() % 6;
        if (reste > 0) {
            nbPages += 1;
        }

        int indexTicket = 0;
        int dernierTicket = listeTickets.size()-1;
        
        try (PDDocument document = new PDDocument()) {
            // on utilise un try avec ressource pour être sûr que le document pdf est bien fermé
            for (int nopage = 1; nopage <= nbPages; nopage++) {
                PDPage page = new PDPage();
                document.addPage(page);  //creation de la page            
                try ( // Définition d'un objet contentStream destiné à "contenir" le document qui va être créée.
                        // On utilise un try avec ressources  pour s'assurer que l'objet contentStream sera bien fermé
                        PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                    //--------------------------------------------------------------
                    // lecture du fichier image du logo et écriture de celui-ci
                    // dans le contentStream
                    //--------------------------------------------------------------
                    BufferedImage logo = ImageIO.read(new File(logoFilename));
                    PDImageXObject logoImage = LosslessFactory.createFromImage(document, logo);
                    contentStream.drawImage(logoImage, 160, 700);

                    //--------------------------------------------------------------
                    // Définition d'un texte donnant le numéro du ticket, le nom du
                    // titulaire, le nom et la date de l'épreuve, le nombre de places.
                    //--------------------------------------------------------------
                    // création d'un nouvel objet font selectionnant l'une des polices de
                    // base du PDF et association de celui-ci au contentStream
                    PDFont font = PDType1Font.HELVETICA_BOLD;
                    contentStream.setFont(font, 12);

                    contentStream.beginText();
                    contentStream.newLineAtOffset(50, 680); // positionnement du curseur là où le texte doit être affiché
                    contentStream.showText("Bonjour Mr " + client.getNOM() + " " + client.getPRENOM() + ". Vous trouverez ci dessus les billets commandés sur MYTHEATRE."); // affichage du texte         
                    contentStream.endText();

                    contentStream.beginText();
                    contentStream.newLineAtOffset(50, 660); // positionnement du curseur là où le texte doit être affiché
                    contentStream.showText("Bon spectacle !");
                    contentStream.endText();

                    int posY = 630;
                    int dernierTicketdeLaPage = indexTicket +5;
                    while ((indexTicket <= dernierTicket) && (indexTicket <= dernierTicketdeLaPage) ){
                        Ticket unticket = listeTickets.get(indexTicket);

                        // affichage du numéro de serie
                        contentStream.beginText();
                        contentStream.newLineAtOffset(140, posY); // positionnement du curseur là où le texte doit être affiché
                        contentStream.showText("Billet : " + unticket.getNoSerie()); // affichage du texte
                        contentStream.endText();

                        //affichage du titre de la représentation
                        contentStream.beginText();
                        contentStream.newLineAtOffset(140, posY - 20);
                        contentStream.showText("Spectacle : " + unticket.getRepresentation().getSpectacle().getNom());
                        contentStream.endText();

                        // affichage de la date de la représentation
                        contentStream.beginText();
                        contentStream.newLineAtOffset(140, posY - 40);

                        Timestamp date = unticket.getRepresentation().getDateRep();
                        contentStream.showText("Le : " + NosDates.dateAuFormatddmmyyy(date) + " à " + NosDates.tempsAuFormathhmm(date));
                        contentStream.endText();

                        // affichage Categorie + Rang + Siege               
                        contentStream.beginText();
                        contentStream.newLineAtOffset(140, posY - 60);
                        contentStream.showText("Categorie   Rang   Siège ");
                        contentStream.endText();

                        // affichage Categorie + Rang + Siege               
                        contentStream.beginText();
                        contentStream.newLineAtOffset(140, posY - 70);
                        contentStream.showText("-----------------------------------");
                        contentStream.endText();

                        // affichage Categorie + Rang + Siege               
                        contentStream.beginText();
                        contentStream.newLineAtOffset(140, posY - 80);
                        contentStream.showText(unticket.getSiege().getCategorie().getNomC() + "     " + unticket.getSiege().getNoRang() + "         "
                                + unticket.getSiege().getNoPlace());
                        contentStream.endText();
                //---------------------------------------------------------------------------
                        // ajout de l'image avec le QR Code

                        //---------------------------------------------------------------------------
                        // appel du web service qrickit.com pour récupérer en une image d'un code QRC
                        // et afficher celle-ci dans le document PDF.
                        // Pour plus d'informations sur l'API de ce webservice et les différents 
                        // paramètres possibles: http://qrickit.com/qrickit_apps/qrickit_api.php
                        // Mais sur les machine de l'ufr cela ne marche pas à cause du proxy....
                        // C'est pour contourner ce problème que nous avaons utilisé la librairie zxing
                        // le texte à encoder avec le QR Code
                        String data = "Ticket N°" + String.format("%06d", unticket.getNoSerie());

                        // attention ce texte devant être passé en paramètre d'une requête HTTP GET
                        // il doit être "url encodé" (pour gérer correctement les espaces et caractères non ASCII).
                        // data = URLEncoder.encode(data, "UTF-8");
                        // appel du service web pour obtenir l'image du QR code
                        // BufferedImage awtImage = ImageIO.read(new URL(
                        // "http://qrickit.com/api/qr?d=" + data
                        //  + "&qrsize=100&t=p&e=m"));
                        // écriture de l'image dans le fichier pdf
                        PDImageXObject ximage = LosslessFactory.createFromImage(document, createQRC(data, 100));
                        contentStream.drawImage(ximage, 20, posY - 100);
                        posY = posY - 105;
                        indexTicket += 1;
                    }
                    contentStream.beginText();
                        contentStream.newLineAtOffset(500, 20);
                        contentStream.showText("page " + nopage + " sur " + nbPages );
                        contentStream.endText();
                    contentStream.close();
                }
            }
            //--------------------------------------------------------------
            // Sauvegarde du document pdf dans l'objet ByteArrayOutputStream
            //--------------------------------------------------------------
            // avant toute chose l'objet ContentStream doit être fermé
            document.save(out);
            return out.toByteArray();
        }
    }
}
