package com.ruitukeji.zwbh.entity;

/**
 * Created by Admin on 2017/7/11.
 */

public class MyWalletBean extends BaseResult<MyWalletBean.ResultBean> {


    public class ResultBean {
        /**
         * balance : 0.00
         * bonus : 1000.00
         */

        private String balance;
        private String bonus;

        public String getBalance() {
            return balance;
        }

        public void setBalance(String balance) {
            this.balance = balance;
        }

        public String getBonus() {
            return bonus;
        }

        public void setBonus(String bonus) {
            this.bonus = bonus;
        }
    }
}
