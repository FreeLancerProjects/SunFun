package com.creativeshare.sunfun.viewmodel.contact_us_view_model;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.AndroidViewModel;

import com.creativeshare.sunfun.R;
import com.creativeshare.sunfun.listeners.ContactListener;

public class ContactViewModel extends AndroidViewModel implements ContactListener {
    private Context context;
    public ObservableField<String> name = new ObservableField<>("");
    public ObservableField<String> email = new ObservableField<>("");
    public ObservableField<String> subject = new ObservableField<>("");

    public ObservableField<String> name_error = new ObservableField<>(null);
    public ObservableField<String> email_error = new ObservableField<>(null);
    public ObservableField<String> subject_error = new ObservableField<>(null);
    private Repository repository;

    public ContactViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository();
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void send() {
        if (!TextUtils.isEmpty(name.get()) &&
                !TextUtils.isEmpty(email.get()) &&
                Patterns.EMAIL_ADDRESS.matcher(email.get()).matches() &&
                !TextUtils.isEmpty(subject.get())) {
            repository.sendContact(this, context, name.get(), email.get(), subject.get());
        } else {
            if (TextUtils.isEmpty(name.get())) {
                name_error.set(context.getString(R.string.field_req));
            } else {
                name_error.set(null);
            }

            if (TextUtils.isEmpty(email.get())) {
                email_error.set(context.getString(R.string.field_req));
            } else if (!Patterns.EMAIL_ADDRESS.matcher(email.get()).matches()) {
                email_error.set(context.getString(R.string.inv_email));

            } else {
                email_error.set(null);
            }

            if (TextUtils.isEmpty(subject.get())) {
                subject_error.set(context.getString(R.string.field_req));
            } else {
                subject_error.set(null);
            }
        }
    }

    @Override
    public void onSuccess() {
        Toast.makeText(context, R.string.suc, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFailed(int code) {
        Log.e("error_code", code + "_");
        Toast.makeText(context, context.getString(R.string.failed), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onError(String error) {
        Log.e("error", error);
        Toast.makeText(context, context.getString(R.string.something), Toast.LENGTH_SHORT).show();

    }


}
