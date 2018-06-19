<%@page import="MyTheatre.model.Representation"%>
<%@page import="java.sql.Timestamp"%>
<%@page import="java.time.LocalDateTime"%>
<%@page import="java.time.format.DateTimeFormatter"%>
<%@page import="MyTheatre.model.Spectacle"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%    List<Representation> lesRepresentations = (List<Representation>) session.getAttribute("lesRepresentations");
    String dateDebut = request.getParameter("dateDebut");
    String dateFin = request.getParameter("dateFin");
    String heureDebut = request.getParameter("heureDebut");
    String heureFin = request.getParameter("heureFin");
%>

<h1>Résultat de la recherche </h1>

<div id="rech"> Suite à votre recherche, nous avons trouvé <span class="gras"> <%=lesRepresentations.size()%> </span> représentations </div><br>

<table style="width:100%">
    <tr>
        <th>Titre</th>
        <th>Date & Heure</th>      
        <th>Catégorie d'âge</th>
    </tr>
    <%
        int i = 1;
        for (Representation representation : lesRepresentations) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime dateETheure = representation.getDateRep().toLocalDateTime();
            String dateAffiche = dateETheure.format(formatter);
            Spectacle spectacle = representation.getSpectacle();
    %>
    <tr>
        <td> <a class="lien" href="/MyTheatre/DetailRepresentation?noSpec=<%= spectacle.getNumS()%>&dateRep=<%=dateAffiche%>"> <%= spectacle.getNom()%> </a> 
            <% if (representation.getNbTotalPlacesDispo() < 70) { %>
            <div class="legende">
                <img class="pictogramme" src="images/pasPlaceDispoEnLigne.jpg">
                <span class="legendeTexte"> Plus de place disponible à la vente en ligne </span>
            </div>
            <% };%>
        </td>
        <td> <%= dateAffiche%> </td>
        <td> <%= spectacle.getCatAge()%> </td>
    </tr>
    <%
            i++;
        }
    %>
</table> 

<form action="rechercheRepresentations.jsp" method="post">
    <input type="hidden" name="dateDebut" value="<%= request.getParameter("dateDebut")%>">
    <input type="hidden" name="dateFin" value="<%= request.getParameter("dateFin")%>">
    <input type="hidden" name="heureDebut" value="<%= request.getParameter("heureDebut")%>">
    <input type="hidden" name="heureFin" value="<%= request.getParameter("heureFin")%>">
    <input class="droite" type="submit" value="Retour recherche">

</form>