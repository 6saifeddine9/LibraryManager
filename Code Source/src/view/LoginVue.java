package view;

import controller.ControleurAuth;
import model.UtilisateurModel;
import view.GestionBibliothequeView;
import exceptions.AuthException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginVue extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;

    private ControleurAuth controleurAuth;
    private GestionBibliothequeView vuePrincipale;

    public LoginVue(ControleurAuth controleurAuth, GestionBibliothequeView vuePrincipale) {
        this.controleurAuth = controleurAuth;
        this.vuePrincipale = vuePrincipale;

        setTitle("Connexion");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Création des composants
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(new Color(240, 240, 240));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;

        // Titre
        JLabel titleLabel = new JLabel("Connexion");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        panel.add(titleLabel, gbc);

        // Nom d'utilisateur
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(new JLabel("Nom d'utilisateur :"), gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        usernameField = new JTextField(15);
        usernameField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        panel.add(usernameField, gbc);

        // Mot de passe
        gbc.gridy = 2;
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(new JLabel("Mot de passe :"), gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        passwordField = new JPasswordField(15);
        passwordField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        panel.add(passwordField, gbc);

        // Bouton de connexion
        gbc.gridy = 3;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        loginButton = new JButton("Se connecter");
        loginButton.setBackground(new Color(0, 120, 215));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        loginButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        loginButton.setFocusPainted(false);
        loginButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                loginButton.setBackground(new Color(0, 90, 170));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                loginButton.setBackground(new Color(0, 120, 215));
            }
        });
        panel.add(loginButton, gbc);

        // Ajouter le panel à la fenêtre
        add(panel, BorderLayout.CENTER);

        // Action du bouton de connexion
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                try {
                    controleurAuth.authentifier(username, password);
                    JOptionPane.showMessageDialog(panel, "Connexion réussie");
                    dispose(); // Fermer la fenêtre de connexion
                    vuePrincipale.setVisible(true);
                } catch (AuthException ex) {
                    JOptionPane.showMessageDialog(panel, ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        setVisible(true);
    }
}
