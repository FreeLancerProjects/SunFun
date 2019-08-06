package com.creativeshare.sunfun.viewmodel.my_notification_view_model;

import android.content.Context;

import com.creativeshare.sunfun.models.NotificationDataModel;
import com.creativeshare.sunfun.remote.Api;
import com.creativeshare.sunfun.tags.Tags;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repository {

    public void getNotification(NotificationListener listener, Context context,int id)
    {

        Api.getService(Tags.base_url)
                .getMyNotification(id,1)
                .enqueue(new Callback<NotificationDataModel>() {
                    @Override
                    public void onResponse(Call<NotificationDataModel> call, Response<NotificationDataModel> response) {
                        if (response.isSuccessful()&&response.body()!=null&&response.body().getData()!=null)
                        {
                            listener.onSuccess(response.body().getData());
                        }else
                            {
                                listener.onFailed(response.code());
                            }
                    }

                    @Override
                    public void onFailure(Call<NotificationDataModel> call, Throwable t) {
                        try {
                            listener.onError(t.getMessage());

                        }catch (Exception e){}
                    }
                });
    }
    public void loadMore(NotificationLoadMoreListener listener, Context context, int id, int page_index)
    {

        Api.getService(Tags.base_url)
                .getMyNotification(id,page_index)
                .enqueue(new Callback<NotificationDataModel>() {
                    @Override
                    public void onResponse(Call<NotificationDataModel> call, Response<NotificationDataModel> response) {
                        if (response.isSuccessful()&&response.body()!=null&&response.body().getData()!=null)
                        {
                            listener.onLoadSuccess(response.body().getData());
                        }else
                        {
                            listener.onLoadFailed(response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<NotificationDataModel> call, Throwable t) {
                        try {
                            listener.onLoadError(t.getMessage());

                        }catch (Exception e){}
                    }
                });
    }
}
