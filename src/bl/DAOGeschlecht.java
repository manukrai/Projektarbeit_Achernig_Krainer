package bl;

import beans.Geschlecht;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DAOGeschlecht {

    private static final Logger logger = Logger.getLogger(DAOGeschlecht.class.getName());

    /**
     * Liefert alle Geschlechter zurück.
     * @return Liefer eine Liste aller Geschlechter zurück.
     */
    public static List<Geschlecht> getAllGeschlechter() {
        List<Geschlecht> geschlechterListe = new ArrayList<>();

        String query = "SELECT GeschlechtID, Bezeichnung FROM geschlecht";

        if(DBAccess.connection == null)
        {
            logger.severe("Keine Verbindung zur Datenbank verfügbar.");
            return geschlechterListe;
        }
        try (
                PreparedStatement statement = DBAccess.connection.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("GeschlechtID");
                String bezeichnung = resultSet.getString("Bezeichnung");

                geschlechterListe.add(new Geschlecht(id, bezeichnung));
            }

        } catch (SQLException ex) {
            logger.setLevel(Level.ALL);
            logger.severe(ex.getMessage());
        }

        return geschlechterListe;
    }
}
