package com.ruitukeji.zwbh.mine.myorder.orderfragment;

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
import com.ruitukeji.zwbh.adapter.OrderViewAdapter;
import com.ruitukeji.zwbh.common.BaseFragment;
import com.ruitukeji.zwbh.common.BindView;
import com.ruitukeji.zwbh.common.ViewInject;
import com.ruitukeji.zwbh.constant.NumericConstants;
import com.ruitukeji.zwbh.constant.StringConstants;
import com.ruitukeji.zwbh.entity.OrderBean;
import com.ruitukeji.zwbh.mine.myorder.MyOrderActivity;
import com.ruitukeji.zwbh.mine.mypublishedorder.publishedorderfragment.QuotationListActivity;
import com.ruitukeji.zwbh.utils.JsonUtil;
import com.ruitukeji.zwbh.utils.RefreshLayoutUtil;


import cn.bingoogolapple.baseadapter.BGAOnItemChildClickListener;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * 全部
 * Created by Administrator on 2017/2/12.
 */

public class AllFragment extends BaseFragment implements OrderContract.View, AdapterView.OnItemClickListener, BGARefreshLayout.BGARefreshLayoutDelegate, BGAOnItemChildClickListener {

    @BindView(id = R.id.mRefreshLayout)
    private BGARefreshLayout mRefreshLayout;

    private OrderViewAdapter mAdapter;

    private MyOrderActivity aty;

    @BindView(id = R.id.lv_order)
    private ListView lv_order;

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
    private String type = "all";

    @Override
    protected View inflaterView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        aty = (MyOrderActivity) getActivity();
        return View.inflate(aty, R.layout.fragment_allorder, null);
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter = new OrderPresenter(this);
        mAdapter = new OrderViewAdapter(getActivity());
    }

    @Override
    protected void initWidget(View parentView) {
        super.initWidget(parentView);
        RefreshLayoutUtil.initRefreshLayout(mRefreshLayout, this, getActivity(), true);
        lv_order.setAdapter(mAdapter);
        lv_order.setOnItemClickListener(this);
        mAdapter.setOnItemChildClickListener(this);
        showLoadingDialog(getString(R.string.dataLoad));
        ((OrderContract.Presenter) mPresenter).getOrder(mMorePageNumber, type);
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        PreferenceHelper.write(aty, StringConstants.FILENAME, "refreshOrderFragment", "AllOrderFragment");
        Intent intent = new Intent(aty, OrderDetailsActivity.class);
        intent.putExtra("order_id", mAdapter.getItem(i).getOrder_id());
        if (mAdapter.getItem(i).getStatus() != null && mAdapter.getItem(i).getStatus().equals("quote")) {
            intent.putExtra("goods_id", mAdapter.getItem(i).getGoods_id());
        }
        aty.showActivity(aty, intent);
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        mMorePageNumber = NumericConstants.START_PAGE_NUMBER;
        mRefreshLayout.endRefreshing();
        showLoadingDialog(getString(R.string.dataLoad));
        ((OrderContract.Presenter) mPresenter).getOrder(mMorePageNumber, type);
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
        ((OrderContract.Presenter) mPresenter).getOrder(mMorePageNumber, type);
        return true;
    }


    @Override
    public void onResume() {
        super.onResume();
        boolean isRefreshOrder = PreferenceHelper.readBoolean(aty, StringConstants.FILENAME, "isRefreshOrder", false);
        if (isRefreshOrder) {
            mRefreshLayout.beginRefreshing();
        }
    }

    @Override
    public void getSuccess(String s) {
        PreferenceHelper.write(aty, StringConstants.FILENAME, "isRefreshOrder", false);
        PreferenceHelper.write(aty, StringConstants.FILENAME, "isRefreshAllOrder1", false);
        isShowLoadingMore = true;
        ll_commonError.setVisibility(View.GONE);
        mRefreshLayout.setVisibility(View.VISIBLE);
        OrderBean orderBean = (OrderBean) JsonUtil.getInstance().json2Obj(s, OrderBean.class);
        mMorePageNumber = orderBean.getResult().getPage();
        totalPageNumber = orderBean.getResult().getPageTotal();
        if (orderBean.getResult().getList() == null || orderBean.getResult().getList().size() == 0) {
            error(getString(R.string.serverReturnsDataNull));
            return;
        }
        if (mMorePageNumber == NumericConstants.START_PAGE_NUMBER) {
            mRefreshLayout.endRefreshing();
            mAdapter.clear();
            mAdapter.addNewData(orderBean.getResult().getList());
        } else if (mMorePageNumber > NumericConstants.START_PAGE_NUMBER) {
            mRefreshLayout.endLoadingMore();
            mAdapter.addMoreData(orderBean.getResult().getList());
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
    public void setPresenter(OrderContract.Presenter presenter) {
        mPresenter = presenter;
    }

    /**
     * 当通过changeFragment()显示时会被调用(类似于onResume)
     */
    @Override
    public void onChange() {
        super.onChange();
        boolean isRefreshAllOrder1 = PreferenceHelper.readBoolean(aty, StringConstants.FILENAME, "isRefreshAllOrder1", false);
        if (isRefreshAllOrder1) {
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
        if (view.getId() == R.id.tv_driverQuotation) {
            Intent intent = new Intent(aty, QuotationListActivity.class);
            intent.putExtra("order_id", mAdapter.getItem(i).getOrder_id());
            intent.putExtra("goods_id", mAdapter.getItem(i).getGoods_id());
            aty.showActivity(aty, intent);
        }
    }
}
