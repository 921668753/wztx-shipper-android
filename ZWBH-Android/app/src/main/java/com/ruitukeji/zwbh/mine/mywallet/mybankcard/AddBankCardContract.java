package com.ruitukeji.zwbh.mine.mywallet.mybankcard;

import com.ruitukeji.zwbh.common.BaseNewView;
import com.ruitukeji.zwbh.common.BasePresenter;

/**
 * Created by Administrator on 2017/2/17.
 */

public interface AddBankCardContract {

    interface Presenter extends BasePresenter {

        /**
         * 获取验证码
         *
         * @param phone
         */
        void postCode(String phone, String type);

        /**
         * 添加银行卡
         */
        void postAddBankCard(String cardholder, String bankCardNumber, String withdrawalsBank, String openingBank, String phone, String verificationCode);

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
