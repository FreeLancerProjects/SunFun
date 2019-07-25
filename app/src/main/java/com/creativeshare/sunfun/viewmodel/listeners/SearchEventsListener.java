package com.creativeshare.sunfun.viewmodel.listeners;

import com.creativeshare.sunfun.models.EventDataModel;

import java.util.List;

public interface SearchEventsListener {
    void onSearchSuccess(List<EventDataModel.EventModel> eventModelList);
    void onSearchFailed(int code);
    void onSearchError(String error);
}
