package MyTheatre.model;

import java.sql.Timestamp;
import java.util.List;

public class Spectacle {

    private int numS;
    private String nom;          //nom du spectacle
    private String resume;       //resume du cpectacle
    private int duree;           //duree du spectacle en heure(decimal)
    private Timestamp dateRep;   //date de la representation en yyy-mm-dd hh:mm:ss.ms
    private String genre;
    private String catAge;
    private List<Photo> lesphotos;  //une liste de photos

//    /**
//     * Constructeur de spectacle pour affichage liste de spectacles simple
//     *
//     * @param nom le nom du spectacle
//     */
//    public Spectacle(String nom) {
//        this(0, nom, "", 0, null, null);
//    }
    public Spectacle(int numS, String nom, String genre, String categorieAge) {
        this(numS, nom, "", 0, genre, categorieAge, null);
    }

    public Spectacle(int numS, String nom, String resume, int duree, String genre, String categorieAge, List<Photo> listephotos) {
        this.numS = numS;
        this.nom = nom;
        this.resume = resume;
        this.duree = duree;
        this.genre = genre;
        this.catAge = categorieAge;
        this.lesphotos = listephotos;
    }

    public String getNom() {
        return nom;
    }

    public String getResume() {
        return resume;
    }

    public int getDuree() {
        return duree;
    }

    public String getGenre() {
        return genre;
    }

    public String getCatAge() {
        return catAge;
    }

    public int getNumS() {
        return numS;
    }

    public List<Photo> getLesphotos() {
        return lesphotos;
    }

}
