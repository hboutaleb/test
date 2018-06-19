<%@page import="MyTheatre.model.NosDates"%>
<%@page import="MyTheatre.model.Representation"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% Representation rep = (Representation) session.getAttribute("representation");
String date = NosDates.dateAuFormatddmmyyy(rep.getDateRep());
String temps = NosDates.tempsAuFormathhmm(rep.getDateRep());

%>
<div class="wrapper">
  <h1>
  Choisissez vos sièges pour le spectacle "<%= rep.getSpectacle().getNom() %>" du <%=date %> à <%=temps %>
  </h1>
  <div id="map-container">
    <div id="seat-map">
      <div class="front-indicator">Scène</div>
    </div>
    <div id="commande">
      <div id="legend"></div>
      <h3>Votre sélection</h3>
      <p>Nbre de places: <strong><span id="nbplaces">0</span></strong></p>
      <p>Prix Total: <strong><span id="prixtotal">0</span> €</strong></p>
      <input type="submit" id="achatBtn" value="Acheter" style="width:100% " >
    </div>
  </div>
</div>
<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
<script src="js/jQuery-Seat-Charts/jquery.seat-charts.min.js" type="text/javascript"></script>
<script>
    var firstSeatLabel = 1;
    $(document).ready(function () {
        var $detailCategorie = $('#detail-categories'),
                $nbPlaces = $('#nbplaces'),
                $prixTotal = $('#prixtotal'),
                sc = $('#seat-map').seatCharts({
            map: [
                '_AAAAAAAAAA__AAAAAAAAAA_',
                '_AAAAAAAAAA__AAAAAAAAAA_',
                '_AAAAAAAAAA__AAAAAAAAAA_',
                '_AAAAAAAAAA__AAAAAAAAAA_',
                '_AAAAAAAAAA__AAAAAAAAAA_',
                '_AAAAAAAAAA__AAAAAAAAAA_',
                '_AAAAAAAAAA__AAAAAAAAAA_',
                '_AAAAAAAAAA__AAAAAAAAAA_',
                '_AAAAAAAAAA__AAAAAAAAAA_',
                '_AAAAAAAAAA__AAAAAAAAAA_',
                '_AAAAAAAAAA__AAAAAAAAAA_',
                '_AAAAAAAAAA__AAAAAAAAAA_',
                '_AAAAAAAAAA__AAAAAAAAAA_',
                '_AAAAAAAAAA__AAAAAAAAAA_',
                '_AAAAAAAAAA__AAAAAAAAAA_',
                '_AAAAAAAAAA__AAAAAAAAAA_',
                '_AAAAAAAAAA__AAAAAAAAAA_',
                '_AAAAAAAAAA__AAAAAAAAAA_',
                '_AAAAAAAAAA__AAAAAAAAAA_',
                '________________________',
                '________________________',
                '_BBBBBBBBBB__BBBBBBBBBB_',
                '_BBBBBBBBBB__BBBBBBBBBB_',
                '_BBBBBBBBBB__BBBBBBBBBB_',
                '_BBBBBBBBBB__BBBBBBBBBB_',
                '_BBBBBBBBBB__BBBBBBBBBB_',
                '_BBBBBBBBBB__BBBBBBBBBB_',
                '_BBBBBBBBBB__BBBBBBBBBB_',
                '_BBBBBBBBBB__BBBBBBBBBB_',
                '_BBBBBBBBBB__BBBBBBBBBB_',
                '_BBBBBBBBBB__BBBBBBBBBB_',
                '________________________',
                '________________________',
                '_CCCCCCCCCC__CCCCCCCCCC_',
                '_CCCCCCCCCC__CCCCCCCCCC_',
                '_CCCCCCCCCC__CCCCCCCCCC_',
                '_CCCCCCCCCC__CCCCCCCCCC_',
                '_CCCCCCCCCC__CCCCCCCCCC_',
                '_CCCCCCCCCC__CCCCCCCCCC_',
                '_CCCCCCCCCC__CCCCCCCCCC_',
                '_CCCCCCCCCC__CCCCCCCCCC_',
                '_CCCCCCCCCC__CCCCCCCCCC_',
                '_CCCCCCCCCC__CCCCCCCCCC_',
                '________________________',
                '________________________',
                '_DDDDDDDDDD__DDDDDDDDDD_',
                '_DDDDDDDDDD__DDDDDDDDDD_',
                '_DDDDDDDDDD__DDDDDDDDDD_',
                '_DDDDDDDDDD__DDDDDDDDDD_',
                '_DDDDDDDDDD__DDDDDDDDDD_',
                '_DDDDDDDDDD__DDDDDDDDDD_',
                '_DDDDDDDDDD__DDDDDDDDDD_',
                '_DDDDDDDDDD__DDDDDDDDDD_',
                '_DDDDDDDDDD__DDDDDDDDDD_',
                '_DDDDDDDDDD__DDDDDDDDDD_',
                '_DDDDDDDDDD__DDDDDDDDDD_'
            ],
            seats: {
                A: {
                    price: 50,
                    classes: 'orchestre', // votre classe CSS spécifique
                    category: 'A'
                },
                B: {
                    price: 40,
                    classes: 'ba1con1', // votre classe CSS spécifique
                    category: 'B'
                },
                C: {
                    price: 20,
                    classes: 'balcon2', // votre classe CSS spécifique
                    category: 'C'
                },
                D: {
                    price: 10,
                    classes: 'poulailler', // votre classe CSS spécifique
                    category: 'D'
                }
            },
            naming: {
                top: false,
                getLabel: function (character, row, column) {
                    if (column <= 12) {
                        return column - 1;
                    }
                    return column - 3;
                },
                rows: ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10',
                    '11', '12', '13', '14', '15', '16', '17', '18', '19',
                    ' ', ' ', '20', '21', '22', '23', '24', '25', '26', '27', '28', '29',
                    ' ', ' ', '30', '31', '32', '33', '34', '35', '36', '37', '38', '39',
                    ' ', ' ', '40', '41', '42', '43', '44', '45', '46', '47', '48', '49', '50']
            },
            legend: {
                node: $('#legend'),
                items: [
                    ['A', 'available', 'Orchestre'],
                    ['B', 'available', 'Balcon 1'],
                    ['C', 'available', 'Balcon 2'],
                    ['D', 'available', 'Poulailler'],
                    [, 'unavailable', 'Place non disponible']
                ]
            },
            click: function () {
                if (this.status() === 'available') {
                    //alert(this.settings.id);
                    /*
                     * Une place disponible a été sélectionnée
                     * Mise à jour du nombre de places et du prix total
                     *
                     * Attention la fonction .find  ne prend pas en compte 
                     * la place qui vient d'être selectionnée, car la liste des
                     * places sléectionnées ne sera modifiée qu'après le retour de cette fonction.
                     * C'est pourquoi il est nécessaire d'ajouter 1 au nombre de places et le prix
                     * de la place sélectionnée au prix calculé.
                     */
                    $nbPlaces.text(sc.find('selected').length + 1);
                    $prixTotal.text(calculerPrixTotal(sc) + this.data().price);
                    return 'selected';
                } else if (this.status() == 'selected') {
                    $nbPlaces.text(sc.find('selected').length - 1);
                    $prixTotal.text(calculerPrixTotal(sc) - this.data().price);
                    // la place est désélectionnée
                    return 'available';
                } else if (this.status() == 'unavailable') {
                    // la place a déjà été achetée.
                    return 'unavailable';
                } else {
                    return this.style();
                }
            }
        });
        majPlanSalle();

        setInterval(majPlanSalle, 10000); //every 10 seconds


        $("#achatBtn").click(function () {
            acheter(sc);
        });

        function majPlanSalle() {
            $.ajax({
                type: 'get',
                url: 'placesNonDisponibles',
                dataType: 'json',
                success: function (reponse) {
                    // iteration sur toutes les réservations de reponse
                    $.each(reponse.bookings, function (index, reservation) {
                        //mettre le status du siège correspondant à non disponible
                        sc.status(reservation.rang + '_' + reservation.colonne, 'unavailable');
                    });
                    $nbPlaces.text(sc.find('selected').length);
                    $prixTotal.text(calculerPrixTotal(sc));
                }
            });
        }
    });



    function calculerPrixTotal(sc) {
        var total = 0;
        //retrouver toutes les places sélectionnées et sommer leur prix
        sc.find('selected').each(function () {
            total += this.data().price;
        });
        return total;
    }

    function acheter(sc) {
        var params = "";
        var premier = true;
        sc.find('selected').each(function () {
            if (premier) {
                params = params + "place=";
                premier = false;
            } else {
                params = params + "&place=";
            }
            // params = params + this.settings.row + '_' + this.settings.column;
            params = params + this.settings.id;
            //alert(this.settings.row + '_' + this.settings.column);
        });
        location.replace("acheterPlaces?" + params + "&prixTotal=" + calculerPrixTotal(sc));
    }
</script>

