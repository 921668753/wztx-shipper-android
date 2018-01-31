package com.ruitukeji.zwbh.loginregister.registerretrievepassword;

import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.application.MyApplication;
import com.ruitukeji.zwbh.common.BaseActivity;
import com.ruitukeji.zwbh.common.BindView;
import com.ruitukeji.zwbh.common.ViewInject;
import com.ruitukeji.zwbh.utils.ActivityTitleUtils;

import static android.text.InputType.TYPE_CLASS_TEXT;
import static android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD;
import static android.text.InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD;

/**
 * 忘记密码
 * Created by ruitu ck on 2016/9/14.
 */

public class RetrievePasswordActivity extends BaseActivity implements RegisterContract.View {

    /**
     * 倒计时内部类
     */
    private TimeCount time;

    /**
     * 手机号
     */
    @BindView(id = R.id.et_phone)
    private EditText et_phone;

    @BindView(id = R.id.img_deletePhone, click = true)
    private ImageView img_deletePhone;

    /**
     * 验证码
     */
    @BindView(id = R.id.et_code)
    private EditText et_code;

    @BindView(id = R.id.img_deleteCode, click = true)
    private ImageView img_deleteCode;
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

    @BindView(id = R.id.img_delete, click = true)
    private ImageView img_delete;

    @BindView(id = R.id.img_biyan, click = true)
    private ImageView img_biyan;

    /**
     * 重置密码
     */
    @BindView(id = R.id.tv_resetPassword, click = true)
    private TextView tv_resetPassword;

    /**
     * 验证码类型 reg=注册 restpwd=找回密码 login=登陆 bind=绑定手机号.
     */
    private String type = "resetpwd";
    private long millisUntilFinished = 0;

    @Override
    public void setRootView() {
        setContentView(R.layout.activity_retrievepassword);
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
        changeInputView(et_phone, img_deletePhone);
        changeInputView(et_code, img_deleteCode);
        changeInputView(et_pwd, img_delete);
    }

    /**
     * 设置标题
     */
    public void initTitle() {
        tv_code.setClickable(false);
        tv_resetPassword.setClickable(false);
        ActivityTitleUtils.initToolbar(aty, getString(R.string.retrievePassword), true, R.id.titlebar);
    }

    @Override
    public void widgetClick(View v) {
        super.widgetClick(v);
        switch (v.getId()) {
            case R.id.img_deletePhone:
                et_phone.setText("");
                break;
            case R.id.img_deleteCode:
                et_code.setText("");
                break;
            case R.id.tv_code:
                showLoadingDialog(MyApplication.getContext().getString(R.string.sendingLoad));
                ((RegisterContract.Presenter) mPresenter).postCode(et_phone.getText().toString(), type);
                break;
            case R.id.img_delete:
                et_pwd.setText("");
                break;
            case R.id.img_biyan:
                if (et_pwd.getInputType() == 0x00000081) {
                    img_biyan.setImageResource(R.mipmap.ic_zhengkai);
                    et_pwd.setInputType(TYPE_CLASS_TEXT | TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    img_biyan.setImageResource(R.mipmap.ic_biyan);
                    et_pwd.setInputType(TYPE_CLASS_TEXT | TYPE_TEXT_VARIATION_PASSWORD);
                }
                et_pwd.setSelection(et_pwd.getText().toString().trim().length());
                et_pwd.requestFocus();
                break;
            case R.id.tv_resetPassword:
                tv_resetPassword.setClickable(false);
                showLoadingDialog(MyApplication.getContext().getString(R.string.submissionLoad));
                ((RegisterContract.Presenter) mPresenter).postResetpwd(et_phone.getText().toString(), et_code.getText().toString(), et_pwd.getText().toString());
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
            tv_code.setText(getString(R.string.revalidation));
            tv_code.setClickable(true);
            millisUntilFinished = 0;
            tv_code.setBackgroundResource(R.drawable.shape_login);
        }

        @Override
        public void onTick(long millisUntilFinished1) {// 计时过程显示
            tv_code.setClickable(false);
            millisUntilFinished = millisUntilFinished1;
            tv_code.setText(millisUntilFinished1 / 1000 + getString(R.string.toResend));
            tv_code.setBackgroundResource(R.drawable.shape_login1);
        }
    }


    /**
     * 监听EditText输入改变
     */
    public void changeInputView(EditText editText, View view) {
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
                    if (editText.getId() == R.id.et_phone && millisUntilFinished == 0 && et_phone.getText().length() == 11) {
                        tv_code.setBackgroundResource(R.drawable.shape_login);
                        tv_code.setClickable(true);
                    }
                    if (et_phone.getText().length() == 11 && et_code.getText().length() >= 4 && et_pwd.getText().length() >= 6) {
                        tv_resetPassword.setClickable(true);
                        tv_resetPassword.setBackgroundResource(R.drawable.shape_login);
                    } else {
                        tv_resetPassword.setClickable(false);
                        tv_resetPassword.setBackgroundResource(R.drawable.shape_login1);
                    }
                } else {
                    if (view != null) {
                        view.setVisibility(View.GONE);
                    }
                    if (editText.getId() == R.id.et_phone) {
                        tv_code.setClickable(false);
                        tv_code.setBackgroundResource(R.drawable.shape_login1);
                    }
                    tv_resetPassword.setClickable(false);
                    tv_resetPassword.setBackgroundResource(R.drawable.shape_login1);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    public void getSuccess(String s, int flag) {
        dismissLoadingDialog();
        if (flag == 0) {
            //    CodeBean bean = (CodeBean) JsonUtil.getInstance().json2Obj(s, CodeBean.class);
            ViewInject.toast(getString(R.string.testget));
            time.start();
        } else if (flag == 2) {
            time.cancel();
            time = null;
            ViewInject.toast(getString(R.string.resetpwd));
            finish();
        }
    }

    @Override
    public void error(String msg) {
        dismissLoadingDialog();
        ViewInject.toast(msg);
        tv_resetPassword.setClickable(true);
    }

    @Override
    public void setPresenter(RegisterContract.Presenter presenter) {
        mPresenter = presenter;
    }
}
