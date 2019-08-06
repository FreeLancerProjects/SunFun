package com.creativeshare.sunfun.models;

import java.io.Serializable;
import java.util.List;

public class BookingScanData implements Serializable {

    private Booking booking;
    private List<BookingDetails> booking_details;
    private User user;

    public Booking getBooking() {
        return booking;
    }

    public List<BookingDetails> getBooking_details() {
        return booking_details;
    }

    public User getUser() {
        return user;
    }

    public class Booking implements Serializable
    {
        private int id;
        private String event_id;
        private String company_id;
        private String event_ar_title;
        private String event_en_title;

        private String booking_code;
        private String total_booking_price;
        private String subscribers_num;
        private String company_name;
        private String responsible;

        public int getId() {
            return id;
        }

        public String getEvent_id() {
            return event_id;
        }

        public String getBooking_code() {
            return booking_code;
        }

        public String getTotal_booking_price() {
            return total_booking_price;
        }

        public String getSubscribers_num() {
            return subscribers_num;
        }

        public String getCompany_name() {
            return company_name;
        }

        public String getResponsible() {
            return responsible;
        }

        public String getEvent_ar_title() {
            return event_ar_title;
        }

        public String getEvent_en_title() {
            return event_en_title;
        }

        public String getCompany_id() {
            return company_id;
        }
    }

    public class BookingDetails implements Serializable
    {
        private int id;
        private int booking_id;
        private int subscribers_num;
        private String activitie_ar_title;
        private String activitie_en_title;
        private String activitie_image;

        public int getId() {
            return id;
        }

        public int getBooking_id() {
            return booking_id;
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

        public String getActivitie_image() {
            return activitie_image;
        }
    }

    public class User implements Serializable
    {
        private String name;
        private String image;

        public String getName() {
            return name;
        }

        public String getImage() {
            return image;
        }
    }
}
