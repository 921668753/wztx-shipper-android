package com.ruitukeji.zwbh.mine.setting.userfeedback;

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
 * Created by Administrator on 2017/2/11.
 */

public class UserFeedbackPresenter implements UserFeedbackContract.Presenter {

    private UserFeedbackContract.View mView;

    public UserFeedbackPresenter(UserFeedbackContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void postUserFeedback(String content, String tel) {
        if (StringUtils.isEmpty(content)) {
            mView.errorMsg(KJActivityStack.create().topActivity().getString(R.string.yourFeedback), 0);
            return;
        }
        if (StringUtils.isEmpty(tel)) {
            mView.errorMsg(KJActivityStack.create().topActivity().getString(R.string.accountText), 0);
            return;
        }
        if (tel.length() != 11) {
            mView.errorMsg(KJActivityStack.create().topActivity().getString(R.string.inputPhone), 0);
            return;
        }
        mView.showLoadingDialog(KJActivityStack.create().topActivity().getString(R.string.submissionLoad));
        HttpParams httpParams = HttpUtilParams.getInstance().getHttpParams();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("content", content);
        map.put("tel", tel);
        httpParams.putJsonParams(JsonUtil.getInstance().obj2JsonString(map).toString());
        RequestClient.postUserFeedback(httpParams, new ResponseListener<String>() {
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
