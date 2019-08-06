package com.creativeshare.sunfun.viewmodel.edit_profile_view_model;


import android.app.Application;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.creativeshare.sunfun.R;
import com.creativeshare.sunfun.models.UserModel;

public class Edit_Profile_View_Model extends AndroidViewModel implements EditProfileListener {

    public ObservableField<String> name = new ObservableField<>("");
    public ObservableField<String> phone_code = new ObservableField<>("");
    public ObservableField<String> phone = new ObservableField<>("");
    public ObservableField<String> email = new ObservableField<>("");
    public ObservableField<String> responsible_name = new ObservableField<>("");

    public ObservableField<String> name_error = new ObservableField<>();
    public ObservableField<String> phone_code_error = new ObservableField<>();
    public ObservableField<String> phone_error = new ObservableField<>();
    public ObservableField<String> email_error = new ObservableField<>();
    public ObservableField<String> responsible_name_error = new ObservableField<>();

    public MutableLiveData<UserModel> userModelMutableLiveData;
    private Context context;
    private Repository repository;
    public Edit_Profile_View_Model(@NonNull Application application) {
        super(application);
        userModelMutableLiveData = new MutableLiveData<>();
        repository = new Repository();
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void editClientProfile(int user_id)
    {
        if (!TextUtils.isEmpty(name.get())&&
                !TextUtils.isEmpty(phone_code.get())&&
                !TextUtils.isEmpty(phone.get())&&
                !TextUtils.isEmpty(email.get())&& Patterns.EMAIL_ADDRESS.matcher(email.get()).matches()
        )
        {

            repository.editClientProfile(user_id,name.get(),phone_code.get(),phone.get(),email.get(),this,context);
        }else
            {

                if (TextUtils.isEmpty(name.get()))
                {
                    name_error.set(getApplication().getString(R.string.field_req));
                }else
                {
                    name_error.set(null);
                }

                if (TextUtils.isEmpty(phone_code.get()))
                {
                    phone_code_error.set(getApplication().getString(R.string.field_req));
                }else
                {
                    phone_code_error.set(null);
                }

                if (TextUtils.isEmpty(phone.get()))
                {
                    phone_error.set(getApplication().getString(R.string.field_req));
                }else
                {
                    phone_error.set(null);
                }

                if (TextUtils.isEmpty(email.get()))
                {
                    email_error.set(getApplication().getString(R.string.field_req));
                }else if (!Patterns.EMAIL_ADDRESS.matcher(email.get()).matches())
                    {
                        email_error.set(getApplication().getString(R.string.inv_email));
                    }else
                        {
                            email_error.set(null);
                        }





            }
    }

    public void editCompanyProfile(int user_id)
    {
        if (!TextUtils.isEmpty(name.get())&&
                !TextUtils.isEmpty(phone_code.get())&&
                !TextUtils.isEmpty(phone.get())&&
                !TextUtils.isEmpty(email.get())&& Patterns.EMAIL_ADDRESS.matcher(email.get()).matches()&&
                !TextUtils.isEmpty(responsible_name.get())
        )
        {

            repository.editCompanyProfile(user_id,name.get(),phone_code.get(),phone.get(),email.get(),responsible_name.get(),this,context);
        }else
        {

            if (TextUtils.isEmpty(name.get()))
            {
                name_error.set(getApplication().getString(R.string.field_req));
            }else
            {
                name_error.set(null);
            }

            if (TextUtils.isEmpty(phone_code.get()))
            {
                phone_code_error.set(getApplication().getString(R.string.field_req));
            }else
            {
                phone_code_error.set(null);
            }

            if (TextUtils.isEmpty(phone.get()))
            {
                phone_error.set(getApplication().getString(R.string.field_req));
            }else
            {
                phone_error.set(null);
            }

            if (TextUtils.isEmpty(email.get()))
            {
                email_error.set(getApplication().getString(R.string.field_req));
            }else if (!Patterns.EMAIL_ADDRESS.matcher(email.get()).matches())
            {
                email_error.set(getApplication().getString(R.string.inv_email));
            }else
            {
                email_error.set(null);
            }

            if (TextUtils.isEmpty(responsible_name.get()))
            {
                responsible_name_error.set(getApplication().getString(R.string.field_req));
            }else
            {
                responsible_name_error.set(null);
            }





        }
    }


    public void editImageProfile(int user_id,String image)
    {
        repository.editImageProfile(user_id,image,this,context);

    }

    public void editPassword(int user_id,String password)
    {
        repository.editPassword(user_id,password,this,context);

    }




    @Override
    public void onSuccess(UserModel userModel) {
        userModelMutableLiveData.setValue(userModel);
    }

    @Override
    public void onPasswordSuccess() {
        Toast.makeText(context, context.getString(R.string.suc), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFailed(int code) {
        if (code == 422)
        {
            Toast.makeText(getApplication(), R.string.em_exist, Toast.LENGTH_SHORT).show();

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
