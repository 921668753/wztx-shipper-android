package com.ruitukeji.zwbh.mine.myorder.orderdetails.dialog;

import com.kymjs.common.StringUtils;
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
 * Created by Admin on 2017/7/12.
 */

public class ComplaintsAboutDriverBouncedPresenter implements ComplaintsAboutDriverBouncedContract.Presenter {


    private ComplaintsAboutDriverBouncedContract.View mView;

    public ComplaintsAboutDriverBouncedPresenter(ComplaintsAboutDriverBouncedContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void postComplaintsAboutDriver(int dr_id, String reason) {
        if (dr_id < 1) {
            mView.error(KJActivityStack.create().topActivity().getString(R.string.serverReturnsDataError));
            return;
        }
        if (StringUtils.isEmpty(reason)) {
            mView.error(KJActivityStack.create().topActivity().getString(R.string.describeSpecificReasons));
            return;
        }
        HttpParams httpParams = HttpUtilParams.getInstance().getHttpParams();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("dr_id", dr_id);
        map.put("reason", reason);
        httpParams.putJsonParams(JsonUtil.getInstance().obj2JsonString(map).toString());
        RequestClient.postComplaintDriver(httpParams, new ResponseListener<String>() {
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
