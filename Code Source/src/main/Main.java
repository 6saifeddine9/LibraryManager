package main;

import model.LivreModel;
import model.UtilisateurModel;
import model.EmpruntModel;
import model.RetourModel;
import view.GestionBibliothequeView;
import view.LoginVue;
import controller.ControleurLivre;
import controller.ControleurUtilisateur;
import controller.ControleurEmprunt;
import controller.ControleurRetour;
import controller.ControleurRapport;

import javax.swing.JOptionPane;

import controller.ControleurAuth;
import exceptions.CSVFileException;

public class Main {
    public static void main(String[] args) {
        try {
            LivreModel livreModel = new LivreModel("livres.csv");
            UtilisateurModel utilisateurModel = new UtilisateurModel("utilisateurs.csv");
            EmpruntModel empruntModel = new EmpruntModel("emprunts.csv");
            RetourModel retourModel = new RetourModel("retours.csv");

            GestionBibliothequeView vuePrincipale = new GestionBibliothequeView();

            ControleurRapport controleurRapport = new ControleurRapport(livreModel, utilisateurModel, empruntModel, retourModel, vuePrincipale);
            new ControleurLivre(livreModel, vuePrincipale);
            new ControleurUtilisateur(utilisateurModel, vuePrincipale);
            new ControleurEmprunt(empruntModel, livreModel, utilisateurModel, vuePrincipale);
            new ControleurRetour(retourModel, empruntModel, livreModel, vuePrincipale);

            ControleurAuth controleurAuth = new ControleurAuth(utilisateurModel, vuePrincipale);

            new LoginVue(controleurAuth, vuePrincipale);

        } catch (CSVFileException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
}
