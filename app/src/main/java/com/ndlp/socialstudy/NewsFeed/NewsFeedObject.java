package com.ndlp.socialstudy.NewsFeed;



public class NewsFeedObject {

    private String category, uploaddate, uploadtime, topic, message;

    public NewsFeedObject(String category, String uploaddate, String uploadtime, String topic, String message){
        this.category = category;
        this.uploaddate = uploaddate;
        this.uploadtime = uploadtime;
        this.topic = topic;
        this.message = message;
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
