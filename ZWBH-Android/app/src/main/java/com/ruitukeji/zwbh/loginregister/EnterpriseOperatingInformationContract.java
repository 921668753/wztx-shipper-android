package com.ruitukeji.zwbh.loginregister;

import com.ruitukeji.zwbh.common.BasePresenter;
import com.ruitukeji.zwbh.common.BaseView;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Administrator on 2017/2/17.
 */

public interface EnterpriseOperatingInformationContract {
    interface Presenter extends BasePresenter {
        /**
         * 发送操作人信息
         */
        void postEnterpriseOperatingInformation(SweetAlertDialog sweetAlertDialog, String com_name, String com_buss_num, String law_person, String identity, String phone, String address, String deposit_name, String bank, String account, String hold_pic, String front_pic, String back_pic, String businessLicense_pic, String sp_identity, String sp_hold_pic, String sp_front_pic, String sp_back_pic);

        /**
         * 上传图片
         */
        void upLoadImg(String path, int flag);
    }

    interface View extends BaseView<Presenter> {
        /**
         * http请求正确
         *
         * @param s
         */
        void getSuccess(String s, int flag);

        /**
         * http请求错误
         */
        void error(String msg);
    }
}
