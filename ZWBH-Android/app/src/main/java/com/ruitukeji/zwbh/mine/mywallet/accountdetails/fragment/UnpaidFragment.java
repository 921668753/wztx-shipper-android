package com.ruitukeji.zwbh.mine.mywallet.accountdetails.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.adapter.mine.mywallet.accountdetails.AccountDetailsViewAdapter;
import com.ruitukeji.zwbh.common.BaseFragment;
import com.ruitukeji.zwbh.common.BindView;
import com.ruitukeji.zwbh.common.ViewInject;
import com.ruitukeji.zwbh.constant.NumericConstants;
import com.ruitukeji.zwbh.entity.mine.mywallet.accountdetails.AccountDetailsBean;
import com.ruitukeji.zwbh.entity.mine.mywallet.accountdetails.ClassificationBouncedBean;
import com.ruitukeji.zwbh.mine.mywallet.accountdetails.AccountDetailsActivity;
import com.ruitukeji.zwbh.utils.JsonUtil;
import com.ruitukeji.zwbh.utils.RefreshLayoutUtil;
import com.ruitukeji.zwbh.utils.rx.MsgEvent;

import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * 未支付
 * Created by Administrator on 2017/12/15.
 */

public class UnpaidFragment extends BaseFragment implements AccountDetailsContract.View, AdapterView.OnItemClickListener, BGARefreshLayout.BGARefreshLayoutDelegate {
    private AccountDetailsActivity aty;

    @BindView(id = R.id.mRefreshLayout)
    private BGARefreshLayout mRefreshLayout;

    private AccountDetailsViewAdapter mAdapter;

    @BindView(id = R.id.lv_accountDetails)
    private ListView lv_accountDetails;

    /**
     * 错误提示页
     */
    @BindView(id = R.id.ll_commonError)
    private LinearLayout ll_commonError;
    @BindView(id = R.id.tv_hintText, click = true)
    private TextView tv_hintText;

    /**
     * 当前页码
     */
    private int mMorePageNumber = NumericConstants.START_PAGE_NUMBER;
    /**
     * 总页码
     */
    private int totalPageNumber = NumericConstants.START_PAGE_NUMBER;
    /**
     * 是否加载更多
     */
    private boolean isShowLoadingMore = false;

    /**
     * 1为已支付 0为未支付
     */
    private int is_pay = 0;

    private int time = 0;

    @Override
    protected View inflaterView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        aty = (AccountDetailsActivity) getActivity();
        return View.inflate(aty, R.layout.fragment_accountdetails, null);
    }


    @Override
    protected void initData() {
        super.initData();
        mPresenter = new AccountDetailsPresenter(this);
        mAdapter = new AccountDetailsViewAdapter(getActivity());
    }

    @Override
    protected void initWidget(View parentView) {
        super.initWidget(parentView);
        RefreshLayoutUtil.initRefreshLayout(mRefreshLayout, this, getActivity(), true);
        lv_accountDetails.setAdapter(mAdapter);
        lv_accountDetails.setOnItemClickListener(this);
        //  mAdapter.setOnItemChildClickListener(this);
        mRefreshLayout.beginRefreshing();
    }

    /**
     * 控件监听事件
     */
    @Override
    public void widgetClick(View v) {
        super.widgetClick(v);
        switch (v.getId()) {
            case R.id.tv_hintText:
                mRefreshLayout.beginRefreshing();
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        //  PreferenceHelper.write(aty, StringConstants.FILENAME, "refreshOrderFragment", "AllOrderFragment");
//        Intent intent = new Intent(aty, NewAddAddress1Activity.class);
//        intent.putExtra("address_id", mAdapter.getItem(i).getOrder_id());
//        aty.showActivity(aty, intent);
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        mMorePageNumber = NumericConstants.START_PAGE_NUMBER;
        mRefreshLayout.endRefreshing();
        showLoadingDialog(getString(R.string.dataLoad));
        ((AccountDetailsContract.Presenter) mPresenter).getAccountDetails(mMorePageNumber, is_pay, time);
    }


    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        mRefreshLayout.endLoadingMore();
        if (!isShowLoadingMore) {
            return false;
        }
        mMorePageNumber++;
        if (mMorePageNumber > totalPageNumber) {
            ViewInject.toast(getString(R.string.noMoreData));
            return false;
        }
        showLoadingDialog(getString(R.string.dataLoad));
        ((AccountDetailsContract.Presenter) mPresenter).getAccountDetails(mMorePageNumber, is_pay, time);
        return true;
    }

    @Override
    public void onChange() {
        super.onChange();
        if (time == aty.paidId) {
            return;
        }
        time = aty.paidId;
        mRefreshLayout.beginRefreshing();
    }

    @Override
    public void setPresenter(AccountDetailsContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void getSuccess(String success, int flag) {
        if (flag == 0) {
            isShowLoadingMore = true;
            ll_commonError.setVisibility(View.GONE);
            mRefreshLayout.setVisibility(View.VISIBLE);
            AccountDetailsBean accountDetailsBean = (AccountDetailsBean) JsonUtil.getInstance().json2Obj(success, AccountDetailsBean.class);
            mMorePageNumber = accountDetailsBean.getResult().getPage();
            totalPageNumber = accountDetailsBean.getResult().getPageTotal();
            if (accountDetailsBean.getResult().getList() == null || accountDetailsBean.getResult().getList().size() == 0) {
                errorMsg(getString(R.string.serverReturnsDataNull), 0);
                return;
            }
            if (mMorePageNumber == NumericConstants.START_PAGE_NUMBER) {
                mRefreshLayout.endRefreshing();
                mAdapter.clear();
                mAdapter.addNewData(accountDetailsBean.getResult().getList());
            } else if (mMorePageNumber > NumericConstants.START_PAGE_NUMBER) {
                mRefreshLayout.endLoadingMore();
                mAdapter.addMoreData(accountDetailsBean.getResult().getList());
            }
            dismissLoadingDialog();
        } else if (flag == 1) {
            dismissLoadingDialog();
        } else if (flag == 2) {
            mRefreshLayout.beginRefreshing();
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void errorMsg(String msg, int flag) {
        toLigon(msg);
        isShowLoadingMore = false;
        mRefreshLayout.setVisibility(View.GONE);
        ll_commonError.setVisibility(View.VISIBLE);
        tv_hintText.setText(msg + getString(R.string.clickRefresh));
        if (mMorePageNumber == NumericConstants.START_PAGE_NUMBER) {
            mRefreshLayout.endRefreshing();
        } else {
            mRefreshLayout.endLoadingMore();
        }
        dismissLoadingDialog();
    }


    @Override
    public void callMsgEvent(MsgEvent msgEvent) {
        super.callMsgEvent(msgEvent);
        if (((String) msgEvent.getData()).equals("RxBusUnpaidFragmentEvent")) {
            time = aty.paidId;
            mRefreshLayout.beginRefreshing();
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mAdapter.clear();
        mAdapter = null;
    }
}
