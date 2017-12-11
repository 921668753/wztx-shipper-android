package com.ruitukeji.zwbh.main.message;

import com.ruitukeji.zwbh.common.BasePresenter;
import com.ruitukeji.zwbh.common.BaseView;

/**
 * Created by Administrator on 2017/2/15.
 */

public interface MessageCenterContract {

    interface Presenter extends BasePresenter {
        /**
         * 未读消息数量
         */
        void getUnRead();

        /**
         * 是否登录
         */
        void isLogin(int flag);
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
