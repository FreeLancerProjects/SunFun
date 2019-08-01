package com.creativeshare.sunfun.viewmodel.add_event_view_model;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.creativeshare.sunfun.R;
import com.creativeshare.sunfun.models.EventIdModel;
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

    public void addEvent(AddEventListener listener, Context context,int id,String name_ar,String name_en,String desc_ar,String desc_en,String info_ar,String info_en,String ticket_num,String price,String image1,String image2,String event_type,int event_category,String address,double lat,double lng,long s_date,long s_time,long e_date,long e_time)
    {
        ProgressDialog dialog = Common.createProgressDialog(context,context.getString(R.string.wait));
        dialog.setCancelable(false);
        dialog.show();

        RequestBody id_part = Common.getRequestBodyText(String.valueOf(id));
        RequestBody name_ar_part = Common.getRequestBodyText(name_ar);
        RequestBody name_en_part = Common.getRequestBodyText(name_en);
        RequestBody desc_ar_part = Common.getRequestBodyText(desc_ar);
        RequestBody desc_en_part = Common.getRequestBodyText(desc_en);
        RequestBody info_ar_part = Common.getRequestBodyText(info_ar);
        RequestBody info_en_part = Common.getRequestBodyText(info_en);
        RequestBody ticket_num_part = Common.getRequestBodyText(ticket_num);
        RequestBody price_part = Common.getRequestBodyText(price);
        RequestBody cat_id_part = Common.getRequestBodyText(event_type);
        RequestBody sub_cat_id_part = Common.getRequestBodyText(String.valueOf(event_category));

        RequestBody address_part = Common.getRequestBodyText(address);
        RequestBody lat_part = Common.getRequestBodyText(String.valueOf(lat));
        RequestBody lng_part = Common.getRequestBodyText(String.valueOf(lng));
        RequestBody s_date_part = Common.getRequestBodyText(String.valueOf(s_date));
        RequestBody s_time_part = Common.getRequestBodyText(String.valueOf(s_time));
        RequestBody e_date_part = Common.getRequestBodyText(String.valueOf(e_date));
        RequestBody e_time_part = Common.getRequestBodyText(String.valueOf(e_time));

        MultipartBody.Part image1_part = Common.getMultiPart(context, Uri.parse(image1),"image1");
        MultipartBody.Part image2_part = Common.getMultiPart(context, Uri.parse(image2),"image2");

        Api.getService(Tags.base_url)
                .addEvent(id_part,name_ar_part,name_en_part,desc_ar_part,desc_en_part,s_date_part,e_date_part,s_time_part,e_time_part,address_part,lat_part,lng_part,price_part,ticket_num_part,info_ar_part,info_en_part,cat_id_part,sub_cat_id_part,image1_part,image2_part)
                .enqueue(new Callback<EventIdModel>() {
                    @Override
                    public void onResponse(Call<EventIdModel> call, Response<EventIdModel> response) {
                        dialog.dismiss();
                        if (response.isSuccessful()&&response.body()!=null)
                        {

                            listener.onSuccess(response.body());
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
                    public void onFailure(Call<EventIdModel> call, Throwable t) {
                        try {
                            dialog.dismiss();
                            listener.onError(t.getMessage());

                        }catch (Exception e){}
                    }
                });
    }

}
