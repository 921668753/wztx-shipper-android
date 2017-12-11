package com.ruitukeji.zwbh.mine.mypublishedorder.publishedorderfragment;

import com.ruitukeji.zwbh.common.BasePresenter;
import com.ruitukeji.zwbh.common.BaseView;

/**
 * Created by Administrator on 2017/2/16.
 */

public interface GoodsDetailsContract {

    interface Presenter extends BasePresenter {
        /**
         * 获取订单详情信息
         */
        void getGoodsDetails(int goods_id);
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
