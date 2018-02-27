package com.ruitukeji.zwbh.mine.myorder.payment.dialog;

import com.ruitukeji.zwbh.common.BaseNewView;
import com.ruitukeji.zwbh.common.BasePresenter;

/**
 * 支付密码弹框
 * Created by Administrator on 2017/2/21.
 */

public interface PayPasswordBouncedContract {


    interface Presenter extends BasePresenter {
        /**
         * 验证旧支付密码
         */
        void postOldPayPassword(String oldPaymentPassword);

        /**
         * 通过余额支付
         */
        void postScorePay(int orderId);

    }

    interface View extends BaseNewView<Presenter, String> {

    }

}
