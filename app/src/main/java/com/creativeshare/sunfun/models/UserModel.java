package com.creativeshare.sunfun.models;

import java.io.Serializable;

public class UserModel implements Serializable {
private User user;
       public  class  User {
        private int id;
               private String name;
           private String email;
           private String email_verified_at;
                private int user_type;
                private int is_login;
              private int software_type;
               private int be_company;
                private double latitude;
                private double longitude;
                private String national_image;
                private String responsible;
               private String phone;
                private String phone_code;
                private String facebook_link;
               private String twitter_link;
                private String youtube_link;
                private String gmail_link;
               private String lang;
                private int is_available;
                private double ratings;
               private int is_deleted;
                private String image;
           private String created_at;
           private String updated_at;

           public int getId() {
               return id;
           }

           public String getName() {
               return name;
           }

           public String getEmail() {
               return email;
           }

           public String getEmail_verified_at() {
               return email_verified_at;
           }

           public int getUser_type() {
               return user_type;
           }

           public int getIs_login() {
               return is_login;
           }

           public int getSoftware_type() {
               return software_type;
           }

           public int getBe_company() {
               return be_company;
           }

           public double getLatitude() {
               return latitude;
           }

           public double getLongitude() {
               return longitude;
           }

           public String getNational_image() {
               return national_image;
           }

           public String getResponsible() {
               return responsible;
           }

           public String getPhone() {
               return phone;
           }

           public String getPhone_code() {
               return phone_code;
           }

           public String getFacebook_link() {
               return facebook_link;
           }

           public String getTwitter_link() {
               return twitter_link;
           }

           public String getYoutube_link() {
               return youtube_link;
           }

           public String getGmail_link() {
               return gmail_link;
           }

           public String getLang() {
               return lang;
           }

           public int getIs_available() {
               return is_available;
           }

           public double getRatings() {
               return ratings;
           }

           public int getIs_deleted() {
               return is_deleted;
           }

           public String getImage() {
               return image;
           }

           public String getCreated_at() {
               return created_at;
           }

           public String getUpdated_at() {
               return updated_at;
           }
       }
    private String api_token;

    public User getUser() {
        return user;
    }

    public String getApi_token() {
        return api_token;
    }
}
