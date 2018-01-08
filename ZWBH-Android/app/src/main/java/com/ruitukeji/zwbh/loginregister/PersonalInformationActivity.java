package com.ruitukeji.zwbh.loginregister;

import android.Manifest;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

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
import com.ruitukeji.zwbh.entity.mine.shippercertification.IndividualOwnersBean;
import com.ruitukeji.zwbh.entity.UploadImageBean;
import com.ruitukeji.zwbh.utils.ActivityTitleUtils;
import com.ruitukeji.zwbh.utils.JsonUtil;

import java.util.ArrayList;
import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * 个人认证----基本信息
 * Created by Administrator on 2017/2/17.
 */

public class PersonalInformationActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks, PersonalInformationContract.View {

    /**
     * 姓名
     */
    @BindView(id = R.id.et_accountNumber)
    private EditText et_accountNumber;
    /**
     * 性别 1=男 2=女 0=未知.
     */
    @BindView(id = R.id.img_man, click = true)
    private ImageView img_man;
    @BindView(id = R.id.img_woman, click = true)
    private ImageView img_woman;

    private int sex = 1;
    /**
     * 身份证号
     */
    @BindView(id = R.id.et_IdNumber)
    private EditText et_IdNumber;


    @BindView(id = R.id.img_holdingIdentityCertification, click = true)
    private ImageView img_holdingIdentityCertification;

    /**
     * 下一步
     */
    @BindView(id = R.id.tv_nextType, click = true)
    private TextView tv_nextType;

    /**
     * 是否预览
     */
    private boolean isPreview = false;

    private ImagePicker imagePicker;
    private String imgUrl = null;
    private String auth_status = null;
    private String back_pic = null;
    private String front_pic = null;


    @Override
    public void setRootView() {
        setContentView(R.layout.activity_personalinformation);
    }


    @Override
    public void initData() {
        super.initData();
        mPresenter = new PersonalInformationPresenter(this);
        images = new ArrayList<ImageItem>();
        initImagePicker();
    }

    @Override
    public void initWidget() {
        super.initWidget();
        ActivityTitleUtils.initToolbar(aty, getString(R.string.personalAuthentication), true, R.id.titlebar);
        auth_status = getIntent().getStringExtra("auth_status");
        if (!(StringUtils.isEmpty(auth_status) || auth_status.equals("init"))) {
            ((PersonalInformationContract.Presenter) mPresenter).getPersonalCertificate();
        }
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
        imagePicker.setSaveRectangle(true);
    }

    @Override
    public void widgetClick(View v) {
        super.widgetClick(v);
        switch (v.getId()) {
            case R.id.img_man:
                img_man.setImageResource(R.mipmap.selected);
                img_woman.setImageResource(R.mipmap.selected1);
                sex = 1;
                break;
            case R.id.img_woman:
                img_man.setImageResource(R.mipmap.selected1);
                img_woman.setImageResource(R.mipmap.selected);
                sex = 2;
                break;
            case R.id.img_holdingIdentityCertification:
                if (isPreview) {
                    images.clear();
                    ImageItem imageItem = new ImageItem();
                    imageItem.path = imgUrl;
                    images.add(imageItem);
                    //打开预览
                    toImagePreviewDelActivity(img_holdingIdentityCertification, images);
                } else {
                    choicePhotoWrapper();
                }
                break;
            case R.id.tv_nextType:
                ((PersonalInformationContract.Presenter) mPresenter).postPersonalInformation(et_accountNumber.getText().toString(), sex, et_IdNumber.getText().toString(), imgUrl);
                break;
        }
    }

    @Override
    public void getSuccess(String s, int flag) {

        if (flag == 0) {
            Intent intent = new Intent();
            intent.putExtra("real_name", et_accountNumber.getText().toString());
            intent.putExtra("sex", sex);
            intent.putExtra("identity", et_IdNumber.getText().toString());
            intent.putExtra("hold_pic", imgUrl);
            intent.setClass(aty, PersonalIdentityAuthenticationActivity.class);
            intent.putExtra("back_pic", back_pic);
            intent.putExtra("front_pic", front_pic);
            intent.putExtra("auth_status", auth_status);
            showActivity(aty, intent);
        } else if (flag == 1) {
            UploadImageBean uploadImageBean = (UploadImageBean) JsonUtil.getInstance().json2Obj(s, UploadImageBean.class);
            if (!(StringUtils.isEmpty(uploadImageBean.getResult().getFile().getUrl()))) {
                imgUrl = uploadImageBean.getResult().getFile().getUrl();
                isPreview = true;
                GlideImageLoader.glideLoader(this, imgUrl + "?imageView2/1/w/140/h/140", img_holdingIdentityCertification, 1);
            }
        } else if (flag == 2) {
            IndividualOwnersBean individualOwnersBean = (IndividualOwnersBean) JsonUtil.getInstance().json2Obj(s, IndividualOwnersBean.class);
            et_accountNumber.setText(individualOwnersBean.getResult().getReal_name());
            et_IdNumber.setText(individualOwnersBean.getResult().getIdentity());
            if (individualOwnersBean.getResult().getSex() == 1) {
                img_man.setImageResource(R.mipmap.selected);
                img_woman.setImageResource(R.mipmap.selected1);
                sex = 1;
            } else if (individualOwnersBean.getResult().getSex() == 2) {
                img_man.setImageResource(R.mipmap.selected1);
                img_woman.setImageResource(R.mipmap.selected);
                sex = 2;
            } else {
                img_man.setImageResource(R.mipmap.selected);
                img_woman.setImageResource(R.mipmap.selected1);
                sex = 1;
            }
            imgUrl = individualOwnersBean.getResult().getHold_pic();
            isPreview = true;
            GlideImageLoader.glideLoader(this, imgUrl + "?imageView2/1/w/140/h/140", img_holdingIdentityCertification, 1);
            back_pic = individualOwnersBean.getResult().getBack_pic();
            front_pic = individualOwnersBean.getResult().getFront_pic();
        }
        dismissLoadingDialog();
    }

    @Override
    public void error(String msg) {
        dismissLoadingDialog();
        ViewInject.toast(msg);
    }

    @Override
    public void setPresenter(PersonalInformationContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @AfterPermissionGranted(NumericConstants.REQUEST_CODE_PERMISSION_PHOTO_PICKER)
    private void choicePhotoWrapper() {
        String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
        if (EasyPermissions.hasPermissions(this, perms)) {
            // 拍照后照片的存放目录，改成你自己拍照后要存放照片的目录。如果不传递该参数的话就没有拍照功能
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
                ((PersonalInformationContract.Presenter) mPresenter).upLoadImg(images.get(0).path);
            } else {
                ViewInject.toast(getString(R.string.noData));
            }
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

    //打开预览
    @SuppressWarnings("unchecked")
    private void toImagePreviewDelActivity(View view, ArrayList<ImageItem> list) {
        Intent intentPreview = new Intent(this, ImagePreviewDelActivity.class);
        intentPreview.putExtra(ImagePicker.EXTRA_IMAGE_ITEMS, list);
        intentPreview.putExtra(ImagePicker.EXTRA_SELECTED_IMAGE_POSITION, 0);
        intentPreview.putExtra(ImagePicker.EXTRA_FROM_ITEMS, true);
        startActivityForResult(intentPreview, NumericConstants.REQUEST_CODE_PREVIEW);
    }

    //预览图片返回
    @SuppressWarnings("unchecked")
    private void previewImageReturn(int requestCode, Intent data) {
        images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_IMAGE_ITEMS);
        //预览图片返回
        if (data != null && images != null && requestCode == NumericConstants.REQUEST_CODE_PREVIEW && images.size() == 0) {
            isPreview = false;
            imgUrl = null;
            GlideImageLoader.glideLoader(this, R.mipmap.holdingidentitycertification, img_holdingIdentityCertification, 1);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        images.clear();
        images = null;
    }
}
