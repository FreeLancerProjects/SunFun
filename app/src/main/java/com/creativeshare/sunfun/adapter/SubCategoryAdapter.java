package com.creativeshare.sunfun.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.databinding.DataBindingUtil;

import com.creativeshare.sunfun.R;
import com.creativeshare.sunfun.databinding.SpinnerSubCategoryRowBinding;
import com.creativeshare.sunfun.models.CategoryDataModel;

import java.util.List;
import java.util.Locale;

import io.paperdb.Paper;

public class SubCategoryAdapter extends BaseAdapter {
    private List<CategoryDataModel.SubCategory> subCategoryList;
    private Context context;
    private LayoutInflater inflater;

    public SubCategoryAdapter(List<CategoryDataModel.SubCategory> subCategoryList, Context context) {
        this.subCategoryList = subCategoryList;
        this.context = context;
        inflater = LayoutInflater.from(context);

    }

    @Override
    public int getCount() {
        return subCategoryList.size();
    }

    @Override
    public Object getItem(int i) {
        return subCategoryList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        @SuppressLint("ViewHolder") SpinnerSubCategoryRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.spinner_sub_category_row,null,false);
        Paper.init(context);
        String lang = Paper.book().read("lang", Locale.getDefault().getLanguage());
        binding.setLang(lang);
        binding.setSubCategory(subCategoryList.get(i));
        return binding.getRoot();
    }
}
