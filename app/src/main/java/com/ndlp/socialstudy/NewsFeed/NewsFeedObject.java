package com.ndlp.socialstudy.NewsFeed;



public class NewsFeedObject {

    private String category, datetime, topic, message;

    public NewsFeedObject(String category, String datetime, String topic, String message){
        this.category = category;
        this.datetime = datetime;
        this.topic = topic;
        this.message = message;
    }

    public String getCategory(){
        return category;
    }

    public String getDatetime(){
       return datetime;
    }

    public String getTopic(){
        return topic;
    }

    public String getMessage(){
        return message;
    }
}
