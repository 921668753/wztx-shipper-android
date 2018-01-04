package com.ruitukeji.zwbh.mine.invoicemanagement.billingdetails;

import com.ruitukeji.zwbh.common.BaseNewView;
import com.ruitukeji.zwbh.common.BasePresenter;

/**
 * Created by ruitu on 2016/9/24.
 */

public interface ContainsOrderContract {
    interface Presenter extends BasePresenter {

        /**
         * 获取订单金额列表
         */
        void getContainsOrder(String id);
    }

    interface View extends BaseNewView<Presenter, String> {

    }

}


