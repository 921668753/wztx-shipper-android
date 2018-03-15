package com.ruitukeji.zwbh.mine.mywallet.withdrawal.dialog;

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
         * 发送提现信息
         */
        void postWithdrawal(String withdrawalAmount, int bankId);

    }

    interface View extends BaseNewView<Presenter, String> {

    }

}
