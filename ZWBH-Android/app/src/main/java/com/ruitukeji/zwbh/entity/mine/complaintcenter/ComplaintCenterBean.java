package com.ruitukeji.zwbh.entity.mine.complaintcenter;

import com.ruitukeji.zwbh.entity.BaseResult;

import java.util.List;

/**
 * Created by Administrator on 2018/1/10.
 */

public class ComplaintCenterBean extends BaseResult<ComplaintCenterBean.ResultBean> {


    /**
     * result : {"list":[{"id":1,"user_id":91,"time":"1970-01-01 08:01:st","dr_name":"戴志浩","reason":"延迟发货","card_number":"苏E12345"},{"id":2,"user_id":91,"time":"1970-01-01 08:01:st","dr_name":"戴志浩","reason":"延迟发货","card_number":"唐S5438"}],"page":"1","pageSize":"10","dataTotal":2,"pageTotal":1}
     */

    public class ResultBean {
        /**
         * list : [{"id":1,"user_id":91,"time":"1970-01-01 08:01:st","dr_name":"戴志浩","reason":"延迟发货","card_number":"苏E12345"},{"id":2,"user_id":91,"time":"1970-01-01 08:01:st","dr_name":"戴志浩","reason":"延迟发货","card_number":"唐S5438"}]
         * page : 1
         * pageSize : 10
         * dataTotal : 2
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
             * id : 1
             * user_id : 91
             * time : 1970-01-01 08:01:st
             * dr_name : 戴志浩
             * reason : 延迟发货
             * card_number : 苏E12345
             */

            private int id;
            private int user_id;
            private String time;
            private String dr_name;
            private String reason;
            private String card_number;
            private String processing_result;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getUser_id() {
                return user_id;
            }

            public void setUser_id(int user_id) {
                this.user_id = user_id;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public String getDr_name() {
                return dr_name;
            }

            public void setDr_name(String dr_name) {
                this.dr_name = dr_name;
            }

            public String getReason() {
                return reason;
            }

            public void setReason(String reason) {
                this.reason = reason;
            }

            public String getCard_number() {
                return card_number;
            }

            public void setCard_number(String card_number) {
                this.card_number = card_number;
            }

            public String getProcessing_result() {
                return processing_result;
            }

            public void setProcessing_result(String processing_result) {
                this.processing_result = processing_result;
            }
        }
    }
}
