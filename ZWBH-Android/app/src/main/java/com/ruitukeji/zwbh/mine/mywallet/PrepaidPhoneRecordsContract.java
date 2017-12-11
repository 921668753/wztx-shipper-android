package com.ruitukeji.zwbh.mine.mywallet;

import com.ruitukeji.zwbh.common.BasePresenter;
import com.ruitukeji.zwbh.common.BaseView;

/**
 * Created by Administrator on 2017/2/13.
 */

public interface PrepaidPhoneRecordsContract {
    interface Presenter extends BasePresenter {
        /**
         * 获取充值记录列表
         */
        void getPrepaidPhoneRecords(int page);
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
