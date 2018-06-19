<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.time.Instant"%>
<%@page import="java.util.Date"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%// Paramètres utilisés en cas de clique sur bouton retour
    String dateDebut = request.getParameter("dateDebut");
    String dateFin = request.getParameter("dateFin");
    String heureDebut = request.getParameter("heureDebut");
    String heureFin = request.getParameter("heureFin");
    SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd");
    Date datedeb = new Date();
    String dated = dateFormat.format(datedeb);
%>

<section>
    <h1>
        Veuillez s'il vous plait choisir une date et un horaire qui vous conviennent.
    </h1> 
    <form action="RechercheRepresentations" method="post">
        <p class="paddingPourForm largeur100p"> 
            <label class="labelGauche"> Date du : </label>
            <input class="largeur15p" type="date" name="dateDebut" value=
                   <% if (dateDebut != null) {%> "<%= dateDebut%>" /> 
            <% } else {%> <%= dated%> /> <% }%>

            <label class="labelSuivant"> à : </label>
            <input class="largeur15p" type="time" name ="heureDebut" value=
                   <% if (heureDebut != null) {%> "<%= heureDebut%>" /> 
            <% } else { %> "08:00" /> <% }%>
        </p>
        <p class="paddingPourForm largeur100p">
            <label class="labelGauche"> Au : </label>
            <input class="largeur15p" type="date" name ="dateFin" value=
                   <% if (dateFin != null) {%> "<%= dateFin%>" /> 
            <% } else { %> "2018-05-02" /> <% }%>

            <label class="labelSuivant"> à : </label>  </td>
            <input class="largeur15p" type="time" name ="heureFin" value=
                   <% if (heureFin != null) {%> "<%= heureFin%>" /> 
            <% } else { %> "08:00" /> <% }%>

        </p>
        <p class="paddingPourForm">
            <label class="labelGauche"> Catégorie d'âge : </label>
            <select name="catage">
                <option value="sans catégorie">sans catégorie</option>
                <option value="tout public">Tout public</option>
                <option value="enfant 0 à 12 ans">Enfant 0 à 12 ans</option>
                <option value="enfant 12 à 14 ans">Enfant 12 à 14 ans</option>
                <option value="enfant 14 à 16 ans">Enfant 14 à 16 ans</option>
                <option value="enfant 16 à 18 ans">Enfant 16 à 18 ans</option>
                <option value="adulte +18 ans">Adulte de plus de 18 ans</option>
            </select>
        </p>

        <input class="droite" type="submit" value="Valider"/>
    </form>
</section>