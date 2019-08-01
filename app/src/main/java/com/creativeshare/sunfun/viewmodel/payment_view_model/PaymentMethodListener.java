package com.creativeshare.sunfun.viewmodel.payment_view_model;

import com.creativeshare.sunfun.models.PaymentDataModel;

import java.util.List;

public interface PaymentMethodListener {

    void onSuccess(List<PaymentDataModel.PaymentModel> paymentModelList);
    void onFailed(int code);
    void onError(String error);
}
