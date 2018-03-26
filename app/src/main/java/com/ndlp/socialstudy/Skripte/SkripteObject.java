package com.ndlp.socialstudy.Skripte;

import android.graphics.drawable.Drawable;

/**
 * Class to create different scriptObjects
 */

public class SkripteObject {

    private Integer id;
    private String filename;
    private String format;
    private String category;
    private String timestamp;
    private String user;
    private String subfolder;

    //  Constructor
    public SkripteObject(Integer id, String filename, String format, String category, String subfolder, String timestamp, String user) {

        this.id = id;
        this.filename = filename;
        this.format = format;
        this.category = category;
        this.timestamp = timestamp;
        this.user = user;
        this.subfolder = subfolder;
    }

    public Integer getId() {
        return id;
    }

    public String getFilename() {
        return filename;
    }

    public String getFormat() {
        return format;
    }

    public String getCategory() {
        return category;
    }

    public String getTimestamp() {
        return  timestamp;
    }

    public String getUser() {
        return user;
    }

    public String getSubfolder() {
        return subfolder;
    }
}
