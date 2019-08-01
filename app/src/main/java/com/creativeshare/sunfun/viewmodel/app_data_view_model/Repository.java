package com.creativeshare.sunfun.viewmodel.app_data_view_model;

import android.content.Context;

import com.creativeshare.sunfun.models.AppData;
import com.creativeshare.sunfun.remote.Api;
import com.creativeshare.sunfun.tags.Tags;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repository {

    public void getAppData(AboutAppListener listener, Context context,int type)
    {

        Api.getService(Tags.base_url)
                .getAppData(type)
                .enqueue(new Callback<AppData>() {
                    @Override
                    public void onResponse(Call<AppData> call, Response<AppData> response) {
                        if (response.isSuccessful()&&response.body()!=null)
                        {
                            listener.onSuccess(response.body());
                        }else
                            {
                                listener.onFailed(response.code());
                            }
                    }

                    @Override
                    public void onFailure(Call<AppData> call, Throwable t) {
                        try {
                            listener.onError(t.getMessage());

                        }catch (Exception e){}
                    }
                });
    }

}
