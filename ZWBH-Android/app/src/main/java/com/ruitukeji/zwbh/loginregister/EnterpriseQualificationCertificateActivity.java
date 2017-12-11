package com.ruitukeji.zwbh.loginregister;

import android.Manifest;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;
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
import com.ruitukeji.zwbh.common.ViewInject;
import com.ruitukeji.zwbh.constant.NumericConstants;
import com.ruitukeji.zwbh.constant.StringConstants;
import com.ruitukeji.zwbh.entity.UploadImageBean;
import com.ruitukeji.zwbh.utils.ActivityTitleUtils;
import com.ruitukeji.zwbh.utils.JsonUtil;

import java.util.ArrayList;
import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * 企业认证----资质认证
 * Created by Administrator on 2017/2/17.
 */

public class EnterpriseQualificationCertificateActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks, EnterpriseQualificationCertificateContract.View {

    @BindView(id = R.id.img_slegalIdCardIs, click = true)
    private ImageView img_slegalIdCardIs;

    @BindView(id = R.id.img_legalIdCardIs, click = true)
    private ImageView img_legalIdCardIs;

    @BindView(id = R.id.img_legalIdCardBack, click = true)
    private ImageView img_legalIdCardBack;

    @BindView(id = R.id.img_businessLicense, click = true)
    private ImageView img_businessLicense;

    /**
     * 下一步
     */
    @BindView(id = R.id.tv_nextType, click = true)
    private TextView tv_nextType;

    private ImagePicker imagePicker;
    private boolean isPreview = false;
    private String imgUrlIdCardIs = null;
    private boolean isPreviewIs = false;
    private String imgUrlCardIs = null;
    private boolean isPreviewIsBack = false;
    private String imgUrlCardBack = null;
    private boolean isPreviewLicense = false;
    private String imgUrlLicense = null;
    private String com_name;
    private String com_buss_num;
    private String law_person;
    private String identity;
    private String phone;
    private String address;
    private String deposit_name;
    private String bank;
    private String account;
    private String auth_status = null;
    private String sp_identity = null;
    private String sp_hold_pic = null;
    private String sp_front_pic = null;
    private String sp_back_pic = null;

    @Override
    public void setRootView() {
        setContentView(R.layout.activity_enterprisequalificationcertificate);
    }

    @Override
    public void initData() {
        super.initData();
        initImagePicker();
        mPresenter = new EnterpriseQualificationCertificatePresenter(this);
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


        auth_status = getIntent().getStringExtra("auth_status");
        if (!(StringUtils.isEmpty(auth_status) || auth_status.equals("init"))) {
            imgUrlIdCardIs = getIntent().getStringExtra("hold_pic");
            imgUrlCardIs = getIntent().getStringExtra("front_pic");
            imgUrlCardBack = getIntent().getStringExtra("back_pic");
            imgUrlLicense = getIntent().getStringExtra("buss_pic");
            isPreview = true;
            GlideImageLoader.glideLoader(this, imgUrlIdCardIs + "?imageView2/1/w/150/h/150", img_slegalIdCardIs, 1);
            isPreviewIs = true;
            GlideImageLoader.glideLoader(this, imgUrlCardIs + "?imageView2/1/w/300/h/150", img_legalIdCardIs, 1);
            isPreviewIsBack = true;
            GlideImageLoader.glideLoader(this, imgUrlCardBack + "?imageView2/1/w/300/h/150", img_legalIdCardBack, 1);
            isPreviewLicense = true;
            GlideImageLoader.glideLoader(this, imgUrlLicense + "?imageView2/1/w/300/h/150", img_businessLicense, 1);
            sp_identity = getIntent().getStringExtra("sp_identity");
            sp_hold_pic = getIntent().getStringExtra("sp_hold_pic");
            sp_front_pic = getIntent().getStringExtra("sp_front_pic");
            sp_back_pic = getIntent().getStringExtra("sp_back_pic");
        }

    }

    @Override
    public void initWidget() {
        super.initWidget();
        ActivityTitleUtils.initToolbar(aty, getString(R.string.enterpriseAuthentication), true, R.id.titlebar);
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
            case R.id.img_slegalIdCardIs:
                if (isPreview) {
                    images.clear();
                    ImageItem imageItem = new ImageItem();
                    imageItem.path = imgUrlIdCardIs;
                    images.add(imageItem);
                    //打开预览
                    toImagePreviewDelActivity(img_slegalIdCardIs, images, NumericConstants.READ_AND_WRITE_CODE);
                } else {
                    choicePhotoWrapper(NumericConstants.REQUEST_CODE_CHOOSE_PHOTO);
                }
                break;
            case R.id.img_legalIdCardIs:
                if (isPreviewIs) {
                    images.clear();
                    ImageItem imageItem = new ImageItem();
                    imageItem.path = imgUrlCardIs;
                    images.add(imageItem);
                    //打开预览
                    toImagePreviewDelActivity(img_legalIdCardIs, images, NumericConstants.REQUEST_CODE_PREVIEW);
                } else {
                    choicePhotoWrapper(NumericConstants.REQUEST_CODE_PHOTO_PREVIEW);
                }

                break;
            case R.id.img_legalIdCardBack:
                if (isPreviewIsBack) {
                    images.clear();
                    ImageItem imageItem = new ImageItem();
                    imageItem.path = imgUrlCardBack;
                    images.add(imageItem);
                    //打开预览
                    toImagePreviewDelActivity(img_legalIdCardBack, images, NumericConstants.REQUEST_CODE_PREVIEW1);
                } else {
                    choicePhotoWrapper(NumericConstants.REQUEST_CODE_PHOTO_PREVIEW1);
                }

                break;
            case R.id.img_businessLicense:
                if (isPreviewLicense) {
                    images.clear();
                    ImageItem imageItem = new ImageItem();
                    imageItem.path = imgUrlLicense;
                    images.add(imageItem);
                    //打开预览
                    toImagePreviewDelActivity(img_businessLicense, images, NumericConstants.REQUEST_CODE_PREVIEW2);
                } else {
                    choicePhotoWrapper(NumericConstants.REQUEST_CODE_PHOTO_PREVIEW2);
                }
                break;
            case R.id.tv_nextType:
                ((EnterpriseQualificationCertificateContract.Presenter) mPresenter).postEnterpriseQualificationCertificate(imgUrlIdCardIs, imgUrlCardIs, imgUrlCardBack, imgUrlLicense);
                break;
        }
    }

    @Override
    public void getSuccess(String s, int flag) {
        if (flag == 0) {
            Intent intent = new Intent();
            intent.putExtra("com_name", com_name);
            intent.putExtra("com_buss_num", com_buss_num);
            intent.putExtra("law_person", law_person);
            intent.putExtra("identity", identity);
            intent.putExtra("phone", phone);
            intent.putExtra("address", address);
            intent.putExtra("deposit_name", deposit_name);
            intent.putExtra("bank", bank);
            intent.putExtra("account", account);
            intent.putExtra("hold_pic", imgUrlIdCardIs);
            intent.putExtra("front_pic", imgUrlCardIs);
            intent.putExtra("back_pic", imgUrlCardBack);
            intent.putExtra("businessLicense_pic", imgUrlLicense);
            intent.putExtra("sp_identity", sp_identity);
            intent.putExtra("sp_hold_pic", sp_hold_pic);
            intent.putExtra("sp_front_pic", sp_front_pic);
            intent.putExtra("sp_back_pic", sp_back_pic);
            intent.putExtra("auth_status", auth_status);
            intent.setClass(aty, EnterpriseOperatingInformationActivity.class);
            showActivity(aty, intent);
            PreferenceHelper.write(this, StringConstants.FILENAME, "isAvatar", true);
        } else if (flag == NumericConstants.REQUEST_CODE_CHOOSE_PHOTO) {
            UploadImageBean uploadImageBean = (UploadImageBean) JsonUtil.getInstance().json2Obj(s, UploadImageBean.class);
            if (!(StringUtils.isEmpty(uploadImageBean.getResult().getFile().getUrl()))) {
                isPreview = true;
                imgUrlIdCardIs = uploadImageBean.getResult().getFile().getUrl();
                GlideImageLoader.glideLoader(this, imgUrlIdCardIs + "?imageView2/1/w/150/h/150", img_slegalIdCardIs, 1);
            }
        } else if (flag == NumericConstants.REQUEST_CODE_PHOTO_PREVIEW) {
            UploadImageBean uploadImageBean = (UploadImageBean) JsonUtil.getInstance().json2Obj(s, UploadImageBean.class);
            if (!(StringUtils.isEmpty(uploadImageBean.getResult().getFile().getUrl()))) {
                isPreviewIs = true;
                imgUrlCardIs = uploadImageBean.getResult().getFile().getUrl();
                GlideImageLoader.glideLoader(this, imgUrlCardIs + "?imageView2/1/w/300/h/150", img_legalIdCardIs, 1);
            }
        } else if (flag == NumericConstants.REQUEST_CODE_PHOTO_PREVIEW1) {
            UploadImageBean uploadImageBean = (UploadImageBean) JsonUtil.getInstance().json2Obj(s, UploadImageBean.class);
            if (!(StringUtils.isEmpty(uploadImageBean.getResult().getFile().getUrl()))) {
                isPreviewIsBack = true;
                imgUrlCardBack = uploadImageBean.getResult().getFile().getUrl();
                GlideImageLoader.glideLoader(this, imgUrlCardBack + "?imageView2/1/w/300/h/150", img_legalIdCardBack, 1);
            }
        } else if (flag == NumericConstants.REQUEST_CODE_PHOTO_PREVIEW2) {
            UploadImageBean uploadImageBean = (UploadImageBean) JsonUtil.getInstance().json2Obj(s, UploadImageBean.class);
            if (!(StringUtils.isEmpty(uploadImageBean.getResult().getFile().getUrl()))) {
                isPreviewLicense = true;
                imgUrlLicense = uploadImageBean.getResult().getFile().getUrl();
                GlideImageLoader.glideLoader(this, imgUrlLicense + "?imageView2/1/w/300/h/150", img_businessLicense, 1);
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
    public void setPresenter(EnterpriseQualificationCertificateContract.Presenter presenter) {
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
            ((EnterpriseQualificationCertificateContract.Presenter) mPresenter).upLoadImg(images.get(0).path, NumericConstants.REQUEST_CODE_CHOOSE_PHOTO);
        } else if (data != null && requestCode == NumericConstants.REQUEST_CODE_PHOTO_PREVIEW) {
            images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
            if (images == null || images.size() == 0) {
                ViewInject.toast(getString(R.string.noData));
                return;
            }
            ((EnterpriseQualificationCertificateContract.Presenter) mPresenter).upLoadImg(images.get(0).path, NumericConstants.REQUEST_CODE_PHOTO_PREVIEW);
        } else if (data != null && requestCode == NumericConstants.REQUEST_CODE_PHOTO_PREVIEW1) {
            images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
            if (images == null || images.size() == 0) {
                ViewInject.toast(getString(R.string.noData));
                return;
            }
            ((EnterpriseQualificationCertificateContract.Presenter) mPresenter).upLoadImg(images.get(0).path, NumericConstants.REQUEST_CODE_PHOTO_PREVIEW1);
        } else if (data != null && requestCode == NumericConstants.REQUEST_CODE_PHOTO_PREVIEW2) {
            images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
            if (images == null || images.size() == 0) {
                ViewInject.toast(getString(R.string.noData));
                return;
            }
            ((EnterpriseQualificationCertificateContract.Presenter) mPresenter).upLoadImg(images.get(0).path, NumericConstants.REQUEST_CODE_PHOTO_PREVIEW2);
        } else {
            ViewInject.toast(getString(R.string.noData));
        }
    }


    //预览图片返回
    @SuppressWarnings("unchecked")
    private void previewImageReturn(int requestCode, Intent data) {
        images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_IMAGE_ITEMS);
        //预览图片返回
        if (data != null && images != null && requestCode == NumericConstants.READ_AND_WRITE_CODE && images.size() == 0) {
            isPreview = false;
            imgUrlIdCardIs = null;
            GlideImageLoader.glideLoader(this, R.mipmap.holdingidentitycertification, img_slegalIdCardIs, 1);
        } else if (data != null && images != null && requestCode == NumericConstants.REQUEST_CODE_PREVIEW && images.size() == 0) {
            isPreviewIs = false;
            imgUrlCardIs = null;
            GlideImageLoader.glideLoader(this, R.mipmap.legalidcardis, img_legalIdCardIs, 1);
        } else if (data != null && images != null && requestCode == NumericConstants.REQUEST_CODE_PREVIEW1 && images.size() == 0) {
            isPreviewIsBack = false;
            imgUrlCardBack = null;
            GlideImageLoader.glideLoader(this, R.mipmap.legalidcardback, img_legalIdCardBack, 1);
        } else if (data != null && images != null && requestCode == NumericConstants.REQUEST_CODE_PREVIEW2 && images.size() == 0) {
            isPreviewLicense = false;
            imgUrlLicense = null;
            GlideImageLoader.glideLoader(this, R.mipmap.businesslicense, img_businessLicense, 1);
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
