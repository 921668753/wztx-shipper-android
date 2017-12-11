package com.ruitukeji.zwbh.loginregister;

import com.kymjs.common.StringUtils;
import com.kymjs.rxvolley.client.HttpParams;
import com.nanchen.compresshelper.FileUtil;
import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.application.MyApplication;
import com.ruitukeji.zwbh.common.KJActivityStack;
import com.ruitukeji.zwbh.constant.StringConstants;
import com.ruitukeji.zwbh.retrofit.RequestClient;
import com.ruitukeji.zwbh.utils.BitmapCoreUtil;
import com.ruitukeji.zwbh.utils.DataCleanManager;
import com.ruitukeji.zwbh.utils.httputil.HttpUtilParams;
import com.ruitukeji.zwbh.utils.httputil.ResponseListener;

import java.io.File;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2017/2/17.
 */

public class PersonalInformationPresenter implements PersonalInformationContract.Presenter {


    private PersonalInformationContract.View mView;

    public PersonalInformationPresenter(PersonalInformationContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }


    @Override
    public void postPersonalInformation(String name, int sex, String IDNumber, String imgUrl) {
        if (StringUtils.isEmpty(name)) {
            mView.error(MyApplication.getContext().getString(R.string.hintName));
            return;
        }
        String all = "^[A-Za-z\\u4e00-\\u9fa5]{2,10}";//{2,10}表示字符的长度是2-10
        Pattern pattern = Pattern.compile(all);
        boolean tf = Pattern.matches(all, name);
        if (!tf) {
            mView.error(MyApplication.getContext().getString(R.string.hintName1));
            return;
        }
        if (StringUtils.isEmpty(IDNumber)) {
            mView.error(MyApplication.getContext().getString(R.string.hintIDtext));
            return;
        }
        if (IDNumber.length() != 15 && IDNumber.length() != 18) {
            mView.error(MyApplication.getContext().getString(R.string.hintIDerrorText));
            return;
        }
        if (StringUtils.isEmpty(imgUrl)) {
            mView.error(MyApplication.getContext().getString(R.string.uploudHoldingIdPhoto));
            return;
        }
        mView.getSuccess("", 0);
    }

    @Override
    public void upLoadImg(String path) {
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
                mView.getSuccess(response, 1);
            }

            @Override
            public void onFailure(String msg) {
                mView.error(msg);
            }
        });
    }

    @Override
    public void getPersonalCertificate() {
        mView.showLoadingDialog(MyApplication.getContext().getString(R.string.dataLoad));
        HttpParams httpParams = HttpUtilParams.getInstance().getHttpParams();

        RequestClient.getPersonalCertificate(httpParams, new ResponseListener<String>() {
            @Override
            public void onSuccess(String response) {
                mView.getSuccess(response, 2);
            }

            @Override
            public void onFailure(String msg) {
                mView.error(msg);
            }
        });
    }


}
