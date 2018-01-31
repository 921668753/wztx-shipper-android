package com.ruitukeji.zwbh.mine.personaldata;

import android.Manifest;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kymjs.common.PreferenceHelper;
import com.kymjs.common.StringUtils;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.view.CropImageView;
import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.application.MyApplication;
import com.ruitukeji.zwbh.common.BaseActivity;
import com.ruitukeji.zwbh.common.BindView;
import com.ruitukeji.zwbh.common.GlideImageLoader;
import com.ruitukeji.zwbh.common.KJActivityStack;
import com.ruitukeji.zwbh.common.ViewInject;
import com.ruitukeji.zwbh.constant.NumericConstants;
import com.ruitukeji.zwbh.constant.StringConstants;
import com.ruitukeji.zwbh.entity.UploadImageBean;
import com.ruitukeji.zwbh.entity.mine.PersonalCenterBean;
import com.ruitukeji.zwbh.loginregister.EnterpriseInformationActivity;
import com.ruitukeji.zwbh.loginregister.PersonalInformationActivity;
import com.ruitukeji.zwbh.utils.ActivityTitleUtils;
import com.ruitukeji.zwbh.utils.JsonUtil;
import com.ruitukeji.zwbh.utils.rx.MsgEvent;
import com.ruitukeji.zwbh.utils.rx.RxBus;

import java.util.ArrayList;
import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * 账户设置
 * Created by Administrator on 2017/2/10.
 */

public class PersonalDataActivity extends BaseActivity implements PersonalDataContract.View, EasyPermissions.PermissionCallbacks {

    @BindView(id = R.id.img_user)
    private ImageView img_user;

    @BindView(id = R.id.ll_upload, click = true)
    private LinearLayout ll_upload;


    @BindView(id = R.id.ll_name)
    private LinearLayout ll_name;

    @BindView(id = R.id.tv_name)
    private TextView tv_name;

    @BindView(id = R.id.img_arrow)
    private ImageView img_arrow;

    @BindView(id = R.id.tv_phone)
    private TextView tv_phone;

    @BindView(id = R.id.ll_accountDeposit, click = true)
    private LinearLayout ll_accountDeposit;

    @BindView(id = R.id.tv_accountDeposit)
    private TextView tv_accountDeposit;

    @BindView(id = R.id.ll_personalCertificate, click = true)
    private LinearLayout ll_personalCertificate;
    @BindView(id = R.id.tv_personalCertificate)
    private TextView tv_personalCertificate;

    private ImagePicker imagePicker;

    @Override
    public void setRootView() {
        setContentView(R.layout.activity_personaldata);
    }

    @Override
    public void initData() {
        super.initData();
        mPresenter = new PersonalDataPresenter(this);
    }

    @Override
    public void initWidget() {
        super.initWidget();
        ActivityTitleUtils.initToolbar(aty, getString(R.string.accountSettings), true, R.id.titlebar);
        showLoadingDialog(MyApplication.getContext().getString(R.string.dataLoad));
        ((PersonalDataContract.Presenter) mPresenter).getInfo();
        initImagePicker();
    }

    /**
     * 图片库设置
     */
    private void initImagePicker() {
        imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());
        imagePicker.setMultiMode(false);
        imagePicker.setShowCamera(true);
        imagePicker.setCrop(true);
        imagePicker.setSaveRectangle(false);
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);
        int width = 300;
        int height = 300;
        width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, getResources().getDisplayMetrics());
        height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, height, getResources().getDisplayMetrics());
        imagePicker.setFocusWidth(width);
        imagePicker.setFocusHeight(height);
        imagePicker.setOutPutX(width);
        imagePicker.setOutPutY(height);
    }


    @Override
    public void widgetClick(View v) {
        super.widgetClick(v);
        switch (v.getId()) {
            case R.id.ll_upload:
                choicePhotoWrapper();
                break;
            case R.id.ll_accountDeposit:
                String bond_status = PreferenceHelper.readString(MyApplication.getContext(), StringConstants.FILENAME, "bond_status");
                if (bond_status == null || bond_status.equals("init")) {
                    showActivity(aty, PayDepositActivity.class);
                } else if (bond_status.equals("checked")) {

                } else if (bond_status.equals("frozen")) {
                    ViewInject.toast(getString(R.string.pleaseWait1));
                }
                break;
            case R.id.ll_personalCertificate:
                String type = PreferenceHelper.readString(MyApplication.getContext(), StringConstants.FILENAME, "type");
                String auth_status = PreferenceHelper.readString(MyApplication.getContext(), StringConstants.FILENAME, "auth_status");
                if (auth_status != null && auth_status.equals("pass")) {
                    showActivity(aty, AuthenticationInformationActivity.class);
                    break;
                } else if (auth_status != null && auth_status.equals("check")) {
                    ViewInject.toast(getString(R.string.inAuthentication) + "," + getString(R.string.pleaseWait));
                    break;
                }
                PreferenceHelper.write(this, StringConstants.FILENAME, "isRefreshPersonalDataActivity", true);
                if (type.equals("person")) {
                    Intent personalInformation = new Intent(aty, PersonalInformationActivity.class);
                    personalInformation.putExtra("auth_status", auth_status);
                    showActivity(aty, personalInformation);
                } else {
                    Intent enterpriseInformation = new Intent(aty, EnterpriseInformationActivity.class);
                    enterpriseInformation.putExtra("auth_status", auth_status);
                    showActivity(aty, enterpriseInformation);
                }
                break;
        }
    }


    @AfterPermissionGranted(NumericConstants.REQUEST_CODE_PERMISSION_PHOTO_PICKER)
    private void choicePhotoWrapper() {
        String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
        if (EasyPermissions.hasPermissions(this, perms)) {
            Intent intent = new Intent(this, ImageGridActivity.class);
            intent.putExtra(ImageGridActivity.EXTRAS_IMAGES, images);
            //ImagePicker.getInstance().setSelectedImages(images);
            startActivityForResult(intent, NumericConstants.REQUEST_CODE_CHOOSE_PHOTO);
        } else {
            EasyPermissions.requestPermissions(this, getString(R.string.needPermission), NumericConstants.REQUEST_CODE_PERMISSION_PHOTO_PICKER, perms);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        if (requestCode == NumericConstants.REQUEST_CODE_PERMISSION_PHOTO_PICKER) {
            ViewInject.toast(getString(R.string.denyPermission));
        }
    }


    ArrayList<ImageItem> images = null;


    @SuppressWarnings("unchecked")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == NumericConstants.REQUEST_CODE_CHOOSE_PHOTO) {
                images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                if (images == null || images.size() == 0) {
                    ViewInject.toast(getString(R.string.noData));
                    return;
                }
                String filePath = images.get(0).path;
                ((PersonalDataContract.Presenter) mPresenter).postUpLoadImg(filePath);
            } else {
                ViewInject.toast(getString(R.string.noData));
            }
        }
    }

    @Override
    public void setPresenter(PersonalDataContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void getSuccess(String s, int flag) {
        if (flag == 0) {
            PersonalCenterBean userInfoBean = (PersonalCenterBean) JsonUtil.getInstance().json2Obj(s, PersonalCenterBean.class);
            GlideImageLoader.glideLoader(KJActivityStack.create().topActivity(), userInfoBean.getResult().getAvatar() + "?imageView2/1/w/60/h/60", img_user, 0);
            tv_phone.setText(userInfoBean.getResult().getPhone());
            if (StringUtils.isEmpty(userInfoBean.getResult().getReal_name())) {
                ll_name.setVisibility(View.GONE);
            } else {
                ll_name.setVisibility(View.VISIBLE);
                tv_name.setText(userInfoBean.getResult().getReal_name());
            }
            String bond_status = userInfoBean.getResult().getBond_status();
            if (bond_status == null || bond_status.equals("init")) {
                img_arrow.setVisibility(View.VISIBLE);
                tv_accountDeposit.setText(getString(R.string.failurePay));
            } else if (bond_status.equals("checked")) {
                img_arrow.setVisibility(View.GONE);
                tv_accountDeposit.setText(getString(R.string.checked));
            } else if (bond_status.equals("frozen")) {
                img_arrow.setVisibility(View.VISIBLE);
                tv_accountDeposit.setText(getString(R.string.frozen));
            }
            String auth_status = userInfoBean.getResult().getAuth_status();
            if (auth_status == null || auth_status.equals("init")) {
                tv_personalCertificate.setText(getString(R.string.unauthorized));
            } else if (auth_status.equals("check")) {
                tv_personalCertificate.setText(getString(R.string.inAuthentication));
            } else if (auth_status.equals("pass")) {
                tv_personalCertificate.setText(getString(R.string.certified));
            } else if (auth_status.equals("refuse")) {
                tv_personalCertificate.setText(getString(R.string.refuse));
            } else if (auth_status.equals("delete")) {
                tv_personalCertificate.setText(getString(R.string.delete));
            }
            PreferenceHelper.write(aty, StringConstants.FILENAME, "id", userInfoBean.getResult().getId());
            PreferenceHelper.write(aty, StringConstants.FILENAME, "phone", userInfoBean.getResult().getPhone());
            PreferenceHelper.write(aty, StringConstants.FILENAME, "sex", userInfoBean.getResult().getSex());
            PreferenceHelper.write(aty, StringConstants.FILENAME, "avatar", userInfoBean.getResult().getAvatar());
            PreferenceHelper.write(aty, StringConstants.FILENAME, "real_name", userInfoBean.getResult().getReal_name());
            PreferenceHelper.write(aty, StringConstants.FILENAME, "auth_status", userInfoBean.getResult().getAuth_status());
            PreferenceHelper.write(aty, StringConstants.FILENAME, "type", userInfoBean.getResult().getType());
            PreferenceHelper.write(aty, StringConstants.FILENAME, "recomm_code", userInfoBean.getResult().getRecomm_code());
            PreferenceHelper.write(aty, StringConstants.FILENAME, "bond_status", userInfoBean.getResult().getBond_status());
            PreferenceHelper.write(aty, StringConstants.FILENAME, "bond", userInfoBean.getResult().getBond());
        } else if (flag == 1) {
            UploadImageBean uploadImageBean = (UploadImageBean) JsonUtil.getInstance().json2Obj(s, UploadImageBean.class);
            if (!(StringUtils.isEmpty(uploadImageBean.getResult().getFile().getUrl()))) {
                PreferenceHelper.write(this, StringConstants.FILENAME, "avatar", uploadImageBean.getResult().getFile().getUrl());
                GlideImageLoader.glideLoader(this, uploadImageBean.getResult().getFile().getUrl(), img_user, 0);
                PreferenceHelper.write(this, StringConstants.FILENAME, "isAvatar", true);
                /**
                 * 发送消息
                 */
                MsgEvent msgEvent = new MsgEvent<String>("RxBusAvatarEvent");
                msgEvent.setMsg(uploadImageBean.getResult().getFile().getUrl());
                RxBus.getInstance().post(msgEvent);
            }
        }
        dismissLoadingDialog();
    }

    @Override
    public void error(String msg) {
        dismissLoadingDialog();
        if (toLigon1(msg)) {
            return;
        }
    }
}
