package com.ruitukeji.zwbh.loginregister;

import com.kymjs.common.StringUtils;
import com.kymjs.rxvolley.client.HttpParams;
import com.nanchen.compresshelper.FileUtil;
import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.application.MyApplication;
import com.ruitukeji.zwbh.common.KJActivityStack;
import com.ruitukeji.zwbh.constant.StringConstants;
import com.ruitukeji.zwbh.retrofit.RequestClient;
import com.ruitukeji.zwbh.utils.picturerelated.BitmapCoreUtil;
import com.ruitukeji.zwbh.utils.DataCleanManager;
import com.ruitukeji.zwbh.utils.httputil.HttpUtilParams;
import com.ruitukeji.zwbh.utils.httputil.ResponseListener;

import java.io.File;

/**
 * Created by Administrator on 2017/2/17.
 */

public class EnterpriseQualificationCertificatePresenter implements EnterpriseQualificationCertificateContract.Presenter {


    private EnterpriseQualificationCertificateContract.View mView;

    public EnterpriseQualificationCertificatePresenter(EnterpriseQualificationCertificateContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }


    @Override
    public void postEnterpriseQualificationCertificate(String hold_pic, String front_pic, String back_pic, String businessLicense_pic) {
//        mView.showLoadingDialog(MyApplication.getContext().getString(R.string.submissionLoad));
        if (StringUtils.isEmpty(hold_pic)) {
            mView.error(MyApplication.getContext().getString(R.string.hold_pic));
            return;
        }
        if (StringUtils.isEmpty(front_pic)) {
            mView.error(MyApplication.getContext().getString(R.string.front_pic));
            return;
        }
        if (StringUtils.isEmpty(back_pic)) {
            mView.error(MyApplication.getContext().getString(R.string.back_pic));
            return;
        }
        if (StringUtils.isEmpty(businessLicense_pic)) {
            mView.error(MyApplication.getContext().getString(R.string.businessLicense_pic));
            return;
        }
        mView.getSuccess("", 0);
//        HttpParams httpParams = HttpUtilParams.getInstance().getHttpParams();
//        Map<String, Object> map = new HashMap<String, Object>();
//
//        httpParams.putJsonParams(JsonUtil.getInstance().obj2JsonString(map).toString());
//        RequestClient.postEnterpriseInformation(httpParams, new ResponseListener<String>() {
//            @Override
//            public void onSuccess(String response) {
//                mView.getSuccess(response, 0);
//            }
//
//            @Override
//            public void onFailure(String msg) {
//                mView.error(msg);
//            }
//        });
    }

    @Override
    public void upLoadImg(String path, int flag) {
        mView.showLoadingDialog(KJActivityStack.create().topActivity().getString(R.string.crossLoad));
        File oldFile = new File(path);
        if (!(FileUtil.isFileExists(oldFile))) {
            mView.error(KJActivityStack.create().topActivity().getString(R.string.imagePathError));
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
                mView.error(msg);
            }
        });
    }

}
