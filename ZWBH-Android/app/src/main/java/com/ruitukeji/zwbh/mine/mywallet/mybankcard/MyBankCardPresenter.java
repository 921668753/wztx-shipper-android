package com.ruitukeji.zwbh.mine.mywallet.mybankcard;

import com.kymjs.rxvolley.client.HttpParams;
import com.ruitukeji.zwbh.retrofit.RequestClient;
import com.ruitukeji.zwbh.utils.httputil.HttpUtilParams;
import com.ruitukeji.zwbh.utils.httputil.ResponseListener;

/**
 * Created by Administrator on 2017/2/17.
 */

public class MyBankCardPresenter implements MyBankCardContract.Presenter {


    private MyBankCardContract.View mView;

    public MyBankCardPresenter(MyBankCardContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void getMyBankCard() {
        HttpParams httpParams = HttpUtilParams.getInstance().getHttpParams();
        RequestClient.getMyBankCard(httpParams, new ResponseListener<String>() {
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
