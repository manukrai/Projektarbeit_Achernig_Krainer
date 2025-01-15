package bl;

import beans.Befund;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DAOBefund {

    /**
     * Hier bekommt man die Befunde welche zu einem Patienten gehören.
     * @param patientID
     * @return eine Liste aller Befunden, welche mit dem Patient verbunden sind.
     */
    public static List<Befund> getBefundeByPatientID(int patientID) {
        List<Befund> liste = new ArrayList<>();
        String query = "SELECT BefundID, PatientID, Pfad, Datum FROM befund WHERE PatientID = ?";

        if(DBAccess.connection != null)
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
            Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
            logger.setLevel(Level.ALL);
            logger.severe(ex.getMessage());
        }

        return liste;
    }

    /**
     * Fügt einen neuen Befund einem Patienten hinzu
     * @param patientID
     * @param pfad
     * @param datum
     */
    public static void addBefund(int patientID, String pfad, LocalDate datum) {
        String query = "INSERT INTO befund (PatientID, Pfad, Datum) VALUES (?, ?, ?)";

        if(DBAccess.connection != null)
        try (
             PreparedStatement statement = DBAccess.connection.prepareStatement(query)) {

            statement.setInt(1, patientID);
            statement.setString(2, pfad);
            statement.setDate(3, Date.valueOf(datum));

            statement.executeUpdate();
        }
        catch (SQLException ex) {
            Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
            logger.setLevel(Level.ALL);
            logger.severe(ex.getMessage());
        }
    }


}
