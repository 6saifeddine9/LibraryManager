package Test;
import exceptions.LivreNotFoundException;
import model.Livre;
import model.LivreModel;
import exceptions.CSVFileException;
import java.io.File;
import java.io.IOException;

public class test {

    public static void main(String[] args) throws IOException {
        String csvFileName = "testLivres.csv";
        LivreModel livreModel = null;

        try {
            
            livreModel = new LivreModel(csvFileName);
            
            new File(csvFileName).delete();

            
            Livre livre1 = new Livre("Titre1", "Auteur1", 2000, "Genre1", true);
            livreModel.ajouterLivre(livre1);
            System.out.println("Livre ajouté : " + livre1);

           
            livreModel.supprimerLivre(1);
            System.out.println("Livre supprimé avec ID 1");

           
            Livre livre2 = new Livre("Titre2", "Auteur2", 2001, "Genre2", true);
            livreModel.ajouterLivre(livre2);
            livreModel.modifierLivre("NouveauTitre", "NouvelAuteur", 2020, "NouveauGenre", false, 2);
            System.out.println("Livre modifié : " + livreModel.rechercherParID(2));

            
            Livre livre3 = new Livre("Titre3", "Auteur3", 2002, "Genre3", true);
            livreModel.ajouterLivre(livre3);
            System.out.println("Liste des livres : ");
            livreModel.listerLivres();

            
            System.out.println("Livres triés par titre : ");
            livreModel.trierLivre();

            
            livreModel.sauvegraderCSV();
            System.out.println("Livres sauvegardés dans le fichier CSV.");

            
            LivreModel newLivreModel = new LivreModel(csvFileName);
            newLivreModel.lireCSV();
            System.out.println("Livres lus depuis le fichier CSV : ");
            newLivreModel.listerLivres();

            
            Livre foundLivre = newLivreModel.rechercherParID(3);
            System.out.println("Livre trouvé avec ID 3 : " + foundLivre);

            
            System.out.println("Le livre avec ID 3 existe : " + newLivreModel.livreExiste(3));
            System.out.println("Le livre avec ID 4 existe : " + newLivreModel.livreExiste(4));

            newLivreModel.changerDisponibilite(3, false);
            System.out.println("Disponibilité du livre avec ID 3 : " + newLivreModel.estDisponible(3));

        } catch (LivreNotFoundException | CSVFileException e) {
            e.printStackTrace();
        }
    }
}
