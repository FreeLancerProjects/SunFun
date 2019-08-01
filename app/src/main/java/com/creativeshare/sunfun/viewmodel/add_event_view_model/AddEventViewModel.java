package com.creativeshare.sunfun.viewmodel.add_event_view_model;


import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.creativeshare.sunfun.R;
import com.creativeshare.sunfun.models.EventIdModel;

public class AddEventViewModel extends AndroidViewModel implements AddEventListener {

    public ObservableField<String> name_ar = new ObservableField<>("");
    public ObservableField<String> name_en = new ObservableField<>("");
    public ObservableField<String> desc_ar = new ObservableField<>("");
    public ObservableField<String> desc_en = new ObservableField<>("");
    public ObservableField<String> info_ar = new ObservableField<>("");
    public ObservableField<String> info_en = new ObservableField<>("");
    public ObservableField<String> image1_uri = new ObservableField<>("");
    public ObservableField<String> image2_uri = new ObservableField<>("");
    public ObservableField<String> ticket_num = new ObservableField<>("");
    public ObservableField<String> price = new ObservableField<>("");

    public ObservableField<String> event_type = new ObservableField<>("");
    public ObservableField<Integer> event_category = new ObservableField<>(0);
    public ObservableField<String> address = new ObservableField<>();
    public ObservableField<Double> lat = new ObservableField<>();
    public ObservableField<Double> lng = new ObservableField<>();
    public ObservableField<Long> start_date = new ObservableField<>();
    public ObservableField<Long> start_time = new ObservableField<>();
    public ObservableField<Long> end_date = new ObservableField<>();
    public ObservableField<Long> end_time = new ObservableField<>();


    public ObservableField<Integer> current_pos = new ObservableField<>(0);
    public int pos = 0;
    public MutableLiveData<Boolean> step1 = new MutableLiveData<>();
    public MutableLiveData<Boolean> step2 = new MutableLiveData<>();
    public MutableLiveData<Boolean> step3 = new MutableLiveData<>();
    public MutableLiveData<EventIdModel> success = new MutableLiveData<>();



    private Context context;
    private Repository repository;

    public AddEventViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository();
    }
    public void setContext(Context context)
    {
        this.context = context;
    }

    public void addEvent(int user_id)
    {
        repository.addEvent(this,context,user_id,name_ar.get(),name_en.get(),desc_ar.get(),desc_en.get(),info_ar.get(),info_en.get(),ticket_num.get(),price.get(),image1_uri.get(),image2_uri.get(),event_type.get(),event_category.get(),address.get(),lat.get(),lng.get(),start_date.get(),start_time.get(),end_date.get(),end_time.get());
    }



    public void setEventDetailsData(String name_ar,String name_en,String desc_ar,String desc_en,String info_ar,String info_en,String image_uri1,String image_uri2,String event_type,int event_category,String ticket_num,String price)
    {
        this.name_ar.set(name_ar);
        this.name_en.set(name_en);
        this.desc_ar.set(desc_ar);
        this.desc_en.set(desc_en);
        this.info_ar.set(info_ar);
        this.info_en.set(info_en);
        this.image1_uri.set(image_uri1);
        this.image2_uri.set(image_uri2);
        this.event_type.set(event_type);
        this.event_category.set(event_category);
        this.ticket_num.set(ticket_num);
        this.price.set(price);
        step1.postValue(true);
    }

    public void setEventAddressData(String address,double lat,double lng)
    {
        this.address.set(address);
        this.lat.set(lat);
        this.lng.set(lng);
        step2.postValue(true);
    }

    public void setEventDateTimeData(long s_date,long s_time,long e_date,long e_time)
    {
        this.start_date.set(s_date);
        this.start_time.set(s_time);
        this.end_date.set(e_date);
        this.end_time.set(e_time);
        step3.postValue(true);
    }


    @Override
    public void onSuccess(EventIdModel eventIdModel) {
        success.postValue(eventIdModel);
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
