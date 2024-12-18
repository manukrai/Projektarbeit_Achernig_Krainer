package accountManagment;

public class Account {
    /**
     * Dieses Attribut speichert einen Unsernamen.
     */
    private String username;
    /**
     * Dieses Attribut speichert ein Passwort.
     */
    private String password;

    public Account(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Diese Methode vergleicht zwei Passwörter miteinander.
     * @param password
     * @return Ob sie gleich sind oder nicht.
     */
    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }
}
