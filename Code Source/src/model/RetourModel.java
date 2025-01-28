package model;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import exceptions.RetourNotFoundException;
import exceptions.CSVFileException;
import exceptions.EmpruntNotFoundException;
import utils.GestionnaireFichier;

public class RetourModel {

    private ArrayList<Retour> liste = new ArrayList<>();
    private String csvFileName;

    public RetourModel() {
        super();
    }

    public RetourModel(String csvFileName) throws CSVFileException {
        super();
        this.csvFileName = csvFileName;
        try {
            GestionnaireFichier.createFileIfNotExists(csvFileName, "Id;EmpruntId;DateRetour;Penalite");
        } catch (IOException e) {
            throw new CSVFileException("Erreur lors de la création du fichier CSV : " + csvFileName);
        }
    }

    public void ajouterRetour(Retour e) {
        liste.add(e);
    }

    public void supprimerRetour(int id) throws RetourNotFoundException {
        Retour e = rechercherParID(id);
        if (e != null)
            liste.remove(e);
        else
            throw new RetourNotFoundException(id);  // Lancer exception si le retour n'est pas trouvé
    }

    public void listerRetours() {
        System.out.println(liste);
    }

    public void sauvegraderCSV() throws CSVFileException {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(csvFileName));
            bw.write("Id;EmpruntId;DateRetour;Penalite");
            for (int i = 0; i < liste.size(); i++) {
                bw.newLine();
                bw.write(liste.get(i).toString());
            }
            bw.close();
        } catch (IOException e) {
            throw new CSVFileException("Erreur lors de la sauvegarde du fichier CSV : " + csvFileName); 
        }
    }

    public Retour rechercherParID(int id) throws RetourNotFoundException {
        Optional<Retour> p = liste.stream().filter(e -> e.getId() == id).findFirst();
        return p.orElseThrow(() -> new RetourNotFoundException(id));  
    }

    public void lireCSV() throws CSVFileException, NumberFormatException {
        try {
            BufferedReader br = new BufferedReader(new FileReader(csvFileName));
            br.readLine(); 
            String line;
            while ((line = br.readLine()) != null) {
                String[] words = line.split(";");
                int id = Integer.parseInt(words[0]);
                int empruntId = Integer.parseInt(words[1]);
                String dateRetour = words[2];
                double penalite = Double.parseDouble(words[3]);

                Retour e = new Retour();
                e.setId(id);
                e.setEmpruntId(empruntId);
                e.setDateRetour(dateRetour);
                e.setPenalite(penalite);
                liste.add(e);
            }
            br.close();
        } catch (IOException e) {
            throw new CSVFileException("Erreur lors de la lecture du fichier CSV : " + csvFileName);  
        } catch (java.lang.NumberFormatException e) {
            throw new NumberFormatException("Erreur lors de l'analyse du nombre dans le fichier CSV : " + csvFileName);
        }
    }

    public List<Retour> getListe() {
        return liste;
    }

    public void calculerPenalite(Retour retour, EmpruntModel empruntModel) throws EmpruntNotFoundException {
        Emprunt emprunt = empruntModel.rechercherParID(retour.getEmpruntId());
        LocalDate dateRetour = LocalDate.parse(retour.getDateRetour(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate dateRetourPrevu = LocalDate.parse(emprunt.getDateRetourPrevu(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        if (dateRetour.isAfter(dateRetourPrevu)) {
            long joursRetard = ChronoUnit.DAYS.between(dateRetourPrevu, dateRetour);
            double penalite = joursRetard * 10; 
            retour.setPenalite(penalite);
        } else {
            retour.setPenalite(0);
        }
    }
}
