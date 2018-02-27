package com.ruitukeji.zwbh.mine.myorder.payment;

import com.ruitukeji.zwbh.common.BasePresenter;
import com.ruitukeji.zwbh.common.BaseView;

/**
 * Created by Administrator on 2017/3/6.
 */

public interface PaymentContract {
    interface Presenter extends BasePresenter {

        /**
         * 获取账户余额
         */
        void getMyWallet();


        /**
         * 获取微信支付信息
         */
        void getWxPay(int orderId, String total_amount);

        /**
         * 获取支付宝支付信息
         */
        void getAlipay(int orderId, String total_amount);


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
        void error(String msg, int flag);
    }
}
