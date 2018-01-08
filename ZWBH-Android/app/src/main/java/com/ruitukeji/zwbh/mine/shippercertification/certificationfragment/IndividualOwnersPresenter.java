package com.ruitukeji.zwbh.mine.shippercertification.certificationfragment;

import com.kymjs.common.CipherUtils;
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
    public void postIndividualOwners(String real_name, int sex, String identity, String hold_pic, String front_pic, String back_pic, String tel, String pic_time) {
        if (StringUtils.isEmpty(real_name)) {
            mView.errorMsg(MyApplication.getContext().getString(R.string.hintName), 0);
            return;
        }
        if (StringUtils.isEmpty(identity)) {
            mView.errorMsg(MyApplication.getContext().getString(R.string.hintIDtext), 0);
            return;
        }
       // hold_pic
        if (StringUtils.isEmpty(front_pic)) {
            mView.errorMsg(MyApplication.getContext().getString(R.string.uploudIdCardPhoto), 0);
            return;
        }
        if (StringUtils.isEmpty(back_pic)) {
            mView.errorMsg(MyApplication.getContext().getString(R.string.uploudIdCardPhotoBack), 0);
            return;
        }
        HttpParams httpParams = HttpUtilParams.getInstance().getHttpParams();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("real_name", real_name);
        map.put("sex", sex);
        map.put("identity", identity);
        map.put("hold_pic", hold_pic);
        map.put("front_pic", front_pic);
        map.put("back_pic", back_pic);
        map.put("tel", tel);
        map.put("pic_time", pic_time);
        httpParams.putJsonParams(JsonUtil.getInstance().obj2JsonString(map).toString());
        RequestClient.getInfo(httpParams, new ResponseListener<String>() {
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
    public void getIndividualOwners() {
        RequestClient.isLogin(new ResponseListener<String>() {
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
