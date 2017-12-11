package com.ruitukeji.zwbh.loginregister;

import android.Manifest;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.View.OnLayoutChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kymjs.common.StringUtils;
import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.application.MyApplication;
import com.ruitukeji.zwbh.common.BaseActivity;
import com.ruitukeji.zwbh.common.BindView;
import com.ruitukeji.zwbh.common.ViewInject;
import com.ruitukeji.zwbh.constant.NumericConstants;
import com.ruitukeji.zwbh.entity.CompanyAuthInfoBean;
import com.ruitukeji.zwbh.utils.ActivityTitleUtils;
import com.ruitukeji.zwbh.utils.JsonUtil;

import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;


/**
 * 企业认证----企业信息
 * Created by Administrator on 2017/2/17.
 */

public class EnterpriseInformationActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks, EnterpriseInformationContract.View, OnLayoutChangeListener {


    @BindView(id = R.id.ll_activityRootView)
    private LinearLayout ll_activityRootView;
    /**
     * 企业全称
     */
    @BindView(id = R.id.et_companyName)
    private EditText et_companyName;

    /**
     * 营业执照注册号
     */
    @BindView(id = R.id.et_registrationNumber)
    private EditText et_registrationNumber;

    /**
     * 企业法人姓名
     */
    @BindView(id = R.id.et_legalPersonName)
    private EditText et_legalPersonName;

    /**
     * 法人身份证号
     */
    @BindView(id = R.id.et_legalPersonIdNumber)
    private EditText et_legalPersonIdNumber;

    /**
     * 企业联系电话
     */
    @BindView(id = R.id.et_phoneNumber)
    private EditText et_phoneNumber;

    /**
     * 地址
     */
    @BindView(id = R.id.et_address)
    private EditText et_address;

    /**
     * 开户名称
     */
    @BindView(id = R.id.et_accountName)
    private EditText et_accountName;

    /**
     * 开户行
     */
    @BindView(id = R.id.et_openingBank)
    private EditText et_openingBank;

    /**
     * 结算账号
     */
    @BindView(id = R.id.et_settlementAccount)
    private EditText et_settlementAccount;


    /**
     * 提示
     */
    @BindView(id = R.id.tv_hint)
    private TextView tv_hint;

    /**
     * 下一步
     */
    @BindView(id = R.id.tv_nextType, click = true)
    private TextView tv_nextType;
    private String auth_status = null;
    private String hold_pic = null;
    private String front_pic = null;
    private String back_pic = null;
    private String sp_identity = null;
    private String sp_hold_pic = null;
    private String sp_front_pic = null;
    private String sp_back_pic = null;
    private String buss_pic = null;
    private int screenHeight;
    private int keyHeight;


    @Override
    public void setRootView() {
        setContentView(R.layout.activity_enterpriseinformation);
    }


    @Override
    public void initData() {
        super.initData();
        mPresenter = new EnterpriseInformationPresenter(this);
    }

    @Override
    public void initWidget() {
        super.initWidget();
        ActivityTitleUtils.initToolbar(aty, getString(R.string.enterpriseAuthentication), true, R.id.titlebar);
        auth_status = getIntent().getStringExtra("auth_status");
        if (!(StringUtils.isEmpty(auth_status) || auth_status.equals("init"))) {
            ((EnterpriseInformationContract.Presenter) mPresenter).getCompanyAuthInfo();
        }
        //获取屏幕高度
        screenHeight = this.getWindowManager().getDefaultDisplay().getHeight();
        //阀值设置为屏幕高度的1/3
        keyHeight = screenHeight / 3;
    }

    @Override
    public void widgetClick(View v) {
        super.widgetClick(v);
        switch (v.getId()) {
            case R.id.tv_nextType:
                showLoadingDialog(MyApplication.getContext().getString(R.string.submissionLoad));
                ((EnterpriseInformationContract.Presenter) mPresenter).postEnterpriseInformation(et_companyName.getText().toString().trim(), et_registrationNumber.getText().toString().trim(), et_legalPersonName.getText().toString().trim(), et_legalPersonIdNumber.getText().toString().trim(), et_phoneNumber.getText().toString().trim(), et_address.getText().toString().trim(), et_accountName.getText().toString().trim(), et_openingBank.getText().toString().trim(), et_settlementAccount.getText().toString().trim());
                break;
        }
    }

    @Override
    public void getSuccess(String s, int flag) {
        if (flag == 0) {
            choicePhotoWrapper();
        } else if (flag == 1) {
            CompanyAuthInfoBean companyAuthInfoBean = (CompanyAuthInfoBean) JsonUtil.getInstance().json2Obj(s, CompanyAuthInfoBean.class);
            et_companyName.setText(companyAuthInfoBean.getResult().getCom_name());
            et_registrationNumber.setText(companyAuthInfoBean.getResult().getCom_buss_num());
            et_legalPersonName.setText(companyAuthInfoBean.getResult().getLaw_person());
            et_legalPersonIdNumber.setText(companyAuthInfoBean.getResult().getLaw_identity());
            et_phoneNumber.setText(companyAuthInfoBean.getResult().getCom_phone());
            et_address.setText(companyAuthInfoBean.getResult().getAddress());
            et_accountName.setText(companyAuthInfoBean.getResult().getDeposit_name());
            et_openingBank.setText(companyAuthInfoBean.getResult().getBank());
            et_settlementAccount.setText(companyAuthInfoBean.getResult().getAccount());

            hold_pic = companyAuthInfoBean.getResult().getLaw_hold_pic();
            front_pic = companyAuthInfoBean.getResult().getLaw_front_pic();
            back_pic = companyAuthInfoBean.getResult().getLaw_back_pic();
            sp_identity = companyAuthInfoBean.getResult().getIdentity();
            sp_hold_pic = companyAuthInfoBean.getResult().getHold_pic();
            sp_front_pic = companyAuthInfoBean.getResult().getFront_pic();
            sp_back_pic = companyAuthInfoBean.getResult().getBack_pic();
            buss_pic = companyAuthInfoBean.getResult().getBuss_pic();
        }
        dismissLoadingDialog();
    }

    @Override
    public void error(String msg) {
        dismissLoadingDialog();
        ViewInject.toast(msg);
    }

    @Override
    public void setPresenter(EnterpriseInformationContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    protected void onResume() {
        super.onResume();
        //添加layout大小发生改变监听器
        ll_activityRootView.addOnLayoutChangeListener(this);
    }

    @Override
    public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
//现在认为只要控件将Activity向上推的高度超过了1/3屏幕高，就认为软键盘弹起
        if (oldBottom != 0 && bottom != 0 && (oldBottom - bottom >= keyHeight)) {
            tv_hint.setVisibility(View.VISIBLE);
            // Toast.makeText(MainActivity.this, "监听到软键盘弹起...", Toast.LENGTH_SHORT).show();

        } else if (oldBottom != 0 && bottom != 0 && (bottom - oldBottom < keyHeight)) {
            tv_hint.setVisibility(View.GONE);
            //   Toast.makeText(MainActivity.this, "监听到软件盘关闭...", Toast.LENGTH_SHORT).show();

        }
    }

    @AfterPermissionGranted(NumericConstants.REQUEST_CODE_PERMISSION_PHOTO_PICKER)
    private void choicePhotoWrapper() {
        String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
        if (EasyPermissions.hasPermissions(this, perms)) {
            // 拍照后照片的存放目录，改成你自己拍照后要存放照片的目录。如果不传递该参数的话就没有拍照功能
            Intent intent = new Intent(aty, EnterpriseQualificationCertificateActivity.class);
            intent.putExtra("com_name", et_companyName.getText().toString().trim());
            intent.putExtra("com_buss_num", et_registrationNumber.getText().toString().trim());
            intent.putExtra("law_person", et_legalPersonName.getText().toString().trim());
            intent.putExtra("identity", et_legalPersonIdNumber.getText().toString().trim());
            intent.putExtra("phone", et_phoneNumber.getText().toString().trim());
            intent.putExtra("address", et_address.getText().toString().trim());
            intent.putExtra("deposit_name", et_accountName.getText().toString().trim());
            intent.putExtra("bank", et_openingBank.getText().toString().trim());
            intent.putExtra("account", et_settlementAccount.getText().toString().trim());
            intent.putExtra("auth_status", auth_status);
            intent.putExtra("hold_pic", hold_pic);
            intent.putExtra("front_pic", front_pic);
            intent.putExtra("back_pic", back_pic);
            intent.putExtra("sp_identity", sp_identity);
            intent.putExtra("sp_hold_pic", sp_hold_pic);
            intent.putExtra("sp_front_pic", sp_front_pic);
            intent.putExtra("sp_back_pic", sp_back_pic);
            intent.putExtra("buss_pic", buss_pic);
            showActivity(aty, intent);
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
}
