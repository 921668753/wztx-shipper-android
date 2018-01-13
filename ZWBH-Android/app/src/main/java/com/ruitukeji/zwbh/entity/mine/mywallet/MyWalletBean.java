package com.ruitukeji.zwbh.entity.mine.mywallet;

import com.ruitukeji.zwbh.entity.BaseResult;

/**
 * Created by Admin on 2017/7/11.
 */

public class MyWalletBean extends BaseResult<MyWalletBean.ResultBean> {


    public class ResultBean {
        /**
         * balance : 0.00
         * NotPayAccount : 0.00
         * PayAccount : 0.00
         * WithdrawalAmount : 0.00
         */

        private String balance;
        private String NotPayAccount;
        private String PayAccount;
        private String WithdrawalAmount;

        public String getBalance() {
            return balance;
        }

        public void setBalance(String balance) {
            this.balance = balance;
        }

        public String getNotPayAccount() {
            return NotPayAccount;
        }

        public void setNotPayAccount(String NotPayAccount) {
            this.NotPayAccount = NotPayAccount;
        }

        public String getPayAccount() {
            return PayAccount;
        }

        public void setPayAccount(String PayAccount) {
            this.PayAccount = PayAccount;
        }

        public String getWithdrawalAmount() {
            return WithdrawalAmount;
        }

        public void setWithdrawalAmount(String WithdrawalAmount) {
            this.WithdrawalAmount = WithdrawalAmount;
        }
    }
}
