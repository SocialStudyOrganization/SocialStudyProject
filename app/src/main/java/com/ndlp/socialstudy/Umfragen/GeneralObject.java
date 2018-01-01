package com.ndlp.socialstudy.Umfragen;




public class GeneralObject {

    private String user;
    private String type;
    private String enddate;
    private String endtime;
    private String topic;

    public GeneralObject(String type, String user, String enddate, String endtime, String topic) {

        this.type = type;
        this.user = user;
        this.enddate = enddate;
        this.endtime = endtime;
        this.topic = topic;
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


}
