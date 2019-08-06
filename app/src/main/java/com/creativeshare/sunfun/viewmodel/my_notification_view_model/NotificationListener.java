package com.creativeshare.sunfun.viewmodel.my_notification_view_model;

import com.creativeshare.sunfun.models.NotificationDataModel;

import java.util.List;

public interface NotificationListener {
    void onSuccess(List<NotificationDataModel.NotificationModel> notificationModelList);
    void onFailed(int code);
    void onError(String error);
}
