package com.ruitukeji.zwbh.entity;

/**
 * Created by Admin on 2017/7/18.
 */

public class LogisticsBean extends BaseResult<LogisticsBean.ResultBean> {

    public class ResultBean {
        /**
         * order_id : 9
         */
        private String goods_id;

        public String getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(String goods_id) {
            this.goods_id = goods_id;
        }
    }
}
