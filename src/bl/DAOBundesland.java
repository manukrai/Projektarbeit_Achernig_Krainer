package bl;

import beans.Bundesland;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DAOBundesland {

    public static List<Bundesland> getAllBundeslaender() {
        List<Bundesland> liste = new ArrayList<>();
        String query = "SELECT BundeslandID, Bezeichnung FROM bundesland";

        try (
                PreparedStatement statement = DBAccess.connection.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("BundeslandID");
                String bezeichnung = resultSet.getString("Bezeichnung");
                liste.add(new Bundesland(id, bezeichnung));
            }
        } catch (SQLException e) {
        }

        return liste;
    }

    public static void addBundesland(String bezeichnung) {
        String query = "INSERT INTO bundesland (Bezeichnung) VALUES (?)";

        try (PreparedStatement statement = DBAccess.connection.prepareStatement(query)) {

            statement.setString(1, bezeichnung);
            statement.executeUpdate();

        } catch (SQLException e) {
        }
    }
}
