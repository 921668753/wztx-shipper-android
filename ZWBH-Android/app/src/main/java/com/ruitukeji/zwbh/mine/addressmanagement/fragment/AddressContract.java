package com.ruitukeji.zwbh.mine.addressmanagement.fragment;

import com.ruitukeji.zwbh.common.BaseNewView;
import com.ruitukeji.zwbh.common.BasePresenter;
import com.ruitukeji.zwbh.common.BaseView;

/**
 * Created by Administrator on 2017/2/13.
 */

public interface AddressContract {
    interface Presenter extends BasePresenter {
        /**
         * 获取地址列表信息
         */
        void getAddress(int page, String type);

        /**
         * 设置默认地址
         */
        void postSetDefaultAddress(int addressId);

        /**
         * 刪除地址
         */
        void postDeleteAddress(int addressId);

    }

    interface View extends BaseNewView<Presenter, String> {
//        /**
//         * http请求正确
//         *
//         * @param s
//         */
//        void getSuccess(String s);
//
//        /**
//         * http请求错误
//         */
//        void error(String msg);
    }

}
