package com.ruitukeji.zwbh.mine.drivermanagement.fragment;

import com.kymjs.rxvolley.client.HttpParams;
import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.application.MyApplication;
import com.ruitukeji.zwbh.common.KJActivityStack;
import com.ruitukeji.zwbh.mine.drivermanagement.dialog.DriverManagementDeleteBouncedDialog;
import com.ruitukeji.zwbh.retrofit.RequestClient;
import com.ruitukeji.zwbh.utils.JsonUtil;
import com.ruitukeji.zwbh.utils.httputil.HttpUtilParams;
import com.ruitukeji.zwbh.utils.httputil.ResponseListener;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/2/13.
 */

public class DriverManagementPresenter implements DriverManagementContract.Presenter {

    private DriverManagementContract.View mView;

    public DriverManagementPresenter(DriverManagementContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }


    @Override
    public void getDriverList(int page, String type) {
        mView.showLoadingDialog(MyApplication.getContext().getString(R.string.dataLoad));
        HttpParams httpParams = HttpUtilParams.getInstance().getHttpParams();
        httpParams.put("page", page);
        httpParams.put("type", type);
        httpParams.put("pageSize", 10);
        RequestClient.getDriverList(httpParams, new ResponseListener<String>() {
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

    /**
     * 加入黑名单
     */
    @Override
    public void postDriverBack(int dr_id, int type) {
        mView.showLoadingDialog(MyApplication.getContext().getString(R.string.dataLoad));
        HttpParams httpParams = HttpUtilParams.getInstance().getHttpParams();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", dr_id);
        map.put("type", type);
        httpParams.putJsonParams(JsonUtil.getInstance().obj2JsonString(map).toString());
        RequestClient.postDriverBack(httpParams, new ResponseListener<String>() {
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
    public void postDeleteDriver(int dr_id) {
        DriverManagementDeleteBouncedDialog addressDeleteBouncedDialog = new DriverManagementDeleteBouncedDialog(KJActivityStack.create().topActivity());
        addressDeleteBouncedDialog.setDriverManagementDeleteDialogCallBack(new DriverManagementDeleteBouncedDialog.DriverManagementDeleteDialogCallBack() {
            @Override
            public void confirm() {
                addressDeleteBouncedDialog.cancel();
                mView.showLoadingDialog(MyApplication.getContext().getString(R.string.dataLoad));
                HttpParams httpParams = HttpUtilParams.getInstance().getHttpParams();
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("id", dr_id);
                httpParams.putJsonParams(JsonUtil.getInstance().obj2JsonString(map).toString());
                RequestClient.postDeleteDriver(httpParams, new ResponseListener<String>() {
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
        });
        addressDeleteBouncedDialog.show();
    }
}
