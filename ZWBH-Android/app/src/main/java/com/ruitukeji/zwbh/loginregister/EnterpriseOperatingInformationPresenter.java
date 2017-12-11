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
import com.ruitukeji.zwbh.utils.JsonUtil;
import com.ruitukeji.zwbh.utils.httputil.HttpUtilParams;
import com.ruitukeji.zwbh.utils.httputil.ResponseListener;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Administrator on 2017/2/17.
 */

public class EnterpriseOperatingInformationPresenter implements EnterpriseOperatingInformationContract.Presenter {

    private EnterpriseOperatingInformationContract.View mView;

    public EnterpriseOperatingInformationPresenter(EnterpriseOperatingInformationContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void postEnterpriseOperatingInformation(SweetAlertDialog sweetAlertDialog, String com_name, String com_buss_num, String law_person, String identity, String phone, String address, String deposit_name, String bank, String account, String hold_pic, String front_pic, String back_pic, String businessLicense_pic, String sp_identity, String sp_hold_pic, String sp_front_pic, String sp_back_pic) {
        if (StringUtils.isEmpty(sp_identity)) {
            mView.error(MyApplication.getContext().getString(R.string.operationIdNumber1));
            return;
        }
        if (sp_identity.length() != 15 && sp_identity.length() != 18) {
            mView.error(MyApplication.getContext().getString(R.string.operationIdNumber1));
            return;
        }
        if (StringUtils.isEmpty(sp_hold_pic)) {
            mView.error(MyApplication.getContext().getString(R.string.sp_hold_pic));
            return;
        }
        if (StringUtils.isEmpty(sp_front_pic)) {
            mView.error(MyApplication.getContext().getString(R.string.sp_front_pic));
            return;
        }
        if (StringUtils.isEmpty(sp_back_pic)) {
            mView.error(MyApplication.getContext().getString(R.string.sp_back_pic));
            return;
        }
        sweetAlertDialog.setTitleText(KJActivityStack.create().topActivity().getString(R.string.submittedInformation));
        sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                sweetAlertDialog.dismiss();
                mView.showLoadingDialog(MyApplication.getContext().getString(R.string.submissionLoad));
                HttpParams httpParams = HttpUtilParams.getInstance().getHttpParams();
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("com_name", com_name);
                map.put("com_buss_num", com_buss_num);
                map.put("law_person", law_person);
                map.put("identity", identity);
                map.put("phone", phone);
                map.put("address", address);
                map.put("deposit_name", deposit_name);
                map.put("bank", bank);
                map.put("account", account);
                map.put("hold_pic", hold_pic);
                map.put("front_pic", front_pic);
                map.put("back_pic", back_pic);
                map.put("buss_pic", businessLicense_pic);
                map.put("sp_identity", sp_identity);
                map.put("sp_hold_pic", sp_hold_pic);
                map.put("sp_front_pic", sp_front_pic);
                map.put("sp_back_pic", sp_back_pic);
                httpParams.putJsonParams(JsonUtil.getInstance().obj2JsonString(map).toString());
                RequestClient.postEnterpriseInformation(httpParams, new ResponseListener<String>() {
                    @Override
                    public void onSuccess(String response) {
                        mView.getSuccess(response, 0);
                    }

                    @Override
                    public void onFailure(String msg) {
                        mView.error(msg);
                    }
                });
            }
        }).show();
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
