package com.ruitukeji.zwbh.loginregister;

import com.ruitukeji.zwbh.common.BasePresenter;
import com.ruitukeji.zwbh.common.BaseView;

/**
 * Created by Administrator on 2017/2/17.
 */

public interface PersonalInformationContract {


    interface Presenter extends BasePresenter {
        /**
         * 发送个人信息
         *
         * @param name     姓名
         * @param sex      性别
         * @param IDNumber 身份证号
         */
        void postPersonalInformation(String name, int sex, String IDNumber,String imgUrl);

        /**
         * 上传图片
         */
        void upLoadImg(String path);

        /**
         * 获取个人认证信息
         */
        void getPersonalCertificate();
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
