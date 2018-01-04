package com.ruitukeji.zwbh.entity.mine.invoicemanagement;

import com.ruitukeji.zwbh.entity.BaseResult;

/**
 * Created by Administrator on 2018/1/4.
 */

public class BillingDetailsBean extends BaseResult<BillingDetailsBean.ResultBean> {


    public class ResultBean {
        /**
         * id : 13
         * address : 江苏省苏州市昆山市
         * address_detail : 太可怜了
         * recipient_man : 黄家驹
         * recipient_tel : 17052855757
         * invoice_up : 看见了
         * ein_num :
         * content : 看见了
         * money : 15000.00
         * create_time : 2018-01-03 18:14:30
         * g_id : 291,292,293
         * z_num : 1
         * good_num : 3
         * start_time : 2017-12-29 15:53:10
         * end_time : 2017-12-30 19:48:43
         */

        private int id;
        private String address;
        private String address_detail;
        private String recipient_man;
        private String recipient_tel;
        private String invoice_up;
        private String ein_num;
        private String content;
        private String money;
        private String create_time;
        private String g_id;
        private String z_num;
        private String good_num;
        private String start_time;
        private String end_time;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getAddress_detail() {
            return address_detail;
        }

        public void setAddress_detail(String address_detail) {
            this.address_detail = address_detail;
        }

        public String getRecipient_man() {
            return recipient_man;
        }

        public void setRecipient_man(String recipient_man) {
            this.recipient_man = recipient_man;
        }

        public String getRecipient_tel() {
            return recipient_tel;
        }

        public void setRecipient_tel(String recipient_tel) {
            this.recipient_tel = recipient_tel;
        }

        public String getInvoice_up() {
            return invoice_up;
        }

        public void setInvoice_up(String invoice_up) {
            this.invoice_up = invoice_up;
        }

        public String getEin_num() {
            return ein_num;
        }

        public void setEin_num(String ein_num) {
            this.ein_num = ein_num;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getG_id() {
            return g_id;
        }

        public void setG_id(String g_id) {
            this.g_id = g_id;
        }

        public String getZ_num() {
            return z_num;
        }

        public void setZ_num(String z_num) {
            this.z_num = z_num;
        }

        public String getGood_num() {
            return good_num;
        }

        public void setGood_num(String good_num) {
            this.good_num = good_num;
        }

        public String getStart_time() {
            return start_time;
        }

        public void setStart_time(String start_time) {
            this.start_time = start_time;
        }

        public String getEnd_time() {
            return end_time;
        }

        public void setEnd_time(String end_time) {
            this.end_time = end_time;
        }
    }
}
