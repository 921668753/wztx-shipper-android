package com.ruitukeji.zwbh.entity;

/**
 * Created by Admin on 2017/7/11.
 */

public class OrderDetailsBean extends BaseResult<OrderDetailsBean.ResultBean> {


    public class ResultBean {
        /**
         * dr_id : 61
         * car_length_info : 4.2米
         * car_style_info : 箱车
         * map_code : 164
         * dr_tel :
         * appoint_at : 2018-01-20 12:57:30
         * order_code : 2018012097491005
         * system_price : 1335.10
         * final_price : 1335.10
         * real_name : 骚年
         * goods_name : 测i货物
         * weight : 2吨
         * volume : 2立方米
         * is_cargo_receipt : 0
         * org_address_name : 江苏省苏州市昆山市花桥镇昆山市仁之源劳务服务有限公司两岸新天地
         * org_address_detail : 测试
         * org_phone : 13748754275
         * org_telphone :
         * org_send_name : 测试人
         * dest_address_name : 江苏省苏州市常熟市尚湖镇练塘道林路常熟巨达建材
         * dest_address_detail : 啊哈
         * dest_phone : 13748754248
         * dest_telphone :
         * dest_receive_name : 测试人
         * car_style_type : 箱车
         * car_style_type_id : 1004
         * car_style_length : 12.5米
         * car_style_length_id : 1014
         * is_driver_dock : 1
         * usecar_time : 4小时2分钟
         * spot : 2
         * spot_cost : 0
         * dr_name :
         * card_number :
         * org_address_maps : 121.062544,31.320191
         * dest_address_maps : 121.062544,31.320191
         * status : quote
         * is_cancel : 0
         * type : often
         * create_at : 2018-01-20 12:57:30
         * is_quote : 0
         * is_collect : 0
         * org_send_client : 测试人
         * dest_receive_client : 测试人
         */

        private int dr_id;
        private String car_length_info;
        private String car_style_info;
        private String map_code;
        private String dr_tel;
        private String appoint_at;
        private String order_code;
        private String system_price;
        private String final_price;
        private String real_name;
        private String goods_name;
        private String weight;
        private String volume;
        private int is_cargo_receipt;
        private String org_address_name;
        private String org_address_detail;
        private String org_phone;
        private String org_telphone;
        private String org_send_name;
        private String dest_address_name;
        private String dest_address_detail;
        private String dest_phone;
        private String dest_telphone;
        private String dest_receive_name;
        private String car_style_type;
        private int car_style_type_id;
        private String car_style_length;
        private int car_style_length_id;
        private int is_driver_dock;
        private String usecar_time;
        private String spot;
        private String spot_cost;
        private String dr_name;
        private String card_number;
        private String org_address_maps;
        private String dest_address_maps;
        private String status;
        private int is_cancel;
        private String type;
        private String create_at;
        private int is_quote;
        private int is_collect;
        private String org_send_client;
        private String dest_receive_client;

        public int getDr_id() {
            return dr_id;
        }

        public void setDr_id(int dr_id) {
            this.dr_id = dr_id;
        }

        public String getCar_length_info() {
            return car_length_info;
        }

        public void setCar_length_info(String car_length_info) {
            this.car_length_info = car_length_info;
        }

        public String getCar_style_info() {
            return car_style_info;
        }

        public void setCar_style_info(String car_style_info) {
            this.car_style_info = car_style_info;
        }

        public String getMap_code() {
            return map_code;
        }

        public void setMap_code(String map_code) {
            this.map_code = map_code;
        }

        public String getDr_tel() {
            return dr_tel;
        }

        public void setDr_tel(String dr_tel) {
            this.dr_tel = dr_tel;
        }

        public String getAppoint_at() {
            return appoint_at;
        }

        public void setAppoint_at(String appoint_at) {
            this.appoint_at = appoint_at;
        }

        public String getOrder_code() {
            return order_code;
        }

        public void setOrder_code(String order_code) {
            this.order_code = order_code;
        }

        public String getSystem_price() {
            return system_price;
        }

        public void setSystem_price(String system_price) {
            this.system_price = system_price;
        }

        public String getFinal_price() {
            return final_price;
        }

        public void setFinal_price(String final_price) {
            this.final_price = final_price;
        }

        public String getReal_name() {
            return real_name;
        }

        public void setReal_name(String real_name) {
            this.real_name = real_name;
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

        public String getVolume() {
            return volume;
        }

        public void setVolume(String volume) {
            this.volume = volume;
        }

        public int getIs_cargo_receipt() {
            return is_cargo_receipt;
        }

        public void setIs_cargo_receipt(int is_cargo_receipt) {
            this.is_cargo_receipt = is_cargo_receipt;
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

        public String getOrg_send_name() {
            return org_send_name;
        }

        public void setOrg_send_name(String org_send_name) {
            this.org_send_name = org_send_name;
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

        public String getDest_receive_name() {
            return dest_receive_name;
        }

        public void setDest_receive_name(String dest_receive_name) {
            this.dest_receive_name = dest_receive_name;
        }

        public String getCar_style_type() {
            return car_style_type;
        }

        public void setCar_style_type(String car_style_type) {
            this.car_style_type = car_style_type;
        }

        public int getCar_style_type_id() {
            return car_style_type_id;
        }

        public void setCar_style_type_id(int car_style_type_id) {
            this.car_style_type_id = car_style_type_id;
        }

        public String getCar_style_length() {
            return car_style_length;
        }

        public void setCar_style_length(String car_style_length) {
            this.car_style_length = car_style_length;
        }

        public int getCar_style_length_id() {
            return car_style_length_id;
        }

        public void setCar_style_length_id(int car_style_length_id) {
            this.car_style_length_id = car_style_length_id;
        }

        public int getIs_driver_dock() {
            return is_driver_dock;
        }

        public void setIs_driver_dock(int is_driver_dock) {
            this.is_driver_dock = is_driver_dock;
        }

        public String getUsecar_time() {
            return usecar_time;
        }

        public void setUsecar_time(String usecar_time) {
            this.usecar_time = usecar_time;
        }

        public String getSpot() {
            return spot;
        }

        public void setSpot(String spot) {
            this.spot = spot;
        }

        public String getSpot_cost() {
            return spot_cost;
        }

        public void setSpot_cost(String spot_cost) {
            this.spot_cost = spot_cost;
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

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public int getIs_cancel() {
            return is_cancel;
        }

        public void setIs_cancel(int is_cancel) {
            this.is_cancel = is_cancel;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getCreate_at() {
            return create_at;
        }

        public void setCreate_at(String create_at) {
            this.create_at = create_at;
        }

        public int getIs_quote() {
            return is_quote;
        }

        public void setIs_quote(int is_quote) {
            this.is_quote = is_quote;
        }

        public int getIs_collect() {
            return is_collect;
        }

        public void setIs_collect(int is_collect) {
            this.is_collect = is_collect;
        }

        public String getOrg_send_client() {
            return org_send_client;
        }

        public void setOrg_send_client(String org_send_client) {
            this.org_send_client = org_send_client;
        }

        public String getDest_receive_client() {
            return dest_receive_client;
        }

        public void setDest_receive_client(String dest_receive_client) {
            this.dest_receive_client = dest_receive_client;
        }
    }
}
