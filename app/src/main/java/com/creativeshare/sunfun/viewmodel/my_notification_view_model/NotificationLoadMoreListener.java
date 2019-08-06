package com.creativeshare.sunfun.viewmodel.my_notification_view_model;

import com.creativeshare.sunfun.models.NotificationDataModel;

import java.util.List;

public interface NotificationLoadMoreListener {
    void onLoadSuccess(List<NotificationDataModel.NotificationModel> notificationModelList);
    void onLoadFailed(int code);
    void onLoadError(String error);
}
