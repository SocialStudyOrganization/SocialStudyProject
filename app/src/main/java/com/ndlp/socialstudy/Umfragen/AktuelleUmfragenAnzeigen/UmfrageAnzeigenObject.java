package com.ndlp.socialstudy.Umfragen.AktuelleUmfragenAnzeigen;

/**
 * Created by ndlp on 08.01.2018.
 */

public class UmfrageAnzeigenObject {

    private String text;
    private Integer id;


    public UmfrageAnzeigenObject(String text, Integer id) {

        this.text = text;
        this.id = id;

    }

    public String getText(){
        return text;
    }

    public Integer getId(){
        return id;
    }


}
