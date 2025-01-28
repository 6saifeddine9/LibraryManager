package model;

public class Emprunt {
    private int utilisateurId;
    private int livreId;
    private String dateEmprunt;
    private String dateRetourPrevu;
    private int id;
    private static int compteur;

    public Emprunt() {
        super();
        compteur++;
        id = compteur;
    }

    public Emprunt(int utilisateurId, int livreId, String dateEmprunt, String dateRetourPrevu) {
        super();
        this.utilisateurId = utilisateurId;
        this.livreId = livreId;
        this.dateEmprunt = dateEmprunt;
        this.dateRetourPrevu = dateRetourPrevu;
        compteur++;
        id = compteur;
    }

    public Emprunt(int utilisateurId, int livreId, String dateEmprunt, String dateRetourPrevu, int id) {
        super();
        this.utilisateurId = utilisateurId;
        this.livreId = livreId;
        this.dateEmprunt = dateEmprunt;
        this.dateRetourPrevu = dateRetourPrevu;
        this.id = id;
        compteur = id;
    }

    // Getters et setters
    public int getUtilisateurId() {
        return utilisateurId;
    }

    public void setUtilisateurId(int utilisateurId) {
        this.utilisateurId = utilisateurId;
    }

    public int getLivreId() {
        return livreId;
    }

    public void setLivreId(int livreId) {
        this.livreId = livreId;
    }

    public String getDateEmprunt() {
        return dateEmprunt;
    }

    public void setDateEmprunt(String dateEmprunt) {
        this.dateEmprunt = dateEmprunt;
    }

    public String getDateRetourPrevu() {
        return dateRetourPrevu;
    }

    public void setDateRetourPrevu(String dateRetourPrevu) {
        this.dateRetourPrevu = dateRetourPrevu;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return id + ";" + utilisateurId + ";" + livreId + ";" + dateEmprunt + ";" + dateRetourPrevu;
    }

    public static int getCompteur() {
        return compteur;
    }

    public static void setCompteur(int compteur) {
        Emprunt.compteur = compteur;
    }
}
