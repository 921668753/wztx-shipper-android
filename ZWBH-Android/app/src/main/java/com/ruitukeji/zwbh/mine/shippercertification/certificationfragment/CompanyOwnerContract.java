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
        void postCompanyOwner(String com_name, String com_buss_num, String address, String phone, String buss_pic, String law_person, String identity, String front_pic,
                              String back_pic, String hold_pic, String sp_identity_name, String sp_identity, String sp_front_pic, String sp_back_pic, String sp_hold_pic);

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
