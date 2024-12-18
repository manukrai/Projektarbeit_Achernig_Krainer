package bl;

import beans.Krankenkasse;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DAOKrankenkasse {

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

    // Methode: Krankenkasse hinzufügen
    public static void addKrankenkasse(String bezeichnung) {
        String query = "INSERT INTO krankenkasse (Bezeichnung) VALUES (?)";

        try (
             PreparedStatement statement = DBAccess.connection.prepareStatement(query)) {

            statement.setString(1, bezeichnung);
            statement.executeUpdate();

        } catch (SQLException e) {
        }
    }

}
