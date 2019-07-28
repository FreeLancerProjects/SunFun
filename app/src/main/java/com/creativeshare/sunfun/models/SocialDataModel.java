package com.creativeshare.sunfun.models;

import java.io.Serializable;

public class SocialDataModel implements Serializable {

    private String facebook;
    private String twitter;
    private String instagram;

    public String getFacebook() {
        return facebook;
    }

    public String getTwitter() {
        return twitter;
    }

    public String getInstagram() {
        return instagram;
    }
}
