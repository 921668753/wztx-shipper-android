package com.ruitukeji.zwbh.mine.myorder.payment;

import com.kymjs.common.StringUtils;
import com.kymjs.rxvolley.client.HttpParams;
import com.nanchen.compresshelper.FileUtil;
import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.application.MyApplication;
import com.ruitukeji.zwbh.common.KJActivityStack;
import com.ruitukeji.zwbh.constant.StringConstants;
import com.ruitukeji.zwbh.retrofit.RequestClient;
import com.ruitukeji.zwbh.utils.DataCleanManager;
import com.ruitukeji.zwbh.utils.JsonUtil;
import com.ruitukeji.zwbh.utils.httputil.HttpUtilParams;
import com.ruitukeji.zwbh.utils.httputil.ResponseListener;
import com.ruitukeji.zwbh.utils.picturerelated.BitmapCoreUtil;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/2/19.
 */

public class PaymentVoucherPresenter implements PaymentVoucherContract.Presenter {

    private PaymentVoucherContract.View mView;

    public PaymentVoucherPresenter(PaymentVoucherContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void getAppConfig() {
        HttpParams httpParams = HttpUtilParams.getInstance().getHttpParams();
        RequestClient.getAppConfig(httpParams, new ResponseListener<String>() {
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
    public void uploadCerPic(int order_id, String img_url) {
        mView.showLoadingDialog(MyApplication.getContext().getString(R.string.crossLoad));
        if (order_id < 1) {
            mView.errorMsg(KJActivityStack.create().topActivity().getString(R.string.serverReturnsDataError), 0);
            return;
        }
        if (StringUtils.isEmpty(img_url)) {
            mView.errorMsg(KJActivityStack.create().topActivity().getString(R.string.uploadProofPayment1), 0);
            return;
        }
        HttpParams httpParams = HttpUtilParams.getInstance().getHttpParams();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("order_id", order_id);
        map.put("img_url", img_url);
        httpParams.putJsonParams(JsonUtil.getInstance().obj2JsonString(map).toString());
        RequestClient.uploadCerPic(httpParams, new ResponseListener<String>() {
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


    /**
     * @param path 上传图片
     */
    @Override
    public void postUpLoadImg(String path, int code) {
        if (StringUtils.isEmpty(path)) {
            mView.errorMsg(KJActivityStack.create().topActivity().getString(R.string.noData), 0);
            return;
        }
        File oldFile = new File(path);
        if (!(FileUtil.isFileExists(oldFile))) {
            mView.errorMsg(KJActivityStack.create().topActivity().getString(R.string.imagePathError), 0);
            return;
        }

        long fileSize = 0;
        try {
            fileSize = DataCleanManager.getFileSize(oldFile);
        } catch (Exception e) {
            e.printStackTrace();
            fileSize = 0;
        }
        if (fileSize >= StringConstants.COMPRESSION_SIZE) {
            oldFile = BitmapCoreUtil.customCompression(oldFile);
        }
        HttpParams httpParams = HttpUtilParams.getInstance().getHttpParams();
        httpParams.put("file", oldFile);
        RequestClient.upLoadImg(httpParams, 1, new ResponseListener<String>() {
            @Override
            public void onSuccess(String response) {
                mView.getSuccess(response, code);
            }

            @Override
            public void onFailure(String msg) {
                mView.errorMsg(msg, 0);
            }
        });
    }

}
