package com.ruitukeji.zwbh.loginregister;

import android.content.Intent;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.kymjs.common.PreferenceHelper;
import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.application.MyApplication;
import com.ruitukeji.zwbh.common.BaseActivity;
import com.ruitukeji.zwbh.common.BindView;
import com.ruitukeji.zwbh.common.KJActivityStack;
import com.ruitukeji.zwbh.common.ViewInject;
import com.ruitukeji.zwbh.constant.StringConstants;
import com.ruitukeji.zwbh.entity.LoginBean;
import com.ruitukeji.zwbh.entity.mine.PersonalCenterBean;
import com.ruitukeji.zwbh.mine.setting.aboutus.AboutUsActivity;
import com.ruitukeji.zwbh.utils.ActivityTitleUtils;
import com.ruitukeji.zwbh.utils.JsonUtil;
import com.umeng.analytics.MobclickAgent;

import static com.ruitukeji.zwbh.main.MainActivity.drawer;

/**
 * 新用户注册
 * Created by Administrator on 2017/2/17.
 */

public class NewUserRegisterActivity extends BaseActivity implements RegisterContract.View {
    private String selectRegisterType;
    /**
     * 倒计时内部类
     */
    private TimeCount time;

    /**
     * 协议图片
     */
    @BindView(id = R.id.img_agreement, click = true)
    private ImageView img_agreement;
    private boolean isClick = true;
    /**
     * 注册协议
     */
    @BindView(id = R.id.tv_agreement, click = true)
    private TextView tv_agreement;
    /**
     * 手机号
     */
    @BindView(id = R.id.et_phone)
    private EditText et_phone;
    /**
     * 验证码
     */
    @BindView(id = R.id.et_code)
    private EditText et_code;
    /**
     * 获取验证码
     */
    @BindView(id = R.id.tv_code, click = true)
    private TextView tv_code;
    /**
     * 密码
     */
    @BindView(id = R.id.et_pwd)
    private EditText et_pwd;
    /**
     * 推荐码
     */
    @BindView(id = R.id.et_referralCode)
    private EditText et_referralCode;


    /**
     * 注册
     */
    @BindView(id = R.id.tv_registe, click = true)
    private TextView tv_registe;

    /**
     * type  验证码类型 reg=注册 restpwd=找回密码 login=登陆 bind=绑定手机号.
     */
    private String opt = "reg";

    @Override
    public void setRootView() {
        setContentView(R.layout.activity_newuserregister);
    }

    /**
     * 初始化数据
     */
    @Override
    public void initData() {
        super.initData();
        mPresenter = new RegisterPresenter(this);
        time = new TimeCount(60000, 1000);// 构造CountDownTimer对象
    }

    /**
     * 渲染view
     */
    @Override
    public void initWidget() {
        super.initWidget();
        initTitle();
    }

    /**
     * 设置标题
     */
    public void initTitle() {
        selectRegisterType = getIntent().getStringExtra(getString(R.string.selectRegisterType));
        ActivityTitleUtils.initToolbar(aty, getString(R.string.newUserRegister), true, R.id.titlebar);
        if (selectRegisterType.equals(getString(R.string.personalUser))) {
            et_phone.setHint(getString(R.string.hintAccountText));
        } else {
            et_phone.setHint(getString(R.string.hintAccountText1));
        }
    }

    @Override
    public void widgetClick(View v) {
        super.widgetClick(v);
        switch (v.getId()) {
            case R.id.tv_code:
                showLoadingDialog(MyApplication.getContext().getString(R.string.submissionLoad));
                ((RegisterContract.Presenter) mPresenter).postCode(et_phone.getText().toString(), opt);
                break;
            case R.id.tv_registe:
                if (isClick) {
                    showLoadingDialog(MyApplication.getContext().getString(R.string.submissionLoad));
                    if (selectRegisterType.equals(getString(R.string.personalUser))) {
                        KJActivityStack.create().finishActivity(SelectRegisterTypeActivity.class);
                        ((RegisterContract.Presenter) mPresenter).postRegister("person", et_phone.getText().toString(), et_code.getText().toString(), et_pwd.getText().toString(), et_referralCode.getText().toString());
                    } else {
                        KJActivityStack.create().finishActivity(SelectRegisterTypeActivity.class);
                        ((RegisterContract.Presenter) mPresenter).postRegister("company", et_phone.getText().toString(), et_code.getText().toString(), et_pwd.getText().toString(), et_referralCode.getText().toString());
                    }
                } else {
                    ViewInject.toast(getString(R.string.agreement1));
                }
                break;
            case R.id.tv_agreement:
                // 注册协议
                Intent registrationAgreement = new Intent();
                registrationAgreement.setClass(aty, AboutUsActivity.class);
                registrationAgreement.putExtra("type", "shipper_registration_protocol");
                showActivity(aty, registrationAgreement);
                break;
            case R.id.img_agreement:
                if (isClick) {
                    img_agreement.setImageResource(R.mipmap.agreement1);
                    isClick = false;
                } else {
                    img_agreement.setImageResource(R.mipmap.agreement);
                    isClick = true;
                }
                break;

            default:
                break;
        }


    }

    /* 定义一个倒计时的内部类 */
    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔
        }

        @Override
        public void onFinish() {// 计时完毕时触发
            tv_code.setText("重新验证");
            tv_code.setClickable(true);
            tv_code.setTextColor(getResources().getColor(R.color.lonincolors));
            tv_code.setBackgroundResource(R.drawable.shape_code);
        }

        @Override
        public void onTick(long millisUntilFinished) {// 计时过程显示
            tv_code.setClickable(false);
            tv_code.setText(millisUntilFinished / 1000 + "秒");
            tv_code.setTextColor(getResources().getColor(R.color.titledivider));
            tv_code.setBackgroundResource(R.drawable.shape_code1);
        }
    }

    @Override
    public void getSuccess(String s, int flag) {
        tv_registe.setEnabled(true);
        if (flag == 0) {
            dismissLoadingDialog();
            //    CodeBean bean = (CodeBean) JsonUtil.getInstance().json2Obj(s, CodeBean.class);
            ViewInject.toast(getString(R.string.testget));
            time.start();
        } else if (flag == 1) {
            time.cancel();
            time = null;
            drawer.closeDrawers();
            PreferenceHelper.write(aty, StringConstants.FILENAME, "isAvatar", true);
            LoginBean bean = (LoginBean) JsonUtil.getInstance().json2Obj(s, LoginBean.class);
            MobclickAgent.onProfileSignIn(et_phone.getText().toString());//账号统计
            PreferenceHelper.write(this, StringConstants.FILENAME, "accessToken", bean.getResult().getAccessToken());
            PreferenceHelper.write(this, StringConstants.FILENAME, "expireTime", bean.getResult().getExpireTime() + "");
            PreferenceHelper.write(this, StringConstants.FILENAME, "refreshToken", bean.getResult().getRefreshToken());
            PreferenceHelper.write(this, StringConstants.FILENAME, "timeBefore", System.currentTimeMillis() + "");
            ((RegisterContract.Presenter) mPresenter).getInfo();
        } else if (flag == 3) {
            PersonalCenterBean userInfoBean = (PersonalCenterBean) JsonUtil.getInstance().json2Obj(s, PersonalCenterBean.class);
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
            KJActivityStack.create().finishActivity(LoginActivity.class);
            if (selectRegisterType.equals(getString(R.string.personalUser))) {
                KJActivityStack.create().finishActivity(SelectRegisterTypeActivity.class);
                skipActivity(aty, PersonalInformationActivity.class);
            } else {
                KJActivityStack.create().finishActivity(SelectRegisterTypeActivity.class);
                skipActivity(aty, EnterpriseInformationActivity.class);
            }
            dismissLoadingDialog();
        }
    }

    @Override
    public void error(String msg) {
        dismissLoadingDialog();
        ViewInject.toast(msg);
        tv_registe.setEnabled(true);
    }

    @Override
    public void setPresenter(RegisterContract.Presenter presenter) {
        mPresenter = presenter;
    }
}
