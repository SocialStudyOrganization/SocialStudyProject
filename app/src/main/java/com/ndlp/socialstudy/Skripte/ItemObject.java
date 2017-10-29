package com.ndlp.socialstudy.Skripte;

import android.graphics.drawable.Drawable;

/**
 * Class to create different scriptObjects
 */

public class ItemObject {

    private Integer scriptID;
    private String scriptName;
    private String scriptFormat;
    private String scriptCategory;
    private String scriptDate;
    private String scriptUser;

    //  Constructor
    public ItemObject(Integer scriptID, String scriptName, String scriptFormat, String scriptCategory, String scriptDate, String scriptUser) {

        this.scriptID = scriptID;
        this.scriptName = scriptName;
        this.scriptFormat = scriptFormat;
        this.scriptCategory = scriptCategory;
        this.scriptDate = scriptDate;
        this.scriptUser = scriptUser;
    }

    public Integer getScriptID() {
        return scriptID;
    }

    public String getScriptName() {
        return scriptName;
    }

    public String getScriptFormat() {
        return scriptFormat;
    }

    public String getScriptCategory() {
        return scriptCategory;
    }

    public String getScriptDate() {
        return  scriptDate;
    }

    public String getScriptUser() {
        return scriptUser;
    }
}
