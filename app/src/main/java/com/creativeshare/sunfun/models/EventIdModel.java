package com.creativeshare.sunfun.models;

import java.io.Serializable;

public class EventIdModel implements Serializable {
    private int id;
    private String company_id;

    public int getId() {
        return id;
    }

    public String getCompany_id() {
        return company_id;
    }
}
