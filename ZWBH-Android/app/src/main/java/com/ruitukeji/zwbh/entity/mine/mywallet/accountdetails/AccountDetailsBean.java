package com.ruitukeji.zwbh.entity.mine.mywallet.accountdetails;

import com.ruitukeji.zwbh.entity.BaseResult;

import java.util.List;

/**
 * Created by Administrator on 2018/1/18.
 */

public class AccountDetailsBean extends BaseResult<AccountDetailsBean.ResultBean> {


    public class ResultBean {
        /**
         * list : [{"id":306,"create_at":1516072076,"order_code":"20171233","final_price":"0.00"},{"id":307,"create_at":1516072145,"order_code":"20171234","final_price":"0.00"},{"id":308,"create_at":1516072169,"order_code":"20171235","final_price":"0.00"}]
         * page : 1
         * pageSize : 10
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
             * id : 306
             * create_at : 1516072076
             * order_code : 20171233
             * final_price : 0.00
             */

            private int id;
            private String create_at;
            private String order_code;
            private String final_price;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getCreate_at() {
                return create_at;
            }

            public void setCreate_at(String create_at) {
                this.create_at = create_at;
            }

            public String getOrder_code() {
                return order_code;
            }

            public void setOrder_code(String order_code) {
                this.order_code = order_code;
            }

            public String getFinal_price() {
                return final_price;
            }

            public void setFinal_price(String final_price) {
                this.final_price = final_price;
            }
        }
    }
}
