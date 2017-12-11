package com.ruitukeji.zwbh.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 主界面
 * Created by Administrator on 2017/5/28.
 */

public class HomeBean extends BaseResult<HomeBean.ResultBean> {


    public class ResultBean {
        /**
         * list : [{"id":2,"position":1,"src":"http://ot090bmn8.bkt.clouddn.com/2b04df3ecc1d94af/ddff082d139c6f15.jpg","url":"http://www.baidu.com67"}]
         * unreadMsg : {"msg":3}
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
             * msg : 3
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
             * id : 2
             * position : 1
             * src : http://ot090bmn8.bkt.clouddn.com/2b04df3ecc1d94af/ddff082d139c6f15.jpg
             * url : http://www.baidu.com67
             */

            private int id;
            private int position;
            private String src;
            private String url;

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

            public String getSrc() {
                return src;
            }

            public void setSrc(String src) {
                this.src = src;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }
    }
}
