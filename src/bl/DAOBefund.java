package bl;

import beans.Befund;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DAOBefund {

    private static final Logger logger = Logger.getLogger(DAOBefund.class.getName());

    /**
     * Hier bekommt man die Befunde welche zu einem Patienten gehören.
     *
     * @param patientID
     * @return eine Liste aller Befunden, welche mit dem Patient verbunden sind.
     */
    public static List<Befund> getBefundeByPatientID(int patientID) {
        List<Befund> liste = new ArrayList<>();
        String query = "SELECT BefundID, PatientID, Pfad, Datum FROM befund WHERE PatientID = ?";

        if (DBAccess.connection == null) {
            logger.severe("Keine Verbindung zur Datenbank verfügbar.");
            return liste;
        }
        try (
                PreparedStatement statement = DBAccess.connection.prepareStatement(query)) {

            statement.setInt(1, patientID);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int befundID = resultSet.getInt("BefundID");
                    String pfad = resultSet.getString("Pfad");
                    Date datum = resultSet.getDate("Datum");

                    liste.add(new Befund(befundID, patientID, pfad, datum.toString()));
                }
            }

        } catch (SQLException ex) {
            logger.setLevel(Level.ALL);
            logger.severe(ex.getMessage());
        }

        return liste;
    }

    /**
     * Fügt einen neuen Befund einem Patienten hinzu
     *
     * @param patientID
     * @param pfad
     * @param datum
     */
    public static void addBefund(int patientID, String pfad, LocalDate datum) {
        String query = "INSERT INTO befund (PatientID, Pfad, Datum) VALUES (?, ?, ?)";

        if (DBAccess.connection == null) {
            logger.severe("Keine Verbindung zur Datenbank verfügbar.");
        } else {
            try (
                    PreparedStatement statement = DBAccess.connection.prepareStatement(query)) {

                statement.setInt(1, patientID);
                statement.setString(2, pfad);
                statement.setDate(3, Date.valueOf(datum));

                statement.executeUpdate();
            } catch (SQLException ex) {
                logger.setLevel(Level.ALL);
                logger.severe(ex.getMessage());
            }
        }

    }

    /**
     * Hier bekommt man die Befunde welche zu einem Patienten gehören (asynchron).
     *
     * @param patientID
     * @return Ein CompletableFuture, das eine Liste aller Befunde liefert.
     */
    public static CompletableFuture<List<Befund>> getBefundeByPatientIDAsync(int patientID) {
        return CompletableFuture.supplyAsync(() -> {
            logger.info("Starte asynchrone Abfrage für Befunde des Patienten: " + patientID);
            return getBefundeByPatientID(patientID); // Nutzt die synchrone Methode
        }).exceptionally(ex -> {
            logger.log(Level.SEVERE, "Fehler bei der asynchronen Datenbankabfrage: {0}", ex.getMessage());
            return new ArrayList<>(); // Leere Liste bei Fehler
        });
    }

    /**
     * Fügt einen neuen Befund einem Patienten hinzu (asynchron).
     *
     * @param patientID
     * @param pfad
     * @param datum
     * @return Ein CompletableFuture, das den Abschluss der Datenbankoperation signalisiert.
     */
    public static CompletableFuture<Void> addBefundAsync(int patientID, String pfad, LocalDate datum) {
        return CompletableFuture.runAsync(() -> {
            logger.info("Füge asynchronen Befund für Patient " + patientID + " hinzu.");
            addBefund(patientID, pfad, datum); // Nutzt die synchrone Methode
        }).exceptionally(ex -> {
            logger.log(Level.SEVERE, "Fehler bei der asynchronen Datenbankoperation: {0}", ex.getMessage());
            return null;
        });
    }


}
