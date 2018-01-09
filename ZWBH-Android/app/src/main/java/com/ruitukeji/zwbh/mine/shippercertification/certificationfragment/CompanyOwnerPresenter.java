package com.ruitukeji.zwbh.mine.shippercertification.certificationfragment;

import com.kymjs.common.StringUtils;
import com.kymjs.rxvolley.client.HttpParams;
import com.nanchen.compresshelper.FileUtil;
import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.application.MyApplication;
import com.ruitukeji.zwbh.common.KJActivityStack;
import com.ruitukeji.zwbh.constant.StringConstants;
import com.ruitukeji.zwbh.mine.shippercertification.dialog.SubmitBouncedDialog;
import com.ruitukeji.zwbh.retrofit.RequestClient;
import com.ruitukeji.zwbh.utils.JsonUtil;
import com.ruitukeji.zwbh.utils.picturerelated.BitmapCoreUtil;
import com.ruitukeji.zwbh.utils.DataCleanManager;
import com.ruitukeji.zwbh.utils.httputil.HttpUtilParams;
import com.ruitukeji.zwbh.utils.httputil.ResponseListener;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2017/12/11.
 */

public class CompanyOwnerPresenter implements CompanyOwnerContract.Presenter {

    private CompanyOwnerContract.View mView;

    public CompanyOwnerPresenter(CompanyOwnerContract.View view) {
        mView = view;
        mView.setPresenter(this);
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


    @Override
    public void postCompanyOwner(String com_name, String com_buss_num, String address, String phone, String buss_pic, String law_person, String identity, String hold_pic, String front_pic,
                                 String back_pic, String sp_identity_name, String sp_identity, String sp_hold_pic, String sp_front_pic, String sp_back_pic) {
        if (StringUtils.isEmpty(com_name)) {
            mView.errorMsg(MyApplication.getContext().getString(R.string.companyName1), 0);
            return;
        }
        if (StringUtils.isEmpty(com_buss_num)) {
            mView.errorMsg(MyApplication.getContext().getString(R.string.registrationNumber1), 0);
            return;
        }
        if (com_buss_num.length() != 15 && com_buss_num.length() != 18) {
            mView.errorMsg(MyApplication.getContext().getString(R.string.registrationNumber2), 0);
            return;
        }
        if (StringUtils.isEmpty(address)) {
            mView.errorMsg(MyApplication.getContext().getString(R.string.pleaseFillOut) + MyApplication.getContext().getString(R.string.companyAddress), 0);
            return;
        }
        if (StringUtils.isEmpty(phone)) {
            mView.errorMsg(MyApplication.getContext().getString(R.string.pleaseFillOut) + MyApplication.getContext().getString(R.string.phoneCompany), 0);
            return;
        }
        if (phone.length() <= 18) {
            mView.errorMsg(MyApplication.getContext().getString(R.string.pleaseFillOut) + MyApplication.getContext().getString(R.string.phoneNumber1), 0);
            return;
        }
        if (StringUtils.isEmpty(buss_pic)) {
            mView.errorMsg(MyApplication.getContext().getString(R.string.uploadPictureBusinessLicense), 0);
            return;
        }
        if (StringUtils.isEmpty(law_person)) {
            mView.errorMsg(MyApplication.getContext().getString(R.string.pleaseFillOut) + MyApplication.getContext().getString(R.string.legalPersonName), 0);
            return;
        }
        String all = "^[A-Za-z\\u4e00-\\u9fa5]{2,20}";//{2,10}表示字符的长度是2-10
        Pattern pattern = Pattern.compile(all);
        boolean tf = pattern.matches(all, law_person);
        if (!tf) {
            mView.errorMsg(MyApplication.getContext().getString(R.string.hintName2), 0);
            return;
        }
        if (StringUtils.isEmpty(identity)) {
            mView.errorMsg(MyApplication.getContext().getString(R.string.pleaseFillOut) + MyApplication.getContext().getString(R.string.legalPersonIdNumber), 0);
            return;
        }
        if (identity.length() != 15 && identity.length() != 18) {
            mView.errorMsg(MyApplication.getContext().getString(R.string.legalPersonIdNumber1), 0);
            return;
        }
        if (StringUtils.isEmpty(front_pic)) {
            mView.errorMsg(MyApplication.getContext().getString(R.string.front_pic), 0);
            return;
        }
        if (StringUtils.isEmpty(back_pic)) {
            mView.errorMsg(MyApplication.getContext().getString(R.string.back_pic), 0);
            return;
        }
        if (StringUtils.isEmpty(hold_pic)) {
            mView.errorMsg(MyApplication.getContext().getString(R.string.hold_pic), 0);
            return;
        }
        if (StringUtils.isEmpty(sp_identity_name)) {
            mView.errorMsg(MyApplication.getContext().getString(R.string.pleaseFillOut) + MyApplication.getContext().getString(R.string.operationName), 0);
            return;
        }
        boolean tf1 = pattern.matches(all, sp_identity_name);
        if (!tf1) {
            mView.errorMsg(MyApplication.getContext().getString(R.string.hintName3), 0);
            return;
        }

        if (StringUtils.isEmpty(sp_identity)) {
            mView.errorMsg(MyApplication.getContext().getString(R.string.operationIdNumber1), 0);
            return;
        }
        if (sp_identity.length() != 15 && sp_identity.length() != 18) {
            mView.errorMsg(MyApplication.getContext().getString(R.string.operationIdNumber1), 0);
            return;
        }

        if (StringUtils.isEmpty(sp_front_pic)) {
            mView.errorMsg(MyApplication.getContext().getString(R.string.sp_front_pic), 0);
            return;
        }
        if (StringUtils.isEmpty(sp_back_pic)) {
            mView.errorMsg(MyApplication.getContext().getString(R.string.sp_back_pic), 0);
            return;
        }
        if (StringUtils.isEmpty(sp_hold_pic)) {
            mView.errorMsg(MyApplication.getContext().getString(R.string.sp_hold_pic), 0);
            return;
        }
//        if (pic_time <= 0) {
//            mView.errorMsg(MyApplication.getContext().getString(R.string.pleaseSelect) + MyApplication.getContext().getString(R.string.validityIdentityCard), 0);
//            return;
//        }
        SubmitBouncedDialog submitBouncedDialog = new SubmitBouncedDialog(KJActivityStack.create().topActivity()) {
            @Override
            public void confirm() {
                this.cancel();
                mView.showLoadingDialog(MyApplication.getContext().getString(R.string.submissionLoad));
                HttpParams httpParams = HttpUtilParams.getInstance().getHttpParams();
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("com_name", com_name);
                map.put("com_buss_num", com_buss_num);
                map.put("address", address);
                map.put("phone", phone);
                map.put("buss_pic", "http://ot090bmn8.bkt.clouddn.com/37bfbbf2e59ee54286762726db5881c5.png");
                map.put("law_person", law_person);
                map.put("identity", identity);
                map.put("hold_pic", "http://ot090bmn8.bkt.clouddn.com/37bfbbf2e59ee54286762726db5881c5.png");
                map.put("front_pic", "http://ot090bmn8.bkt.clouddn.com/37bfbbf2e59ee54286762726db5881c5.png");
                map.put("back_pic", "http://ot090bmn8.bkt.clouddn.com/37bfbbf2e59ee54286762726db5881c5.png");
                map.put("sp_identity_name", sp_identity_name);
                map.put("sp_identity", sp_identity);
                map.put("sp_hold_pic", "http://ot090bmn8.bkt.clouddn.com/37bfbbf2e59ee54286762726db5881c5.png");
                map.put("sp_front_pic", "http://ot090bmn8.bkt.clouddn.com/37bfbbf2e59ee54286762726db5881c5.png");
                map.put("sp_back_pic", "http://ot090bmn8.bkt.clouddn.com/37bfbbf2e59ee54286762726db5881c5.png");
                httpParams.putJsonParams(JsonUtil.getInstance().obj2JsonString(map).toString());
                RequestClient.postEnterpriseInformation(httpParams, new ResponseListener<String>() {
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
    public void getCompanyOwner() {
        HttpParams httpParams = HttpUtilParams.getInstance().getHttpParams();
        RequestClient.getCompanyAuthInfo(httpParams, new ResponseListener<String>() {
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
}
