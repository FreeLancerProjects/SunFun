package com.creativeshare.sunfun.viewmodel.search_category_view_model;

import com.creativeshare.sunfun.models.EventDataModel;

import java.util.List;

public interface SearchEventsListener {
    void onSearchSuccess(List<EventDataModel.EventModel> eventModelList);
    void onSearchFailed(int code);
    void onSearchError(String error);
}
