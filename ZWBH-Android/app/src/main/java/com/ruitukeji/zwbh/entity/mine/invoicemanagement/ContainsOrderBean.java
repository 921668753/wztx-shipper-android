package com.ruitukeji.zwbh.entity.mine.invoicemanagement;

import com.ruitukeji.zwbh.entity.BaseResult;

import java.util.List;

/**
 * Created by Administrator on 2018/1/4.
 */

public class ContainsOrderBean extends BaseResult<List<ContainsOrderBean.ResultBean>> {


    public class ResultBean {
        /**
         * id : 291
         * type : often
         * order_code : 2017123098545215
         * pay_time : 2017-09-30 16:40
         * clear_price : 5000.00
         * org_address_name : 江苏省苏州市昆山市花桥镇海星路两岸新天地
         * dest_address_name : 江苏省苏州市吴中区越溪镇南溪江路255号CF汽车设计工作室
         */

        private int id;
        private String type;
        private String order_code;
        private String pay_time;
        private String clear_price;
        private String org_address_name;
        private String dest_address_name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getOrder_code() {
            return order_code;
        }

        public void setOrder_code(String order_code) {
            this.order_code = order_code;
        }

        public String getPay_time() {
            return pay_time;
        }

        public void setPay_time(String pay_time) {
            this.pay_time = pay_time;
        }

        public String getClear_price() {
            return clear_price;
        }

        public void setClear_price(String clear_price) {
            this.clear_price = clear_price;
        }

        public String getOrg_address_name() {
            return org_address_name;
        }

        public void setOrg_address_name(String org_address_name) {
            this.org_address_name = org_address_name;
        }

        public String getDest_address_name() {
            return dest_address_name;
        }

        public void setDest_address_name(String dest_address_name) {
            this.dest_address_name = dest_address_name;
        }
    }
}
