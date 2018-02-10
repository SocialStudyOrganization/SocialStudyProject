package com.ndlp.socialstudy.ShowKursmitglieder;



public class KursmitgliederObject {

    private String firstname, surname, matrikelnummer, email;

    public KursmitgliederObject(Integer matrikelnummer, String email, String firstname, String surname){
        this.firstname = firstname;
        this.surname = surname;
        this.matrikelnummer = matrikelnummer + "";
        this.email = email;
    }

    public String getFirstname(){
        return firstname;
    }

    public String getSurname(){
        return surname;
    }

    public String getMatrikelnummer(){
        return matrikelnummer;
    }

    public String getEmail(){
        return email;
    }
}
