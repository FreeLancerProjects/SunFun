package com.creativeshare.sunfun.listeners;

public interface ContactListener {

    void onSuccess();
    void onFailed(int code);
    void onError(String error);
}
