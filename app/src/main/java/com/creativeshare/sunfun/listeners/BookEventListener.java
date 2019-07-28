package com.creativeshare.sunfun.listeners;

public interface BookEventListener {

    void onSuccess();
    void onFailed(int code);
    void onError(String error);
}
