package com.ruitukeji.zwbh.mine.personaldata;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alipay.sdk.app.EnvUtils;
import com.kymjs.common.PreferenceHelper;
import com.kymjs.common.StringUtils;
import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.application.MyApplication;
import com.ruitukeji.zwbh.common.BaseActivity;
import com.ruitukeji.zwbh.common.BindView;
import com.ruitukeji.zwbh.common.ViewInject;
import com.ruitukeji.zwbh.constant.NumericConstants;
import com.ruitukeji.zwbh.constant.StringConstants;
import com.ruitukeji.zwbh.entity.mine.mywallet.recharge.AlipayBean;
import com.ruitukeji.zwbh.entity.startpage.AppConfigBean;
import com.ruitukeji.zwbh.entity.mine.mywallet.recharge.WxPayBean;
import com.ruitukeji.zwbh.loginregister.LoginActivity;
import com.ruitukeji.zwbh.utils.ActivityTitleUtils;
import com.ruitukeji.zwbh.utils.JsonUtil;
import com.ruitukeji.zwbh.utils.PayUtils;

/**
 * 缴纳保证金
 * Created by Administrator on 2017/2/23.
 */

public class PayDepositActivity extends BaseActivity implements PayDepositContract.View {

    @BindView(id = R.id.tv_accountDeposit)
    private TextView tv_accountDeposit;

    @BindView(id = R.id.img_weChatPay, click = true)
    private ImageView img_weChatPay;

    @BindView(id = R.id.img_alipayPay, click = true)
    private ImageView img_alipayPay;

    @BindView(id = R.id.tv_determine, click = true)
    private TextView tv_determine;


    private int pay_way = 2;
    private PayUtils payUtils = null;


    @Override
    public void setRootView() {
        setContentView(R.layout.activity_paydeposit);
    }

    @Override
    public void initData() {
        super.initData();
        mPresenter = new PayDepositPresenter(this);
        payUtils = new PayUtils(this, PaySuccessActivity.class);
    }

    @Override
    public void initWidget() {
        super.initWidget();
        ActivityTitleUtils.initToolbar(aty, getString(R.string.payDeposit), true, R.id.titlebar);
        showLoadingDialog(MyApplication.getContext().getString(R.string.dataLoad));
        ((PayDepositContract.Presenter) mPresenter).getAppConfig();
    }

    @Override
    public void widgetClick(View v) {
        super.widgetClick(v);
        switch (v.getId()) {
            case R.id.img_weChatPay:
                img_weChatPay.setImageResource(R.mipmap.selected);
                img_alipayPay.setImageResource(R.mipmap.selected1);
                pay_way = 2;
                break;
            case R.id.img_alipayPay:
                img_weChatPay.setImageResource(R.mipmap.selected1);
                img_alipayPay.setImageResource(R.mipmap.selected);
                pay_way = 1;
                break;
            case R.id.tv_determine:
                showLoadingDialog(getString(R.string.submissionLoad));
                ((PayDepositContract.Presenter) mPresenter).getPayDeposit(pay_way);
                break;
        }
    }

    @Override
    public void getSuccess(String s, int flag) {
        if (flag == 0) {
            AppConfigBean appConfigBean = (AppConfigBean) JsonUtil.getInstance().json2Obj(s, AppConfigBean.class);
            PreferenceHelper.write(this, StringConstants.FILENAME, "lastApkUrl", appConfigBean.getResult().getLastApkUrl());
            PreferenceHelper.write(this, StringConstants.FILENAME, "lastApkVersion", appConfigBean.getResult().getLastApkVersion());
            PreferenceHelper.write(this, StringConstants.FILENAME, "lastApkVersionNum", appConfigBean.getResult().getLastApkVersionNum());
            PreferenceHelper.write(this, StringConstants.FILENAME, "defaultAvatar", appConfigBean.getResult().getDefaultAvatar());
            PreferenceHelper.write(this, StringConstants.FILENAME, "share_percent", appConfigBean.getResult().getShare_percent());
            PreferenceHelper.write(this, StringConstants.FILENAME, "grab_range", appConfigBean.getResult().getGrab_range());
            PreferenceHelper.write(this, StringConstants.FILENAME, "premium_rate", appConfigBean.getResult().getPremium_rate());
            PreferenceHelper.write(this, StringConstants.FILENAME, "bond_person_amount", appConfigBean.getResult().getBond_person_amount());
            PreferenceHelper.write(this, StringConstants.FILENAME, "bond_company_amount", appConfigBean.getResult().getBond_company_amount());
            PreferenceHelper.write(this, StringConstants.FILENAME, "withdraw_begintime", appConfigBean.getResult().getWithdraw_begintime());
            PreferenceHelper.write(this, StringConstants.FILENAME, "withdraw_endtime", appConfigBean.getResult().getWithdraw_endtime());
            PreferenceHelper.write(this, StringConstants.FILENAME, "custom_phone", appConfigBean.getResult().getCustom_phone());
            PreferenceHelper.write(this, StringConstants.FILENAME, "custom_email", appConfigBean.getResult().getCustom_email());
            PreferenceHelper.write(this, StringConstants.FILENAME, "complain_phone", appConfigBean.getResult().getComplain_phone());
            PreferenceHelper.write(this, StringConstants.FILENAME, "weixin_limit", appConfigBean.getResult().getWeixin_limit());
            PreferenceHelper.write(this, StringConstants.FILENAME, "alipay_limit", appConfigBean.getResult().getAlipay_limit());
            PreferenceHelper.write(this, StringConstants.FILENAME, "tran_account", appConfigBean.getResult().getTran_account());
            PreferenceHelper.write(this, StringConstants.FILENAME, "share_shipper", appConfigBean.getResult().getShare_shipper());
            PreferenceHelper.write(this, StringConstants.FILENAME, "share_shipper_description", appConfigBean.getResult().getShare_shipper_description());
            PreferenceHelper.write(this, StringConstants.FILENAME, "share_shipper_title", appConfigBean.getResult().getShare_shipper_title());
            String type = PreferenceHelper.readString(this, StringConstants.FILENAME, "type");
            if (type.equals("person")) {
                String bond_person_amount = appConfigBean.getResult().getBond_person_amount();
                if (StringUtils.isEmpty(bond_person_amount)) {
                    dismissLoadingDialog();
                    finish();
                    return;
                }
                tv_accountDeposit.setText(bond_person_amount + getString(R.string.yuan));
            } else {
                String bond_company_amount = appConfigBean.getResult().getBond_company_amount();
                if (StringUtils.isEmpty(bond_company_amount)) {
                    dismissLoadingDialog();
                    finish();
                    return;
                }
                tv_accountDeposit.setText(bond_company_amount + getString(R.string.yuan));
            }
        } else if (flag == 1) {
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
    public void setPresenter(PayDepositContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        payUtils = null;
    }
}
