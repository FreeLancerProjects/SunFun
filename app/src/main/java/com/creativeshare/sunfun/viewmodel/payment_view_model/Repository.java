package com.creativeshare.sunfun.viewmodel.payment_view_model;

import android.content.Context;

import com.creativeshare.sunfun.models.PaymentDataModel;
import com.creativeshare.sunfun.remote.Api;
import com.creativeshare.sunfun.tags.Tags;
import com.creativeshare.sunfun.viewmodel.listeners.PaymentMethodListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repository {

    public void getPayment(PaymentMethodListener listener, Context context)
    {

        Api.getService(Tags.base_url)
                .getPaymentMethod()
                .enqueue(new Callback<PaymentDataModel>() {
                    @Override
                    public void onResponse(Call<PaymentDataModel> call, Response<PaymentDataModel> response) {
                        if (response.isSuccessful()&&response.body()!=null&&response.body().getData()!=null)
                        {
                            listener.onSuccess(response.body().getData());
                        }else
                            {
                                listener.onFailed(response.code());
                            }
                    }

                    @Override
                    public void onFailure(Call<PaymentDataModel> call, Throwable t) {
                        try {
                            listener.onError(t.getMessage());

                        }catch (Exception e){}
                    }
                });
    }

}
