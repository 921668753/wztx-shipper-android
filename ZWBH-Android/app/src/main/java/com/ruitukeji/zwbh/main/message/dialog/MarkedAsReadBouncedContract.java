package com.ruitukeji.zwbh.main.message.dialog;

import com.ruitukeji.zwbh.common.BasePresenter;
import com.ruitukeji.zwbh.common.BaseView;

/**
 * Created by Admin on 2017/7/12.
 */

public class MarkedAsReadBouncedContract {

    interface Presenter extends BasePresenter {

        /**
         * 提交司机报价
         */
        void getQuoteAdd(int goods_id, String dr_price, int is_place_order);
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
