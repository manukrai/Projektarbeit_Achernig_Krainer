package beans;

public class Krankenkasse {
    private int krankenkasseID;
    private String bezeichnung;

    // Standard-Konstruktor
    public Krankenkasse() {}

    // Konstruktor mit Parametern
    public Krankenkasse(int krankenkasseID, String bezeichnung) {
        this.krankenkasseID = krankenkasseID;
        this.bezeichnung = bezeichnung;
    }

    // Getter und Setter
    public int getKrankenkasseID() {
        return krankenkasseID;
    }

    public void setKrankenkasseID(int krankenkasseID) {
        this.krankenkasseID = krankenkasseID;
    }

    public String getBezeichnung() {
        return bezeichnung;
    }

    public void setBezeichnung(String bezeichnung) {
        this.bezeichnung = bezeichnung;
    }
}

