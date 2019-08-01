package com.creativeshare.sunfun.viewmodel.delete_event_view_model;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.creativeshare.sunfun.R;

public class DeleteEventViewModel extends AndroidViewModel implements DeleteEventListener {

    private Context context;
    public MutableLiveData<Boolean> success = new MutableLiveData<>();
    private Repository repository;

    public DeleteEventViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository();

    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void deleteEvents(int event_id)
    {
        repository.deleteEvent(this,context,event_id);
    }



    @Override
    public void onSuccess() {
        success.postValue(true);
    }

    @Override
    public void onFailed(int code) {
        Log.e("code",code+"_");
        if (code == 422)
        {
            Toast.makeText(getApplication(), R.string.cnt_dele_event, Toast.LENGTH_SHORT).show();

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
