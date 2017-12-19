package com.ruitukeji.zwbh.mine.invoicemanagement.billingdetails;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.adapter.mine.invoicemanagement.ContainsOrderViewAdapter;
import com.ruitukeji.zwbh.common.BaseActivity;
import com.ruitukeji.zwbh.common.BindView;
import com.ruitukeji.zwbh.entity.ConductorModelsBean;
import com.ruitukeji.zwbh.utils.ActivityTitleUtils;
import com.ruitukeji.zwbh.utils.JsonUtil;

/**
 * 所含订单
 * Created by Administrator on 2017/12/16.
 */

public class ContainsOrderActivity extends BaseActivity implements ContainsOrderContract.View {

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


    @Override
    public void setRootView() {
        setContentView(R.layout.activity_containsorder);
    }

    @Override
    public void initData() {
        super.initData();
        mPresenter = new ContainsOrderPresenter(this);
        mAdapter = new ContainsOrderViewAdapter(this);
    }

    @Override
    public void initWidget() {
        super.initWidget();
        ActivityTitleUtils.initToolbar(aty, getString(R.string.containsOrder), true, R.id.titlebar);
        lv_applicationInvoice.setAdapter(mAdapter);
//        lv_applicationInvoice.setOnItemClickListener(this);
        showLoadingDialog(getString(R.string.dataLoad));
        ((ContainsOrderContract.Presenter) mPresenter).getContainsOrder();
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
                ((ContainsOrderContract.Presenter) mPresenter).getContainsOrder();
                break;
        }
    }


    @Override
    public void setPresenter(ContainsOrderContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void getSuccess(String success, int flag) {
        ll_commonError.setVisibility(View.GONE);
        ConductorModelsBean recommendedRecordBean = (ConductorModelsBean) JsonUtil.json2Obj(success, ConductorModelsBean.class);
        assert recommendedRecordBean != null;
        if (recommendedRecordBean.getResult().getLength() == null || recommendedRecordBean.getResult().getLength().size() == 0) {
            errorMsg(getString(R.string.serverReturnsDataNull), 0);
            return;
        }
        mAdapter.clear();
        mAdapter.addNewData(recommendedRecordBean.getResult().getLength());
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
