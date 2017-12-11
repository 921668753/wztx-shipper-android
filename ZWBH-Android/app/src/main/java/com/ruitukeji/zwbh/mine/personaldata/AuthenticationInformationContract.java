package com.ruitukeji.zwbh.mine.personaldata;

import com.ruitukeji.zwbh.common.BasePresenter;
import com.ruitukeji.zwbh.common.BaseView;

/**
 * Created by Administrator on 2017/2/14.
 */

public interface AuthenticationInformationContract {

    interface Presenter extends BasePresenter {
        /**
         * 获取个人认证信息
         */
        void getPersonalCertificate();

        /**
         * 获取企业个人认证信息
         */
        void getCompanyAuthInfo();
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
