package com.ruitukeji.zwbh.entity;

import java.util.List;

/**
 * Created by Admin on 2017/7/11.
 */

public class QuotationListBean extends BaseResult<QuotationListBean.ResultBean> {


    public class ResultBean {
        /**
         * list : [{"id":80,"dr_id":6,"avatar":"","score":5,"car_style_type":"厢式","car_style_length":"4.2米","card_number":"苏EAE86","dr_price":"1000.25"}]
         * page : 1
         * pageSize : 1000
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
             * id : 80
             * dr_id : 6
             * avatar :
             * score : 5
             * car_style_type : 厢式
             * car_style_length : 4.2米
             * card_number : 苏EAE86
             * dr_price : 1000.25
             */

            private int id;
            private int dr_id;
            private String avatar;
            private int score;
            private String car_style_type;
            private String car_style_length;
            private String card_number;
            private String dr_price;
            private boolean is_selected;

            public boolean is_selected() {
                return is_selected;
            }

            public void setIs_selected(boolean is_selected) {
                this.is_selected = is_selected;
            }

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

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public int getScore() {
                return score;
            }

            public void setScore(int score) {
                this.score = score;
            }

            public String getCar_style_type() {
                return car_style_type;
            }

            public void setCar_style_type(String car_style_type) {
                this.car_style_type = car_style_type;
            }

            public String getCar_style_length() {
                return car_style_length;
            }

            public void setCar_style_length(String car_style_length) {
                this.car_style_length = car_style_length;
            }

            public String getCard_number() {
                return card_number;
            }

            public void setCard_number(String card_number) {
                this.card_number = card_number;
            }

            public String getDr_price() {
                return dr_price;
            }

            public void setDr_price(String dr_price) {
                this.dr_price = dr_price;
            }
        }
    }
}
