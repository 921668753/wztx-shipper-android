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
         * 获取银行列表
         */
        void getBank();

        /**
         * 添加银行卡
         */
        void postAddBankCard(String cardholder, String bankCardNumber, int bank_id, String openingBank, String phone, String verificationCode);

    }

    interface View extends BaseNewView<Presenter, String> {

    }


}
