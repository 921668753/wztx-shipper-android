package com.ruitukeji.zwbh.mine.mywallet.paymentpasswordmanagement.modifypaymentpassword;

import com.ruitukeji.zwbh.common.BaseNewView;
import com.ruitukeji.zwbh.common.BasePresenter;

/**
 * Created by Administrator on 2017/2/15.
 */

public interface ModifyPaymentPasswordContract {

    interface Presenter extends BasePresenter {


        /**
         * 获取验证码
         *
         * @param phone
         */
        void postCode(String phone, String type);

        /**
         * 验证验证码
         */
        void postVerificationCode(String phone,String code);

        /**
         * 验证身份证号
         */
        void postVerifyIdNumber(String idNumber);

        /**
         * 验证旧支付密码
         */
        void postOldPayPassword(String oldPaymentPassword);

        /**
         * 设置支付密码
         */
        void postModifyPaymentPassword(String newPaymentPassword, String paymentPassword);
    }

    interface View extends BaseNewView<Presenter, String> {

    }

}
