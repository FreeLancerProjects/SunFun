package com.creativeshare.sunfun.viewmodel.signup_view_model;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.creativeshare.sunfun.R;
import com.creativeshare.sunfun.models.UserModel;
import com.creativeshare.sunfun.remote.Api;
import com.creativeshare.sunfun.share.Common;
import com.creativeshare.sunfun.tags.Tags;
import com.creativeshare.sunfun.viewmodel.listeners.Listener;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repository {

    public void signUp(String name,String phone_code,String phone,String email, String password, Listener listener, Context context)
    {
        ProgressDialog dialog = Common.createProgressDialog(context,context.getString(R.string.wait));
        dialog.setCancelable(false);
        dialog.show();

        Api.getService(Tags.base_url)
                .signUp(name,email,password,phone_code,phone,"2")
                .enqueue(new Callback<UserModel>() {
                    @Override
                    public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                        dialog.dismiss();
                        if (response.isSuccessful()&&response.body()!=null)
                        {
                            listener.onSuccess(response.body());
                        }else
                            {
                                try {
                                    Log.e("respons",response.errorBody().string());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                listener.onFailed(response.code());
                            }
                    }

                    @Override
                    public void onFailure(Call<UserModel> call, Throwable t) {
                        try {
                            dialog.dismiss();
                            listener.onError(t.getMessage());

                        }catch (Exception e){}
                    }
                });
    }
}
