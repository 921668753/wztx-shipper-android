package com.ruitukeji.zwbh.entity;

import java.util.List;

/**
 * Created by Admin on 2017/7/11.
 */

public class OrderBean extends BaseResult<OrderBean.ResultBean> {


    public class ResultBean {
        /**
         * list : [{"order_id":33,"org_city":"江苏省苏州市吴中区","goods_id":63,"dest_city":"江苏省苏州市吴中区","weight":"2","goods_name":"电子","status":"quote","car_style_length":"6米","car_style_type":"中卡"},{"order_id":32,"org_city":"江苏省苏州市吴中区","goods_id":62,"dest_city":"河南省郑州市新郑市","weight":"50","goods_name":"金属","status":"distribute","car_style_length":"5.2米","car_style_type":"平板"},{"order_id":30,"org_city":"江苏省苏州市吴中区","goods_id":41,"dest_city":"辽宁省沈阳市沈河区","weight":"1","goods_name":"电子","status":"quoted","car_style_length":"5.2米","car_style_type":"小卡"}]
         * page : 1
         * pageSize : 20
         * dataTotal : 3
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
             * order_id : 33
             * org_city : 江苏省苏州市吴中区
             * goods_id : 63
             * dest_city : 江苏省苏州市吴中区
             * weight : 2
             * goods_name : 电子
             * status : quote
             * car_style_length : 6米
             * car_style_type : 中卡
             */

            private int order_id;
            private String org_city;
            private int goods_id;
            private String dest_city;
            private String weight;
            private String goods_name;
            private String status;
            private String car_style_length;
            private String car_style_type;

            public int getOrder_id() {
                return order_id;
            }

            public void setOrder_id(int order_id) {
                this.order_id = order_id;
            }

            public String getOrg_city() {
                return org_city;
            }

            public void setOrg_city(String org_city) {
                this.org_city = org_city;
            }

            public int getGoods_id() {
                return goods_id;
            }

            public void setGoods_id(int goods_id) {
                this.goods_id = goods_id;
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
        }
    }
}
