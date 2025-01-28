package controller;

import model.Emprunt;

import model.EmpruntModel;
import model.Livre;
import model.LivreModel;
import model.UtilisateurModel;
import view.GestionBibliothequeView;
import exceptions.EmpruntNotFoundException;
import exceptions.CSVFileException;
import exceptions.LivreNotFoundException;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ControleurEmprunt {

    private EmpruntModel empruntModel;
    private LivreModel livreModel;
    private UtilisateurModel utilisateurModel;
    private GestionBibliothequeView vue;

    public ControleurEmprunt(EmpruntModel empruntModel, LivreModel livreModel, UtilisateurModel utilisateurModel, GestionBibliothequeView vue) throws CSVFileException {
        this.empruntModel = empruntModel;
        this.livreModel = livreModel;
        this.utilisateurModel = utilisateurModel;
        this.vue = vue;
        addListeners();
        empruntModel.lireCSV();
        refreshTable();
    }

    private void addListeners() {
        vue.getAjouterEmpruntBtn().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JPanel panel = createEmpruntInputPanel(null);
                int result = JOptionPane.showConfirmDialog(vue.getFrame(), panel, "Ajouter un Emprunt",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                if (result == JOptionPane.OK_OPTION) {
                    try {
                        Emprunt emprunt = extractEmpruntFromPanel(panel);

                       
                        if (!utilisateurModel.utilisateurExiste(emprunt.getUtilisateurId())) {
                            JOptionPane.showMessageDialog(vue.getFrame(), "Erreur : L'utilisateur n'existe pas.", "Erreur", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        if (!livreModel.livreExiste(emprunt.getLivreId())) {
                            JOptionPane.showMessageDialog(vue.getFrame(), "Erreur : Le livre n'existe pas.", "Erreur", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        if (!livreModel.estDisponible(emprunt.getLivreId())) {
                            JOptionPane.showMessageDialog(vue.getFrame(), "Erreur : Le livre est déjà emprunté.", "Erreur", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        empruntModel.ajouterEmprunt(emprunt);
                        empruntModel.sauvegraderCSV();

                        livreModel.changerDisponibilite(emprunt.getLivreId(), false);
                        livreModel.sauvegraderCSV();
                         

                        JOptionPane.showMessageDialog(vue.getFrame(), "Emprunt ajouté avec succès !");
                        refreshTable();
                        refreshTableLivre();
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(vue.getFrame(), "Erreur : Les IDs doivent être des nombres valides.", "Erreur", JOptionPane.ERROR_MESSAGE);
                    } catch (CSVFileException ex) {
                        JOptionPane.showMessageDialog(vue.getFrame(), "Erreur lors de la sauvegarde du fichier CSV : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
                    } catch (IllegalArgumentException ex) {
                        JOptionPane.showMessageDialog(vue.getFrame(), ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
                    } catch (LivreNotFoundException ex) {
                        JOptionPane.showMessageDialog(vue.getFrame(), "Erreur : Le livre n'existe pas.", "Erreur", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        vue.getProlongerEmpruntBtn().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int id = Integer.parseInt(JOptionPane.showInputDialog("Entrez l'ID de l'emprunt à prolonger :"));
                    Emprunt emprunt = empruntModel.rechercherParID(id);

                    JPanel panel = createProlongementInputPanel(emprunt);
                    int result = JOptionPane.showConfirmDialog(vue.getFrame(), panel, "Prolonger un Emprunt",
                            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                    if (result == JOptionPane.OK_OPTION) {
                        String nouvelleDateRetour = extractDateRetourFromPanel(panel);
                        empruntModel.prolongerEmprunt(id, nouvelleDateRetour);
                        empruntModel.sauvegraderCSV();
                        JOptionPane.showMessageDialog(vue.getFrame(), "Emprunt prolongé avec succès !");
                        refreshTable();
                        refreshTableLivre();
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(vue.getFrame(), "Erreur : L'ID doit être un nombre valide.", "Erreur", JOptionPane.ERROR_MESSAGE);
                } catch (EmpruntNotFoundException ex) {
                    JOptionPane.showMessageDialog(vue.getFrame(), "Erreur : L'emprunt n'existe pas.", "Erreur", JOptionPane.ERROR_MESSAGE);
                } catch (CSVFileException ex) {
                    JOptionPane.showMessageDialog(vue.getFrame(), "Erreur lors de la sauvegarde du fichier CSV : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(vue.getFrame(), ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        vue.getSupprimerEmpruntBtn().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int id = Integer.parseInt(JOptionPane.showInputDialog("Entrez l'ID de l'emprunt à supprimer :"));
                    Emprunt emprunt = empruntModel.rechercherParID(id);
                    empruntModel.supprimerEmprunt(id);
                    empruntModel.sauvegraderCSV();
                    livreModel.changerDisponibilite(emprunt.getLivreId(), true);
                    livreModel.sauvegraderCSV();
                    JOptionPane.showMessageDialog(vue.getFrame(), "Emprunt supprimé avec succès !");
                    refreshTable();
                    refreshTableLivre();

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(vue.getFrame(), "Erreur : L'ID doit être un nombre valide.", "Erreur", JOptionPane.ERROR_MESSAGE);
                } catch (EmpruntNotFoundException ex) {
                    JOptionPane.showMessageDialog(vue.getFrame(), "Erreur : L'emprunt n'existe pas.", "Erreur", JOptionPane.ERROR_MESSAGE);
                } catch (CSVFileException ex) {
                    JOptionPane.showMessageDialog(vue.getFrame(), "Erreur lors de la sauvegarde du fichier CSV : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
                } catch (LivreNotFoundException e1) {
					e1.printStackTrace();
				}
            }
        });

        vue.getSearchEmpruntsField().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchQuery = vue.getSearchEmpruntsField().getText();
                filterEmprunts(searchQuery);
            }
        });
    }

    private void refreshTable() {
        vue.getModelEmprunts().setRowCount(0); 
        for (Emprunt emprunt : empruntModel.getListe()) {
            vue.getModelEmprunts().addRow(new Object[]{emprunt.getId(), emprunt.getUtilisateurId(), emprunt.getLivreId(), emprunt.getDateEmprunt(), emprunt.getDateRetourPrevu()});
        }
    }
    
    public void refreshTableLivre() {
        
        vue.getModelLivres().setRowCount(0); 
        for (Livre livre : livreModel.getListe()) {
            vue.getModelLivres().addRow(new Object[]{livre.getId(), livre.getTitre(), livre.getAuteur(), livre.getAnneePublication(), livre.getGenre(), livre.isDisponible()});
        }
    }

    private void filterEmprunts(String query) {
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(vue.getModelEmprunts());
        vue.getTableEmprunts().setRowSorter(sorter);

        if (query.length() == 0) {
            sorter.setRowFilter(null);
        } else {
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + query));
        }
    }

    private JPanel createEmpruntInputPanel(Emprunt emprunt) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JTextField utilisateurIdField = new JTextField(emprunt != null ? String.valueOf(emprunt.getUtilisateurId()) : "");
        JTextField livreIdField = new JTextField(emprunt != null ? String.valueOf(emprunt.getLivreId()) : "");
        JTextField dateEmpruntField = new JTextField(emprunt != null ? emprunt.getDateEmprunt() : "");
        JTextField dateRetourPrevuField = new JTextField(emprunt != null ? emprunt.getDateRetourPrevu() : "");

        panel.add(new JLabel("ID Utilisateur :"));
        panel.add(utilisateurIdField);
        panel.add(new JLabel("ID Livre :"));
        panel.add(livreIdField);
        panel.add(new JLabel("Date d'Emprunt (YYYY-MM-DD) :"));
        panel.add(dateEmpruntField);
        panel.add(new JLabel("Date de Retour Prévue (YYYY-MM-DD) :"));
        panel.add(dateRetourPrevuField);

        panel.putClientProperty("utilisateurIdField", utilisateurIdField);
        panel.putClientProperty("livreIdField", livreIdField);
        panel.putClientProperty("dateEmpruntField", dateEmpruntField);
        panel.putClientProperty("dateRetourPrevuField", dateRetourPrevuField);

        return panel;
    }

    private JPanel createProlongementInputPanel(Emprunt emprunt) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JTextField dateRetourPrevuField = new JTextField(emprunt != null ? emprunt.getDateRetourPrevu() : "");

        panel.add(new JLabel("Nouvelle Date de Retour Prévue (YYYY-MM-DD) :"));
        panel.add(dateRetourPrevuField);

        panel.putClientProperty("dateRetourPrevuField", dateRetourPrevuField);

        return panel;
    }

    private Emprunt extractEmpruntFromPanel(JPanel panel) throws NumberFormatException, IllegalArgumentException {
        JTextField utilisateurIdField = (JTextField) panel.getClientProperty("utilisateurIdField");
        JTextField livreIdField = (JTextField) panel.getClientProperty("livreIdField");
        JTextField dateEmpruntField = (JTextField) panel.getClientProperty("dateEmpruntField");
        JTextField dateRetourPrevuField = (JTextField) panel.getClientProperty("dateRetourPrevuField");

        String utilisateurIdText = utilisateurIdField.getText().trim();
        String livreIdText = livreIdField.getText().trim();
        String dateEmprunt = dateEmpruntField.getText().trim();
        String dateRetourPrevu = dateRetourPrevuField.getText().trim();

        if (utilisateurIdText.isEmpty() || livreIdText.isEmpty() || dateEmprunt.isEmpty() || dateRetourPrevu.isEmpty()) {
            throw new IllegalArgumentException("Tous les champs doivent être remplis.");
        }

        if (!isValidDate(dateEmprunt) || !isValidDate(dateRetourPrevu)) {
            throw new IllegalArgumentException("Les dates doivent être au format YYYY-MM-DD.");
        }

        int utilisateurId = Integer.parseInt(utilisateurIdText);
        int livreId = Integer.parseInt(livreIdText);

        return new Emprunt(utilisateurId, livreId, dateEmprunt, dateRetourPrevu);
    }

    private String extractDateRetourFromPanel(JPanel panel) throws IllegalArgumentException {
        JTextField dateRetourPrevuField = (JTextField) panel.getClientProperty("dateRetourPrevuField");
        String dateRetourPrevu = dateRetourPrevuField.getText().trim();

        if (dateRetourPrevu.isEmpty()) {
            throw new IllegalArgumentException("La date de retour prévue doit être remplie.");
        }

        if (!isValidDate(dateRetourPrevu)) {
            throw new IllegalArgumentException("La date doit être au format YYYY-MM-DD.");
        }

        return dateRetourPrevu;
    }

    private boolean isValidDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            LocalDate.parse(date, formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
    
    
}
