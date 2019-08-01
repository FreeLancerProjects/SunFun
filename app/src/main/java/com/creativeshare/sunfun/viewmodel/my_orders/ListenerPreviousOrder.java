package com.creativeshare.sunfun.viewmodel.my_orders;

import com.creativeshare.sunfun.models.OrderDataModel;

import java.util.List;

public interface ListenerPreviousOrder {

    void onPreviousSuccess(List<OrderDataModel.OrderModel> orderModelList);
    void onPreviousFailed(int code);
    void onPreviousError(String error);
}
