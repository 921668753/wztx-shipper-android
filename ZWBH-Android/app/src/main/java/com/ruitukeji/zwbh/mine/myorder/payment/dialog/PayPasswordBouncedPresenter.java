package com.ruitukeji.zwbh.mine.myorder.payment.dialog;

import com.kymjs.common.CipherUtils;
import com.kymjs.rxvolley.client.HttpParams;
import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.common.KJActivityStack;
import com.ruitukeji.zwbh.retrofit.RequestClient;
import com.ruitukeji.zwbh.utils.JsonUtil;
import com.ruitukeji.zwbh.utils.httputil.HttpUtilParams;
import com.ruitukeji.zwbh.utils.httputil.ResponseListener;

import java.util.HashMap;
import java.util.Map;

/**
 * 支付密码弹框
 * Created by Administrator on 2017/2/21.
 */

public class PayPasswordBouncedPresenter implements PayPasswordBouncedContract.Presenter {

    private PayPasswordBouncedContract.View mView;

    public PayPasswordBouncedPresenter(PayPasswordBouncedContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void postOldPayPassword(String oldPaymentPassword) {
        if (oldPaymentPassword.length() != 6) {
            mView.errorMsg(KJActivityStack.create().topActivity().getString(R.string.pleaseEnterPaymentPassword1), 0);
            return;
        }
        HttpParams httpParams = HttpUtilParams.getInstance().getHttpParams();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("old_password", CipherUtils.md5("WUZAI" + oldPaymentPassword + "TIANXIA"));
        httpParams.putJsonParams(JsonUtil.getInstance().obj2JsonString(map).toString());
        RequestClient.getCheckPayPassword(httpParams, new ResponseListener<String>() {
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
    public void postScorePay(int orderId) {
        HttpParams httpParams = HttpUtilParams.getInstance().getHttpParams();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("order_id", orderId);
        httpParams.putJsonParams(JsonUtil.getInstance().obj2JsonString(map).toString());
        RequestClient.postScorePay(httpParams, new ResponseListener<String>() {
            @Override
            public void onSuccess(String response) {
                mView.getSuccess(response, 1);
            }

            @Override
            public void onFailure(String msg) {
                mView.errorMsg(msg,1);
            }
        });
    }
}
