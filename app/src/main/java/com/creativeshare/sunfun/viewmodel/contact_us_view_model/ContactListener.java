package com.creativeshare.sunfun.viewmodel.contact_us_view_model;

public interface ContactListener {

    void onSuccess();
    void onFailed(int code);
    void onError(String error);
}
