package com.creativeshare.sunfun.viewmodel.edit_profile_view_model;

import com.creativeshare.sunfun.models.UserModel;

public interface EditProfileListener {

    void onSuccess(UserModel userModel);
    void onPasswordSuccess();
    void onFailed(int code);
    void onError(String error);

}
