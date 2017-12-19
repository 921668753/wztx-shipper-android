package com.ruitukeji.zwbh.mine.invoicemanagement.fragment;

import com.ruitukeji.zwbh.common.BaseNewView;
import com.ruitukeji.zwbh.common.BasePresenter;
import com.ruitukeji.zwbh.common.BaseView;

/**
 * Created by ruitu on 2016/9/24.
 */

public interface ApplicationInvoiceContract {
    interface Presenter extends BasePresenter {
        /**
         * 获取订单金额列表
         */
        void getApplicationInvoiceList();

        /**
         * 提交开票信息
         */
        void postApplicationInvoice(String path);

    }

    interface View extends BaseNewView<Presenter, String> {

    }

}


