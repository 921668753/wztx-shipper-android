package com.ruitukeji.zwbh.main.message;


import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.kymjs.common.PreferenceHelper;
import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.adapter.MessageCenterViewAdapter;
import com.ruitukeji.zwbh.adapter.main.message.SystemMessageViewAdapter;
import com.ruitukeji.zwbh.common.BaseActivity;
import com.ruitukeji.zwbh.common.BindView;
import com.ruitukeji.zwbh.common.KJActivityStack;
import com.ruitukeji.zwbh.common.ViewInject;
import com.ruitukeji.zwbh.constant.NumericConstants;
import com.ruitukeji.zwbh.constant.StringConstants;
import com.ruitukeji.zwbh.entity.MessageBean;
import com.ruitukeji.zwbh.entity.MessageCenterBean;
import com.ruitukeji.zwbh.loginregister.LoginActivity;
import com.ruitukeji.zwbh.utils.ActivityTitleUtils;
import com.ruitukeji.zwbh.utils.JsonUtil;

import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.bingoogolapple.titlebar.BGATitleBar;

/**
 * 系统消息
 * Created by Administrator on 2017/12/12.
 */

public class SystemMessageActivity extends BaseActivity implements MessageContract.View, AdapterView.OnItemClickListener, BGARefreshLayout.BGARefreshLayoutDelegate {

    @BindView(id = R.id.titlebar)
    private BGATitleBar titlebar;

    /**
     * 系统消息
     */
    @BindView(id = R.id.ll_systemMessage, click = true)
    private TextView ll_systemMessage;
    @BindView(id = R.id.tv_systemMessage)
    private TextView tv_systemMessage;
    @BindView(id = R.id.tv_systemMessage1)
    private TextView tv_systemMessage1;

    /**
     * 订单消息
     */
    @BindView(id = R.id.ll_orderMessage, click = true)
    private TextView ll_orderMessage;
    @BindView(id = R.id.tv_orderMessage)
    private TextView tv_orderMessage;
    @BindView(id = R.id.tv_orderMessage1)
    private TextView tv_orderMessage1;


    @BindView(id = R.id.mRefreshLayout)
    private BGARefreshLayout mRefreshLayout;


    @BindView(id = R.id.lv_messagecenter)
    private ListView lv_messagecenter;

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

    private String push_type = "system";

    private boolean isEdit = true;

    private SystemMessageViewAdapter mAdapter;

    @Override
    public void setRootView() {
        setContentView(R.layout.activity_systemmessage);
    }


    @Override
    public void initData() {
        super.initData();
        mPresenter = new MessagePresenter(this);
        mAdapter = new SystemMessageViewAdapter(this);
        tv_orderMessage.setTextColor(getResources().getColor(R.color.typecolors));
        tv_orderMessage1.setBackgroundResource(R.color.white);
    }

    @Override
    public void initWidget() {
        super.initWidget();
        BGATitleBar.SimpleDelegate simpleDelegate = new BGATitleBar.SimpleDelegate() {
            @Override
            public void onClickLeftCtv() {
                super.onClickLeftCtv();
                KJActivityStack.create().finishActivity(OrderMessageActivity.class);
                aty.finish();
            }

            @Override
            public void onClickRightCtv() {
                super.onClickRightCtv();
                if (isEdit) {
                    isEdit = false;
                    titlebar.setRightText(getString(R.string.complete));
                } else {
                    isEdit = true;
                    titlebar.setRightText(getString(R.string.edit));
                }

//                Intent intent = new Intent(aty, AboutUsActivity.class);
//                intent.putExtra("type", "type");
//                showActivity(aty, intent);
            }
        };
        ActivityTitleUtils.initToolbar(aty, getString(R.string.message), getString(R.string.edit), R.id.titlebar, simpleDelegate);
    }

    @Override
    public void widgetClick(View v) {
        super.widgetClick(v);
        switch (v.getId()) {
            case R.id.ll_orderMessage:
                showActivity(aty, OrderMessageActivity.class);
                overridePendingTransition(0, 0);
                break;
            case R.id.tv_hintText:
                mRefreshLayout.beginRefreshing();
                break;
        }
    }


    @Override
    public void setPresenter(MessageContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void getSuccess(String s, int flag) {
        if (flag == 0) {
            isShowLoadingMore = true;
            ll_commonError.setVisibility(View.GONE);
            mRefreshLayout.setVisibility(View.VISIBLE);
            MessageBean messageBean = (MessageBean) JsonUtil.getInstance().json2Obj(s, MessageBean.class);
            mMorePageNumber = messageBean.getResult().getPage();
            totalPageNumber = messageBean.getResult().getPageTotal();
            if (messageBean.getResult().getList() == null || messageBean.getResult().getList().size() == 0) {
                error(getString(R.string.serverReturnsDataNull), 0);
                return;
            }
            if (mMorePageNumber == NumericConstants.START_PAGE_NUMBER) {
                mRefreshLayout.endRefreshing();
                mAdapter.clear();
                mAdapter.addNewData(messageBean.getResult().getList());
                mRefreshLayout.endRefreshing();
            } else {
                mRefreshLayout.endLoadingMore();
                mAdapter.addMoreData(messageBean.getResult().getList());
                mRefreshLayout.endLoadingMore();
            }
            dismissLoadingDialog();
        } else if (flag == 1) {
            mRefreshLayout.beginRefreshing();
        }
    }

    @Override
    public void error(String msg, int flag) {
        if (flag == 0) {
            mRefreshLayout.setVisibility(View.GONE);
            ll_commonError.setVisibility(View.VISIBLE);
            tv_hintText.setText(msg + getString(R.string.clickRefresh));
        }
        dismissLoadingDialog();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent();
        intent.putExtra("messageId", mAdapter.getItem(position).getId());
        intent.setClass(getApplicationContext(), MessageDetailsActivity.class);
        showActivity(aty, intent);
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        mMorePageNumber = NumericConstants.START_PAGE_NUMBER;
        mRefreshLayout.endRefreshing();
        showLoadingDialog(getString(R.string.dataLoad));
        ((MessageContract.Presenter) mPresenter).getMessage(push_type, mMorePageNumber);
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
        ((MessageContract.Presenter) mPresenter).getMessage(push_type, mMorePageNumber);
        return true;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAdapter.clear();
        mAdapter = null;
    }
}
