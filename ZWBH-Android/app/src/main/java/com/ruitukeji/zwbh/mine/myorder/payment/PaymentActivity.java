package com.ruitukeji.zwbh.mine.myorder.payment;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alipay.sdk.app.EnvUtils;
import com.kymjs.common.StringUtils;
import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.application.MyApplication;
import com.ruitukeji.zwbh.common.BaseActivity;
import com.ruitukeji.zwbh.common.BindView;
import com.ruitukeji.zwbh.common.KJActivityStack;
import com.ruitukeji.zwbh.common.ViewInject;
import com.ruitukeji.zwbh.constant.NumericConstants;
import com.ruitukeji.zwbh.entity.mine.mywallet.recharge.AlipayBean;
import com.ruitukeji.zwbh.entity.mine.mywallet.MyWalletBean;
import com.ruitukeji.zwbh.entity.mine.mywallet.recharge.WxPayBean;
import com.ruitukeji.zwbh.loginregister.LoginActivity;
import com.ruitukeji.zwbh.mine.personaldata.PaySuccessActivity;
import com.ruitukeji.zwbh.utils.ActivityTitleUtils;
import com.ruitukeji.zwbh.utils.JsonUtil;
import com.ruitukeji.zwbh.utils.PayUtils;

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

    @BindView(id = R.id.tv_balancePayment)
    private TextView tv_balancePayment;


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
     * 确定
     */
    @BindView(id = R.id.tv_determine, click = true)
    private TextView tv_determine;

    /**
     * 选择支付方式  1 余额   2  微信  3  支付宝
     */
    private int methodPayment = 0;

    private int order_id = 0;
    private String total_amount = "";

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
            case R.id.img_balancePayment1:
                img_balancePayment1.setImageResource(R.mipmap.selected);
                img_weChatPay.setImageResource(R.mipmap.selected1);
                img_alipayPay.setImageResource(R.mipmap.selected1);
                methodPayment = 1;
                break;
            case R.id.img_weChatPay:
                img_balancePayment1.setImageResource(R.mipmap.selected1);
                img_weChatPay.setImageResource(R.mipmap.selected);
                img_alipayPay.setImageResource(R.mipmap.selected1);
                methodPayment = 2;
                break;
            case R.id.img_alipayPay:
                img_balancePayment1.setImageResource(R.mipmap.selected1);
                img_weChatPay.setImageResource(R.mipmap.selected1);
                img_alipayPay.setImageResource(R.mipmap.selected);
                methodPayment = 3;
                break;
            case R.id.tv_determine:
                showLoadingDialog(getString(R.string.submissionLoad));
                if (methodPayment == 1) {
                    ((PaymentContract.Presenter) mPresenter).postScorePay(order_id);
                } else if (methodPayment == 2) {
                    ((PaymentContract.Presenter) mPresenter).getWxPay(order_id, total_amount);
                } else if (methodPayment == 3) {
                    ((PaymentContract.Presenter) mPresenter).getAlipay(order_id, total_amount);
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
            MyWalletBean myWalletBean = (MyWalletBean) JsonUtil.getInstance().json2Obj(s, MyWalletBean.class);
            String balance = myWalletBean.getResult().getBalance();
            tv_choicePayment.setText(getString(R.string.choicePayment));
            if (StringUtils.toDouble(total_amount) > StringUtils.toDouble(balance)) {
                tv_balancePayment.setText("余额不足");
                img_balancePayment1.setVisibility(View.GONE);
                // 微信
                ll_weChatPay.setVisibility(View.VISIBLE);
                img_weChatPay.setImageResource(R.mipmap.selected);
                img_alipayPay.setImageResource(R.mipmap.selected1);
                methodPayment = 2;
            } else {
                tv_balancePayment.setText(getString(R.string.balancePayment));
                img_balancePayment1.setVisibility(View.VISIBLE);
                img_balancePayment1.setImageResource(R.mipmap.selected);
                img_weChatPay.setImageResource(R.mipmap.selected1);
                img_alipayPay.setImageResource(R.mipmap.selected1);
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
    protected void onDestroy() {
        super.onDestroy();
        payUtils = null;
    }
}