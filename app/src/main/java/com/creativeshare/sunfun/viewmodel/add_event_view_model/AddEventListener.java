package com.creativeshare.sunfun.viewmodel.add_event_view_model;

import com.creativeshare.sunfun.models.EventIdModel;

public interface AddEventListener {

    void onSuccess(EventIdModel eventIdModel);
    void onFailed(int code);
    void onError(String error);
}
