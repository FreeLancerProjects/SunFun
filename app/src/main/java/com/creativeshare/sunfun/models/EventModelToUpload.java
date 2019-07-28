package com.creativeshare.sunfun.models;

import java.io.Serializable;
import java.util.List;

public class EventModelToUpload implements Serializable {
    private String company_id;
    private int user_id;
    private int event_id;
    private int subscribers_num;
    private int paid_type;
    private List<ActivityModelUpload> activatis;

    public EventModelToUpload() {
    }

    public EventModelToUpload(String company_id, int user_id, int event_id, int subscribers_num, int paid_type, List<ActivityModelUpload> activatis) {
        this.company_id = company_id;
        this.user_id = user_id;
        this.event_id = event_id;
        this.subscribers_num = subscribers_num;
        this.paid_type = paid_type;
        this.activatis = activatis;
    }

    public void setCompany_id(String company_id) {
        this.company_id = company_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public void setEvent_id(int event_id) {
        this.event_id = event_id;
    }

    public void setSubscribers_num(int subscribers_num) {
        this.subscribers_num = subscribers_num;
    }

    public void setPaid_type(int paid_type) {
        this.paid_type = paid_type;
    }

    public void setActivatis(List<ActivityModelUpload> activatis) {
        this.activatis = activatis;
    }

    public String getCompany_id() {
        return company_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public int getEvent_id() {
        return event_id;
    }

    public int getSubscribers_num() {
        return subscribers_num;
    }

    public int getPaid_type() {
        return paid_type;
    }

    public List<ActivityModelUpload> getActivatis() {
        return activatis;
    }
}
