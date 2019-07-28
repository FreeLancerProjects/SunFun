package com.creativeshare.sunfun.models;

import java.io.Serializable;
import java.util.List;

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

            public void setAr_title(String ar_title) {
                this.ar_title = ar_title;
            }

            public void setEn_title(String en_title) {
                this.en_title = en_title;
            }
        }

    }
}
