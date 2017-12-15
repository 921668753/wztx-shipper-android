package com.ruitukeji.zwbh.mine.mywallet.recharge;

import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.adapter.mine.mywallet.RechargeRecordViewAdapter;
import com.ruitukeji.zwbh.common.BaseActivity;
import com.ruitukeji.zwbh.common.BindView;
import com.ruitukeji.zwbh.common.ViewInject;
import com.ruitukeji.zwbh.constant.NumericConstants;
import com.ruitukeji.zwbh.entity.PrepaidPhoneRecordsBean;
import com.ruitukeji.zwbh.utils.ActivityTitleUtils;
import com.ruitukeji.zwbh.utils.JsonUtil;
import com.ruitukeji.zwbh.utils.RefreshLayoutUtil;

import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * 充值记录
 * Created by Administrator on 2017/2/15.
 */

public class PrepaidPhoneRecordsActivity extends BaseActivity implements PrepaidPhoneRecordsContract.View, AdapterView.OnItemClickListener, BGARefreshLayout.BGARefreshLayoutDelegate {


    @BindView(id = R.id.lv_prepaidPhoneRecords)
    private ListView lv_prepaidPhoneRecords;

    @BindView(id = R.id.mRefreshLayout)
    private BGARefreshLayout mRefreshLayout;

    private RechargeRecordViewAdapter rechargeRecordViewAdapter;

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
        setContentView(R.layout.activity_prepaidphonerecords);
    }

    @Override
    public void initData() {
        super.initData();
        mPresenter = new PrepaidPhoneRecordsPresenter(this);
        rechargeRecordViewAdapter = new RechargeRecordViewAdapter(this);
    }

    @Override
    public void initWidget() {
        super.initWidget();
        ActivityTitleUtils.initToolbar(aty, getString(R.string.prepaidPhoneRecords), true, R.id.titlebar);
        RefreshLayoutUtil.initRefreshLayout(mRefreshLayout, this, aty, true);
        lv_prepaidPhoneRecords.setAdapter(rechargeRecordViewAdapter);
//        lv_prepaidPhoneRecords.setOnItemClickListener(this);
        ((PrepaidPhoneRecordsContract.Presenter) mPresenter).getPrepaidPhoneRecords(mMorePageNumber);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        mMorePageNumber = NumericConstants.START_PAGE_NUMBER;
        mRefreshLayout.endRefreshing();
//        showLoadingDialog(getString(R.string.dataLoad));
        ((PrepaidPhoneRecordsContract.Presenter) mPresenter).getPrepaidPhoneRecords(mMorePageNumber);
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
//        showLoadingDialog(getString(R.string.dataLoad));
        ((PrepaidPhoneRecordsContract.Presenter) mPresenter).getPrepaidPhoneRecords(mMorePageNumber);
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
        PrepaidPhoneRecordsBean prepaidPhoneRecordsBean = (PrepaidPhoneRecordsBean) JsonUtil.getInstance().json2Obj(s, PrepaidPhoneRecordsBean.class);
        if (prepaidPhoneRecordsBean.getResult().getList() == null || prepaidPhoneRecordsBean.getResult().getList().size() == 0) {
            error(getString(R.string.serverReturnsDataNull));
            return;
        }
        if (mMorePageNumber == NumericConstants.START_PAGE_NUMBER) {
            mRefreshLayout.endRefreshing();
            rechargeRecordViewAdapter.clear();
            rechargeRecordViewAdapter.addNewData(prepaidPhoneRecordsBean.getResult().getList());
        } else {
            mRefreshLayout.endLoadingMore();
            rechargeRecordViewAdapter.addMoreData(prepaidPhoneRecordsBean.getResult().getList());
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
    public void setPresenter(PrepaidPhoneRecordsContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        rechargeRecordViewAdapter.clear();
        rechargeRecordViewAdapter = null;
    }
}
