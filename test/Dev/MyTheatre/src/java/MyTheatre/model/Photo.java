
package MyTheatre.model;

public class Photo {
private int noPhoto;
private String contenu;

    public Photo(int noPhoto, String contenu) {
        this.noPhoto = noPhoto;
        this.contenu = contenu;
    }

    public int getNoPhoto() {
        return noPhoto;
    }

    public String getContenu() {
        return contenu;
    }

    public void setNoPhoto(int noPhoto) {
        this.noPhoto = noPhoto;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }


}
