package beans;

public class Bundesland {
    private int bundeslandID;
    private String bezeichnung;

    // Standard-Konstruktor
    public Bundesland() {}

    // Konstruktor mit Parametern
    public Bundesland(int bundeslandID, String bezeichnung) {
        this.bundeslandID = bundeslandID;
        this.bezeichnung = bezeichnung;
    }

    // Getter und Setter
    public int getBundeslandID() {
        return bundeslandID;
    }

    public void setBundeslandID(int bundeslandID) {
        this.bundeslandID = bundeslandID;
    }

    public String getBezeichnung() {
        return bezeichnung;
    }

    public void setBezeichnung(String bezeichnung) {
        this.bezeichnung = bezeichnung;
    }
}

