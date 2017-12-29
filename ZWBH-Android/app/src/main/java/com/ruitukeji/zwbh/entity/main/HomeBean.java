package com.ruitukeji.zwbh.entity.main;

import com.google.gson.annotations.SerializedName;
import com.ruitukeji.zwbh.entity.BaseResult;

import java.util.List;

/**
 * 主界面
 * Created by Administrator on 2017/5/28.
 */

public class HomeBean extends BaseResult<HomeBean.ResultBean> {


    public class ResultBean {
        /**
         * list : [{"id":1,"position":1,"ad_content":"15秒钟急速响应，随时随地找车配送","endtime":"2018-02-28 11:06:04"},{"id":2,"position":2,"ad_content":"货物随时随地拉，有货请联系货某","endtime":"2017-12-29 11:06:14"}]
         * unreadMsg : {"msg":0}
         */

        private UnreadMsgBean unreadMsg;
        private List<ListBean> list;

        public UnreadMsgBean getUnreadMsg() {
            return unreadMsg;
        }

        public void setUnreadMsg(UnreadMsgBean unreadMsg) {
            this.unreadMsg = unreadMsg;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public class UnreadMsgBean {
            /**
             * msg : 0
             */

            @SerializedName("msg")
            private int msgX;

            public int getMsgX() {
                return msgX;
            }

            public void setMsgX(int msgX) {
                this.msgX = msgX;
            }
        }

        public class ListBean {
            /**
             * id : 1
             * position : 1
             * ad_content : 15秒钟急速响应，随时随地找车配送
             * endtime : 2018-02-28 11:06:04
             */

            private int id;
            private int position;
            private String ad_content;
            private String endtime;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getPosition() {
                return position;
            }

            public void setPosition(int position) {
                this.position = position;
            }

            public String getAd_content() {
                return ad_content;
            }

            public void setAd_content(String ad_content) {
                this.ad_content = ad_content;
            }

            public String getEndtime() {
                return endtime;
            }

            public void setEndtime(String endtime) {
                this.endtime = endtime;
            }
        }
    }
}
