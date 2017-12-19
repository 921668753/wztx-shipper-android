package com.ruitukeji.zwbh.mine.setting.userfeedback;

import com.ruitukeji.zwbh.common.BaseNewView;
import com.ruitukeji.zwbh.common.BasePresenter;
import com.ruitukeji.zwbh.common.BaseView;

/**
 * Created by Administrator on 2017/2/11.
 */

public interface UserFeedbackContract {

    interface Presenter extends BasePresenter {
        /**
         * 发送用户反馈
         */
        void postUserFeedback();
    }

    interface View extends BaseNewView<Presenter, String> {

    }
}
