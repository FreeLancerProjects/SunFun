package com.creativeshare.sunfun.viewmodel.my_notification_view_model;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.creativeshare.sunfun.R;
import com.creativeshare.sunfun.models.NotificationDataModel;

import java.util.List;

public class NotificationViewModel extends AndroidViewModel implements NotificationListener, NotificationLoadMoreListener {

    private Context context;
    public MutableLiveData<List<NotificationDataModel.NotificationModel>> data,dataLoadMore;
    public MutableLiveData<Boolean> error,errorLoadMore;
    private Repository repository;
    private int current_page = 1;

    public NotificationViewModel(@NonNull Application application) {
        super(application);
        data = new MutableLiveData<>();
        dataLoadMore = new MutableLiveData<>();
        error = new MutableLiveData<>();
        errorLoadMore = new MutableLiveData<>();

        repository = new Repository();

    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void getNotification(int id)
    {
        current_page = 1;
        repository.getNotification(this,context,id);
    }

    public void loadMore(int user_id) {
        int next_page = current_page + 1;
        repository.loadMore(this, context, user_id, next_page);

    }


    @Override
    public void onSuccess(List<NotificationDataModel.NotificationModel> notificationModelList) {

        data.setValue(notificationModelList);
    }

    @Override
    public void onFailed(int code) {
        error.postValue(true);
        Log.e("code",code+"_");
        Toast.makeText(getApplication(), R.string.failed, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onError(String error) {

        this.error.postValue(true);
        Log.e("Error",error);
        Toast.makeText(getApplication(), R.string.something, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onLoadSuccess(List<NotificationDataModel.NotificationModel> notificationModelList) {
        if (notificationModelList.size() > 0) {
            current_page++;
        }
        dataLoadMore.setValue(notificationModelList);
    }

    @Override
    public void onLoadFailed(int code) {
        errorLoadMore.postValue(true);
        Log.e("code",code+"_");

    }

    @Override
    public void onLoadError(String error) {
        this.errorLoadMore.postValue(true);
        Log.e("Error",error);

    }
}
