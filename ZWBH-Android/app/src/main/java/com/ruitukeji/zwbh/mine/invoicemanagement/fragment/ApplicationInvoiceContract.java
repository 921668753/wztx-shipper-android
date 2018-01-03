package com.ruitukeji.zwbh.mine.invoicemanagement.fragment;

import com.ruitukeji.zwbh.common.BaseNewView;
import com.ruitukeji.zwbh.common.BasePresenter;
import com.ruitukeji.zwbh.common.BaseView;
import com.ruitukeji.zwbh.entity.mine.invoicemanagement.ApplicationInvoiceBean.ResultBean;

import java.util.List;

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
        void postApplicationInvoice(int invoice_type, String money, int up_type, String invoice_up, String content, String recipient_man, String recipient_tel,
                                    String address, String address_detail, String ein_num, String address_phone, String bank_account, List<ResultBean> list);

    }

    interface View extends BaseNewView<Presenter, String> {

    }

}


