package bl;

import beans.Geschlecht;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DAOGeschlecht {

    public static List<Geschlecht> getAllGeschlechter() {
        List<Geschlecht> geschlechterListe = new ArrayList<>();

        String query = "SELECT GeschlechtID, Bezeichnung FROM geschlecht";

        // Verbindung zur Datenbank herstellen
        try (
                PreparedStatement statement = DBAccess.connection.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("GeschlechtID");
                String bezeichnung = resultSet.getString("Bezeichnung");

                // Geschlecht-Objekt erstellen und zur Liste hinzufügen
                geschlechterListe.add(new Geschlecht(id, bezeichnung));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return geschlechterListe;
    }
}
