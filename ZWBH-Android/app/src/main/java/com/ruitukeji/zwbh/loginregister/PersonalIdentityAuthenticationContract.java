package com.ruitukeji.zwbh.loginregister;

import com.ruitukeji.zwbh.common.BasePresenter;
import com.ruitukeji.zwbh.common.BaseView;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Administrator on 2017/2/17.
 */

public interface PersonalIdentityAuthenticationContract {
    interface Presenter extends BasePresenter {
        /**
         * 发送个人认证信息
         *
         * @param real_name
         */
        void postPersonalIdentityAuthentication(SweetAlertDialog sweetAlertDialog,String real_name, int sex, String identity, String hold_pic, String front_pic, String back_pic);

        /**
         * 上传图片
         */
        void upLoadImg(String path, int flag);
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
