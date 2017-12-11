package com.ruitukeji.zwbh.entity;

import java.util.List;

/**
 * Created by Admin on 2017/8/30.
 */

public class GoodsBean extends BaseResult<GoodsBean.ResultBean> {


    public class ResultBean {
        /**
         * list : [{"goods_id":220,"org_city":"江苏省苏州市吴中区","dest_city":"江苏省苏州市姑苏区","weight":"10","goods_name":"ck","status":"quote","car_style_length":"4.2米","car_style_type":"高栏","is_quote":0}]
         * page : 1
         * pageSize : 20
         * dataTotal : 1
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
             * goods_id : 220
             * org_city : 江苏省苏州市吴中区
             * dest_city : 江苏省苏州市姑苏区
             * weight : 10
             * goods_name : ck
             * status : quote
             * car_style_length : 4.2米
             * car_style_type : 高栏
             * is_quote : 0
             */

            private int goods_id;
            private String org_city;
            private String dest_city;
            private String weight;
            private String goods_name;
            private String status;
            private String car_style_length;
            private String car_style_type;
            private int is_quote;

            public int getGoods_id() {
                return goods_id;
            }

            public void setGoods_id(int goods_id) {
                this.goods_id = goods_id;
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

            public String getWeight() {
                return weight;
            }

            public void setWeight(String weight) {
                this.weight = weight;
            }

            public String getGoods_name() {
                return goods_name;
            }

            public void setGoods_name(String goods_name) {
                this.goods_name = goods_name;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getCar_style_length() {
                return car_style_length;
            }

            public void setCar_style_length(String car_style_length) {
                this.car_style_length = car_style_length;
            }

            public String getCar_style_type() {
                return car_style_type;
            }

            public void setCar_style_type(String car_style_type) {
                this.car_style_type = car_style_type;
            }

            public int getIs_quote() {
                return is_quote;
            }

            public void setIs_quote(int is_quote) {
                this.is_quote = is_quote;
            }
        }
    }
}
