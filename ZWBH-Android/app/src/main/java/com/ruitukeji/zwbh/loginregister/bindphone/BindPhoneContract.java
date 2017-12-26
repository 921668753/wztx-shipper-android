package com.ruitukeji.zwbh.loginregister.bindphone;

import com.ruitukeji.zwbh.common.BasePresenter;
import com.ruitukeji.zwbh.common.BaseView;

/**
 * Created by ruitu on 2016/9/24.
 */
interface BindPhoneContract {
    interface Presenter extends BasePresenter {
        /**
         * 获取验证码
         *
         * @param phone
         */
        void postCode(String phone, String type);

        /**
         * 绑定手机号
         */
        void postBindPhone(String phone, String code);

        /**
         * 第三方账号登录
         */
        void postThirdToLogin(String qq_openid, String we_openid, String nickname, String avatar, int sex, String tel);

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