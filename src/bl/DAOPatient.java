package bl;

import beans.Patient;

import java.sql.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DAOPatient {

    private static final Logger logger = Logger.getLogger(DAOPatient.class.getName());

    /**
     * Liefert alle Patienten zurück
     *
     * @return Liste aller Patienten.
     */
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

        if (DBAccess.connection == null) {
            logger.severe("Keine Verbindung zur Datenbank verfügbar.");
            return patients;
        }
        try (PreparedStatement stmt = DBAccess.connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                // Erstelle ein Patient-Objekt und setze die Werte
                Patient patient = new Patient();
                patient.setPatientID(rs.getInt("PatientID"));
                patient.setVorname(rs.getString("Vorname"));
                patient.setNachname(rs.getString("Nachname"));
                patient.setAnrede(rs.getString("Anrede"));
                if (rs.getString("geburtsdatum") != null)
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

        } catch (SQLException ex) {
            logger.setLevel(Level.ALL);
            logger.severe(ex.getMessage());
        }

        return patients;
    }

    /**
     * Fügt einem Patienten hinzu.
     *
     * @param patient
     * @return Ob es funktioniert hat.
     */
    public static boolean addPatient(Patient patient) {
        String query = """
                    INSERT INTO patient (
                        Vorname, Nachname, Anrede, Geburtsdatum, Strasse, PLZ, Ort, 
                        Bundesland, Telefon, GeschlechtID, Krankenkasse, Sonstiges
                    ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);
                """;

        if (DBAccess.connection == null) {
            logger.severe("Keine Verbindung zur Datenbank verfügbar.");
            return false;
        }
        try (PreparedStatement stmt = DBAccess.connection.prepareStatement(query)) {

            // Setze die Parameter für das PreparedStatement
            stmt.setString(1, patient.getVorname());
            stmt.setString(2, patient.getNachname());
            stmt.setString(3, patient.getAnrede());
            if (patient.getGeburtsdatum() != null) {
                stmt.setDate(4, Date.valueOf(patient.getGeburtsdatum()));
            } else {
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

        } catch (SQLException ex) {
            logger.setLevel(Level.ALL);
            logger.severe(ex.getMessage());
            return false;
        }
    }

    /**
     * Löscht einen Patienten aus der Datenbank.
     *
     * @param patientId
     * @return Ob es funktioniert hat.
     */
    public static boolean deletePatient(int patientId) {
        String sql = "DELETE FROM patient WHERE PatientID = ?";
        String sql_befund = "DELETE FROM befund WHERE PatientID = ?";

        if (DBAccess.connection != null)
            try (PreparedStatement statement = DBAccess.connection.prepareStatement(sql_befund)) {
                statement.setInt(1, patientId);
                int rowsAffected = statement.executeUpdate();

            } catch (SQLException ex) {
                logger.setLevel(Level.ALL);
                logger.severe(ex.getMessage());
                return false;
            }

        if (DBAccess.connection == null) {
            logger.severe("Keine Verbindung zur Datenbank verfügbar.");
            return false;
        }
        try (PreparedStatement statement = DBAccess.connection.prepareStatement(sql)) {
            // Setze die PatientID als Parameter für die SQL-Abfrage
            statement.setInt(1, patientId);

            // Führe das DELETE-Statement aus
            int rowsAffected = statement.executeUpdate();

            // Wenn mindestens eine Zeile betroffen ist, war das Löschen erfolgreich
            return rowsAffected > 0;

        } catch (SQLException ex) {
            logger.setLevel(Level.ALL);
            logger.severe(ex.getMessage());
            return false; // Fehler beim Löschen
        }
    }

    /**
     * Updated die Daten im Patienten.
     *
     * @param patientID
     * @param vorname
     * @param nachname
     * @param anrede
     * @param geburtsdatum
     * @param strasse
     * @param plz
     * @param ort
     * @param bundesland
     * @param telefon
     * @param geschlechtID
     * @param krankenkasse
     * @param sonstiges
     */
    public static void updatePatient(int patientID, String vorname, String nachname, String anrede, String geburtsdatum,
                                     String strasse, String plz, String ort, int bundesland, String telefon, int geschlechtID,
                                     int krankenkasse, String sonstiges) {

        String updateSQL = "UPDATE patient SET Vorname = ?, Nachname = ?, Anrede = ?, Geburtsdatum = ?, Strasse = ?, " +
                "PLZ = ?, Ort = ?, Bundesland = ?, Telefon = ?, GeschlechtID = ?, Krankenkasse = ?, Sonstiges = ? " +
                "WHERE PatientID = ?";


        if (DBAccess.connection == null) {
            logger.severe("Keine Verbindung zur Datenbank verfügbar.");
        } else {
            try (PreparedStatement stmt = DBAccess.connection.prepareStatement(updateSQL);) {
                // PreparedStatement für das Update vorbereiten

                stmt.setString(1, vorname);
                stmt.setString(2, nachname);
                stmt.setString(3, anrede);
                if (!geburtsdatum.isBlank()) {
                    stmt.setDate(4, Date.valueOf(geburtsdatum));
                } else {
                    stmt.setNull(4, java.sql.Types.DATE);
                }
                stmt.setString(5, strasse);
                stmt.setString(6, plz);
                stmt.setString(7, ort);
                stmt.setInt(8, bundesland);
                stmt.setString(9, telefon);
                stmt.setInt(10, geschlechtID);
                stmt.setInt(11, krankenkasse);
                stmt.setString(12, sonstiges);
                stmt.setInt(13, patientID);

                // SQL Update ausführen
                stmt.executeUpdate();
            } catch (SQLException ex) {
                logger.setLevel(Level.ALL);
                logger.severe(ex.getMessage());
            }
        }

    }

    /**
     * Liefert alle Patienten zurück (asynchron).
     *
     * @return Ein CompletableFuture, das eine Liste aller Patienten liefert.
     */
    public static CompletableFuture<List<Patient>> getAllPatientsAsync() {
        return CompletableFuture.supplyAsync(() -> {
            return getAllPatients(); // Nutzt die synchrone Methode
        }).exceptionally(ex -> {
            logger.log(Level.SEVERE, "Fehler bei der asynchronen Datenbankabfrage: {0}", ex.getMessage());
            return new LinkedList<>(); // Leere Liste bei Fehler
        });
    }

    /**
     * Fügt einem Patienten hinzu (asynchron).
     *
     * @param patient
     * @return Ein CompletableFuture, das angibt, ob das Hinzufügen erfolgreich war.
     */
    public static CompletableFuture<Boolean> addPatientAsync(Patient patient) {
        return CompletableFuture.supplyAsync(() -> {
            return addPatient(patient); // Nutzt die synchrone Methode
        }).exceptionally(ex -> {
            logger.log(Level.SEVERE, "Fehler bei der asynchronen Datenbankoperation: {0}", ex.getMessage());
            return false; // Fehler bei der Operation
        });
    }

    /**
     * Löscht einen Patienten aus der Datenbank (asynchron).
     *
     * @param patientId
     * @return Ein CompletableFuture, das angibt, ob das Löschen erfolgreich war.
     */
    public static CompletableFuture<Boolean> deletePatientAsync(int patientId) {
        return CompletableFuture.supplyAsync(() -> {
            return deletePatient(patientId); // Nutzt die synchrone Methode
        }).exceptionally(ex -> {
            logger.log(Level.SEVERE, "Fehler bei der asynchronen Datenbankoperation: {0}", ex.getMessage());
            return false; // Fehler bei der Operation
        });
    }

    /**
     * Updated die Daten eines Patienten (asynchron).
     *
     * @param patientID
     * @param vorname
     * @param nachname
     * @param anrede
     * @param geburtsdatum
     * @param strasse
     * @param plz
     * @param ort
     * @param bundesland
     * @param telefon
     * @param geschlechtID
     * @param krankenkasse
     * @param sonstiges
     */
    public static CompletableFuture<Void> updatePatientAsync(int patientID, String vorname, String nachname, String anrede,
                                                             String geburtsdatum, String strasse, String plz, String ort,
                                                             int bundesland, String telefon, int geschlechtID,
                                                             int krankenkasse, String sonstiges) {
        return CompletableFuture.runAsync(() -> {
            updatePatient(patientID, vorname, nachname, anrede, geburtsdatum, strasse, plz, ort, bundesland, telefon, geschlechtID, krankenkasse, sonstiges);
        }).exceptionally(ex -> {
            logger.log(Level.SEVERE, "Fehler bei der asynchronen Datenbankoperation: {0}", ex.getMessage());
            return null;
        });
    }
}
