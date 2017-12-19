package com.ruitukeji.zwbh.mine.invoicemanagement.fragment;

import com.ruitukeji.zwbh.common.BaseNewView;
import com.ruitukeji.zwbh.common.BasePresenter;

/**
 * Created by Administrator on 2017/2/24.
 */

public interface BillingRecordsContract {


    interface Presenter extends BasePresenter {
        /**
         * 获取开票记录
         */
        void getBillingRecordsList(int page);

    }

    interface View extends BaseNewView<Presenter, String> {

    }


}
