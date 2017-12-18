package com.ruitukeji.zwbh.mine;

import com.ruitukeji.zwbh.common.BaseNewView;
import com.ruitukeji.zwbh.common.BasePresenter;
import com.ruitukeji.zwbh.common.BaseView;

/**
 * Created by Administrator on 2017/2/11.
 */

public interface PersonalCenterContract {

    interface Presenter extends BasePresenter {
        /**
         * 获取用户信息
         */
        void getInfo();

        /**
         * 是否登录
         */
        void isLogin(int flag);
    }

    interface View extends BaseNewView<Presenter, String> {

    }
}
