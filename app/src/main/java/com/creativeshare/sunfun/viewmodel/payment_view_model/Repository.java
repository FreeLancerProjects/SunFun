package com.creativeshare.sunfun.viewmodel.payment_view_model;

import android.app.ProgressDialog;
import android.content.Context;

import com.creativeshare.sunfun.R;
import com.creativeshare.sunfun.models.PaymentDataModel;
import com.creativeshare.sunfun.remote.Api;
import com.creativeshare.sunfun.share.Common;
import com.creativeshare.sunfun.tags.Tags;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repository {

    public void getPayment(PaymentMethodListener listener, Context context)
    {
        ProgressDialog dialog = Common.createProgressDialog(context,context.getString(R.string.wait));
        dialog.show();

        Api.getService(Tags.base_url)
                .getPaymentMethod()
                .enqueue(new Callback<PaymentDataModel>() {
                    @Override
                    public void onResponse(Call<PaymentDataModel> call, Response<PaymentDataModel> response) {
                        dialog.dismiss();
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
                            dialog.dismiss();

                            listener.onError(t.getMessage());

                        }catch (Exception e){}
                    }
                });
    }

}
