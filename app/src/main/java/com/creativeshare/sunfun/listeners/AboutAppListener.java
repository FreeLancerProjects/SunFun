package com.creativeshare.sunfun.listeners;

import com.creativeshare.sunfun.models.AppData;

import java.util.List;

public interface AboutAppListener {

    void onSuccess(List<AppData.AppModel> appDataList);
    void onFailed(int code);
    void onError(String error);
}
