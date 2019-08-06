package com.creativeshare.sunfun.activities_fragments.add_activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.creativeshare.sunfun.R;
import com.creativeshare.sunfun.databinding.ActivityAddBinding;
import com.creativeshare.sunfun.databinding.DialogSelectImageBinding;
import com.creativeshare.sunfun.language.Language;
import com.creativeshare.sunfun.models.EventIdModel;
import com.creativeshare.sunfun.share.Common;
import com.creativeshare.sunfun.singleton.Singleton;
import com.creativeshare.sunfun.viewmodel.add_activity_view_model.AddActivityViewModel;
import com.squareup.picasso.Picasso;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import io.paperdb.Paper;

public class ActivityAddActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    private String lang;
    private ActivityAddBinding binding;
    private EventIdModel eventIdModel;
    private DatePickerDialog datePickerDialog;
    private int selected_type = 0;
    private final String READ_PERM = Manifest.permission.READ_EXTERNAL_STORAGE;
    private final String write_permission = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    private final String camera_permission = Manifest.permission.CAMERA;
    private final int IMG_REQ1 = 1, IMG_REQ2 = 2;
    private Uri imgUri1 = null;
    private AddActivityViewModel addActivityViewModel;
    private Singleton singleton;

    @Override
    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(Language.updateResources(newBase,Paper.book().read("lang",Locale.getDefault().getLanguage())));

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_add);
        addActivityViewModel = ViewModelProviders.of(this).get(AddActivityViewModel.class);
        getDataFromIntent();
        initView();
    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        if (intent!=null)
        {
            eventIdModel = (EventIdModel) intent.getSerializableExtra("data");
        }
    }

    private void initView() {
        singleton = Singleton.newInstance();
        Paper.init(this);
        lang = Paper.book().read("lang", Locale.getDefault().getLanguage());
        binding.setLang(lang);
        binding.setEventIdModel(eventIdModel);
        binding.setViewModel(addActivityViewModel);
        addActivityViewModel.setContext(this);
        addActivityViewModel.success.observe(this,aBoolean ->
        {
            binding.image.setImageBitmap(null);
            binding.icon.setVisibility(View.VISIBLE);
            binding.tvEndDate.setText("");
            binding.tvStartDate.setText("");
            binding.edtTitleAr.setText("");
            binding.edtTitleEn.setText("");
            binding.edtAddressAr.setText("");
            binding.edtAddressEn.setText("");
            binding.edtPrice.setText("");
            binding.edtTicket.setText("");
            binding.edtUnit.setText("");
            imgUri1 = null;
            singleton.setEventAdded(true);



        });

        createDatePickerDialog();
        binding.llStartdate.setOnClickListener(view -> {
            selected_type=1;
            datePickerDialog.show(getFragmentManager(),"");
        });

        binding.llEndDate.setOnClickListener(view -> {
            selected_type=2;
            datePickerDialog.show(getFragmentManager(),"");
        });
        binding.llBack.setOnClickListener(view -> finish());
        binding.image.setOnClickListener(view -> CreateImageAlertDialog());
    }

    private void createDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        datePickerDialog = DatePickerDialog.newInstance(this,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.dismissOnPause(true);
        datePickerDialog.setAccentColor(ActivityCompat.getColor(this,R.color.colorPrimary));
        datePickerDialog.setCancelColor(ActivityCompat.getColor(this,R.color.gray4));
        datePickerDialog.setOkColor(ActivityCompat.getColor(this,R.color.colorPrimary));
        datePickerDialog.setOkText(getString(R.string.select));
        datePickerDialog.setCancelText(getString(R.string.cancel));
        datePickerDialog.setLocale(new Locale(lang));
        datePickerDialog.setVersion(DatePickerDialog.Version.VERSION_2);
        datePickerDialog.setMinDate(calendar);


    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR,year);
        calendar.set(Calendar.MONTH,monthOfYear);
        calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy",Locale.ENGLISH);
        String t = dateFormat.format(new Date(calendar.getTimeInMillis()));

        if (selected_type ==1)
        {
            binding.tvStartDate.setText(t);
            addActivityViewModel.start_date.set(calendar.getTimeInMillis());

        }else
            {
                binding.tvEndDate.setText(t);
                addActivityViewModel.end_date.set(calendar.getTimeInMillis());

            }





    }


    private void CreateImageAlertDialog()
    {

        final AlertDialog dialog = new AlertDialog.Builder(this)
                .setCancelable(true)
                .create();

        DialogSelectImageBinding binding = DataBindingUtil.inflate(LayoutInflater.from(this),R.layout.dialog_select_image,null,false);



        binding.btnCamera.setOnClickListener(v -> {
            dialog.dismiss();
            Check_CameraPermission();

        });

        binding.btnGallery.setOnClickListener(v -> {
            dialog.dismiss();
            CheckReadPermission();



        });

        binding.btnCancel.setOnClickListener(v -> dialog.dismiss());

        dialog.getWindow().getAttributes().windowAnimations= R.style.dialog_congratulation_animation;
        dialog.setCanceledOnTouchOutside(false);
        dialog.setView(binding.getRoot());
        dialog.show();
    }
    private void CheckReadPermission()
    {
        if (ActivityCompat.checkSelfPermission(this, READ_PERM) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{READ_PERM}, IMG_REQ1);
        } else {
            SelectImage(IMG_REQ1);
        }
    }

    private void Check_CameraPermission()
    {
        if (ContextCompat.checkSelfPermission(this,camera_permission)!= PackageManager.PERMISSION_GRANTED&&ContextCompat.checkSelfPermission(this,write_permission)!= PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,new String[]{camera_permission,write_permission},IMG_REQ2);
        }else
        {
            SelectImage(IMG_REQ2);

        }

    }
    private void SelectImage(int img_req) {

        Intent intent = new Intent();

        if (img_req == IMG_REQ1)
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
            {
                intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
                intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
            }else
            {
                intent.setAction(Intent.ACTION_GET_CONTENT);

            }

            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setType("image/*");
            startActivityForResult(intent,img_req);

        }else if (img_req ==IMG_REQ2)
        {
            try {
                intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,img_req);
            }catch (SecurityException e)
            {
                Toast.makeText(this,R.string.perm_image_denied, Toast.LENGTH_SHORT).show();
            }
            catch (Exception e)
            {
                Toast.makeText(this,R.string.perm_image_denied, Toast.LENGTH_SHORT).show();

            }


        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == IMG_REQ1) {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                SelectImage(IMG_REQ1);
            } else {
                Toast.makeText(this, getString(R.string.perm_image_denied), Toast.LENGTH_SHORT).show();
            }

        } else if (requestCode == IMG_REQ2) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                SelectImage(IMG_REQ2);
            } else {
                Toast.makeText(this, getString(R.string.perm_image_denied), Toast.LENGTH_SHORT).show();
            }
        }


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMG_REQ2 && resultCode == Activity.RESULT_OK && data != null) {
            binding.icon.setVisibility(View.GONE);

            Bitmap bitmap = (Bitmap) data.getExtras().get("data");

            imgUri1 = getUriFromBitmap(bitmap);

            addActivityViewModel.image_uri.set(imgUri1.toString());
            if (imgUri1 != null) {
                String path = Common.getImagePath(this, imgUri1);

                if (path != null) {
                    Picasso.with(this).load(new File(path)).fit().into(binding.image);

                } else {
                    Picasso.with(this).load(imgUri1).fit().into(binding.image);

                }
            }




        } else if (requestCode == IMG_REQ1 && resultCode == Activity.RESULT_OK && data != null) {

            imgUri1 = data.getData();
            addActivityViewModel.image_uri.set(imgUri1.toString());
            binding.icon.setVisibility(View.GONE);
            File file = new File(Common.getImagePath(this, imgUri1));

            Picasso.with(this).load(file).fit().into(binding.image);

        }

    }

    private Uri getUriFromBitmap(Bitmap bitmap) {
        String path = "";
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
            path = MediaStore.Images.Media.insertImage(this.getContentResolver(), bitmap, "title", null);
            return Uri.parse(path);

        } catch (SecurityException e) {
            Toast.makeText(this, getString(R.string.perm_image_denied), Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            Toast.makeText(this, getString(R.string.perm_image_denied), Toast.LENGTH_SHORT).show();

        }
        return null;
    }


}
