package com.ruitukeji.zwbh.mine.myorder.orderdetails.dialog;

import com.ruitukeji.zwbh.common.BasePresenter;
import com.ruitukeji.zwbh.common.BaseView;

/**
 * Created by Admin on 2017/7/12.
 */

public class ComplaintsAboutDriverBouncedContract {

    interface Presenter extends BasePresenter {

        /**
         * 提交投诉司机内容
         */
        void postComplaintsAboutDriver(String id, String content);
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
