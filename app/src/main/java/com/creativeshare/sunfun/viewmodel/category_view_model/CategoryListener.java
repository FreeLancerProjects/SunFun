package com.creativeshare.sunfun.viewmodel.category_view_model;

import com.creativeshare.sunfun.models.CategoryDataModel;

import java.util.List;

public interface CategoryListener {
    void onSuccess(List<CategoryDataModel.Category>  categoryList);
    void onFailed(int code);
    void onError(String error);
}
