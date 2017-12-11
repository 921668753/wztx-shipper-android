package com.ruitukeji.zwbh.loginregister;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.kymjs.common.PreferenceHelper;
import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.application.MyApplication;
import com.ruitukeji.zwbh.common.BaseActivity;
import com.ruitukeji.zwbh.common.BindView;
import com.ruitukeji.zwbh.common.ViewInject;
import com.ruitukeji.zwbh.constant.StringConstants;
import com.ruitukeji.zwbh.entity.CodeBean;
import com.ruitukeji.zwbh.entity.IsAdBean;
import com.ruitukeji.zwbh.entity.LoginBean;
import com.ruitukeji.zwbh.entity.UserInfoBean;
import com.ruitukeji.zwbh.main.MainActivity;
import com.ruitukeji.zwbh.utils.ActivityTitleUtils;
import com.ruitukeji.zwbh.utils.JsonUtil;
import com.umeng.analytics.MobclickAgent;

import cn.bingoogolapple.titlebar.BGATitleBar;

/**
 * 登录
 * Created by ruitu ck on 2016/9/14.
 */

public class LoginActivity extends BaseActivity implements LoginContract.View {
    private LoginContract.Presenter mPresenter;
    /**
     * 标题
     */
    @BindView(id = R.id.titlebar)
    private BGATitleBar titleba;
    /**
     * 账号
     */
    @BindView(id = R.id.et_accountNumber)
    private EditText et_accountNumber;
    /**
     * 密码
     */
    @BindView(id = R.id.et_pwd)
    private EditText et_pwd;
    /**
     * 忘记密码
     */
    @BindView(id = R.id.tv_forgotPassword, click = true)
    private TextView tv_forgotPassword;
    /**
     * 登录
     */
    @BindView(id = R.id.tv_login, click = true)
    private TextView tv_login;
    /**
     * 注册
     */
    @BindView(id = R.id.tv_register, click = true)
    private TextView tv_register;
    /**
     * 取消
     */
    @BindView(id = R.id.img_quxiao, click = true)
    private ImageView img_quxiao;
    @BindView(id = R.id.img_quxiao1, click = true)
    private ImageView img_quxiao1;

    @Override
    public void setRootView() {
        setContentView(R.layout.activity_login);
    }

    /**
     * 初始化数据
     */
    @Override
    public void initData() {
        super.initData();
        mPresenter = new LoginPresenter(this);
    }

    /**
     * 渲染view
     */
    @Override
    public void initWidget() {
        super.initWidget();
        initTitle();
        //   tv_login.setFocusable(false);
        tv_login.setClickable(false);//不可点击
        changeInputView(et_accountNumber, img_quxiao);
        changeInputView(et_pwd, img_quxiao1);
    }

    /**
     * 设置标题
     */
    public void initTitle() {
        ActivityTitleUtils.initToolbar(aty, getString(R.string.login), true, R.id.titlebar);
    }

    /**
     * view监听事件
     *
     * @param v
     */
    @Override
    public void widgetClick(View v) {
        super.widgetClick(v);

        switch (v.getId()) {
            case R.id.tv_forgotPassword:
                Intent intent = new Intent();
                intent.setClass(aty, RetrievePasswordActivity.class);
                intent.putExtra("title", getString(R.string.retrievePassword));
                showActivity(aty, intent);
                break;
            case R.id.tv_login:
                showLoadingDialog(MyApplication.getContext().getString(R.string.loggingLoad));
                mPresenter.postToLogin(et_accountNumber.getText().toString(), et_pwd.getText().toString());
                break;
            case R.id.img_quxiao:
                et_accountNumber.setText("");
                break;
            case R.id.img_quxiao1:
                et_pwd.setText("");
                break;
            case R.id.tv_register:
                showActivity(aty, SelectRegisterTypeActivity.class);
                break;
            default:
                break;
        }
    }

    @Override
    public void getSuccess(String s, int flag) {
        if (flag == 0) {
            LoginBean bean = (LoginBean) JsonUtil.getInstance().json2Obj(s, LoginBean.class);
            MobclickAgent.onProfileSignIn(et_accountNumber.getText().toString());
            PreferenceHelper.write(aty, StringConstants.FILENAME, "accessToken", bean.getResult().getAccessToken());
            PreferenceHelper.write(aty, StringConstants.FILENAME, "expireTime", bean.getResult().getExpireTime() + "");
            PreferenceHelper.write(aty, StringConstants.FILENAME, "refreshToken", bean.getResult().getRefreshToken());
            PreferenceHelper.write(aty, StringConstants.FILENAME, "timeBefore", System.currentTimeMillis() + "");
            PreferenceHelper.write(aty, StringConstants.FILENAME, "id", bean.getResult().getUserId());
            PreferenceHelper.write(aty, StringConstants.FILENAME, "phone", et_accountNumber.getText().toString().trim());
            PreferenceHelper.write(aty, StringConstants.FILENAME, "real_name", bean.getResult().getReal_name());
            PreferenceHelper.write(aty, StringConstants.FILENAME, "isAvatar", true);
            MainActivity.drawer.closeDrawers();
            //    finish();
            ((LoginContract.Presenter) mPresenter).getIsAd();
            //   dismissLoadingDialog();
        } else if (flag == 1) {
            UserInfoBean userInfoBean = (UserInfoBean) JsonUtil.getInstance().json2Obj(s, UserInfoBean.class);
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
            finish();
            dismissLoadingDialog();
        } else if (flag == 2) {
            IsAdBean isAdBean = (IsAdBean) JsonUtil.getInstance().json2Obj(s, IsAdBean.class);
            if (isAdBean.getResult().getIs_ad().equals("0")) {
                PreferenceHelper.write(aty, StringConstants.FILENAME, "isGoneBanner", false);
            } else {
                PreferenceHelper.write(aty, StringConstants.FILENAME, "isGoneBanner", true);
            }
            finish();
            dismissLoadingDialog();
        }
    }

    @Override
    public void error(String msg) {

        dismissLoadingDialog();
        ViewInject.toast(msg);

    }

    @Override
    public void setPresenter(LoginContract.Presenter presenter) {
        mPresenter = presenter;
    }

    /**
     * 监听EditText输入改变
     */
    public void changeInputView(final EditText editText, final View view) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (editText.getText().toString().length() > 0) {
                    if (view != null) {
                        view.setVisibility(View.VISIBLE);
                    }
                    if (et_accountNumber.getText().length() > 0 && et_pwd.getText().length() > 0) {
                        tv_login.setClickable(true);
                        tv_login.setBackgroundResource(R.drawable.shape_login);
                        tv_login.setTextColor(getResources().getColor(R.color.mainColor));
                    } else {
                        tv_login.setClickable(false);
                        tv_login.setBackgroundResource(R.drawable.shape_login1);
                        tv_login.setTextColor(getResources().getColor(R.color.mainColor));
                    }
                } else {
                    if (view != null) {
                        view.setVisibility(View.GONE);
                    }
                    tv_login.setClickable(false);
                    tv_login.setBackgroundResource(R.drawable.shape_login1);
                    tv_login.setTextColor(getResources().getColor(R.color.mainColor));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

}
