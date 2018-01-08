package com.ruitukeji.zwbh.mine.shippercertification.certificationfragment;

import com.kymjs.common.CipherUtils;
import com.kymjs.common.StringUtils;
import com.kymjs.rxvolley.client.HttpParams;
import com.nanchen.compresshelper.FileUtil;
import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.application.MyApplication;
import com.ruitukeji.zwbh.common.KJActivityStack;
import com.ruitukeji.zwbh.constant.StringConstants;
import com.ruitukeji.zwbh.mine.shippercertification.dialog.SubmitBouncedDialog;
import com.ruitukeji.zwbh.retrofit.RequestClient;
import com.ruitukeji.zwbh.utils.BitmapCoreUtil;
import com.ruitukeji.zwbh.utils.DataCleanManager;
import com.ruitukeji.zwbh.utils.JsonUtil;
import com.ruitukeji.zwbh.utils.httputil.HttpUtilParams;
import com.ruitukeji.zwbh.utils.httputil.ResponseListener;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by Administrator on 2017/12/11.
 */

public class IndividualOwnersPresenter implements IndividualOwnersContract.Presenter {

    private IndividualOwnersContract.View mView;

    public IndividualOwnersPresenter(IndividualOwnersContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void postIndividualOwners(String real_name, int sex, String identity, String tel, long pic_time, String front_pic, String back_pic, String hold_pic) {
        if (StringUtils.isEmpty(real_name)) {
            mView.errorMsg(MyApplication.getContext().getString(R.string.hintName), 0);
            return;
        }
        if (StringUtils.isEmpty(identity)) {
            mView.errorMsg(MyApplication.getContext().getString(R.string.hintIDtext), 0);
            return;
        }
        if (identity.length() != 15 && identity.length() != 18) {
            mView.errorMsg(MyApplication.getContext().getString(R.string.hintIDerrorText), 0);
            return;
        }
        if (pic_time <= 0) {
            mView.errorMsg(MyApplication.getContext().getString(R.string.pleaseSelect) + MyApplication.getContext().getString(R.string.validityIdentityCard), 0);
            return;
        }
//        if (StringUtils.isEmpty(front_pic)) {
//            mView.errorMsg(MyApplication.getContext().getString(R.string.uploadYourIdCard), 0);
//            return;
//        }
//        if (StringUtils.isEmpty(back_pic)) {
//            mView.errorMsg(MyApplication.getContext().getString(R.string.uploadClearYourIdCard), 0);
//            return;
//        }
//        if (StringUtils.isEmpty(hold_pic)) {
//            mView.errorMsg(MyApplication.getContext().getString(R.string.uploudHoldingIdPhoto), 0);
//            return;
//        }
        SubmitBouncedDialog submitBouncedDialog = new SubmitBouncedDialog(KJActivityStack.create().topActivity()) {
            @Override
            public void confirm() {
                this.cancel();
                mView.showLoadingDialog(MyApplication.getContext().getString(R.string.submissionLoad));
                HttpParams httpParams = HttpUtilParams.getInstance().getHttpParams();
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("real_name", real_name);
                map.put("sex", sex);
                map.put("identity", identity);
                map.put("hold_pic", "http://ot090bmn8.bkt.clouddn.com/37bfbbf2e59ee54286762726db5881c5.png");
                map.put("front_pic", "http://ot090bmn8.bkt.clouddn.com/37bfbbf2e59ee54286762726db5881c5.png");
                map.put("back_pic", "http://ot090bmn8.bkt.clouddn.com/37bfbbf2e59ee54286762726db5881c5.png");
                map.put("tel", tel);
                map.put("pic_time", pic_time);
                httpParams.putJsonParams(JsonUtil.getInstance().obj2JsonString(map).toString());
                RequestClient.postPersonalInformation(httpParams, new ResponseListener<String>() {
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
        };
        submitBouncedDialog.show();
    }


    @Override
    public void getIndividualOwners() {
        HttpParams httpParams = HttpUtilParams.getInstance().getHttpParams();
        RequestClient.getPersonalCertificate(httpParams, new ResponseListener<String>() {
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
    public void postUpLoadImg(String path, int flag) {
        mView.showLoadingDialog(KJActivityStack.create().topActivity().getString(R.string.crossLoad));
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
                mView.getSuccess(response, flag);
            }

            @Override
            public void onFailure(String msg) {
                mView.errorMsg(msg, 0);
            }
        });
    }

}
