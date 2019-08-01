package com.creativeshare.sunfun.viewmodel.my_orders;

import com.creativeshare.sunfun.models.OrderDataModel;

import java.util.List;

public interface ListenerPreviousOrderLoadMore {

    void onPreviousLoadMoreSuccess(List<OrderDataModel.OrderModel> orderModelList);
    void onPreviousLoadMoreFailed(int code);
    void onPreviousLoadMoreError(String error);
}
