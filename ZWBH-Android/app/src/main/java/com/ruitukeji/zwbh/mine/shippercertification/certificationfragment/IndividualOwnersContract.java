package com.ruitukeji.zwbh.mine.shippercertification.certificationfragment;

import com.ruitukeji.zwbh.common.BaseNewView;
import com.ruitukeji.zwbh.common.BasePresenter;

/**
 * Created by Administrator on 2017/2/11.
 */

public interface IndividualOwnersContract {

    interface Presenter extends BasePresenter {
        /**
         * 提交个人货主信息
         */
        void postIndividualOwners();

        /**
         * 获取个人货主信息
         */
        void getIndividualOwners();


        /**
         * 上传头像
         */
        void postUpLoadImg(String path, int flag);
    }

    interface View extends BaseNewView<Presenter, String> {

    }
}
