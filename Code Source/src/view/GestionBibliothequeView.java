package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GestionBibliothequeView extends JFrame {
    private JTabbedPane tabbedPane;
    private VueLivre vueLivre;
    private VueUtilisateur vueUtilisateur;
    private VueEmprunt vueEmprunt;
    private VueRetour vueRetour;
    private VueRapport vueRapport;
    private JButton genererRapportBtn;
    private JTextArea rapportArea;

    public GestionBibliothequeView() {
        // Initialiser les composants de la vue principale
        vueLivre = new VueLivre();
        vueUtilisateur = new VueUtilisateur();
        vueEmprunt = new VueEmprunt();
        vueRetour = new VueRetour();
        vueRapport = new VueRapport();

        // Créer le JTabbedPane
        tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Livres", vueLivre);
        tabbedPane.addTab("Utilisateurs", vueUtilisateur);
        tabbedPane.addTab("Emprunts", vueEmprunt);
        tabbedPane.addTab("Retours", vueRetour);
        tabbedPane.addTab("Rapports", vueRapport);

        // Initialiser le bouton de génération de rapport
       

        // Initialiser la zone de texte pour afficher le rapport
        rapportArea = new JTextArea();
        rapportArea.setEditable(false);
        rapportArea.setFont(new Font("Arial", Font.PLAIN, 14));
        rapportArea.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));

        // Ajouter le bouton de génération de rapport et la zone de texte à la vue principale
        JPanel rapportPanel = new JPanel(new BorderLayout());
        rapportPanel.add(genererRapportBtn, BorderLayout.NORTH);
        rapportPanel.add(new JScrollPane(rapportArea), BorderLayout.CENTER);

        vueRapport.add(rapportPanel);

        tabbedPane.addTab("Rapports", vueRapport);

        // Ajouter le JTabbedPane à la fenêtre principale
        setLayout(new BorderLayout());
        add(tabbedPane, BorderLayout.CENTER);

        setTitle("Gestion de Bibliothèque");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(false); // Masquer la vue principale au début
    }

    public JFrame getFrame() {
        return this;
    }

    // Méthodes pour accéder aux composants de VueLivre
    public JButton getAjouterLivreBtn() {
        return vueLivre.getAjouterLivreBtn();
    }

    public JButton getModifierLivreBtn() {
        return vueLivre.getModifierLivreBtn();
    }

    public JButton getSupprimerLivreBtn() {
        return vueLivre.getSupprimerLivreBtn();
    }

    public JButton getChangerDisponibiliteBtn() {
        return vueLivre.getChangerDisponibiliteBtn();
    }

    public JTextField getSearchLivresField() {
        return vueLivre.getSearchLivresField();
    }

    public DefaultTableModel getModelLivres() {
        return vueLivre.getModelLivres();
    }

    // Méthodes pour accéder aux composants de VueUtilisateur
    public JButton getAjouterUtilisateurBtn() {
        return vueUtilisateur.getAjouterUtilisateurBtn();
    }

    public JButton getModifierUtilisateurBtn() {
        return vueUtilisateur.getModifierUtilisateurBtn();
    }

    public JButton getSupprimerUtilisateurBtn() {
        return vueUtilisateur.getSupprimerUtilisateurBtn();
    }

    public JTextField getSearchUtilisateursField() {
        return vueUtilisateur.getSearchUtilisateursField();
    }

    public DefaultTableModel getModelUtilisateurs() {
        return vueUtilisateur.getModelUtilisateurs();
    }

    // Méthodes pour accéder aux composants de VueEmprunt
    public JButton getAjouterEmpruntBtn() {
        return vueEmprunt.getAjouterEmpruntBtn();
    }

    public JButton getProlongerEmpruntBtn() {
        return vueEmprunt.getProlongerEmpruntBtn();
    }

    public JButton getSupprimerEmpruntBtn() {
        return vueEmprunt.getSupprimerEmpruntBtn();
    }

    public JTextField getSearchEmpruntsField() {
        return vueEmprunt.getSearchEmpruntsField();
    }

    public DefaultTableModel getModelEmprunts() {
        return vueEmprunt.getModelEmprunts();
    }

    public JTable getTableEmprunts() {
        return vueEmprunt.getTableEmprunts();
    }
    
    public JTable getTableUtilisateurs() {
    	return vueUtilisateur.getTableUtilisateur();
    }
    
    public JTable getTableRetours() {
    	return vueRetour.getTableRetours();
    }
    
    public JTable getTableLivres() {
        return vueLivre.getTableLivres();
    }

    // Méthodes pour accéder aux composants de VueRetour
    public JButton getAjouterRetourBtn() {
        return vueRetour.getAjouterRetourBtn();
    }

    public JButton getSupprimerRetourBtn() {
        return vueRetour.getSupprimerRetourBtn();
    }

    public JTextField getSearchRetoursField() {
        return vueRetour.getSearchRetoursField();
    }

    public DefaultTableModel getModelRetours() {
        return vueRetour.getModelRetours();
    }

    public JButton getGenererRapportBtn() {
        return genererRapportBtn;
    }

    public JTextArea getRapportArea() {
        return rapportArea;
    }
}
