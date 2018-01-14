package com.ruitukeji.zwbh.startpage;

import com.ruitukeji.zwbh.common.BasePresenter;
import com.ruitukeji.zwbh.common.BaseView;

/**
 * Created by Administrator on 2016/11/29.
 */

public interface StartPageContract {

    interface Presenter extends BasePresenter {
        /**
         * 获取配置参数
         */
        void getAppConfig();
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
