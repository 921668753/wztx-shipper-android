package com.ruitukeji.zwbh.mine.personaldata;

import com.ruitukeji.zwbh.common.BasePresenter;
import com.ruitukeji.zwbh.common.BaseView;

/**
 * Created by ruitu on 2016/9/24.
 */

public interface PersonalDataContract {
    interface Presenter extends BasePresenter {

        /**
         * 获取用户信息
         */
        void getInfo();

        /**
         * 上传头像
         */
        void postUpLoadImg(String path);

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


