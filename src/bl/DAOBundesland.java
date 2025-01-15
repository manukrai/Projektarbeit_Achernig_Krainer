package bl;

import beans.Bundesland;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DAOBundesland {

    private static final Logger logger = Logger.getLogger(DAOBundesland.class.getName());

    /**
     * Liefer alle Bundeslaender zurück.
     *
     * @return Liste aller Bundeslaender.
     */
    public static List<Bundesland> getAllBundeslaender() {
        List<Bundesland> liste = new ArrayList<>();
        String query = "SELECT BundeslandID, Bezeichnung FROM bundesland";

        if (DBAccess.connection == null) {
            logger.severe("Keine Verbindung zur Datenbank verfügbar.");
            return liste;
        }
        try (
                PreparedStatement statement = DBAccess.connection.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("BundeslandID");
                String bezeichnung = resultSet.getString("Bezeichnung");
                liste.add(new Bundesland(id, bezeichnung));
            }
        } catch (SQLException ex) {
            logger.setLevel(Level.ALL);
            logger.severe(ex.getMessage());
        }

        return liste;
    }

    /**
     * Fügt ein neues Bundesland hinzu.
     *
     * @param bezeichnung
     */
    public static void addBundesland(String bezeichnung) {
        String query = "INSERT INTO bundesland (Bezeichnung) VALUES (?)";

        if (DBAccess.connection == null) {
            logger.severe("Keine Verbindung zur Datenbank verfügbar.");
        } else {
            try (PreparedStatement statement = DBAccess.connection.prepareStatement(query)) {

                statement.setString(1, bezeichnung);
                statement.executeUpdate();

            } catch (SQLException ex) {
                logger.setLevel(Level.ALL);
                logger.severe(ex.getMessage());
            }
        }

    }

    /**
     * Liefert alle Bundesländer zurück (asynchron).
     *
     * @return Ein CompletableFuture, das eine Liste aller Bundesländer liefert.
     */
    public static CompletableFuture<List<Bundesland>> getAllBundeslaenderAsync() {
        return CompletableFuture.supplyAsync(() -> {
            logger.info("Starte asynchrone Abfrage für alle Bundesländer.");
            return getAllBundeslaender(); // Nutzt die synchrone Methode
        }).exceptionally(ex -> {
            logger.log(Level.SEVERE, "Fehler bei der asynchronen Datenbankabfrage: {0}", ex.getMessage());
            return new ArrayList<>(); // Leere Liste bei Fehler
        });
    }

    /**
     * Fügt ein neues Bundesland hinzu (asynchron).
     *
     * @param bezeichnung
     * @return Ein CompletableFuture, das den Abschluss der Datenbankoperation signalisiert.
     */
    public static CompletableFuture<Void> addBundeslandAsync(String bezeichnung) {
        return CompletableFuture.runAsync(() -> {
            logger.info("Füge asynchron ein neues Bundesland hinzu: " + bezeichnung);
            addBundesland(bezeichnung); // Nutzt die synchrone Methode
        }).exceptionally(ex -> {
            logger.log(Level.SEVERE, "Fehler bei der asynchronen Datenbankoperation: {0}", ex.getMessage());
            return null;
        });
    }
}
