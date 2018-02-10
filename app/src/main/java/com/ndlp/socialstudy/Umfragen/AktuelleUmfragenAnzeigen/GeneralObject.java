package com.ndlp.socialstudy.Umfragen.AktuelleUmfragenAnzeigen;




public class GeneralObject {

    private String user;
    private String type;
    private String enddate;
    private String endtime;
    private String topic;
    private String onlyoneanswer;

    public GeneralObject(String type, String user, String enddate, String endtime, String topic, String onlyoneanswer) {

        this.type = type;
        this.user = user;
        this.enddate = enddate;
        this.endtime = endtime;
        this.topic = topic;
        this.onlyoneanswer = onlyoneanswer;
    }

    public String getType(){
        return type;
    }
    public String getUser(){
        return user;
    }
    public String getEnddate(){
        return enddate;
    }
    public String getEndtime(){
        return endtime;
    }
    public String getTopic(){
        return topic;
    }
    public String getOnlyoneanswer(){
        return onlyoneanswer;
    }


}
