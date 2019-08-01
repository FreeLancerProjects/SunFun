package com.creativeshare.sunfun.viewmodel.category_view_model;

import android.app.ProgressDialog;
import android.content.Context;

import com.creativeshare.sunfun.R;
import com.creativeshare.sunfun.models.CategoryDataModel;
import com.creativeshare.sunfun.remote.Api;
import com.creativeshare.sunfun.share.Common;
import com.creativeshare.sunfun.tags.Tags;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repository {

    public void getCategories(CategoryListener listener, Context context)
    {
        ProgressDialog dialog = Common.createProgressDialog(context,context.getString(R.string.wait));
        dialog.setCancelable(false);
        dialog.show();

        Api.getService(Tags.base_url)
                .getCategories()
                .enqueue(new Callback<CategoryDataModel>() {
                    @Override
                    public void onResponse(Call<CategoryDataModel> call, Response<CategoryDataModel> response) {
                        dialog.dismiss();
                        if (response.isSuccessful()&&response.body()!=null&&response.body().getData()!=null)
                        {
                            listener.onSuccess(response.body().getData());
                        }else
                            {
                                listener.onFailed(response.code());
                            }
                    }

                    @Override
                    public void onFailure(Call<CategoryDataModel> call, Throwable t) {
                        try {
                            dialog.dismiss();
                            listener.onError(t.getMessage());

                        }catch (Exception e){}
                    }
                });
    }
}
