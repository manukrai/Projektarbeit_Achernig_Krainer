package beans;

public class Geschlecht {
    private int geschlechtID;
    private String bezeichnung;

    // Standard-Konstruktor
    public Geschlecht() {}

    // Konstruktor mit Parametern
    public Geschlecht(int geschlechtID, String bezeichnung) {
        this.geschlechtID = geschlechtID;
        this.bezeichnung = bezeichnung;
    }

    // Getter und Setter
    public int getGeschlechtID() {
        return geschlechtID;
    }

    public void setGeschlechtID(int geschlechtID) {
        this.geschlechtID = geschlechtID;
    }

    public String getBezeichnung() {
        return bezeichnung;
    }

    public void setBezeichnung(String bezeichnung) {
        this.bezeichnung = bezeichnung;
    }
}

