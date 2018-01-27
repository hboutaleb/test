<%@page import="MyTheatre.model.Photo"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<script src="./js/touteAppli.js" type="text/javascript"></script>

<%@page import="MyTheatre.model.ListePlacesDispo"%>
<%@page import="MyTheatre.model.Representation"%>
<%@page import="java.time.LocalDateTime"%>
<%@page import="java.time.format.DateTimeFormatter"%>
<%@page import="MyTheatre.model.Categorie"%>
<%@page import="MyTheatre.model.Spectacle"%>

<section>
  <h1>
    Détail de la représentation 
  </h1>
  <%    
         Representation representation = (Representation) session.getAttribute("representation");
      Spectacle spec = representation.getSpectacle();
      List<Categorie> listeCategorie = (List<Categorie>) session.getAttribute("ListeCategorie");
  %>

  <div class="floatLeft photoDetail maxWidth500p" >
    <% int idImage = 1;
        for (Photo unePhoto : representation.getSpectacle().getLesphotos()) {%>
    <div id="image<%=idImage%>" class="cache largeur100p" 
         <% if (idImage == 1) { %> style="display:block;" <% }%> >
      <img class="floatLeft pointeurMain largeur100p hauteur300px" src="images/<%=unePhoto.getContenu()%>" onclick="imageSuivante(<%= representation.getSpectacle().getLesphotos().size()%>)">    
    </div>
    <% idImage++;
          } %>
  </div>

  <form action="Recapitulatif">
    <%
        if (representation.getNbTotalPlacesDispo() <= 70) {
    %>
    <div>
      <p id="infodetail" class="floatLeftAndClear messageInformation"> Plus de place disponible pour la vente en ligne. <br> Contacter le guichet. </p>
    </div>
    <%
    } else {
    %>
    <table class="floatLeftAndClear largeur50p maxWidth500p">
        <tr class="largeur100p hauteur25px">
          <td colspan="3"> <span class="gras"> Choix de siège(s) par catégorie : </span> </td>
      </tr>
      <tr class="largeur100p hauteur25px">
        <th> Catégorie </th>
        <th> Nb de places </th>
        <th> € / unité </th>
      </tr>
      <% int i = 0;
          for (Categorie categorie : listeCategorie) {
      %>
      <tr class="largeur100p hauteur25px">
        <td> <%= categorie.getNomC()%> </td>
        <td class="largeur90p"> <input class="largeur100p" id="cat<%=i%>" name="<%= categorie.getNomC()%>"type="number" min="0" value="0" 
                    <%
                        if (representation.getNbPlacesDispos(categorie.getNomC()) == 0) {
                            out.print(" disabled ");
                        } else {
                            out.println(" max=" + representation.getNbPlacesDispos(categorie.getNomC()) + " ");
                        }
                    %>
                    oninput="calcul();"> </td>
        <td id="prix<%=i%>" > <%= categorie.getPrixStr()%> </td>
      </tr>

      <%
              i++;
          }
      %>
      <tr hauteur20px>
        <td colspan="2"> Prix total (€): </td>
        <td id="prixTotal" class="gras"> 0  </td>
      </tr> 
      <tr class="largeur100p hauteur25px">
        <td colspan="3"> <span class="gras"> Autre option : </span> <br>  <span> Choisissez vos sièges sur le </span> <a href="achatPlaces.jsp" class="lien">plan de salle </a></td>
       </tr> 
    </table>

    <%
        }
    %>
    <div id="divDetail">
      <div>
        <h1><%= spec.getNom()%> </h1>
        <h1> <%= spec.getGenre()%> | <%= spec.getCatAge()%>  | Durée: <%= spec.getDuree()%> min </h1>
        <%
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime dateETheure = representation.getDateRep().toLocalDateTime();
            String dateAffiche = dateETheure.format(formatter);
        %>    
        <h1> Date de représentation : <%= dateAffiche%> </h1>
      </div>
      <h2>Résumé: </h2>
      <p> <%= spec.getResume()%> </p>
    </div>    
    <div id="boutonDetail">
      <input type="hidden" id="formulairePrixTotal" name="prixTotal" value="0">
      <input type="hidden" name="numS" value="<%= spec.getNumS()%>">
      <input type="hidden" name="dateRep" value="<%= dateAffiche%>">
      <input id="boutonPayer" type="submit" value="Acheter" disabled>
      </form>

      <form action="propositionRepresentations.jsp">
        <input  type="submit" value="Retour">
      </form>   
    </div>
      
     
      
</section>

<script>
    function calcul() {
        var total = 0;
        for (j = 0; j < <%=listeCategorie.size()%>; j++) {
            id = "cat" + j.toString();
            prix = "prix" + j.toString();
            total += document.getElementById(id).value * document.getElementById(prix).innerText;
        }
        document.getElementById("prixTotal").innerHTML = total.toString();
        document.getElementById("formulairePrixTotal").value = total.toString();
        if (document.getElementById("prixTotal").innerHTML !== "0") {
            document.getElementById("boutonPayer").disabled = false;
        }
        else {
            document.getElementById("boutonPayer").disabled = true;
        }
    }
</script>


