package model;

import java.io.*;
import java.util.*;
import exceptions.UtilisateurNotFoundException;
import exceptions.CSVFileException;
import utils.GestionnaireFichier;

/**
 * La classe UtilisateurModel gère une liste d'utilisateurs et permet de les manipuler.
 */
public class UtilisateurModel {

    private ArrayList<Utilisateur> liste = new ArrayList<>();
    private String csvFileName;

    /**
     * Constructeur par défaut.
     */
    public UtilisateurModel() {
        super();
    }

    /**
     * Constructeur qui initialise le modèle avec un nom de fichier CSV.
     *
     * @param csvFileName le nom du fichier CSV
     * @throws CSVFileException si une erreur survient lors de la création du fichier CSV
     */
    public UtilisateurModel(String csvFileName) throws CSVFileException {
        super();
        this.csvFileName = csvFileName;
        try {
            GestionnaireFichier.createFileIfNotExists(csvFileName, "Id;Nom;Prenom;Role");
        } catch (IOException e) {
            throw new CSVFileException("Erreur lors de la création du fichier CSV : " + csvFileName);
        }
    }

    /**
     * Ajoute un utilisateur à la liste.
     *
     * @param e l'utilisateur à ajouter
     */
    public void ajouterUtilisateur(Utilisateur e) {
        liste.add(e);
    }

    /**
     * Supprime un utilisateur de la liste par son ID.
     *
     * @param id l'ID de l'utilisateur à supprimer
     * @throws UtilisateurNotFoundException si l'utilisateur n'est pas trouvé
     */
    public void supprimerUtilisateur(int id) throws UtilisateurNotFoundException {
        Utilisateur e = rechercherParID(id);
        if (e != null)
            liste.remove(e);
        else
            throw new UtilisateurNotFoundException(id);
    }

    /**
     * Modifie les informations d'un utilisateur par son ID.
     *
     * @param nom le nouveau nom de l'utilisateur
     * @param prenom le nouveau prénom de l'utilisateur
     * @param role le nouveau rôle de l'utilisateur
     * @param id l'ID de l'utilisateur à modifier
     * @throws UtilisateurNotFoundException si l'utilisateur n'est pas trouvé
     */
    public void modifierUtilisateur(String nom, String prenom, String role, int id) throws UtilisateurNotFoundException {
        Utilisateur e = rechercherParID(id);
        if (e != null) {
            e.setNom(nom);
            e.setPrenom(prenom);
            e.setRole(role);
        } else {
            throw new UtilisateurNotFoundException(id);
        }
    }

    /**
     * Affiche la liste de tous les utilisateurs.
     */
    public void listerUtilisateurs() {
        System.out.println(liste);
    }

    /**
     * Sauvegarde la liste des utilisateurs dans un fichier CSV.
     *
     * @throws CSVFileException si une erreur survient lors de la sauvegarde du fichier CSV
     */
    public void sauvegraderCSV() throws CSVFileException {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(csvFileName));
            bw.write("Id;Nom;Prenom;Role");
            for (int i = 0; i < liste.size(); i++) {
                bw.newLine();
                bw.write(liste.get(i).toString());
            }
            bw.close();
        } catch (IOException e) {
            throw new CSVFileException("Erreur lors de la sauvegarde du fichier CSV : " + csvFileName);
        }
    }

    /**
     * Recherche un utilisateur par son ID.
     *
     * @param id l'ID de l'utilisateur à rechercher
     * @return l'utilisateur trouvé
     * @throws UtilisateurNotFoundException si l'utilisateur n'est pas trouvé
     */
    public Utilisateur rechercherParID(int id) throws UtilisateurNotFoundException {
        Optional<Utilisateur> p = liste.stream().filter(e -> e.getId() == id).findFirst();
        return p.orElseThrow(() -> new UtilisateurNotFoundException(id));
    }

    /**
     * Lit les utilisateurs depuis un fichier CSV.
     *
     * @throws CSVFileException si une erreur survient lors de la lecture du fichier CSV
     * @throws NumberFormatException si une erreur survient lors de l'analyse des nombres dans le fichier CSV
     */
    public void lireCSV() throws CSVFileException, NumberFormatException {
        try {
            BufferedReader br = new BufferedReader(new FileReader(csvFileName));
            br.readLine();
            String line;
            while ((line = br.readLine()) != null) {
                String[] words = line.split(";");
                int id = Integer.parseInt(words[0]);
                String nom = words[1];
                String prenom = words[2];
                String role = words[3];

                Utilisateur e = new Utilisateur();
                e.setId(id);
                e.setNom(nom);
                e.setPrenom(prenom);
                e.setRole(role);
                liste.add(e);
            }
            br.close();
        } catch (IOException e) {
            throw new CSVFileException("Erreur lors de la lecture du fichier CSV : " + csvFileName);
        } catch (java.lang.NumberFormatException e) {
            throw new NumberFormatException("Erreur lors de l'analyse du nombre dans le fichier CSV : " + csvFileName);
        }
    }

    /**
     * Retourne la liste de tous les utilisateurs.
     *
     * @return la liste des utilisateurs
     */
    public List<Utilisateur> getListe() {
        return liste;
    }

    /**
     * Vérifie si un utilisateur existe par son ID.
     *
     * @param id l'ID de l'utilisateur à vérifier
     * @return true si l'utilisateur existe, false sinon
     */
    public boolean utilisateurExiste(int id) {
        return liste.stream().anyMatch(e -> e.getId() == id);
    }
}
