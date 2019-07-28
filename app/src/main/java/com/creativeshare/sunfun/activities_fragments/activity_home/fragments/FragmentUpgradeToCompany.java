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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.creativeshare.sunfun.databinding.FragmentUpgradeToCompanyBinding;
import com.creativeshare.sunfun.models.SelectedLocation;
import com.creativeshare.sunfun.models.UserModel;
import com.creativeshare.sunfun.preferences.Preferences;
import com.creativeshare.sunfun.share.Common;
import com.creativeshare.sunfun.viewmodel.upgrade_model_view.UpgradeViewModel;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Locale;

import io.paperdb.Paper;

public class FragmentUpgradeToCompany extends Fragment {

    private FragmentUpgradeToCompanyBinding binding;
    private SelectedLocation selectedLocation= null;
    private HomeActivity activity;
    private String lang;
    private final int IMG1 = 1, IMG2 = 2;
    private Uri uri = null;
    private final String read_permission = Manifest.permission.READ_EXTERNAL_STORAGE;
    private final String write_permission = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    private final String camera_permission = Manifest.permission.CAMERA;
    private UpgradeViewModel upgradeViewModel;
    private UserModel userModel;
    private Preferences preferences;

    public static FragmentUpgradeToCompany newInstance() {
        return new FragmentUpgradeToCompany();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_upgrade_to_company,container,false);
        upgradeViewModel = ViewModelProviders.of(this).get(UpgradeViewModel.class);
        initView();
        return binding.getRoot();
    }

    private void initView() {
        activity = (HomeActivity) getActivity();
        Paper.init(activity);
        preferences = Preferences.getInstance();
        userModel = preferences.getUserData(activity);
        binding.setUserModel(userModel);
        binding.setViewModel(upgradeViewModel);
        upgradeViewModel.setContext(activity);

        lang = Paper.book().read("lang", Locale.getDefault().getLanguage());
        binding.setLang(lang);
        binding.image.setOnClickListener((view -> CreateImageAlertDialog()));
        binding.flLocation.setOnClickListener(view -> activity.DisplayFragmentMap("fragmentUpgradeToCompany"));
        binding.arrow.setOnClickListener(view -> activity.Back());
    }

    private void CreateImageAlertDialog() {

        final AlertDialog dialog = new AlertDialog.Builder(activity)
                .setCancelable(true)
                .create();


        View view = LayoutInflater.from(activity).inflate(R.layout.dialog_select_image, null);
        Button btn_camera = view.findViewById(R.id.btn_camera);
        Button btn_gallery = view.findViewById(R.id.btn_gallery);
        Button btn_cancel = view.findViewById(R.id.btn_cancel);


        btn_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Check_CameraPermission();

            }
        });

        btn_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Check_ReadPermission();


            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.getWindow().getAttributes().windowAnimations = R.style.dialog_congratulation_animation;
        dialog.setCanceledOnTouchOutside(false);
        dialog.setView(view);
        dialog.show();
    }


    private void Check_ReadPermission() {
        if (ContextCompat.checkSelfPermission(activity, read_permission) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{read_permission}, IMG1);
        } else {
            select_photo(1);
        }
    }

    public void setSelectedLocation(SelectedLocation selectedLocation)
    {
        this.selectedLocation = selectedLocation;
        binding.tvAddress.setText(selectedLocation.getAddress());
        upgradeViewModel.address.set(selectedLocation.getAddress());
        upgradeViewModel.lat.set(selectedLocation.getLat());
        upgradeViewModel.lng.set(selectedLocation.getLng());
    }
    private void Check_CameraPermission()
    {
        if (ContextCompat.checkSelfPermission(activity, camera_permission) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(activity, write_permission) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{camera_permission, write_permission}, IMG2);
        } else {
            select_photo(2);

        }

    }
    private void select_photo(int type)
    {

        Intent intent = new Intent();

        if (type == 1)
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
            startActivityForResult(intent,IMG1);

        }else if (type ==2)
        {
            try {
                intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,IMG2);
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMG1 && resultCode == Activity.RESULT_OK && data != null) {
            uri = data.getData();
            upgradeViewModel.image_uri.set(String.valueOf(uri));

            String path = Common.getImagePath(activity, uri);
            binding.icon.setVisibility(View.GONE);
            if (path!=null)
            {
                Picasso.with(activity).load(new File(path)).fit().into(binding.image);
            }else
                {
                    Picasso.with(activity).load(uri).fit().into(binding.image);

                }
        } else if (requestCode == IMG2 && resultCode == Activity.RESULT_OK && data != null) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            binding.icon.setVisibility(View.GONE);
            uri = getUriFromBitmap(bitmap);
            upgradeViewModel.image_uri.set(String.valueOf(uri));

            if (uri != null) {
                String path = Common.getImagePath(activity, uri);

                if (path!=null)
                {
                    Picasso.with(activity).load(new File(path)).fit().into(binding.image);
                }else
                {
                    Picasso.with(activity).load(uri).fit().into(binding.image);

                }
            }

        }


    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == IMG1) {
            if (grantResults.length > 0) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    select_photo(IMG1);
                } else {
                    Toast.makeText(activity, getString(R.string.perm_image_denied), Toast.LENGTH_SHORT).show();
                }
            }
        } else if (requestCode == IMG2) {
            if (grantResults.length > 0) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    select_photo(2);

                } else {
                    Toast.makeText(activity, getString(R.string.perm_image_denied), Toast.LENGTH_SHORT).show();
                }
            }
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


}
