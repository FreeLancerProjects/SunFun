package com.creativeshare.sunfun.viewmodel.add_activity_view_model;


import android.app.Application;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.creativeshare.sunfun.R;

public class AddActivityViewModel extends AndroidViewModel implements AddActivityListener {

    public ObservableField<String> name_ar = new ObservableField<>("");
    public ObservableField<String> name_en = new ObservableField<>("");
    public ObservableField<String> place_ar = new ObservableField<>("");
    public ObservableField<String> place_en = new ObservableField<>("");
    public ObservableField<String> image_uri = new ObservableField<>("");
    public ObservableField<String> price = new ObservableField<>("");
    public ObservableField<String> ticket_num = new ObservableField<>("");
    public ObservableField<String> unit = new ObservableField<>("");
    public ObservableField<Long> start_date = new ObservableField<>(0L);
    public ObservableField<Long> end_date = new ObservableField<>(0L);


    public ObservableField<String> name_ar_error = new ObservableField<>();
    public ObservableField<String> name_en_error = new ObservableField<>();
    public ObservableField<String> place_ar_error = new ObservableField<>();
    public ObservableField<String> place_en_error = new ObservableField<>();
    public ObservableField<String> unit_error = new ObservableField<>();
    public ObservableField<String> start_date_error = new ObservableField<>();
    public ObservableField<String> end_date_error = new ObservableField<>();
    public ObservableField<String> ticket_num_error = new ObservableField<>();
    public ObservableField<String> price_error = new ObservableField<>();
    public MutableLiveData<Boolean> success = new MutableLiveData<>();
    private Context context;
    private Repository repository;

    public AddActivityViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository();
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void addActivity(int event_id) {


        if (!TextUtils.isEmpty(name_ar.get()) &&
                !TextUtils.isEmpty(name_en.get()) &&
                !TextUtils.isEmpty(place_ar.get()) &&
                !TextUtils.isEmpty(place_en.get()) &&
                !TextUtils.isEmpty(unit.get()) &&
                start_date.get() != 0 &&
                end_date.get() != 0 &&
                !TextUtils.isEmpty(ticket_num.get()) &&
                !TextUtils.isEmpty(price.get()) &&
                !TextUtils.isEmpty(image_uri.get())) {
                name_ar_error.set(null);
                name_en_error.set(null);
                place_ar_error.set(null);
                place_en_error.set(null);
                unit_error.set(null);
                start_date_error.set(null);
                end_date_error.set(null);

                repository.addActivity(this, context, event_id, name_ar.get(), name_en.get(), place_ar.get(), place_en.get(), ticket_num.get(), price.get(), unit.get(), image_uri.get(), (start_date.get() / 1000), (end_date.get() / 1000));

        } else {
            if (TextUtils.isEmpty(name_ar.get())) {
                name_ar_error.set(context.getString(R.string.field_req));

            } else {
                name_ar_error.set(null);

            }

            if (TextUtils.isEmpty(name_en.get())) {
                name_en_error.set(context.getString(R.string.field_req));

            } else {
                name_en_error.set(null);

            }

            if (TextUtils.isEmpty(place_ar.get())) {
                place_ar_error.set(context.getString(R.string.field_req));

            } else {
                place_ar_error.set(null);

            }

            if (TextUtils.isEmpty(place_en.get())) {
                place_en_error.set(context.getString(R.string.field_req));

            } else {
                place_en_error.set(null);

            }

            if (TextUtils.isEmpty(unit.get())) {
                unit_error.set(context.getString(R.string.field_req));

            } else {
                unit_error.set(null);

            }
            if (TextUtils.isEmpty(ticket_num.get())) {
                ticket_num_error.set(context.getString(R.string.field_req));

            } else {
                ticket_num_error.set(null);

            }

            if (TextUtils.isEmpty(price.get())) {
                price_error.set(context.getString(R.string.field_req));

            } else {
                price_error.set(null);

            }

            if (start_date.get() == 0) {
                start_date_error.set(context.getString(R.string.field_req));

            } else {
                start_date_error.set(null);

            }

            if (end_date.get() == 0) {
                end_date_error.set(context.getString(R.string.field_req));

            } else {
                end_date_error.set(null);

            }


            if (TextUtils.isEmpty(image_uri.get())) {
                Toast.makeText(context, context.getString(R.string.ch_image), Toast.LENGTH_SHORT).show();
            }


        }
    }


    @Override
    public void onSuccess() {

        success.postValue(true);
        name_ar.set("");
        name_en.set("");
        place_ar.set("");
        place_en.set("");
        image_uri.set("");
        price.set("");
        ticket_num.set("");
        start_date.set(0L);
        end_date.set(0L);
    }

    @Override
    public void onFailed(int code) {
        Log.e("code",code+"_");
        if (code == 404)
        {
            Toast.makeText(getApplication(), R.string.inv_em_ps, Toast.LENGTH_SHORT).show();
        }else
        {
            Toast.makeText(getApplication(), R.string.failed, Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public void onError(String error) {
        Log.e("Error",error);
        Toast.makeText(getApplication(), R.string.something, Toast.LENGTH_SHORT).show();

    }
}
