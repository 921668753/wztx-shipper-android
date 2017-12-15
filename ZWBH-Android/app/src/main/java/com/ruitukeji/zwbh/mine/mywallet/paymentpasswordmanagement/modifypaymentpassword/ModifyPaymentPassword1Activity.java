package com.ruitukeji.zwbh.mine.mywallet.paymentpasswordmanagement.modifypaymentpassword;

import android.annotation.SuppressLint;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;

import com.kymjs.common.PreferenceHelper;
import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.common.BaseActivity;
import com.ruitukeji.zwbh.common.BindView;
import com.ruitukeji.zwbh.common.ViewInject;
import com.ruitukeji.zwbh.constant.StringConstants;
import com.ruitukeji.zwbh.utils.ActivityTitleUtils;
import com.ruitukeji.zwbh.utils.myview.VerifyCodeView;

/**
 * 修改支付密码
 * Created by Administrator on 2017/12/13.
 */

public class ModifyPaymentPassword1Activity extends BaseActivity implements ModifyPaymentPasswordContract.View {

    /**
     * 倒计时内部类
     */
    private TimeCount time;

    /**
     * 手机号
     */
    @BindView(id = R.id.tv_phone)
    private TextView tv_phone;

    /**
     * 验证码
     */
    @BindView(id = R.id.verify_code_view)
    private VerifyCodeView verifyCodeView;

    /**
     * 倒计时
     */
    @BindView(id = R.id.tv_timing, click = true)
    private TextView tv_timing;

    String phone;

    /**
     * 验证码类型 reg=注册 restpwd=找回密码 login=登陆 bind=绑定手机号.
     */
    private String type = "resetpwd";

    @Override
    public void setRootView() {
        setContentView(R.layout.activity_modifypaymentpassword1);
    }


    @Override
    public void initData() {
        super.initData();
        mPresenter = new ModifyPaymentPasswordPresenter(this);
        time = new TimeCount(60000, 1000);// 构造CountDownTimer对象
        phone = PreferenceHelper.readString(aty, StringConstants.FILENAME, "phone", "");
        ((ModifyPaymentPasswordContract.Presenter) mPresenter).postCode(phone, type);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void initWidget() {
        super.initWidget();
        ActivityTitleUtils.initToolbar(aty, getString(R.string.modifyPaymentPassword), true, R.id.titlebar);
        verifyCodeView.setInputCompleteListener(new VerifyCodeView.InputCompleteListener() {
            @Override
            public void inputComplete() {
                showLoadingDialog(getString(R.string.submissionLoad));
                ((ModifyPaymentPasswordContract.Presenter) mPresenter).postVerificationCode(phone, verifyCodeView.getEditContent());
            }

            @Override
            public void invalidContent() {

            }
        });

        tv_phone.setText(phone.substring(0, 2) + "****" + phone.substring(7));
    }


    @Override
    public void widgetClick(View v) {
        super.widgetClick(v);
        switch (v.getId()) {
            case R.id.tv_timing:
                showLoadingDialog(getString(R.string.sendingLoad));
                ((ModifyPaymentPasswordContract.Presenter) mPresenter).postCode(phone, type);
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
            tv_timing.setText(getString(R.string.revalidation));
            tv_timing.setClickable(true);
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onTick(long millisUntilFinished) {// 计时过程显示
            tv_timing.setClickable(false);
            tv_timing.setText(millisUntilFinished / 1000 + getString(R.string.toResend1));
        }
    }

    @Override
    public void setPresenter(ModifyPaymentPasswordContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void getSuccess(String success, int flag) {
        if (flag == 0) {
            //   tv_timing.setEnabled(false);
            //    CodeBean bean = (CodeBean) JsonUtil.getInstance().json2Obj(s, CodeBean.class);
            //   ViewInject.toast(getString(R.string.testget));
            time.start();
        } else if (flag == 1) {
            time.cancel();
            time = null;
            showActivity(aty, ModifyPaymentPassword2Activity.class);
        }
        dismissLoadingDialog();
    }

    @Override
    public void errorMsg(String msg, int flag) {
        dismissLoadingDialog();
        ViewInject.toast(msg);
    }
}
