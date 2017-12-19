package com.ruitukeji.zwbh.mine.setting;

import android.Manifest;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kymjs.common.PreferenceHelper;
import com.kymjs.common.StringUtils;
import com.kymjs.common.SystemTool;
import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.application.MyApplication;
import com.ruitukeji.zwbh.common.BaseActivity;
import com.ruitukeji.zwbh.common.BindView;
import com.ruitukeji.zwbh.common.ViewInject;
import com.ruitukeji.zwbh.constant.NumericConstants;
import com.ruitukeji.zwbh.constant.StringConstants;
import com.ruitukeji.zwbh.entity.BaseResult;
import com.ruitukeji.zwbh.loginregister.LoginActivity;
import com.ruitukeji.zwbh.mine.setting.aboutus.AboutUsActivity;
import com.ruitukeji.zwbh.mine.setting.changepassword.ChangePasswordActivity;
import com.ruitukeji.zwbh.mine.setting.userfeedback.UserFeedbackActivity;
import com.ruitukeji.zwbh.utils.ActivityTitleUtils;
import com.ruitukeji.zwbh.utils.DataCleanManager;
import com.ruitukeji.zwbh.utils.FileNewUtil;
import com.ruitukeji.zwbh.utils.JsonUtil;

import java.io.File;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * 设置
 * Created by Administrator on 2017/2/10.
 */

public class SettingsActivity extends BaseActivity implements SettingsContract.View, EasyPermissions.PermissionCallbacks {


    @BindView(id = R.id.ll_changePassword, click = true)
    private LinearLayout changePassword;


    @BindView(id = R.id.tv_showing, click = true)
    private TextView tv_showing;

    @BindView(id = R.id.tv_hides, click = true)
    private TextView tv_hides;

    @BindView(id = R.id.ll_systemVersion, click = true)
    private LinearLayout systemVersion;

    @BindView(id = R.id.tv_version)
    private TextView version;

    @BindView(id = R.id.ll_clearCache, click = true)
    private LinearLayout clearCache;

    @BindView(id = R.id.tv_cache)
    private TextView cache;

    /**
     * 用户反馈
     */
    @BindView(id = R.id.ll_userFeedback, click = true)
    private LinearLayout ll_userFeedback;

    /**
     * 关于我们
     */
    @BindView(id = R.id.ll_aboutUs, click = true)
    private LinearLayout ll_aboutUs;

    /**
     * 退出登陆
     */
    @BindView(id = R.id.tv_logOut, click = true)
    private TextView tv_logOut;

    private String updateAppUrl = null;
    private boolean isUpdateApp = false;
    private SweetAlertDialog sweetAlertDialog = null;
    private boolean isGoneBanner = false;


    @Override
    public void setRootView() {
        setContentView(R.layout.activity_settings);
    }

    @Override
    public void initData() {
        super.initData();
        sweetAlertDialog = new SweetAlertDialog(this, SweetAlertDialog.CUSTOM_IMAGE_TYPE);
        mPresenter = new SettingsPresenter(this);
    }

    @Override
    public void initWidget() {
        super.initWidget();
        ActivityTitleUtils.initToolbar(aty, getString(R.string.settings), true, R.id.titlebar);
        initDialog();
        isUpdateApp = PreferenceHelper.readBoolean(MyApplication.getContext(), StringConstants.FILENAME, "isUpdate", false);
        if (isUpdateApp) {
            version.setText(getString(R.string.newVersion));
        } else {
            version.setText(SystemTool.getAppVersionName(this));
        }
        isGoneBanner = PreferenceHelper.readBoolean(aty, StringConstants.FILENAME, "isGoneBanner", false);
        if (isGoneBanner) {
            tv_showing.setTextColor(getResources().getColor(R.color.lonincolors));
            tv_showing.setBackgroundResource(R.drawable.shape_gotowork);
            tv_hides.setTextColor(getResources().getColor(R.color.mainColor));
            tv_hides.setBackgroundResource(R.drawable.shape_afterwork1);
        } else {
            tv_showing.setTextColor(getResources().getColor(R.color.mainColor));
            tv_showing.setBackgroundResource(R.drawable.shape_gotowork1);
            tv_hides.setTextColor(getResources().getColor(R.color.lonincolors));
            tv_hides.setBackgroundResource(R.drawable.shape_afterwork);
        }
        queryCache();
    }

    /**
     * 弹框设置
     */
    private void initDialog() {
        sweetAlertDialog.setCancelable(false);
        sweetAlertDialog.setTitleText(getString(R.string.updateVersion))
                .setCancelText(getString(R.string.cancel1))
                .setConfirmText(getString(R.string.update))
                .showCancelButton(true)
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                    }
                });
    }

    /**
     * 监听事件
     *
     * @param v
     */
    @Override
    public void widgetClick(View v) {
        super.widgetClick(v);
        switch (v.getId()) {
            case R.id.ll_changePassword:
                showActivity(aty, ChangePasswordActivity.class);
                break;
            case R.id.tv_showing:
                if (isGoneBanner) {
                    ((SettingsContract.Presenter) mPresenter).postChangeAd("0");
                }
                break;
            case R.id.tv_hides:
                if (!isGoneBanner) {
                    ((SettingsContract.Presenter) mPresenter).postChangeAd("1");
                }
                break;
            case R.id.ll_systemVersion:
                if (isUpdateApp) {
                    updateAppUrl = PreferenceHelper.readString(MyApplication.getContext(), StringConstants.FILENAME, "updateAppUrl", null);
                    if (StringUtils.isEmpty(updateAppUrl)) {
                        return;
                    }
                    sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismiss();
                            readAndWriteTask(updateAppUrl);
                        }
                    }).show();
                    break;
                }
                ViewInject.toast(getString(R.string.latestVersion));
                break;
            case R.id.ll_clearCache:
                DataCleanManager.clearAllCache(aty);
                cache.setText("0KB");
                ViewInject.toast("清除成功");
                break;
            case R.id.ll_userFeedback:
                showActivity(aty, UserFeedbackActivity.class);
                break;
            case R.id.ll_aboutUs:
                Intent aboutUs = new Intent(aty, AboutUsActivity.class);
                aboutUs.putExtra("type", "shipper_about");
                showActivity(aty, aboutUs);
                break;
            case R.id.tv_logOut:
                // PreferenceHelper.clean(this, StringConstants.FILENAME);
             //   PreferenceHelper.write(aty, StringConstants.FILENAME, "isGoneBanner", false);
                PreferenceHelper.write(aty, StringConstants.FILENAME, "userId", 0);
                PreferenceHelper.write(aty, StringConstants.FILENAME, "accessToken", "");
                PreferenceHelper.write(aty, StringConstants.FILENAME, "refreshToken", "");
                PreferenceHelper.write(aty, StringConstants.FILENAME, "expireTime", "0");
                PreferenceHelper.write(aty, StringConstants.FILENAME, "timeBefore", "0");
              //  drawer.closeDrawers();
             //   PreferenceHelper.write(aty, StringConstants.FILENAME, "isAvatar", true);
                skipActivity(aty, LoginActivity.class);
                break;
        }
    }

    /**
     * 查询缓存
     */
    public void queryCache() {
        try {
            cache.setText(DataCleanManager.getTotalCacheSize(aty));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void setPresenter(SettingsContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void getSuccess(String s, int flag) {
        if (flag == 0) {
            BaseResult baseResult = (BaseResult) JsonUtil.getInstance().json2Obj(s, BaseResult.class);
            if ((String) baseResult.getResult() == null) {
                return;
            }
            File path = new File((String) baseResult.getResult());
            FileNewUtil.installApkFile(this, path.getAbsolutePath());
        } else if (flag == 1) {
            tv_showing.setTextColor(getResources().getColor(R.color.mainColor));
            tv_showing.setBackgroundResource(R.drawable.shape_gotowork1);
            tv_hides.setTextColor(getResources().getColor(R.color.lonincolors));
            tv_hides.setBackgroundResource(R.drawable.shape_afterwork);
            PreferenceHelper.write(aty, StringConstants.FILENAME, "isGoneBanner", false);
        } else if (flag == 2) {
            tv_showing.setTextColor(getResources().getColor(R.color.lonincolors));
            tv_showing.setBackgroundResource(R.drawable.shape_gotowork);
            tv_hides.setTextColor(getResources().getColor(R.color.mainColor));
            tv_hides.setBackgroundResource(R.drawable.shape_afterwork1);
            PreferenceHelper.write(aty, StringConstants.FILENAME, "isGoneBanner", true);
        }
        dismissLoadingDialog();
    }

    @Override
    public void error(String msg) {
        if (msg != null && msg.equals("" + NumericConstants.TOLINGIN)) {
            dismissLoadingDialog();
            showActivity(aty, LoginActivity.class);
            return;
        }
        isUpdateApp = false;
        dismissLoadingDialog();
        ViewInject.toast(msg);
    }

    @AfterPermissionGranted(NumericConstants.READ_AND_WRITE_CODE)
    public void readAndWriteTask(String updateAppUrl) {
        String[] perms = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(this, perms)) {
            // Have permission, do the thing!
            ((SettingsContract.Presenter) mPresenter).downloadApp(updateAppUrl);
        } else {
            // Ask for one permission
            EasyPermissions.requestPermissions(this, getString(R.string.readAndWrite),
                    NumericConstants.READ_AND_WRITE_CODE, perms);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // EasyPermissions handles the request result.
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        //  Log.d(TAG, "onPermissionsGranted:" + requestCode + ":" + perms.size());
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        if (requestCode == NumericConstants.READ_AND_WRITE_CODE) {
            ViewInject.toast(getString(R.string.sdPermission));
        }
    }

}
