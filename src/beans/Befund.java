package beans;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Befund {
    private int befundID;
    private int patientID;
    private String pfad;
    private LocalDate datum;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");


    public Befund() {
    }

    public Befund(int befundID, int patientID, String pfad, String datum) {
        this.befundID = befundID;
        this.patientID = patientID;
        this.pfad = pfad;
        this.datum = LocalDate.parse(datum, formatter);
    }

    public int getBefundID() {
        return befundID;
    }

    public String getPfad() {
        return pfad;
    }

    public LocalDate getDatum() {
        return datum;
    }
}

