package com.ruitukeji.zwbh.mine.myorder.orderdetails;

import com.ruitukeji.zwbh.common.BaseNewView;
import com.ruitukeji.zwbh.common.BasePresenter;
import com.ruitukeji.zwbh.common.BaseView;

/**
 * Created by Administrator on 2017/2/16.
 */

public interface EvaluationDriverContract {
    interface Presenter extends BasePresenter {
        /**
         * 获得评价信息
         */
        void getEvaluationShare(int order_id);

        /**
         * 发送评价信息
         */
        void postEvaluationShare(int order_id, int deliveryTime, int serviceAttitude);
    }

    interface View extends BaseNewView<Presenter,String> {

    }
}
