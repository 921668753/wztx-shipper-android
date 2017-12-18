package com.ruitukeji.zwbh.mine.mywallet;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.common.BaseActivity;
import com.ruitukeji.zwbh.common.BindView;
import com.ruitukeji.zwbh.entity.MyWalletBean;
import com.ruitukeji.zwbh.mine.mywallet.recharge.PrepaidPhoneRecordsActivity;
import com.ruitukeji.zwbh.mine.mywallet.recharge.RechargeActivity;
import com.ruitukeji.zwbh.mine.mywallet.withdrawal.WithdrawalActivity;
import com.ruitukeji.zwbh.mine.invitefriends.RecommendedRecordActivity;
import com.ruitukeji.zwbh.utils.ActivityTitleUtils;
import com.ruitukeji.zwbh.utils.JsonUtil;
import com.ruitukeji.zwbh.utils.RefreshLayoutUtil;

import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.bingoogolapple.titlebar.BGATitleBar;
import cn.bingoogolapple.titlebar.BGATitleBar.SimpleDelegate;

import static com.ruitukeji.zwbh.main.MainActivity.drawer;

/**
 * 我的钱包
 * Created by Administrator on 2017/2/10.
 */

public class MyWalletActivity extends BaseActivity implements MyWalletContract.View, BGARefreshLayout.BGARefreshLayoutDelegate {

    @BindView(id = R.id.mRefreshLayout)
    private BGARefreshLayout mRefreshLayout;

    @BindView(id = R.id.tv_accountBalance)
    private TextView tv_accountBalance;

    @BindView(id = R.id.tv_withdrawalAmount)
    private TextView tv_withdrawalAmount;

    @BindView(id = R.id.tv_myReferralBonuses)
    private TextView tv_myReferralBonuses;

    @BindView(id = R.id.ll_myReferralBonuses, click = true)
    private LinearLayout ll_myReferralBonuses;

    @BindView(id = R.id.ll_withdrawalAmount, click = true)
    private LinearLayout ll_withdrawalAmount;

    @BindView(id = R.id.tv_recharge, click = true)
    private TextView tv_recharge;

    @BindView(id = R.id.ll_prepaidPhoneRecords, click = true)
    private LinearLayout ll_prepaidPhoneRecords;

    @Override
    public void setRootView() {
        setContentView(R.layout.activity_mywallet);
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
        drawer.closeDrawers();
        BGATitleBar.SimpleDelegate simpleDelegate = new SimpleDelegate() {
            @Override
            public void onClickLeftCtv() {
                aty.finish();
            }

            @Override
            public void onClickRightCtv() {
                showActivity(aty, BillActivity.class);
            }
        };
        ActivityTitleUtils.initToolbar(aty, getString(R.string.myWallet), getString(R.string.bill), R.id.titlebar, simpleDelegate);
    }

    @Override
    public void getSuccess(String s) {
        MyWalletBean myWalletBean = (MyWalletBean) JsonUtil.getInstance().json2Obj(s, MyWalletBean.class);
        tv_accountBalance.setText(myWalletBean.getResult().getBalance());
        tv_myReferralBonuses.setText("+" + myWalletBean.getResult().getBonus());
        dismissLoadingDialog();
    }

    @Override
    public void error(String msg) {
        toLigon(msg);
        dismissLoadingDialog();
        //    ViewInject.toast(msg);
    }

    @Override
    public void setPresenter(MyWalletContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void widgetClick(View v) {
        super.widgetClick(v);
        switch (v.getId()) {
            case R.id.ll_myReferralBonuses:
                showActivity(aty, RecommendedRecordActivity.class);
                break;
            case R.id.ll_withdrawalAmount:
                showActivity(aty, WithdrawalActivity.class);
                break;
            case R.id.tv_recharge:
                showActivity(aty, RechargeActivity.class);
                break;
            case R.id.ll_prepaidPhoneRecords:
                showActivity(aty, PrepaidPhoneRecordsActivity.class);
                break;
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
