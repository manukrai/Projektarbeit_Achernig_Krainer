package beans;

public class Geschlecht {
    private int geschlechtID;
    private String bezeichnung;

    public Geschlecht() {
    }

    public Geschlecht(int geschlechtID, String bezeichnung) {
        this.geschlechtID = geschlechtID;
        this.bezeichnung = bezeichnung;
    }


    public int getGeschlechtID() {
        return geschlechtID;
    }


    public String getBezeichnung() {
        return bezeichnung;
    }

}

