package com.creativeshare.sunfun.viewmodel;


import android.content.Context;

import java.util.Observable;

public class User_View_Model extends Observable {
    private Context context;
    public String useremail;
    public String userpass;
    public String msg;

    public User_View_Model(Context context) {
        this.context = context;
    }
}
