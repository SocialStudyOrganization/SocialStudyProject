package com.ndlp.socialstudy.Umfragen;


import java.util.ArrayList;

public class Wortumfragenobject {

    private String wortumfragefrage;
    private ArrayList<String> wortumfrageantwortoptionen;

    public Wortumfragenobject(String wortumfragefrage, ArrayList<String> wortumfrageantwortoptionen){
        this.wortumfragefrage = wortumfragefrage;
        this.wortumfrageantwortoptionen = wortumfrageantwortoptionen;
    }

    public String getWortumFrageFrage(){
        return wortumfragefrage;
    }

    public ArrayList<String> getWortumfrageantwortoptionen(){
        return wortumfrageantwortoptionen;
    }
}
