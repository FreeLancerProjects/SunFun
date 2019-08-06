package com.creativeshare.sunfun.activities_fragments.activity_home.fragments;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.creativeshare.sunfun.R;
import com.creativeshare.sunfun.activities_fragments.activity_home.activity.HomeActivity;
import com.creativeshare.sunfun.databinding.DialogPasswordBinding;
import com.creativeshare.sunfun.databinding.DialogSelectImageBinding;
import com.creativeshare.sunfun.databinding.FragmentCompanyEditProfileBinding;
import com.creativeshare.sunfun.models.UserModel;
import com.creativeshare.sunfun.preferences.Preferences;
import com.creativeshare.sunfun.share.Common;
import com.creativeshare.sunfun.viewmodel.edit_profile_view_model.Edit_Profile_View_Model;
import com.mukesh.countrypicker.Country;
import com.mukesh.countrypicker.CountryPicker;
import com.mukesh.countrypicker.listeners.OnCountryPickerListener;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Locale;

import io.paperdb.Paper;

public class Fragment_Company_Edit_Profile extends Fragment implements OnCountryPickerListener {
    private HomeActivity activity;
    private String current_language;
    private final String READ_PERM = Manifest.permission.READ_EXTERNAL_STORAGE;
    private final String write_permission = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    private final String camera_permission = Manifest.permission.CAMERA;
    private final int IMG_REQ1 = 1, IMG_REQ2 = 2;
    private Uri imgUri1 = null;
    private FragmentCompanyEditProfileBinding binding;
    private CountryPicker picker;
    private Preferences preferences;
    private UserModel userModel;
    private Edit_Profile_View_Model edit_profile_view_model;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_company_edit_profile, container, false);
        edit_profile_view_model = ViewModelProviders.of(this).get(Edit_Profile_View_Model.class);
        initView();
        return binding.getRoot();
    }

    private void initView() {
        activity = (HomeActivity) getActivity();
        Paper.init(activity);
        preferences = Preferences.getInstance();
        userModel =preferences.getUserData(activity);
        current_language = Paper.book().read("lang", Locale.getDefault().getLanguage());
        binding.setLang(current_language);
        binding.setUserModel(userModel);
        binding.setViewModel(edit_profile_view_model);
        edit_profile_view_model.setContext(activity);
        updateUIData(userModel);

        edit_profile_view_model.userModelMutableLiveData.observe(this, userModel -> {
            Fragment_Company_Edit_Profile.this.userModel = userModel;
            updateUIData(userModel);
            activity.refreshProfile(userModel);
            activity.Back();
        });



        CreateCountryDialog();

        binding.llBack.setOnClickListener((v) ->
                activity.Back()
        );
        binding.imagePhoneCode.setOnClickListener((v)->picker.showDialog(activity));
        binding.image.setOnClickListener(view -> CreateImageAlertDialog());
        binding.btnEditPassword.setOnClickListener(view -> CreatePasswordAlertDialog());

    }

    private void updateUIData(UserModel userModel) {
        binding.edtName.setText(userModel.getUser().getName());
        binding.edtEmail.setText(userModel.getUser().getEmail());
        binding.tvPhoneCode.setText(userModel.getUser().getPhone_code().replaceFirst("00","+"));
        binding.edtPhone.setText(userModel.getUser().getPhone());
        binding.edtResponsibleName.setText(userModel.getUser().getResponsible()!=null?userModel.getUser().getResponsible():"");
        edit_profile_view_model.name.set(userModel.getUser().getName());
        edit_profile_view_model.email.set(userModel.getUser().getEmail());
        edit_profile_view_model.phone_code.set(userModel.getUser().getPhone_code());
        edit_profile_view_model.phone.set(userModel.getUser().getPhone());
        edit_profile_view_model.responsible_name.set(userModel.getUser().getResponsible()!=null?userModel.getUser().getResponsible():"");

    }

    public static Fragment_Company_Edit_Profile newInstance() {
        return new Fragment_Company_Edit_Profile();
    }

    private void CreateCountryDialog() {
        CountryPicker.Builder builder = new CountryPicker.Builder()
                .canSearch(true)
                .with(activity)
                .listener(this);
        picker = builder.build();




    }
    @Override
    public void onSelectCountry(Country country) {
        updateUi(country);
    }

    private void updateUi(Country country) {
        edit_profile_view_model.phone_code.set(country.getDialCode());
    }

    private void CreateImageAlertDialog()
    {

        final AlertDialog dialog = new AlertDialog.Builder(activity)
                .setCancelable(true)
                .create();

        DialogSelectImageBinding binding = DataBindingUtil.inflate(LayoutInflater.from(activity),R.layout.dialog_select_image,null,false);



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
        if (ActivityCompat.checkSelfPermission(activity, READ_PERM) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{READ_PERM}, IMG_REQ1);
        } else {
            SelectImage(IMG_REQ1);
        }
    }

    private void Check_CameraPermission()
    {
        if (ContextCompat.checkSelfPermission(activity,camera_permission)!= PackageManager.PERMISSION_GRANTED&&ContextCompat.checkSelfPermission(activity,write_permission)!= PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(activity,new String[]{camera_permission,write_permission},IMG_REQ2);
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
                Toast.makeText(activity,R.string.perm_image_denied, Toast.LENGTH_SHORT).show();
            }
            catch (Exception e)
            {
                Toast.makeText(activity,R.string.perm_image_denied, Toast.LENGTH_SHORT).show();

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
                Toast.makeText(activity, getString(R.string.perm_image_denied), Toast.LENGTH_SHORT).show();
            }

        } else if (requestCode == IMG_REQ2) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                SelectImage(IMG_REQ2);
            } else {
                Toast.makeText(activity, getString(R.string.perm_image_denied), Toast.LENGTH_SHORT).show();
            }
        }


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMG_REQ2 && resultCode == Activity.RESULT_OK && data != null) {

            Bitmap bitmap = (Bitmap) data.getExtras().get("data");

            imgUri1 = getUriFromBitmap(bitmap);
            edit_profile_view_model.editImageProfile(userModel.getUser().getId(),imgUri1.toString());
            if (imgUri1 != null) {
                String path = Common.getImagePath(activity, imgUri1);

                if (path != null) {
                    Picasso.with(activity).load(new File(path)).fit().into(binding.image);

                } else {
                    Picasso.with(activity).load(imgUri1).fit().into(binding.image);

                }
            }




        } else if (requestCode == IMG_REQ1 && resultCode == Activity.RESULT_OK && data != null) {

            imgUri1 = data.getData();
            edit_profile_view_model.editImageProfile(userModel.getUser().getId(),imgUri1.toString());

            File file = new File(Common.getImagePath(activity, imgUri1));

            Picasso.with(activity).load(file).fit().into(binding.image);

        }

    }

    private Uri getUriFromBitmap(Bitmap bitmap) {
        String path = "";
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
            path = MediaStore.Images.Media.insertImage(activity.getContentResolver(), bitmap, "title", null);
            return Uri.parse(path);

        } catch (SecurityException e) {
            Toast.makeText(activity, getString(R.string.perm_image_denied), Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            Toast.makeText(activity, getString(R.string.perm_image_denied), Toast.LENGTH_SHORT).show();

        }
        return null;
    }

    private void CreatePasswordAlertDialog() {
        final android.app.AlertDialog dialog = new android.app.AlertDialog.Builder(activity)
                .create();

        DialogPasswordBinding binding = DataBindingUtil.inflate(LayoutInflater.from(activity), R.layout.dialog_password, null, false);



        binding.btnOk.setOnClickListener((v) ->
                {
                    String password = binding.edtNewPassword.getText().toString().trim();
                    if (!TextUtils.isEmpty(password))
                    {
                        binding.edtNewPassword.setError(null);
                        Common.CloseKeyBoard(activity,binding.edtNewPassword);
                        dialog.dismiss();
                        edit_profile_view_model.editPassword(userModel.getUser().getId(),password);

                    }else
                    {
                        binding.edtNewPassword.setError(getString(R.string.field_req));
                    }
                }

        );
        dialog.getWindow().getAttributes().windowAnimations = R.style.dialog_congratulation_animation;
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_window_bg);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setView(binding.getRoot());
        dialog.show();
    }

}
