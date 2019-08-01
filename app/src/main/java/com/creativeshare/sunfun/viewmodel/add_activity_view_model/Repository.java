package com.creativeshare.sunfun.viewmodel.add_activity_view_model;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.creativeshare.sunfun.R;
import com.creativeshare.sunfun.remote.Api;
import com.creativeshare.sunfun.share.Common;
import com.creativeshare.sunfun.tags.Tags;

import java.io.IOException;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repository {

    public void addActivity(AddActivityListener listener, Context context,int id,String name_ar,String name_en,String place_ar,String place_en,String ticket_num,String price,String unit,String image,long s_date,long e_date)
    {
        ProgressDialog dialog = Common.createProgressDialog(context,context.getString(R.string.wait));
        dialog.setCancelable(false);
        dialog.show();

        RequestBody id_part = Common.getRequestBodyText(String.valueOf(id));
        RequestBody name_ar_part = Common.getRequestBodyText(name_ar);
        RequestBody name_en_part = Common.getRequestBodyText(name_en);
        RequestBody place_ar_part = Common.getRequestBodyText(place_ar);
        RequestBody place_en_part = Common.getRequestBodyText(place_en);
        RequestBody unit_part = Common.getRequestBodyText(unit);
        RequestBody ticket_num_part = Common.getRequestBodyText(ticket_num);
        RequestBody price_part = Common.getRequestBodyText(price);

        RequestBody s_date_part = Common.getRequestBodyText(String.valueOf(s_date));
        RequestBody e_date_part = Common.getRequestBodyText(String.valueOf(e_date));

        MultipartBody.Part image_part = Common.getMultiPart(context, Uri.parse(image),"image");

        Api.getService(Tags.base_url)
                .addActivity(id_part,s_date_part,e_date_part,unit_part,price_part,ticket_num_part,name_ar_part,name_en_part,place_ar_part,place_en_part,image_part)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        dialog.dismiss();
                        if (response.isSuccessful()&&response.body()!=null)
                        {
                            listener.onSuccess();
                        }else
                            {
                                try {
                                    Log.e("error_code",response.errorBody().string());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                listener.onFailed(response.code());
                            }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        try {
                            dialog.dismiss();
                            listener.onError(t.getMessage());

                        }catch (Exception e){}
                    }
                });
    }

}
