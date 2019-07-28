package com.creativeshare.sunfun.services;


import com.creativeshare.sunfun.models.AppData;
import com.creativeshare.sunfun.models.BankDataModel;
import com.creativeshare.sunfun.models.CategoryDataModel;
import com.creativeshare.sunfun.models.EventDataModel;
import com.creativeshare.sunfun.models.PaymentDataModel;
import com.creativeshare.sunfun.models.UserModel;

import okhttp3.ResponseBody;
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
                                   @Query("page") int page);

    @FormUrlEncoded
    @POST("api/searchByCategory")
    Call<EventDataModel> searchByCategory(@Field("id") String subCategoryId,
                                          @Field("cat_id") String cat_id,
                                          @Field("user_id") String user_id
    );

    @GET("api/all_paying")
    Call<PaymentDataModel> getPaymentMethod();

    @FormUrlEncoded
    @POST("api/contact_us")
    Call<ResponseBody> sendContact(@Field("fname") String fname,
                                   @Field("lname") String lname,
                                   @Field("email") String email,
                                   @Field("message") String message
    );

    @GET("api/all_bank_accounts")
    Call<BankDataModel> getBanks();

    @GET("api/about_us")
    Call<AppData> getAppData();


    @FormUrlEncoded
    @POST("api/add_order")
    Call<ResponseBody> bookEvent(@Field("company_id") String company_id,
                                 @Field("user_id") int user_id,
                                 @Field("event_id") int event_id,
                                 @Field("subscribers_num") String subscribers_num,
                                 @Field("paid_type") String paid_type

    );

    @FormUrlEncoded
    @POST("api/visit")
    Call<ResponseBody> updateVisit(@Field("date") String date,
                                   @Field("software_type") int software_type

    );

}


