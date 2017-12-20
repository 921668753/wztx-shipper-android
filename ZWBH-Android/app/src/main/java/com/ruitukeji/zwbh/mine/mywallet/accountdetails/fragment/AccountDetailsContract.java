package com.ruitukeji.zwbh.mine.mywallet.accountdetails.fragment;

import com.ruitukeji.zwbh.common.BaseNewView;
import com.ruitukeji.zwbh.common.BasePresenter;
import com.ruitukeji.zwbh.common.BaseView;

/**
 * Created by Administrator on 2017/2/13.
 */

public interface AccountDetailsContract {
    interface Presenter extends BasePresenter {
        /**
         * 获取订单列表信息
         */
        void getAccountDetails(int page, int is_pay);
    }

    interface View extends BaseNewView<Presenter, String> {

    }

}
