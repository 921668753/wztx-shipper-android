package com.ruitukeji.zwbh.mine.myorder.payment;

import com.ruitukeji.zwbh.common.BaseNewView;
import com.ruitukeji.zwbh.common.BasePresenter;

/**
 * Created by Administrator on 2017/2/19.
 */

public interface PaymentVoucherContract {

    interface Presenter extends BasePresenter {

        /**
         * 上传支付凭证
         */
        void uploadCerPic(int orderId, String img_url);

        /**
         * 上传图片
         */
        void postUpLoadImg(String path, int code);

        /**
         * 获取配置参数
         */
        void getAppConfig();
    }

    interface View extends BaseNewView<Presenter, String> {

    }
}
