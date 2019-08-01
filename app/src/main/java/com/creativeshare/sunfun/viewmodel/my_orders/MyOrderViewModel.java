package com.creativeshare.sunfun.viewmodel.my_orders;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.creativeshare.sunfun.R;
import com.creativeshare.sunfun.models.OrderDataModel;

import java.util.ArrayList;
import java.util.List;

public class MyOrderViewModel extends AndroidViewModel implements ListenerCurrentOrder,ListenerCurrentOrderLoadMore,ListenerPreviousOrder,ListenerPreviousOrderLoadMore {

    private Context context;
    public MutableLiveData<List<OrderDataModel.OrderModel>> dataCurrentOrder,dataPreviousOrder,dataLoadMoreCurrentOrder,dataLoadMorePreviousOrder;
    public MutableLiveData<Boolean> errorCurrentOrder,errorLoadMoreCurrentOrder,errorPreviousOrder,errorLoadMorePreviousOrder;
    public int current_page_current_order = 1,current_page_previous_order=1;
    private Repository repository;
    public MyOrderViewModel(@NonNull Application application) {
        super(application);
        dataCurrentOrder = new MutableLiveData<>();
        dataPreviousOrder = new MutableLiveData<>();
        dataLoadMoreCurrentOrder = new MutableLiveData<>();
        dataLoadMorePreviousOrder = new MutableLiveData<>();

        errorCurrentOrder = new MutableLiveData<>();
        errorLoadMoreCurrentOrder = new MutableLiveData<>();
        errorPreviousOrder = new MutableLiveData<>();
        errorLoadMorePreviousOrder = new MutableLiveData<>();
        repository = new Repository();


    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void getMyCurrentOrder(int id)
    {
        current_page_current_order = 1;
        repository.getMyCurrentOrder(this,context,id);
    }

    public void loadMoreCurrentOrder(int user_id) {
        int next_page = current_page_current_order + 1;
        repository.loadMoreCurrentOrder(this,context, user_id,next_page);

    }


    public void getMyPreviousOrder(int id)
    {
        current_page_previous_order = 1;
        repository.getMyPreviousOrder(this,context,id);
    }

    public void loadMorePreviousOrder(int user_id) {
        int next_page = current_page_previous_order + 1;
        repository.loadMorePreviousOrder(this,context,user_id,next_page);

    }


    @Override
    public void onCurrentSuccess(List<OrderDataModel.OrderModel> orderModelList) {
     dataCurrentOrder.postValue(orderModelList);
    }

    @Override
    public void onCurrentFailed(int code) {
        if (code==201)
        {
            List<OrderDataModel.OrderModel> orderModelList = new ArrayList<>();
            dataCurrentOrder.postValue(orderModelList);
        }else
            {
                errorCurrentOrder.postValue(true);
                Log.e("code",code+"_");
                Toast.makeText(getApplication(), R.string.failed, Toast.LENGTH_SHORT).show();

            }

    }

    @Override
    public void onCurrentError(String error) {
        this.errorCurrentOrder.postValue(true);
        Log.e("Error",error);
        Toast.makeText(getApplication(), R.string.something, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onCurrentLoadMoreSuccess(List<OrderDataModel.OrderModel> orderModelList) {
        dataLoadMoreCurrentOrder.postValue(orderModelList);
    }

    @Override
    public void onCurrentLoadMoreFailed(int code) {
        errorLoadMoreCurrentOrder.postValue(true);
        Log.e("code",code+"_");
    }

    @Override
    public void onCurrentLoadMoreError(String error) {
        this.errorLoadMoreCurrentOrder.postValue(true);
        Log.e("Error",error);
    }

    @Override
    public void onPreviousSuccess(List<OrderDataModel.OrderModel> orderModelList) {
        dataPreviousOrder.postValue(orderModelList);
    }

    @Override
    public void onPreviousFailed(int code) {
        if (code==201)
        {
            List<OrderDataModel.OrderModel> orderModelList = new ArrayList<>();
            dataCurrentOrder.postValue(orderModelList);
        }else
        {
            errorPreviousOrder.postValue(true);
            Log.e("code",code+"_");
            Toast.makeText(getApplication(), R.string.failed, Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public void onPreviousError(String error) {
        this.errorPreviousOrder.postValue(true);
        Log.e("Error",error);
        Toast.makeText(getApplication(), R.string.something, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onPreviousLoadMoreSuccess(List<OrderDataModel.OrderModel> orderModelList) {
        dataLoadMorePreviousOrder.postValue(orderModelList);
    }

    @Override
    public void onPreviousLoadMoreFailed(int code) {
        errorLoadMorePreviousOrder.postValue(true);
        Log.e("code",code+"_");
    }

    @Override
    public void onPreviousLoadMoreError(String error) {
        this.errorLoadMorePreviousOrder.postValue(true);
        Log.e("Error",error);
    }



}
