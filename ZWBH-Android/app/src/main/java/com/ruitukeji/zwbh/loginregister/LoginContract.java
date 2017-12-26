package com.ruitukeji.zwbh.loginregister;

import com.ruitukeji.zwbh.common.BaseNewView;
import com.ruitukeji.zwbh.common.BasePresenter;

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
         * 第三方账号登录
         */
        void postThirdToLogin(String qq_openid, String we_openid, String nickname, String avatar, int sex, String tel);

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


