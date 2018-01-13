package com.ruitukeji.zwbh.entity.mine.mywallet;

import com.ruitukeji.zwbh.entity.BaseResult;

/**
 * Created by Admin on 2017/7/11.
 */

public class MyWalletBean extends BaseResult<MyWalletBean.ResultBean> {


    public class ResultBean {
        /**
         * balance : 0.00
         * notPayAccount : 0.00
         * payAccount : 0.00
         * withdrawalAmount : 0.00
         * bankCard : 8558
         * id : 28
         * bankName : 中国工商银行
         */

        private String balance;
        private String notPayAccount;
        private String payAccount;
        private String withdrawalAmount;
        private String bankCard;
        private int id;
        private String bankName;

        public String getBalance() {
            return balance;
        }

        public void setBalance(String balance) {
            this.balance = balance;
        }

        public String getNotPayAccount() {
            return notPayAccount;
        }

        public void setNotPayAccount(String notPayAccount) {
            this.notPayAccount = notPayAccount;
        }

        public String getPayAccount() {
            return payAccount;
        }

        public void setPayAccount(String payAccount) {
            this.payAccount = payAccount;
        }

        public String getWithdrawalAmount() {
            return withdrawalAmount;
        }

        public void setWithdrawalAmount(String withdrawalAmount) {
            this.withdrawalAmount = withdrawalAmount;
        }

        public String getBankCard() {
            return bankCard;
        }

        public void setBankCard(String bankCard) {
            this.bankCard = bankCard;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getBankName() {
            return bankName;
        }

        public void setBankName(String bankName) {
            this.bankName = bankName;
        }
    }
}
