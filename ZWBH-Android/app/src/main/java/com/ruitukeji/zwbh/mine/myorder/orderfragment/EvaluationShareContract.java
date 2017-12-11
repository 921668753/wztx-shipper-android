package com.ruitukeji.zwbh.mine.myorder.orderfragment;

import com.ruitukeji.zwbh.common.BasePresenter;
import com.ruitukeji.zwbh.common.BaseView;

/**
 * Created by Administrator on 2017/2/16.
 */

public interface EvaluationShareContract {
    interface Presenter extends BasePresenter {
        /**
         * 获得评价信息
         */
        void getEvaluationShare(int order_id);

        /**
         * 发送评价信息
         */
        void postEvaluationShare(int order_id, float deliveryTime,float serviceAttitude, float satisfaction,  String note);
    }

    interface View extends BaseView<Presenter> {
        /**
         * http请求正确
         *
         * @param s
         */
        void getSuccess(String s, int flag);

        /**
         * http请求错误
         */
        void error(String msg);
    }
}
