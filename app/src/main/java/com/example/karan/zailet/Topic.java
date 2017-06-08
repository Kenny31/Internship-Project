package com.example.karan.zailet;

/**
 * Created by karan on 8/6/17.
 */

public class Topic {
    private String id;
    private String interest;
    private String cover;
    private String time;

    public String getId() {
        return id;
    }

    public String getInterest() {
        return interest;
    }

    public String getCover() {
        return cover;
    }

    public String getTime() {
        return time;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
