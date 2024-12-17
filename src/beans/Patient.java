package beans;

import java.io.Serializable;
import java.util.Date;

public class Patient implements Comparable<Patient>
{
    private int patientID;
    private String vorname;
    private String nachname;
    private String anrede;
    private Date geburtsdatum;
    private String strasse;
    private String plz;
    private String ort;
    private int bundeslandID;
    private String telefon;
    private int geschlechtID;
    private int krankenkasseID;
    private String sonstiges;

    // Standard-Konstruktor
    public Patient() {}

    // Konstruktor mit Parametern
    public Patient(int patientID, String vorname, String nachname, String anrede, Date geburtsdatum, String strasse,
                   String plz, String ort, int bundesland, String telefon, int geschlechtID, int krankenkasse, String sonstiges) {
        this.patientID = patientID;
        this.vorname = vorname;
        this.nachname = nachname;
        this.anrede = anrede;
        this.geburtsdatum = geburtsdatum;
        this.strasse = strasse;
        this.plz = plz;
        this.ort = ort;
        this.bundeslandID = bundesland;
        this.telefon = telefon;
        this.geschlechtID = geschlechtID;
        this.krankenkasseID = krankenkasse;
        this.sonstiges = sonstiges;
    }

    // Getter und Setter
    public int getPatientID() {
        return patientID;
    }

    public void setPatientID(int patientID) {
        this.patientID = patientID;
    }

    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public String getNachname() {
        return nachname;
    }

    public void setNachname(String nachname) {
        this.nachname = nachname;
    }

    public String getAnrede() {
        return anrede;
    }

    public void setAnrede(String anrede) {
        this.anrede = anrede;
    }

    public Date getGeburtsdatum() {
        return geburtsdatum;
    }

    public void setGeburtsdatum(Date geburtsdatum) {
        this.geburtsdatum = geburtsdatum;
    }

    public String getStrasse() {
        return strasse;
    }

    public void setStrasse(String strasse) {
        this.strasse = strasse;
    }

    public String getPlz() {
        return plz;
    }

    public void setPlz(String plz) {
        this.plz = plz;
    }

    public String getOrt() {
        return ort;
    }

    public void setOrt(String ort) {
        this.ort = ort;
    }

    public int getBundeslandID() {
        return bundeslandID;
    }

    public void setBundeslandID(int bundesland) {
        this.bundeslandID = bundesland;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public int getGeschlechtID() {
        return geschlechtID;
    }

    public void setGeschlechtID(int geschlechtID) {
        this.geschlechtID = geschlechtID;
    }

    public int getKrankenkasseID() {
        return krankenkasseID;
    }

    public void setKrankenkasseID(int krankenkasse) {
        this.krankenkasseID = krankenkasse;
    }

    public String getSonstiges() {
        return sonstiges;
    }

    public void setSonstiges(String sonstiges) {
        this.sonstiges = sonstiges;
    }

    @Override
    public int compareTo(Patient p) {
        return p.vorname.compareTo(this.vorname);
    }
}

