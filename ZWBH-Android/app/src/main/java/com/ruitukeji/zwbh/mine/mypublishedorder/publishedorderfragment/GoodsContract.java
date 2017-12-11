package com.ruitukeji.zwbh.mine.mypublishedorder.publishedorderfragment;

import com.ruitukeji.zwbh.common.BasePresenter;
import com.ruitukeji.zwbh.common.BaseView;

/**
 * Created by Administrator on 2017/2/13.
 */

public interface GoodsContract {
    interface Presenter extends BasePresenter {
        /**
         * 获取报价列表信息
         */
        void getGoodsList(int page, String type);

        /**
         * 取消报价信息
         */
        void postCancelGoods(int goods_id);
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
