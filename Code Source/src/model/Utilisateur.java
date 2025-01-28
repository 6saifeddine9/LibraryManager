package model;

public class Utilisateur {
    private String nom;
    private String prenom;
    private String role;
    private int id;
    private static int compteur;

    public Utilisateur() {
        super();
        compteur++;
        id = compteur;
    }

    public Utilisateur(String nom, String prenom, String role) {
        super();
        this.nom = nom;
        this.prenom = prenom;
        this.role = role;
        compteur++;
        id = compteur;
    }

    public Utilisateur(String nom, String prenom, String role, int id) {
        super();
        this.nom = nom;
        this.prenom = prenom;
        this.role = role;
        this.id = id;
        compteur = id;
    }

    // Getters et setters
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return id + ";" + nom + ";" + prenom + ";" + role;
    }

    public static int getCompteur() {
        return compteur;
    }

    public static void setCompteur(int compteur) {
        Utilisateur.compteur = compteur;
    }
}
