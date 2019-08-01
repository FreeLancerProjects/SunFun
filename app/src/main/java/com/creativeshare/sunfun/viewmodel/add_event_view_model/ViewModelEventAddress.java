package com.creativeshare.sunfun.viewmodel.add_event_view_model;


import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.AndroidViewModel;

import com.creativeshare.sunfun.R;

public class ViewModelEventAddress extends AndroidViewModel{

    public ObservableField<String> address = new ObservableField<>();
    public ObservableField<Double> lat = new ObservableField<>();
    public ObservableField<Double> lng = new ObservableField<>();
    public ObservableField<String> address_error = new ObservableField<>();

    private Context context;

    public ViewModelEventAddress(@NonNull Application application) {
        super(application);
    }
    public void setContext(Context context)
    {
        this.context = context;
    }


    public void checkEventAddressData(AddEventViewModel addEventViewModel) {

        if (!TextUtils.isEmpty(address.get())
        )
        {
            address_error.set(null);
            addEventViewModel.setEventAddressData(address.get(),lat.get(),lng.get());

        }else
        {
            if (TextUtils.isEmpty(address.get()))
            {
                address_error.set(context.getString(R.string.field_req));

            }else
            {
                address_error.set(null);

            }




        }
    }




}
