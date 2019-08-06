package com.creativeshare.sunfun.viewmodel.user_profile_view_model;

import android.app.ProgressDialog;
import android.content.Context;

import com.creativeshare.sunfun.R;
import com.creativeshare.sunfun.models.UserModel;
import com.creativeshare.sunfun.remote.Api;
import com.creativeshare.sunfun.share.Common;
import com.creativeshare.sunfun.tags.Tags;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repository {

    public void getUser(String id, UserListener listener, Context context)
    {
        ProgressDialog dialog = Common.createProgressDialog(context,context.getString(R.string.wait));
        dialog.setCancelable(false);
        dialog.show();

        Api.getService(Tags.base_url)
                .getUserData(id)
                .enqueue(new Callback<UserModel>() {
                    @Override
                    public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                        dialog.dismiss();
                        if (response.isSuccessful()&&response.body()!=null)
                        {
                            listener.onSuccess(response.body());
                        }else
                            {
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
