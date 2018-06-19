<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div>
  <h1>Nouvelle recherche?</h1>
  <p>
    Aucun résultat ne répond à votre recherche.Veuillez recommencer s'il vous plaît.
  </p>
</div>
<br>
<br>

<form action="rechercheRepresentations.jsp" method="post">
  <input type="hidden" name="dateDebut" value="<%= request.getParameter("dateDebut")%>">
  <input type="hidden" name="dateFin" value="<%= request.getParameter("dateFin")%>">
  <input type="hidden" name="heureDebut" value="<%= request.getParameter("heureDebut")%>">
  <input type="hidden" name="heureFin" value="<%= request.getParameter("heureFin")%>">
  <input class="droite" type="submit" value="Retour recherche">

</form>