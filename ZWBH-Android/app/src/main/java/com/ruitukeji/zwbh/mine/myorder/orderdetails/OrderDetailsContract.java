package com.ruitukeji.zwbh.mine.myorder.orderdetails;

import com.ruitukeji.zwbh.common.BaseNewView;
import com.ruitukeji.zwbh.common.BasePresenter;
import com.ruitukeji.zwbh.common.BaseView;

/**
 * Created by Administrator on 2017/2/16.
 */

public interface OrderDetailsContract {

    interface Presenter extends BasePresenter {
        /**
         * 获取订单详情信息
         */
        void getOrderDetails(int orderId);

        /**
         * 收藏司机信息
         */
        void postCollectDriver(int orderId);

        /**
         * 删除收藏司机
         */
        void postDelCollectDriver(int orderId);

        /**
         * 货主确认 取消订单功能
         */
        void postCancelGoodsComplete(int orderId, int type);

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
