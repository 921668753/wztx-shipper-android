package com.ruitukeji.zwbh.entity.mine.drivermanagement;

import com.ruitukeji.zwbh.entity.BaseResult;

import java.util.List;

/**
 * Created by Administrator on 2018/1/3.
 */

public class DriverManagementBean extends BaseResult<DriverManagementBean.ResultBean> {


    public class ResultBean {
        /**
         * list : [{"id":1,"dr_id":59,"dr_name":"戴师傅","card_code":"苏EA6670","car_length":4.2,"car_type":"箱式火车","is_black":"0","dr_tel":"135****545"},{"id":2,"dr_id":58,"dr_name":"c师傅","card_code":"苏EA9528","car_length":3.6,"car_type":"箱式火车","is_black":"0","dr_tel":"187****545"}]
         * page : 1
         * pageSize : 20
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
             * id : 1
             * dr_id : 59
             * dr_name : 戴师傅
             * card_code : 苏EA6670
             * car_length : 4.2
             * car_type : 箱式火车
             * is_black : 0
             * dr_tel : 135****545
             */

            private int id;
            private int dr_id;
            private String dr_name;
            private String card_code;
            private String car_length;
            private String car_type;
            private int is_black;
            private String dr_tel;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getDr_id() {
                return dr_id;
            }

            public void setDr_id(int dr_id) {
                this.dr_id = dr_id;
            }

            public String getDr_name() {
                return dr_name;
            }

            public void setDr_name(String dr_name) {
                this.dr_name = dr_name;
            }

            public String getCard_code() {
                return card_code;
            }

            public void setCard_code(String card_code) {
                this.card_code = card_code;
            }

            public String getCar_length() {
                return car_length;
            }

            public void setCar_length(String car_length) {
                this.car_length = car_length;
            }

            public String getCar_type() {
                return car_type;
            }

            public void setCar_type(String car_type) {
                this.car_type = car_type;
            }

            public int getIs_black() {
                return is_black;
            }

            public void setIs_black(int is_black) {
                this.is_black = is_black;
            }

            public String getDr_tel() {
                return dr_tel;
            }

            public void setDr_tel(String dr_tel) {
                this.dr_tel = dr_tel;
            }
        }
    }
}
