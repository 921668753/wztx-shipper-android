package com.ruitukeji.zwbh.mine.myorder.orderfragment;

import com.ruitukeji.zwbh.common.BaseNewView;
import com.ruitukeji.zwbh.common.BasePresenter;
import com.ruitukeji.zwbh.common.BaseView;

/**
 * Created by Administrator on 2017/2/13.
 */

public interface OrderContract {
    interface Presenter extends BasePresenter {
        /**
         * 获取订单列表信息
         */
        void getOrder(int page, String type);

        /**
         * 模拟点击
         *
         * @param view
         * @param x
         * @param y
         */
        void setSimulateClick(android.view.View view, float x, float y);

    }

    interface View extends BaseNewView<Presenter,String> {
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
