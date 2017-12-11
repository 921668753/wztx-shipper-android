package com.ruitukeji.zwbh.main.message;

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
 * Created by Administrator on 2017/2/15.
 */

public class MessagePresenter implements MessageContract.Presenter {


    private MessageContract.View mView;

    public MessagePresenter(MessageContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    /**
     * 获取消息列表
     *
     * @param page 页码
     */
    @Override
    public void getMessage(String type, int page) {
        mView.showLoadingDialog(KJActivityStack.create().topActivity().getString(R.string.dataLoad));
        HttpParams httpParams = HttpUtilParams.getInstance().getHttpParams();
        httpParams.put("push_type", type);
        httpParams.put("page", page);
        httpParams.put("pageSize", 20);
        RequestClient.getMessage(httpParams, new ResponseListener<String>() {
            @Override
            public void onSuccess(String response) {
                mView.getSuccess(response, 0);
            }

            @Override
            public void onFailure(String msg) {
                mView.error(msg, 0);
            }
        });
    }

    /**
     * 删除消息
     *
     * @param masageId 消息id
     */
    @Override
    public void postDeleteMessage(int masageId) {
        mView.showLoadingDialog(KJActivityStack.create().topActivity().getString(R.string.dataLoad));
        HttpParams httpParams = HttpUtilParams.getInstance().getHttpParams();
        Map map = new HashMap();
        map.put("msg_id", masageId);
        httpParams.putJsonParams(JsonUtil.getInstance().obj2JsonString(map).toString());
        RequestClient.postDeleteMessage(httpParams, new ResponseListener<String>() {
            @Override
            public void onSuccess(String response) {
                mView.getSuccess(response, 1);
            }

            @Override
            public void onFailure(String msg) {
                mView.error(msg, 1);
            }
        });
    }
}
