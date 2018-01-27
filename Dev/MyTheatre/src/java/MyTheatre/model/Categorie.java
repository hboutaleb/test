package MyTheatre.model;

public class Categorie {
    private String nomC;
    private int prix;

    public Categorie(String NOMC, int PRIX) {
        this.nomC = NOMC;
        this.prix = PRIX;
    }

    /**
     * Renvoie le nom de la categorie
     * @return le nom de la categorie de prix
     */
    public String getNomC() {
        return nomC;
    }

    /**
     * Renvoie le prix en integer
     * @return le prix au format integer
     */
    public int getPrix() {
        return prix;
    }

    /**
     * Renvoi le prix en string
     * @return le prix d'une categorie en string
     */
    public String getPrixStr() {
        return String.valueOf(prix);
    }

}
