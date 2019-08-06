package com.creativeshare.sunfun.activities_fragments.activity_company_action;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import com.creativeshare.sunfun.R;
import com.creativeshare.sunfun.databinding.ActivityCompanyActionBinding;
import com.creativeshare.sunfun.language.Language;
import com.creativeshare.sunfun.models.BookingScanData;
import com.creativeshare.sunfun.models.NotificationDataModel;
import com.creativeshare.sunfun.remote.Api;
import com.creativeshare.sunfun.share.Common;
import com.creativeshare.sunfun.tags.Tags;

import java.io.IOException;
import java.util.Locale;

import io.paperdb.Paper;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CompanyActionActivity extends AppCompatActivity {

    private ActivityCompanyActionBinding binding;
    private String lang;
    private NotificationDataModel.NotificationModel notificationModel;
    private BookingScanData bookingScanData;

    @Override
    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(Language.updateResources(newBase, Paper.book().read("lang", Locale.getDefault().getLanguage())));

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_company_action);
        getDataFromIntent();
        initView();
    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        if (intent!=null)
        {
            notificationModel = (NotificationDataModel.NotificationModel) intent.getSerializableExtra("data");

        }
    }

    private void initView() {
        Paper.init(this);
        lang = Paper.book().read("lang",Locale.getDefault().getLanguage());
        binding.progBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(this,R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
        binding.setLang(lang);
        binding.consBack.setOnClickListener(view -> finish());
        binding.btnAccept.setOnClickListener(view -> accept());
        binding.btnRefuse.setOnClickListener(view -> refuse());

        getBookingData();


    }

    private void accept() {
        ProgressDialog dialog = Common.createProgressDialog(this,getString(R.string.wait));
        dialog.show();
        Api.getService(Tags.base_url)
                .accept(bookingScanData.getBooking().getId(),bookingScanData.getBooking().getCompany_id(),notificationModel.getId(),bookingScanData.getBooking().getEvent_id())
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        dialog.dismiss();
                        if (response.isSuccessful())
                        {
                            Intent intent = getIntent();
                            intent.putExtra("action",1);
                            setResult(RESULT_OK);
                            finish();
                        }else
                            {
                                try {
                                    Log.e("error_code",response.code()+"_"+response.errorBody().string());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                Toast.makeText(CompanyActionActivity.this, getString(R.string.failed), Toast.LENGTH_SHORT).show();
                            }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        try {
                            dialog.dismiss();
                            Toast.makeText(CompanyActionActivity.this, getString(R.string.something), Toast.LENGTH_SHORT).show();
                            Log.e("Error",t.getMessage());

                        }catch (Exception e){}
                    }
                });

    }

    private void refuse() {

        ProgressDialog dialog = Common.createProgressDialog(this,getString(R.string.wait));
        dialog.show();
        Api.getService(Tags.base_url)
                .refuse(bookingScanData.getBooking().getId(),bookingScanData.getBooking().getCompany_id(),notificationModel.getId(),bookingScanData.getBooking().getEvent_id())
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        dialog.dismiss();
                        if (response.isSuccessful())
                        {
                            Intent intent = getIntent();
                            intent.putExtra("action",2);
                            setResult(RESULT_OK);
                            finish();
                        }else
                        {
                            try {
                                Log.e("error_code",response.code()+"_"+response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            Toast.makeText(CompanyActionActivity.this, getString(R.string.failed), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        try {
                            dialog.dismiss();
                            Toast.makeText(CompanyActionActivity.this, getString(R.string.something), Toast.LENGTH_SHORT).show();
                            Log.e("Error",t.getMessage());

                        }catch (Exception e){}
                    }
                });
    }



    private void getBookingData() {

        Api.getService(Tags.base_url)
                .myBookingDetails(notificationModel.getBooking_id())
                .enqueue(new Callback<BookingScanData>() {
                    @Override
                    public void onResponse(Call<BookingScanData> call, Response<BookingScanData> response) {
                        binding.progBar.setVisibility(View.GONE);
                        if (response.isSuccessful()&&response.body()!=null)
                        {
                            bookingScanData = response.body();
                            binding.consContainer.setVisibility(View.VISIBLE);
                            binding.setBookingModel(response.body());
                        }else
                            {
                                try {
                                    Log.e("error_code",response.code()+"_"+response.errorBody().string());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                Toast.makeText(CompanyActionActivity.this,getString(R.string.failed), Toast.LENGTH_SHORT).show();

                            }
                    }

                    @Override
                    public void onFailure(Call<BookingScanData> call, Throwable t) {

                        try {
                            binding.progBar.setVisibility(View.GONE);
                            Toast.makeText(CompanyActionActivity.this,getString(R.string.something), Toast.LENGTH_SHORT).show();
                            Log.e("error",t.getMessage());

                        }catch (Exception e)
                        {

                        }
                    }
                });
    }
}
