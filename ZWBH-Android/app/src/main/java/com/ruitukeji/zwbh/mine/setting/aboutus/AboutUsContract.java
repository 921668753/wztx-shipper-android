package com.ruitukeji.zwbh.mine.setting.aboutus;

import com.ruitukeji.zwbh.common.BasePresenter;
import com.ruitukeji.zwbh.common.BaseView;

/**
 * Created by ruitu on 2016/9/24.
 */
interface AboutUsContract {
    interface Presenter extends BasePresenter {
        /**
         * 获取文章列表
         *
         * @param type
         */
        void getArticle(String type);
    }

    interface View extends BaseView<Presenter> {
        /**
         * http请求正确
         *
         * @param s
         */
        void getSuccess(String s);

        /**
         * http请求错误
         */
        void error(String msg);
    }

}