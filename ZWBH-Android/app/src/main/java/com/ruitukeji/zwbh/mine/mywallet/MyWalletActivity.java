package com.ruitukeji.zwbh.mine.mywallet;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.common.BaseActivity;
import com.ruitukeji.zwbh.common.BindView;
import com.ruitukeji.zwbh.constant.NumericConstants;
import com.ruitukeji.zwbh.entity.MyWalletBean;
import com.ruitukeji.zwbh.loginregister.LoginActivity;
import com.ruitukeji.zwbh.mine.mywallet.accountdetails.AccountDetailsActivity;
import com.ruitukeji.zwbh.mine.mywallet.mybankcard.MyBankCardActivity;
import com.ruitukeji.zwbh.mine.mywallet.paymentpasswordmanagement.modifypaymentpassword.ModifyPaymentPasswordActivity;
import com.ruitukeji.zwbh.mine.mywallet.paymentpasswordmanagement.setpaymentpassword.SetPaymentPasswordActivity;
import com.ruitukeji.zwbh.mine.mywallet.recharge.PrepaidPhoneRecordsActivity;
import com.ruitukeji.zwbh.mine.mywallet.recharge.RechargeActivity;
import com.ruitukeji.zwbh.mine.mywallet.withdrawal.WithdrawalActivity;
import com.ruitukeji.zwbh.mine.invitefriends.RecommendedRecordActivity;
import com.ruitukeji.zwbh.utils.ActivityTitleUtils;
import com.ruitukeji.zwbh.utils.JsonUtil;
import com.ruitukeji.zwbh.utils.RefreshLayoutUtil;

import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * 我的钱包
 * Created by Administrator on 2017/2/10.
 */

public class MyWalletActivity extends BaseActivity implements MyWalletContract.View, BGARefreshLayout.BGARefreshLayoutDelegate {

    @BindView(id = R.id.mRefreshLayout)
    private BGARefreshLayout mRefreshLayout;

    /**
     * 账户余额
     */
    @BindView(id = R.id.tv_accountBalance)
    private TextView tv_accountBalance;

    /**
     * 可提现金额
     */
    @BindView(id = R.id.tv_withdrawalAmount)
    private TextView tv_withdrawalAmount;

    /**
     * 消费总额
     */
    @BindView(id = R.id.tv_totalConsumption)
    private TextView tv_totalConsumption;

    /**
     * 未支付总额
     */
    @BindView(id = R.id.tv_outstandingAmount)
    private TextView tv_outstandingAmount;

    /**
     * 账户明细
     */
    @BindView(id = R.id.ll_accountDetails, click = true)
    private LinearLayout ll_accountDetails;

    /**
     * 充值
     */
    @BindView(id = R.id.ll_recharge, click = true)
    private LinearLayout ll_recharge;

    /**
     * 提现
     */
    @BindView(id = R.id.ll_cashWithdrawal, click = true)
    private TextView ll_cashWithdrawal;

    /**
     * 我的银行卡
     */
    @BindView(id = R.id.ll_myBankCard, click = true)
    private LinearLayout ll_myBankCard;

    /**
     * 支付密码管理
     */
    @BindView(id = R.id.ll_paymentPassword, click = true)
    private LinearLayout ll_paymentPassword;

    @Override
    public void setRootView() {
        setContentView(R.layout.activity_mywallet1);
    }

    @Override
    public void initData() {
        super.initData();
        mPresenter = new MyWalletPresenter(this);
        ((MyWalletContract.Presenter) mPresenter).getMyWallet();
    }

    @Override
    public void initWidget() {
        super.initWidget();
        RefreshLayoutUtil.initRefreshLayout(mRefreshLayout, this, aty, false);
        ActivityTitleUtils.initToolbar(aty, getString(R.string.myWallet), true, R.id.titlebar);
    }

    @Override
    public void widgetClick(View v) {
        super.widgetClick(v);
        switch (v.getId()) {
            case R.id.ll_accountDetails:
                ((MyWalletContract.Presenter) mPresenter).isLogin(1);
                break;
            case R.id.tv_recharge:
                showActivity(aty, RechargeActivity.class);
                break;
            case R.id.ll_cashWithdrawal:
                showActivity(aty, WithdrawalActivity.class);
                break;
            case R.id.ll_myBankCard:
                showActivity(aty, MyBankCardActivity.class);
                break;
            case R.id.ll_paymentPassword:
                ((MyWalletContract.Presenter) mPresenter).isLogin(2);
                break;
        }
    }


    @Override
    public void setPresenter(MyWalletContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void getSuccess(String success, int flag) {
        if (flag == 0) {
            MyWalletBean myWalletBean = (MyWalletBean) JsonUtil.getInstance().json2Obj(success, MyWalletBean.class);
            tv_accountBalance.setText(myWalletBean.getResult().getBalance());
//        tv_myReferralBonuses.setText("+" + myWalletBean.getResult().getBonus());
        } else if (flag == 1) {
            showActivity(aty, AccountDetailsActivity.class);
        } else if (flag == 2) {
            if (true) {
                showActivity(aty, ModifyPaymentPasswordActivity.class);
            } else {
                showActivity(aty, SetPaymentPasswordActivity.class);
            }
        }
        dismissLoadingDialog();


    }

    @Override
    public void errorMsg(String msg, int flag) {
        if (flag == 0) {
            toLigon(msg);
        }
        dismissLoadingDialog();
        if (msg != null && msg.equals("" + NumericConstants.TOLINGIN)) {
            Intent intent = new Intent(aty, LoginActivity.class);
            intent.putExtra("type", "personalCenter");
            showActivity(aty, intent);
            return;
        }

    }


    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout bgaRefreshLayout) {
        mRefreshLayout.endRefreshing();
        ((MyWalletContract.Presenter) mPresenter).getMyWallet();
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout bgaRefreshLayout) {
        mRefreshLayout.endLoadingMore();
        return false;
    }
}
