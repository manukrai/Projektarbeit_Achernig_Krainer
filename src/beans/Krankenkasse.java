package beans;

public class Krankenkasse {
    private int krankenkasseID;
    private String bezeichnung;

    public Krankenkasse() {
    }

    public Krankenkasse(int krankenkasseID, String bezeichnung) {
        this.krankenkasseID = krankenkasseID;
        this.bezeichnung = bezeichnung;
    }

    public int getKrankenkasseID() {
        return krankenkasseID;
    }


    public String getBezeichnung() {
        return bezeichnung;
    }

}

