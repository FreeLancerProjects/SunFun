package com.creativeshare.sunfun.services;


import com.creativeshare.sunfun.models.CategoryDataModel;
import com.creativeshare.sunfun.models.EventDataModel;
import com.creativeshare.sunfun.models.PaymentDataModel;
import com.creativeshare.sunfun.models.UserModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Service {


    @FormUrlEncoded
    @POST("api/login")
    Call<UserModel> logIn(@Field("email") String email,
                          @Field("password") String password

    );

    @FormUrlEncoded
    @POST("api/register")
    Call<UserModel> signUp(@Field("name") String name,
                           @Field("email") String email,
                           @Field("password") String password,
                           @Field("phone_code") String phone_code,
                           @Field("phone") String phone,
                           @Field("software_type") String software_type
                           );

    @GET("api/categories")
    Call<CategoryDataModel> getCategories();

    @GET("api/events")
    Call<EventDataModel> getEvents(@Query("id") String id,
                                   @Query("page")int page);

    @FormUrlEncoded
    @POST("api/searchByCategory")
    Call<EventDataModel> searchByCategory(@Field("id") String subCategoryId,
                                          @Field("cat_id") String cat_id,
                                          @Field("user_id") String user_id
                                          );

    @GET("api/all_paying")
    Call<PaymentDataModel> getPaymentMethod();

}


