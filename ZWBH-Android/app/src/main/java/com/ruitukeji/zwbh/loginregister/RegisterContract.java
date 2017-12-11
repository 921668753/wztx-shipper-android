package com.ruitukeji.zwbh.loginregister;

import com.ruitukeji.zwbh.common.BasePresenter;
import com.ruitukeji.zwbh.common.BaseView;

/**
 * Created by ruitu on 2016/9/24.
 */
interface RegisterContract {
    interface Presenter extends BasePresenter {
        /**
         * 获取验证码
         *
         * @param phone
         */
        void postCode(String phone, String postCode);

        /**
         * 重置密码请求
         */
        void postResetpwd(String phone, String code, String pwd);

        /**
         * 注册
         */
        void postRegister(String type, String phone, String code, String pwd, String recommendcode);

        /**
         * 获取用户信息
         */
        void getInfo();
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