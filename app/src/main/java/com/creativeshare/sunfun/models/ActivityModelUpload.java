package com.creativeshare.sunfun.models;

import java.io.Serializable;

public class ActivityModelUpload implements Serializable {

    private int id;
    private int subscribers;
    private String ar_title;
    private String en_title;


    public ActivityModelUpload(int id, int subscribers, String ar_title, String en_title) {
        this.id = id;
        this.subscribers = subscribers;
        this.ar_title = ar_title;
        this.en_title = en_title;
    }

    public void setSubscribers(int subscribers) {
        this.subscribers = subscribers;
    }

    public int getId() {
        return id;
    }

    public int getSubscribers() {
        return subscribers;
    }

    public String getAr_title() {
        return ar_title;
    }

    public String getEn_title() {
        return en_title;
    }
}
