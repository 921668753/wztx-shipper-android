package com.ruitukeji.zwbh.mine.mypublishedorder.publishedorderfragment;

import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.kymjs.common.PreferenceHelper;
import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.adapter.QuotationListViewAdapter;
import com.ruitukeji.zwbh.common.BaseActivity;
import com.ruitukeji.zwbh.common.BindView;
import com.ruitukeji.zwbh.common.KJActivityStack;
import com.ruitukeji.zwbh.common.ViewInject;
import com.ruitukeji.zwbh.constant.StringConstants;
import com.ruitukeji.zwbh.entity.QuotationListBean;
import com.ruitukeji.zwbh.utils.ActivityTitleUtils;
import com.ruitukeji.zwbh.utils.JsonUtil;
import com.ruitukeji.zwbh.utils.RefreshLayoutUtil;

import java.util.List;

import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * 报价列表
 * Created by Administrator on 2017/2/25.
 */

public class QuotationListActivity extends BaseActivity implements QuotationListContract.View, AdapterView.OnItemClickListener, BGARefreshLayout.BGARefreshLayoutDelegate {


    private QuotationListViewAdapter quotationListViewAdapter;

    @BindView(id = R.id.lv_quotationlist)
    private ListView lv_quotationlist;

    @BindView(id = R.id.mRefreshLayout)
    private BGARefreshLayout mRefreshLayout;

    @BindView(id = R.id.ll_bottombar)
    private LinearLayout ll_bottombar;

    @BindView(id = R.id.tv_nextType, click = true)
    private TextView tv_nextType;
    /**
     * 错误提示页
     */
    @BindView(id = R.id.ll_commonError)
    private LinearLayout ll_commonError;
    @BindView(id = R.id.tv_hintText, click = true)
    private TextView tv_hintText;
    private int goods_id;
    private List<QuotationListBean.ResultBean.ListBean> quotationList;
    private QuotationListBean.ResultBean.ListBean quotationBean;


    @Override
    public void setRootView() {
        setContentView(R.layout.activity_quotationlist);
    }

    @Override
    public void initData() {
        super.initData();
        mPresenter = new QuotationListPresenter(this);
        quotationListViewAdapter = new QuotationListViewAdapter(this);
    }

    @Override
    public void initWidget() {
        super.initWidget();
        ActivityTitleUtils.initToolbar(aty, getString(R.string.quotationList), true, R.id.titlebar);
        tv_nextType.setText(getString(R.string.submit));
        RefreshLayoutUtil.initRefreshLayout(mRefreshLayout, this, aty, false);
        goods_id = getIntent().getIntExtra("goods_id", 0);
        ((QuotationListContract.Presenter) mPresenter).getQuotationList(goods_id);
        lv_quotationlist.setAdapter(quotationListViewAdapter);
        lv_quotationlist.setOnItemClickListener(this);
    }

    @Override
    public void widgetClick(View v) {
        super.widgetClick(v);
        switch (v.getId()) {
            case R.id.tv_nextType:
                //     showActivity(aty, PaymentActivity.class);
                ((QuotationListContract.Presenter) mPresenter).postQuotation(quotationBean.getId(), goods_id);
                break;
            case R.id.tv_hintText:
                ((QuotationListContract.Presenter) mPresenter).getQuotationList(goods_id);
                break;
        }
    }

    @Override
    public void setPresenter(QuotationListContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void getSuccess(String s, int flag) {
        if (flag == 0) {
            ll_commonError.setVisibility(View.GONE);
            mRefreshLayout.setVisibility(View.VISIBLE);
            ll_bottombar.setVisibility(View.VISIBLE);
            QuotationListBean quotationListBean = (QuotationListBean) JsonUtil.getInstance().json2Obj(s, QuotationListBean.class);
            quotationList = quotationListBean.getResult().getList();
            if (quotationList == null || quotationList.size() == 0) {
                error(getString(R.string.serverReturnsDataNull));
                return;
            }
            mRefreshLayout.endRefreshing();
            refreshList(0);
        } else if (flag == 1) {
            PreferenceHelper.write(aty, StringConstants.FILENAME, "isRefreshQuoteGoods1", true);
            //  String fragment = PreferenceHelper.readString(aty, StringConstants.FILENAME, "refreshGoodsFragment", "AllOrderFragment");
//            if (fragment.equals("AllOrderFragment")) {
//                PreferenceHelper.write(aty, StringConstants.FILENAME, "isRefreshOrder", true);
//                PreferenceHelper.write(aty, StringConstants.FILENAME, "isRefreshQuoteOrder1", true);
//            } else if (fragment.equals("QuoteOrderFragment")) {
//                PreferenceHelper.write(aty, StringConstants.FILENAME, "isRefreshQuoteOrder", true);
//                PreferenceHelper.write(aty, StringConstants.FILENAME, "isRefreshOrder1", true);
//            }
            ViewInject.toast(getString(R.string.submittedSuccessfully));
            KJActivityStack.create().finishActivity(GoodsDetailsActivity.class);
            finish();
        }
        dismissLoadingDialog();
    }

    private void refreshList(int position) {
        quotationListViewAdapter.clear();
        for (int i = 0; i < quotationList.size(); i++) {
            QuotationListBean.ResultBean.ListBean bean = quotationList.get(i);
            if (position == i) {
                quotationBean = quotationList.get(i);
                bean.setIs_selected(true);
            } else {
                bean.setIs_selected(false);
            }
        }
        quotationListViewAdapter.addNewData(quotationList);
    }


    @Override
    public void error(String msg) {
        mRefreshLayout.setVisibility(View.GONE);
        ll_bottombar.setVisibility(View.GONE);
        ll_commonError.setVisibility(View.VISIBLE);
        tv_hintText.setText(msg + getString(R.string.clickRefresh));
        dismissLoadingDialog();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        quotationBean = quotationListViewAdapter.getItem(position);
        refreshList(position);
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        mRefreshLayout.endRefreshing();
        ((QuotationListContract.Presenter) mPresenter).getQuotationList(goods_id);
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        mRefreshLayout.endLoadingMore();
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        quotationListViewAdapter.clear();
        quotationListViewAdapter = null;
    }
}
