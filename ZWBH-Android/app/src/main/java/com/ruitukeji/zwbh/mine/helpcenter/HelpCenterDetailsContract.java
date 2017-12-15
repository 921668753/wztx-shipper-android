package com.ruitukeji.zwbh.mine.helpcenter;

import com.ruitukeji.zwbh.common.BasePresenter;
import com.ruitukeji.zwbh.common.BaseView;

/**
 * Created by Administrator on 2017/2/13.
 */

public interface HelpCenterDetailsContract {

    interface Presenter extends BasePresenter {
        /**
         * 获取帮助中心详情
         */
        void getHelpCenterDetails(int id);
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
