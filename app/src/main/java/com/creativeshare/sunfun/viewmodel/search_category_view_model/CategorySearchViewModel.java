package com.creativeshare.sunfun.viewmodel.search_category_view_model;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.creativeshare.sunfun.R;
import com.creativeshare.sunfun.models.EventDataModel;
import com.creativeshare.sunfun.viewmodel.listeners.SearchEventsListener;

import java.util.List;

public class CategorySearchViewModel extends AndroidViewModel implements SearchEventsListener {

    private Context context;
    public MutableLiveData<List<EventDataModel.EventModel>> data;
    public MutableLiveData<Boolean> error;
    private Repository repository;


    public CategorySearchViewModel(@NonNull Application application) {
        super(application);
        data = new MutableLiveData<>();
        error = new MutableLiveData<>();
        repository = new Repository();

    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void getEvents(String cat_id, String sub_catId, String user_id) {
        repository.getEvents(this, context, cat_id, sub_catId, user_id);
    }




    @Override
    public void onSearchSuccess(List<EventDataModel.EventModel> eventModelList) {

        data.setValue(eventModelList);
    }

    @Override
    public void onSearchFailed(int code) {
        error.postValue(true);
        Log.e("code", code + "_");
        Toast.makeText(getApplication(), R.string.failed, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onSearchError(String error) {
        this.error.postValue(true);
        Log.e("Error", error);
        Toast.makeText(getApplication(), R.string.something, Toast.LENGTH_SHORT).show();

    }
}
