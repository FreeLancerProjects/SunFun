package com.creativeshare.sunfun.viewmodel.my_event_view_model;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.creativeshare.sunfun.R;
import com.creativeshare.sunfun.models.EventDataModel;

import java.util.ArrayList;
import java.util.List;

public class MyEventViewModel extends AndroidViewModel implements EventsListener, EventsLoadMoreListener {

    private Context context;
    public MutableLiveData<List<EventDataModel.EventModel>> data,dataLoadMore;
    public MutableLiveData<Boolean> error,errorLoadMore;
    private Repository repository;
    public int current_page = 1;

    public MyEventViewModel(@NonNull Application application) {
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

    public void getMyEvents(String id)
    {
        current_page = 1;
        repository.getEvents(this,context,id);
    }

    public void loadMore(String user_id) {
        int next_page = current_page + 1;
        repository.loadMore(this, context, user_id, next_page);

    }


    @Override
    public void onSuccess(List<EventDataModel.EventModel> eventModelList) {

        data.setValue(eventModelList);
    }

    @Override
    public void onFailed(int code) {
        if (code==201)
        {
            List<EventDataModel.EventModel> list = new ArrayList<>();
            data.postValue(list);
        }else
            {
                error.postValue(true);
                Toast.makeText(getApplication(), R.string.failed, Toast.LENGTH_SHORT).show();

            }

        Log.e("code",code+"_");

    }

    @Override
    public void onError(String error) {

        this.error.postValue(true);
        Log.e("Error",error);
        Toast.makeText(getApplication(), R.string.something, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onLoadSuccess(List<EventDataModel.EventModel> eventModelList) {
        if (eventModelList.size() > 0) {
            current_page++;
        }
        dataLoadMore.setValue(eventModelList);
    }

    @Override
    public void onLoadFailed(int code) {
        if (code == 201)
        {
            List<EventDataModel.EventModel> list = new ArrayList<>();
            data.postValue(list);

        }
        errorLoadMore.postValue(true);
        Log.e("code",code+"_");

    }

    @Override
    public void onLoadError(String error) {
        this.errorLoadMore.postValue(true);
        Log.e("Error",error);

    }
}
