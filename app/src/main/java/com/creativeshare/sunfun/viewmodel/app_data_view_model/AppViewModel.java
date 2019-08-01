package com.creativeshare.sunfun.viewmodel.app_data_view_model;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.creativeshare.sunfun.R;
import com.creativeshare.sunfun.models.AppData;

public class AppViewModel extends AndroidViewModel implements AboutAppListener {

    private Context context;
    public MutableLiveData<AppData> data;
    public MutableLiveData<Boolean> error;
    private Repository repository;

    public AppViewModel(@NonNull Application application) {
        super(application);
        data = new MutableLiveData<>();
        error = new MutableLiveData<>();
        repository = new Repository();

    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void getAppData(int type) {
        repository.getAppData(this, context,type);
    }


    @Override
    public void onSuccess(AppData appData) {
        data.postValue(appData);
    }

    @Override
    public void onFailed(int code) {
        error.postValue(true);
        Log.e("code", code + "_");
        Toast.makeText(getApplication(), R.string.failed, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onError(String error) {

        this.error.postValue(true);
        Log.e("Error", error);
        Toast.makeText(getApplication(), R.string.something, Toast.LENGTH_SHORT).show();

    }


}
