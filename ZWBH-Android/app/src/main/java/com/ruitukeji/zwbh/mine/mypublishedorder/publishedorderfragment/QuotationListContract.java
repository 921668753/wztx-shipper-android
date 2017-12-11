package com.ruitukeji.zwbh.mine.mypublishedorder.publishedorderfragment;

import com.ruitukeji.zwbh.common.BasePresenter;
import com.ruitukeji.zwbh.common.BaseView;

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
