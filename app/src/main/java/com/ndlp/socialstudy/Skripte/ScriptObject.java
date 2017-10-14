package com.ndlp.socialstudy.Skripte;

/**
 * Class to create different scriptObjects
 */

public class ScriptObject {

    private Integer scriptID;
    private String scriptName;
    private String scriptDate;
    private String scriptUser;

    //  Constructor
    public ScriptObject(Integer scriptID, String scriptName, String scriptDate, String scriptUser) {

        this.scriptID = scriptID;
        this.scriptName = scriptName;
        this.scriptDate = scriptDate;
        this.scriptUser = scriptUser;
    }

    public Integer getScriptID() {
        return scriptID;
    }

    public String getScriptName() {
        return scriptName;
    }

    public String getScriptDate() {
        return  scriptDate;
    }

    public String getScriptUser() {
        return scriptUser;
    }
}
