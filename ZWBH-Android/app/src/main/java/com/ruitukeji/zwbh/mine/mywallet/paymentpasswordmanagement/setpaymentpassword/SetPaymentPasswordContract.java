package com.ruitukeji.zwbh.mine.mywallet.paymentpasswordmanagement.setpaymentpassword;

import com.ruitukeji.zwbh.common.BaseNewView;
import com.ruitukeji.zwbh.common.BasePresenter;

/**
 * Created by Administrator on 2017/2/15.
 */

public interface SetPaymentPasswordContract {

    interface Presenter extends BasePresenter {
        /**
         * 设置支付密码
         */
        void postSetPaymentPassword(String oldPaymentPassword, String paymentPassword);
    }

    interface View extends BaseNewView<Presenter, String> {

    }

}
