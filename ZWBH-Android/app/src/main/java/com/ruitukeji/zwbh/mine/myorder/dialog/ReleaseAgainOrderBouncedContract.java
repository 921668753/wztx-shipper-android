package com.ruitukeji.zwbh.mine.myorder.dialog;

import com.ruitukeji.zwbh.common.BaseNewView;
import com.ruitukeji.zwbh.common.BasePresenter;

/**
 * 再次发布弹框
 * Created by Administrator on 2017/2/21.
 */

public interface ReleaseAgainOrderBouncedContract {


    interface Presenter extends BasePresenter {
        /**
         * 再次发布弹框
         */
        void postReleaseAgain(int orderId);
    }

    interface View extends BaseNewView<Presenter, String> {

    }

}
