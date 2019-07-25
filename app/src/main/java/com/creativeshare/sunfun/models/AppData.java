package com.creativeshare.sunfun.models;

import java.io.Serializable;
import java.util.List;

public class AppData implements Serializable {

    private List<AppModel> data;

    public List<AppModel> getData() {
        return data;
    }

    public class AppModel implements Serializable
    {
        private String ar_content;
        private String en_content;

        public String getAr_content() {
            return ar_content;
        }

        public String getEn_content() {
            return en_content;
        }
    }

}
