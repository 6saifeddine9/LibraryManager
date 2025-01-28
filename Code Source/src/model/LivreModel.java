package model;

import java.io.*;
import java.util.*;
import exceptions.LivreNotFoundException;
import exceptions.CSVFileException;
import utils.GestionnaireFichier;

public class LivreModel {

    private ArrayList<Livre> liste = new ArrayList<>();
    private String csvFileName;

    public LivreModel() {
        super();
    }

    public LivreModel(String csvFileName) throws CSVFileException {
        super();
        this.csvFileName = csvFileName;
        try {
            GestionnaireFichier.createFileIfNotExists(csvFileName, "Id;Titre;Auteur;AnneePublication;Genre;Disponible");
        } catch (IOException e) {
            throw new CSVFileException("Erreur lors de la création du fichier CSV : " + csvFileName);
        }
    }

    public void ajouterLivre(Livre e) {
        liste.add(e);
    }

    public void supprimerLivre(int id) throws LivreNotFoundException {
        Livre e = rechercherParID(id);
        if (e != null)
            liste.remove(e);
        else
            throw new LivreNotFoundException(id);  
    }

    public void modifierLivre(String titre, String auteur, int anneePublication, String genre, boolean disponible, int id) throws LivreNotFoundException {
        Livre e = rechercherParID(id);
        if (e != null) {
            e.setTitre(titre);
            e.setAuteur(auteur);
            e.setGenre(genre);
            e.setAnneePublication(anneePublication);
            e.setDisponible(disponible);
        } else {
            throw new LivreNotFoundException(id);  
        }
    }

    public void listerLivres() {
        System.out.println(liste);
    }

    public void trierLivre() {
        liste.stream().sorted((o1, o2) -> o1.getTitre().compareTo(o2.getTitre())).forEach(t -> System.out.println(t));
    }

    public void sauvegraderCSV() throws CSVFileException {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(csvFileName));
            bw.write("Id;Titre;Auteur;AnneePublication;Genre;Disponible");
            for (int i = 0; i < liste.size(); i++) {
                bw.newLine();
                bw.write(liste.get(i).toString());
            }
            bw.close();
        } catch (IOException e) {
            throw new CSVFileException("Erreur lors de la sauvegarde du fichier CSV : " + csvFileName);  
        }
    }

    public Livre rechercherParID(int id) throws LivreNotFoundException {
        Optional<Livre> p = liste.stream().filter(e -> e.getId() == id).findFirst();
        return p.orElseThrow(() -> new LivreNotFoundException(id));  
    }

    public void lireCSV() throws CSVFileException, NumberFormatException {
        try {
            BufferedReader br = new BufferedReader(new FileReader(csvFileName));
            br.readLine(); 
            String line;
            while ((line = br.readLine()) != null) {
                String[] words = line.split(";");
                int id = Integer.parseInt(words[0]);
                String titre = words[1];
                String auteur = words[2];
                int anneePublication = Integer.parseInt(words[3]);
                String genre = words[4];
                boolean disponible = Boolean.parseBoolean(words[5]);

                Livre e = new Livre();
                e.setId(id);
                e.setAuteur(auteur);
                e.setTitre(titre);
                e.setAnneePublication(anneePublication);
                e.setGenre(genre);
                e.setDisponible(disponible);
                liste.add(e);
            }
            br.close();
        } catch (IOException e) {
            throw new CSVFileException("Erreur lors de la lecture du fichier CSV : " + csvFileName);  
        } catch (java.lang.NumberFormatException e) {
            throw new NumberFormatException("Erreur lors de l'analyse du nombre dans le fichier CSV : " + csvFileName);
        }
    }

    public List<Livre> getListe() {
        return liste;
    }

    public boolean livreExiste(int id) {
        return liste.stream().anyMatch(e -> e.getId() == id);
    }

    public void changerDisponibilite(int id, boolean disponible) throws LivreNotFoundException {
        Livre e = rechercherParID(id);
        if (e != null) {
            e.setDisponible(disponible);
        } else {
            throw new LivreNotFoundException(id);  
        }
    }
    
    public boolean estDisponible(int id) throws LivreNotFoundException {
        Livre e = rechercherParID(id);
        if (e != null) {
            return e.isDisponible();
        } else {
            throw new LivreNotFoundException(id);  
        }
    }
    
}
