package bl;

import beans.Patient;

import java.sql.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

public class DAOPatient {


    public static List<Patient> getAllPatients() {
        List<Patient> patients = new LinkedList<>();
        String query = """
            SELECT 
                p.PatientID, p.Vorname, p.Nachname, p.Anrede, p.Geburtsdatum, 
                p.Strasse, p.PLZ, p.Ort, p.Telefon, p.Sonstiges, 
                b.bundeslandid AS Bundesland, 
                g.geschlechtid AS Geschlecht, 
                k.krankenkasseid AS Krankenkasse
            FROM patient p
            LEFT JOIN bundesland b ON p.Bundesland = b.BundeslandID
            LEFT JOIN geschlecht g ON p.GeschlechtID = g.GeschlechtID
            LEFT JOIN krankenkasse k ON p.Krankenkasse = k.KrankenkasseID;
        """;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        try (PreparedStatement stmt = DBAccess.connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                // Erstelle ein Patient-Objekt und setze die Werte
                Patient patient = new Patient();
                patient.setPatientID(rs.getInt("PatientID"));
                patient.setVorname(rs.getString("Vorname"));
                patient.setNachname(rs.getString("Nachname"));
                patient.setAnrede(rs.getString("Anrede"));
                if(rs.getString("geburtsdatum")!=null)
                patient.setGeburtsdatum(rs.getString("Geburtsdatum"));
                patient.setStrasse(rs.getString("Strasse"));
                patient.setPlz(rs.getString("PLZ"));
                patient.setOrt(rs.getString("Ort"));
                patient.setTelefon(rs.getString("Telefon"));
                patient.setSonstiges(rs.getString("Sonstiges"));

                patient.setBundeslandID(rs.getInt("Bundesland"));
                patient.setGeschlechtID(rs.getInt("Geschlecht"));
                patient.setKrankenkasseID(rs.getInt("Krankenkasse"));


                patients.add(patient);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return patients;
    }

    public static boolean addPatient(Patient patient) {
        String query = """
        INSERT INTO patient (
            Vorname, Nachname, Anrede, Geburtsdatum, Strasse, PLZ, Ort, 
            Bundesland, Telefon, GeschlechtID, Krankenkasse, Sonstiges
        ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);
    """;

        try (PreparedStatement stmt = DBAccess.connection.prepareStatement(query)) {

            // Setze die Parameter für das PreparedStatement
            stmt.setString(1, patient.getVorname());
            stmt.setString(2, patient.getNachname());
            stmt.setString(3, patient.getAnrede());
            System.out.println(patient.getGeburtsdatum());
            if(patient.getGeburtsdatum()!=null)
            {
                stmt.setDate(4, Date.valueOf(patient.getGeburtsdatum()));
            }
            else
            {
                stmt.setNull(4, java.sql.Types.DATE);
            }
            stmt.setString(5, patient.getStrasse());
            stmt.setString(6, patient.getPlz());
            stmt.setString(7, patient.getOrt());
            stmt.setObject(8, patient.getBundeslandID(), Types.INTEGER);
            stmt.setString(9, patient.getTelefon());
            stmt.setObject(10, patient.getGeschlechtID(), Types.INTEGER);
            stmt.setObject(11, patient.getKrankenkasseID(), Types.INTEGER);
            stmt.setString(12, patient.getSonstiges());

            // Führe das INSERT aus
            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean deletePatient(int patientId) {
        String sql = "DELETE FROM patient WHERE PatientID = ?";
        String sql_befund = "DELETE FROM befund WHERE PatientID = ?";

        try (PreparedStatement statement = DBAccess.connection.prepareStatement(sql_befund)) {
            statement.setInt(1, patientId);
            int rowsAffected = statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        try (PreparedStatement statement = DBAccess.connection.prepareStatement(sql)) {
            // Setze die PatientID als Parameter für die SQL-Abfrage
            statement.setInt(1, patientId);

            // Führe das DELETE-Statement aus
            int rowsAffected = statement.executeUpdate();

            // Wenn mindestens eine Zeile betroffen ist, war das Löschen erfolgreich
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Fehler beim Löschen
        }
    }
}
