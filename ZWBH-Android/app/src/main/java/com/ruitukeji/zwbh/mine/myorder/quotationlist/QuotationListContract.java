package com.ruitukeji.zwbh.mine.myorder.quotationlist;

import com.ruitukeji.zwbh.common.BaseNewView;
import com.ruitukeji.zwbh.common.BasePresenter;

/**
 * Created by Administrator on 2017/3/6.
 */

public interface QuotationListContract {
    interface Presenter extends BasePresenter {
        /**
         * 获取报价列表信息
         */
        void getQuotationList(int goods_id);

        /**
         * 提交报价信息
         */
        void postQuotation(int quote_id, int order_id);
    }

    interface View extends BaseNewView<Presenter, String> {

    }
}
