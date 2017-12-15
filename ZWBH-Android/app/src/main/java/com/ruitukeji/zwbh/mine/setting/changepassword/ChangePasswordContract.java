package com.ruitukeji.zwbh.mine.setting.changepassword;

import com.ruitukeji.zwbh.common.BasePresenter;
import com.ruitukeji.zwbh.common.BaseView;

/**
 * Created by Administrator on 2017/2/11.
 */

public interface ChangePasswordContract {

    interface Presenter extends BasePresenter {
        /**
         * 发送加密数据
         */
        void postChangePassword(String originalPassword, String newPassword, String newPassword1);

    }

    interface View extends BaseView<Presenter> {
        /**
         * http请求正确
         *
         * @param s
         */
        void getSuccess(String s);

        /**
         * http请求错误
         */
        void error(String flag);

        /**
         * 清空输入框
         */
        void clear(int flag);

    }
}
