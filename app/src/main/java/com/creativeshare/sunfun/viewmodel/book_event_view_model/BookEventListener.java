package com.creativeshare.sunfun.viewmodel.book_event_view_model;

public interface BookEventListener {

    void onSuccess();
    void onFailed(int code);
    void onError(String error);
}
