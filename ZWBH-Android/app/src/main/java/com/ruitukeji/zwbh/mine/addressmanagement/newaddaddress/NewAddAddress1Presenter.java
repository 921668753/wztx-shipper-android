package com.ruitukeji.zwbh.mine.addressmanagement.newaddaddress;

import com.kymjs.rxvolley.client.HttpParams;
import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.application.MyApplication;
import com.ruitukeji.zwbh.retrofit.RequestClient;
import com.ruitukeji.zwbh.utils.JsonUtil;
import com.ruitukeji.zwbh.utils.httputil.HttpUtilParams;
import com.ruitukeji.zwbh.utils.httputil.ResponseListener;

import java.util.HashMap;
import java.util.Map;

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
    public void getAddress(int id) {
        mView.showLoadingDialog(MyApplication.getContext().getString(R.string.dataLoad));
        HttpParams httpParams = HttpUtilParams.getInstance().getHttpParams();
        httpParams.put("id", id);
        RequestClient.getOneInfoAddress(httpParams, new ResponseListener<String>() {
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
    public void postAddress(String longi, String lat, String provincialLevel, String address, String detailedAddress, String deliveryCustomer, String shipper, String phone, String eixedTelephone, int type, int is_default) {
        mView.showLoadingDialog(MyApplication.getContext().getString(R.string.dataLoad));
        HttpParams httpParams = HttpUtilParams.getInstance().getHttpParams();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("address_maps", longi + "," + lat);
        map.put("city", provincialLevel);
        map.put("address_name", address);
        map.put("address_detail", detailedAddress);
        map.put("client", deliveryCustomer);
        map.put("client_name", shipper);
        map.put("phone", phone);
        map.put("telphone", eixedTelephone);
        if (type == 0 || type == 1) {
            map.put("type", 0);
        } else if (type == 2 || type == 3) {
            map.put("type", 1);
        }
        map.put("is_default", is_default);
        httpParams.putJsonParams(JsonUtil.getInstance().obj2JsonString(map).toString());
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

    @Override
    public void postUpdateAddress(String address_maps, String provincialLevel, String address, String detailedAddress, String deliveryCustomer, String shipper, String phone, String eixedTelephone, int id, int type, int is_default) {
        mView.showLoadingDialog(MyApplication.getContext().getString(R.string.dataLoad));
        HttpParams httpParams = HttpUtilParams.getInstance().getHttpParams();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("address_maps", address_maps);
        map.put("city", provincialLevel);
        map.put("address_name", address);
        map.put("address_detail", detailedAddress);
        map.put("client", deliveryCustomer);
        map.put("client_name", shipper);
        map.put("phone", phone);
        map.put("telphone", eixedTelephone);
        if (type == 0 || type == 1) {
            map.put("type", 0);
        } else if (type == 2 || type == 3) {
            map.put("type", 1);
        }
        map.put("id", id);
        map.put("is_default", is_default);
        httpParams.putJsonParams(JsonUtil.getInstance().obj2JsonString(map).toString());
        RequestClient.postUpdateAddress(httpParams, new ResponseListener<String>() {
            @Override
            public void onSuccess(String response) {
                mView.getSuccess(response, 2);
            }

            @Override
            public void onFailure(String msg) {
                mView.errorMsg(msg, 0);
            }
        });
    }
}
