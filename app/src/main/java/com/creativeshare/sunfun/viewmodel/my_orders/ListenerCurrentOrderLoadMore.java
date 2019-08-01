package com.creativeshare.sunfun.viewmodel.my_orders;

import com.creativeshare.sunfun.models.OrderDataModel;

import java.util.List;

public interface ListenerCurrentOrderLoadMore {

    void onCurrentLoadMoreSuccess(List<OrderDataModel.OrderModel> orderModelList);
    void onCurrentLoadMoreFailed(int code);
    void onCurrentLoadMoreError(String error);
}
