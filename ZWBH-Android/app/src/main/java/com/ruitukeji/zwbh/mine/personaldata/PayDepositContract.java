package com.ruitukeji.zwbh.mine.personaldata;

import com.ruitukeji.zwbh.common.BasePresenter;
import com.ruitukeji.zwbh.common.BaseView;

/**
 * Created by Administrator on 2017/2/24.
 */

public interface PayDepositContract {


    interface Presenter extends BasePresenter {
        /**
         * 获取配置参数
         */
        void getAppConfig();

        /**
         * 获取支付信息
         */
        void getPayDeposit(int pay_way);
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
        void error(String msg);
    }


}
