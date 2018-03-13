package com.ruitukeji.zwbh.mine.mywallet.withdrawal;

import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.adapter.mine.mywallet.WithdrawalRecordViewAdapter;
import com.ruitukeji.zwbh.common.BaseActivity;
import com.ruitukeji.zwbh.common.BindView;
import com.ruitukeji.zwbh.common.ViewInject;
import com.ruitukeji.zwbh.constant.NumericConstants;
import com.ruitukeji.zwbh.entity.mine.mywallet.withdrawal.WithdrawalRecordBean;
import com.ruitukeji.zwbh.utils.ActivityTitleUtils;
import com.ruitukeji.zwbh.utils.JsonUtil;
import com.ruitukeji.zwbh.utils.RefreshLayoutUtil;

import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * 提现记录
 * Created by Administrator on 2017/2/15.
 */

public class WithdrawalRecordActivity extends BaseActivity implements WithdrawalRecordContract.View, AdapterView.OnItemClickListener, BGARefreshLayout.BGARefreshLayoutDelegate {

    @BindView(id = R.id.lv_withdrawalrecord)
    private ListView lv_withdrawalrecord;
    @BindView(id = R.id.mRefreshLayout)
    private BGARefreshLayout mRefreshLayout;


    private WithdrawalRecordViewAdapter withdrawalRecordViewAdapter;

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
    public void setRootView() {
        setContentView(R.layout.activity_withdrawalrecord);
    }

    @Override
    public void initData() {
        super.initData();
        mPresenter = new WithdrawalRecordPresenter(this);
        withdrawalRecordViewAdapter = new WithdrawalRecordViewAdapter(this);
    }

    @Override
    public void initWidget() {
        super.initWidget();
        ActivityTitleUtils.initToolbar(aty, getString(R.string.withdrawalRecord), true, R.id.titlebar);
        RefreshLayoutUtil.initRefreshLayout(mRefreshLayout, this, aty, true);
        lv_withdrawalrecord.setAdapter(withdrawalRecordViewAdapter);
        lv_withdrawalrecord.setOnItemClickListener(this);
        mRefreshLayout.beginRefreshing();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        mMorePageNumber = NumericConstants.START_PAGE_NUMBER;
        mRefreshLayout.endRefreshing();
        showLoadingDialog(getString(R.string.dataLoad));
        ((WithdrawalRecordContract.Presenter) mPresenter).getWithdrawalRecord(mMorePageNumber);
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
        ((WithdrawalRecordContract.Presenter) mPresenter).getWithdrawalRecord(mMorePageNumber);
        return true;
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
    public void getSuccess(String s) {
        isShowLoadingMore = true;
        ll_commonError.setVisibility(View.GONE);
        mRefreshLayout.setVisibility(View.VISIBLE);
        WithdrawalRecordBean withdrawalRecordBean = (WithdrawalRecordBean) JsonUtil.getInstance().json2Obj(s, WithdrawalRecordBean.class);
        if (withdrawalRecordBean.getResult().getList() == null || withdrawalRecordBean.getResult().getList().size() == 0) {
            error(getString(R.string.serverReturnsDataNull));
            return;
        }
        mMorePageNumber = withdrawalRecordBean.getResult().getPage();
        totalPageNumber = withdrawalRecordBean.getResult().getPageTotal();
        if (mMorePageNumber == NumericConstants.START_PAGE_NUMBER) {
            mRefreshLayout.endRefreshing();
            withdrawalRecordViewAdapter.clear();
            withdrawalRecordViewAdapter.addNewData(withdrawalRecordBean.getResult().getList());
        } else {
            mRefreshLayout.endLoadingMore();
            withdrawalRecordViewAdapter.addMoreData(withdrawalRecordBean.getResult().getList());
        }
        dismissLoadingDialog();
    }

    @Override
    public void error(String msg) {
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
    public void setPresenter(WithdrawalRecordContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        withdrawalRecordViewAdapter.clear();
        withdrawalRecordViewAdapter = null;
    }
}
