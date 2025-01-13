package bl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class DBAccess {

    private static DBAccess instance;
    private static String url = "jdbc:postgresql://localhost:5432/Patient";
    private static String user = "postgres";
    private static String password = "root";
    public static Connection connection = null;

    private DBAccess() {}

    /**
     * Erstellt die Instance mit dem Singleton Pattern.
     * @return Die erstellte Instance
     */
    public static DBAccess getInstance() {
        if (instance == null) {
            instance = new DBAccess();
        }
        return instance;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Verbindet zu der Datenbank.
     */
    public static void connect()
    {
        try (BufferedReader reader = new BufferedReader(new FileReader("/Users/manuel.krainer/IntelliJ/Projektarbeit_Achernig_Krainer/src/data/databaseConnectionData.txt"))) {
            Properties properties = new Properties();
            properties.load(reader);

            url = properties.getProperty("url");
            user = properties.getProperty("username");
            password = properties.getProperty("password");

            System.out.println("URL: " + url);
            System.out.println("Username: " + user);
            System.out.println("Password: " + password);

        } catch (IOException e) {
            System.err.println("Fehler beim Lesen der Datei: " + e.getMessage());
        }

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException ex) {
            System.out.println(ex);
        }

        try {
            connection = DriverManager.getConnection(url, user, password);

            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                try {
                    if (connection != null && !connection.isClosed()) {
                        connection.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }));

        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

}
