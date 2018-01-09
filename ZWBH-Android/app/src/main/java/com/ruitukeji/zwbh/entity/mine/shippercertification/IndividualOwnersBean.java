package com.ruitukeji.zwbh.entity.mine.shippercertification;

import com.ruitukeji.zwbh.entity.BaseResult;

/**
 * Created by Admin on 2017/7/11.
 */

public class IndividualOwnersBean extends BaseResult<IndividualOwnersBean.ResultBean> {


    public class ResultBean {
        /**
         * auth_status : qqqq
         * auth_info : qqqq
         * real_name : qqqq
         * phone : qqqq
         * identity : qqqq
         * sex : 1
         * front_pic : qqqq
         * back_pic : qqqq
         */

        private String auth_status;
        private String auth_info; //失败原因
        private String real_name;
        private String phone;
        private String identity;
        private int sex;
        private String hold_pic;
        private String front_pic;
        private String back_pic;
        private String pic_time;

        public String getAuth_status() {
            return auth_status;
        }

        public void setAuth_status(String auth_status) {
            this.auth_status = auth_status;
        }

        public String getAuth_info() {
            return auth_info;
        }

        public void setAuth_info(String auth_info) {
            this.auth_info = auth_info;
        }

        public String getReal_name() {
            return real_name;
        }

        public void setReal_name(String real_name) {
            this.real_name = real_name;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getIdentity() {
            return identity;
        }

        public void setIdentity(String identity) {
            this.identity = identity;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public String getHold_pic() {
            return hold_pic;
        }

        public void setHold_pic(String hold_pic) {
            this.hold_pic = hold_pic;
        }

        public String getFront_pic() {
            return front_pic;
        }

        public void setFront_pic(String front_pic) {
            this.front_pic = front_pic;
        }

        public String getBack_pic() {
            return back_pic;
        }

        public void setBack_pic(String back_pic) {
            this.back_pic = back_pic;
        }

        public String getPic_time() {
            return pic_time;
        }

        public void setPic_time(String pic_time) {
            this.pic_time = pic_time;
        }
    }
}
