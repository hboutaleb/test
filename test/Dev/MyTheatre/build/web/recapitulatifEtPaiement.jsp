<%@page import="java.util.Map"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@page import="MyTheatre.model.Representation"%>
<%@page import="java.time.LocalDateTime"%>
<%@page import="java.time.format.DateTimeFormatter"%>
<%@page import="MyTheatre.model.Spectacle"%>

<!DOCTYPE html>
<html>
  <%  Representation rep = (Representation) session.getAttribute("representation");
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
      LocalDateTime dateETheure = rep.getDateRep().toLocalDateTime();
      String dateAffiche = dateETheure.format(formatter);

      Map<String, Integer> placesDemandees = (Map<String, Integer>) session.getAttribute("placesDemandees");
      Integer nbPlacePoulailler = placesDemandees.get("poulailler");
      Integer nbplaceBalcon1 = placesDemandees.get("1er balcon");
      Integer nbplaceBalcon2 = placesDemandees.get("2nd balcon");
      Integer nbplaceOrchestre = placesDemandees.get("orchestre");
      String prixTotal = request.getParameter("prixTotal");
  %>

  <div id="reca">
    <h1>Récapitulatif</h1>
    <p class="paddingPourForm"> Vous êtes en train de passer la commande: </p>
    <p class="decalageGauche"> Titre du spectacle :<a> <%= rep.getSpectacle().getNom()%> </a> </p>
    <p class="decalageGauche"> Date de représentation :<a> <%= dateAffiche%> </a> </p>  
  </div>

  <div id="reca">
    <p class="paddingPourForm"> Catégorie et nombre de places choisies: </p> 
    <%
        if (nbPlacePoulailler != null  && nbPlacePoulailler != 0) {%>
    <p class="decalageGauche">   Poulailler : <a> <%= nbPlacePoulailler%> </a>  place(s) demandée(s)</p>
    <%
        }
        if (nbplaceBalcon1 != null && nbplaceBalcon1 != 0 ) {%>
    <p class="decalageGauche" >Balcon 1 : <a> <%= nbplaceBalcon1%> </a> place(s) demandée(s) </p>
    <%
        }
        if (nbplaceBalcon2 != null && nbplaceBalcon2 !=0 ) {%>
    <p class="decalageGauche">Balcon 2 : <a> <%= nbplaceBalcon2%> </a> place(s) demandée(s) </p>
    <%
        }
        if (nbplaceOrchestre != null && !nbplaceOrchestre.equals("0")) {%>
    <p class="decalageGauche"> Orchestre : <a> <%= nbplaceOrchestre%> </a> place(s) demandée(s) </p>
    <%
        }
    %>

    <p class="paddingPourForm">Prix total : <span class="gras"> <%= prixTotal%> € </span> </p>

  </div>

  <div id=reca">
    <h1> Paiement </h1>
    <p class="paddingPourForm">Veuillez entrer vos coordonnées pour le paiement par carte bancaire </p>

    <form action="acheter">
      <p class="paddingPourForm">  
        <label class="labelGauche"> Nom: </label>
        <input type="text" name ="nom" value=""/>
        </</p>
      <p class="paddingPourForm">
        <label class="labelGauche"> Prénom: </label> 
        <input type="text" name ="prenom" value=""/>
      </p>
      <p class="paddingPourForm">
        <label class="labelGauche"> Email: </label> 
        <input type="email" name ="email" value=""/>
      </p>
      <p> 
        <input class="droite" type="submit" value="Paiement"/> </p>
      </p>
    </form>
    <form action="DetailRepresentation">
      <input type="hidden" name="noSpec" value="<%=rep.getSpectacle().getNumS()%>" >
      <input type="hidden" name="dateRep" value="<%=dateAffiche%>">
      <input class="droite" type="submit" value="Annulation"/> </p>
    </form>
    <p></p>
  </div>

</html>
