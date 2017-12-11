package com.ruitukeji.zwbh.loginregister;

import android.Manifest;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.kymjs.common.PreferenceHelper;
import com.kymjs.common.StringUtils;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.ui.ImagePreviewDelActivity;
import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.common.BaseActivity;
import com.ruitukeji.zwbh.common.BindView;
import com.ruitukeji.zwbh.common.GlideImageLoader;
import com.ruitukeji.zwbh.common.KJActivityStack;
import com.ruitukeji.zwbh.common.ViewInject;
import com.ruitukeji.zwbh.constant.NumericConstants;
import com.ruitukeji.zwbh.constant.StringConstants;
import com.ruitukeji.zwbh.entity.UploadImageBean;
import com.ruitukeji.zwbh.utils.ActivityTitleUtils;
import com.ruitukeji.zwbh.utils.JsonUtil;


import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * 企业认证----操作人信息
 * Created by Administrator on 2017/2/17.
 */

public class EnterpriseOperatingInformationActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks, EnterpriseOperatingInformationContract.View {

    /**
     * 操作人身份证号
     */
    @BindView(id = R.id.et_operationIdNumber)
    private EditText et_operationIdNumber;

    /**
     * 手持身份证照
     */
    @BindView(id = R.id.img_operationlIdCard, click = true)
    private ImageView img_operationlIdCard;
    /**
     * 身份证照正
     */
    @BindView(id = R.id.img_operationlIdCardIs, click = true)
    private ImageView img_operationlIdCardIs;
    /**
     * 身份证照反
     */
    @BindView(id = R.id.img_operationlIdCardBack, click = true)
    private ImageView img_operationlIdCardBack;
    /**
     * 下一步
     */
    @BindView(id = R.id.tv_nextType, click = true)
    private TextView tv_nextType;

    private ImagePicker imagePicker;
    private boolean isPreview = false;
    private boolean isPreviewIs = false;
    private boolean isPreviewIsBack = false;
    private String imgUrlCard = null;
    private String imgUrlCardIs = null;
    private String imgUrlCardBack = null;
    private String com_name;
    private String com_buss_num;
    private String law_person;
    private String identity;
    private String phone;
    private String address;
    private String deposit_name;
    private String bank;
    private String account;
    private String hold_pic;
    private String front_pic;
    private String back_pic;
    private String businessLicense_pic;

    private SweetAlertDialog sweetAlertDialog = null;
    private String auth_status = null;

    @Override
    public void setRootView() {
        setContentView(R.layout.activity_enterpriseoperatinginformation);
    }


    @Override
    public void initData() {
        super.initData();
        initDialog();
        initImagePicker();
        mPresenter = new EnterpriseOperatingInformationPresenter(this);
        images = new ArrayList<>();
        com_name = getIntent().getStringExtra("com_name");
        com_buss_num = getIntent().getStringExtra("com_buss_num");
        law_person = getIntent().getStringExtra("law_person");
        identity = getIntent().getStringExtra("identity");
        phone = getIntent().getStringExtra("phone");
        address = getIntent().getStringExtra("address");
        deposit_name = getIntent().getStringExtra("deposit_name");
        bank = getIntent().getStringExtra("bank");
        account = getIntent().getStringExtra("account");
        hold_pic = getIntent().getStringExtra("hold_pic");
        front_pic = getIntent().getStringExtra("front_pic");
        back_pic = getIntent().getStringExtra("back_pic");
        businessLicense_pic = getIntent().getStringExtra("businessLicense_pic");
        auth_status = getIntent().getStringExtra("auth_status");
        if (!(StringUtils.isEmpty(auth_status) || auth_status.equals("init"))) {
            String sp_identity = getIntent().getStringExtra("sp_identity");
            et_operationIdNumber.setText(sp_identity);
            imgUrlCard = getIntent().getStringExtra("sp_hold_pic");
            imgUrlCardIs = getIntent().getStringExtra("sp_front_pic");
            imgUrlCardBack = getIntent().getStringExtra("sp_back_pic");

            isPreview = true;
            GlideImageLoader.glideLoader(this, imgUrlCard + "?imageView2/1/w/150/h/150", img_operationlIdCard, 1);

            isPreviewIs = true;
            GlideImageLoader.glideLoader(this, imgUrlCardIs + "?imageView2/1/w/300/h/150", img_operationlIdCardIs, 1);

            isPreviewIsBack = true;
            GlideImageLoader.glideLoader(this, imgUrlCardBack + "?imageView2/1/w/300/h/150", img_operationlIdCardBack, 1);

        }

    }

    @Override
    public void initWidget() {
        super.initWidget();
        ActivityTitleUtils.initToolbar(aty, getString(R.string.enterpriseAuthentication), true, R.id.titlebar);
        tv_nextType.setText(getString(R.string.submit));
    }

    /**
     * 图片库设置
     */
    private void initImagePicker() {
        imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());
        imagePicker.setMultiMode(false);
        imagePicker.setShowCamera(true);
        imagePicker.setCrop(false);
        imagePicker.setSaveRectangle(false);
    }


    @Override
    public void widgetClick(View v) {
        super.widgetClick(v);
        switch (v.getId()) {
            case R.id.img_operationlIdCard:
                if (isPreview) {
                    images.clear();
                    ImageItem imageItem = new ImageItem();
                    imageItem.path = imgUrlCard;
                    images.add(imageItem);
                    //打开预览
                    toImagePreviewDelActivity(img_operationlIdCard, images, NumericConstants.REQUEST_CODE_PREVIEW);
                } else {
                    choicePhotoWrapper(NumericConstants.REQUEST_CODE_CHOOSE_PHOTO);
                }
                break;
            case R.id.img_operationlIdCardIs:
                if (isPreviewIs) {
                    images.clear();
                    ImageItem imageItem = new ImageItem();
                    imageItem.path = imgUrlCardIs;
                    images.add(imageItem);
                    //打开预览
                    toImagePreviewDelActivity(img_operationlIdCardIs, images, NumericConstants.REQUEST_CODE_PREVIEW1);
                } else {
                    choicePhotoWrapper(NumericConstants.REQUEST_CODE_PHOTO_PREVIEW);
                }
                break;
            case R.id.img_operationlIdCardBack:
                if (isPreviewIsBack) {
                    images.clear();
                    ImageItem imageItem = new ImageItem();
                    imageItem.path = imgUrlCardBack;
                    images.add(imageItem);
                    //打开预览
                    toImagePreviewDelActivity(img_operationlIdCardBack, images, NumericConstants.REQUEST_CODE_PREVIEW2);
                } else {
                    choicePhotoWrapper(NumericConstants.REQUEST_CODE_PHOTO_PREVIEW1);
                }
                break;
            case R.id.tv_nextType:
                if (sweetAlertDialog == null) {
                    initDialog();
                }
                ((EnterpriseOperatingInformationContract.Presenter) mPresenter).postEnterpriseOperatingInformation(sweetAlertDialog, com_name, com_buss_num, law_person, identity, phone, address, deposit_name, bank, account, hold_pic, front_pic, back_pic, businessLicense_pic, et_operationIdNumber.getText().toString().trim(), imgUrlCard, imgUrlCardIs, imgUrlCardBack);
                break;
        }
    }

    /**
     * 弹框设置
     */
    private void initDialog() {
        sweetAlertDialog = null;
        sweetAlertDialog = new SweetAlertDialog(this, SweetAlertDialog.CUSTOM_IMAGE_TYPE);
        sweetAlertDialog.setCancelable(false);
        sweetAlertDialog.setCancelText(getString(R.string.cancel1))
                .setConfirmText(getString(R.string.confirm))
                .showCancelButton(true)
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                    }
                });
    }


    @Override
    public void getSuccess(String s, int flag) {
        if (flag == 0) {
            PreferenceHelper.write(aty, StringConstants.FILENAME, "isAvatar", true);
            KJActivityStack.create().finishActivity(EnterpriseInformationActivity.class);
            KJActivityStack.create().finishActivity(EnterpriseQualificationCertificateActivity.class);
            ViewInject.toast(getString(R.string.submittedSuccessfully));
            finish();
        } else if (flag == NumericConstants.REQUEST_CODE_CHOOSE_PHOTO) {
            UploadImageBean uploadImageBean = (UploadImageBean) JsonUtil.getInstance().json2Obj(s, UploadImageBean.class);
            if (!(StringUtils.isEmpty(uploadImageBean.getResult().getFile().getUrl()))) {
                isPreview = true;
                imgUrlCard = uploadImageBean.getResult().getFile().getUrl();
                GlideImageLoader.glideLoader(this, imgUrlCard + "?imageView2/1/w/150/h/150", img_operationlIdCard, 1);
            }
        } else if (flag == NumericConstants.REQUEST_CODE_PHOTO_PREVIEW) {
            UploadImageBean uploadImageBean = (UploadImageBean) JsonUtil.getInstance().json2Obj(s, UploadImageBean.class);
            if (!(StringUtils.isEmpty(uploadImageBean.getResult().getFile().getUrl()))) {
                isPreviewIs = true;
                imgUrlCardIs = uploadImageBean.getResult().getFile().getUrl();
                GlideImageLoader.glideLoader(this, imgUrlCardIs + "?imageView2/1/w/300/h/150", img_operationlIdCardIs, 1);
            }
        } else if (flag == NumericConstants.REQUEST_CODE_PHOTO_PREVIEW1) {
            UploadImageBean uploadImageBean = (UploadImageBean) JsonUtil.getInstance().json2Obj(s, UploadImageBean.class);
            if (!(StringUtils.isEmpty(uploadImageBean.getResult().getFile().getUrl()))) {
                isPreviewIsBack = true;
                imgUrlCardBack = uploadImageBean.getResult().getFile().getUrl();
                GlideImageLoader.glideLoader(this, imgUrlCardBack + "?imageView2/1/w/300/h/150", img_operationlIdCardBack, 1);
            }
        }
        dismissLoadingDialog();
    }

    @Override
    public void error(String msg) {
        dismissLoadingDialog();
        ViewInject.toast(msg);
    }

    @Override
    public void setPresenter(EnterpriseOperatingInformationContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @AfterPermissionGranted(NumericConstants.REQUEST_CODE_PERMISSION_PHOTO_PICKER)
    private void choicePhotoWrapper(int code) {
        String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
        if (EasyPermissions.hasPermissions(this, perms)) {
            // 拍照后照片的存放目录，改成你自己拍照后要存放照片的目录。如果不传递该参数的话就没有拍照功能
            Intent intent = new Intent(this, ImageGridActivity.class);
            intent.putExtra(ImageGridActivity.EXTRAS_IMAGES, images);
            //ImagePicker.getInstance().setSelectedImages(images);
            startActivityForResult(intent, code);
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
            selectImageReturn(requestCode, data);
        } else if (resultCode == ImagePicker.RESULT_CODE_BACK) {
            if (data == null) {
                ViewInject.toast(getString(R.string.noData));
                return;
            }
            if (images == null || images.size() == 0) {
                ViewInject.toast(getString(R.string.noData));
                return;
            }
            //预览图片返回
            previewImageReturn(requestCode, data);
        }
    }

    //选择图片返回
    @SuppressWarnings("unchecked")
    private void selectImageReturn(int requestCode, Intent data) {
        if (data != null && requestCode == NumericConstants.REQUEST_CODE_CHOOSE_PHOTO) {
            images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
            if (images == null || images.size() == 0) {
                ViewInject.toast(getString(R.string.noData));
                return;
            }
            ((EnterpriseOperatingInformationContract.Presenter) mPresenter).upLoadImg(images.get(0).path, NumericConstants.REQUEST_CODE_CHOOSE_PHOTO);
        } else if (data != null && requestCode == NumericConstants.REQUEST_CODE_PHOTO_PREVIEW) {
            images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
            if (images == null || images.size() == 0) {
                ViewInject.toast(getString(R.string.noData));
                return;
            }
            ((EnterpriseOperatingInformationContract.Presenter) mPresenter).upLoadImg(images.get(0).path, NumericConstants.REQUEST_CODE_PHOTO_PREVIEW);
        } else if (data != null && requestCode == NumericConstants.REQUEST_CODE_PHOTO_PREVIEW1) {
            images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
            if (images == null || images.size() == 0) {
                ViewInject.toast(getString(R.string.noData));
                return;
            }
            ((EnterpriseOperatingInformationContract.Presenter) mPresenter).upLoadImg(images.get(0).path, NumericConstants.REQUEST_CODE_PHOTO_PREVIEW1);
        } else {
            ViewInject.toast(getString(R.string.noData));
        }
    }

    //预览图片返回
    @SuppressWarnings("unchecked")
    private void previewImageReturn(int requestCode, Intent data) {
        images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_IMAGE_ITEMS);
        //预览图片返回
        if (data != null && images != null && requestCode == NumericConstants.REQUEST_CODE_PREVIEW && images.size() == 0) {
            isPreview = false;
            imgUrlCard = null;
            GlideImageLoader.glideLoader(this, R.mipmap.operationlidcard, img_operationlIdCard, 1);
        } else if (data != null && images != null && requestCode == NumericConstants.REQUEST_CODE_PREVIEW1 && images.size() == 0) {
            isPreviewIs = false;
            imgUrlCardIs = null;
            GlideImageLoader.glideLoader(this, R.mipmap.operationlidcardis, img_operationlIdCardIs, 1);
        } else if (data != null && images != null && requestCode == NumericConstants.REQUEST_CODE_PREVIEW2 && images.size() == 0) {
            isPreviewIsBack = false;
            imgUrlCardBack = null;
            GlideImageLoader.glideLoader(this, R.mipmap.operationlidcardback, img_operationlIdCardBack, 1);
        }
    }

    //打开预览
    @SuppressWarnings("unchecked")
    private void toImagePreviewDelActivity(View view, ArrayList<ImageItem> list, int code) {
        Intent intentPreview = new Intent(this, ImagePreviewDelActivity.class);
        intentPreview.putExtra(ImagePicker.EXTRA_IMAGE_ITEMS, list);
        intentPreview.putExtra(ImagePicker.EXTRA_SELECTED_IMAGE_POSITION, 0);
        intentPreview.putExtra(ImagePicker.EXTRA_FROM_ITEMS, true);
        startActivityForResult(intentPreview, code);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        images.clear();
        images = null;
    }
}
