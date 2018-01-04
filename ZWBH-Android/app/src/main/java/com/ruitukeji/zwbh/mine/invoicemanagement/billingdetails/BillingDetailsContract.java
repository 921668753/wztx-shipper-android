package com.ruitukeji.zwbh.mine.invoicemanagement.billingdetails;

import com.ruitukeji.zwbh.common.BaseNewView;
import com.ruitukeji.zwbh.common.BasePresenter;

/**
 * Created by Administrator on 2017/2/24.
 */

public interface BillingDetailsContract {


    interface Presenter extends BasePresenter {
        /**
         * 获取开票详情
         */
        void getBillingDetails(int id);


        /**
         * 是否登录
         */
        void isLogin(int flag);

    }

    interface View extends BaseNewView<Presenter, String> {

    }


}
