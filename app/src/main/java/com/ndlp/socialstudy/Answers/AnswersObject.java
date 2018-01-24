package com.ndlp.socialstudy.Answers;

/**
 * Class to create different scriptObjects
 */

public class AnswersObject {

    private Integer answerID;
    private String answerName;
    private String answerFormat;
    private String answerCategory;
    private String answerDate;
    private String answerUser;

    //  Constructor
    public AnswersObject(Integer scriptID, String scriptName, String scriptFormat, String scriptCategory, String scriptDate, String scriptUser) {

        this.answerID = scriptID;
        this.answerName = scriptName;
        this.answerFormat = scriptFormat;
        this.answerCategory = scriptCategory;
        this.answerDate = scriptDate;
        this.answerUser = scriptUser;
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
