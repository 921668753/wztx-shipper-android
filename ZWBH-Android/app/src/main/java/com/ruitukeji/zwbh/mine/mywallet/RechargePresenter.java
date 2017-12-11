package com.ruitukeji.zwbh.mine.mywallet;

import com.kymjs.common.StringUtils;
import com.kymjs.rxvolley.client.HttpParams;
import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.application.MyApplication;
import com.ruitukeji.zwbh.retrofit.RequestClient;
import com.ruitukeji.zwbh.utils.JsonUtil;
import com.ruitukeji.zwbh.utils.MathUtil;
import com.ruitukeji.zwbh.utils.httputil.HttpUtilParams;
import com.ruitukeji.zwbh.utils.httputil.ResponseListener;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/2/17.
 */

public class RechargePresenter implements RechargeContract.Presenter {

    private RechargeContract.View mView;

    public RechargePresenter(RechargeContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void postRecharge(String money, int type) {
        if (StringUtils.isEmpty(money)) {
            mView.error(MyApplication.getContext().getString(R.string.inputAmountFull));
            return;
        }
        if (!(MathUtil.judgeTwoDecimal(money))) {
            mView.error(MyApplication.getContext().getString(R.string.inputAmountFull1));
            return;
        }
        mView.showLoadingDialog(MyApplication.getContext().getString(R.string.submissionLoad));
        HttpParams httpParams = HttpUtilParams.getInstance().getHttpParams();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("money", money);
        httpParams.putJsonParams(JsonUtil.getInstance().obj2JsonString(map).toString());
        RequestClient.postRecharge(httpParams, type, new ResponseListener<String>() {
            @Override
            public void onSuccess(String response) {
                mView.getSuccess(response, type);
            }

            @Override
            public void onFailure(String msg) {
                mView.error(msg);
            }
        });
    }
}
