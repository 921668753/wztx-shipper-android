package com.ruitukeji.zwbh.mine.mywallet.accountdetails.fragment;

import com.kymjs.rxvolley.client.HttpParams;
import com.ruitukeji.zwbh.retrofit.RequestClient;
import com.ruitukeji.zwbh.utils.httputil.HttpUtilParams;
import com.ruitukeji.zwbh.utils.httputil.ResponseListener;


/**
 * Created by Administrator on 2017/2/13.
 */

public class AccountDetailsPresenter implements AccountDetailsContract.Presenter {

    private AccountDetailsContract.View mView;

    public AccountDetailsPresenter(AccountDetailsContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void getAccountDetails(int page, int is_pay) {
        HttpParams httpParams = HttpUtilParams.getInstance().getHttpParams();
        httpParams.put("page", page);
        httpParams.put("pageSize", 10);
        httpParams.put("is_pay", is_pay);
        RequestClient.getPayRecord(httpParams, new ResponseListener<String>() {
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
