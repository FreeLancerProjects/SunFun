package com.creativeshare.sunfun.viewmodel.events_view_model;

import android.content.Context;

import com.creativeshare.sunfun.models.EventDataModel;
import com.creativeshare.sunfun.remote.Api;
import com.creativeshare.sunfun.tags.Tags;
import com.creativeshare.sunfun.viewmodel.listeners.EventsListener;
import com.creativeshare.sunfun.viewmodel.listeners.EventsLoadMoreListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repository {

    public void getEvents(EventsListener listener, Context context,String id)
    {

        Api.getService(Tags.base_url)
                .getEvents(id,1)
                .enqueue(new Callback<EventDataModel>() {
                    @Override
                    public void onResponse(Call<EventDataModel> call, Response<EventDataModel> response) {
                        if (response.isSuccessful()&&response.body()!=null&&response.body().getData()!=null)
                        {
                            listener.onSuccess(response.body().getData());
                        }else
                            {
                                listener.onFailed(response.code());
                            }
                    }

                    @Override
                    public void onFailure(Call<EventDataModel> call, Throwable t) {
                        try {
                            listener.onError(t.getMessage());

                        }catch (Exception e){}
                    }
                });
    }
    public void loadMore(EventsLoadMoreListener listener, Context context, String id, int page_index)
    {

        Api.getService(Tags.base_url)
                .getEvents(id,page_index)
                .enqueue(new Callback<EventDataModel>() {
                    @Override
                    public void onResponse(Call<EventDataModel> call, Response<EventDataModel> response) {
                        if (response.isSuccessful()&&response.body()!=null&&response.body().getData()!=null)
                        {
                            listener.onLoadSuccess(response.body().getData());
                        }else
                        {
                            listener.onLoadFailed(response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<EventDataModel> call, Throwable t) {
                        try {
                            listener.onLoadError(t.getMessage());

                        }catch (Exception e){}
                    }
                });
    }
}
