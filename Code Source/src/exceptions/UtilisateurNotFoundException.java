package exceptions;

public class UtilisateurNotFoundException extends Exception {
    public UtilisateurNotFoundException(int id) {
        super("Utilisateur avec l'ID " + id + " non trouvé.");
    }
}
