package com.creativeshare.sunfun.services;


import com.creativeshare.sunfun.models.AppData;
import com.creativeshare.sunfun.models.BankDataModel;
import com.creativeshare.sunfun.models.BookingScanData;
import com.creativeshare.sunfun.models.CategoryDataModel;
import com.creativeshare.sunfun.models.EventDataModel;
import com.creativeshare.sunfun.models.EventIdModel;
import com.creativeshare.sunfun.models.EventModelToUpload;
import com.creativeshare.sunfun.models.NotificationDataModel;
import com.creativeshare.sunfun.models.OrderDataModel;
import com.creativeshare.sunfun.models.PaymentDataModel;
import com.creativeshare.sunfun.models.PlaceGeocodeData;
import com.creativeshare.sunfun.models.PlaceMapDetailsData;
import com.creativeshare.sunfun.models.SocialDataModel;
import com.creativeshare.sunfun.models.UserModel;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
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
                                   @Field("subject") String subject,
                                   @Field("message") String message
    );

    @GET("api/all_bank_accounts")
    Call<BankDataModel> getBanks();

    @FormUrlEncoded
    @POST("api/about_us")
    Call<AppData> getAppData(@Field("type") int type);


    @POST("api/add_order")
    Call<ResponseBody> bookEvent(@Body EventModelToUpload eventModelToUpload);

    @FormUrlEncoded
    @POST("api/visit")
    Call<ResponseBody> updateVisit(@Field("date") long date,
                                   @Field("software_type") int software_type

    );

    @GET("place/findplacefromtext/json")
    Call<PlaceMapDetailsData> searchOnMap(@Query(value = "inputtype") String inputtype,
                                          @Query(value = "input") String input,
                                          @Query(value = "fields") String fields,
                                          @Query(value = "language") String language,
                                          @Query(value = "key") String key);

    @GET("geocode/json")
    Call<PlaceGeocodeData> getGeoData(@Query(value = "latlng") String latlng,
                                      @Query(value = "language") String language,
                                      @Query(value = "key") String key);

    @GET("api/social")
    Call<SocialDataModel> getSocial();

    @Multipart
    @POST("api/upgrade")
    Call<UserModel> upgradeToCompany(@Part("id") RequestBody user_id,
                                     @Part("responsible") RequestBody responsible,
                                     @Part("latitude") RequestBody latitude,
                                     @Part("longitude") RequestBody longitude,
                                     @Part("address") RequestBody address,
                                     @Part MultipartBody.Part image
    );

    @FormUrlEncoded
    @POST("api/myEvents")
    Call<EventDataModel> getMyEvents(@Field("id") String id, @Field("page") int page);


    @FormUrlEncoded
    @POST("api/delete_event")
    Call<ResponseBody> deleteEvent(@Field("event_id") int event_id);

    @Multipart
    @POST("api/add_event")
    Call<EventIdModel> addEvent(@Part("company_id") RequestBody company_id,
                                @Part("ar_title") RequestBody ar_title,
                                @Part("en_title") RequestBody en_title,
                                @Part("ar_description") RequestBody ar_description,
                                @Part("en_description") RequestBody en_description,
                                @Part("start_at") RequestBody start_at,
                                @Part("end_at") RequestBody end_at,
                                @Part("from_time") RequestBody from_time,
                                @Part("to_time") RequestBody to_time,
                                @Part("address") RequestBody address,
                                @Part("latitude") RequestBody latitude,
                                @Part("longitude") RequestBody longitude,
                                @Part("price") RequestBody price,
                                @Part("max_number") RequestBody max_number,
                                @Part("ar_information") RequestBody ar_information,
                                @Part("en_information") RequestBody en_information,
                                @Part("cat_id") RequestBody cat_id,
                                @Part("sub_id") RequestBody sub_id,
                                @Part MultipartBody.Part image1,
                                @Part MultipartBody.Part image2


    );

    @Multipart
    @POST("api/add_activity")
    Call<ResponseBody> addActivity(@Part("event_id") RequestBody event_id,
                                   @Part("start_at") RequestBody start_at,
                                   @Part("end_at") RequestBody end_at,
                                   @Part("unit") RequestBody unit,
                                   @Part("price") RequestBody price,
                                   @Part("max_number") RequestBody max_number,
                                   @Part("ar_title") RequestBody ar_title,
                                   @Part("en_title") RequestBody en_title,
                                   @Part("ar_place") RequestBody ar_place,
                                   @Part("en_place") RequestBody en_place,
                                   @Part MultipartBody.Part image


    );

    @FormUrlEncoded
    @POST("api/myOrders")
    Call<OrderDataModel> getMyCurrentOrders(@Field("id") int id, @Field("page") int page);

    @FormUrlEncoded
    @POST("api/myEndedOrders")
    Call<OrderDataModel> getMyPreviousOrders(@Field("id") int id, @Field("page") int page);


    @FormUrlEncoded
    @POST("api/myNotification")
    Call<NotificationDataModel> getMyNotification(@Field("user_id") int id, @Field("page") int page);

    @Multipart
    @POST("api/update/user_image")
    Call<UserModel> editUserImage(@Part("id") RequestBody id,
                                  @Part MultipartBody.Part image);


    @FormUrlEncoded
    @POST("api/user_profile_update")
    Call<UserModel> editClientProfile(@Field("id") int id,
                                      @Field("name") String name,
                                      @Field("email") String email,
                                      @Field("phone_code") String phone_code,
                                      @Field("phone") String phone);


    @FormUrlEncoded
    @POST("api/user_profile_update")
    Call<UserModel> editCompanyProfile(@Field("id") int id,
                                       @Field("name") String name,
                                       @Field("email") String email,
                                       @Field("phone_code") String phone_code,
                                       @Field("phone") String phone,
                                       @Field("responsible") String responsible
    );

    @FormUrlEncoded
    @POST("api/myBooking")
    Call<BookingScanData> scanCode(@Field("booking_code") String booking_code);

    @FormUrlEncoded
    @POST("api/Booking")
    Call<BookingScanData> myBookingDetails(@Field("booking_id") String booking_id);

    @FormUrlEncoded
    @POST("api/acceptBookingEvent")
    Call<ResponseBody> accept(@Field("booking_id") int booking_id,
                              @Field("company_id") String company_id,
                              @Field("notification_id") int notification_id,
                              @Field("event_id") String event_id

    );

    @FormUrlEncoded
    @POST("api/refuseBookingEvent")
    Call<ResponseBody> refuse(@Field("booking_id") int booking_id,
                              @Field("company_id") String company_id,
                              @Field("notification_id") int notification_id,
                              @Field("event_id") String event_id

    );

    @FormUrlEncoded
    @POST("api/user_update_info")
    Call<UserModel> getUserData(@Field("user_id") String user_id
    );

    @FormUrlEncoded
    @POST("api/user_password")
    Call<UserModel> updatePassword(@Field("id") int id,
                                   @Field("password") String password

    );

    @FormUrlEncoded
    @POST("api/updateToken")
    Call<ResponseBody> updateToken(@Field("user_id_fk") int user_id,
                                   @Field("phone_token") String phone_token

    );

}


