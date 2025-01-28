package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class VueUtilisateur extends JPanel {
    private JButton ajouterUtilisateurBtn;
    private JButton modifierUtilisateurBtn;
    private JButton supprimerUtilisateurBtn;
    private JTextField searchUtilisateursField;
    private DefaultTableModel modelUtilisateurs;
    private JTable tableUtilisateurs;

    public VueUtilisateur() {
        // Initialiser les composants de la vue
        setLayout(new BorderLayout());
        setBackground(new Color(240, 240, 240));

        // Initialiser les boutons
        ajouterUtilisateurBtn = new JButton("Ajouter Utilisateur");
        modifierUtilisateurBtn = new JButton("Modifier Utilisateur");
        supprimerUtilisateurBtn = new JButton("Supprimer Utilisateur");

        // Styliser les boutons
        styleButton(ajouterUtilisateurBtn);
        styleButton(modifierUtilisateurBtn);
        styleButton(supprimerUtilisateurBtn);

        // Initialiser le champ de recherche
        searchUtilisateursField = new JTextField(20);
        searchUtilisateursField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        searchUtilisateursField.setFont(new Font("Arial", Font.PLAIN, 14));

        // Initialiser le modèle de table et la table
        modelUtilisateurs = new DefaultTableModel(new Object[]{"ID", "Nom", "Prénom", "Rôle"}, 0);
        tableUtilisateurs = new JTable(modelUtilisateurs);
        tableUtilisateurs.setFont(new Font("Arial", Font.PLAIN, 14));
        tableUtilisateurs.setRowHeight(25);
        tableUtilisateurs.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        tableUtilisateurs.getTableHeader().setBackground(new Color(0, 120, 215));
        tableUtilisateurs.getTableHeader().setForeground(Color.WHITE);

        // Ajouter les composants à la vue
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(240, 240, 240));
        buttonPanel.add(ajouterUtilisateurBtn);
        buttonPanel.add(modifierUtilisateurBtn);
        buttonPanel.add(supprimerUtilisateurBtn);

        JPanel searchPanel = new JPanel();
        searchPanel.setBackground(new Color(240, 240, 240));
        searchPanel.add(new JLabel("Rechercher :"));
        searchPanel.add(searchUtilisateursField);

        add(searchPanel, BorderLayout.NORTH);
        add(new JScrollPane(tableUtilisateurs), BorderLayout.CENTER);
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

    public JButton getAjouterUtilisateurBtn() {
        return ajouterUtilisateurBtn;
    }

    public JButton getModifierUtilisateurBtn() {
        return modifierUtilisateurBtn;
    }

    public JButton getSupprimerUtilisateurBtn() {
        return supprimerUtilisateurBtn;
    }

    public JTextField getSearchUtilisateursField() {
        return searchUtilisateursField;
    }

    public DefaultTableModel getModelUtilisateurs() {
        return modelUtilisateurs;
    }
    
    public JTable getTableUtilisateur() {
        return tableUtilisateurs;
    }
}
