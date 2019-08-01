package com.creativeshare.sunfun.viewmodel.app_data_view_model;

import com.creativeshare.sunfun.models.AppData;

public interface AboutAppListener {

    void onSuccess(AppData appData);
    void onFailed(int code);
    void onError(String error);
}
