package com.creativeshare.sunfun.viewmodel.category_view_model;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.creativeshare.sunfun.R;
import com.creativeshare.sunfun.models.CategoryDataModel;
import com.creativeshare.sunfun.listeners.CategoryListener;

import java.util.List;

public class CategoryViewModel extends AndroidViewModel implements CategoryListener {

    private Context context;
    public MutableLiveData<List<CategoryDataModel.Category>> data;
    private Repository repository;
    public CategoryViewModel(@NonNull Application application) {
        super(application);
        data = new MutableLiveData<>();
        repository = new Repository();
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void getCategories()
    {
        repository.getCategories(this,context);
    }


    @Override
    public void onSuccess(List<CategoryDataModel.Category> categories) {
        CategoryDataModel.Category category = new CategoryDataModel.Category();
        category.setAr_title("نوع الحدث");
        category.setEn_title("Event Type");
        categories.add(0,category);
        data.setValue(categories);
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
