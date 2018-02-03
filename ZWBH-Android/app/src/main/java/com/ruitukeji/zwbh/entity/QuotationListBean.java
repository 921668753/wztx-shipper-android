package com.ruitukeji.zwbh.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Admin on 2017/7/11.
 */

public class QuotationListBean extends BaseResult<List<QuotationListBean.ResultBean>> {


    public class ResultBean {
        /**
         * id : 289
         * dr_id : 39
         * dr_name : 刘艳锋
         * avatar :
         * dr_score :
         * dr_level :
         * car_style_type : 厢式
         * car_style_length : 4.2米
         * card_number :
         * dr_price : 70.00
         * goods_num :
         * complain_num :
         */

        private int id;
        private int dr_id;
        private int is_selected;
        private String dr_name;
        private String avatar;
        private String dr_score;
        private String dr_level;
        private String car_style_type;
        private String car_style_length;
        private String card_number;
        private String dr_price;
        private String goods_num;
        private String complain_num;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getDr_id() {
            return dr_id;
        }

        public void setDr_id(int dr_id) {
            this.dr_id = dr_id;
        }

        public int getIs_selected() {
            return is_selected;
        }

        public void setIs_selected(int is_selected) {
            this.is_selected = is_selected;
        }

        public String getDr_name() {
            return dr_name;
        }

        public void setDr_name(String dr_name) {
            this.dr_name = dr_name;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getDr_score() {
            return dr_score;
        }

        public void setDr_score(String dr_score) {
            this.dr_score = dr_score;
        }

        public String getDr_level() {
            return dr_level;
        }

        public void setDr_level(String dr_level) {
            this.dr_level = dr_level;
        }

        public String getCar_style_type() {
            return car_style_type;
        }

        public void setCar_style_type(String car_style_type) {
            this.car_style_type = car_style_type;
        }

        public String getCar_style_length() {
            return car_style_length;
        }

        public void setCar_style_length(String car_style_length) {
            this.car_style_length = car_style_length;
        }

        public String getCard_number() {
            return card_number;
        }

        public void setCard_number(String card_number) {
            this.card_number = card_number;
        }

        public String getDr_price() {
            return dr_price;
        }

        public void setDr_price(String dr_price) {
            this.dr_price = dr_price;
        }

        public String getGoods_num() {
            return goods_num;
        }

        public void setGoods_num(String goods_num) {
            this.goods_num = goods_num;
        }

        public String getComplain_num() {
            return complain_num;
        }

        public void setComplain_num(String complain_num) {
            this.complain_num = complain_num;
        }
    }
}
