package com.ruitukeji.zwbh.loginregister;

import com.kymjs.common.StringUtils;
import com.kymjs.rxvolley.client.HttpParams;
import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.application.MyApplication;
import com.ruitukeji.zwbh.retrofit.RequestClient;
import com.ruitukeji.zwbh.utils.httputil.HttpUtilParams;
import com.ruitukeji.zwbh.utils.httputil.ResponseListener;

import java.util.regex.Pattern;

/**
 * Created by Administrator on 2017/2/17.
 */

public class EnterpriseInformationPresenter implements EnterpriseInformationContract.Presenter {

    private EnterpriseInformationContract.View mView;

    public EnterpriseInformationPresenter(EnterpriseInformationContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }


    @Override
    public void postEnterpriseInformation(String com_name, String com_buss_num, String law_person, String identity, String phone, String address, String deposit_name, String bank, String account) {
        if (StringUtils.isEmpty(com_name)) {
            mView.error(MyApplication.getContext().getString(R.string.companyName1));
            return;
        }
        if (StringUtils.isEmpty(com_buss_num)) {
            mView.error(MyApplication.getContext().getString(R.string.registrationNumber1));
            return;
        }
        if (com_buss_num.length() < 10) {
            mView.error(MyApplication.getContext().getString(R.string.registrationNumber2));
            return;
        }
        if (StringUtils.isEmpty(law_person)) {
            mView.error(MyApplication.getContext().getString(R.string.pleaseFillOut) + MyApplication.getContext().getString(R.string.legalPersonName));
            return;
        }
        String all = "^[A-Za-z\\u4e00-\\u9fa5]{2,10}";//{2,10}表示字符的长度是2-10
        Pattern pattern = Pattern.compile(all);
        boolean tf = Pattern.matches(all, law_person);
        if (!tf) {
            mView.error(MyApplication.getContext().getString(R.string.hintName1));
            return;
        }
        if (StringUtils.isEmpty(identity)) {
            mView.error(MyApplication.getContext().getString(R.string.pleaseFillOut) + MyApplication.getContext().getString(R.string.legalPersonIdNumber));
            return;
        }
        if (identity.length() != 15 && identity.length() != 18) {
            mView.error(MyApplication.getContext().getString(R.string.legalPersonIdNumber1));
            return;
        }
        if (StringUtils.isEmpty(phone)) {
            mView.error(MyApplication.getContext().getString(R.string.pleaseFillOut) + MyApplication.getContext().getString(R.string.phoneNumber));
            return;
        }
        if (phone.length() <= 8) {
            mView.error(MyApplication.getContext().getString(R.string.pleaseFillOut) + MyApplication.getContext().getString(R.string.phoneNumber1));
            return;
        }
        if (StringUtils.isEmpty(address)) {
            mView.error(MyApplication.getContext().getString(R.string.pleaseFillOut) + MyApplication.getContext().getString(R.string.address));
            return;
        }
        if (StringUtils.isEmpty(deposit_name)) {
            mView.error(MyApplication.getContext().getString(R.string.accountName1));
            return;
        }
        if (StringUtils.isEmpty(bank)) {
            mView.error(MyApplication.getContext().getString(R.string.openingBank1));
            return;
        }
        if (StringUtils.isEmpty(account)) {
            mView.error(MyApplication.getContext().getString(R.string.settlementAccount1));
            return;
        }
        mView.getSuccess("", 0);
//        HttpParams httpParams = HttpUtilParams.getInstance().getHttpParams();
//        Map<String, Object> map = new HashMap<String, Object>();
//
//        httpParams.putJsonParams(JsonUtil.getInstance().obj2JsonString(map).toString());
//        RequestClient.postEnterpriseInformation(httpParams, new ResponseListener<String>() {
//            @Override
//            public void onSuccess(String response) {
//                mView.getSuccess(response);
//            }
//
//            @Override
//            public void onFailure(String msg) {
//                mView.error(msg);
//            }
//        });
    }


    @Override
    public void getCompanyAuthInfo() {
        mView.showLoadingDialog(MyApplication.getContext().getString(R.string.dataLoad));
        HttpParams httpParams = HttpUtilParams.getInstance().getHttpParams();

        RequestClient.getCompanyAuthInfo(httpParams, new ResponseListener<String>() {
            @Override
            public void onSuccess(String response) {
                mView.getSuccess(response, 1);
            }

            @Override
            public void onFailure(String msg) {
                mView.error(msg);
            }
        });
    }


}
