package com.creativeshare.sunfun.viewmodel.signup_view_model;

import com.creativeshare.sunfun.models.UserModel;

public interface Listener {

    void onSuccess(UserModel userModel);
    void onFailed(int code);
    void onError(String error);
}
