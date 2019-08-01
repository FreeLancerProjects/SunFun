package com.creativeshare.sunfun.viewmodel.add_event_view_model;


import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.AndroidViewModel;

import com.creativeshare.sunfun.R;

public class ViewModelEventDateTime extends AndroidViewModel{

    public ObservableField<Long> start_date = new ObservableField<>(0L);
    public ObservableField<Long> start_time = new ObservableField<>(0L);
    public ObservableField<Long> end_date = new ObservableField<>(0L);
    public ObservableField<Long> end_time = new ObservableField<>(0L);
    public ObservableField<String>start_date_error = new ObservableField<>();
    public ObservableField<String>start_time_error = new ObservableField<>();
    public ObservableField<String>end_date_error = new ObservableField<>();
    public ObservableField<String>end_time_error = new ObservableField<>();
    private Context context;

    public ViewModelEventDateTime(@NonNull Application application) {
        super(application);
    }
    public void setContext(Context context)
    {
        this.context = context;
    }



    public boolean checkEventDateTimeData(AddEventViewModel addEventViewModel,int user_id) {
        if (start_date.get()!=0&&
                start_time.get()!=0&&
                end_date.get()!=0&&
                end_time.get()!=0
        )
        {
            start_date_error.set(null);
            start_time_error.set(null);
            end_date_error.set(null);
            end_time_error.set(null);

            addEventViewModel.setEventDateTimeData((start_date.get()/1000),(start_time.get()/1000),(end_date.get()/1000),(end_time.get()/1000));
            addEventViewModel.addEvent(user_id);
            return true;
        }else
        {
            if (start_date.get()==0)
            {
                start_date_error.set(context.getString(R.string.field_req));

            }else
            {
                start_date_error.set(null);

            }

            if (start_time.get()==0)
            {
                start_time_error.set(context.getString(R.string.field_req));

            }else
            {
                start_time_error.set(null);

            }

            if (end_date.get()==0)
            {
                end_date_error.set(context.getString(R.string.field_req));

            }else
            {
                end_date_error.set(null);

            }

            if (end_time.get()==0)
            {
                end_time_error.set(context.getString(R.string.field_req));

            }else
            {
                end_time_error.set(null);

            }
        }

        return false;
    }




}
