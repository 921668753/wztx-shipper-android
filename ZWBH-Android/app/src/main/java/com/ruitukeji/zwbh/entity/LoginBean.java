package com.ruitukeji.zwbh.entity;

/**
 * Created by ruitu on 2016/8/27.
 */

public class LoginBean extends BaseResult<LoginBean.ResultBean> {


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

        private String accessToken;
        private String refreshToken;
        private int userId;
        private String type;
        private String real_name;
        private int expireTime;

        public String getReal_name() {
            return real_name;
        }

        public void setReal_name(String real_name) {
            this.real_name = real_name;
        }

        public String getAccessToken() {
            return accessToken;
        }

        public void setAccessToken(String accessToken) {
            this.accessToken = accessToken;
        }

        public String getRefreshToken() {
            return refreshToken;
        }

        public void setRefreshToken(String refreshToken) {
            this.refreshToken = refreshToken;
        }

        public int getExpireTime() {
            return expireTime;
        }

        public void setExpireTime(int expireTime) {
            this.expireTime = expireTime;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
