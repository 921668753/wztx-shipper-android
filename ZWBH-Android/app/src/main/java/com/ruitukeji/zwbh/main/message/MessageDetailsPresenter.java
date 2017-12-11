package com.ruitukeji.zwbh.main.message;

import com.kymjs.rxvolley.client.HttpParams;
import com.ruitukeji.zwbh.main.message.MessageDetailsContract;
import com.ruitukeji.zwbh.retrofit.RequestClient;
import com.ruitukeji.zwbh.utils.httputil.HttpUtilParams;
import com.ruitukeji.zwbh.utils.httputil.ResponseListener;

/**
 * Created by Administrator on 2017/2/15.
 */

public class MessageDetailsPresenter implements MessageDetailsContract.Presenter {

    private MessageDetailsContract.View mView;

    public MessageDetailsPresenter(MessageDetailsContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }


    @Override
    public void getMessageDetails(int massageId) {
        HttpParams httpParams = HttpUtilParams.getInstance().getHttpParams();
        httpParams.put("id", massageId);
        RequestClient.getMessageDetails(httpParams, new ResponseListener<String>() {
            @Override
            public void onSuccess(String response) {
                mView.getSuccess(response);
            }

            @Override
            public void onFailure(String msg) {
                mView.error(msg);
            }
        });


    }
}
