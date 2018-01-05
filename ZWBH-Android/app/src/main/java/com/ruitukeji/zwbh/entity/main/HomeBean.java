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
         * list : [{"id":1,"position":1,"ad_content":"15秒钟急速响应，随时随地找车配送","endtime":"2018-02-28 11:06:04"},{"id":2,"position":2,"ad_content":"货物随时随地拉，有货请联系货某","endtime":"2017-12-29 11:06:14"},{"id":3,"position":3,"ad_content":"专业拉货，有货联系电话","endtime":"2015-05-09 16:00:00"}]
         * unreadMsg : {"msg":7}
         * start_address : {"id":38,"user_id":91,"address_maps":"31.334579,121.066408","city":"江苏省苏州市昆山市","address_name":"江苏省苏州市昆山市陆家镇金阳东路与望星路交汇处西北角常发豪郡","address_detail":"12栋2109","client":"个体户","client_name":"张三","phone":"18255648007","telphone":"","is_default":"1","type":"0","create_time":1515129387,"update_time":1515129387}
         * end_address : {"id":35,"user_id":91,"address_maps":"115.043937,38.028167","city":"河北省石家庄市晋州市","address_name":"河北省石家庄市晋州市晋州镇向阳街38号商铺哈哈镜鸭脖店","address_detail":"vvv","client":"物载天下科技有限公司","client_name":"戴志豪","phone":"18255648007","telphone":"","is_default":"1","type":"1","create_time":1515129382,"update_time":1515129382}
         */

        private UnreadMsgBean unreadMsg;
        private StartAddressBean start_address;
        private EndAddressBean end_address;
        private List<ListBean> list;

        public UnreadMsgBean getUnreadMsg() {
            return unreadMsg;
        }

        public void setUnreadMsg(UnreadMsgBean unreadMsg) {
            this.unreadMsg = unreadMsg;
        }

        public StartAddressBean getStart_address() {
            return start_address;
        }

        public void setStart_address(StartAddressBean start_address) {
            this.start_address = start_address;
        }

        public EndAddressBean getEnd_address() {
            return end_address;
        }

        public void setEnd_address(EndAddressBean end_address) {
            this.end_address = end_address;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public class UnreadMsgBean {
            /**
             * msg : 7
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

        public class StartAddressBean {
            /**
             * id : 38
             * user_id : 91
             * address_maps : 31.334579,121.066408
             * city : 江苏省苏州市昆山市
             * address_name : 江苏省苏州市昆山市陆家镇金阳东路与望星路交汇处西北角常发豪郡
             * address_detail : 12栋2109
             * client : 个体户
             * client_name : 张三
             * phone : 18255648007
             * telphone :
             * is_default : 1
             * type : 0
             * create_time : 1515129387
             * update_time : 1515129387
             */

            private int id;
            private int user_id;
            private String address_maps;
            private String city;
            private String address_name;
            private String address_detail;
            private String client;
            private String client_name;
            private String phone;
            private String telphone;
            private String is_default;
            private int type;

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

            public String getAddress_maps() {
                return address_maps;
            }

            public void setAddress_maps(String address_maps) {
                this.address_maps = address_maps;
            }

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public String getAddress_name() {
                return address_name;
            }

            public void setAddress_name(String address_name) {
                this.address_name = address_name;
            }

            public String getAddress_detail() {
                return address_detail;
            }

            public void setAddress_detail(String address_detail) {
                this.address_detail = address_detail;
            }

            public String getClient() {
                return client;
            }

            public void setClient(String client) {
                this.client = client;
            }

            public String getClient_name() {
                return client_name;
            }

            public void setClient_name(String client_name) {
                this.client_name = client_name;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public String getTelphone() {
                return telphone;
            }

            public void setTelphone(String telphone) {
                this.telphone = telphone;
            }

            public String getIs_default() {
                return is_default;
            }

            public void setIs_default(String is_default) {
                this.is_default = is_default;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }
        }

        public class EndAddressBean {
            /**
             * id : 35
             * user_id : 91
             * address_maps : 115.043937,38.028167
             * city : 河北省石家庄市晋州市
             * address_name : 河北省石家庄市晋州市晋州镇向阳街38号商铺哈哈镜鸭脖店
             * address_detail : vvv
             * client : 物载天下科技有限公司
             * client_name : 戴志豪
             * phone : 18255648007
             * telphone :
             * is_default : 1
             * type : 1
             * create_time : 1515129382
             * update_time : 1515129382
             */

            private int id;
            private int user_id;
            private String address_maps;
            private String city;
            private String address_name;
            private String address_detail;
            private String client;
            private String client_name;
            private String phone;
            private String telphone;
            private String is_default;
            private int type;

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

            public String getAddress_maps() {
                return address_maps;
            }

            public void setAddress_maps(String address_maps) {
                this.address_maps = address_maps;
            }

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public String getAddress_name() {
                return address_name;
            }

            public void setAddress_name(String address_name) {
                this.address_name = address_name;
            }

            public String getAddress_detail() {
                return address_detail;
            }

            public void setAddress_detail(String address_detail) {
                this.address_detail = address_detail;
            }

            public String getClient() {
                return client;
            }

            public void setClient(String client) {
                this.client = client;
            }

            public String getClient_name() {
                return client_name;
            }

            public void setClient_name(String client_name) {
                this.client_name = client_name;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public String getTelphone() {
                return telphone;
            }

            public void setTelphone(String telphone) {
                this.telphone = telphone;
            }

            public String getIs_default() {
                return is_default;
            }

            public void setIs_default(String is_default) {
                this.is_default = is_default;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
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
