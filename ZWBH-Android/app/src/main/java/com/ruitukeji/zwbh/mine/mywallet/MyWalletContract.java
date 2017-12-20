package com.ruitukeji.zwbh.mine.mywallet;

import com.ruitukeji.zwbh.common.BaseNewView;
import com.ruitukeji.zwbh.common.BasePresenter;
import com.ruitukeji.zwbh.common.BaseView;

/**
 * Created by Administrator on 2017/2/15.
 */

public interface MyWalletContract {

    interface Presenter extends BasePresenter {
        /**
         * 获取我的钱包信息
         */
        void getMyWallet();

        /**
         * 是否登录
         */
        void isLogin(int flag);
    }

    interface View extends BaseNewView<Presenter, String> {
    }

}
