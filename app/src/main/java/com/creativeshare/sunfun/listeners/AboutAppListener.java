package com.creativeshare.sunfun.listeners;

import com.creativeshare.sunfun.models.AppData;

public interface AboutAppListener {

    void onSuccess(AppData appData);
    void onFailed(int code);
    void onError(String error);
}
