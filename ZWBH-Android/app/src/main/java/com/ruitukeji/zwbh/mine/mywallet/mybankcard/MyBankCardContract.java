package com.ruitukeji.zwbh.mine.mywallet.mybankcard;

import com.ruitukeji.zwbh.common.BaseNewView;
import com.ruitukeji.zwbh.common.BasePresenter;

/**
 * Created by Administrator on 2017/2/17.
 */

public interface MyBankCardContract {

    interface Presenter extends BasePresenter {
        /**
         * 发送提现信息
         */
        void getMyBankCard();
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
