package com.ruitukeji.zwbh.entity;

import java.util.List;

/**
 * Created by Administrator on 2017/2/20.
 */

public class NearbySearchBean {


    /**
     * count : 4
     * info : OK
     * infocode : 10000
     * status : 1
     * datas : [{"_id":"22","_location":"120.735442,31.251365","_name":"6","_address":"江苏省苏州市吴中区新平街","car_length":"4.2米","car_number":"","car_type":"平板","_createtime":"2017-07-20 11:43:58","_updatetime":"2017-07-26 08:23:08","_province":"江苏省","_city":"苏州市","_district":"吴中区","_distance":"349","_image":[]},{"_id":"23","_location":"120.735442,31.251365","_name":"8","_address":"江苏省苏州市吴中区新平街","car_length":"4.5米","car_number":"","car_type":"厢式","_createtime":"2017-07-24 17:58:36","_updatetime":"2017-07-24 17:58:36","_province":"江苏省","_city":"苏州市","_district":"吴中区","_distance":"349","_image":[]},{"_id":"24","_location":"120.735442,31.251365","_name":"10","_address":"江苏省苏州市吴中区新平街","car_length":"4.5米","car_number":"","car_type":"厢式","_createtime":"2017-07-25 11:13:06","_updatetime":"2017-07-25 11:13:06","_province":"江苏省","_city":"苏州市","_district":"吴中区","_distance":"349","_image":[]},{"_id":"25","_location":"120.735442,31.251365","_name":"11","_address":"江苏省苏州市吴中区新平街","car_length":"4.2米","car_number":"","car_type":"厢式","_createtime":"2017-07-26 15:29:58","_updatetime":"2017-07-26 15:29:58","_province":"江苏省","_city":"苏州市","_district":"吴中区","_distance":"349","_image":[]}]
     */

    private String count;
    private String info;
    private String infocode;
    private int status;
    private List<DatasBean> datas;

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getInfocode() {
        return infocode;
    }

    public void setInfocode(String infocode) {
        this.infocode = infocode;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<DatasBean> getDatas() {
        return datas;
    }

    public void setDatas(List<DatasBean> datas) {
        this.datas = datas;
    }

    public class DatasBean {
        /**
         * _id : 22
         * _location : 120.735442,31.251365
         * _name : 6
         * _address : 江苏省苏州市吴中区新平街
         * car_length : 4.2米
         * car_number :
         * car_type : 平板
         * _createtime : 2017-07-20 11:43:58
         * _updatetime : 2017-07-26 08:23:08
         * _province : 江苏省
         * _city : 苏州市
         * _district : 吴中区
         * _distance : 349
         * _image : []
         */

        private String _id;
        private String _location;
        private String _name;
        private String _address;
        private String car_length;
        private String car_number;
        private String car_type;
        private String _createtime;
        private String _updatetime;
        private String _province;
        private String _city;
        private String _district;
        private String _distance;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String get_location() {
            return _location;
        }

        public void set_location(String _location) {
            this._location = _location;
        }

        public String get_name() {
            return _name;
        }

        public void set_name(String _name) {
            this._name = _name;
        }

        public String get_address() {
            return _address;
        }

        public void set_address(String _address) {
            this._address = _address;
        }

        public String getCar_length() {
            return car_length;
        }

        public void setCar_length(String car_length) {
            this.car_length = car_length;
        }

        public String getCar_number() {
            return car_number;
        }

        public void setCar_number(String car_number) {
            this.car_number = car_number;
        }

        public String getCar_type() {
            return car_type;
        }

        public void setCar_type(String car_type) {
            this.car_type = car_type;
        }

        public String get_createtime() {
            return _createtime;
        }

        public void set_createtime(String _createtime) {
            this._createtime = _createtime;
        }

        public String get_updatetime() {
            return _updatetime;
        }

        public void set_updatetime(String _updatetime) {
            this._updatetime = _updatetime;
        }

        public String get_province() {
            return _province;
        }

        public void set_province(String _province) {
            this._province = _province;
        }

        public String get_city() {
            return _city;
        }

        public void set_city(String _city) {
            this._city = _city;
        }

        public String get_district() {
            return _district;
        }

        public void set_district(String _district) {
            this._district = _district;
        }

        public String get_distance() {
            return _distance;
        }

        public void set_distance(String _distance) {
            this._distance = _distance;
        }
    }
}
