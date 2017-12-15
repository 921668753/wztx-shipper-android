package com.ruitukeji.zwbh.mine.personaldata;

import android.view.View;
import android.widget.TextView;

import com.kymjs.common.PreferenceHelper;
import com.kymjs.common.StringUtils;
import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.common.BaseActivity;
import com.ruitukeji.zwbh.common.BindView;
import com.ruitukeji.zwbh.common.KJActivityStack;
import com.ruitukeji.zwbh.constant.StringConstants;
import com.ruitukeji.zwbh.mine.myorder.orderfragment.CheckVoucherActivity;
import com.ruitukeji.zwbh.mine.myorder.orderfragment.OrderDetailsActivity;
import com.ruitukeji.zwbh.mine.myorder.orderfragment.PaymentActivity;
import com.ruitukeji.zwbh.mine.mywallet.recharge.RechargeActivity;
import com.ruitukeji.zwbh.utils.ActivityTitleUtils;

/**
 * 支付成功
 * Created by Administrator on 2017/2/24.
 */

public class PaySuccessActivity extends BaseActivity {


    @BindView(id = R.id.tv_topUpSuccess)
    private TextView tv_topUpSuccess;

    @BindView(id = R.id.topUpAmount)
    private TextView topUpAmount;


    @BindView(id = R.id.tv_complete, click = true)
    private TextView tv_complete;

    @Override
    public void setRootView() {
        setContentView(R.layout.activity_paysuccess);
    }

    @Override
    public void initData() {
        super.initData();
        String payClass = PreferenceHelper.readString(this, StringConstants.FILENAME, "payClass");
        if (StringUtils.isEmpty(payClass)) {
            finish();
        } else if (payClass.contains("PayDepositActivity")) {//缴纳保证金
            PreferenceHelper.write(this, StringConstants.FILENAME, "isRefreshPersonalDataActivity1", true);
            PreferenceHelper.write(aty, StringConstants.FILENAME, "bond_status", "checked");
            KJActivityStack.create().finishActivity(PayDepositActivity.class);
            ActivityTitleUtils.initToolbar(aty, getString(R.string.payResult), true, R.id.titlebar);
            topUpAmount.setVisibility(View.GONE);
            tv_topUpSuccess.setText(getString(R.string.paySuccess));
        } else if (payClass.contains("RechargeActivity")) {//充值
            KJActivityStack.create().finishActivity(RechargeActivity.class);
            ActivityTitleUtils.initToolbar(aty, getString(R.string.topUpResults), true, R.id.titlebar);
            String rechargeMoney = PreferenceHelper.readString(this, StringConstants.FILENAME, "rechargeMoney");
            topUpAmount.setText(getString(R.string.topUpAmount) + rechargeMoney + getString(R.string.yuan));
        } else if (payClass.contains("PaymentActivity")) {//订单  个人  //订单  企业
            String fragment = PreferenceHelper.readString(aty, StringConstants.FILENAME, "refreshOrderFragment", "AllOrderFragment");
            if (fragment.equals("AllOrderFragment")) {
                PreferenceHelper.write(aty, StringConstants.FILENAME, "isRefreshCompleted1", true);
                PreferenceHelper.write(aty, StringConstants.FILENAME, "isRefreshOrder", true);
            } else if (fragment.equals("CompletedFragment")) {
                PreferenceHelper.write(aty, StringConstants.FILENAME, "isRefreshCompleted", true);
                PreferenceHelper.write(aty, StringConstants.FILENAME, "isRefreshOrder1", true);
            }
            KJActivityStack.create().finishActivity(OrderDetailsActivity.class);
            KJActivityStack.create().finishActivity(CheckVoucherActivity.class);
            KJActivityStack.create().finishActivity(PaymentActivity.class);
            ActivityTitleUtils.initToolbar(aty, getString(R.string.payResult), true, R.id.titlebar);
            topUpAmount.setVisibility(View.GONE);
            tv_topUpSuccess.setText(getString(R.string.paySuccess));
        } else {
            finish();
        }
        PreferenceHelper.write(this, StringConstants.FILENAME, "payClass", "");
    }

    @Override
    public void initWidget() {
        super.initWidget();
    }

    @Override
    public void widgetClick(View v) {
        super.widgetClick(v);
        switch (v.getId()) {
            case R.id.tv_complete:
                finish();
                break;
        }
    }
}
