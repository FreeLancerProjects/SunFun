package com.creativeshare.sunfun.models;

import java.io.Serializable;
import java.util.List;

public class NotificationDataModel implements Serializable {

    private List<NotificationModel> data;

    public List<NotificationModel> getData() {
        return data;
    }

    public class NotificationModel implements Serializable
    {
        private int id;
        private String from_id;
        private String to_id;
        private String notification_type;
        private String notification_date;
        private int notification_read;
        private String action_type;
        private String event_id;
        private String booking_id;
        private String status;

        public int getId() {
            return id;
        }

        public String getFrom_id() {
            return from_id;
        }

        public String getTo_id() {
            return to_id;
        }

        public String getNotification_type() {
            return notification_type;
        }

        public String getNotification_date() {
            return notification_date;
        }

        public int getNotification_read() {
            return notification_read;
        }

        public String getAction_type() {
            return action_type;
        }

        public String getEvent_id() {
            return event_id;
        }

        public String getBooking_id() {
            return booking_id;
        }

        public String getStatus() {
            return status;
        }
    }
}
