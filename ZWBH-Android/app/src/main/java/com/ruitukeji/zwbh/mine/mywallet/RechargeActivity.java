package com.ruitukeji.zwbh.mine.mywallet;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alipay.sdk.app.EnvUtils;
import com.kymjs.common.PreferenceHelper;
import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.common.BaseActivity;
import com.ruitukeji.zwbh.common.BindView;
import com.ruitukeji.zwbh.common.ViewInject;
import com.ruitukeji.zwbh.constant.NumericConstants;
import com.ruitukeji.zwbh.constant.StringConstants;
import com.ruitukeji.zwbh.entity.AlipayBean;
import com.ruitukeji.zwbh.entity.WxPayBean;
import com.ruitukeji.zwbh.loginregister.LoginActivity;
import com.ruitukeji.zwbh.mine.personaldata.PaySuccessActivity;
import com.ruitukeji.zwbh.utils.ActivityTitleUtils;
import com.ruitukeji.zwbh.utils.JsonUtil;
import com.ruitukeji.zwbh.utils.PayUtils;

/**
 * 充值
 * Created by Administrator on 2017/2/17.
 */

public class RechargeActivity extends BaseActivity implements RechargeContract.View {

    @BindView(id = R.id.et_inputAmountFull)
    private EditText et_inputAmountFull;

    @BindView(id = R.id.img_weChatPay, click = true)
    private ImageView img_weChatPay;

    @BindView(id = R.id.tv_recharge, click = true)
    private TextView tv_recharge;

    @BindView(id = R.id.img_alipayPay, click = true)
    private ImageView img_alipayPay;

    /**
     * 支付方式 1=支付宝，2=微信
     */
    private int type = 2;

    private PayUtils payUtils = null;

    @Override
    public void setRootView() {
        setContentView(R.layout.activity_recharge);
    }

    @Override
    public void initData() {
        super.initData();
        mPresenter = new RechargePresenter(this);
        payUtils = new PayUtils(this, PaySuccessActivity.class);
    }

    @Override
    public void initWidget() {
        super.initWidget();
        ActivityTitleUtils.initToolbar(aty, getString(R.string.recharge), true, R.id.titlebar);
    }

    @Override
    public void widgetClick(View v) {
        super.widgetClick(v);
        switch (v.getId()) {
            case R.id.img_weChatPay:
                img_weChatPay.setImageResource(R.mipmap.selected);
                img_alipayPay.setImageResource(R.mipmap.selected1);
                type = 2;
                break;
            case R.id.img_alipayPay:
                img_weChatPay.setImageResource(R.mipmap.selected1);
                img_alipayPay.setImageResource(R.mipmap.selected);
                type = 1;
                break;
            case R.id.tv_recharge:
                showLoadingDialog(getString(R.string.submissionLoad));
                PreferenceHelper.write(this, StringConstants.FILENAME, "rechargeMoney", et_inputAmountFull.getText().toString());
                ((RechargeContract.Presenter) mPresenter).postRecharge(et_inputAmountFull.getText().toString(), type);
                break;
        }
    }

    @Override
    public void getSuccess(String s, int flag) {
        if (flag == 1) {
            //支付宝
            AlipayBean alipayBean = (AlipayBean) JsonUtil.getInstance().json2Obj(s, AlipayBean.class);
            if (payUtils == null) {
                payUtils = new PayUtils(this, PaySuccessActivity.class);
            }
            if (alipayBean.getResult().getIsUseSandbox().equals("1")) {
                EnvUtils.setEnv(EnvUtils.EnvEnum.SANDBOX);
            }
            payUtils.doPay(alipayBean.getResult().getOrderString());
        } else if (flag == 2) {
            //微信
            WxPayBean wxPayBean = (WxPayBean) JsonUtil.getInstance().json2Obj(s, WxPayBean.class);
            if (payUtils == null) {
                payUtils = new PayUtils(this, PaySuccessActivity.class);
            }
            payUtils.doPayment(wxPayBean.getResult().getAppid(), wxPayBean.getResult().getPartnerid(), wxPayBean.getResult().getPrepayid(), wxPayBean.getResult().getPackageX(), wxPayBean.getResult().getNoncestr(), wxPayBean.getResult().getTimestamp(), wxPayBean.getResult().getSign());
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
        dismissLoadingDialog();
        ViewInject.toast(msg);
    }

    @Override
    public void setPresenter(RechargeContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        payUtils = null;
    }
}
