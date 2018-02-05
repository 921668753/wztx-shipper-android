package com.ruitukeji.zwbh.entity;

import java.util.List;

/**
 * Created by Admin on 2017/7/11.
 */

public class OrderBean extends BaseResult<OrderBean.ResultBean> {


    public class ResultBean {
        /**
         * list : [{"is_quote":0,"dr_phone":"17509148610","card_number":"苏123456","avatar":"http://ot090bmn8.bkt.clouddn.com/c44ba5d972343ae2232c627818dd502a.jpg","is_abnormal":0,"map_code":"80","appoint_at":"2017-12-29 15:53","create_at":"2017-12-29 15:53","system_price":"70.00","final_price":"70.00","order_id":291,"type":"often","org_city":"江苏省苏州市昆山市花桥镇海星路两岸新天地Fhjk","arr_org_time":"2018-02-02 13:18:47","arr_time":"1970-01-01 08:00","send_time":"2018-02-05 10:33","org_address_maps":"121.062752,31.320404","dest_address_maps":"120.600931,31.215254","dest_city":"江苏省苏州市吴中区越溪镇南溪江路255号CF汽车设计工作室Dghj","status":"distribute","dr_name":"刘艳锋","order_code":"2017123098545215","mind_price":"0.00","is_cancel":0}]
         * page : 1
         * pageSize : 10
         * dataTotal : 2
         * pageTotal : 1
         */

        private int page;
        private int pageSize;
        private int dataTotal;
        private int pageTotal;
        private List<ListBean> list;

        public int getPage() {
            return page;
        }

        public void setPage(int page) {
            this.page = page;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public int getDataTotal() {
            return dataTotal;
        }

        public void setDataTotal(int dataTotal) {
            this.dataTotal = dataTotal;
        }

        public int getPageTotal() {
            return pageTotal;
        }

        public void setPageTotal(int pageTotal) {
            this.pageTotal = pageTotal;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public class ListBean {
            /**
             * is_quote : 0
             * dr_phone : 17509148610
             * card_number : 苏123456
             * avatar : http://ot090bmn8.bkt.clouddn.com/c44ba5d972343ae2232c627818dd502a.jpg
             * is_abnormal : 0
             * map_code : 80
             * appoint_at : 2017-12-29 15:53
             * create_at : 2017-12-29 15:53
             * system_price : 70.00
             * final_price : 70.00
             * order_id : 291
             * type : often
             * org_city : 江苏省苏州市昆山市花桥镇海星路两岸新天地Fhjk
             * arr_org_time : 2018-02-02 13:18:47
             * arr_time : 1970-01-01 08:00
             * send_time : 2018-02-05 10:33
             * org_address_maps : 121.062752,31.320404
             * dest_address_maps : 120.600931,31.215254
             * dest_city : 江苏省苏州市吴中区越溪镇南溪江路255号CF汽车设计工作室Dghj
             * status : distribute
             * dr_name : 刘艳锋
             * order_code : 2017123098545215
             * mind_price : 0.00
             * is_cancel : 0
             */

            private int is_quote;
            private String dr_phone;
            private String card_number;
            private String avatar;
            private int is_abnormal;
            private String map_code;
            private String appoint_at;
            private String create_at;
            private String system_price;
            private String final_price;
            private int order_id;
            private String type;
            private String org_city;
            private String arr_org_time;
            private String arr_time;
            private String send_time;
            private String org_address_maps;
            private String dest_address_maps;
            private String dest_city;
            private String status;
            private String dr_name;
            private String order_code;
            private String mind_price;
            private int is_cancel;
            private String per_status;

            public int getIs_quote() {
                return is_quote;
            }

            public void setIs_quote(int is_quote) {
                this.is_quote = is_quote;
            }

            public String getDr_phone() {
                return dr_phone;
            }

            public void setDr_phone(String dr_phone) {
                this.dr_phone = dr_phone;
            }

            public String getCard_number() {
                return card_number;
            }

            public void setCard_number(String card_number) {
                this.card_number = card_number;
            }

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public int getIs_abnormal() {
                return is_abnormal;
            }

            public void setIs_abnormal(int is_abnormal) {
                this.is_abnormal = is_abnormal;
            }

            public String getMap_code() {
                return map_code;
            }

            public void setMap_code(String map_code) {
                this.map_code = map_code;
            }

            public String getAppoint_at() {
                return appoint_at;
            }

            public void setAppoint_at(String appoint_at) {
                this.appoint_at = appoint_at;
            }

            public String getCreate_at() {
                return create_at;
            }

            public void setCreate_at(String create_at) {
                this.create_at = create_at;
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

            public int getOrder_id() {
                return order_id;
            }

            public void setOrder_id(int order_id) {
                this.order_id = order_id;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getOrg_city() {
                return org_city;
            }

            public void setOrg_city(String org_city) {
                this.org_city = org_city;
            }

            public String getArr_org_time() {
                return arr_org_time;
            }

            public void setArr_org_time(String arr_org_time) {
                this.arr_org_time = arr_org_time;
            }

            public String getArr_time() {
                return arr_time;
            }

            public void setArr_time(String arr_time) {
                this.arr_time = arr_time;
            }

            public String getSend_time() {
                return send_time;
            }

            public void setSend_time(String send_time) {
                this.send_time = send_time;
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

            public String getDest_city() {
                return dest_city;
            }

            public void setDest_city(String dest_city) {
                this.dest_city = dest_city;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getDr_name() {
                return dr_name;
            }

            public void setDr_name(String dr_name) {
                this.dr_name = dr_name;
            }

            public String getOrder_code() {
                return order_code;
            }

            public void setOrder_code(String order_code) {
                this.order_code = order_code;
            }

            public String getMind_price() {
                return mind_price;
            }

            public void setMind_price(String mind_price) {
                this.mind_price = mind_price;
            }

            public int getIs_cancel() {
                return is_cancel;
            }

            public void setIs_cancel(int is_cancel) {
                this.is_cancel = is_cancel;
            }

            public String getPer_status() {
                return per_status;
            }

            public void setPer_status(String per_status) {
                this.per_status = per_status;
            }
        }
    }
}
