package com.ruitukeji.zwbh.mine.abnormalrecords;

import com.ruitukeji.zwbh.common.BasePresenter;
import com.ruitukeji.zwbh.common.BaseView;

/**
 * Created by Administrator on 2017/2/13.
 */

public interface AbnormalSituationContract {


    interface Presenter extends BasePresenter {
        /**
         * 获取异常情况信息
         */
        void getAbnormalSituation(int id);
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
