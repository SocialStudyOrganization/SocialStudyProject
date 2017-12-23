package com.ndlp.socialstudy.activity;

import java.util.Calendar;



public class TImeDateRequest {

    private String date;
    private String time;
    private String day;
    private String month;
    private String year;

    Calendar calander = Calendar.getInstance();

    public String getDate() {
        int cDay = calander.get(Calendar.DAY_OF_MONTH);
        int cMonth = calander.get(Calendar.MONTH) + 1;
        int cYear = calander.get(Calendar.YEAR);
        date = String.valueOf(cDay) + "." + String.valueOf(cMonth) + "." + String.valueOf(cYear);
        return date;
    }

    public String getTime() {

        int cHour = calander.get(Calendar.HOUR_OF_DAY);
        int cMinute = calander.get(Calendar.MINUTE);
        int cSecond = calander.get(Calendar.SECOND);
        time = String.valueOf(cHour)+ ":" +String.valueOf(cMinute)+ ":" + String.valueOf(cSecond);
        return time;
    }



}
