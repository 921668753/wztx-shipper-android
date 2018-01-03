package com.ruitukeji.zwbh.mine.drivermanagement.fragment;

import com.ruitukeji.zwbh.common.BaseNewView;
import com.ruitukeji.zwbh.common.BasePresenter;

/**
 * Created by Administrator on 2017/2/13.
 */

public interface DriverManagementContract {
    interface Presenter extends BasePresenter {
        /**
         * 获取司机列表信息
         */
        void getDriverList(int page, String type);

        /**
         * 加入黑名单/移除黑名单
         */
        void postDriverBack(int dr_id, int type);


        /**
         * 删除司机
         */
        void postDeleteDriver(int addressId);
    }

    interface View extends BaseNewView<Presenter, String> {
//        /**
//         * http请求正确
//         *
//         * @param s
//         */
//        void getSuccess(String s);
//
//        /**
//         * http请求错误
//         */
//        void error(String msg);
    }

}
