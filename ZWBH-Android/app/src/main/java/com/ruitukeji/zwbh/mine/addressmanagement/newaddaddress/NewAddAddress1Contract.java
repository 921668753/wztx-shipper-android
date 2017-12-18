package com.ruitukeji.zwbh.mine.addressmanagement.newaddaddress;

import com.ruitukeji.zwbh.common.BaseNewView;
import com.ruitukeji.zwbh.common.BasePresenter;

/**
 * Created by Administrator on 2017/2/13.
 */

public interface NewAddAddress1Contract {
    interface Presenter extends BasePresenter {
        /**
         * 获取地址信息
         */
        void getAddress(int page, String type);

        /**
         * 提交地址信息
         */
        void postAddress(int addressId);

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
