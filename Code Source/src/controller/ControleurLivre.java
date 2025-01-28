package controller;

import model.Livre;
import model.LivreModel;
import view.GestionBibliothequeView;
import exceptions.LivreNotFoundException;
import exceptions.CSVFileException;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControleurLivre {

    private LivreModel livreModel;
    private GestionBibliothequeView vue;

    public ControleurLivre(LivreModel livreModel, GestionBibliothequeView vue) throws CSVFileException {
        this.livreModel = livreModel;
        this.vue = vue;
        addListeners();
        livreModel.lireCSV();
        refreshTable();
    }

    private void addListeners() {
        vue.getAjouterLivreBtn().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JPanel panel = createLivreInputPanel(null);
                int result = JOptionPane.showConfirmDialog(vue.getFrame(), panel, "Ajouter un Livre",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                if (result == JOptionPane.OK_OPTION) {
                    try {
                        Livre livre = extractLivreFromPanel(panel);
                        livreModel.ajouterLivre(livre);

                        livreModel.sauvegraderCSV(); 
                        JOptionPane.showMessageDialog(vue.getFrame(), "Livre ajouté avec succès !");
                        refreshTable();
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(vue.getFrame(), "Erreur : l'année doit être un nombre valide.", "Erreur", JOptionPane.ERROR_MESSAGE);
                    } catch (CSVFileException ex) {
                        JOptionPane.showMessageDialog(vue.getFrame(), "Erreur lors de la sauvegarde du fichier CSV : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
                    } catch (IllegalArgumentException ex) {
                        JOptionPane.showMessageDialog(vue.getFrame(), ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        vue.getModifierLivreBtn().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int id = Integer.parseInt(JOptionPane.showInputDialog("Entrez l'ID du livre à modifier :"));
                    Livre livre = livreModel.rechercherParID(id);

                    JPanel panel = createLivreInputPanel(livre);
                    int result = JOptionPane.showConfirmDialog(vue.getFrame(), panel, "Modifier un Livre",
                            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                    if (result == JOptionPane.OK_OPTION) {
                        Livre updatedLivre = extractLivreFromPanel(panel);
                        livreModel.modifierLivre(updatedLivre.getTitre(), updatedLivre.getAuteur(),
                                updatedLivre.getAnneePublication(), updatedLivre.getGenre(), updatedLivre.isDisponible(), id);

                        livreModel.sauvegraderCSV(); 
                        JOptionPane.showMessageDialog(vue.getFrame(), "Livre modifié avec succès !");
                        refreshTable();
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(vue.getFrame(), "Erreur : l'ID ou l'année doit être un nombre valide.", "Erreur", JOptionPane.ERROR_MESSAGE);
                } catch (LivreNotFoundException ex) {
                    JOptionPane.showMessageDialog(vue.getFrame(), "Livre non trouvé !", "Erreur", JOptionPane.ERROR_MESSAGE);
                } catch (CSVFileException ex) {
                    JOptionPane.showMessageDialog(vue.getFrame(), "Erreur lors de la sauvegarde du fichier CSV : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(vue.getFrame(), ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        vue.getSupprimerLivreBtn().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int id = Integer.parseInt(JOptionPane.showInputDialog("Entrez l'ID du livre à supprimer :"));
                    livreModel.supprimerLivre(id);

                    livreModel.sauvegraderCSV(); 
                    JOptionPane.showMessageDialog(vue.getFrame(), "Livre supprimé avec succès !");
                    refreshTable();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(vue.getFrame(), "Erreur : l'ID doit être un nombre valide.", "Erreur", JOptionPane.ERROR_MESSAGE);
                } catch (LivreNotFoundException ex) {
                    JOptionPane.showMessageDialog(vue.getFrame(), "Livre non trouvé !", "Erreur", JOptionPane.ERROR_MESSAGE);
                } catch (CSVFileException ex) {
                    JOptionPane.showMessageDialog(vue.getFrame(), "Erreur lors de la sauvegarde du fichier CSV : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        vue.getSearchLivresField().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchQuery = vue.getSearchLivresField().getText();
                filterLivres(searchQuery);
            }
        });

        vue.getChangerDisponibiliteBtn().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int id = Integer.parseInt(JOptionPane.showInputDialog("Entrez l'ID du livre à modifier :"));
                    boolean disponible = JOptionPane.showConfirmDialog(vue.getFrame(), "Le livre est-il disponible ?", "Disponibilité", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
                    livreModel.changerDisponibilite(id, disponible);

                    livreModel.sauvegraderCSV(); 
                    JOptionPane.showMessageDialog(vue.getFrame(), "Disponibilité du livre modifiée avec succès !");
                    refreshTable();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(vue.getFrame(), "Erreur : l'ID doit être un nombre valide.", "Erreur", JOptionPane.ERROR_MESSAGE);
                } catch (LivreNotFoundException ex) {
                    JOptionPane.showMessageDialog(vue.getFrame(), "Livre non trouvé !", "Erreur", JOptionPane.ERROR_MESSAGE);
                } catch (CSVFileException ex) {
                    JOptionPane.showMessageDialog(vue.getFrame(), "Erreur lors de la sauvegarde du fichier CSV : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    public void refreshTable() {
        vue.getModelLivres().setRowCount(0); 
        for (Livre livre : livreModel.getListe()) {
            vue.getModelLivres().addRow(new Object[]{livre.getId(), livre.getTitre(), livre.getAuteur(), livre.getAnneePublication(), livre.getGenre(), livre.isDisponible()});
        }
    }

    private void filterLivres(String query) {
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(vue.getModelLivres());
        vue.getTableLivres().setRowSorter(sorter);

        if (query.length() == 0) {
            sorter.setRowFilter(null);
        } else {
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + query));
        }
    }

    private JPanel createLivreInputPanel(Livre livre) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JTextField titreField = new JTextField(livre != null ? livre.getTitre() : "");
        JTextField auteurField = new JTextField(livre != null ? livre.getAuteur() : "");
        JTextField genreField = new JTextField(livre != null ? livre.getGenre() : "");
        JTextField anneeField = new JTextField(livre != null ? String.valueOf(livre.getAnneePublication()) : "");
        JCheckBox disponibleCheckBox = new JCheckBox("Disponible", livre != null ? livre.isDisponible() : true);

        panel.add(new JLabel("Titre :"));
        panel.add(titreField);
        panel.add(new JLabel("Auteur :"));
        panel.add(auteurField);
        panel.add(new JLabel("Genre :"));
        panel.add(genreField);
        panel.add(new JLabel("Année de publication :"));
        panel.add(anneeField);
        panel.add(new JLabel("Disponible :"));
        panel.add(disponibleCheckBox);

        panel.putClientProperty("titreField", titreField);
        panel.putClientProperty("auteurField", auteurField);
        panel.putClientProperty("genreField", genreField);
        panel.putClientProperty("anneeField", anneeField);
        panel.putClientProperty("disponibleCheckBox", disponibleCheckBox);

        return panel;
    }

    private Livre extractLivreFromPanel(JPanel panel) throws NumberFormatException, IllegalArgumentException {
        JTextField titreField = (JTextField) panel.getClientProperty("titreField");
        JTextField auteurField = (JTextField) panel.getClientProperty("auteurField");
        JTextField genreField = (JTextField) panel.getClientProperty("genreField");
        JTextField anneeField = (JTextField) panel.getClientProperty("anneeField");
        JCheckBox disponibleCheckBox = (JCheckBox) panel.getClientProperty("disponibleCheckBox");

        String titre = titreField.getText().trim();
        String auteur = auteurField.getText().trim();
        String genre = genreField.getText().trim();
        String anneePublicationText = anneeField.getText().trim();

        if (titre.isEmpty() || auteur.isEmpty() || genre.isEmpty() || anneePublicationText.isEmpty()) {
            throw new IllegalArgumentException("Tous les champs doivent être remplis.");
        }

        int anneePublication = Integer.parseInt(anneePublicationText);
        boolean disponible = disponibleCheckBox.isSelected();

        return new Livre(titre, auteur, anneePublication, genre, disponible);
    }
}
