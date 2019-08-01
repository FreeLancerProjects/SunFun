package com.creativeshare.sunfun.viewmodel.upgrade_model_view;

import com.creativeshare.sunfun.models.UserModel;

public interface UpgradeListener {

    void onSuccess(UserModel userModel);
    void onFailed(int code);
    void onError(String error);
}
