package com.creativeshare.sunfun.viewmodel.book_event_view_model;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.databinding.ObservableField;
import androidx.lifecycle.AndroidViewModel;

import com.creativeshare.sunfun.R;

public class BookEventViewModel extends AndroidViewModel {

    private Context context;
    private ObservableField<Integer> subscribers = new ObservableField<>(0);
    private ObservableField<String> payment = new ObservableField<>("");

    public ObservableField<String> subscribers_error = new ObservableField<>(null);


    public BookEventViewModel(@NonNull Application application) {
        super(application);
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void BookEvent()
    {
        if (subscribers.get()!=0&& !TextUtils.isEmpty(payment.get()))
        {

        }else
            {
                if (subscribers.get()==0)
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


    @BindingAdapter("app:Error")
    public static void error(TextView textView,String error)
    {
        textView.setError(error);
    }
}
