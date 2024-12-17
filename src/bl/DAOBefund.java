package bl;

import beans.Befund;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class DAOBefund {

    public static List<Befund> getBefundeByPatientID(int patientID) {
        List<Befund> liste = new ArrayList<>();
        String query = "SELECT BefundID, PatientID, Pfad, Datum FROM befund WHERE PatientID = ?";

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

        } catch (SQLException e) {
            System.err.println("Fehler beim Abrufen der Befunde für PatientID " + patientID + ": " + e.getMessage());
        }

        return liste;
    }

    public static void addBefund(int patientID, String pfad, LocalDate datum) {
        String query = "INSERT INTO befund (PatientID, Pfad, Datum) VALUES (?, ?, ?)";

        try (
             PreparedStatement statement = DBAccess.connection.prepareStatement(query)) {

            statement.setInt(1, patientID);
            statement.setString(2, pfad);
            statement.setDate(3, Date.valueOf(datum));

            statement.executeUpdate();
            System.out.println("Befund erfolgreich hinzugefügt: PatientID " + patientID + ", Pfad: " + pfad);

        } catch (SQLException e) {
            System.err.println("Fehler beim Hinzufügen des Befunds: " + e.getMessage());
        }
    }


}
