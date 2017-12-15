package com.ruitukeji.zwbh.mine.mywallet.withdrawal;

import com.ruitukeji.zwbh.common.BasePresenter;
import com.ruitukeji.zwbh.common.BaseView;

/**
 * Created by Administrator on 2017/2/13.
 */

public interface WithdrawalRecordContract {
    interface Presenter extends BasePresenter {
        /**
         * 获取订单列表信息
         */
        void getWithdrawalRecord(int page);
    }

    interface View extends BaseView<Presenter> {
        /**
         * http请求正确
         *
         * @param s
         */
        void getSuccess(String s);

        /**
         * http请求错误
         */
        void error(String msg);
    }

}
