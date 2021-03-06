package com.ruitukeji.zwbh.mine.drivermanagement.fragment;

import android.annotation.SuppressLint;
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
import com.ruitukeji.zwbh.adapter.mine.drivermanagement.DriverManagementViewAdapter;
import com.ruitukeji.zwbh.common.BaseFragment;
import com.ruitukeji.zwbh.common.BindView;
import com.ruitukeji.zwbh.common.ViewInject;
import com.ruitukeji.zwbh.constant.NumericConstants;
import com.ruitukeji.zwbh.constant.StringConstants;
import com.ruitukeji.zwbh.entity.mine.drivermanagement.DriverManagementBean;
import com.ruitukeji.zwbh.mine.drivermanagement.DriverManagementActivity;
import com.ruitukeji.zwbh.utils.JsonUtil;
import com.ruitukeji.zwbh.utils.RefreshLayoutUtil;

import cn.bingoogolapple.baseadapter.BGAOnItemChildClickListener;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * 黑名单
 * Created by Administrator on 2017/12/15.
 */

public class BlacklistFragment extends BaseFragment implements DriverManagementContract.View, AdapterView.OnItemClickListener, BGARefreshLayout.BGARefreshLayoutDelegate, BGAOnItemChildClickListener {

    private DriverManagementActivity aty;


    @BindView(id = R.id.mRefreshLayout)
    private BGARefreshLayout mRefreshLayout;

    private DriverManagementViewAdapter mAdapter;

    @BindView(id = R.id.lv_driver)
    private ListView lv_driver;

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
     * 订单状态（all全部状态，quote报价中，quoted已报价，待发货 distribute配送中（在配送-未拍照）发货中 photo 拍照完毕（订单已完成））
     */
    private String type = "1";

    @Override
    protected View inflaterView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        aty = (DriverManagementActivity) getActivity();
        return View.inflate(aty, R.layout.fragment_driver, null);
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter = new DriverManagementPresenter(this);
        mAdapter = new DriverManagementViewAdapter(getActivity());
    }

    @Override
    protected void initWidget(View parentView) {
        super.initWidget(parentView);
        RefreshLayoutUtil.initRefreshLayout(mRefreshLayout, this, getActivity(), true);
        lv_driver.setAdapter(mAdapter);
        lv_driver.setOnItemClickListener(this);
        mAdapter.setOnItemChildClickListener(this);
        mRefreshLayout.beginRefreshing();
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
        ((DriverManagementContract.Presenter) mPresenter).getDriverList(mMorePageNumber, type);
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
        ((DriverManagementContract.Presenter) mPresenter).getDriverList(mMorePageNumber, type);
        return true;
    }


    @Override
    public void setPresenter(DriverManagementContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void getSuccess(String success, int flag) {
        if (flag == 0) {
            isShowLoadingMore = true;
            PreferenceHelper.write(aty, StringConstants.FILENAME, "isDriverManagement", false);
            ll_commonError.setVisibility(View.GONE);
            mRefreshLayout.setVisibility(View.VISIBLE);
            DriverManagementBean driverManagementBean = (DriverManagementBean) JsonUtil.getInstance().json2Obj(success, DriverManagementBean.class);
            mMorePageNumber = driverManagementBean.getResult().getPage();
            totalPageNumber = driverManagementBean.getResult().getPageTotal();
            if (driverManagementBean.getResult().getList() == null || driverManagementBean.getResult().getList().size() == 0) {
                errorMsg(getString(R.string.serverReturnsDataNull), 0);
                return;
            }
            if (mMorePageNumber == NumericConstants.START_PAGE_NUMBER) {
                mRefreshLayout.endRefreshing();
                mAdapter.clear();
                mAdapter.addNewData(driverManagementBean.getResult().getList());
            } else if (mMorePageNumber > NumericConstants.START_PAGE_NUMBER) {
                mRefreshLayout.endLoadingMore();
                mAdapter.addMoreData(driverManagementBean.getResult().getList());
            }
            dismissLoadingDialog();
        } else if (flag == 1 || flag == 2) {
            if (flag == 1) {
                PreferenceHelper.write(aty, StringConstants.FILENAME, "isDriverManagement", true);
            }
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

    /**
     * 当通过changeFragment()显示时会被调用(类似于onResume)
     */
    @Override
    public void onChange() {
        super.onChange();
        boolean isDriverManagement = PreferenceHelper.readBoolean(aty, StringConstants.FILENAME, "isDriverManagement", false);
        if (isDriverManagement) {
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
        if (view.getId() == R.id.ll_joinBlacklist) {
            ((DriverManagementContract.Presenter) mPresenter).postDriverBack(mAdapter.getItem(i).getId(), 0);
        } else if (view.getId() == R.id.ll_delete) {
            ((DriverManagementContract.Presenter) mPresenter).postDeleteDriver(mAdapter.getItem(i).getId());
        }
    }
}

