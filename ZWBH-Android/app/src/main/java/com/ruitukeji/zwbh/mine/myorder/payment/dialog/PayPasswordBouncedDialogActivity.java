package com.ruitukeji.zwbh.mine.myorder.payment.dialog;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kymjs.common.PreferenceHelper;
import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.common.BaseActivity;
import com.ruitukeji.zwbh.common.BindView;
import com.ruitukeji.zwbh.common.ViewInject;
import com.ruitukeji.zwbh.constant.StringConstants;
import com.ruitukeji.zwbh.mine.mywallet.paymentpasswordmanagement.modifypaymentpassword.ModifyPaymentPassword1Activity;
import com.ruitukeji.zwbh.mine.shippercertification.ShipperCertificationActivity;
import com.ruitukeji.zwbh.utils.SoftKeyboardUtils;
import com.ruitukeji.zwbh.utils.myview.PayPwdEditText;

/**
 * 付款---支付密码弹框
 * Created by Administrator on 2017/11/28.
 */

public class PayPasswordBouncedDialogActivity extends BaseActivity implements PayPasswordBouncedContract.View {

    private int orderId = 0;

    /**
     * 关闭
     */
    @BindView(id = R.id.img_cancel, click = true)
    private ImageView img_cancel;

    /**
     * 密码输入框
     */
    @BindView(id = R.id.et_paymentPassword)
    private PayPwdEditText et_paymentPassword;

    /**
     * 忘记密码
     */
    @BindView(id = R.id.tv_forgotPassword, click = true)
    private TextView tv_forgotPassword;

    @Override
    public void setRootView() {
        setContentView(R.layout.dialog_paypasswordbounced);
        getWindow().setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void initData() {
        super.initData();
        mPresenter = new PayPasswordBouncedPresenter(this);
        orderId = getIntent().getIntExtra("order_id", 0);
    }

    @Override
    public void initWidget() {
        super.initWidget();
        et_paymentPassword.initStyle(R.drawable.edit_num_bg, 6, 0.33f, R.color.bEBEC0Colors, R.color.f2222Colors, 20);
        et_paymentPassword.setOnTextFinishListener(new PayPwdEditText.OnTextFinishListener() {
            @Override
            public void onFinish(String str) {//密码输入完后的回调
                if (str.length() == 6) {
                    ((PayPasswordBouncedContract.Presenter) mPresenter).postOldPayPassword(str);
                }
            }
        });
        et_paymentPassword.setFocus();
    }

    @Override
    public void widgetClick(View v) {
        super.widgetClick(v);
        switch (v.getId()) {
            case R.id.img_cancel:
                et_paymentPassword.clearText();
                SoftKeyboardUtils.packUpKeyboard(this);
                finish();
                break;
            case R.id.tv_forgotPassword:
                Intent intent = new Intent();
                String auth_status = PreferenceHelper.readString(this, StringConstants.FILENAME, "auth_status", "init");
                if (auth_status != null && auth_status.equals("pass")) {
                    intent.setClass(this, ModifyPaymentPassword1Activity.class);
                    startActivity(intent);
                    break;
                } else if (auth_status != null && auth_status.equals("init") || auth_status != null && auth_status.equals("refuse")) {
                    intent.setClass(this, ShipperCertificationActivity.class);
                    startActivity(intent);
                    break;
                }
                ViewInject.toast(getString(R.string.doNotRemember1));
                break;
        }
    }


    @Override
    public void setPresenter(PayPasswordBouncedContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void getSuccess(String success, int flag) {
        if (flag == 0) {
            ((PayPasswordBouncedContract.Presenter) mPresenter).postScorePay(orderId);
        } else if (flag == 1) {
            Intent intent = new Intent();
            setResult(RESULT_OK, intent);
//            // 结束该activity 结束之后，前面的activity才可以处理结果
            finish();
        }
        dismissLoadingDialog();
    }

    @Override
    public void errorMsg(String msg, int flag) {
        toLigon1(msg);
        dismissLoadingDialog();
    }

}

