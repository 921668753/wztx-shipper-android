package com.ruitukeji.zwbh.entity.mine.abnormalrecords;

import com.ruitukeji.zwbh.entity.BaseResult;

/**
 * Created by Administrator on 2018/1/8.
 */

public class AbnormalSituationBean extends BaseResult<AbnormalSituationBean.ResultBean> {


    public class ResultBean {
        /**
         * id : 1
         * sp_id : 91
         * dr_id : 59
         * dr_name : 曹大福司机
         * card_number : 苏A123654
         * car_length : 2.5米
         * car_type : 货车
         * g_time : 2018-01-08 08:46:30
         * g_type : often
         * order_code : 13759842654
         * start_address : 江苏省苏州市昆山市花桥镇海星路5号两岸新天地
         * end_address : 陕西省商洛市洛南县景兴村
         * create_time : 2017-12-30 18:42:17
         * update_time : 2017-12-22 12:53:12
         * content : 这是异常的内容
         * place : 异常地点
         * abnormal_time : 1514630537
         * reason : 异常原因
         * goods_name : 铁块
         */

        private int id;
        private int sp_id;
        private int dr_id;
        private String dr_name;
        private String card_number;
        private String car_length;
        private String car_type;
        private String g_time;
        private String g_type;
        private String order_code;
        private String start_address;
        private String end_address;
        private String create_time;
        private String update_time;
        private String content;
        private String place;
        private String abnormal_time;
        private String reason;
        private String goods_name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getSp_id() {
            return sp_id;
        }

        public void setSp_id(int sp_id) {
            this.sp_id = sp_id;
        }

        public int getDr_id() {
            return dr_id;
        }

        public void setDr_id(int dr_id) {
            this.dr_id = dr_id;
        }

        public String getDr_name() {
            return dr_name;
        }

        public void setDr_name(String dr_name) {
            this.dr_name = dr_name;
        }

        public String getCard_number() {
            return card_number;
        }

        public void setCard_number(String card_number) {
            this.card_number = card_number;
        }

        public String getCar_length() {
            return car_length;
        }

        public void setCar_length(String car_length) {
            this.car_length = car_length;
        }

        public String getCar_type() {
            return car_type;
        }

        public void setCar_type(String car_type) {
            this.car_type = car_type;
        }

        public String getG_time() {
            return g_time;
        }

        public void setG_time(String g_time) {
            this.g_time = g_time;
        }

        public String getG_type() {
            return g_type;
        }

        public void setG_type(String g_type) {
            this.g_type = g_type;
        }

        public String getOrder_code() {
            return order_code;
        }

        public void setOrder_code(String order_code) {
            this.order_code = order_code;
        }

        public String getStart_address() {
            return start_address;
        }

        public void setStart_address(String start_address) {
            this.start_address = start_address;
        }

        public String getEnd_address() {
            return end_address;
        }

        public void setEnd_address(String end_address) {
            this.end_address = end_address;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(String update_time) {
            this.update_time = update_time;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getPlace() {
            return place;
        }

        public void setPlace(String place) {
            this.place = place;
        }

        public String getAbnormal_time() {
            return abnormal_time;
        }

        public void setAbnormal_time(String abnormal_time) {
            this.abnormal_time = abnormal_time;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }

        public String getGoods_name() {
            return goods_name;
        }

        public void setGoods_name(String goods_name) {
            this.goods_name = goods_name;
        }
    }
}
