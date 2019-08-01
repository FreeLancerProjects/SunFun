package com.creativeshare.sunfun.viewmodel.login_view_model;


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

public class Login_View_Model extends AndroidViewModel implements Listener {

    public ObservableField<String> email = new ObservableField<>("");
    public ObservableField<String> password = new ObservableField<>("");
    public ObservableField<String> email_error = new ObservableField<>();
    public ObservableField<String> password_error = new ObservableField<>();
    private Context context;
    public MutableLiveData<UserModel> userModelMutableLiveData;

    public Login_View_Model(@NonNull Application application) {
        super(application);
        userModelMutableLiveData = new MutableLiveData<>();
    }
    public void setContext(Context context)
    {
        this.context = context;
    }



    public void Login()
    {
        if (!TextUtils.isEmpty(email.get())&& Patterns.EMAIL_ADDRESS.matcher(email.get()).matches()&&
                !TextUtils.isEmpty(password.get())
        )
        {
            Repository repository = new Repository();
            repository.logIn(email.get(),password.get(),this,context);
        }else
            {
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

   /* @BindingAdapter("app:error")
    public static void setErrorUi(EditText editText, String error)
    {
        editText.setError(error);
    }
*/

    @Override
    public void onSuccess(UserModel userModel) {
        userModelMutableLiveData.setValue(userModel);
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
