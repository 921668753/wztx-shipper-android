package com.ruitukeji.zwbh.mine.myorder.orderdetails;

import com.ruitukeji.zwbh.common.BaseNewView;
import com.ruitukeji.zwbh.common.BasePresenter;

/**
 * Created by Administrator on 2017/2/19.
 */

public interface CheckReceiptContract {

    interface Presenter extends BasePresenter {

        /**
         * 获取签收单信息
         */
        void getReceiptInformation(int g_id);

    }

    interface View extends BaseNewView<Presenter, String> {

    }
}
