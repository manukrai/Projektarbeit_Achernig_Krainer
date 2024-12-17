package beans;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Befund {
    private int befundID;
    private int patientID;
    private String pfad;
    private LocalDate datum;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    // Standard-Konstruktor
    public Befund() {}

    // Konstruktor mit Parametern
    public Befund(int befundID, int patientID, String pfad, String datum) {
        this.befundID = befundID;
        this.patientID = patientID;
        this.pfad = pfad;
        this.datum = LocalDate.parse(datum, formatter);
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

    public LocalDate getDatum() {
        return datum;
    }

    public void setDatum(String datum)
    {
        this.datum = LocalDate.parse(datum, formatter);
    }
}

