package model;

import exceptions.EmpruntNotFoundException;
import exceptions.CSVFileException;
import java.time.LocalDate;
import java.util.List;

public interface EmpruntModelInterface {

    void ajouterEmprunt(Emprunt e);
    void supprimerEmprunt(int id) throws EmpruntNotFoundException;
    void prolongerEmprunt(int id, String nouvelleDateRetour) throws EmpruntNotFoundException;
    void listerEmprunts();
    void sauvegraderCSV() throws CSVFileException;
    Emprunt rechercherParID(int id) throws EmpruntNotFoundException;
    void lireCSV() throws CSVFileException, NumberFormatException;
    boolean empruntExiste(int empruntId);
    List<Emprunt> getListe();
    LocalDate getDateEmprunt(int empruntId) throws EmpruntNotFoundException;
}
