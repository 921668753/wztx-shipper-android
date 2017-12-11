package com.ruitukeji.zwbh.mine.mywallet.billfragment;

import com.kymjs.rxvolley.client.HttpParams;
import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.application.MyApplication;
import com.ruitukeji.zwbh.retrofit.RequestClient;
import com.ruitukeji.zwbh.utils.httputil.HttpUtilParams;
import com.ruitukeji.zwbh.utils.httputil.ResponseListener;


/**
 * Created by Administrator on 2017/2/13.
 */

public class BillPresenter implements BillContract.Presenter {

    private BillContract.View mView;

    public BillPresenter(BillContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void getBill(int page, int is_pay) {
        HttpParams httpParams = HttpUtilParams.getInstance().getHttpParams();
        httpParams.put("page", page);
        httpParams.put("pageSize", 10);
        httpParams.put("is_pay", is_pay);
        RequestClient.getPayRecord(httpParams, new ResponseListener<String>() {
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
