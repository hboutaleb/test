// variables globales
var compteurPhoto = 1;

//fonction pour changement des photos quand on clique dessus
function imageSuivante(max) {
    var id_image = "image" + compteurPhoto.toString();
    var div = document.getElementById(id_image);
    div.style.display = 'none';
    if (compteurPhoto >= max) {
        compteurPhoto = 1;
    } else {
        compteurPhoto += 1;
    }
    var id_image = "image" + compteurPhoto.toString();
    div = document.getElementById(id_image);
    div.style.display = 'block';
}


