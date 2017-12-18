package com.ruitukeji.zwbh.mine.shippercertification.certificationfragment;

import com.ruitukeji.zwbh.common.BaseNewView;
import com.ruitukeji.zwbh.common.BasePresenter;

/**
 * Created by Administrator on 2017/2/11.
 */

public interface CompanyOwnerContract {

    interface Presenter extends BasePresenter {
        /**
         * 提交公司货主信息
         */
        void postCompanyOwner();

        /**
         * 获取公司货主信息
         */
        void getCompanyOwner();

        /**
         * 上传头像
         */
        void postUpLoadImg(String path, int flag);

    }

    interface View extends BaseNewView<Presenter, String> {

    }
}
