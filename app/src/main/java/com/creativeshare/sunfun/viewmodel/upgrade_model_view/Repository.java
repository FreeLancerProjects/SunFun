package com.creativeshare.sunfun.viewmodel.upgrade_model_view;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.creativeshare.sunfun.R;
import com.creativeshare.sunfun.listeners.UpgradeListener;
import com.creativeshare.sunfun.models.UserModel;
import com.creativeshare.sunfun.remote.Api;
import com.creativeshare.sunfun.share.Common;
import com.creativeshare.sunfun.tags.Tags;

import java.io.IOException;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repository {

    public void upgrade(UpgradeListener listener, Context context, int user_id,String image_uri, String responsible_name, String address, double lat,double lng)
    {
        Log.e("id",user_id+"_");
        Log.e("image",image_uri+"_");
        Log.e("name",responsible_name+"_");
        Log.e("add",address+"_");
        Log.e("lat",lat+"_");
        Log.e("lng",lng+"_");

        ProgressDialog dialog = Common.createProgressDialog(context,context.getString(R.string.wait));
        dialog.setCancelable(false);
        dialog.show();

        RequestBody id_part = Common.getRequestBodyText(String.valueOf(user_id));
        RequestBody responsible_name__part = Common.getRequestBodyText(responsible_name);
        RequestBody address_part = Common.getRequestBodyText(address);
        RequestBody lat_part = Common.getRequestBodyText(String.valueOf(lat));
        RequestBody lng_part = Common.getRequestBodyText(String.valueOf(lng));
        MultipartBody.Part image_part = Common.getMultiPart(context, Uri.parse(image_uri),"national_image");

        Api.getService(Tags.base_url)
                .upgradeToCompany(id_part,responsible_name__part,lat_part,lng_part,address_part,image_part)
                .enqueue(new Callback<UserModel>() {
                    @Override
                    public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                        dialog.dismiss();
                        if (response.isSuccessful()&&response.body()!=null)
                        {
                            listener.onSuccess(response.body());
                        }else
                            {
                                listener.onFailed(response.code());
                                try {
                                    Log.e("error",response.errorBody().string());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                    }

                    @Override
                    public void onFailure(Call<UserModel> call, Throwable t) {
                        try {
                            dialog.dismiss();
                            listener.onError(t.getMessage());

                        }catch (Exception e){}
                    }
                });
    }

}
