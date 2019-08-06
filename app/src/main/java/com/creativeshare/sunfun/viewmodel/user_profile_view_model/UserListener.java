package com.creativeshare.sunfun.viewmodel.user_profile_view_model;

import com.creativeshare.sunfun.models.UserModel;

public interface UserListener {

    void onSuccess(UserModel userModel);
    void onFailed(int code);
    void onError(String error);
}
