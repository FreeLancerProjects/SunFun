package com.creativeshare.sunfun.viewmodel.my_event_view_model;

import com.creativeshare.sunfun.models.EventDataModel;

import java.util.List;

public interface EventsListener {
    void onSuccess(List<EventDataModel.EventModel> eventModelList);
    void onFailed(int code);
    void onError(String error);
}
