package com.ruitukeji.zwbh.loginregister;

import com.ruitukeji.zwbh.common.BasePresenter;
import com.ruitukeji.zwbh.common.BaseView;

/**
 * Created by ruitu on 2016/9/24.
 */

public interface LoginContract {
    interface Presenter extends BasePresenter {
        /**
         * 账号登录
         */
        void postToLogin(String phone, String pwd);

        /**
         * 获取用户信息
         */
        void getInfo();

        /**
         * 获取广告状态
         */
        void getIsAd();
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


