package com.creativeshare.sunfun.viewmodel.signup_view_model;


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
import com.creativeshare.sunfun.listeners.Listener;

public class SignUp_View_Model extends AndroidViewModel implements Listener {

    public ObservableField<String> name = new ObservableField<>("");
    public ObservableField<String> phone_code = new ObservableField<>("");
    public ObservableField<String> phone = new ObservableField<>("");

    public ObservableField<String> email = new ObservableField<>("");
    public ObservableField<String> password = new ObservableField<>("");
    public ObservableField<String> name_error = new ObservableField<>(null);
    public ObservableField<String> phone_code_error = new ObservableField<>(null);
    public ObservableField<String> phone_error = new ObservableField<>(null);
    public ObservableField<String> email_error = new ObservableField<>(null);
    public ObservableField<String> password_error = new ObservableField<>(null);
    public MutableLiveData<UserModel> userModelMutableLiveData;
    private Context context;
    private Repository repository;

    public SignUp_View_Model(@NonNull Application application) {
        super(application);
        userModelMutableLiveData = new MutableLiveData<>();
        repository = new Repository();
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void signUp()
    {
        if (!TextUtils.isEmpty(name.get())&&
                !TextUtils.isEmpty(phone_code.get())&&
                !TextUtils.isEmpty(phone.get())&&
                !TextUtils.isEmpty(email.get())&& Patterns.EMAIL_ADDRESS.matcher(email.get()).matches()&&
                !TextUtils.isEmpty(password.get())
        )
        {
            if (phone.get().startsWith("0"))
            {
                phone.set(phone.get().replaceFirst("0",""));
            }
            repository.signUp(name.get(),phone_code.get().replace("+","00"),phone.get(),email.get(),password.get(),this,context);
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


                if (TextUtils.isEmpty(password.get()))
                {
                    password_error.set(getApplication().getString(R.string.field_req));
                }else
                    {
                        password_error.set(null);
                    }


            }
    }

    /*@BindingAdapter("app:error")
    public static void setErrorUi(View view, String error)
    {
        if (view instanceof EditText)
        {
            EditText editText = (EditText) view;
            editText.setError(error);

        }else if (view instanceof TextView)
        {
            TextView textView = (TextView) view;
            textView.setError(error);

        }
    }*/


    @Override
    public void onSuccess(UserModel userModel) {
        userModelMutableLiveData.setValue(userModel);
    }

    @Override
    public void onFailed(int code) {
        Log.e("code_sign_up",code+"_");
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
