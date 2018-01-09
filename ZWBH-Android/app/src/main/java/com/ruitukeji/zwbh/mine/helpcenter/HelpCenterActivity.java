package com.ruitukeji.zwbh.mine.helpcenter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.adapter.mine.helpcenter.HelpCenterViewAdapter;
import com.ruitukeji.zwbh.common.BaseActivity;
import com.ruitukeji.zwbh.common.BindView;
import com.ruitukeji.zwbh.common.ViewInject;
import com.ruitukeji.zwbh.constant.NumericConstants;
import com.ruitukeji.zwbh.entity.mine.invitefriends.RecommendedRecordBean;
import com.ruitukeji.zwbh.utils.ActivityTitleUtils;
import com.ruitukeji.zwbh.utils.JsonUtil;
import com.ruitukeji.zwbh.utils.RefreshLayoutUtil;

import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * 帮助中心
 * Created by Administrator on 2017/12/12.
 */

public class HelpCenterActivity extends BaseActivity implements HelpCenterContract.View, AdapterView.OnItemClickListener, BGARefreshLayout.BGARefreshLayoutDelegate {


    @BindView(id = R.id.mRefreshLayout)
    private BGARefreshLayout mRefreshLayout;

    private HelpCenterViewAdapter mAdapter;

    @BindView(id = R.id.lv_helpCenter)
    private ListView lv_helpCenter;

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
        setContentView(R.layout.activity_helpcenter);
    }

    @Override
    public void initData() {
        super.initData();
        mPresenter = new HelpCenterPresenter(this);
        mAdapter = new HelpCenterViewAdapter(this);
    }

    @Override
    public void initWidget() {
        super.initWidget();
        ActivityTitleUtils.initToolbar(aty, getString(R.string.helpCenter), true, R.id.titlebar);
        RefreshLayoutUtil.initRefreshLayout(mRefreshLayout, this, aty, true);
        lv_helpCenter.setAdapter(mAdapter);
        lv_helpCenter.setOnItemClickListener(this);
        mRefreshLayout.beginRefreshing();
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        mMorePageNumber = NumericConstants.START_PAGE_NUMBER;
        mRefreshLayout.endRefreshing();
        showLoadingDialog(getString(R.string.dataLoad));
        ((HelpCenterContract.Presenter) mPresenter).getHelpCenter(mMorePageNumber);
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
        ((HelpCenterContract.Presenter) mPresenter).getHelpCenter(mMorePageNumber);
        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(aty, HelpCenterDetailsActivity.class);
        intent.putExtra("id", 0);
        showActivity(aty, intent);
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
        RecommendedRecordBean recommendedRecordBean = (RecommendedRecordBean) JsonUtil.getInstance().json2Obj(s, RecommendedRecordBean.class);
        mMorePageNumber = recommendedRecordBean.getResult().getPage();
        totalPageNumber = recommendedRecordBean.getResult().getPageTotal();
        if (recommendedRecordBean.getResult().getList() == null || recommendedRecordBean.getResult().getList().size() == 0) {
            error(getString(R.string.serverReturnsDataNull));
            return;
        }
        if (mMorePageNumber == NumericConstants.START_PAGE_NUMBER) {
            mRefreshLayout.endRefreshing();
            mAdapter.clear();
            mAdapter.addNewData(recommendedRecordBean.getResult().getList());
        } else {
            mRefreshLayout.endLoadingMore();
            mAdapter.addMoreData(recommendedRecordBean.getResult().getList());
        }
        dismissLoadingDialog();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void error(String msg) {
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
    public void setPresenter(HelpCenterContract.Presenter presenter) {
        mPresenter = presenter;
    }
}
