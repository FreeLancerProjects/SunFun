package com.creativeshare.sunfun.models;

import java.io.Serializable;

public class ActivityModelUpload implements Serializable {

    private int activity_id;
    private int subscribers_num;
    private String ar_title;
    private String en_title;


    public ActivityModelUpload(int activity_id, int subscribers_num, String ar_title, String en_title) {
        this.activity_id = activity_id;
        this.subscribers_num = subscribers_num;
        this.ar_title = ar_title;
        this.en_title = en_title;
    }

    public void setSubscribers_num(int subscribers_num) {
        this.subscribers_num = subscribers_num;
    }

    public int getActivity_id() {
        return activity_id;
    }

    public int getSubscribers_num() {
        return subscribers_num;
    }

    public String getAr_title() {
        return ar_title;
    }

    public String getEn_title() {
        return en_title;
    }
}
