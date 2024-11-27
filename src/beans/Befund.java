package beans;

import java.util.Date;

public class Befund {
    private int befundID;
    private int patientID;
    private String pfad;
    private Date datum;

    // Standard-Konstruktor
    public Befund() {}

    // Konstruktor mit Parametern
    public Befund(int befundID, int patientID, String pfad, Date datum) {
        this.befundID = befundID;
        this.patientID = patientID;
        this.pfad = pfad;
        this.datum = datum;
    }

    // Getter und Setter
    public int getBefundID() {
        return befundID;
    }

    public void setBefundID(int befundID) {
        this.befundID = befundID;
    }

    public int getPatientID() {
        return patientID;
    }

    public void setPatientID(int patientID) {
        this.patientID = patientID;
    }

    public String getPfad() {
        return pfad;
    }

    public void setPfad(String pfad) {
        this.pfad = pfad;
    }

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }
}

