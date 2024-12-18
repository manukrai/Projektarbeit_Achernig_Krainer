package bl;

import beans.Krankenkasse;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DAOKrankenkasse {

    /**
     * Liefer alle Krankhäuser zurück.
     * @return Liste aller Krankenhäuser.
     */
    public static List<Krankenkasse> getAllKrankenkassen() {
        List<Krankenkasse> liste = new ArrayList<>();
        String query = "SELECT KrankenkasseID, Bezeichnung FROM krankenkasse";

        try (
                PreparedStatement statement = DBAccess.connection.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("KrankenkasseID");
                String bezeichnung = resultSet.getString("Bezeichnung");
                liste.add(new Krankenkasse(id, bezeichnung));
            }
        } catch (SQLException e) {
        }

        return liste;
    }

}
