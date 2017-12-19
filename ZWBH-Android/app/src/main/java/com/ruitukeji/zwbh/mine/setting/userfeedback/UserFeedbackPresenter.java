package com.ruitukeji.zwbh.mine.setting.userfeedback;

import com.kymjs.rxvolley.client.HttpParams;
import com.kymjs.rxvolley.client.ProgressListener;
import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.common.KJActivityStack;
import com.ruitukeji.zwbh.retrofit.RequestClient;
import com.ruitukeji.zwbh.utils.JsonUtil;
import com.ruitukeji.zwbh.utils.MathUtil;
import com.ruitukeji.zwbh.utils.httputil.HttpUtilParams;
import com.ruitukeji.zwbh.utils.httputil.ResponseListener;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/2/11.
 */

public class UserFeedbackPresenter implements UserFeedbackContract.Presenter {

    private UserFeedbackContract.View mView;

    public UserFeedbackPresenter(UserFeedbackContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void postUserFeedback() {
        mView.showLoadingDialog(KJActivityStack.create().topActivity().getString(R.string.sendingLoad));
        HttpParams httpParams = HttpUtilParams.getInstance().getHttpParams();
//        Map<String, Object> map = new HashMap<String, Object>();
//        map.put("is_ad", isAd);
//        httpParams.putJsonParams(JsonUtil.getInstance().obj2JsonString(map).toString());
        RequestClient.postChangeAd(httpParams, new ResponseListener<String>() {
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
