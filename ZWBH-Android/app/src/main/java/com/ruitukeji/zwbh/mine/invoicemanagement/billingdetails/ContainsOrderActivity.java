package com.ruitukeji.zwbh.mine.invoicemanagement.billingdetails;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.adapter.mine.invoicemanagement.ContainsOrderViewAdapter;
import com.ruitukeji.zwbh.common.BaseActivity;
import com.ruitukeji.zwbh.common.BindView;
import com.ruitukeji.zwbh.entity.mine.invoicemanagement.ContainsOrderBean;
import com.ruitukeji.zwbh.utils.ActivityTitleUtils;
import com.ruitukeji.zwbh.utils.JsonUtil;

/**
 * 所含订单
 * Created by Administrator on 2017/12/16.
 */

public class ContainsOrderActivity extends BaseActivity implements ContainsOrderContract.View, AdapterView.OnItemClickListener {

    private ContainsOrderViewAdapter mAdapter;

    @BindView(id = R.id.lv_applicationInvoice)
    private ListView lv_applicationInvoice;

    /**
     * 错误提示页
     */
    @BindView(id = R.id.ll_commonError)
    private LinearLayout ll_commonError;
    @BindView(id = R.id.tv_hintText, click = true)
    private TextView tv_hintText;
    private String g_id = "";


    @Override
    public void setRootView() {
        setContentView(R.layout.activity_containsorder);
    }

    @Override
    public void initData() {
        super.initData();
        mPresenter = new ContainsOrderPresenter(this);
        mAdapter = new ContainsOrderViewAdapter(this);
        g_id = getIntent().getStringExtra("g_id");
        showLoadingDialog(getString(R.string.dataLoad));
        ((ContainsOrderContract.Presenter) mPresenter).getContainsOrder(g_id);
    }

    @Override
    public void initWidget() {
        super.initWidget();
        ActivityTitleUtils.initToolbar(aty, getString(R.string.containsOrder), true, R.id.titlebar);
        lv_applicationInvoice.setAdapter(mAdapter);
        lv_applicationInvoice.setOnItemClickListener(this);

    }

    /**
     * 控件监听事件
     */
    @Override
    public void widgetClick(View v) {
        super.widgetClick(v);
        switch (v.getId()) {
            case R.id.tv_hintText:
                showLoadingDialog(getString(R.string.dataLoad));
                ((ContainsOrderContract.Presenter) mPresenter).getContainsOrder(g_id);
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void setPresenter(ContainsOrderContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void getSuccess(String success, int flag) {
        ll_commonError.setVisibility(View.GONE);
        ContainsOrderBean containsOrderBean = (ContainsOrderBean) JsonUtil.json2Obj(success, ContainsOrderBean.class);
        if (containsOrderBean.getResult() == null || containsOrderBean.getResult().size() == 0) {
            errorMsg(getString(R.string.serverReturnsDataNull), 0);
            return;
        }
        mAdapter.clear();
        mAdapter.addNewData(containsOrderBean.getResult());
        dismissLoadingDialog();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void errorMsg(String msg, int flag) {
        toLigon(msg);
        ll_commonError.setVisibility(View.VISIBLE);
        tv_hintText.setText(msg + getString(R.string.clickRefresh));
        dismissLoadingDialog();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mAdapter.clear();
        mAdapter = null;
    }


}
