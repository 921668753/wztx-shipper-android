package com.ruitukeji.zwbh.mine.myorder.orderdetails.dialog;

import com.kymjs.rxvolley.client.HttpParams;
import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.application.MyApplication;
import com.ruitukeji.zwbh.utils.JsonUtil;
import com.ruitukeji.zwbh.utils.httputil.HttpUtilParams;

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
    public void postComplaintsAboutDriver(String id, String content) {
        mView.showLoadingDialog(MyApplication.getContext().getString(R.string.submissionLoad));
        HttpParams httpParams = HttpUtilParams.getInstance().getHttpParams();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", id);
        map.put("content", content);
        httpParams.putJsonParams(JsonUtil.getInstance().obj2JsonString(map).toString());
//        RequestClient.getQuoteAdd(httpParams, new ResponseListener<String>() {
//            @Override
//            public void onSuccess(String response) {
//                mView.getSuccess(response, 1);
//            }
//
//            @Override
//            public void onFailure(String msg) {
//                mView.error(msg);
//            }
//        });
    }
}
