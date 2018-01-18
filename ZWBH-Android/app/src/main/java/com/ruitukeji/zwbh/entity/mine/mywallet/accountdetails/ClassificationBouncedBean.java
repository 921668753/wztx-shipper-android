package com.ruitukeji.zwbh.entity.mine.mywallet.accountdetails;

import com.ruitukeji.zwbh.entity.BaseResult;

import java.util.List;

/**
 * Created by Administrator on 2018/1/18.
 */

public class ClassificationBouncedBean extends BaseResult<List<ClassificationBouncedBean.ResultBean>> {

    public class ResultBean {
        /**
         * id : 1
         * name_time : 本周
         */

        private int id;
        private String name_time;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName_time() {
            return name_time;
        }

        public void setName_time(String name_time) {
            this.name_time = name_time;
        }
    }
}
