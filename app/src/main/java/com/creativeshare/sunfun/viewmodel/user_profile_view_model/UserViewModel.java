package com.creativeshare.sunfun.viewmodel.user_profile_view_model;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.creativeshare.sunfun.R;
import com.creativeshare.sunfun.models.UserModel;

public class UserViewModel extends AndroidViewModel implements UserListener {

    private Context context;
    public MutableLiveData<UserModel> data;
    public MutableLiveData<Boolean> error;
    private Repository repository;

    public UserViewModel(@NonNull Application application) {
        super(application);
        data = new MutableLiveData<>();
        error = new MutableLiveData<>();

        repository = new Repository();

    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void getUser(String id)
    {
        repository.getUser(id,this,context);
    }



    @Override
    public void onSuccess(UserModel userModel) {

        data.setValue(userModel);
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


}
