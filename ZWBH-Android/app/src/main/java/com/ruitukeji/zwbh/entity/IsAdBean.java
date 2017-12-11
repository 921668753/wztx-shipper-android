package com.ruitukeji.zwbh.entity;

/**
 * Created by ruitu on 2016/8/27.
 */

public class IsAdBean extends BaseResult<IsAdBean.ResultBean> {


    /**
     * result : {"accessToken":"61581728f56628bfd565a6bb8f42d0b5","refreshToken":"a9de48465b52f16bb976343a75665ce9","expireTime":7200}
     */

    public class ResultBean {
        /**
         * accessToken : 61581728f56628bfd565a6bb8f42d0b5
         * refreshToken : a9de48465b52f16bb976343a75665ce9
         * expireTime : 7200
         * real_name
         */

        private String is_ad;

        public String getIs_ad() {
            return is_ad;
        }

        public void setIs_ad(String is_ad) {
            this.is_ad = is_ad;
        }
    }
}
