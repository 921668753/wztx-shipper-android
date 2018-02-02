package com.ruitukeji.zwbh.mine.mypublishedorder.publishedorderfragment;

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
import com.ruitukeji.zwbh.adapter.mine.myorder.OrderViewAdapter;
import com.ruitukeji.zwbh.common.BaseFragment;
import com.ruitukeji.zwbh.common.BindView;
import com.ruitukeji.zwbh.common.ViewInject;
import com.ruitukeji.zwbh.constant.NumericConstants;
import com.ruitukeji.zwbh.constant.StringConstants;
import com.ruitukeji.zwbh.entity.OrderBean;
import com.ruitukeji.zwbh.mine.mypublishedorder.MyPublishedGoodsActivity;
import com.ruitukeji.zwbh.utils.JsonUtil;
import com.ruitukeji.zwbh.utils.RefreshLayoutUtil;

import cn.bingoogolapple.baseadapter.BGAOnItemChildClickListener;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * 待报价
 * Created by Administrator on 2017/2/12.
 */

public class ToQuoteGoodsFragment extends BaseFragment implements GoodsContract.View, AdapterView.OnItemClickListener, BGARefreshLayout.BGARefreshLayoutDelegate, BGAOnItemChildClickListener {

    @BindView(id = R.id.mRefreshLayout)
    private BGARefreshLayout mRefreshLayout;

    private OrderViewAdapter mAdapter;

    private MyPublishedGoodsActivity aty;

    @BindView(id = R.id.lv_order)
    private ListView lv_order;


    /**
     * 错误提示页
     */
    @BindView(id = R.id.ll_commonError, click = true)
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
    private String type = "quote";

    @Override
    protected View inflaterView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        aty = (MyPublishedGoodsActivity) getActivity();
        return View.inflate(aty, R.layout.fragment_allorder, null);
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter = new GoodsPresenter(this);
        mAdapter = new OrderViewAdapter(getActivity());
    }

    @Override
    protected void initWidget(View parentView) {
        super.initWidget(parentView);
        RefreshLayoutUtil.initRefreshLayout(mRefreshLayout, this, getActivity(), true);
        ((GoodsContract.Presenter) mPresenter).getGoodsList(mMorePageNumber, type);
        lv_order.setAdapter(mAdapter);
        lv_order.setOnItemClickListener(this);
        mAdapter.setOnItemChildClickListener(this);
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        PreferenceHelper.write(aty, StringConstants.FILENAME, "refreshOrderFragment", "QuoteOrderFragment");
        Intent intent = new Intent(aty, GoodsDetailsActivity.class);
        intent.putExtra("order_id", mAdapter.getItem(i).getOrder_id());
       // intent.putExtra("goods_id", mAdapter.getItem(i).getGoods_id());
        aty.showActivity(aty, intent);
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        mMorePageNumber = NumericConstants.START_PAGE_NUMBER;
        mRefreshLayout.endRefreshing();
        showLoadingDialog(getString(R.string.dataLoad));
        ((GoodsContract.Presenter) mPresenter).getGoodsList(mMorePageNumber, type);
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
        ((GoodsContract.Presenter) mPresenter).getGoodsList(mMorePageNumber, type);
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
    public void onResume() {
        super.onResume();
        boolean isRefreshQuoteOrder = PreferenceHelper.readBoolean(aty, StringConstants.FILENAME, "isRefreshQuoteOrder", false);
        if (isRefreshQuoteOrder) {
            mRefreshLayout.beginRefreshing();
        }
    }


    @Override
    public void getSuccess(String s, int flag) {
        PreferenceHelper.write(aty, StringConstants.FILENAME, "isRefreshQuoteOrder", false);
        PreferenceHelper.write(aty, StringConstants.FILENAME, "isRefreshQuoteOrder1", false);
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
        } else {
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
    public void setPresenter(GoodsContract.Presenter presenter) {
        mPresenter = presenter;
    }

    /**
     * 当通过changeFragment()显示时会被调用(类似于onResume)
     */
    @Override
    public void onChange() {
        super.onChange();
        boolean isRefreshQuoteOrder1 = PreferenceHelper.readBoolean(aty, StringConstants.FILENAME, "isRefreshQuoteOrder1", false);
        if (isRefreshQuoteOrder1) {
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
        PreferenceHelper.write(aty, StringConstants.FILENAME, "refreshOrderFragment", "QuoteOrderFragment");
        if (view.getId() == R.id.tv_driverQuotation) {
            Intent intent = new Intent(aty, QuotationListActivity.class);
            intent.putExtra("order_id", mAdapter.getItem(i).getOrder_id());
          //  intent.putExtra("goods_id", mAdapter.getItem(i).getGoods_id());
            aty.showActivity(aty, intent);
        }
    }
}

