package com.ruitukeji.zwbh.mine.personaldata;


import com.kymjs.rxvolley.client.HttpParams;
import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.application.MyApplication;
import com.ruitukeji.zwbh.retrofit.RequestClient;
import com.ruitukeji.zwbh.utils.JsonUtil;
import com.ruitukeji.zwbh.utils.httputil.HttpUtilParams;
import com.ruitukeji.zwbh.utils.httputil.ResponseListener;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/2/24.
 */

public class PayDepositPresenter implements PayDepositContract.Presenter {


    private PayDepositContract.View mView;

    public PayDepositPresenter(PayDepositContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }


    @Override
    public void getPayDeposit(int pay_way) {
        HttpParams httpParams = HttpUtilParams.getInstance().getHttpParams();
//        Map<String, Object> map = new HashMap<String, Object>();
//        map.put("pay_way", pay_way);
        //   httpParams.putJsonParams(JsonUtil.getInstance().obj2JsonString(map).toString());
        RequestClient.getPayBond(httpParams, pay_way, new ResponseListener<String>() {
            @Override
            public void onSuccess(String response) {
                mView.getSuccess(response, pay_way);
            }

            @Override
            public void onFailure(String msg) {
                mView.error(msg);
            }
        });
    }

    @Override
    public void getAppConfig() {
        HttpParams httpParams = HttpUtilParams.getInstance().getHttpParams();
        RequestClient.getAppConfig(httpParams, new ResponseListener<String>() {
            @Override
            public void onSuccess(String response) {
                mView.getSuccess(response, 0);
            }

            @Override
            public void onFailure(String msg) {
                mView.error(msg);
            }
        });
    }
}
