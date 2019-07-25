package com.creativeshare.sunfun.models;

import java.io.Serializable;
import java.util.List;

public class PaymentDataModel implements Serializable {

    private List<PaymentModel> data;

    public List<PaymentModel> getData() {
        return data;
    }

    public class PaymentModel implements Serializable
    {
        private int id;
        private String ar_title;
        private String en_title;

        public int getId() {
            return id;
        }

        public String getAr_title() {
            return ar_title;
        }

        public String getEn_title() {
            return en_title;
        }
    }
}
