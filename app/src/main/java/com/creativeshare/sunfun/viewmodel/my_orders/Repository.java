package com.creativeshare.sunfun.viewmodel.my_orders;

import android.content.Context;

import com.creativeshare.sunfun.models.OrderDataModel;
import com.creativeshare.sunfun.remote.Api;
import com.creativeshare.sunfun.tags.Tags;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repository {

    public void getMyCurrentOrder(ListenerCurrentOrder listener, Context context,int id)
    {

        Api.getService(Tags.base_url)
                .getMyCurrentOrders(id,1)
                .enqueue(new Callback<OrderDataModel>() {
                    @Override
                    public void onResponse(Call<OrderDataModel> call, Response<OrderDataModel> response) {
                        if (response.isSuccessful()&&response.body()!=null&&response.body().getData()!=null)
                        {
                            listener.onCurrentSuccess(response.body().getData());
                        }else
                            {
                                listener.onCurrentFailed(response.code());
                            }
                    }

                    @Override
                    public void onFailure(Call<OrderDataModel> call, Throwable t) {
                        try {
                            listener.onCurrentError(t.getMessage());

                        }catch (Exception e){}
                    }
                });
    }
    public void loadMoreCurrentOrder(ListenerCurrentOrderLoadMore listener, Context context, int id, int page_index)
    {

        Api.getService(Tags.base_url)
                .getMyCurrentOrders(id,1)
                .enqueue(new Callback<OrderDataModel>() {
                    @Override
                    public void onResponse(Call<OrderDataModel> call, Response<OrderDataModel> response) {
                        if (response.isSuccessful()&&response.body()!=null&&response.body().getData()!=null)
                        {
                            listener.onCurrentLoadMoreSuccess(response.body().getData());
                        }else
                        {

                            listener.onCurrentLoadMoreFailed(response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<OrderDataModel> call, Throwable t) {
                        try {
                            listener.onCurrentLoadMoreError(t.getMessage());

                        }catch (Exception e){}
                    }
                });
    }

    public void getMyPreviousOrder(ListenerPreviousOrder listener, Context context,int id)
    {

        Api.getService(Tags.base_url)
                .getMyPreviousOrders(id,1)
                .enqueue(new Callback<OrderDataModel>() {
                    @Override
                    public void onResponse(Call<OrderDataModel> call, Response<OrderDataModel> response) {
                        if (response.isSuccessful()&&response.body()!=null&&response.body().getData()!=null)
                        {
                            listener.onPreviousSuccess(response.body().getData());
                        }else
                        {

                            listener.onPreviousFailed(response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<OrderDataModel> call, Throwable t) {
                        try {
                            listener.onPreviousError(t.getMessage());

                        }catch (Exception e){}
                    }
                });
    }
    public void loadMorePreviousOrder(ListenerPreviousOrderLoadMore listener, Context context, int id, int page_index)
    {

        Api.getService(Tags.base_url)
                .getMyPreviousOrders(id,1)
                .enqueue(new Callback<OrderDataModel>() {
                    @Override
                    public void onResponse(Call<OrderDataModel> call, Response<OrderDataModel> response) {
                        if (response.isSuccessful()&&response.body()!=null&&response.body().getData()!=null)
                        {
                            listener.onPreviousLoadMoreSuccess(response.body().getData());
                        }else
                        {

                            listener.onPreviousLoadMoreFailed(response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<OrderDataModel> call, Throwable t) {
                        try {
                            listener.onPreviousLoadMoreError(t.getMessage());

                        }catch (Exception e){}
                    }
                });
    }
}
