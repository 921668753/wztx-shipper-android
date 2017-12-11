package com.ruitukeji.zwbh.mine.mywallet;

import com.ruitukeji.zwbh.common.BasePresenter;
import com.ruitukeji.zwbh.common.BaseView;

/**
 * Created by Administrator on 2017/2/17.
 */

public interface WithdrawalContract {

    interface Presenter extends BasePresenter {
        /**
         * 获取订单列表信息
         */
        void postWithdrawal(String withdrawalAmount, String bankName, String paymentAccount, String accountName);
    }

    interface View extends BaseView<Presenter> {
        /**
         * http请求正确
         *
         * @param s
         */
        void getSuccess(String s);


        void showAlertDialog(WithdrawalActivity.OnDialogSelectListener onDialogSelectListener);

        /**
         * http请求错误
         */
        void error(String msg);
    }

}
