package com.ruitukeji.zwbh.entity;

/**
 * Created by Admin on 2017/7/11.
 */

public class EvaluationShareBean extends BaseResult<EvaluationShareBean.ResultBean> {


    public class ResultBean {

        private int limit_ship;
        private int attitude;
        private int satisfaction;
        private String content;

        public int getLimit_ship() {
            return limit_ship;
        }

        public void setLimit_ship(int limit_ship) {
            this.limit_ship = limit_ship;
        }

        public int getAttitude() {
            return attitude;
        }

        public void setAttitude(int attitude) {
            this.attitude = attitude;
        }

        public int getSatisfaction() {
            return satisfaction;
        }

        public void setSatisfaction(int satisfaction) {
            this.satisfaction = satisfaction;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }

}
