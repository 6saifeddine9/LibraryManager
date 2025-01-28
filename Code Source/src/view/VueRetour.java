package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class VueRetour extends JPanel {
    private JButton ajouterRetourBtn;
    private JButton supprimerRetourBtn;
    private JTextField searchRetoursField;
    private DefaultTableModel modelRetours;
    private JTable tableRetours;

    public VueRetour() {
        // Initialiser les composants de la vue
        setLayout(new BorderLayout());
        setBackground(new Color(240, 240, 240));

        // Initialiser les boutons
        ajouterRetourBtn = new JButton("Ajouter Retour");
        supprimerRetourBtn = new JButton("Supprimer Retour");

        // Styliser les boutons
        styleButton(ajouterRetourBtn);
        styleButton(supprimerRetourBtn);

        // Initialiser le champ de recherche
        searchRetoursField = new JTextField(20);
        searchRetoursField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        searchRetoursField.setFont(new Font("Arial", Font.PLAIN, 14));

        // Initialiser le modèle de table et la table
        modelRetours = new DefaultTableModel(new Object[]{"ID", "Emprunt ID", "Date de Retour", "Pénalité"}, 0);
        tableRetours = new JTable(modelRetours);
        tableRetours.setFont(new Font("Arial", Font.PLAIN, 14));
        tableRetours.setRowHeight(25);
        tableRetours.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        tableRetours.getTableHeader().setBackground(new Color(0, 120, 215));
        tableRetours.getTableHeader().setForeground(Color.WHITE);

        // Ajouter les composants à la vue
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(240, 240, 240));
        buttonPanel.add(ajouterRetourBtn);
        buttonPanel.add(supprimerRetourBtn);

        JPanel searchPanel = new JPanel();
        searchPanel.setBackground(new Color(240, 240, 240));
        searchPanel.add(new JLabel("Rechercher :"));
        searchPanel.add(searchRetoursField);

        add(searchPanel, BorderLayout.NORTH);
        add(new JScrollPane(tableRetours), BorderLayout.CENTER);
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

    public JButton getAjouterRetourBtn() {
        return ajouterRetourBtn;
    }

    public JButton getSupprimerRetourBtn() {
        return supprimerRetourBtn;
    }

    public JTextField getSearchRetoursField() {
        return searchRetoursField;
    }

    public DefaultTableModel getModelRetours() {
        return modelRetours;
    }
    
    public JTable getTableRetours() {
        return tableRetours;
    }
}
