package com.creativeshare.sunfun.viewmodel.book_event_view_model;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.creativeshare.sunfun.R;
import com.creativeshare.sunfun.listeners.BookEventListener;
import com.creativeshare.sunfun.models.ActivityModelUpload;
import com.creativeshare.sunfun.models.EventModelToUpload;

import java.util.List;

public class BookEventViewModel extends AndroidViewModel implements BookEventListener {

    private Context context;
    public ObservableField<String> subscribers = new ObservableField<>("");
    public ObservableField<String> payment = new ObservableField<>("");
    public ObservableField<String> subscribers_error = new ObservableField<>();
    public MutableLiveData<Boolean> success = new MutableLiveData<>();
    public ObservableField<List<ActivityModelUpload>> activityList = new ObservableField<>();
    private Repository repository;

    public BookEventViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository();
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void BookEvent(int user_id,String company_id,int event_id)
    {
        if (!TextUtils.isEmpty(subscribers.get())&& !TextUtils.isEmpty(payment.get()))
        {
            EventModelToUpload eventModelToUpload = new EventModelToUpload(company_id,user_id,event_id,Integer.parseInt(subscribers.get()),Integer.parseInt(payment.get()),activityList.get());
            repository.bookEvent(this,context,eventModelToUpload);
        }else
            {
                if (TextUtils.isEmpty(subscribers.get()))
                {
                    subscribers_error.set(context.getString(R.string.field_req));
                }else
                    {
                        subscribers_error.set(null);
                    }

                if (payment.get().isEmpty())
                {
                    Toast.makeText(context, R.string.ch_pm_methd, Toast.LENGTH_SHORT).show();

                }

            }
    }


    @Override
    public void onSuccess() {
        success.postValue(true);
    }

    @Override
    public void onFailed(int code) {
        Toast.makeText(context, context.getString(R.string.failed), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onError(String error) {
        Toast.makeText(context, context.getString(R.string.something), Toast.LENGTH_SHORT).show();

    }
}
