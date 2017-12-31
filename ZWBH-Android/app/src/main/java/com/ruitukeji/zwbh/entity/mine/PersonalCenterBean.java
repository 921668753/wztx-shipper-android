package com.ruitukeji.zwbh.entity.mine;

import com.ruitukeji.zwbh.entity.BaseResult;

/**
 * Created by Administrator on 2017/7/10.
 */

public class PersonalCenterBean extends BaseResult<PersonalCenterBean.ResultBean> {

    public class ResultBean {
        /**
         * id : 1
         * phone : 123ghhh
         * sex : 1
         * avatar : 61581728f56628bfd565a6bb8f42d0b5
         * real_name : a9de48465b52f16bb976343a75665ce9
         * auth_status : int
         * bond_status : ghhj
         * bond : 123ffff
         */

        private int id;
        private String phone;
        private int sex;
        private String avatar;
        private String real_name;
        private String auth_status;
        private String bond_status;
        private String bond;
        private String type;
        private String recomm_code;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getRecomm_code() {
            return recomm_code;
        }

        public void setRecomm_code(String recomm_code) {
            this.recomm_code = recomm_code;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getReal_name() {
            return real_name;
        }

        public void setReal_name(String real_name) {
            this.real_name = real_name;
        }

        public String getAuth_status() {
            return auth_status;
        }

        public void setAuth_status(String auth_status) {
            this.auth_status = auth_status;
        }

        public String getBond_status() {
            return bond_status;
        }

        public void setBond_status(String bond_status) {
            this.bond_status = bond_status;
        }

        public String getBond() {
            return bond;
        }

        public void setBond(String bond) {
            this.bond = bond;
        }
    }
}
