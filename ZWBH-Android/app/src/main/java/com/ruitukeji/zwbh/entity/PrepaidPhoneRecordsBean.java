package com.ruitukeji.zwbh.entity;

import java.util.List;

/**
 * Created by Admin on 2017/7/25.
 */

public class PrepaidPhoneRecordsBean extends BaseResult<PrepaidPhoneRecordsBean.ResultBean> {


    public class ResultBean {

        private int page;
        private int pageSize;
        private int pageTotal;
        private int dataTotal;
        private List<ListBean> list;

        public int getDataTotal() {
            return dataTotal;
        }

        public void setDataTotal(int dataTotal) {
            this.dataTotal = dataTotal;
        }

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
             * avatar : 2222
             * name : 2222
             * bonus : 2222
             */

            private String real_amount;
            private int pay_way;
            private String pay_time;
            private int pay_status;

            public String getReal_amount() {
                return real_amount;
            }

            public void setReal_amount(String real_amount) {
                this.real_amount = real_amount;
            }

            public int getPay_way() {
                return pay_way;
            }

            public void setPay_way(int pay_way) {
                this.pay_way = pay_way;
            }

            public String getPay_time() {
                return pay_time;
            }

            public void setPay_time(String pay_time) {
                this.pay_time = pay_time;
            }

            public int getPay_status() {
                return pay_status;
            }

            public void setPay_status(int pay_status) {
                this.pay_status = pay_status;
            }
        }
    }


}
