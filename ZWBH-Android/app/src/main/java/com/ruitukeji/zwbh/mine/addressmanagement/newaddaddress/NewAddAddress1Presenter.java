package com.ruitukeji.zwbh.mine.addressmanagement.newaddaddress;

import com.kymjs.rxvolley.client.HttpParams;
import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.application.MyApplication;
import com.ruitukeji.zwbh.retrofit.RequestClient;
import com.ruitukeji.zwbh.utils.httputil.HttpUtilParams;
import com.ruitukeji.zwbh.utils.httputil.ResponseListener;

/**
 * Created by Administrator on 2017/2/13.
 */

public class NewAddAddress1Presenter implements NewAddAddress1Contract.Presenter {

    private NewAddAddress1Contract.View mView;

    public NewAddAddress1Presenter(NewAddAddress1Contract.View view) {
        mView = view;
        mView.setPresenter(this);
    }


    @Override
    public void getAddress(int page, String type) {
        mView.showLoadingDialog(MyApplication.getContext().getString(R.string.dataLoad));
        HttpParams httpParams = HttpUtilParams.getInstance().getHttpParams();
        httpParams.put("page", page);
        httpParams.put("type", type);
        httpParams.put("pageSize", 10);
        RequestClient.getOrderList(httpParams, new ResponseListener<String>() {
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

    @Override
    public void postAddress(String longi, String lat, String provincialLevel, String address, String detailedAddress, String deliveryCustomer, String shipper, String phone, String eixedTelephone, int type) {
        mView.showLoadingDialog(MyApplication.getContext().getString(R.string.dataLoad));
        HttpParams httpParams = HttpUtilParams.getInstance().getHttpParams();
        httpParams.put("address_maps", longi + "," + lat);
        httpParams.put("city", provincialLevel);
        httpParams.put("address_name", address);
        httpParams.put("address_detail", detailedAddress);
        httpParams.put("client", deliveryCustomer);
        httpParams.put("client_name", shipper);
        httpParams.put("phone", phone);
        httpParams.put("telphone", eixedTelephone);
        if (type == 0 || type == 1) {
            httpParams.put("type", 0);
        } else if (type == 2 || type == 3) {
            httpParams.put("type", 1);
        }
        RequestClient.postAddress(httpParams, new ResponseListener<String>() {
            @Override
            public void onSuccess(String response) {
                mView.getSuccess(response, 1);
            }

            @Override
            public void onFailure(String msg) {
                mView.errorMsg(msg, 0);
            }
        });
    }
}
