package com.creativeshare.sunfun.viewmodel.upgrade_model_view;

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
import com.creativeshare.sunfun.models.UserModel;

public class UpgradeViewModel extends AndroidViewModel implements UpgradeListener {

    private Context context;
    public ObservableField<String> responsible_name = new ObservableField<>("");
    public ObservableField<String> address = new ObservableField<>("");
    public ObservableField<Double> lat = new ObservableField<>(0.0);
    public ObservableField<Double> lng = new ObservableField<>(0.0);
    public ObservableField<String> image_uri = new ObservableField<>("");

    public MutableLiveData<UserModel> data = new MutableLiveData<>();
    public ObservableField<String> responsible_name_error = new ObservableField<>();
    public ObservableField<String> address_error = new ObservableField<>();

    private Repository repository;

    public UpgradeViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository();
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void upgrade(int user_id)
    {
        if (!TextUtils.isEmpty(responsible_name.get())&&
                !TextUtils.isEmpty(address.get())&&
                !TextUtils.isEmpty(image_uri.get())
        )
        {
            repository.upgrade(this,context,user_id,image_uri.get(),responsible_name.get(),address.get(),lat.get(),lng.get());
        }else
            {
                if (TextUtils.isEmpty(responsible_name.get()))
                {
                    responsible_name_error.set(context.getString(R.string.field_req));
                }else
                    {
                        responsible_name_error.set(null);
                    }

                if (TextUtils.isEmpty(address.get()))
                {
                    address_error.set(context.getString(R.string.field_req));
                }else
                {
                    address_error.set(null);
                }

                if (TextUtils.isEmpty(image_uri.get()))
                {
                    Toast.makeText(context, R.string.ch_image, Toast.LENGTH_SHORT).show();
                }

            }
    }


    @Override
    public void onSuccess(UserModel userModel) {
        data.postValue(userModel);
    }

    @Override
    public void onFailed(int code) {
        Log.e("error_code",code+"_");
        if (code == 422)
        {
            Toast.makeText(context, R.string.already_request_send, Toast.LENGTH_SHORT).show();

        }else
            {
                Toast.makeText(context, context.getString(R.string.failed), Toast.LENGTH_SHORT).show();

            }
    }

    @Override
    public void onError(String error) {
        Toast.makeText(context, context.getString(R.string.something), Toast.LENGTH_SHORT).show();

    }
}
