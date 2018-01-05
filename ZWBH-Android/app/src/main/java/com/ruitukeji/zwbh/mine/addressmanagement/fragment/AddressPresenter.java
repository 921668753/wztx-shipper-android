package com.ruitukeji.zwbh.mine.addressmanagement.fragment;

import com.kymjs.rxvolley.client.HttpParams;
import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.application.MyApplication;
import com.ruitukeji.zwbh.common.KJActivityStack;
import com.ruitukeji.zwbh.mine.addressmanagement.dialog.AddressDeleteBouncedDialog;
import com.ruitukeji.zwbh.retrofit.RequestClient;
import com.ruitukeji.zwbh.utils.JsonUtil;
import com.ruitukeji.zwbh.utils.httputil.HttpUtilParams;
import com.ruitukeji.zwbh.utils.httputil.ResponseListener;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/2/13.
 */

public class AddressPresenter implements AddressContract.Presenter {

    private AddressContract.View mView;

    public AddressPresenter(AddressContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }


    @Override
    public void getAddress(int page, String type) {
        mView.showLoadingDialog(KJActivityStack.create().topActivity().getString(R.string.dataLoad));
        HttpParams httpParams = HttpUtilParams.getInstance().getHttpParams();
        httpParams.put("page", page);
        httpParams.put("type", type);
        httpParams.put("pageSize", 10);
        RequestClient.getAddress(httpParams, new ResponseListener<String>() {
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
    public void postSetDefaultAddress(int addressId, String type) {
        mView.showLoadingDialog(KJActivityStack.create().topActivity().getString(R.string.dataLoad));
        HttpParams httpParams = HttpUtilParams.getInstance().getHttpParams();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", addressId);
        map.put("type", type);
        httpParams.putJsonParams(JsonUtil.getInstance().obj2JsonString(map).toString());
        RequestClient.getUpdateDefault(httpParams, new ResponseListener<String>() {
            @Override
            public void onSuccess(String response) {
                mView.getSuccess(response, 1);
            }

            @Override
            public void onFailure(String msg) {
                mView.errorMsg(msg, 1);
            }
        });
    }

    @Override
    public void postDeleteAddress(int addressId) {
        AddressDeleteBouncedDialog addressDeleteBouncedDialog = new AddressDeleteBouncedDialog(KJActivityStack.create().topActivity());
        addressDeleteBouncedDialog.setAddressDeleteDialogCallBack(new AddressDeleteBouncedDialog.AddressDeleteDialogCallBack() {
            @Override
            public void confirm() {
                addressDeleteBouncedDialog.cancel();
                mView.showLoadingDialog(KJActivityStack.create().topActivity().getString(R.string.dataLoad));
                HttpParams httpParams = HttpUtilParams.getInstance().getHttpParams();
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("ad_id", addressId);
                httpParams.putJsonParams(JsonUtil.getInstance().obj2JsonString(map).toString());
                RequestClient.getDelAddress(httpParams, new ResponseListener<String>() {
                    @Override
                    public void onSuccess(String response) {
                        mView.getSuccess(response, 2);
                    }

                    @Override
                    public void onFailure(String msg) {
                        mView.errorMsg(msg, 2);
                    }
                });
            }
        });
        addressDeleteBouncedDialog.show();
    }
}
