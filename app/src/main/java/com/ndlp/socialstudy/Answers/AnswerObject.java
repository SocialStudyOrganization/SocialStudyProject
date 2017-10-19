package com.ndlp.socialstudy.Answers;

/**
 * Created by ndlp on 20.10.2017.
 */

public class AnswerObject {
    private Integer answerID;
    private String answerName;
    private String answerFormat;
    private String answerCategory;
    private String answerDate;
    private String answerUser;

    //  Constructor
    public AnswerObject(Integer answerID, String answerName, String answerFormat, String answerCategory, String answerDate, String answerUser) {

        this.answerID = answerID;
        this.answerName = answerName;
        this.answerFormat = answerFormat;
        this.answerCategory = answerCategory;
        this.answerDate = answerDate;
        this.answerUser = answerUser;
    }

    public Integer getAnswerID() {
        return answerID;
    }

    public String getAnswerName() {
        return answerName;
    }

    public String getAnswerFormat() {
        return answerFormat;
    }

    public String getAnswerCategory() {
        return answerCategory;
    }

    public String getAnswerDate() {
        return  answerDate;
    }

    public String getAnswerUser() {
        return answerUser;
    }
}
