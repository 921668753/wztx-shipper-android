package com.ruitukeji.zwbh.main.selectaddress.dialog;

import com.ruitukeji.zwbh.common.BaseNewView;
import com.ruitukeji.zwbh.common.BasePresenter;

/**
 * Created by Administrator on 2017/2/21.
 */

public interface AddressBouncedContract {

    interface Presenter extends BasePresenter {

        /**
         * 获取省份列表信息
         */
        void getAddress(int id, int type);

    }

    interface View extends BaseNewView<Presenter, String> {
//        /**
//         * http请求正确
//         * @param s
//         */
//        void getSuccess(String s, int flag);
//
//        /**
//         * http请求错误
//         */
//        void error(String msg, int flag);
    }


}
