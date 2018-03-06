package com.ruitukeji.zwbh.mine.myorder.quotationlist;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.adapter.QuotationListViewAdapter;
import com.ruitukeji.zwbh.application.MyApplication;
import com.ruitukeji.zwbh.common.BaseActivity;
import com.ruitukeji.zwbh.common.BindView;
import com.ruitukeji.zwbh.common.ViewInject;
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


    @BindView(id = R.id.ll_submit)
    private LinearLayout ll_submit;


    @BindView(id = R.id.tv_submit, click = true)
    private TextView tv_submit;

    /**
     * 错误提示页
     */
    @BindView(id = R.id.ll_commonError)
    private LinearLayout ll_commonError;

    @BindView(id = R.id.tv_hintText, click = true)
    private TextView tv_hintText;

    private int order_id = 0;

    private List<QuotationListBean.ResultBean> quotationList;

    private QuotationListBean.ResultBean quotationBean;

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
        RefreshLayoutUtil.initRefreshLayout(mRefreshLayout, this, aty, false);
        order_id = getIntent().getIntExtra("order_id", 0);
        ((QuotationListContract.Presenter) mPresenter).getQuotationList(order_id);
        lv_quotationlist.setAdapter(quotationListViewAdapter);
        lv_quotationlist.setOnItemClickListener(this);
    }

    @Override
    public void widgetClick(View v) {
        super.widgetClick(v);
        switch (v.getId()) {
            case R.id.tv_submit:
                showLoadingDialog(MyApplication.getContext().getString(R.string.submissionLoad));
                ((QuotationListContract.Presenter) mPresenter).postQuotation(quotationBean.getId(), order_id);
                break;
            case R.id.tv_hintText:
                mRefreshLayout.beginRefreshing();
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
            ll_submit.setVisibility(View.VISIBLE);
            mRefreshLayout.setVisibility(View.VISIBLE);
            QuotationListBean quotationListBean = (QuotationListBean) JsonUtil.getInstance().json2Obj(s, QuotationListBean.class);
            quotationList = quotationListBean.getResult();
            if (quotationList == null || quotationList.size() == 0) {
                errorMsg(getString(R.string.serverReturnsDataNull), 0);
                return;
            }
            mRefreshLayout.endRefreshing();
            refreshList(0);
        } else if (flag == 1) {
            ViewInject.toast(getString(R.string.submittedSuccessfully));
            Intent intent = new Intent();
            // 设置结果 结果码，一个数据
            setResult(RESULT_OK, intent);
            finish();
        }
        dismissLoadingDialog();
    }

    private void refreshList(int position) {
        quotationListViewAdapter.clear();
        for (int i = 0; i < quotationList.size(); i++) {
            QuotationListBean.ResultBean bean = quotationList.get(i);
            if (position == i) {
                quotationBean = quotationList.get(i);
                bean.setIs_selected(1);
            } else {
                bean.setIs_selected(0);
            }
        }
        quotationListViewAdapter.addNewData(quotationList);
    }


    @Override
    public void errorMsg(String msg, int flag) {
        if (flag == 0) {
            toLigon(msg);
            mRefreshLayout.setVisibility(View.GONE);
            ll_submit.setVisibility(View.GONE);
            ll_commonError.setVisibility(View.VISIBLE);
            tv_hintText.setText(msg + getString(R.string.clickRefresh));
        } else if (flag == 1) {
            toLigon1(msg);
        }

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
        showLoadingDialog(MyApplication.getContext().getString(R.string.dataLoad));
        ((QuotationListContract.Presenter) mPresenter).getQuotationList(order_id);
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
