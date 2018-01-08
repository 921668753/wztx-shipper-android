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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
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
import com.ruitukeji.zwbh.entity.mine.shippercertification.IndividualOwnersBean;
import com.ruitukeji.zwbh.entity.UploadImageBean;
import com.ruitukeji.zwbh.mine.shippercertification.ShipperCertificationActivity;
import com.ruitukeji.zwbh.utils.JsonUtil;
import com.ruitukeji.zwbh.utils.rx.MsgEvent;
import com.ruitukeji.zwbh.utils.rx.RxBus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

import static com.ruitukeji.zwbh.constant.NumericConstants.REQUEST_CODE_CHOOSE_PHOTO;
import static com.ruitukeji.zwbh.constant.NumericConstants.REQUEST_CODE_PHOTO_PREVIEW;
import static com.ruitukeji.zwbh.constant.NumericConstants.REQUEST_CODE_PHOTO_PREVIEW1;

/**
 * 个人货主
 * Created by Administrator on 2017/12/14.
 */

public class IndividualOwnersFragment extends BaseFragment implements EasyPermissions.PermissionCallbacks, IndividualOwnersContract.View {

    private ShipperCertificationActivity aty;

    /**
     * 认证状态
     */
    @BindView(id = R.id.tv_unauthorized)
    private TextView tv_unauthorized;

    /**
     * 姓名
     */
    @BindView(id = R.id.et_userName)
    private EditText et_userName;

    /**
     * 男
     */
    @BindView(id = R.id.img_man, click = true)
    private ImageView img_man;
    private int sex = 1;
    /**
     * 女
     */
    @BindView(id = R.id.img_woman, click = true)
    private ImageView img_woman;

    /**
     * 身份证号
     */
    @BindView(id = R.id.et_IdNumber)
    private EditText et_IdNumber;

    /**
     * 手机号
     */
    @BindView(id = R.id.tv_userPhone)
    private TextView tv_userPhone;

    /**
     * 身份证有效期
     */
    @BindView(id = R.id.ll_validityIdentityCard, click = true)
    private LinearLayout ll_validityIdentityCard;
    @BindView(id = R.id.tv_validityIdentityCard)
    private TextView tv_validityIdentityCard;
    Calendar calendar = Calendar.getInstance();
    public long validityIdentityCard = 0;
    private TimePickerView pvTime;

    /**
     * 身份证正
     */
    @BindView(id = R.id.img_uploadYourIdCard, click = true)
    private ImageView img_uploadYourIdCard;
    String uploadYourIdCardUrl = "";
    private boolean isUploadYourIdCard = true;


    /**
     * 身份证反
     */
    @BindView(id = R.id.img_uploadClearYourIdCard, click = true)
    private ImageView img_uploadClearYourIdCard;
    String uploadClearYourIdCardUrl = "";
    private boolean isUploadClearYourIdCard = true;

    /**
     * 身份证手持
     */
    @BindView(id = R.id.img_uploudHoldingIdPhoto, click = true)
    private ImageView img_uploudHoldingIdPhoto;
    String uploudHoldingIdPhotoUrl = "";
    private boolean isUploudHoldingIdPhoto = true;

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
        return View.inflate(aty, R.layout.fragment_individualowners, null);
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter = new IndividualOwnersPresenter(this);
        images = new ArrayList<>();
        initImagePicker();
        asOfTheDatePicker();
        String auth_status = PreferenceHelper.readString(aty, StringConstants.FILENAME, "auth_status", "init");
        if (auth_status != null && auth_status.equals("init")) {
            return;
        }
        showLoadingDialog(getString(R.string.dataLoad));
        ((IndividualOwnersContract.Presenter) mPresenter).getIndividualOwners();
    }


    /**
     * 时间选择器----截止日期
     */
    @SuppressWarnings("deprecation")
    public void asOfTheDatePicker() {
        boolean[] type = new boolean[]{true, true, true, false, false, false};//显示类型 默认全部显示
        //控制时间范围
        Calendar calendarSet = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        startDate.set(calendarSet.get(Calendar.YEAR), calendarSet.get(Calendar.MONTH), calendarSet.get(Calendar.DAY_OF_MONTH));
        Calendar endDate = Calendar.getInstance();
        endDate.set(calendarSet.get(Calendar.YEAR) + 99, calendarSet.get(Calendar.MONTH), calendarSet.get(Calendar.DAY_OF_MONTH));
        pvTime = new TimePickerView.Builder(aty, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                if (date.getTime() <= System.currentTimeMillis()) {
                    ViewInject.toast(getString(R.string.aboveCurrentTime));
                    return;
                }
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                if (v.getId() == R.id.tv_validityIdentityCard) {
                    validityIdentityCard = date.getTime() / 1000;
                    calendar.setTime(date);
                }
                ((TextView) v).setText(format.format(date));
            }
        }).setType(type)
                .setLabel("", "", "", "", "", "") //设置空字符串以隐藏单位提示   hide label
                .setContentSize(20)
                .setRangDate(startDate, endDate)
                .build();
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
        String phone = PreferenceHelper.readString(aty, StringConstants.FILENAME, "phone");
        tv_userPhone.setText(phone);
    }

    @Override
    protected void widgetClick(View v) {
        super.widgetClick(v);
        switch (v.getId()) {
            case R.id.img_man:
                sex = 1;
                img_man.setImageResource(R.mipmap.ic_checkbox_select);
                img_woman.setImageResource(R.mipmap.ic_checkbox_unselect);
                break;
            case R.id.img_woman:
                sex = 2;
                img_man.setImageResource(R.mipmap.ic_checkbox_unselect);
                img_woman.setImageResource(R.mipmap.ic_checkbox_select);
                break;
            case R.id.ll_validityIdentityCard:
                pvTime.setDate(calendar);
                //弹出时间选择器
                pvTime.show(tv_validityIdentityCard);
                break;
            case R.id.img_uploadYourIdCard:
                if (isUploadYourIdCard) {
                    choicePhotoWrapper(REQUEST_CODE_CHOOSE_PHOTO);
                    break;
                }
                images.clear();
                ImageItem imageItem = new ImageItem();
                imageItem.path = uploadYourIdCardUrl;
                images.add(imageItem);
                //打开预览
                toImagePreviewDelActivity(img_uploadYourIdCard, images, NumericConstants.REQUEST_CODE_PREVIEW);
                break;
            case R.id.img_uploadClearYourIdCard:
                if (isUploadClearYourIdCard) {
                    choicePhotoWrapper(REQUEST_CODE_PHOTO_PREVIEW);
                    break;
                }
                images.clear();
                ImageItem imageItem1 = new ImageItem();
                imageItem1.path = uploadClearYourIdCardUrl;
                images.add(imageItem1);
                //打开预览
                toImagePreviewDelActivity(img_uploadClearYourIdCard, images, NumericConstants.REQUEST_CODE_PREVIEW1);
                break;
            case R.id.img_uploudHoldingIdPhoto:
                if (isUploudHoldingIdPhoto) {
                    choicePhotoWrapper(REQUEST_CODE_PHOTO_PREVIEW1);
                    break;
                }
                images.clear();
                ImageItem imageItem2 = new ImageItem();
                imageItem2.path = uploudHoldingIdPhotoUrl;
                images.add(imageItem2);
                //打开预览
                toImagePreviewDelActivity(img_uploudHoldingIdPhoto, images, NumericConstants.REQUEST_CODE_PREVIEW2);
                break;
            case R.id.tv_submit:
                ((IndividualOwnersContract.Presenter) mPresenter).postIndividualOwners(et_userName.getText().toString().trim(), sex,
                        et_IdNumber.getText().toString().trim(), tv_userPhone.getText().toString(), validityIdentityCard, uploadYourIdCardUrl, uploadClearYourIdCardUrl, uploudHoldingIdPhotoUrl);
                break;
        }
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
                ((IndividualOwnersContract.Presenter) mPresenter).postUpLoadImg(filePath, requestCode);
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
            isUploadYourIdCard = true;
            uploadYourIdCardUrl = null;
            img_uploadYourIdCard.setImageDrawable(null);
        } else if (data != null && images != null && requestCode == NumericConstants.REQUEST_CODE_PREVIEW1 && images.size() == 0) {
            isUploadClearYourIdCard = true;
            uploadClearYourIdCardUrl = null;
            img_uploadClearYourIdCard.setImageDrawable(null);
        } else if (data != null && images != null && requestCode == NumericConstants.REQUEST_CODE_PREVIEW2 && images.size() == 0) {
            isUploudHoldingIdPhoto = true;
            uploudHoldingIdPhotoUrl = null;
            img_uploudHoldingIdPhoto.setImageDrawable(null);
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

    @Override
    public void setPresenter(IndividualOwnersContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void getSuccess(String success, int flag) throws ParseException {
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
            IndividualOwnersBean individualOwnersBean = (IndividualOwnersBean) JsonUtil.getInstance().json2Obj(success, IndividualOwnersBean.class);
            et_userName.setText(individualOwnersBean.getResult().getReal_name());
            et_IdNumber.setText(individualOwnersBean.getResult().getIdentity());
            tv_userPhone.setText(individualOwnersBean.getResult().getPhone());
            sex = individualOwnersBean.getResult().getSex();
            if (sex == 1) {
                img_man.setImageResource(R.mipmap.ic_checkbox_select);
                img_woman.setImageResource(R.mipmap.ic_checkbox_unselect);
            } else if (sex == 2) {
                img_man.setImageResource(R.mipmap.ic_checkbox_unselect);
                img_woman.setImageResource(R.mipmap.ic_checkbox_select);
            } else {
                img_man.setImageResource(R.mipmap.ic_checkbox_select);
                img_woman.setImageResource(R.mipmap.ic_checkbox_unselect);
            }
            SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd");
            Long time=new Long(445555555);
            String d = format.format(time);
            Date date=format.parse(d);
            validityIdentityCard = date.getTime() / 1000;
            calendar.setTime(date);
            tv_validityIdentityCard.setText(d);
            isUploadYourIdCard = false;
            uploadYourIdCardUrl = individualOwnersBean.getResult().getFront_pic();
            GlideImageLoader.glideOrdinaryLoader(aty, uploadYourIdCardUrl + "?imageView2/1/w/161/h/103", img_uploadYourIdCard);
            isUploadClearYourIdCard = false;
            uploadClearYourIdCardUrl = individualOwnersBean.getResult().getBack_pic();
            GlideImageLoader.glideOrdinaryLoader(aty, uploadClearYourIdCardUrl + "?imageView2/1/w/161/h/103", img_uploadClearYourIdCard);
            uploudHoldingIdPhotoUrl = individualOwnersBean.getResult().getHold_pic();
            isUploudHoldingIdPhoto = false;
            GlideImageLoader.glideOrdinaryLoader(aty, uploudHoldingIdPhotoUrl + "?imageView2/1/w/161/h/103", img_uploudHoldingIdPhoto);
        } else if (flag == REQUEST_CODE_CHOOSE_PHOTO) {
            UploadImageBean uploadImageBean = (UploadImageBean) JsonUtil.getInstance().json2Obj(success, UploadImageBean.class);
            if (!(StringUtils.isEmpty(uploadImageBean.getResult().getFile().getUrl()))) {
                GlideImageLoader.glideOrdinaryLoader(aty, uploadImageBean.getResult().getFile().getUrl() + "?imageView2/1/w/161/h/103", img_uploadYourIdCard);
                uploadYourIdCardUrl = uploadImageBean.getResult().getFile().getUrl();
                isUploadYourIdCard = false;
            }
        } else if (flag == REQUEST_CODE_PHOTO_PREVIEW) {
            UploadImageBean uploadImageBean = (UploadImageBean) JsonUtil.getInstance().json2Obj(success, UploadImageBean.class);
            if (!(StringUtils.isEmpty(uploadImageBean.getResult().getFile().getUrl()))) {
                GlideImageLoader.glideOrdinaryLoader(aty, uploadImageBean.getResult().getFile().getUrl() + "?imageView2/1/w/161/h/103", img_uploadClearYourIdCard);
                uploadClearYourIdCardUrl = uploadImageBean.getResult().getFile().getUrl();
                isUploadClearYourIdCard = false;
            }
        } else if (flag == REQUEST_CODE_PHOTO_PREVIEW1) {
            UploadImageBean uploadImageBean = (UploadImageBean) JsonUtil.getInstance().json2Obj(success, UploadImageBean.class);
            if (!(StringUtils.isEmpty(uploadImageBean.getResult().getFile().getUrl()))) {
                GlideImageLoader.glideOrdinaryLoader(aty, uploadImageBean.getResult().getFile().getUrl() + "?imageView2/1/w/161/h/103", img_uploudHoldingIdPhoto);
                uploudHoldingIdPhotoUrl = uploadImageBean.getResult().getFile().getUrl();
                isUploudHoldingIdPhoto = false;
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
}
