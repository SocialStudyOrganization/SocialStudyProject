package com.ndlp.socialstudy.NewsFeed;



public class NewsFeedObject {

    private String user, category, uploaddate, uploadtime, topic, message;

    public NewsFeedObject(String user, String category, String uploaddate, String uploadtime, String topic, String message){
        this.user = user;
        this.category = category;
        this.uploaddate = uploaddate;
        this.uploadtime = uploadtime;
        this.topic = topic;
        this.message = message;
    }

    public String getUser(){
        return user;
    }

    public String getCategory(){
        return category;
    }

    public String getUploaddate(){
        return uploaddate;
    }

    public String getUploadtime(){
        return uploadtime;
    }

    public String getTopic(){
        return topic;
    }

    public String getMessage(){
        return message;
    }
}
