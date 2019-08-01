package com.creativeshare.sunfun.viewmodel.my_orders;

import com.creativeshare.sunfun.models.OrderDataModel;

import java.util.List;

public interface ListenerCurrentOrder {

    void onCurrentSuccess(List<OrderDataModel.OrderModel> orderModelList);
    void onCurrentFailed(int code);
    void onCurrentError(String error);
}
