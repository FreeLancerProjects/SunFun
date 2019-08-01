package com.creativeshare.sunfun.viewmodel.add_activity_view_model;

public interface AddActivityListener {

    void onSuccess();
    void onFailed(int code);
    void onError(String error);
}
