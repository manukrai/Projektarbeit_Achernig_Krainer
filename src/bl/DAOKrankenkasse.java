package bl;

import beans.Krankenkasse;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DAOKrankenkasse {

    /**
     * Liefer alle Krankhäuser zurück.
     * @return Liste aller Krankenhäuser.
     */
    public static List<Krankenkasse> getAllKrankenkassen() {
        List<Krankenkasse> liste = new ArrayList<>();
        String query = "SELECT KrankenkasseID, Bezeichnung FROM krankenkasse";

        if(DBAccess.connection != null)
        try (
                PreparedStatement statement = DBAccess.connection.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("KrankenkasseID");
                String bezeichnung = resultSet.getString("Bezeichnung");
                liste.add(new Krankenkasse(id, bezeichnung));
            }
        } catch (SQLException ex) {
            Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
            logger.setLevel(Level.ALL);
            logger.severe(ex.getMessage());
        }

        return liste;
    }

}
