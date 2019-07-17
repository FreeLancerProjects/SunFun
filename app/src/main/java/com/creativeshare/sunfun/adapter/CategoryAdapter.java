package com.creativeshare.sunfun.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.databinding.DataBindingUtil;

import com.creativeshare.sunfun.R;
import com.creativeshare.sunfun.databinding.SpinnerCategoryRowBinding;
import com.creativeshare.sunfun.models.CategoryDataModel;

import java.util.List;
import java.util.Locale;

import io.paperdb.Paper;

public class CategoryAdapter extends BaseAdapter {
    private List<CategoryDataModel.Category> categoryList;
    private Context context;
    private LayoutInflater inflater;

    public CategoryAdapter(List<CategoryDataModel.Category> categoryList, Context context) {
        this.categoryList = categoryList;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return categoryList.size();
    }

    @Override
    public Object getItem(int i) {
        return categoryList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        @SuppressLint("ViewHolder") SpinnerCategoryRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.spinner_category_row,null,false);
        Paper.init(context);
        String lang = Paper.book().read("lang", Locale.getDefault().getLanguage());
        binding.setLang(lang);
        binding.setCategory(categoryList.get(i));
        return binding.getRoot();
    }
}
