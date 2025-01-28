package model;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import exceptions.EmpruntNotFoundException;
import exceptions.LivreNotFoundException;
import exceptions.CSVFileException;
import utils.GestionnaireFichier;

public class EmpruntModel {

    private ArrayList<Emprunt> liste = new ArrayList<>();
    private String csvFileName;

    public EmpruntModel() {
        super();
    }

    public EmpruntModel(String csvFileName) throws CSVFileException {
        super();
        this.csvFileName = csvFileName;
        try {
            GestionnaireFichier.createFileIfNotExists(csvFileName, "Id;UtilisateurId;LivreId;DateEmprunt;DateRetourPrevu");
        } catch (IOException e) {
            throw new CSVFileException("Erreur lors de la création du fichier CSV : " + csvFileName);
        }
    }

    public void ajouterEmprunt(Emprunt e) {
        liste.add(e);
    }

    public void supprimerEmprunt(int id) throws EmpruntNotFoundException {
        Emprunt e = rechercherParID(id);
        if (e != null)
            liste.remove(e);
        else
            throw new EmpruntNotFoundException(id);  // Lancer exception si l'emprunt n'est pas trouvé
    }

    public void prolongerEmprunt(int id, String nouvelleDateRetour) throws EmpruntNotFoundException {
        Emprunt e = rechercherParID(id);
        if (e != null) {
            e.setDateRetourPrevu(nouvelleDateRetour);
        } else {
            throw new EmpruntNotFoundException(id);  // Lancer exception si l'emprunt n'est pas trouvé
        }
    }

    public void listerEmprunts() {
        System.out.println(liste);
    }

    public void sauvegraderCSV() throws CSVFileException {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(csvFileName));
            bw.write("Id;UtilisateurId;LivreId;DateEmprunt;DateRetourPrevu");
            for (int i = 0; i < liste.size(); i++) {
                bw.newLine();
                bw.write(liste.get(i).toString());
            }
            bw.close();
        } catch (IOException e) {
            throw new CSVFileException("Erreur lors de la sauvegarde du fichier CSV : " + csvFileName);  // Lancer exception CSVFileException
        }
    }

    public Emprunt rechercherParID(int id) throws EmpruntNotFoundException {
        Optional<Emprunt> p = liste.stream().filter(e -> e.getId() == id).findFirst();
        return p.orElseThrow(() -> new EmpruntNotFoundException(id));  // Lancer exception si l'emprunt n'est pas trouvé
    }

    public void lireCSV() throws CSVFileException, NumberFormatException {
        try {
            BufferedReader br = new BufferedReader(new FileReader(csvFileName));
            String line;
            br.readLine(); // Ignorer l'en-tête
            while ((line = br.readLine()) != null) {
                String[] words = line.split(";");
                if (words.length == 5) { // Vérifier que la ligne a le bon nombre de colonnes
                    int id = Integer.parseInt(words[0]);
                    int utilisateurId = Integer.parseInt(words[1]);
                    int livreId = Integer.parseInt(words[2]);
                    String dateEmprunt = words[3];
                    String dateRetourPrevu = words[4];

                    Emprunt e = new Emprunt();
                    e.setId(id);
                    e.setUtilisateurId(utilisateurId);
                    e.setLivreId(livreId);
                    e.setDateEmprunt(dateEmprunt);
                    e.setDateRetourPrevu(dateRetourPrevu);
                    liste.add(e);
                }
            }
            br.close();
        } catch (IOException e) {
            throw new CSVFileException("Erreur lors de la lecture du fichier CSV : " + csvFileName);  // Lancer exception CSVFileException
        } catch (java.lang.NumberFormatException e) {
            throw new NumberFormatException("Erreur lors de l'analyse du nombre dans le fichier CSV : " + csvFileName);
        }
    }

    public boolean empruntExiste(int empruntId) {
        for (Emprunt emprunt : liste) {
            if (emprunt.getId() == empruntId) {
                return true;
            }
        }
        return false;
    }
    
    public List<Emprunt> getListe() {
        return liste;
    }

	public LocalDate getDateEmprunt(int empruntId) throws EmpruntNotFoundException {
        Emprunt emprunt = rechercherParID(empruntId);
        if (emprunt != null) {
            return LocalDate.parse(emprunt.getDateEmprunt(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        } else {
            throw new EmpruntNotFoundException(empruntId);
        }
    }

	public void changerDisponibilite(int livreId, boolean disponible) throws LivreNotFoundException {
        LivreModel livreModel = new LivreModel(); // Assurez-vous d'initialiser correctement le LivreModel
        livreModel.changerDisponibilite(livreId, disponible);
    }
	
	public List<Integer> getLivresLesPlusEmpruntes() {
        Map<Integer, Integer> livreEmpruntsCount = new HashMap<>();
        for (Emprunt emprunt : liste) {
            livreEmpruntsCount.put(emprunt.getLivreId(), livreEmpruntsCount.getOrDefault(emprunt.getLivreId(), 0) + 1);
        }

        List<Map.Entry<Integer, Integer>> sortedEntries = new ArrayList<>(livreEmpruntsCount.entrySet());
        sortedEntries.sort(Map.Entry.<Integer, Integer>comparingByValue().reversed());

        List<Integer> livresLesPlusEmpruntes = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : sortedEntries) {
            livresLesPlusEmpruntes.add(entry.getKey());
        }

        return livresLesPlusEmpruntes;
    }

    public List<Integer> getUtilisateursLesPlusActifs() {
        Map<Integer, Integer> utilisateurEmpruntsCount = new HashMap<>();
        for (Emprunt emprunt : liste) {
            utilisateurEmpruntsCount.put(emprunt.getUtilisateurId(), utilisateurEmpruntsCount.getOrDefault(emprunt.getUtilisateurId(), 0) + 1);
        }

        List<Map.Entry<Integer, Integer>> sortedEntries = new ArrayList<>(utilisateurEmpruntsCount.entrySet());
        sortedEntries.sort(Map.Entry.<Integer, Integer>comparingByValue().reversed());

        List<Integer> utilisateursLesPlusActifs = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : sortedEntries) {
            utilisateursLesPlusActifs.add(entry.getKey());
        }

        return utilisateursLesPlusActifs;
    }
	 
    public int getNombreEmpruntsLivre(int livreId) {
        return (int) liste.stream().filter(e -> e.getLivreId() == livreId).count();
    }

    public int getNombreEmpruntsUtilisateur(int utilisateurId) {
        return (int) liste.stream().filter(e -> e.getUtilisateurId() == utilisateurId).count();
    }
}
