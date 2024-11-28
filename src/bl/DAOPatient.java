package bl;

import beans.Patient;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class DAOPatient {

    // Alle Patienten abrufen
    public List<Patient> getAllPatients() {
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

        try (Connection conn = DBAccess.connection;
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                // Erstelle ein Patient-Objekt und setze die Werte
                Patient patient = new Patient();
                patient.setPatientID(rs.getInt("PatientID"));
                patient.setVorname(rs.getString("Vorname"));
                patient.setNachname(rs.getString("Nachname"));
                patient.setAnrede(rs.getString("Anrede"));
                patient.setGeburtsdatum(rs.getDate("Geburtsdatum"));
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
}
