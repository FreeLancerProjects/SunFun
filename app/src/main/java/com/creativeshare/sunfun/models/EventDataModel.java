package com.creativeshare.sunfun.models;

import android.net.Uri;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import com.creativeshare.sunfun.R;
import com.creativeshare.sunfun.tags.Tags;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class EventDataModel implements Serializable {

    private List<EventModel> data;

    public List<EventModel> getData() {
        return data;
    }

    public static class EventModel implements Serializable
    {
        private int id;
        private String company_id;
        private String ar_title;
        private String en_title;
        private String ar_description;
        private String en_description;
        private String start_at;
        private String end_at;
        private String from_time;
        private String to_time;
        private String address;
        private String image1;
        private String image2;
        private String available;
        private String latitude;
        private String longitude;
        private String max_number;
        private String price;
        private String status;
        private String ar_information;
        private String en_information;
        private String cat_id;
        private String sub_id;
        private String responsible;
        private int booking_number;
        private int is_booking;

        private List<ActivityModel> activities;


        public int getId() {
            return id;
        }

        public String getCompany_id() {
            return company_id;
        }

        public String getAr_title() {
            return ar_title;
        }

        public String getEn_title() {
            return en_title;
        }

        public String getAr_description() {
            return ar_description;
        }

        public String getEn_description() {
            return en_description;
        }

        public String getStart_at() {
            return start_at;
        }

        public String getEnd_at() {
            return end_at;
        }

        public String getFrom_time() {
            return from_time;
        }

        public String getTo_time() {
            return to_time;
        }

        public String getAddress() {
            return address;
        }

        public String getImage1() {
            return image1;
        }

        public String getImage2() {
            return image2;
        }

        public String getAvailable() {
            return available;
        }

        public String getLatitude() {
            return latitude;
        }

        public String getLongitude() {
            return longitude;
        }

        public String getMax_number() {
            return max_number;
        }

        public String getPrice() {
            return price;
        }

        public String getStatus() {
            return status;
        }

        public String getAr_information() {
            return ar_information;
        }

        public String getEn_information() {
            return en_information;
        }

        public String getCat_id() {
            return cat_id;
        }

        public String getSub_id() {
            return sub_id;
        }

        public String getResponsible() {
            return responsible;
        }

        public int getBooking_number() {
            return booking_number;
        }

        public int getIs_booking() {
            return is_booking;
        }

        public List<ActivityModel> getActivities() {
            return activities;
        }

        @BindingAdapter("app:Uri")
        public static void displayImage(RoundedImageView imageView,String imageEndPoint)
        {

            Picasso.with(imageView.getContext()).load(Uri.parse(Tags.IMAGE_EVENT_URL+imageEndPoint)).placeholder(R.drawable.logo).fit().into(imageView);

        }
        @BindingAdapter({"app:startDate","app:endDate"})
        public static void displayDate(TextView textView,String start_date,String end_date)
        {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
            String sDate = dateFormat.format(new Date(Long.parseLong(start_date)*1000));

            SimpleDateFormat dateFormat2 = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);

            String eDate = dateFormat2.format(new Date(Long.parseLong(end_date)*1000));

            textView.setText(String.format("%s - %s",sDate,eDate));
        }

        @BindingAdapter({"app:startTime","app:endTime"})
        public static void displayTime(TextView textView,String start_time,String end_time)
        {
            SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm aa", Locale.ENGLISH);
            String sTime = dateFormat.format(new Date(Long.parseLong(start_time)*1000));

            SimpleDateFormat dateFormat2 = new SimpleDateFormat("hh:mm aa", Locale.ENGLISH);

            String eTime = dateFormat2.format(new Date(Long.parseLong(end_time)*1000));

            textView.setText(String.format("%s - %s",sTime,eTime));
        }

        public static class ActivityModel implements Serializable
        {
            private int id;
            private String event_id;
            private String ar_title;
            private String en_title;
            private String ar_place;
            private String en_place;
            private String image;
            private String unit;
            private String start_at;
            private String end_at;
            private String address;
            private String price;
            private String max_number;
            private int booking_number;


            public int getId() {
                return id;
            }

            public String getEvent_id() {
                return event_id;
            }

            public String getAr_title() {
                return ar_title;
            }

            public String getEn_title() {
                return en_title;
            }

            public String getAr_place() {
                return ar_place;
            }

            public String getEn_place() {
                return en_place;
            }

            public String getImage() {
                return image;
            }

            public String getUnit() {
                return unit;
            }

            public String getStart_at() {
                return start_at;
            }

            public String getEnd_at() {
                return end_at;
            }

            public String getAddress() {
                return address;
            }

            public String getPrice() {
                return price;
            }

            public String getMax_number() {
                return max_number;
            }

            public int getBooking_number() {
                return booking_number;
            }


            @BindingAdapter("app:activityImage")
            public static void displayActivityImage(ImageView imageView,String url)
            {
                Picasso.with(imageView.getContext()).load(Uri.parse(Tags.IMAGE_ACTIVITY_URL+url)).fit().placeholder(R.drawable.logo).into(imageView);
            }

        }

    }
}
