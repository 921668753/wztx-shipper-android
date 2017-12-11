package com.ruitukeji.zwbh.loginregister;

import com.ruitukeji.zwbh.common.BasePresenter;
import com.ruitukeji.zwbh.common.BaseView;

/**
 * Created by Administrator on 2017/2/17.
 */

public interface EnterpriseInformationContract {
    interface Presenter extends BasePresenter {
        /**
         * 发送企业信息
         *
         * @param com_name     企业全称
         * @param com_buss_num 营业执照注册号
         * @param law_person   企业法人姓名
         * @param identity     法人身份证号
         * @param phone        企业联系电话
         * @param address      地址
         * @param deposit_name 开户名称
         * @param bank         开户行
         * @param account      结算账号
         */
        void postEnterpriseInformation(String com_name, String com_buss_num, String law_person, String identity, String phone, String address, String deposit_name, String bank, String account);

        /**
         * 获取企业个人认证信息
         */
        void getCompanyAuthInfo();


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
