package com.ruitukeji.zwbh.mine.mypublishedorder.publishedorderfragment;

import com.kymjs.rxvolley.client.HttpParams;
import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.application.MyApplication;
import com.ruitukeji.zwbh.mine.mypublishedorder.publishedorderfragment.QuotationListContract;
import com.ruitukeji.zwbh.retrofit.RequestClient;
import com.ruitukeji.zwbh.utils.JsonUtil;
import com.ruitukeji.zwbh.utils.httputil.HttpUtilParams;
import com.ruitukeji.zwbh.utils.httputil.ResponseListener;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/6.
 */

public class QuotationListPresenter implements QuotationListContract.Presenter {

    private QuotationListContract.View mView;

    public QuotationListPresenter(QuotationListContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }


    @Override
    public void getQuotationList(int orderId) {
        mView.showLoadingDialog(MyApplication.getContext().getString(R.string.dataLoad));
        HttpParams httpParams = HttpUtilParams.getInstance().getHttpParams();
        httpParams.put("goods_id", orderId);
        httpParams.put("pageSize", 1000);
        RequestClient.getShowDriverQuoteList(httpParams, new ResponseListener<String>() {
            @Override
            public void onSuccess(String response) {
                mView.getSuccess(response, 0);
            }

            @Override
            public void onFailure(String msg) {
                mView.error(msg);
            }
        });

    }

    @Override
    public void postQuotation(int quote_id, int order_id) {
        mView.showLoadingDialog(MyApplication.getContext().getString(R.string.submissionLoad));
        HttpParams httpParams = HttpUtilParams.getInstance().getHttpParams();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("quote_id", quote_id);
        map.put("goods_id", order_id);
        httpParams.putJsonParams(JsonUtil.getInstance().obj2JsonString(map).toString());
        RequestClient.sendDriverPrice(httpParams, new ResponseListener<String>() {
            @Override
            public void onSuccess(String response) {
                mView.getSuccess(response, 1);
            }

            @Override
            public void onFailure(String msg) {
                mView.error(msg);
            }
        });
    }
}
