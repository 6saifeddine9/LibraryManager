package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class VueLivre extends JPanel {
    private JButton ajouterLivreBtn;
    private JButton modifierLivreBtn;
    private JButton supprimerLivreBtn;
    private JButton changerDisponibiliteBtn;
    private JTextField searchLivresField;
    private DefaultTableModel modelLivres;
    private JTable tableLivres;

    public VueLivre() {
        // Initialiser les composants de la vue
        setLayout(new BorderLayout());
        setBackground(new Color(240, 240, 240));

        // Initialiser les boutons
        ajouterLivreBtn = new JButton("Ajouter Livre");
        modifierLivreBtn = new JButton("Modifier Livre");
        supprimerLivreBtn = new JButton("Supprimer Livre");
        changerDisponibiliteBtn = new JButton("Changer Disponibilité");

        // Styliser les boutons
        styleButton(ajouterLivreBtn);
        styleButton(modifierLivreBtn);
        styleButton(supprimerLivreBtn);
        styleButton(changerDisponibiliteBtn);

        // Initialiser le champ de recherche
        searchLivresField = new JTextField(20);
        searchLivresField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        searchLivresField.setFont(new Font("Arial", Font.PLAIN, 14));

        // Initialiser le modèle de table et la table
        modelLivres = new DefaultTableModel(new Object[]{"ID", "Titre", "Auteur", "Année de Publication", "Genre", "Disponible"}, 0);
        tableLivres = new JTable(modelLivres);
        tableLivres.setFont(new Font("Arial", Font.PLAIN, 14));
        tableLivres.setRowHeight(25);
        tableLivres.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        tableLivres.getTableHeader().setBackground(new Color(0, 120, 215));
        tableLivres.getTableHeader().setForeground(Color.WHITE);

        // Ajouter les composants à la vue
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(240, 240, 240));
        buttonPanel.add(ajouterLivreBtn);
        buttonPanel.add(modifierLivreBtn);
        buttonPanel.add(supprimerLivreBtn);
        buttonPanel.add(changerDisponibiliteBtn);

        JPanel searchPanel = new JPanel();
        searchPanel.setBackground(new Color(240, 240, 240));
        searchPanel.add(new JLabel("Rechercher :"));
        searchPanel.add(searchLivresField);

        add(searchPanel, BorderLayout.NORTH);
        add(new JScrollPane(tableLivres), BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void styleButton(JButton button) {
        button.setBackground(new Color(0, 120, 215));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setFocusPainted(false);
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(0, 90, 170));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(0, 120, 215));
            }
        });
    }

    public JButton getAjouterLivreBtn() {
        return ajouterLivreBtn;
    }

    public JButton getModifierLivreBtn() {
        return modifierLivreBtn;
    }

    public JButton getSupprimerLivreBtn() {
        return supprimerLivreBtn;
    }

    public JButton getChangerDisponibiliteBtn() {
        return changerDisponibiliteBtn;
    }

    public JTextField getSearchLivresField() {
        return searchLivresField;
    }

    public DefaultTableModel getModelLivres() {
        return modelLivres;
    }
    
    public JTable getTableLivres() {
        return tableLivres;
    }
}
