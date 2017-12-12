package com.ruitukeji.zwbh.loginregister;

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

import cn.jpush.android.api.JPushInterface;

/**
 * Created by ruitu on 2016/9/24.
 */

public class LoginPresenter implements LoginContract.Presenter {
    private LoginContract.View mView;

    public LoginPresenter(LoginContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void postToLogin(String phone, String pwd) {
        if (StringUtils.isEmpty(phone)) {
            mView.errorMsg(MyApplication.getContext().getString(R.string.hintAccountText), 0);
            return;
        }
        if (phone.length() != 11) {
            mView.errorMsg(MyApplication.getContext().getString(R.string.inputPhone), 0);
            return;
        }
        if (StringUtils.isEmpty(pwd)) {
            mView.errorMsg(MyApplication.getContext().getString(R.string.hintPasswordText), 0);
            return;
        }
        if (pwd.length() < 6 || pwd.length() > 20) {
            mView.errorMsg(KJActivityStack.create().topActivity().getString(R.string.hintPasswordText1), 0);
            return;
        }
        HttpParams httpParams = HttpUtilParams.getInstance().getHttpParams();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("account", phone);
        map.put("password", CipherUtils.md5("RUITU" + pwd + "KEJI"));
        map.put("wxOpenid", "");
        map.put("pushToken", JPushInterface.getRegistrationID(KJActivityStack.create().topActivity()));
        httpParams.putJsonParams(JsonUtil.getInstance().obj2JsonString(map).toString());
        RequestClient.postLogin(httpParams, new ResponseListener<String>() {
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

    @Override
    public void postThirdToLogin(String openid, String from, String nickname, String head_pic, int sex) {
        HttpParams httpParams = HttpUtilParams.getInstance().getHttpParams();
        //   Map<String, Object> map = new HashMap<String, Object>();
        httpParams.put("openid", openid);
        httpParams.put("from", from);
        httpParams.put("nickname", nickname);
        httpParams.put("head_pic", head_pic);
        httpParams.put("sex", sex);
        httpParams.put("push_id", JPushInterface.getRegistrationID(KJActivityStack.create().topActivity()));
        // httpParams.putJsonParams(JsonUtil.getInstance().obj2JsonString(map).toString());
//        RequestClient.postThirdLogin(httpParams, new ResponseListener<String>() {
//            @Override
//            public void onSuccess(String response) {
//                mView.getSuccess(response, 2);
//            }
//
//            @Override
//            public void onFailure(String msg) {
//                mView.errorMsg(msg, 1);
//            }
//        });
    }

}
