package com.ruitukeji.zwbh.mine.setting;

import com.kymjs.common.CipherUtils;
import com.kymjs.rxvolley.client.HttpParams;
import com.kymjs.rxvolley.client.ProgressListener;
import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.common.KJActivityStack;
import com.ruitukeji.zwbh.retrofit.RequestClient;
import com.ruitukeji.zwbh.utils.JsonUtil;
import com.ruitukeji.zwbh.utils.MathUtil;
import com.ruitukeji.zwbh.utils.httputil.HttpUtilParams;
import com.ruitukeji.zwbh.utils.httputil.ResponseListener;

import java.util.HashMap;
import java.util.Map;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by Administrator on 2017/2/11.
 */

public class SettingsPresenter implements SettingsContract.Presenter {

    private SettingsContract.View mView;

    public SettingsPresenter(SettingsContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }


    @Override
    public void getUpdateApp() {

    }

    /**
     * @param updateAppUrl 下载app
     */
    @Override
    public void downloadApp(String updateAppUrl) {
        mView.showLoadingDialog(KJActivityStack.create().topActivity().getString(R.string.download));
        ProgressListener progressListener = new ProgressListener() {
            @Override
            public void onProgress(long transferredBytes, long totalSize) {
                String size = MathUtil.keepZero(((double) transferredBytes) * 100 / totalSize) + "%";
                mView.showLoadingDialog(KJActivityStack.create().topActivity().getString(R.string.download) + size);
            }
        };
        RequestClient.downloadApp(updateAppUrl, progressListener, new ResponseListener<String>() {
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
    public void postChangeAd(String isAd) {
        mView.showLoadingDialog(KJActivityStack.create().topActivity().getString(R.string.sendingLoad));
        HttpParams httpParams = HttpUtilParams.getInstance().getHttpParams();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("is_ad", isAd);
        httpParams.putJsonParams(JsonUtil.getInstance().obj2JsonString(map).toString());
        RequestClient.postChangeAd(httpParams, new ResponseListener<String>() {
            @Override
            public void onSuccess(String response) {
                if (isAd.equals("0")) {
                    mView.getSuccess(response, 1);
                } else {
                    mView.getSuccess(response, 2);
                }
            }

            @Override
            public void onFailure(String msg) {
                mView.errorMsg(msg, 0);
            }
        });
    }

    @Override
    public void isLogin(int flag) {
        RequestClient.isLogin(new ResponseListener<String>() {
            @Override
            public void onSuccess(String response) {
                mView.getSuccess(response, flag);
            }

            @Override
            public void onFailure(String msg) {
                mView.errorMsg(msg, flag);
            }
        });
    }
}
