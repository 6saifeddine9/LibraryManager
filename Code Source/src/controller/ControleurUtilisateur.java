package controller;

import model.Utilisateur;
import model.UtilisateurModel;
import view.GestionBibliothequeView;
import exceptions.UtilisateurNotFoundException;
import exceptions.CSVFileException;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControleurUtilisateur {

    private UtilisateurModel utilisateurModel;
    private GestionBibliothequeView vue;

    public ControleurUtilisateur(UtilisateurModel utilisateurModel, GestionBibliothequeView vue) throws CSVFileException {
        this.utilisateurModel = utilisateurModel;
        this.vue = vue;
        addListeners();
        utilisateurModel.lireCSV();
        refreshTable();
    }

    private void addListeners() {
        // Action pour le bouton Ajouter
        vue.getAjouterUtilisateurBtn().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JPanel panel = createUtilisateurInputPanel(null);
                int result = JOptionPane.showConfirmDialog(vue.getFrame(), panel, "Ajouter un Utilisateur",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                if (result == JOptionPane.OK_OPTION) {
                    try {
                        Utilisateur utilisateur = extractUtilisateurFromPanel(panel);
                        utilisateurModel.ajouterUtilisateur(utilisateur);

                        utilisateurModel.sauvegraderCSV();
                        JOptionPane.showMessageDialog(vue.getFrame(), "Utilisateur ajouté avec succès !");
                        refreshTable();
                    } catch (IllegalArgumentException ex) {
                        JOptionPane.showMessageDialog(vue.getFrame(), ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
                    } catch (CSVFileException ex) {
                        JOptionPane.showMessageDialog(vue.getFrame(), "Erreur lors de la sauvegarde du fichier CSV : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        // Action pour le bouton Modifier
        vue.getModifierUtilisateurBtn().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int id = Integer.parseInt(JOptionPane.showInputDialog("Entrez l'ID de l'utilisateur à modifier :"));
                    Utilisateur utilisateur = utilisateurModel.rechercherParID(id);

                    JPanel panel = createUtilisateurInputPanel(utilisateur);
                    int result = JOptionPane.showConfirmDialog(vue.getFrame(), panel, "Modifier un Utilisateur",
                            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                    if (result == JOptionPane.OK_OPTION) {
                        Utilisateur updatedUtilisateur = extractUtilisateurFromPanel(panel);
                        utilisateurModel.modifierUtilisateur(updatedUtilisateur.getNom(), updatedUtilisateur.getPrenom(),
                                updatedUtilisateur.getRole(), id);

                        utilisateurModel.sauvegraderCSV(); // Sauvegarder après modification
                        JOptionPane.showMessageDialog(vue.getFrame(), "Utilisateur modifié avec succès !");
                        refreshTable();
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(vue.getFrame(), "Erreur : l'ID doit être un nombre valide.", "Erreur", JOptionPane.ERROR_MESSAGE);
                } catch (UtilisateurNotFoundException ex) {
                    JOptionPane.showMessageDialog(vue.getFrame(), "Utilisateur non trouvé !", "Erreur", JOptionPane.ERROR_MESSAGE);
                } catch (CSVFileException ex) {
                    JOptionPane.showMessageDialog(vue.getFrame(), "Erreur lors de la sauvegarde du fichier CSV : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(vue.getFrame(), ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Action pour le bouton Supprimer
        vue.getSupprimerUtilisateurBtn().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int id = Integer.parseInt(JOptionPane.showInputDialog("Entrez l'ID de l'utilisateur à supprimer :"));
                    utilisateurModel.supprimerUtilisateur(id);

                    utilisateurModel.sauvegraderCSV(); // Sauvegarder après suppression
                    JOptionPane.showMessageDialog(vue.getFrame(), "Utilisateur supprimé avec succès !");
                    refreshTable();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(vue.getFrame(), "Erreur : l'ID doit être un nombre valide.", "Erreur", JOptionPane.ERROR_MESSAGE);
                } catch (UtilisateurNotFoundException ex) {
                    JOptionPane.showMessageDialog(vue.getFrame(), "Utilisateur non trouvé !", "Erreur", JOptionPane.ERROR_MESSAGE);
                } catch (CSVFileException ex) {
                    JOptionPane.showMessageDialog(vue.getFrame(), "Erreur lors de la sauvegarde du fichier CSV : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Action pour la recherche des utilisateurs
        vue.getSearchUtilisateursField().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchQuery = vue.getSearchUtilisateursField().getText();
                filterUtilisateurs(searchQuery);
            }
        });
    }

    private void refreshTable() {
        // Rafraîchissement des données de la table
        vue.getModelUtilisateurs().setRowCount(0); // Effacer les anciennes données
        for (Utilisateur utilisateur : utilisateurModel.getListe()) {
            vue.getModelUtilisateurs().addRow(new Object[]{utilisateur.getId(), utilisateur.getNom(), utilisateur.getPrenom(), utilisateur.getRole()});
        }
    }

    private void filterUtilisateurs(String query) {
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(vue.getModelUtilisateurs());
        vue.getTableUtilisateurs().setRowSorter(sorter);

        if (query.length() == 0) {
            sorter.setRowFilter(null);
        } else {
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + query));
        }
    }

    private JPanel createUtilisateurInputPanel(Utilisateur utilisateur) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JTextField nomField = new JTextField(utilisateur != null ? utilisateur.getNom() : "");
        JTextField prenomField = new JTextField(utilisateur != null ? utilisateur.getPrenom() : "");
        JTextField roleField = new JTextField(utilisateur != null ? utilisateur.getRole() : "");

        panel.add(new JLabel("Nom :"));
        panel.add(nomField);
        panel.add(new JLabel("Prénom :"));
        panel.add(prenomField);
        panel.add(new JLabel("Rôle :"));
        panel.add(roleField);

        panel.putClientProperty("nomField", nomField);
        panel.putClientProperty("prenomField", prenomField);
        panel.putClientProperty("roleField", roleField);

        return panel;
    }

    private Utilisateur extractUtilisateurFromPanel(JPanel panel) throws IllegalArgumentException {
        JTextField nomField = (JTextField) panel.getClientProperty("nomField");
        JTextField prenomField = (JTextField) panel.getClientProperty("prenomField");
        JTextField roleField = (JTextField) panel.getClientProperty("roleField");

        String nom = nomField.getText().trim();
        String prenom = prenomField.getText().trim();
        String role = roleField.getText().trim();

        if (nom.isEmpty() || prenom.isEmpty() || role.isEmpty()) {
            throw new IllegalArgumentException("Tous les champs doivent être remplis.");
        }

        return new Utilisateur(nom, prenom, role);
    }
}
