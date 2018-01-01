package com.ruitukeji.zwbh.entity.mine.addressmanagement;

import com.ruitukeji.zwbh.entity.BaseResult;

/**
 * Created by Administrator on 2018/1/1.
 */

public class NewAddAddress1Bean extends BaseResult<NewAddAddress1Bean.ResultBean> {


    /**
     * code : 2000
     * msg : 查询成功
     * result : {"id":10,"user_id":80,"address_maps":"120.646435,31.248493","city":"江苏省苏州市吴中区","address_name":"江苏省苏州市吴中区石湖东路25号附近FG造型(钱家花园店)","address_detail":"Bobby","client":"Hhh","client_name":"Vbh","phone":"15800000000","telphone":null,"is_default":"1","type":"0"}
     */

    public class ResultBean {
        /**
         * id : 10
         * user_id : 80
         * address_maps : 120.646435,31.248493
         * city : 江苏省苏州市吴中区
         * address_name : 江苏省苏州市吴中区石湖东路25号附近FG造型(钱家花园店)
         * address_detail : Bobby
         * client : Hhh
         * client_name : Vbh
         * phone : 15800000000
         * telphone : null
         * is_default : 1
         * type : 0
         */

        private int id;
        private int user_id;
        private String address_maps;
        private String city;
        private String address_name;
        private String address_detail;
        private String client;
        private String client_name;
        private String phone;
        private String telphone;
        private int is_default;
        private int type;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public String getAddress_maps() {
            return address_maps;
        }

        public void setAddress_maps(String address_maps) {
            this.address_maps = address_maps;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getAddress_name() {
            return address_name;
        }

        public void setAddress_name(String address_name) {
            this.address_name = address_name;
        }

        public String getAddress_detail() {
            return address_detail;
        }

        public void setAddress_detail(String address_detail) {
            this.address_detail = address_detail;
        }

        public String getClient() {
            return client;
        }

        public void setClient(String client) {
            this.client = client;
        }

        public String getClient_name() {
            return client_name;
        }

        public void setClient_name(String client_name) {
            this.client_name = client_name;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getTelphone() {
            return telphone;
        }

        public void setTelphone(String telphone) {
            this.telphone = telphone;
        }

        public int getIs_default() {
            return is_default;
        }

        public void setIs_default(int is_default) {
            this.is_default = is_default;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }
}
