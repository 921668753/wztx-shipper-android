package com.ruitukeji.zwbh.mine.shippercertification.certificationfragment;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.ruitukeji.zwbh.common.BaseFragment;
import com.ruitukeji.zwbh.common.BindView;
import com.ruitukeji.zwbh.common.GlideImageLoader;
import com.ruitukeji.zwbh.common.ViewInject;
import com.ruitukeji.zwbh.constant.NumericConstants;
import com.ruitukeji.zwbh.constant.StringConstants;
import com.ruitukeji.zwbh.entity.mine.shippercertification.CompanyOwnerBean;
import com.ruitukeji.zwbh.entity.UploadImageBean;
import com.ruitukeji.zwbh.mine.shippercertification.ShipperCertificationActivity;
import com.ruitukeji.zwbh.utils.JsonUtil;
import com.ruitukeji.zwbh.utils.rx.MsgEvent;
import com.ruitukeji.zwbh.utils.rx.RxBus;

import java.util.ArrayList;
import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

import static com.ruitukeji.zwbh.constant.NumericConstants.REQUEST_CODE_CHOOSE_PHOTO;
import static com.ruitukeji.zwbh.constant.NumericConstants.REQUEST_CODE_PHOTO_PREVIEW;
import static com.ruitukeji.zwbh.constant.NumericConstants.REQUEST_CODE_PHOTO_PREVIEW1;
import static com.ruitukeji.zwbh.constant.NumericConstants.REQUEST_CODE_PHOTO_PREVIEW2;
import static com.ruitukeji.zwbh.constant.NumericConstants.REQUEST_CODE_PHOTO_PREVIEW3;
import static com.ruitukeji.zwbh.constant.NumericConstants.REQUEST_CODE_PHOTO_PREVIEW4;
import static com.ruitukeji.zwbh.constant.NumericConstants.REQUEST_CODE_PHOTO_PREVIEW5;

/**
 * 公司货主
 * Created by Administrator on 2017/12/14.
 */

public class CompanyOwnerFragment extends BaseFragment implements EasyPermissions.PermissionCallbacks, CompanyOwnerContract.View {

    private ShipperCertificationActivity aty;

    /**
     * 认证状态
     */
    @BindView(id = R.id.tv_unauthorized)
    private TextView tv_unauthorized;

    /**
     * 公司名称
     */
    @BindView(id = R.id.et_companyName)
    private EditText et_companyName;

    /**
     * 营业执照
     */
    @BindView(id = R.id.et_businessLicense)
    private EditText et_businessLicense;

    /**
     * 公司地址
     */
    @BindView(id = R.id.et_companyAddress)
    private EditText et_companyAddress;

    /**
     * 公司电话
     */
    @BindView(id = R.id.et_phoneCompany)
    private EditText et_phoneCompany;

    /**
     * 营业执照
     */
    @BindView(id = R.id.img_uploadPictureBusinessLicense, click = true)
    private ImageView img_uploadPictureBusinessLicense;
    String uploadPictureBusinessLicenseUrl = "";
    private boolean isUploadPictureBusinessLicense = true;


    /**
     * 法人姓名
     */
    @BindView(id = R.id.et_legalPersonName)
    private EditText et_legalPersonName;

    /**
     * 法人身份证号
     */
    @BindView(id = R.id.et_legalPersonIdNumber)
    private EditText et_legalPersonIdNumber;

    /**
     * 法人身份证正
     */
    @BindView(id = R.id.img_uploadYourIdCard, click = true)
    private ImageView img_uploadYourIdCard;
    String uploadYourIdCardUrl = "";
    private boolean isUploadYourIdCard = true;

    /**
     * 法人身份证反
     */
    @BindView(id = R.id.img_uploadClearYourIdCard, click = true)
    private ImageView img_uploadClearYourIdCard;
    String uploadClearYourIdCardUrl = "";
    private boolean isUploadClearYourIdCard = true;

    /**
     * 法人身份证手持
     */
    @BindView(id = R.id.img_uploudHoldingIdPhoto, click = true)
    private ImageView img_uploudHoldingIdPhoto;
    String uploudHoldingIdPhotoUrl = "";
    private boolean isUploudHoldingIdPhoto = true;

    /**
     * 操作人姓名
     */
    @BindView(id = R.id.et_operationName)
    private EditText et_operationName;

    /**
     * 操作人身份证号
     */
    @BindView(id = R.id.et_IdNumber)
    private EditText et_IdNumber;


    /**
     * 操作人身份证正
     */
    @BindView(id = R.id.img_uploadYourIdCardOperation, click = true)
    private ImageView img_uploadYourIdCardOperation;
    String uploadYourIdCardOperationUrl = "";
    private boolean isUploadYourIdCardOperation = true;

    /**
     * 操作人身份证反
     */
    @BindView(id = R.id.img_uploadClearYourIdCardOperation, click = true)
    private ImageView img_uploadClearYourIdCardOperation;
    String uploadClearYourIdCardOperationUrl = "";
    private boolean isUploadClearYourIdCardOperation = true;

    /**
     * 操作人身份证手持
     */
    @BindView(id = R.id.img_uploudHoldingIdPhotoOperation, click = true)
    private ImageView img_uploudHoldingIdPhotoOperation;
    String uploudHoldingIdPhotoOperationUrl = "";
    private boolean isUploudHoldingIdPhotoOperation = true;

    /**
     * 提交
     */
    @BindView(id = R.id.tv_submit, click = true)
    private TextView tv_submit;


    private ImagePicker imagePicker;
    ArrayList<ImageItem> images = null;

    @Override
    protected View inflaterView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        aty = (ShipperCertificationActivity) getActivity();
        return View.inflate(aty, R.layout.fragment_companyowner, null);
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter = new CompanyOwnerPresenter(this);
        images = new ArrayList<>();
        initImagePicker();
        String auth_status = PreferenceHelper.readString(aty, StringConstants.FILENAME, "auth_status", "init");
        if (auth_status != null && auth_status.equals("init")) {
            return;
        }
        showLoadingDialog(getString(R.string.dataLoad));
        ((CompanyOwnerContract.Presenter) mPresenter).getCompanyOwner();
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
    protected void initWidget(View parentView) {
        super.initWidget(parentView);
        String auth_status = PreferenceHelper.readString(aty, StringConstants.FILENAME, "auth_status", "init");
        if (auth_status != null && auth_status.equals("pass")) {
            tv_unauthorized.setText(getString(R.string.authorized));
        } else if (auth_status != null && auth_status.equals("check")) {
            tv_unauthorized.setText(getString(R.string.inAuthentication));
        } else if (auth_status != null && auth_status.equals("refuse")) {
            tv_unauthorized.setText(getString(R.string.refuse));
        } else {
            tv_unauthorized.setText(getString(R.string.unauthorized));
        }
    }

    @Override
    protected void widgetClick(View v) {
        super.widgetClick(v);
        switch (v.getId()) {
            case R.id.img_uploadPictureBusinessLicense:
                if (isUploadPictureBusinessLicense) {
                    choicePhotoWrapper(REQUEST_CODE_CHOOSE_PHOTO);
                    break;
                }
                images.clear();
                ImageItem imageItem = new ImageItem();
                imageItem.path = uploadPictureBusinessLicenseUrl;
                images.add(imageItem);
                //打开预览
                toImagePreviewDelActivity(img_uploadPictureBusinessLicense, images, NumericConstants.REQUEST_CODE_PREVIEW);
                break;
            case R.id.img_uploadYourIdCard:
                if (isUploadYourIdCard) {
                    choicePhotoWrapper(REQUEST_CODE_PHOTO_PREVIEW);
                    break;
                }
                images.clear();
                ImageItem imageItem1 = new ImageItem();
                imageItem1.path = uploadYourIdCardUrl;
                images.add(imageItem1);
                //打开预览
                toImagePreviewDelActivity(img_uploadYourIdCard, images, NumericConstants.REQUEST_CODE_PREVIEW1);
                break;
            case R.id.img_uploadClearYourIdCard:
                if (isUploadClearYourIdCard) {
                    choicePhotoWrapper(REQUEST_CODE_PHOTO_PREVIEW1);
                    break;
                }
                images.clear();
                ImageItem imageItem2 = new ImageItem();
                imageItem2.path = uploadClearYourIdCardUrl;
                images.add(imageItem2);
                //打开预览
                toImagePreviewDelActivity(img_uploadClearYourIdCard, images, NumericConstants.REQUEST_CODE_PREVIEW2);
                break;
            case R.id.img_uploudHoldingIdPhoto:
                if (isUploudHoldingIdPhoto) {
                    choicePhotoWrapper(REQUEST_CODE_PHOTO_PREVIEW2);
                    break;
                }
                images.clear();
                ImageItem imageItem3 = new ImageItem();
                imageItem3.path = uploudHoldingIdPhotoUrl;
                images.add(imageItem3);
                //打开预览
                toImagePreviewDelActivity(img_uploudHoldingIdPhoto, images, NumericConstants.REQUEST_CODE_PREVIEW3);
                break;
            case R.id.img_uploadYourIdCardOperation:
                if (isUploadYourIdCardOperation) {
                    choicePhotoWrapper(REQUEST_CODE_PHOTO_PREVIEW3);
                    break;
                }
                images.clear();
                ImageItem imageItem4 = new ImageItem();
                imageItem4.path = uploadYourIdCardOperationUrl;
                images.add(imageItem4);
                //打开预览
                toImagePreviewDelActivity(img_uploadYourIdCardOperation, images, NumericConstants.REQUEST_CODE_PREVIEW4);
                break;
            case R.id.img_uploadClearYourIdCardOperation:
                if (isUploadClearYourIdCardOperation) {
                    choicePhotoWrapper(REQUEST_CODE_PHOTO_PREVIEW4);
                    break;
                }
                images.clear();
                ImageItem imageItem5 = new ImageItem();
                imageItem5.path = uploadClearYourIdCardOperationUrl;
                images.add(imageItem5);
                //打开预览
                toImagePreviewDelActivity(img_uploadClearYourIdCardOperation, images, NumericConstants.REQUEST_CODE_PREVIEW5);
                break;
            case R.id.img_uploudHoldingIdPhotoOperation:
                if (isUploudHoldingIdPhotoOperation) {
                    choicePhotoWrapper(REQUEST_CODE_PHOTO_PREVIEW5);
                    break;
                }
                images.clear();
                ImageItem imageItem6 = new ImageItem();
                imageItem6.path = uploudHoldingIdPhotoOperationUrl;
                images.add(imageItem6);
                //打开预览
                toImagePreviewDelActivity(img_uploudHoldingIdPhotoOperation, images, NumericConstants.REQUEST_CODE_PREVIEW6);
                break;
            case R.id.tv_submit:
                ((CompanyOwnerContract.Presenter) mPresenter).postCompanyOwner(et_companyName.getText().toString().trim(), et_businessLicense.getText().toString().trim(),
                        et_companyAddress.getText().toString().trim(), et_phoneCompany.getText().toString().trim(), uploadPictureBusinessLicenseUrl, et_legalPersonName.getText().toString().trim(),
                        et_legalPersonIdNumber.getText().toString().trim(), uploadYourIdCardUrl, uploadClearYourIdCardUrl, uploudHoldingIdPhotoUrl, et_operationName.getText().toString().trim(),
                        et_IdNumber.getText().toString().trim(), uploadYourIdCardOperationUrl, uploadClearYourIdCardOperationUrl, uploudHoldingIdPhotoOperationUrl);
                break;
        }
    }

    @Override
    public void setPresenter(CompanyOwnerContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void getSuccess(String success, int flag) {
        if (flag == 0) {
            PreferenceHelper.write(aty, StringConstants.FILENAME, "auth_status", "check");
            ViewInject.toast(getString(R.string.submittedSuccessfully));
            tv_unauthorized.setText(getString(R.string.inAuthentication));
            /**
             * 发送消息
             */
            RxBus.getInstance().post(new MsgEvent<String>("RxBusShipperCertificationEvent"));
            dismissLoadingDialog();
            aty.finish();
            return;
        } else if (flag == 1) {
            CompanyOwnerBean companyOwnerBean = (CompanyOwnerBean) JsonUtil.getInstance().json2Obj(success, CompanyOwnerBean.class);
            String auth_status = companyOwnerBean.getResult().getAuth_status();
            if (auth_status != null && auth_status.equals("pass")) {
                tv_unauthorized.setText(getString(R.string.authorized));
            } else if (auth_status != null && auth_status.equals("check")) {
                tv_unauthorized.setText(getString(R.string.inAuthentication));
            } else if (auth_status != null && auth_status.equals("refuse")) {
                tv_unauthorized.setText(getString(R.string.refuse));
            } else {
                tv_unauthorized.setText(getString(R.string.unauthorized));
            }
            et_companyName.setText(companyOwnerBean.getResult().getCom_name());
            et_businessLicense.setText(companyOwnerBean.getResult().getCom_buss_num());
            et_companyAddress.setText(companyOwnerBean.getResult().getAddress());
            et_phoneCompany.setText(companyOwnerBean.getResult().getCom_phone());
            uploadPictureBusinessLicenseUrl = companyOwnerBean.getResult().getBuss_pic();
            GlideImageLoader.glideRoundRectangleLoader(aty, uploadPictureBusinessLicenseUrl + "?imageView2/1/w/161/h/161", 3, img_uploadPictureBusinessLicense, R.mipmap.default_image);
            isUploadPictureBusinessLicense = false;

            et_legalPersonName.setText(companyOwnerBean.getResult().getLaw_person());
            et_legalPersonIdNumber.setText(companyOwnerBean.getResult().getLaw_identity());
            uploadYourIdCardUrl = companyOwnerBean.getResult().getLaw_front_pic();
            GlideImageLoader.glideRoundRectangleLoader(aty, uploadYourIdCardUrl + "?imageView2/1/w/161/h/103", 3, img_uploadYourIdCard, R.mipmap.default_image);
            isUploadYourIdCard = false;
            uploadClearYourIdCardUrl = companyOwnerBean.getResult().getLaw_back_pic();
            GlideImageLoader.glideRoundRectangleLoader(aty, uploadClearYourIdCardUrl + "?imageView2/1/w/161/h/103", 3, img_uploadClearYourIdCard, R.mipmap.default_image);
            isUploadClearYourIdCard = false;
            uploudHoldingIdPhotoUrl = companyOwnerBean.getResult().getLaw_hold_pic();
            GlideImageLoader.glideRoundRectangleLoader(aty, uploudHoldingIdPhotoUrl + "?imageView2/1/w/161/h/103", 3, img_uploudHoldingIdPhoto, R.mipmap.default_image);
            isUploudHoldingIdPhoto = false;
            et_operationName.setText(companyOwnerBean.getResult().getSp_identity_name());
            et_IdNumber.setText(companyOwnerBean.getResult().getIdentity());

            uploadYourIdCardOperationUrl = companyOwnerBean.getResult().getFront_pic();
            GlideImageLoader.glideRoundRectangleLoader(aty, uploadYourIdCardOperationUrl + "?imageView2/1/w/161/h/103", 3, img_uploadYourIdCardOperation, R.mipmap.default_image);
            isUploadYourIdCardOperation = false;

            uploadClearYourIdCardOperationUrl = companyOwnerBean.getResult().getBack_pic();
            GlideImageLoader.glideRoundRectangleLoader(aty, uploadClearYourIdCardOperationUrl + "?imageView2/1/w/161/h/103", 3, img_uploadClearYourIdCardOperation, R.mipmap.default_image);
            isUploadClearYourIdCardOperation = false;

            uploudHoldingIdPhotoOperationUrl = companyOwnerBean.getResult().getHold_pic();
            GlideImageLoader.glideRoundRectangleLoader(aty, uploudHoldingIdPhotoOperationUrl + "?imageView2/1/w/161/h/103", 3, img_uploudHoldingIdPhotoOperation, R.mipmap.default_image);
            isUploudHoldingIdPhotoOperation = false;
        } else if (flag == REQUEST_CODE_CHOOSE_PHOTO) {
            UploadImageBean uploadImageBean = (UploadImageBean) JsonUtil.getInstance().json2Obj(success, UploadImageBean.class);
            if (!(StringUtils.isEmpty(uploadImageBean.getResult().getFile().getUrl()))) {
                uploadPictureBusinessLicenseUrl = uploadImageBean.getResult().getFile().getUrl();
                GlideImageLoader.glideRoundRectangleLoader(aty, uploadPictureBusinessLicenseUrl + "?imageView2/1/w/161/h/103", 3, img_uploadPictureBusinessLicense, R.mipmap.default_image);
                isUploadPictureBusinessLicense = false;
            }
        } else if (flag == REQUEST_CODE_PHOTO_PREVIEW) {
            UploadImageBean uploadImageBean = (UploadImageBean) JsonUtil.getInstance().json2Obj(success, UploadImageBean.class);
            if (!(StringUtils.isEmpty(uploadImageBean.getResult().getFile().getUrl()))) {
                uploadYourIdCardUrl = uploadImageBean.getResult().getFile().getUrl();
                GlideImageLoader.glideRoundRectangleLoader(aty, uploadYourIdCardUrl + "?imageView2/1/w/161/h/103", 3, img_uploadYourIdCard, R.mipmap.default_image);
                isUploadYourIdCard = false;
            }
        } else if (flag == REQUEST_CODE_PHOTO_PREVIEW1) {
            UploadImageBean uploadImageBean = (UploadImageBean) JsonUtil.getInstance().json2Obj(success, UploadImageBean.class);
            if (!(StringUtils.isEmpty(uploadImageBean.getResult().getFile().getUrl()))) {
                uploadClearYourIdCardUrl = uploadImageBean.getResult().getFile().getUrl();
                GlideImageLoader.glideRoundRectangleLoader(aty, uploadClearYourIdCardUrl + "?imageView2/1/w/161/h/103", 3, img_uploadClearYourIdCard, R.mipmap.default_image);
                isUploadClearYourIdCard = false;
            }
        } else if (flag == REQUEST_CODE_PHOTO_PREVIEW2) {
            UploadImageBean uploadImageBean = (UploadImageBean) JsonUtil.getInstance().json2Obj(success, UploadImageBean.class);
            if (!(StringUtils.isEmpty(uploadImageBean.getResult().getFile().getUrl()))) {
                uploudHoldingIdPhotoUrl = uploadImageBean.getResult().getFile().getUrl();
                GlideImageLoader.glideRoundRectangleLoader(aty, uploudHoldingIdPhotoUrl + "?imageView2/1/w/161/h/103", 3, img_uploudHoldingIdPhoto, R.mipmap.default_image);
                isUploudHoldingIdPhoto = false;
            }
        } else if (flag == REQUEST_CODE_PHOTO_PREVIEW3) {
            UploadImageBean uploadImageBean = (UploadImageBean) JsonUtil.getInstance().json2Obj(success, UploadImageBean.class);
            if (!(StringUtils.isEmpty(uploadImageBean.getResult().getFile().getUrl()))) {
                uploadYourIdCardOperationUrl = uploadImageBean.getResult().getFile().getUrl();
                GlideImageLoader.glideRoundRectangleLoader(aty, uploadYourIdCardOperationUrl + "?imageView2/1/w/161/h/103", 3, img_uploadYourIdCardOperation, R.mipmap.default_image);
                isUploadYourIdCardOperation = false;
            }
        } else if (flag == REQUEST_CODE_PHOTO_PREVIEW4) {
            UploadImageBean uploadImageBean = (UploadImageBean) JsonUtil.getInstance().json2Obj(success, UploadImageBean.class);
            if (!(StringUtils.isEmpty(uploadImageBean.getResult().getFile().getUrl()))) {
                uploadClearYourIdCardOperationUrl = uploadImageBean.getResult().getFile().getUrl();
                GlideImageLoader.glideRoundRectangleLoader(aty, uploadClearYourIdCardOperationUrl + "?imageView2/1/w/161/h/103", 3, img_uploadClearYourIdCardOperation, R.mipmap.default_image);
                isUploadClearYourIdCardOperation = false;
            }
        } else if (flag == REQUEST_CODE_PHOTO_PREVIEW5) {
            UploadImageBean uploadImageBean = (UploadImageBean) JsonUtil.getInstance().json2Obj(success, UploadImageBean.class);
            if (!(StringUtils.isEmpty(uploadImageBean.getResult().getFile().getUrl()))) {
                uploudHoldingIdPhotoOperationUrl = uploadImageBean.getResult().getFile().getUrl();
                GlideImageLoader.glideRoundRectangleLoader(aty, uploudHoldingIdPhotoOperationUrl + "?imageView2/1/w/161/h/103", 3, img_uploudHoldingIdPhotoOperation, R.mipmap.default_image);
                isUploudHoldingIdPhotoOperation = false;
            }
        }
        dismissLoadingDialog();
    }

    @Override
    public void errorMsg(String msg, int flag) {
        toLigon(msg);
        ViewInject.toast(msg);
        dismissLoadingDialog();
    }

    @AfterPermissionGranted(NumericConstants.REQUEST_CODE_PERMISSION_PHOTO_PICKER)
    private void choicePhotoWrapper(int code) {
        String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
        if (EasyPermissions.hasPermissions(aty, perms)) {
            // 拍照后照片的存放目录，改成你自己拍照后要存放照片的目录。如果不传递该参数的话就没有拍照功能
            Intent intent = new Intent(aty, ImageGridActivity.class);
            intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS, true); // 是否是直接打开相机
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


    @SuppressWarnings("unchecked")
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == requestCode) {
                images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                if (images == null || images.size() == 0) {
                    ViewInject.toast(getString(R.string.noData));
                    return;
                }
                String filePath = images.get(0).path;
                ((CompanyOwnerContract.Presenter) mPresenter).postUpLoadImg(filePath, requestCode);
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

    //预览图片返回
    @SuppressWarnings("unchecked")
    private void previewImageReturn(int requestCode, Intent data) {
        images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_IMAGE_ITEMS);
        //预览图片返回
        if (data != null && images != null && requestCode == NumericConstants.REQUEST_CODE_PREVIEW && images.size() == 0) {
            isUploadPictureBusinessLicense = true;
            uploadPictureBusinessLicenseUrl = null;
            img_uploadPictureBusinessLicense.setImageDrawable(null);
        } else if (data != null && images != null && requestCode == NumericConstants.REQUEST_CODE_PREVIEW1 && images.size() == 0) {
            isUploadYourIdCard = true;
            uploadYourIdCardUrl = null;
            img_uploadYourIdCard.setImageDrawable(null);
        } else if (data != null && images != null && requestCode == NumericConstants.REQUEST_CODE_PREVIEW2 && images.size() == 0) {
            isUploadClearYourIdCard = true;
            uploadClearYourIdCardUrl = null;
            img_uploadClearYourIdCard.setImageDrawable(null);
        } else if (data != null && images != null && requestCode == NumericConstants.REQUEST_CODE_PREVIEW3 && images.size() == 0) {
            isUploudHoldingIdPhoto = true;
            uploudHoldingIdPhotoUrl = null;
            img_uploudHoldingIdPhoto.setImageDrawable(null);
        } else if (data != null && images != null && requestCode == NumericConstants.REQUEST_CODE_PREVIEW4 && images.size() == 0) {
            isUploadYourIdCardOperation = true;
            uploadYourIdCardOperationUrl = null;
            img_uploadYourIdCardOperation.setImageDrawable(null);
        } else if (data != null && images != null && requestCode == NumericConstants.REQUEST_CODE_PREVIEW5 && images.size() == 0) {
            isUploadClearYourIdCardOperation = true;
            uploadClearYourIdCardOperationUrl = null;
            img_uploadClearYourIdCardOperation.setImageDrawable(null);
        } else if (data != null && images != null && requestCode == NumericConstants.REQUEST_CODE_PREVIEW6 && images.size() == 0) {
            isUploudHoldingIdPhotoOperation = true;
            uploudHoldingIdPhotoOperationUrl = null;
            img_uploudHoldingIdPhotoOperation.setImageDrawable(null);
        }
    }

    //打开预览
    @SuppressWarnings("unchecked")
    private void toImagePreviewDelActivity(View view, ArrayList<ImageItem> list, int code) {
        Intent intentPreview = new Intent(aty, ImagePreviewDelActivity.class);
        intentPreview.putExtra(ImagePicker.EXTRA_IMAGE_ITEMS, list);
        intentPreview.putExtra(ImagePicker.EXTRA_SELECTED_IMAGE_POSITION, 0);
        intentPreview.putExtra(ImagePicker.EXTRA_FROM_ITEMS, true);
        startActivityForResult(intentPreview, code);
    }

}
