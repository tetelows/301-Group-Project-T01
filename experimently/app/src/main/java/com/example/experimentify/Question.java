package com.example.experimentify;

import android.icu.text.SimpleDateFormat;

import java.util.Date;

public class Question {
    private String desc;
    private String uID;
    // Not sure how the date should be stored yet.
    private String date;
    private int numReplies;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    public Question(String desc, String uID) {
        this.desc = desc;
        this.uID = uID;
        this.numReplies = 0;
        date = sdf.format(new Date(System.currentTimeMillis()));
    }

    public String getDesc() {
        return desc;
    }

    public String getuID() {
        return uID;
    }

    public String getDate() {
        return date;
    }

    public int getNumReplies() {
        return numReplies;
    }
}