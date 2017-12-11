package com.ruitukeji.zwbh.entity;

import java.util.List;

/**
 * Created by Admin on 2017/7/11.
 */

public class CheckVoucherBean extends BaseResult<CheckVoucherBean.ResultBean> {


    public class ResultBean {
        private List<String> list;

        public List<String> getList() {
            return list;
        }

        public void setList(List<String> list) {
            this.list = list;
        }
    }
}
