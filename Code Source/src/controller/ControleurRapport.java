package controller;

import model.LivreModel;

import model.UtilisateurModel;
import model.EmpruntModel;
import model.Livre;
import model.RetourModel;
import model.Utilisateur;
import view.GestionBibliothequeView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import exceptions.LivreNotFoundException;
import exceptions.UtilisateurNotFoundException;

public class ControleurRapport {

    private LivreModel livreModel;
    private UtilisateurModel utilisateurModel;
    private EmpruntModel empruntModel;
    private RetourModel retourModel;
    private GestionBibliothequeView vue;

    public ControleurRapport(LivreModel livreModel, UtilisateurModel utilisateurModel, EmpruntModel empruntModel, RetourModel retourModel, GestionBibliothequeView vue) {
        this.livreModel = livreModel;
        this.utilisateurModel = utilisateurModel;
        this.empruntModel = empruntModel;
        this.retourModel = retourModel;
        this.vue = vue;
        addListeners();
    }

    private void addListeners() {
        vue.getGenererRapportBtn().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                afficherStatistiquesGenerales();
            }
        });
    }

    

    public void afficherStatistiquesGenerales() {
        // Vider la zone de texte avant d'ajouter un nouveau rapport
        vue.getRapportArea().setText("");

        // Générer les statistiques
        StringBuilder rapport = new StringBuilder("Statistiques générales :\n");
        rapport.append("Nombre de livres : ").append(livreModel.getListe().size()).append("\n");
        rapport.append("Nombre d'utilisateurs : ").append(utilisateurModel.getListe().size()).append("\n");
        rapport.append("Nombre d'emprunts : ").append(empruntModel.getListe().size()).append("\n");
        rapport.append("Nombre de retours : ").append(retourModel.getListe().size()).append("\n");

        // Ajouter des détails supplémentaires
        rapport.append("\nDétails supplémentaires :\n");

        // Livres les plus empruntés
        rapport.append("\nLivres les plus empruntés :\n");
        List<Integer> livresLesPlusEmpruntesIds = empruntModel.getLivresLesPlusEmpruntes();
        for (Integer livreId : livresLesPlusEmpruntesIds) {
            try {
                Livre livre = livreModel.rechercherParID(livreId);
                int nombreEmprunts = empruntModel.getNombreEmpruntsLivre(livreId);
                rapport.append(livre.getTitre()).append(" (").append(nombreEmprunts).append(" emprunts)\n");
            } catch (LivreNotFoundException e) {
                rapport.append("Livre non trouvé : ").append(livreId).append("\n");
            }
        }

        // Utilisateurs les plus actifs
        rapport.append("\nUtilisateurs les plus actifs :\n");
        List<Integer> utilisateursLesPlusActifsIds = empruntModel.getUtilisateursLesPlusActifs();
        for (Integer utilisateurId : utilisateursLesPlusActifsIds) {
            try {
                Utilisateur utilisateur = utilisateurModel.rechercherParID(utilisateurId);
                int nombreEmprunts = empruntModel.getNombreEmpruntsUtilisateur(utilisateurId);
                rapport.append(utilisateur.getNom()).append(" (").append(nombreEmprunts).append(" emprunts)\n");
            } catch (UtilisateurNotFoundException e) {
                rapport.append("Utilisateur non trouvé : ").append(utilisateurId).append("\n");
            }
        }

        // Ajouter les statistiques dans la zone de texte
        vue.getRapportArea().append(rapport.toString());
    }


}
