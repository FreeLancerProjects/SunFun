package com.creativeshare.sunfun.models;

import java.io.Serializable;
import java.util.List;

public class CategoryDataModel implements Serializable {


    private List<Category> data;

    public List<Category> getData() {
        return data;
    }


    public static class Category implements Serializable
    {

        private int id;
        private String ar_title;
        private String en_title;
        private List<SubCategory> sub_categories;

        public void setAr_title(String ar_title) {
            this.ar_title = ar_title;
        }

        public void setEn_title(String en_title) {
            this.en_title = en_title;
        }

        public int getId() {
            return id;
        }

        public String getAr_title() {
            return ar_title;
        }

        public String getEn_title() {
            return en_title;
        }

        public List<SubCategory> getSub_categories() {
            return sub_categories;
        }
    }

    public static class SubCategory implements Serializable
    {
        private int id;
        private String cat_id;
        private String ar_title;
        private String en_title;


        public void setAr_title(String ar_title) {
            this.ar_title = ar_title;
        }

        public void setEn_title(String en_title) {
            this.en_title = en_title;
        }

        public int getId() {
            return id;
        }

        public String getCat_id() {
            return cat_id;
        }

        public String getAr_title() {
            return ar_title;
        }

        public String getEn_title() {
            return en_title;
        }
    }
}
