package accountManagment;

import java.util.HashMap;

public class AccountManager {
    private HashMap<String, Account> accounts; // Speichert Accounts mit Benutzernamen als Schlüssel

    public AccountManager() {
        accounts = new HashMap<>();
        // Erstelle ein Standardkonto bei Initialisierung
        if (accounts.isEmpty()) {
            createAccount("doctor", "doctor"); // Beispiel Standardkonto
        }
    }

    // Konto erstellen
    public boolean createAccount(String username, String password) {
        if (accounts.containsKey(username)) {
            return false; // Benutzername existiert bereits
        }
        accounts.put(username, new Account(username, password));
        return true;
    }

    // Login validieren
    public boolean login(String username, String password) {
        Account account = accounts.get(username);
        if (account != null) {
            return account.checkPassword(password);
        }
        return false;
    }

}

