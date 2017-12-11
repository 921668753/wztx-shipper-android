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
 * 个人认证----身份认证
 * Created by Administrator on 2017/2/17.
 */

public class PersonalIdentityAuthenticationActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks, PersonalIdentityAuthenticationContract.View {

    @BindView(id = R.id.img_idCardIs, click = true)
    private ImageView img_idCardIs;


    @BindView(id = R.id.img_idCardBack, click = true)
    private ImageView img_idCardBack;

    /**
     * 是否预览
     */
    private boolean isPreviewIs = false;
    private boolean isPreviewIsBack = false;

    /**
     * 下一步
     */
    @BindView(id = R.id.tv_nextType, click = true)
    private TextView tv_nextType;

    private ImagePicker imagePicker;
    private String imgUrl = null;
    private String imgUrlBack = null;
    private String real_name;
    private int sex = 0;
    private String identity;
    private String hold_pic;

    private SweetAlertDialog sweetAlertDialog = null;
    private String auth_status = null;

    @Override
    public void setRootView() {
        setContentView(R.layout.activity_personalidentityauthentication);
    }

    @Override
    public void initData() {
        super.initData();
        initDialog();
        mPresenter = new PersonalIdentityAuthenticationPresenter(this);
        images = new ArrayList<>();
        initImagePicker();
        real_name = getIntent().getStringExtra("real_name");
        sex = getIntent().getIntExtra("sex", 0);
        identity = getIntent().getStringExtra("identity");
        hold_pic = getIntent().getStringExtra("hold_pic");
        auth_status = getIntent().getStringExtra("auth_status");
        if (!(StringUtils.isEmpty(auth_status) || auth_status.equals("init"))) {
            imgUrlBack = getIntent().getStringExtra("back_pic");
            isPreviewIsBack = true;
            GlideImageLoader.glideLoader(this, imgUrlBack + "?imageView2/1/w/300/h/150", img_idCardBack, 1);
            imgUrl = getIntent().getStringExtra("front_pic");
            isPreviewIs = true;
            GlideImageLoader.glideLoader(this, imgUrl + "?imageView2/1/w/300/h/150", img_idCardIs, 1);
        }
    }

    @Override
    public void initWidget() {
        super.initWidget();
        ActivityTitleUtils.initToolbar(aty, getString(R.string.personalAuthentication), true, R.id.titlebar);
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
            case R.id.img_idCardIs:
                if (isPreviewIs) {
                    images.clear();
                    ImageItem imageItem = new ImageItem();
                    imageItem.path = imgUrl;
                    images.add(imageItem);
                    //打开预览
                    toImagePreviewDelActivity(img_idCardIs, images, NumericConstants.REQUEST_CODE_PREVIEW);
                } else {
                    choicePhotoWrapper(NumericConstants.REQUEST_CODE_CHOOSE_PHOTO);
                }
                break;
            case R.id.img_idCardBack:
                if (isPreviewIsBack) {
                    images.clear();
                    ImageItem imageItem = new ImageItem();
                    imageItem.path = imgUrlBack;
                    images.add(imageItem);
                    //打开预览
                    toImagePreviewDelActivity(img_idCardBack, images, NumericConstants.REQUEST_CODE_PREVIEW1);
                } else {
                    choicePhotoWrapper(NumericConstants.REQUEST_CODE_PHOTO_PREVIEW);
                }
                break;
            case R.id.tv_nextType:
                if (sweetAlertDialog == null) {
                    initDialog();
                }
                ((PersonalIdentityAuthenticationContract.Presenter) mPresenter).postPersonalIdentityAuthentication(sweetAlertDialog, real_name, sex, identity, hold_pic, imgUrl, imgUrlBack);
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
            PreferenceHelper.write(this, StringConstants.FILENAME, "isAvatar", true);
            KJActivityStack.create().finishActivity(PersonalInformationActivity.class);
            ViewInject.toast(getString(R.string.submittedSuccessfully));
            PreferenceHelper.write(aty, StringConstants.FILENAME, "sex", sex);
            PreferenceHelper.write(aty, StringConstants.FILENAME, "real_name", real_name);
            PreferenceHelper.write(aty, StringConstants.FILENAME, "auth_status", "check");
            finish();
        } else if (flag == NumericConstants.REQUEST_CODE_CHOOSE_PHOTO) {
            UploadImageBean uploadImageBean = (UploadImageBean) JsonUtil.getInstance().json2Obj(s, UploadImageBean.class);
            if (!(StringUtils.isEmpty(uploadImageBean.getResult().getFile().getUrl()))) {
                isPreviewIs = true;
                imgUrl = uploadImageBean.getResult().getFile().getUrl();
                GlideImageLoader.glideLoader(this, imgUrl + "?imageView2/1/w/300/h/150", img_idCardIs, 1);
            }
        } else if (flag == NumericConstants.REQUEST_CODE_PHOTO_PREVIEW) {
            UploadImageBean uploadImageBean = (UploadImageBean) JsonUtil.getInstance().json2Obj(s, UploadImageBean.class);
            if (!(StringUtils.isEmpty(uploadImageBean.getResult().getFile().getUrl()))) {
                isPreviewIsBack = true;
                imgUrlBack = uploadImageBean.getResult().getFile().getUrl();
                GlideImageLoader.glideLoader(this, imgUrlBack + "?imageView2/1/w/300/h/150", img_idCardBack, 1);
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
    public void setPresenter(PersonalIdentityAuthenticationContract.Presenter presenter) {
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
            ((PersonalIdentityAuthenticationContract.Presenter) mPresenter).upLoadImg(images.get(0).path, NumericConstants.REQUEST_CODE_CHOOSE_PHOTO);
        } else if (data != null && requestCode == NumericConstants.REQUEST_CODE_PHOTO_PREVIEW) {
            images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
            if (images == null || images.size() == 0) {
                ViewInject.toast(getString(R.string.noData));
                return;
            }
            ((PersonalIdentityAuthenticationContract.Presenter) mPresenter).upLoadImg(images.get(0).path, NumericConstants.REQUEST_CODE_PHOTO_PREVIEW);
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
            isPreviewIs = false;
            imgUrl = null;
            GlideImageLoader.glideLoader(this, R.mipmap.idcardis, img_idCardIs, 1);
        } else if (data != null && images != null && requestCode == NumericConstants.REQUEST_CODE_PREVIEW1 && images.size() == 0) {
            isPreviewIsBack = false;
            imgUrlBack = null;
            GlideImageLoader.glideLoader(this, R.mipmap.idcardback, img_idCardBack, 1);
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
