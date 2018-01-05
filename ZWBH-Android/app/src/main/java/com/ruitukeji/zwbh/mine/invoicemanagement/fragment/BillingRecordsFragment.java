package com.ruitukeji.zwbh.mine.invoicemanagement.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.kymjs.common.PreferenceHelper;
import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.adapter.mine.invoicemanagement.BillingRecordsViewAdapter;
import com.ruitukeji.zwbh.common.BaseFragment;
import com.ruitukeji.zwbh.common.BindView;
import com.ruitukeji.zwbh.common.ViewInject;
import com.ruitukeji.zwbh.constant.NumericConstants;
import com.ruitukeji.zwbh.constant.StringConstants;
import com.ruitukeji.zwbh.entity.mine.invoicemanagement.BillingRecordsBean;
import com.ruitukeji.zwbh.mine.invoicemanagement.InvoiceManagementActivity;
import com.ruitukeji.zwbh.mine.invoicemanagement.billingdetails.BillingDetailsActivity;
import com.ruitukeji.zwbh.utils.JsonUtil;
import com.ruitukeji.zwbh.utils.RefreshLayoutUtil;

import cn.bingoogolapple.baseadapter.BGAOnItemChildClickListener;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * 开票记录
 * Created by Administrator on 2017/12/15.
 */

public class BillingRecordsFragment extends BaseFragment implements BillingRecordsContract.View, AdapterView.OnItemClickListener, BGARefreshLayout.BGARefreshLayoutDelegate, BGAOnItemChildClickListener {

    private InvoiceManagementActivity aty;

    @BindView(id = R.id.mRefreshLayout)
    private BGARefreshLayout mRefreshLayout;

    private BillingRecordsViewAdapter mAdapter;

    @BindView(id = R.id.lv_billingRecords)
    private ListView lv_billingRecords;

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


    @Override
    protected View inflaterView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        aty = (InvoiceManagementActivity) getActivity();
        return View.inflate(aty, R.layout.fragment_billingrecords, null);
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter = new BillingRecordsPresenter(this);
        mAdapter = new BillingRecordsViewAdapter(getActivity());
    }

    @Override
    protected void initWidget(View parentView) {
        super.initWidget(parentView);
        RefreshLayoutUtil.initRefreshLayout(mRefreshLayout, this, getActivity(), true);
        lv_billingRecords.setAdapter(mAdapter);
        lv_billingRecords.setOnItemClickListener(this);
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
        Intent intent = new Intent(aty, BillingDetailsActivity.class);
        intent.putExtra("id", mAdapter.getItem(i).getId());
        aty.showActivity(aty, intent);
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        mMorePageNumber = NumericConstants.START_PAGE_NUMBER;
        mRefreshLayout.endRefreshing();
        showLoadingDialog(getString(R.string.dataLoad));
        ((BillingRecordsContract.Presenter) mPresenter).getBillingRecordsList(mMorePageNumber);
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
        ((BillingRecordsContract.Presenter) mPresenter).getBillingRecordsList(mMorePageNumber);
        return true;
    }


    @Override
    public void setPresenter(BillingRecordsContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void getSuccess(String success, int flag) {
        if (flag == 0) {
            isShowLoadingMore = true;
            ll_commonError.setVisibility(View.GONE);
            mRefreshLayout.setVisibility(View.VISIBLE);
            BillingRecordsBean billingRecordsBean = (BillingRecordsBean) JsonUtil.getInstance().json2Obj(success, BillingRecordsBean.class);
            mMorePageNumber = billingRecordsBean.getResult().getPage();
            totalPageNumber = billingRecordsBean.getResult().getPageTotal();
            if (billingRecordsBean.getResult().getList() == null || billingRecordsBean.getResult().getList().size() == 0) {
                errorMsg(getString(R.string.serverReturnsDataNull), 0);
                return;
            }
            if (mMorePageNumber == NumericConstants.START_PAGE_NUMBER) {
                mRefreshLayout.endRefreshing();
                mAdapter.clear();
                mAdapter.addNewData(billingRecordsBean.getResult().getList());
            } else if (mMorePageNumber > NumericConstants.START_PAGE_NUMBER) {
                mRefreshLayout.endLoadingMore();
                mAdapter.addMoreData(billingRecordsBean.getResult().getList());
            }
            dismissLoadingDialog();
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
    public void onChange() {
        super.onChange();
        boolean RefreshBillingRecordsFragment = PreferenceHelper.readBoolean(aty, StringConstants.FILENAME, "RefreshBillingRecordsFragment", false);
        if (RefreshBillingRecordsFragment) {
            mRefreshLayout.beginRefreshing();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mAdapter.clear();
        mAdapter = null;
    }

    @Override
    public void onItemChildClick(ViewGroup viewGroup, View view, int i) {
//        if (view.getId() == R.id.ll_joinBlacklist) {
//            ((BillingRecordsContract.Presenter) mPresenter).postBlacklist(i);
//        } else if (view.getId() == R.id.ll_delete) {
//            ((BillingRecordsContract.Presenter) mPresenter).postDeleteDriver(i);
//        }
    }
}