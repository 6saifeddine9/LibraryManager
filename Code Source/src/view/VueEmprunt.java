package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class VueEmprunt extends JPanel {
    private JButton ajouterEmpruntBtn;
    private JButton prolongerEmpruntBtn;
    private JButton supprimerEmpruntBtn;
    private JTextField searchEmpruntsField;
    private DefaultTableModel modelEmprunts;
    private JTable tableEmprunts;

    public VueEmprunt() {
        // Initialiser les composants de la vue
        setLayout(new BorderLayout());
        setBackground(new Color(240, 240, 240));

        // Initialiser les boutons
        ajouterEmpruntBtn = new JButton("Ajouter Emprunt");
        prolongerEmpruntBtn = new JButton("Prolonger Emprunt");
        supprimerEmpruntBtn = new JButton("Supprimer Emprunt");

        // Styliser les boutons
        styleButton(ajouterEmpruntBtn);
        styleButton(prolongerEmpruntBtn);
        styleButton(supprimerEmpruntBtn);

        // Initialiser le champ de recherche
        searchEmpruntsField = new JTextField(20);
        searchEmpruntsField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        searchEmpruntsField.setFont(new Font("Arial", Font.PLAIN, 14));

        // Initialiser le modèle de table et la table
        modelEmprunts = new DefaultTableModel(new Object[]{"ID", "Utilisateur ID", "Livre ID", "Date d'Emprunt", "Date de Retour Prévue"}, 0);
        tableEmprunts = new JTable(modelEmprunts);
        tableEmprunts.setFont(new Font("Arial", Font.PLAIN, 14));
        tableEmprunts.setRowHeight(25);
        tableEmprunts.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        tableEmprunts.getTableHeader().setBackground(new Color(0, 120, 215));
        tableEmprunts.getTableHeader().setForeground(Color.WHITE);

        // Ajouter les composants à la vue
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(240, 240, 240));
        buttonPanel.add(ajouterEmpruntBtn);
        buttonPanel.add(prolongerEmpruntBtn);
        buttonPanel.add(supprimerEmpruntBtn);

        JPanel searchPanel = new JPanel();
        searchPanel.setBackground(new Color(240, 240, 240));
        searchPanel.add(new JLabel("Rechercher :"));
        searchPanel.add(searchEmpruntsField);

        add(searchPanel, BorderLayout.NORTH);
        add(new JScrollPane(tableEmprunts), BorderLayout.CENTER);
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

    public JButton getAjouterEmpruntBtn() {
        return ajouterEmpruntBtn;
    }

    public JButton getProlongerEmpruntBtn() {
        return prolongerEmpruntBtn;
    }

    public JButton getSupprimerEmpruntBtn() {
        return supprimerEmpruntBtn;
    }

    public JTextField getSearchEmpruntsField() {
        return searchEmpruntsField;
    }

    public DefaultTableModel getModelEmprunts() {
        return modelEmprunts;
    }

    public JTable getTableEmprunts() {
        return tableEmprunts;
    }
    
    
}
