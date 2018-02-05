package com.ruitukeji.zwbh.mine.myorder.payment;

import com.kymjs.rxvolley.client.HttpParams;
import com.ruitukeji.zwbh.mine.myorder.payment.CheckVoucherContract;
import com.ruitukeji.zwbh.retrofit.RequestClient;
import com.ruitukeji.zwbh.utils.httputil.HttpUtilParams;
import com.ruitukeji.zwbh.utils.httputil.ResponseListener;

/**
 * Created by Administrator on 2017/2/16.
 */

public class CheckVoucherPresenter implements CheckVoucherContract.Presenter {


    private CheckVoucherContract.View mView;

    public CheckVoucherPresenter(CheckVoucherContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void getCheckVoucher(int order_id) {
        HttpParams httpParams = HttpUtilParams.getInstance().getHttpParams();
        httpParams.put("order_id", order_id);
        RequestClient.getShowCerPic(httpParams, new ResponseListener<String>() {
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
