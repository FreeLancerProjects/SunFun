package com.creativeshare.sunfun.viewmodel.add_event_view_model;


import android.app.Application;
import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.AndroidViewModel;

import com.creativeshare.sunfun.R;

public class ViewModelEventDetails extends AndroidViewModel{

    public ObservableField<String> name_ar = new ObservableField<>("");
    public ObservableField<String> name_en = new ObservableField<>("");
    public ObservableField<String> desc_ar = new ObservableField<>("");
    public ObservableField<String> desc_en = new ObservableField<>("");
    public ObservableField<String> info_ar = new ObservableField<>("");
    public ObservableField<String> info_en = new ObservableField<>("");
    public ObservableField<String> image1_uri = new ObservableField<>("");
    public ObservableField<String> image2_uri = new ObservableField<>("");
    public ObservableField<String> event_type = new ObservableField<>("");
    public ObservableField<String> ticket_num = new ObservableField<>("");

    public ObservableField<Integer> event_category = new ObservableField<>(0);
    public ObservableField<String> price = new ObservableField<>();



    public ObservableField<String> name_ar_error = new ObservableField<>();
    public ObservableField<String> name_en_error = new ObservableField<>();
    public ObservableField<String> desc_ar_error = new ObservableField<>();
    public ObservableField<String> desc_en_error = new ObservableField<>();
    public ObservableField<String> info_ar_error = new ObservableField<>();
    public ObservableField<String> info_en_error = new ObservableField<>();
    public ObservableField<String> ticket_num_error = new ObservableField<>();
    public ObservableField<String> price_error = new ObservableField<>();

    private Context context;

    public ViewModelEventDetails(@NonNull Application application) {
        super(application);
    }
    public void setContext(Context context)
    {
        this.context = context;
    }



    public void checkEventDetailsData(AddEventViewModel addEventViewModel) {


        if (!TextUtils.isEmpty(name_ar.get())&&
                !TextUtils.isEmpty(name_en.get())&&
                !TextUtils.isEmpty(desc_ar.get())&&
                !TextUtils.isEmpty(desc_en.get())&&
                !TextUtils.isEmpty(info_ar.get())&&
                !TextUtils.isEmpty(info_en.get())&&

                !TextUtils.isEmpty(ticket_num.get())&&
                !TextUtils.isEmpty(price.get())&&
                event_category.get()!=0&&
                !TextUtils.isEmpty(image1_uri.get())&&
                !TextUtils.isEmpty(image2_uri.get())
        )
        {
            name_ar_error.set(null);
            name_en_error.set(null);
            desc_ar_error.set(null);
            desc_en_error.set(null);
            info_ar_error.set(null);
            info_en_error.set(null);
            addEventViewModel.setEventDetailsData(name_ar.get(),name_en.get(),desc_ar.get(),desc_en.get(),info_ar.get(),info_en.get(),image1_uri.get(),image2_uri.get(),event_type.get(),event_category.get(),ticket_num.get(),price.get());

        }else
            {
                if (TextUtils.isEmpty(name_ar.get()))
                {
                    name_ar_error.set(context.getString(R.string.field_req));

                }else
                    {
                        name_ar_error.set(null);

                    }

                if (TextUtils.isEmpty(name_en.get()))
                {
                    name_en_error.set(context.getString(R.string.field_req));

                }else
                {
                    name_en_error.set(null);

                }

                if (TextUtils.isEmpty(desc_ar.get()))
                {
                    desc_ar_error.set(context.getString(R.string.field_req));

                }else
                {
                    desc_ar_error.set(null);

                }

                if (TextUtils.isEmpty(desc_en.get()))
                {
                    desc_en_error.set(context.getString(R.string.field_req));

                }else
                {
                    desc_en_error.set(null);

                }

                if (TextUtils.isEmpty(info_ar.get()))
                {
                    info_ar_error.set(context.getString(R.string.field_req));

                }else
                {
                    info_ar_error.set(null);

                }
                if (TextUtils.isEmpty(ticket_num.get()))
                {
                    ticket_num_error.set(context.getString(R.string.field_req));

                }else
                {
                    ticket_num_error.set(null);

                }

                if (TextUtils.isEmpty(price.get()))
                {
                    price_error.set(context.getString(R.string.field_req));

                }else
                {
                    price_error.set(null);

                }

                if (TextUtils.isEmpty(info_en.get()))
                {
                    info_en_error.set(context.getString(R.string.field_req));

                }else
                {
                    info_en_error.set(null);

                }





                if (TextUtils.isEmpty(image1_uri.get())||TextUtils.isEmpty(image2_uri.get()))
                {
                    Toast.makeText(context, context.getString(R.string.ch_image), Toast.LENGTH_SHORT).show();
                }

                if (event_category.get()==0)
                {
                    Toast.makeText(context, R.string.ch_event_type_cat, Toast.LENGTH_SHORT).show();
                }


            }
    }




}
