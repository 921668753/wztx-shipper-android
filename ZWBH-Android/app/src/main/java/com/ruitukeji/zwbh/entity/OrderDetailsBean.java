package com.ruitukeji.zwbh.entity;

/**
 * Created by Admin on 2017/7/11.
 */

public class OrderDetailsBean extends BaseResult<OrderDetailsBean.ResultBean> {


    public class ResultBean {
        /**
         * order_id : 66
         * status : photo
         * order_code : 2017081110010052
         * goods_name : 铜
         * weight : 20
         * org_city : 江苏省苏州市吴中区
         * dest_city : 河南省郑州市二七区
         * dest_receive_name : ck
         * dest_phone : 17051335257
         * dest_telphone :
         * dest_address_name : 河南省郑州市二七区郑州大学第一附属医院河医院区
         * dest_address_detail : 二楼
         * org_send_name : 睿途
         * org_phone : 17051335257
         * org_telphone :
         * org_address_name : 江苏省苏州市吴中区腾飞苏州创新园
         * org_address_detail :
         * usecar_time : 2017-08-11 08:48:06
         * send_time : 2017-08-11 08:48:39
         * arr_time : 2017-08-11 08:50:00
         * real_name : ck
         * phone : 17051335257
         * avatar : http://ot090bmn8.bkt.clouddn.com/0e8bc458ac2f13adbbcfe13bfa0a7535.jpg
         * card_number : 苏E·123456
         * policy_code : null
         * is_pay : 0
         * per_status : init
         * is_receipt : 1
         * system_price : 267325.67
         * mind_price : 0.00
         * final_price : 267325.67
         * effective_time : 0
         * remark :
         * org_address_maps : 120.733518,31.25411
         * dest_address_maps : 120.733518,31.25411
         * map_code : 31
         */

        private int order_id;
        private String status;
        private String order_code;
        private String goods_name;
        private String weight;
        private String org_city;
        private String dest_city;
        private String dest_receive_name;
        private String dest_phone;
        private String dest_telphone;
        private String dest_address_name;
        private String dest_address_detail;
        private String org_send_name;
        private String org_phone;
        private String org_telphone;
        private String org_address_name;
        private String org_address_detail;
        private String usecar_time;
        private String send_time;
        private String arr_time;
        private String real_name;
        private String phone;
        private String avatar;
        private String card_number;
        private String policy_code;
        private int is_pay;
        private String per_status;
        private int is_receipt;
        private String system_price;
        private String mind_price;
        private String final_price;
        private int effective_time;
        private String remark;
        private String org_address_maps;
        private String dest_address_maps;
        private String map_code;

        public int getOrder_id() {
            return order_id;
        }

        public void setOrder_id(int order_id) {
            this.order_id = order_id;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getOrder_code() {
            return order_code;
        }

        public void setOrder_code(String order_code) {
            this.order_code = order_code;
        }

        public String getGoods_name() {
            return goods_name;
        }

        public void setGoods_name(String goods_name) {
            this.goods_name = goods_name;
        }

        public String getWeight() {
            return weight;
        }

        public void setWeight(String weight) {
            this.weight = weight;
        }

        public String getOrg_city() {
            return org_city;
        }

        public void setOrg_city(String org_city) {
            this.org_city = org_city;
        }

        public String getDest_city() {
            return dest_city;
        }

        public void setDest_city(String dest_city) {
            this.dest_city = dest_city;
        }

        public String getDest_receive_name() {
            return dest_receive_name;
        }

        public void setDest_receive_name(String dest_receive_name) {
            this.dest_receive_name = dest_receive_name;
        }

        public String getDest_phone() {
            return dest_phone;
        }

        public void setDest_phone(String dest_phone) {
            this.dest_phone = dest_phone;
        }

        public String getDest_telphone() {
            return dest_telphone;
        }

        public void setDest_telphone(String dest_telphone) {
            this.dest_telphone = dest_telphone;
        }

        public String getDest_address_name() {
            return dest_address_name;
        }

        public void setDest_address_name(String dest_address_name) {
            this.dest_address_name = dest_address_name;
        }

        public String getDest_address_detail() {
            return dest_address_detail;
        }

        public void setDest_address_detail(String dest_address_detail) {
            this.dest_address_detail = dest_address_detail;
        }

        public String getOrg_send_name() {
            return org_send_name;
        }

        public void setOrg_send_name(String org_send_name) {
            this.org_send_name = org_send_name;
        }

        public String getOrg_phone() {
            return org_phone;
        }

        public void setOrg_phone(String org_phone) {
            this.org_phone = org_phone;
        }

        public String getOrg_telphone() {
            return org_telphone;
        }

        public void setOrg_telphone(String org_telphone) {
            this.org_telphone = org_telphone;
        }

        public String getOrg_address_name() {
            return org_address_name;
        }

        public void setOrg_address_name(String org_address_name) {
            this.org_address_name = org_address_name;
        }

        public String getOrg_address_detail() {
            return org_address_detail;
        }

        public void setOrg_address_detail(String org_address_detail) {
            this.org_address_detail = org_address_detail;
        }

        public String getUsecar_time() {
            return usecar_time;
        }

        public void setUsecar_time(String usecar_time) {
            this.usecar_time = usecar_time;
        }

        public String getSend_time() {
            return send_time;
        }

        public void setSend_time(String send_time) {
            this.send_time = send_time;
        }

        public String getArr_time() {
            return arr_time;
        }

        public void setArr_time(String arr_time) {
            this.arr_time = arr_time;
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

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getCard_number() {
            return card_number;
        }

        public void setCard_number(String card_number) {
            this.card_number = card_number;
        }

        public String getPolicy_code() {
            return policy_code;
        }

        public void setPolicy_code(String policy_code) {
            this.policy_code = policy_code;
        }

        public int getIs_pay() {
            return is_pay;
        }

        public void setIs_pay(int is_pay) {
            this.is_pay = is_pay;
        }

        public String getPer_status() {
            return per_status;
        }

        public void setPer_status(String per_status) {
            this.per_status = per_status;
        }

        public int getIs_receipt() {
            return is_receipt;
        }

        public void setIs_receipt(int is_receipt) {
            this.is_receipt = is_receipt;
        }

        public String getSystem_price() {
            return system_price;
        }

        public void setSystem_price(String system_price) {
            this.system_price = system_price;
        }

        public String getMind_price() {
            return mind_price;
        }

        public void setMind_price(String mind_price) {
            this.mind_price = mind_price;
        }

        public String getFinal_price() {
            return final_price;
        }

        public void setFinal_price(String final_price) {
            this.final_price = final_price;
        }

        public int getEffective_time() {
            return effective_time;
        }

        public void setEffective_time(int effective_time) {
            this.effective_time = effective_time;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getOrg_address_maps() {
            return org_address_maps;
        }

        public void setOrg_address_maps(String org_address_maps) {
            this.org_address_maps = org_address_maps;
        }

        public String getDest_address_maps() {
            return dest_address_maps;
        }

        public void setDest_address_maps(String dest_address_maps) {
            this.dest_address_maps = dest_address_maps;
        }

        public String getMap_code() {
            return map_code;
        }

        public void setMap_code(String map_code) {
            this.map_code = map_code;
        }
    }
}
