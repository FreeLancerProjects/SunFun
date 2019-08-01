package com.creativeshare.sunfun.activities_fragments.activity_create_event.fragments;

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
import android.widget.AdapterView;
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
import com.creativeshare.sunfun.activities_fragments.activity_create_event.activity.Create_Event_Activity;
import com.creativeshare.sunfun.adapter.CategoryAdapter;
import com.creativeshare.sunfun.adapter.SubCategoryAdapter;
import com.creativeshare.sunfun.databinding.DialogSelectImageBinding;
import com.creativeshare.sunfun.databinding.FragmentDetailsAboutEventBinding;
import com.creativeshare.sunfun.models.CategoryDataModel;
import com.creativeshare.sunfun.share.Common;
import com.creativeshare.sunfun.viewmodel.add_event_view_model.AddEventViewModel;
import com.creativeshare.sunfun.viewmodel.add_event_view_model.ViewModelEventDetails;
import com.creativeshare.sunfun.viewmodel.category_view_model.CategoryViewModel;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.paperdb.Paper;

public class Fragment_Details_About_Event extends Fragment {
    private Create_Event_Activity activity;
    private FragmentDetailsAboutEventBinding binding;
    private CategoryViewModel viewModel;
    private CategoryAdapter categoryAdapter;
    private String cuurent_language;
    private List<CategoryDataModel.Category> categoryList;
    private List<CategoryDataModel.SubCategory> subCategoryList;
    private SubCategoryAdapter subCategoryAdapter;
    private final String READ_PERM = Manifest.permission.READ_EXTERNAL_STORAGE;
    private final String write_permission = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    private final String camera_permission = Manifest.permission.CAMERA;
    private final int IMG_REQ1 = 1, IMG_REQ2 = 2;
    private Uri imgUri1 = null, imgUri2 = null;
    private int selectedType = 0;
    private ViewModelEventDetails viewModelEventDetails;

    public static Fragment_Details_About_Event newInstance() {
        return new Fragment_Details_About_Event();
    }



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_details_about_event,container,false);
        viewModel = ViewModelProviders.of(this).get(CategoryViewModel.class);
        viewModelEventDetails = ViewModelProviders.of(this).get(ViewModelEventDetails.class);

        initView();
        return binding.getRoot();
    }

    private void initView() {
        activity = (Create_Event_Activity) getActivity();
        Paper.init(activity);
        cuurent_language = Paper.book().read("lang", Locale.getDefault().getLanguage());
        binding.setViewModel(viewModelEventDetails);
        viewModelEventDetails.setContext(activity);
        categoryList = new ArrayList<>();
        subCategoryList = new ArrayList<>();

        viewModel.setContext(activity);
        viewModel.data.observe(this, categories -> {
            categoryList.clear();
            categoryList.addAll(categories);
            categoryAdapter = new CategoryAdapter(categories, activity);
            binding.spinnerCategory.setAdapter(categoryAdapter);
        });
        binding.spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    subCategoryList.clear();
                    CategoryDataModel.SubCategory subCategory = new CategoryDataModel.SubCategory();
                    subCategory.setAr_title("التصنيف");
                    subCategory.setEn_title("Category");
                    subCategoryList.add(subCategory);
                    updateSubCategoryAdapter(subCategoryList);



                } else {
                    subCategoryList.clear();
                    CategoryDataModel.SubCategory subCategory = new CategoryDataModel.SubCategory();
                    subCategory.setAr_title("التصنيف");
                    subCategory.setEn_title("Category");
                    subCategoryList.add(subCategory);
                    subCategoryList.addAll(categoryList.get(i).getSub_categories());
                    updateSubCategoryAdapter(subCategoryList);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        binding.spinnerSubCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i != 0) {
                    viewModelEventDetails.event_type.set(subCategoryList.get(i).getCat_id());
                    viewModelEventDetails.event_category.set(subCategoryList.get(i).getId());



                }else
                {
                    viewModelEventDetails.event_category.set(0);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        binding.image1.setOnClickListener(view -> CreateImageAlertDialog(IMG_REQ1));
        binding.image2.setOnClickListener(view -> CreateImageAlertDialog(IMG_REQ2));
        viewModel.getCategories();

    }

    private void updateSubCategoryAdapter(List<CategoryDataModel.SubCategory> subCategoryList) {
        subCategoryAdapter = new SubCategoryAdapter(subCategoryList, activity);
        binding.spinnerSubCategory.setAdapter(subCategoryAdapter);
    }

    public void CheckData(AddEventViewModel addEventViewModel)
    {
        viewModelEventDetails.checkEventDetailsData(addEventViewModel);
    }
    private void CreateImageAlertDialog(final int img_req)
    {

        final AlertDialog dialog = new AlertDialog.Builder(activity)
                .setCancelable(true)
                .create();

        DialogSelectImageBinding binding = DataBindingUtil.inflate(LayoutInflater.from(activity),R.layout.dialog_select_image,null,false);



        binding.btnCamera.setOnClickListener(v -> {
            dialog.dismiss();
            selectedType = 2;
            Check_CameraPermission(img_req);

        });

        binding.btnGallery.setOnClickListener(v -> {
            dialog.dismiss();
            selectedType = 1;
            CheckReadPermission(img_req);



        });

        binding.btnCancel.setOnClickListener(v -> dialog.dismiss());

        dialog.getWindow().getAttributes().windowAnimations= R.style.dialog_congratulation_animation;
        dialog.setCanceledOnTouchOutside(false);
        dialog.setView(binding.getRoot());
        dialog.show();
    }
    private void CheckReadPermission(int img_req)
    {
        if (ActivityCompat.checkSelfPermission(activity, READ_PERM) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{READ_PERM}, img_req);
        } else {
            SelectImage(1,img_req);
        }
    }

    private void Check_CameraPermission(int img_req)
    {
        if (ContextCompat.checkSelfPermission(activity,camera_permission)!= PackageManager.PERMISSION_GRANTED&&ContextCompat.checkSelfPermission(activity,write_permission)!= PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(activity,new String[]{camera_permission,write_permission},img_req);
        }else
        {
            SelectImage(2,img_req);

        }

    }
    private void SelectImage(int type,int img_req) {

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
            startActivityForResult(intent,img_req);

        }else if (type ==2)
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

            if (selectedType ==1)
            {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    SelectImage(selectedType,IMG_REQ1);
                } else {
                    Toast.makeText(activity, getString(R.string.perm_image_denied), Toast.LENGTH_SHORT).show();
                }
            }else
            {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED&& grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    SelectImage(selectedType,IMG_REQ1);
                } else {
                    Toast.makeText(activity, getString(R.string.perm_image_denied), Toast.LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == IMG_REQ2) {
            if (selectedType ==1)
            {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    SelectImage(selectedType,IMG_REQ2);
                } else {
                    Toast.makeText(activity, getString(R.string.perm_image_denied), Toast.LENGTH_SHORT).show();
                }
            }else
            {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED&& grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    SelectImage(selectedType,IMG_REQ2);
                } else {
                    Toast.makeText(activity, getString(R.string.perm_image_denied), Toast.LENGTH_SHORT).show();
                }
            }
        }


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMG_REQ1 && resultCode == Activity.RESULT_OK && data != null) {
            if (selectedType == 1)
            {
                imgUri1 = data.getData();
                binding.icon1.setVisibility(View.GONE);
                File file = new File(Common.getImagePath(activity, imgUri1));
                Picasso.with(activity).load(file).fit().into(binding.image1);
                viewModelEventDetails.image1_uri.set(imgUri1.toString());
            }else if (selectedType ==2)
            {
                binding.icon1.setVisibility(View.GONE);

                Bitmap bitmap = (Bitmap) data.getExtras().get("data");

                imgUri1 = getUriFromBitmap(bitmap);
                viewModelEventDetails.image1_uri.set(imgUri1.toString());

                if (imgUri1 != null) {
                    String path = Common.getImagePath(activity, imgUri1);

                    if (path != null) {
                        Picasso.with(activity).load(new File(path)).fit().into(binding.image1);

                    } else {
                        Picasso.with(activity).load(imgUri1).fit().into(binding.image1);

                    }
                }
            }




        } else if (requestCode == IMG_REQ2 && resultCode == Activity.RESULT_OK && data != null) {

            if (selectedType == 1)
            {
                imgUri2 = data.getData();
                viewModelEventDetails.image2_uri.set(imgUri2.toString());

                binding.icon2.setVisibility(View.GONE);
                File file = new File(Common.getImagePath(activity, imgUri2));

                Picasso.with(activity).load(file).fit().into(binding.image2);
            }else if (selectedType ==2)
            {

                binding.icon2.setVisibility(View.GONE);

                Bitmap bitmap = (Bitmap) data.getExtras().get("data");

                imgUri2 = getUriFromBitmap(bitmap);
                viewModelEventDetails.image2_uri.set(imgUri2.toString());

                if (imgUri2 != null) {
                    String path = Common.getImagePath(activity, imgUri2);

                    if (path != null) {
                        Picasso.with(activity).load(new File(path)).fit().into(binding.image2);

                    } else {
                        Picasso.with(activity).load(imgUri2).fit().into(binding.image2);

                    }
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
