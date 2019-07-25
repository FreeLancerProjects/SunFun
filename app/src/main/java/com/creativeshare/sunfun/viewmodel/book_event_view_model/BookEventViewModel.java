package com.creativeshare.sunfun.viewmodel.book_event_view_model;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.AndroidViewModel;

import com.creativeshare.sunfun.R;

public class BookEventViewModel extends AndroidViewModel {

    private Context context;
    public ObservableField<String> subscribers = new ObservableField<>("");
    public ObservableField<String> payment = new ObservableField<>("");

    public ObservableField<String> subscribers_error = new ObservableField<>(null);


    public BookEventViewModel(@NonNull Application application) {
        super(application);
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void BookEvent()
    {
        if (!TextUtils.isEmpty(subscribers.get())&& !TextUtils.isEmpty(payment.get()))
        {

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


}
