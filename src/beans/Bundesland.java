package beans;

public class Bundesland {
    private int bundeslandID;
    private String bezeichnung;

    public Bundesland() {
    }

    public Bundesland(int bundeslandID, String bezeichnung) {
        this.bundeslandID = bundeslandID;
        this.bezeichnung = bezeichnung;
    }

    public int getBundeslandID() {
        return bundeslandID;
    }

    public String getBezeichnung() {
        return bezeichnung;
    }

}

