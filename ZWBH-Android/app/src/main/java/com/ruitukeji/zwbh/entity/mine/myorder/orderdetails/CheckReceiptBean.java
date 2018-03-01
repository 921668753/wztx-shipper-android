package com.ruitukeji.zwbh.entity.mine.myorder.orderdetails;

import com.ruitukeji.zwbh.entity.BaseResult;

/**
 * Created by Administrator on 2018/3/1.
 */

public class CheckReceiptBean extends BaseResult<CheckReceiptBean.ResultBean> {


    public class ResultBean {
        /**
         * id : 74
         * num : 123456
         * company : 京东
         * create_at : 1519814037
         * update_at : 1519814037
         * user_id : 168
         * g_id : 464
         * cargo_man : 王牧师
         * cargo_tel : 15651720805
         * cargo_address : 江苏省苏州市吴中区
         * cargo_address_detail : 独墅湖教堂
         * cargo_is_express : 1
         */

        private int id;
        private String num;
        private String company;
        private int create_at;
        private int update_at;
        private int user_id;
        private int g_id;
        private String cargo_man;
        private String cargo_tel;
        private String cargo_address;
        private String cargo_address_detail;
        private int cargo_is_express;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getNum() {
            return num;
        }

        public void setNum(String num) {
            this.num = num;
        }

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public int getCreate_at() {
            return create_at;
        }

        public void setCreate_at(int create_at) {
            this.create_at = create_at;
        }

        public int getUpdate_at() {
            return update_at;
        }

        public void setUpdate_at(int update_at) {
            this.update_at = update_at;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public int getG_id() {
            return g_id;
        }

        public void setG_id(int g_id) {
            this.g_id = g_id;
        }

        public String getCargo_man() {
            return cargo_man;
        }

        public void setCargo_man(String cargo_man) {
            this.cargo_man = cargo_man;
        }

        public String getCargo_tel() {
            return cargo_tel;
        }

        public void setCargo_tel(String cargo_tel) {
            this.cargo_tel = cargo_tel;
        }

        public String getCargo_address() {
            return cargo_address;
        }

        public void setCargo_address(String cargo_address) {
            this.cargo_address = cargo_address;
        }

        public String getCargo_address_detail() {
            return cargo_address_detail;
        }

        public void setCargo_address_detail(String cargo_address_detail) {
            this.cargo_address_detail = cargo_address_detail;
        }

        public int getCargo_is_express() {
            return cargo_is_express;
        }

        public void setCargo_is_express(int cargo_is_express) {
            this.cargo_is_express = cargo_is_express;
        }
    }
}
