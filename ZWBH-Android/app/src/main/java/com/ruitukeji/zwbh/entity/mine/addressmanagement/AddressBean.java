package com.ruitukeji.zwbh.entity.mine.addressmanagement;

import com.ruitukeji.zwbh.entity.BaseResult;

import java.util.List;

/**
 * Created by Administrator on 2017/12/31.
 */

public class AddressBean extends BaseResult<AddressBean.ResultBean> {


    public class ResultBean {
        /**
         * list : [{"id":10,"user_id":80,"address_maps":"120.646435,31.248493","city":"江苏省苏州市吴中区","address_name":"江苏省苏州市吴中区石湖东路25号附近FG造型(钱家花园店)","address_detail":"Bobby","client":"Hhh","client_name":"Vbh","phone":"15800000000","telphone":null,"is_default":"1","type":"0"},{"id":11,"user_id":80,"address_maps":"121.10941,31.44535","city":"江苏省苏州市太仓市","address_name":"江苏省苏州市太仓市人民南路96号南洋广场F2V.H.P.O(","address_detail":"Ggh","client":"Vgg","client_name":"Ggg","phone":"17051335257","telphone":null,"is_default":"1","type":"0"},{"id":13,"user_id":80,"address_maps":"121.062858,31.320112","city":"江苏省苏州市昆山市","address_name":"江苏省苏州市昆山市花桥镇海星路两岸新天地","address_detail":"Fhjk","client":"Ghj","client_name":"Ghh","phone":"18255648007","telphone":null,"is_default":"0","type":"0"}]
         * page : 1
         * pageSize : 10
         * dataTotal : 3
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
             * id : 10
             * user_id : 80
             * address_maps : 120.646435,31.248493
             * city : 江苏省苏州市吴中区
             * address_name : 江苏省苏州市吴中区石湖东路25号附近FG造型(钱家花园店)
             * address_detail : Bobby
             * client : Hhh
             * client_name : Vbh
             * phone : 15800000000
             * telphone : null
             * is_default : 1
             * type : 0
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
            private int is_default;
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

            public int getIs_default() {
                return is_default;
            }

            public void setIs_default(int is_default) {
                this.is_default = is_default;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }
        }
    }
}
