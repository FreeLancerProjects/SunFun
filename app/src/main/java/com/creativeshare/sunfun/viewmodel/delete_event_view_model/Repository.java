package com.creativeshare.sunfun.viewmodel.delete_event_view_model;

import android.content.Context;
import android.util.Log;

import com.creativeshare.sunfun.remote.Api;
import com.creativeshare.sunfun.tags.Tags;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repository {

    public void deleteEvent(DeleteEventListener listener, Context context, int event_id)
    {

        Api.getService(Tags.base_url)
                .deleteEvent(event_id)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()&&response.body()!=null)
                        {
                            listener.onSuccess();
                        }else
                            {
                                try {
                                    Log.e("error_code",response.errorBody().string());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                listener.onFailed(response.code());
                            }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        try {
                            listener.onError(t.getMessage());

                        }catch (Exception e){}
                    }
                });
    }

}
