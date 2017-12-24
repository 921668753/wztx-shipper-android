package com.ruitukeji.zwbh.main.selectaddress;

import com.ruitukeji.zwbh.common.BaseNewView;
import com.ruitukeji.zwbh.common.BasePresenter;

/**
 * Created by Administrator on 2017/2/13.
 */

public interface ProvenanceContract {
    interface Presenter extends BasePresenter {

        /**
         * 提交地址信息
         */
        void postAddress(String longi, String lat, String provincialLevel, String address, String detailedAddress, String deliveryCustomer,
                         String shipper, String phone, String eixedTelephone, int isOff, int type);

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
