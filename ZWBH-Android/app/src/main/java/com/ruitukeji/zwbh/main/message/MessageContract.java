package com.ruitukeji.zwbh.main.message;

import com.ruitukeji.zwbh.common.BasePresenter;
import com.ruitukeji.zwbh.common.BaseView;

/**
 * Created by Administrator on 2017/2/15.
 */

public interface MessageContract {

    interface Presenter extends BasePresenter {
        /**
         * 获取消息列表
         */
        void getMessage(String type, int page);

        /**
         * 删除消息
         */
        void postDeleteMessage(int masageId);
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
        void error(String msg, int flag);
    }

}
