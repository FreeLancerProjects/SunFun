package com.creativeshare.sunfun.viewmodel.search_category_view_model;

import android.content.Context;
import android.util.Log;

import com.creativeshare.sunfun.models.EventDataModel;
import com.creativeshare.sunfun.remote.Api;
import com.creativeshare.sunfun.tags.Tags;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repository {

    public void getEvents(SearchEventsListener listener, Context context, String cat_id, String sub_catId, String user_id)
    {
        Log.e("cat_id",cat_id);
        Log.e("sub_id",sub_catId);
        Api.getService(Tags.base_url)
                .searchByCategory(sub_catId,cat_id,user_id)
                .enqueue(new Callback<EventDataModel>() {
                    @Override
                    public void onResponse(Call<EventDataModel> call, Response<EventDataModel> response) {
                        if (response.isSuccessful()&&response.body()!=null&&response.body().getData()!=null)
                        {
                            listener.onSearchSuccess(response.body().getData());
                        }else
                            {
                                try {
                                    Log.e("error_code",response.code()+"_"+response.errorBody().string());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                listener.onSearchFailed(response.code());
                            }
                    }

                    @Override
                    public void onFailure(Call<EventDataModel> call, Throwable t) {
                        try {
                            listener.onSearchError(t.getMessage());

                        }catch (Exception e){}
                    }
                });
    }

}
