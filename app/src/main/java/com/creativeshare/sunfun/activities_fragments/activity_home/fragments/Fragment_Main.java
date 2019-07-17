package com.creativeshare.sunfun.activities_fragments.activity_home.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.creativeshare.sunfun.R;
import com.creativeshare.sunfun.activities_fragments.activity_home.activity.HomeActivity;
import com.creativeshare.sunfun.adapter.CategoryAdapter;
import com.creativeshare.sunfun.adapter.SubCategoryAdapter;
import com.creativeshare.sunfun.databinding.FragmentMainBinding;
import com.creativeshare.sunfun.models.CategoryDataModel;
import com.creativeshare.sunfun.viewmodel.category_view_model.CategoryViewModel;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

public class Fragment_Main extends Fragment {
    private HomeActivity activity;
    private FragmentMainBinding binding;
    private CategoryViewModel viewModel;
    private CategoryAdapter categoryAdapter;
    private List<CategoryDataModel.Category> categoryList;
    private List<CategoryDataModel.SubCategory> subCategoryList;
    private SubCategoryAdapter subCategoryAdapter;
    private String category_id = "", subcategory_id = "";
    private boolean isFirstTime = true;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false);
        viewModel = ViewModelProviders.of(this).get(CategoryViewModel.class);
        initView();
        return binding.getRoot();
    }

    private void initView() {
        categoryList = new ArrayList<>();
        subCategoryList = new ArrayList<>();
        activity = (HomeActivity) getActivity();
        Paper.init(activity);
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
                    category_id = subCategoryList.get(i).getCat_id();
                    subcategory_id = String.valueOf(subCategoryList.get(i).getId());

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void updateSubCategoryAdapter(List<CategoryDataModel.SubCategory> subCategoryList) {
        subCategoryAdapter = new SubCategoryAdapter(subCategoryList, activity);
        binding.spinnerSubCategory.setAdapter(subCategoryAdapter);
    }


    public static Fragment_Main newInstance() {
        return new Fragment_Main();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isFirstTime)
        {
            viewModel.getCategories();
            isFirstTime=false;
        }
    }
}
