package com.ruitukeji.zwbh.mine.mywallet.paymentpasswordmanagement.modifypaymentpassword;

import com.kymjs.common.CipherUtils;
import com.kymjs.common.StringUtils;
import com.kymjs.rxvolley.client.HttpParams;
import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.application.MyApplication;
import com.ruitukeji.zwbh.common.KJActivityStack;
import com.ruitukeji.zwbh.retrofit.RequestClient;
import com.ruitukeji.zwbh.utils.JsonUtil;
import com.ruitukeji.zwbh.utils.httputil.HttpUtilParams;
import com.ruitukeji.zwbh.utils.httputil.ResponseListener;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/2/15.
 */

public class ModifyPaymentPasswordPresenter implements ModifyPaymentPasswordContract.Presenter {


    private ModifyPaymentPasswordContract.View mView;

    public ModifyPaymentPasswordPresenter(ModifyPaymentPasswordContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void postCode(String phone, String type) {
        if (StringUtils.isEmpty(phone)) {
            mView.errorMsg(MyApplication.getContext().getString(R.string.hintAccountText), 0);
            return;
        }
        if (phone.length() != 11) {
            mView.errorMsg(MyApplication.getContext().getString(R.string.inputPhone), 0);
            return;
        }
        HttpParams httpParams = HttpUtilParams.getInstance().getHttpParams();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("mobile", phone);
        String codeI = String.valueOf(System.currentTimeMillis());
        String codeId = CipherUtils.md5(codeI.substring(2, codeI.length() - 1));
        map.put("codeId", codeId);
        String validationI = phone.substring(1, phone.length() - 1) + codeId.substring(3, codeId.length() - 1);
        String validationId = CipherUtils.md5(validationI);
        map.put("validationId", validationId);
        map.put("opt", type);
        httpParams.putJsonParams(JsonUtil.getInstance().obj2JsonString(map).toString());
        RequestClient.postCaptcha(httpParams, new ResponseListener<String>() {
            @Override
            public void onSuccess(String response) {
                mView.getSuccess(response, 0);
            }

            @Override
            public void onFailure(String msg) {
                mView.errorMsg(msg, 0);
            }
        });
    }

//    @Override
//    public void postModifyPaymentPassword(String is_remember, String captcha, String identity, String old_password, String pay_password) {
//
//    }

//    @Override
//    public void postVerificationCode(String phone, String code) {
//        if (StringUtils.isEmpty(phone)) {
//            mView.errorMsg(MyApplication.getContext().getString(R.string.hintAccountText), 0);
//            return;
//        }
//        if (phone.length() != 11) {
//            mView.errorMsg(MyApplication.getContext().getString(R.string.inputPhone), 0);
//            return;
//        }
//        if (StringUtils.isEmpty(code)) {
//            mView.errorMsg(MyApplication.getContext().getString(R.string.errorCode), 0);
//            return;
//        }
//        HttpParams httpParams = HttpUtilParams.getInstance().getHttpParams();
//        Map<String, Object> map = new HashMap<String, Object>();
//        map.put("account", phone);
//        map.put("captcha", code);
//        httpParams.putJsonParams(JsonUtil.getInstance().obj2JsonString(map).toString());
//        RequestClient.postResetpwd(httpParams, new ResponseListener<String>() {
//            @Override
//            public void onSuccess(String response) {
//                mView.getSuccess(response, 1);
//            }
//
//            @Override
//            public void onFailure(String msg) {
//                mView.errorMsg(msg, 0);
//            }
//        });
//    }

//    @Override
//    public void postVerifyIdNumber(String idNumber) {
//        if (StringUtils.isEmpty(idNumber)) {
//            mView.errorMsg(MyApplication.getContext().getString(R.string.pleaseFillOut) + MyApplication.getContext().getString(R.string.IdNumber), 2);
//            return;
//        }
//        if (idNumber.length() != 15 && idNumber.length() != 18) {
//            mView.errorMsg(MyApplication.getContext().getString(R.string.hintIDerrorText), 2);
//            return;
//        }
//        HttpParams httpParams = HttpUtilParams.getInstance().getHttpParams();
//        Map<String, Object> map = new HashMap<String, Object>();
//        map.put("idNumber", idNumber);
//        httpParams.putJsonParams(JsonUtil.getInstance().obj2JsonString(map).toString());
//        RequestClient.postResetpwd(httpParams, new ResponseListener<String>() {
//            @Override
//            public void onSuccess(String response) {
//                mView.getSuccess(response, 2);
//            }
//
//            @Override
//            public void onFailure(String msg) {
//                mView.errorMsg(msg, 2);
//            }
//        });
//    }


    @Override
    public void postModifyPaymentPassword(String oldPaymentPassword, String paymentPassword) {
        if (paymentPassword.length() < 6) {
            mView.errorMsg(KJActivityStack.create().topActivity().getString(R.string.pleaseEnterPaymentPassword1), 0);
            return;
        }
        if (oldPaymentPassword.equals(paymentPassword)) {
            mView.errorMsg(KJActivityStack.create().topActivity().getString(R.string.paymentPasswordsNotMatch), 0);
            return;
        }
        HttpParams httpParams = HttpUtilParams.getInstance().getHttpParams();
        RequestClient.getHome(httpParams, new ResponseListener<String>() {
            @Override
            public void onSuccess(String response) {
                mView.getSuccess(response, 0);
            }

            @Override
            public void onFailure(String msg) {
                mView.errorMsg(msg, 0);
            }
        });
    }
}