package model;

public class Livre {
    private String titre;
    private String auteur;
    private int anneePublication;
    private String genre;
    private int id;
    private static int compteur;
    private boolean disponible; 

    public Livre() {
        super();
        compteur++;
        id = compteur;
        disponible = true; 
    }

    public Livre(String titre, String auteur, int anneePublication, String genre) {
        super();
        this.titre = titre;
        this.auteur = auteur;
        this.anneePublication = anneePublication;
        this.genre = genre;
        compteur++;
        id = compteur;
        disponible = true; 
    }

    public Livre(String titre, String auteur, int anneePublication, String genre, int id) {
        super();
        this.titre = titre;
        this.auteur = auteur;
        this.anneePublication = anneePublication;
        this.genre = genre;
        this.id = id;
        compteur = id;
        disponible = true; 
    }
    
    public Livre(String titre, String auteur, int anneePublication, String genre, boolean disponible) {
        super();
        this.titre = titre;
        this.auteur = auteur;
        this.anneePublication = anneePublication;
        this.genre = genre;
        compteur++;
        id = compteur;
        this.disponible = disponible; 
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getAuteur() {
        return auteur;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public int getAnneePublication() {
        return anneePublication;
    }

    public void setAnneePublication(int anneePublication) {
        this.anneePublication = anneePublication;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    @Override
    public String toString() {
        return id + ";" + titre + ";" + auteur + ";" + anneePublication + ";" + genre + ";" + disponible;
    }

    public static int getCompteur() {
        return compteur;
    }

    public static void setCompteur(int compteur) {
        Livre.compteur = compteur;
    }
}
