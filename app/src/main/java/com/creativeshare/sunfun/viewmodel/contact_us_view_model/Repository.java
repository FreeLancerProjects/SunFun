package com.creativeshare.sunfun.viewmodel.contact_us_view_model;

import android.app.ProgressDialog;
import android.content.Context;

import com.creativeshare.sunfun.R;
import com.creativeshare.sunfun.remote.Api;
import com.creativeshare.sunfun.share.Common;
import com.creativeshare.sunfun.tags.Tags;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repository {

    public void sendContact(ContactListener listener, Context context, String name,String email,String subject)
    {
        ProgressDialog dialog = Common.createProgressDialog(context,context.getString(R.string.wait));
        dialog.setCancelable(false);
        dialog.show();
        Api.getService(Tags.base_url)
                .sendContact(name,"",email,subject)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        dialog.dismiss();
                        if (response.isSuccessful())
                        {
                            listener.onSuccess();
                        }else
                            {
                                listener.onFailed(response.code());
                            }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        try {
                            dialog.dismiss();
                            listener.onError(t.getMessage());

                        }catch (Exception e){}
                    }
                });
    }

}
