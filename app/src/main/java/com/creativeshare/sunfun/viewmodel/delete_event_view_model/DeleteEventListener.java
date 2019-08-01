package com.creativeshare.sunfun.viewmodel.delete_event_view_model;

public interface DeleteEventListener {

    void onSuccess();
    void onFailed(int code);
    void onError(String error);
}
