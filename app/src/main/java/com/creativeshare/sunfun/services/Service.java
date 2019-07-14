package com.creativeshare.sunfun.services;



import com.creativeshare.sunfun.models.UserModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Service {


    @FormUrlEncoded
    @POST("api/login")
    Call<UserModel> signIn(@Field("email") String email,
                           @Field("password") String password

    );


}


