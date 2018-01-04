package com.ruitukeji.zwbh.entity.mine.invoicemanagement;

import com.ruitukeji.zwbh.entity.BaseResult;

import java.util.List;

/**
 * Created by Administrator on 2018/1/4.
 */

public class BillingRecordsBean extends BaseResult<BillingRecordsBean.ResultBean> {


    public class ResultBean {
        /**
         * list : [{"id":13,"invoice_type":"普通发票","create":"2018-01-03 18:14:30","money":"15000.00","is_invoice_status":"待审核"},{"id":13,"invoice_type":"普通发票","create":"2018-01-03 18:14:30","money":"15000.00","is_invoice_status":"待审核"},{"id":14,"invoice_type":"普通发票","create":"2018-01-04 10:13:14","money":"5000.00","is_invoice_status":"待审核"},{"id":13,"invoice_type":"普通发票","create":"2018-01-03 18:14:30","money":"15000.00","is_invoice_status":"待审核"},{"id":15,"invoice_type":"普通发票","create":"2018-01-04 13:16:07","money":"5000.00","is_invoice_status":"待审核"}]
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
             * id : 13
             * invoice_type : 普通发票
             * create : 2018-01-03 18:14:30
             * money : 15000.00
             * is_invoice_status : 待审核
             */

            private int id;
            private String invoice_type;
            private String create;
            private String money;
            private String is_invoice_status;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getInvoice_type() {
                return invoice_type;
            }

            public void setInvoice_type(String invoice_type) {
                this.invoice_type = invoice_type;
            }

            public String getCreate() {
                return create;
            }

            public void setCreate(String create) {
                this.create = create;
            }

            public String getMoney() {
                return money;
            }

            public void setMoney(String money) {
                this.money = money;
            }

            public String getIs_invoice_status() {
                return is_invoice_status;
            }

            public void setIs_invoice_status(String is_invoice_status) {
                this.is_invoice_status = is_invoice_status;
            }
        }
    }
}
