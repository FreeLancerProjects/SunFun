package com.creativeshare.sunfun.viewmodel.payment_view_model;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.creativeshare.sunfun.R;
import com.creativeshare.sunfun.models.PaymentDataModel;

import java.util.List;

public class PaymentViewModel extends AndroidViewModel implements PaymentMethodListener {

    private Context context;
    public MutableLiveData<List<PaymentDataModel.PaymentModel>> data;
    public MutableLiveData<Boolean> error,errorLoadMore;
    private Repository repository;

    public PaymentViewModel(@NonNull Application application) {
        super(application);
        data = new MutableLiveData<>();
        error = new MutableLiveData<>();
        errorLoadMore = new MutableLiveData<>();

        repository = new Repository();

    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void getPaymentMethod()
    {
        repository.getPayment(this,context);
    }




    @Override
    public void onSuccess(List<PaymentDataModel.PaymentModel> paymentModelList) {
        data.postValue(paymentModelList);
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
