package com.ruitukeji.zwbh.main.message;

import com.kymjs.rxvolley.client.HttpParams;
import com.ruitukeji.zwbh.retrofit.RequestClient;
import com.ruitukeji.zwbh.utils.httputil.HttpUtilParams;
import com.ruitukeji.zwbh.utils.httputil.ResponseListener;

/**
 * Created by Administrator on 2017/2/15.
 */

public class MessageCenterPresenter implements MessageCenterContract.Presenter {


    private MessageCenterContract.View mView;

    public MessageCenterPresenter(MessageCenterContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }


    @Override
    public void getUnRead() {
        HttpParams httpParams = HttpUtilParams.getInstance().getHttpParams();
        RequestClient.getUnRead(httpParams, new ResponseListener<String>() {
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

    @Override
    public void isLogin(int flag) {
        RequestClient.isLogin(new ResponseListener<String>() {
            @Override
            public void onSuccess(String response) {
                mView.getSuccess(response, flag);
            }

            @Override
            public void onFailure(String msg) {
                mView.error(msg, flag);
            }
        });
    }
}
