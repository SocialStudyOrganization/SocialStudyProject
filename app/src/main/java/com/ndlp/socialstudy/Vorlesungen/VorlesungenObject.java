package com.ndlp.socialstudy.Vorlesungen;

/**
 * Class used to set up each class object
 */

public class VorlesungenObject {

    private String bezeichnung;
    private String anzahl;

    public VorlesungenObject(String bezeichnung, String anzahl) {

        this.bezeichnung = bezeichnung;
        this.anzahl = anzahl;
    }

    public String getBezeichnung() {

        return bezeichnung;
    }

    public String getAnzahl(){
        return anzahl;
    }
}
