package com.ruitukeji.zwbh.mine.myorder.orderfragment;

import android.Manifest;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alipay.sdk.app.EnvUtils;
import com.kymjs.common.PreferenceHelper;
import com.kymjs.common.StringUtils;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.ui.ImagePreviewDelActivity;
import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.application.MyApplication;
import com.ruitukeji.zwbh.common.BaseActivity;
import com.ruitukeji.zwbh.common.BindView;
import com.ruitukeji.zwbh.common.GlideImageLoader;
import com.ruitukeji.zwbh.common.KJActivityStack;
import com.ruitukeji.zwbh.common.ViewInject;
import com.ruitukeji.zwbh.constant.NumericConstants;
import com.ruitukeji.zwbh.constant.StringConstants;
import com.ruitukeji.zwbh.entity.mine.mywallet.recharge.AlipayBean;
import com.ruitukeji.zwbh.entity.startpage.AppConfigBean;
import com.ruitukeji.zwbh.entity.mine.mywallet.MyWalletBean;
import com.ruitukeji.zwbh.entity.UploadImageBean;
import com.ruitukeji.zwbh.entity.mine.mywallet.recharge.WxPayBean;
import com.ruitukeji.zwbh.loginregister.LoginActivity;
import com.ruitukeji.zwbh.mine.personaldata.PaySuccessActivity;
import com.ruitukeji.zwbh.utils.ActivityTitleUtils;
import com.ruitukeji.zwbh.utils.JsonUtil;
import com.ruitukeji.zwbh.utils.PayUtils;

import java.util.ArrayList;
import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * 付款
 * Created by Administrator on 2017/2/24.
 */

public class PaymentActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks, PaymentContract.View {

    /**
     * 选择支付方式
     */
    @BindView(id = R.id.tv_choicePayment)
    private TextView tv_choicePayment;

    /**
     * 余额支付
     */
    @BindView(id = R.id.ll_balancePayment)
    private LinearLayout ll_balancePayment;

    @BindView(id = R.id.tv_balancePayment)
    private TextView tv_balancePayment;

    @BindView(id = R.id.img_balancePayment)
    private ImageView img_balancePayment;


    @BindView(id = R.id.img_balancePayment1, click = true)
    private ImageView img_balancePayment1;


    /**
     * 微信支付
     */
    @BindView(id = R.id.ll_weChatPay)
    private LinearLayout ll_weChatPay;

    @BindView(id = R.id.img_weChatPay, click = true)
    private ImageView img_weChatPay;

    /**
     * 支付宝支付
     */
    @BindView(id = R.id.ll_alipayPay)
    private LinearLayout ll_alipayPay;
    @BindView(id = R.id.img_alipayPay, click = true)
    private ImageView img_alipayPay;


    /**
     * 上传付款凭证
     */
    @BindView(id = R.id.ll_broughtAccount)
    private LinearLayout ll_broughtAccount;

    @BindView(id = R.id.tv_broughtAccount)
    private TextView tv_broughtAccount;

    @BindView(id = R.id.img_proofPayment, click = true)
    private ImageView img_proofPayment;

    /**
     * 确定
     */
    @BindView(id = R.id.tv_determine, click = true)
    private TextView tv_determine;

    /**
     * 选择支付方式  1 余额   2  微信  3  支付宝  4   上传凭证
     */
    private int methodPayment = 0;
    private ImagePicker imagePicker;
    private int order_id = 0;
    private String total_amount = "";
    private String imgUrlPayment = null;
    private boolean isUploadPayment = false;
    private PayUtils payUtils = null;

    @Override
    public void setRootView() {
        setContentView(R.layout.activity_payment);
    }

    @Override
    public void initData() {
        super.initData();
        mPresenter = new PaymentPresenter(this);
        payUtils = new PayUtils(this, PaySuccessActivity.class);
        order_id = getIntent().getIntExtra("order_id", 0);
        total_amount = getIntent().getStringExtra("total_amount");
    }

    @Override
    public void initWidget() {
        super.initWidget();
        ActivityTitleUtils.initToolbar(aty, getString(R.string.payment), true, R.id.titlebar);
        showLoadingDialog(MyApplication.getContext().getString(R.string.dataLoad));
        ((PaymentContract.Presenter) mPresenter).getMyWallet();
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
        imagePicker.setCrop(false);
        imagePicker.setSaveRectangle(false);
    }

    @Override
    public void widgetClick(View v) {
        super.widgetClick(v);

        switch (v.getId()) {
            case R.id.img_balancePayment1:
                img_balancePayment1.setImageResource(R.mipmap.selected);
                img_weChatPay.setImageResource(R.mipmap.selected1);
                img_alipayPay.setImageResource(R.mipmap.selected1);
                methodPayment = 1;
                isUploadPayment = false;
                imgUrlPayment = null;
                img_proofPayment.setImageResource(R.mipmap.paypingzheng);
                break;
            case R.id.img_weChatPay:
                img_balancePayment1.setImageResource(R.mipmap.selected1);
                img_weChatPay.setImageResource(R.mipmap.selected);
                img_alipayPay.setImageResource(R.mipmap.selected1);
                methodPayment = 2;
                isUploadPayment = false;
                imgUrlPayment = null;
                img_proofPayment.setImageResource(R.mipmap.paypingzheng);
                break;
            case R.id.img_alipayPay:
                img_balancePayment1.setImageResource(R.mipmap.selected1);
                img_weChatPay.setImageResource(R.mipmap.selected1);
                img_alipayPay.setImageResource(R.mipmap.selected);
                methodPayment = 3;
                isUploadPayment = false;
                imgUrlPayment = null;
                img_proofPayment.setImageResource(R.mipmap.paypingzheng);
                break;
            case R.id.img_proofPayment:
                img_balancePayment1.setImageResource(R.mipmap.selected1);
                img_weChatPay.setImageResource(R.mipmap.selected1);
                img_alipayPay.setImageResource(R.mipmap.selected1);
                methodPayment = 4;
                if (isUploadPayment) {
                    images.clear();
                    ImageItem imageItem = new ImageItem();
                    imageItem.path = imgUrlPayment;
                    images.add(imageItem);
                    //打开预览
                    toImagePreviewDelActivity(img_proofPayment, images, NumericConstants.READ_AND_WRITE_CODE);
                } else {
                    choicePhotoWrapper();
                }
                break;
            case R.id.tv_determine:
                showLoadingDialog(getString(R.string.submissionLoad));
                if (methodPayment == 1) {
                    ((PaymentContract.Presenter) mPresenter).postScorePay(order_id);
                } else if (methodPayment == 2) {
                    ((PaymentContract.Presenter) mPresenter).getWxPay(order_id, total_amount);
                } else if (methodPayment == 3) {
                    ((PaymentContract.Presenter) mPresenter).getAlipay(order_id, total_amount);
                } else if (methodPayment == 4) {
                    ((PaymentContract.Presenter) mPresenter).uploadCerPic(order_id, imgUrlPayment);
                }
                break;
        }
    }

    @Override
    public void setPresenter(PaymentContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void getSuccess(String s, int flag) {
        if (flag == 0) {
            String fragment = PreferenceHelper.readString(aty, StringConstants.FILENAME, "refreshOrderFragment", "AllOrderFragment");
            if (fragment.equals("AllOrderFragment")) {
                PreferenceHelper.write(aty, StringConstants.FILENAME, "isRefreshCompleted1", true);
                PreferenceHelper.write(aty, StringConstants.FILENAME, "isRefreshOrder", true);
            } else if (fragment.equals("CompletedFragment")) {
                PreferenceHelper.write(aty, StringConstants.FILENAME, "isRefreshCompleted", true);
                PreferenceHelper.write(aty, StringConstants.FILENAME, "isRefreshOrder1", true);
            }
            //余额
            PreferenceHelper.write(aty, StringConstants.FILENAME, "payClass", aty.getClass().getName());
            PreferenceHelper.write(aty, StringConstants.FILENAME, "isRefreshOrderDetailsActivity", true);
            KJActivityStack.create().finishActivity(CheckVoucherActivity.class);
            showActivity(aty, PaySuccessActivity.class);
            finish();
        } else if (flag == 1) {
            //微信
            WxPayBean wxPayBean = (WxPayBean) JsonUtil.getInstance().json2Obj(s, WxPayBean.class);
            if (payUtils == null) {
                payUtils = new PayUtils(this, PaySuccessActivity.class);
            }
            payUtils.doPayment(wxPayBean.getResult().getAppid(), wxPayBean.getResult().getPartnerid(), wxPayBean.getResult().getPrepayid(), wxPayBean.getResult().getPackageX(), wxPayBean.getResult().getNoncestr(), wxPayBean.getResult().getTimestamp(), wxPayBean.getResult().getSign());
        } else if (flag == 2) {
            //支付宝
            AlipayBean alipayBean = (AlipayBean) JsonUtil.getInstance().json2Obj(s, AlipayBean.class);
            if (payUtils == null) {
                payUtils = new PayUtils(this, PaySuccessActivity.class);
            }
            if (alipayBean.getResult().getIsUseSandbox().equals("1")) {
                EnvUtils.setEnv(EnvUtils.EnvEnum.SANDBOX);
            }
            payUtils.doPay(alipayBean.getResult().getOrderString());
        } else if (flag == 3) {
            //凭证
            String fragment = PreferenceHelper.readString(aty, StringConstants.FILENAME, "refreshOrderFragment", "AllOrderFragment");
            if (fragment.equals("AllOrderFragment")) {
                PreferenceHelper.write(aty, StringConstants.FILENAME, "isRefreshCompleted1", true);
                PreferenceHelper.write(aty, StringConstants.FILENAME, "isRefreshOrder", true);
            } else if (fragment.equals("CompletedFragment")) {
                PreferenceHelper.write(aty, StringConstants.FILENAME, "isRefreshCompleted", true);
                PreferenceHelper.write(aty, StringConstants.FILENAME, "isRefreshOrder1", true);
            }
            ViewInject.toast(getString(R.string.submittedSuccessfully));
            PreferenceHelper.write(aty, StringConstants.FILENAME, "isRefreshOrderDetailsActivity", true);
            KJActivityStack.create().finishActivity(CheckVoucherActivity.class);
            finish();
        } else if (flag == 4) {
            //上传图片
            UploadImageBean uploadImageBean = (UploadImageBean) JsonUtil.getInstance().json2Obj(s, UploadImageBean.class);
            if (!(StringUtils.isEmpty(uploadImageBean.getResult().getFile().getUrl()))) {
                isUploadPayment = true;
                imgUrlPayment = uploadImageBean.getResult().getFile().getUrl();
                GlideImageLoader.glideLoader(this, imgUrlPayment + "?imageView2/1/w/300/h/150", img_proofPayment, 1);
            }

        } else if (flag == 5) {
            MyWalletBean myWalletBean = (MyWalletBean) JsonUtil.getInstance().json2Obj(s, MyWalletBean.class);
            String balance = myWalletBean.getResult().getBalance();
            String weixin_limit = PreferenceHelper.readString(this, StringConstants.FILENAME, "weixin_limit");
            String alipay_limit = PreferenceHelper.readString(this, StringConstants.FILENAME, "alipay_limit");
            String tran_account = PreferenceHelper.readString(this, StringConstants.FILENAME, "tran_account");
            tv_choicePayment.setText(getString(R.string.choicePayment));
            if (StringUtils.toDouble(total_amount) > StringUtils.toDouble(balance)) {
                tv_balancePayment.setText("余额不足");
                img_balancePayment1.setVisibility(View.GONE);
                //   if (StringUtils.toDouble(total_amount) <= StringUtils.toDouble(weixin_limit)) {
                // 微信
                ll_weChatPay.setVisibility(View.VISIBLE);
                img_weChatPay.setImageResource(R.mipmap.selected);
                img_alipayPay.setImageResource(R.mipmap.selected1);
                methodPayment = 2;
//                } else {
//                    if (StringUtils.toDouble(total_amount) <= StringUtils.toDouble(alipay_limit)) {
//                        // 微信
//                        ll_alipayPay.setVisibility(View.VISIBLE);
//                        img_alipayPay.setImageResource(R.mipmap.selected);
//                        methodPayment = 3;
//                    }
//                }
            } else {
                tv_balancePayment.setText(getString(R.string.balancePayment));
                img_balancePayment1.setVisibility(View.VISIBLE);
                img_balancePayment1.setImageResource(R.mipmap.selected);
                img_weChatPay.setImageResource(R.mipmap.selected1);
                img_alipayPay.setImageResource(R.mipmap.selected1);
                methodPayment = 1;
            }
//            if (StringUtils.toDouble(total_amount) > StringUtils.toDouble(weixin_limit)) {
//                // 微信
//                ll_weChatPay.setVisibility(View.GONE);
//            }
//            if (StringUtils.toDouble(total_amount) > StringUtils.toDouble(alipay_limit)) {
//                //支付宝
//                ll_alipayPay.setVisibility(View.GONE);
//            }
//            if (StringUtils.toDouble(total_amount) > StringUtils.toDouble(alipay_limit) && StringUtils.toDouble(total_amount) > StringUtils.toDouble(weixin_limit)) {
//                //大额付款
//                ll_broughtAccount.setVisibility(View.VISIBLE);
//                tv_choicePayment.setText(getString(R.string.uploadProofPayment));
//                ll_balancePayment.setVisibility(View.GONE);
//                ll_weChatPay.setVisibility(View.GONE);
//                ll_alipayPay.setVisibility(View.GONE);
            if (StringUtils.isEmpty(tran_account) || tran_account.length() == 0) {
                ((PaymentContract.Presenter) mPresenter).getAppConfig();
                return;
            } else {
                tv_broughtAccount.setText(getString(R.string.broughtAccount) + tran_account);
            }


//                methodPayment = 4;
//            } else {
//                ll_broughtAccount.setVisibility(View.GONE);
//            }
        } else if (flag == 6) {
            AppConfigBean appConfigBean = (AppConfigBean) JsonUtil.getInstance().json2Obj(s, AppConfigBean.class);
            PreferenceHelper.write(this, StringConstants.FILENAME, "lastApkUrl", appConfigBean.getResult().getLastApkUrl());
            PreferenceHelper.write(this, StringConstants.FILENAME, "lastApkVersion", appConfigBean.getResult().getLastApkVersion());
            PreferenceHelper.write(this, StringConstants.FILENAME, "lastApkVersionNum", appConfigBean.getResult().getLastApkVersionNum());
            PreferenceHelper.write(this, StringConstants.FILENAME, "defaultAvatar", appConfigBean.getResult().getDefaultAvatar());
            PreferenceHelper.write(this, StringConstants.FILENAME, "share_percent", appConfigBean.getResult().getShare_percent());
            PreferenceHelper.write(this, StringConstants.FILENAME, "grab_range", appConfigBean.getResult().getGrab_range());
            PreferenceHelper.write(this, StringConstants.FILENAME, "premium_rate", appConfigBean.getResult().getPremium_rate());
            PreferenceHelper.write(this, StringConstants.FILENAME, "bond_person_amount", appConfigBean.getResult().getBond_person_amount());
            PreferenceHelper.write(this, StringConstants.FILENAME, "bond_company_amount", appConfigBean.getResult().getBond_company_amount());
            PreferenceHelper.write(this, StringConstants.FILENAME, "withdraw_begintime", appConfigBean.getResult().getWithdraw_begintime());
            PreferenceHelper.write(this, StringConstants.FILENAME, "withdraw_endtime", appConfigBean.getResult().getWithdraw_endtime());
            PreferenceHelper.write(this, StringConstants.FILENAME, "custom_phone", appConfigBean.getResult().getCustom_phone());
            PreferenceHelper.write(this, StringConstants.FILENAME, "custom_email", appConfigBean.getResult().getCustom_email());
            PreferenceHelper.write(this, StringConstants.FILENAME, "complain_phone", appConfigBean.getResult().getComplain_phone());
            PreferenceHelper.write(this, StringConstants.FILENAME, "weixin_limit", appConfigBean.getResult().getWeixin_limit());
            PreferenceHelper.write(this, StringConstants.FILENAME, "alipay_limit", appConfigBean.getResult().getAlipay_limit());
            PreferenceHelper.write(this, StringConstants.FILENAME, "tran_account", appConfigBean.getResult().getTran_account());
            PreferenceHelper.write(this, StringConstants.FILENAME, "share_shipper", appConfigBean.getResult().getShare_shipper());
            PreferenceHelper.write(this, StringConstants.FILENAME, "share_shipper_description", appConfigBean.getResult().getShare_shipper_description());
            PreferenceHelper.write(this, StringConstants.FILENAME, "share_shipper_title", appConfigBean.getResult().getShare_shipper_title());
            tv_broughtAccount.setText(getString(R.string.broughtAccount) + appConfigBean.getResult().getTran_account());
        }
        dismissLoadingDialog();
    }

    @Override
    public void error(String msg, int flag) {
        if (msg.equals("" + NumericConstants.TOLINGIN)) {
            dismissLoadingDialog();
            showActivity(aty, LoginActivity.class);
            return;
        }
        if (flag == 0) {
            dismissLoadingDialog();
            ViewInject.toast(msg);
        } else if (flag == 5) {
            ((PaymentContract.Presenter) mPresenter).getMyWallet();
        } else if (flag == 6) {
            ((PaymentContract.Presenter) mPresenter).getAppConfig();
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
            EasyPermissions.requestPermissions(this, "图片选择需要以下权限:\n\n1.访问设备上的照片\n\n2.拍照", NumericConstants.REQUEST_CODE_PERMISSION_PHOTO_PICKER, perms);
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
            ViewInject.toast("您拒绝了「图片选择」所需要的相关权限!");
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
            ((PaymentContract.Presenter) mPresenter).upLoadImg(images.get(0).path);
        }
    }

    //预览图片返回
    @SuppressWarnings("unchecked")
    private void previewImageReturn(int requestCode, Intent data) {
        images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_IMAGE_ITEMS);
        //预览图片返回
        if (data != null && images != null && requestCode == NumericConstants.READ_AND_WRITE_CODE && images.size() == 0) {
            isUploadPayment = false;
            imgUrlPayment = null;
            GlideImageLoader.glideLoader(this, R.mipmap.paypingzheng, img_proofPayment, 1);
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
        payUtils = null;
    }
}