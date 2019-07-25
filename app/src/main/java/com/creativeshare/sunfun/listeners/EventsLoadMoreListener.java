package com.creativeshare.sunfun.listeners;

import com.creativeshare.sunfun.models.EventDataModel;

import java.util.List;

public interface EventsLoadMoreListener {
    void onLoadSuccess(List<EventDataModel.EventModel> eventModelList);
    void onLoadFailed(int code);
    void onLoadError(String error);
}
