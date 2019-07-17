package com.creativeshare.sunfun.models;

import java.io.Serializable;

public class UserModel implements Serializable {

    private User user;

    public User getUser() {
        return user;
    }

    public class User
    {
        private int id;
        private String name;
        private String email;
        private int user_type;
        private int be_company;
        private double latitude;
        private double longitude;
        private String national_image;
        private String responsible;
        private String phone;
        private int is_available;
        private double ratings;
        private String image;

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getEmail() {
            return email;
        }

        public int getUser_type() {
            return user_type;
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

        public int getIs_available() {
            return is_available;
        }

        public double getRatings() {
            return ratings;
        }

        public String getImage() {
            return image;
        }
    }
}
