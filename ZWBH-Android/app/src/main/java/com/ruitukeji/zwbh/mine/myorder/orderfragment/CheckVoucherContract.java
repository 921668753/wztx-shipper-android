package com.ruitukeji.zwbh.mine.myorder.orderfragment;

import com.ruitukeji.zwbh.common.BasePresenter;
import com.ruitukeji.zwbh.common.BaseView;

/**
 * Created by Administrator on 2017/2/16.
 */

public interface CheckVoucherContract {

    interface Presenter extends BasePresenter {
        /**
         * 获取凭证列表
         */
        void getCheckVoucher(int order_id);
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
