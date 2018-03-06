package com.ruitukeji.zwbh.mine.myorder.payment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alipay.sdk.app.EnvUtils;
import com.kymjs.common.PreferenceHelper;
import com.kymjs.common.StringUtils;
import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.application.MyApplication;
import com.ruitukeji.zwbh.common.BaseActivity;
import com.ruitukeji.zwbh.common.BindView;
import com.ruitukeji.zwbh.common.KJActivityStack;
import com.ruitukeji.zwbh.common.ViewInject;
import com.ruitukeji.zwbh.constant.NumericConstants;
import com.ruitukeji.zwbh.constant.StringConstants;
import com.ruitukeji.zwbh.entity.mine.mywallet.recharge.AlipayBean;
import com.ruitukeji.zwbh.entity.mine.mywallet.MyWalletBean;
import com.ruitukeji.zwbh.entity.mine.mywallet.recharge.WxPayBean;
import com.ruitukeji.zwbh.loginregister.LoginActivity;
import com.ruitukeji.zwbh.mine.myorder.payment.dialog.PayPasswordBouncedDialogActivity;
import com.ruitukeji.zwbh.mine.mywallet.paymentpasswordmanagement.setpaymentpassword.SetPaymentPasswordActivity;
import com.ruitukeji.zwbh.mine.personaldata.PaySuccessActivity;
import com.ruitukeji.zwbh.utils.ActivityTitleUtils;
import com.ruitukeji.zwbh.utils.JsonUtil;
import com.ruitukeji.zwbh.utils.PayUtils;

import static com.ruitukeji.zwbh.constant.NumericConstants.REQUEST_CODE_CHOOSE_PHOTO;

/**
 * 付款
 * Created by Administrator on 2017/2/24.
 */

public class PaymentActivity extends BaseActivity implements PaymentContract.View {

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

    @BindView(id = R.id.tv_balance)
    private TextView tv_balance;

    @BindView(id = R.id.tv_balancePayment)
    private TextView tv_balancePayment;

    @BindView(id = R.id.img_balancePayment, click = true)
    private ImageView img_balancePayment;

    /**
     * 上传支付凭证
     */
    @BindView(id = R.id.img_uploadProofPayment, click = true)
    private ImageView img_uploadProofPayment;

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
     * 确定
     */
    @BindView(id = R.id.tv_determine, click = true)
    private TextView tv_determine;

    /**
     * 选择支付方式  1   余额   2   微信  3   支付宝 4   上传支付凭证
     */
    private int methodPayment = 0;

    private int order_id = 0;
    private String total_amount = "";

    private PayUtils payUtils = null;
    private PayPasswordBouncedDialogActivity payPasswordBouncedDialog = null;

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
        showLoadingDialog(MyApplication.getContext().getString(R.string.dataLoad));
        ((PaymentContract.Presenter) mPresenter).getMyWallet();
    }

    @Override
    public void initWidget() {
        super.initWidget();
        ActivityTitleUtils.initToolbar(aty, getString(R.string.payment), true, R.id.titlebar);
    }


    @Override
    public void widgetClick(View v) {
        super.widgetClick(v);
        switch (v.getId()) {
            case R.id.img_balancePayment:
                img_balancePayment.setImageResource(R.mipmap.ic_checkbox_select);
                img_weChatPay.setImageResource(R.mipmap.ic_checkbox_unselect);
                img_alipayPay.setImageResource(R.mipmap.ic_checkbox_unselect);
                img_uploadProofPayment.setImageResource(R.mipmap.ic_checkbox_unselect);
                methodPayment = 1;
                break;
            case R.id.img_uploadProofPayment:
                img_balancePayment.setImageResource(R.mipmap.ic_checkbox_unselect);
                img_weChatPay.setImageResource(R.mipmap.ic_checkbox_unselect);
                img_alipayPay.setImageResource(R.mipmap.ic_checkbox_unselect);
                img_uploadProofPayment.setImageResource(R.mipmap.ic_checkbox_select);
                methodPayment = 4;
                break;
            case R.id.img_weChatPay:
                img_balancePayment.setImageResource(R.mipmap.ic_checkbox_unselect);
                img_weChatPay.setImageResource(R.mipmap.ic_checkbox_select);
                img_alipayPay.setImageResource(R.mipmap.ic_checkbox_unselect);
                img_uploadProofPayment.setImageResource(R.mipmap.ic_checkbox_unselect);
                methodPayment = 2;
                break;
            case R.id.img_alipayPay:
                img_balancePayment.setImageResource(R.mipmap.ic_checkbox_unselect);
                img_weChatPay.setImageResource(R.mipmap.ic_checkbox_unselect);
                img_alipayPay.setImageResource(R.mipmap.ic_checkbox_select);
                img_uploadProofPayment.setImageResource(R.mipmap.ic_checkbox_unselect);
                methodPayment = 3;
                break;
            case R.id.tv_determine:
                showLoadingDialog(getString(R.string.submissionLoad));
                if (methodPayment == 1) {
                    dismissLoadingDialog();
                    int is_pay_password = PreferenceHelper.readInt(aty, StringConstants.FILENAME, "is_pay_password", 0);
                    if (is_pay_password == 0) {
                        ViewInject.toast(getString(R.string.notPaymentPassword));
                        Intent intent = new Intent(aty, SetPaymentPasswordActivity.class);
                        aty.startActivity(intent);
                        return;
                    }
                    Intent intent = new Intent(aty, PayPasswordBouncedDialogActivity.class);
                    intent.putExtra("order_id", order_id);
                    startActivityForResult(intent, REQUEST_CODE_CHOOSE_PHOTO);
//                    if (payPasswordBouncedDialog != null && !payPasswordBouncedDialog.isShowing()) {
//                        payPasswordBouncedDialog.show();
//                        return;
//                    }
//                    payPasswordBouncedDialog = new PayPasswordBouncedDialogActivity(this, order_id) {
//                        @Override
//                        public void confirm() {
//                            PaymentActivity.this.getSuccess("", 0);
//                        }
//                    };
//                    payPasswordBouncedDialog.show();
                } else if (methodPayment == 2) {
                    ((PaymentContract.Presenter) mPresenter).getWxPay(order_id, total_amount);
                } else if (methodPayment == 3) {
                    ((PaymentContract.Presenter) mPresenter).getAlipay(order_id, total_amount);
                } else if (methodPayment == 4) {
                    Intent intent = new Intent(aty, PaymentVoucherActivity.class);
                    intent.putExtra("order_id", order_id);
                    startActivityForResult(intent, REQUEST_CODE_CHOOSE_PHOTO);
                }
                break;
        }
    }

    @Override
    public void setPresenter(PaymentContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void getSuccess(String s, int flag) {
        if (flag == 0) {

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
            MyWalletBean myWalletBean = (MyWalletBean) JsonUtil.getInstance().json2Obj(s, MyWalletBean.class);
            String balance = myWalletBean.getResult().getBalance();
            tv_choicePayment.setText(getString(R.string.choicePayment));
            if (StringUtils.toDouble(total_amount) > StringUtils.toDouble(balance)) {
                tv_balancePayment.setText(getString(R.string.lackBalance));
                tv_balance.setVisibility(View.GONE);
                img_balancePayment.setVisibility(View.GONE);
                img_uploadProofPayment.setImageResource(R.mipmap.ic_checkbox_select);
                img_weChatPay.setImageResource(R.mipmap.ic_checkbox_unselect);
                img_alipayPay.setImageResource(R.mipmap.ic_checkbox_unselect);
                methodPayment = 4;
            } else {
                tv_balancePayment.setText(getString(R.string.balancePayment));
                img_balancePayment.setVisibility(View.VISIBLE);
                tv_balance.setVisibility(View.VISIBLE);
                tv_balance.setText(getString(R.string.currentBalance) + balance);
                img_balancePayment.setImageResource(R.mipmap.ic_checkbox_select);
                img_uploadProofPayment.setImageResource(R.mipmap.ic_checkbox_unselect);
                img_weChatPay.setImageResource(R.mipmap.ic_checkbox_unselect);
                img_alipayPay.setImageResource(R.mipmap.ic_checkbox_unselect);
                methodPayment = 1;
            }
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
        } else if (flag == 3) {
            ((PaymentContract.Presenter) mPresenter).getMyWallet();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSE_PHOTO && resultCode == RESULT_OK) {
            Intent intent = new Intent();
            setResult(RESULT_OK, intent);
            finish();
        } else if (requestCode == REQUEST_CODE_CHOOSE_PHOTO && resultCode == RESULT_OK) {
            PreferenceHelper.write(aty, StringConstants.FILENAME, "payClass", getClass().getName());
            KJActivityStack.create().finishActivity(CheckVoucherActivity.class);
            showActivity(aty, PaySuccessActivity.class);
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        payUtils = null;
//        if (payPasswordBouncedDialog != null) {
//            payPasswordBouncedDialog.cancel();
//        }
//        payPasswordBouncedDialog = null;
    }
}