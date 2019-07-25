package com.creativeshare.sunfun.viewmodel.bank_account_view_model;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.creativeshare.sunfun.R;
import com.creativeshare.sunfun.models.BankDataModel;
import com.creativeshare.sunfun.listeners.BankListener;

import java.util.List;

public class BankViewModel extends AndroidViewModel implements BankListener {

    private Context context;
    public MutableLiveData<List<BankDataModel.BankModel>> data;
    public MutableLiveData<Boolean> error;
    private Repository repository;

    public BankViewModel(@NonNull Application application) {
        super(application);
        data = new MutableLiveData<>();
        error = new MutableLiveData<>();
        repository = new Repository();

    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void getBanks()
    {
        repository.getBanks(this,context);
    }


    @Override
    public void onSuccess(List<BankDataModel.BankModel> bankModelList) {
        data.postValue(bankModelList);
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
