package com.creativeshare.sunfun.activities_fragments.activity_qr_code;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import com.creativeshare.sunfun.R;
import com.creativeshare.sunfun.activities_fragments.scan_booking_details.ScanBookingDetailsActivity;
import com.creativeshare.sunfun.databinding.ActivityQrcodeBinding;
import com.creativeshare.sunfun.databinding.DialogCustom2Binding;
import com.creativeshare.sunfun.language.Language;
import com.creativeshare.sunfun.models.BookingScanData;
import com.creativeshare.sunfun.remote.Api;
import com.creativeshare.sunfun.share.Common;
import com.creativeshare.sunfun.tags.Tags;
import com.google.zxing.Result;

import java.io.IOException;
import java.util.Locale;

import io.paperdb.Paper;
import me.dm7.barcodescanner.zxing.ZXingScannerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QRCodeActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    private final int CAMERA_REQ = 1022;
    private final String camera_perm = Manifest.permission.CAMERA;
    private ActivityQrcodeBinding binding;

    @Override
    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(Language.updateResources(newBase, Paper.book().read("lang", Locale.getDefault().getLanguage())));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_qrcode);
        binding.scannerView.setFormats(ZXingScannerView.ALL_FORMATS);
        binding.scannerView.setResultHandler(this);
        binding.scannerView.setAutoFocus(false);
        checkCameraPermission();
    }
    private void checkCameraPermission()
    {
        if (ContextCompat.checkSelfPermission(this, camera_perm) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{camera_perm}, CAMERA_REQ);

        } else {
            binding.scannerView.startCamera();


        }
    }
    @Override
    public void handleResult(Result result) {
        scanCode(result.getText());
    }

    private void scanCode(String code)
    {
        ProgressDialog dialog = Common.createProgressDialog(this, getString(R.string.wait));
        dialog.show();
        Api.getService(Tags.base_url)
                .scanCode(code)
                .enqueue(new Callback<BookingScanData>() {
                    @Override
                    public void onResponse(Call<BookingScanData> call, Response<BookingScanData> response) {
                        dialog.dismiss();
                        if (response.isSuccessful()&&response.body()!=null) {
                            Intent intent = new Intent(QRCodeActivity.this, ScanBookingDetailsActivity.class);
                            intent.putExtra("data",response.body());
                            startActivity(intent);
                            finish();
                            Log.e("data",response.body().getBooking().getSubscribers_num()+"_");
                            Log.e("qr_code",code);
                        } else {
                            if (response.code() == 201) {
                                CreateAlertDialog();
                            } else {
                                Toast.makeText(QRCodeActivity.this, getString(R.string.failed), Toast.LENGTH_SHORT).show();
                            }
                            try {
                                Log.e("error_code", response.code() + "_" + response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<BookingScanData> call, Throwable t) {
                        try {
                            dialog.dismiss();
                            Log.e("error", t.getMessage());
                        } catch (Exception e) {
                        }
                    }
                });

    }
    private void CreateAlertDialog()
    {
        final AlertDialog dialog = new AlertDialog.Builder(this)
                .create();

        DialogCustom2Binding binding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.dialog_custom2, null, false);
        binding.tvMsg.setText(R.string.qr_not_available);
        binding.btnCancel.setOnClickListener((v) ->
                {
                    dialog.dismiss();
                    finish();
                }


        );
        dialog.getWindow().getAttributes().windowAnimations = R.style.dialog_congratulation_animation;
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_window_bg);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setView(binding.getRoot());
        dialog.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_REQ) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (binding.scannerView != null) {
                    binding.scannerView.startCamera();
                }
            } else {
                Toast.makeText(this, R.string.cam_pem_denied, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        checkCameraPermission();
    }
    @Override
    protected void onStop()
    {
        super.onStop();
        if (binding.scannerView != null) {
            binding.scannerView.stopCamera();
        }
    }


}
