package com.ruitukeji.zwbh.loginregister;

import com.ruitukeji.zwbh.common.BasePresenter;
import com.ruitukeji.zwbh.common.BaseView;

/**
 * Created by Administrator on 2017/2/17.
 */

public interface EnterpriseQualificationCertificateContract {
    interface Presenter extends BasePresenter {
        /**
         * 发送资质认证信息
         */
        void postEnterpriseQualificationCertificate(String hold_pic, String front_pic, String back_pic, String businessLicense_pic);

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
