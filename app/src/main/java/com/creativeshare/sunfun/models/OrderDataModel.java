package com.creativeshare.sunfun.models;

import java.io.Serializable;
import java.util.List;

public class OrderDataModel implements Serializable {

    private List<OrderModel> data;

    public List<OrderModel> getData() {
        return data;
    }

    public class OrderModel implements Serializable
    {
        private int id;
        private String company_id;
        private String event_id;
        private String booking_code;
        private String booking_image;
        private int status;
        private String paid_type;
        private String created_at;
        private String event_ar_title;
        private String event_en_title;
        private int event_price;
        private int subscribers_num;

        private BookingDetails booking_details;

        public int getId() {
            return id;
        }

        public String getCompany_id() {
            return company_id;
        }

        public String getEvent_id() {
            return event_id;
        }

        public String getBooking_code() {
            return booking_code;
        }

        public String getBooking_image() {
            return booking_image;
        }

        public int getStatus() {
            return status;
        }

        public int getSubscribers_num() {
            return subscribers_num;
        }

        public String getPaid_type() {
            return paid_type;
        }

        public String getCreated_at() {
            return created_at;
        }

        public String getEvent_ar_title() {
            return event_ar_title;
        }

        public String getEvent_en_title() {
            return event_en_title;
        }

        public int getEvent_price() {
            return event_price;
        }

        public BookingDetails getBooking_details() {
            return booking_details;
        }
    }

    public class BookingDetails implements Serializable
    {
        private int id;
        private String booking_id;
        private String event_id;
        private String activity_id;
        private int subscribers_num;
        private String activitie_ar_title;
        private String activitie_en_title;


        public int getId() {
            return id;
        }

        public String getBooking_id() {
            return booking_id;
        }

        public String getEvent_id() {
            return event_id;
        }

        public String getActivity_id() {
            return activity_id;
        }

        public int getSubscribers_num() {
            return subscribers_num;
        }

        public String getActivitie_ar_title() {
            return activitie_ar_title;
        }

        public String getActivitie_en_title() {
            return activitie_en_title;
        }
    }
}
