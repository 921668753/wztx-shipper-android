package com.ruitukeji.zwbh.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Admin on 2017/7/11.
 */

public class OrderBean extends BaseResult<OrderBean.ResultBean> {


    /**
     * code : 2000
     * result : {"list":[{"is_quote":0,"is_abnormal":0,"appoint_at":"2018-01-16 11:09","create_at":"2018-01-16 11:09","system_price":"849.68","final_price":"849.68","order_id":308,"type":"often","org_city":"江苏省苏州市昆山市","arr_org_time":"1970-01-01 08:00:00","arr_time":"1970-01-01 08:00","send_time":"1970-01-01 08:00","dest_city":"江苏省苏州市昆山市","status":"quote"}],"page":1,"pageSize":10,"dataTotal":4,"pageTotal":1}
     */


    public class ResultBean {
        /**
         * list : [{"is_quote":0,"is_abnormal":0,"appoint_at":"2018-01-16 11:09","create_at":"2018-01-16 11:09","system_price":"849.68","final_price":"849.68","order_id":308,"type":"often","org_city":"江苏省苏州市昆山市","arr_org_time":"1970-01-01 08:00:00","arr_time":"1970-01-01 08:00","send_time":"1970-01-01 08:00","dest_city":"江苏省苏州市昆山市","status":"quote"}]
         * page : 1
         * pageSize : 10
         * dataTotal : 4
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
             * is_abnormal : 0
             * appoint_at : 2018-01-16 11:09
             * create_at : 2018-01-16 11:09
             * system_price : 849.68
             * final_price : 849.68
             * order_id : 308
             * type : often
             * org_city : 江苏省苏州市昆山市
             * arr_org_time : 1970-01-01 08:00:00
             * arr_time : 1970-01-01 08:00
             * send_time : 1970-01-01 08:00
             * dest_city : 江苏省苏州市昆山市
             * status : quote
             */

            private int is_quote;
            private int is_abnormal;
            private String appoint_at;
            private String create_at;
            private String system_price;
            private String final_price;
            private int order_id;
            private String type;
            private String org_city;
            private String order_code;
            private String arr_org_time;
            private String arr_time;
            private String send_time;
            private String dest_city;
            private String status;

            public int getIs_quote() {
                return is_quote;
            }

            public void setIs_quote(int is_quote) {
                this.is_quote = is_quote;
            }

            public int getIs_abnormal() {
                return is_abnormal;
            }

            public void setIs_abnormal(int is_abnormal) {
                this.is_abnormal = is_abnormal;
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

            public String getOrder_code() {
                return order_code;
            }

            public void setOrder_code(String order_code) {
                this.order_code = order_code;
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
        }
    }
}
