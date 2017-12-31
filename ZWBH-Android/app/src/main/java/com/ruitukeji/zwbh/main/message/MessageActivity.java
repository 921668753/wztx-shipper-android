package com.ruitukeji.zwbh.main.message;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.kymjs.common.PreferenceHelper;
import com.kymjs.common.StringUtils;
import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.adapter.main.message.MessageViewAdapter;
import com.ruitukeji.zwbh.common.BaseActivity;
import com.ruitukeji.zwbh.common.BindView;
import com.ruitukeji.zwbh.common.ViewInject;
import com.ruitukeji.zwbh.constant.NumericConstants;
import com.ruitukeji.zwbh.constant.StringConstants;
import com.ruitukeji.zwbh.entity.MessageBean;
import com.ruitukeji.zwbh.loginregister.LoginActivity;
import com.ruitukeji.zwbh.utils.ActivityTitleUtils;
import com.ruitukeji.zwbh.utils.DialogUtil;
import com.ruitukeji.zwbh.utils.JsonUtil;
import com.ruitukeji.zwbh.utils.RefreshLayoutUtil;

import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * 消息列表
 * Created by Administrator on 2017/2/15.
 */

public class MessageActivity extends BaseActivity implements SystemMessageContract.View, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener, BGARefreshLayout.BGARefreshLayoutDelegate {


    @BindView(id = R.id.mRefreshLayout)
    private BGARefreshLayout mRefreshLayout;

    private MessageViewAdapter mAdapter;

    @BindView(id = R.id.lv_message)
    private ListView lv_message;

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
    private String push_type = null;

    @Override
    public void setRootView() {
        setContentView(R.layout.activity_message);
    }

    @Override
    public void initData() {
        super.initData();
        mPresenter = new SystemMessagePresenter(this);
        mAdapter = new MessageViewAdapter(this);
    }

    @Override
    public void initWidget() {
        super.initWidget();
        String title = getIntent().getStringExtra("title");
        push_type = getIntent().getStringExtra("push_type");
        if (StringUtils.isEmpty(push_type)) {
            finish();
            return;
        }
        ActivityTitleUtils.initToolbar(aty, title, true, R.id.titlebar);
        RefreshLayoutUtil.initRefreshLayout(mRefreshLayout, this, aty, true);
        lv_message.setAdapter(mAdapter);
        lv_message.setOnItemClickListener(this);
        lv_message.setOnItemLongClickListener(this);
        showLoadingDialog(getString(R.string.dataLoad));
        ((SystemMessageContract.Presenter) mPresenter).getMessage(push_type, mMorePageNumber);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        Intent intent = new Intent();
        intent.putExtra("messageId", mAdapter.getItem(position).getId());
        intent.setClass(getApplicationContext(), SystemMessageDetailsActivity.class);
        showActivity(aty, intent);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        DialogUtil.showAlertDialog(this, R.string.deleteMessage, new DialogUtil.OnDialogSelectListener() {
            @Override
            public void onDialogSelect() {
               // ((SystemMessageContract.Presenter) mPresenter).postDeleteMessage(mAdapter.getItem(position).getId());
            }
        });
        return true;
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        mMorePageNumber = NumericConstants.START_PAGE_NUMBER;
        mRefreshLayout.endRefreshing();
        showLoadingDialog(getString(R.string.dataLoad));
        ((SystemMessageContract.Presenter) mPresenter).getMessage(push_type, mMorePageNumber);
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
        ((SystemMessageContract.Presenter) mPresenter).getMessage(push_type, mMorePageNumber);
        return true;
    }

    @Override
    public void getSuccess(String s, int flag) {
        if (flag == 0) {
            PreferenceHelper.write(this, StringConstants.FILENAME, "isRefreshMessageActivity", false);
            isShowLoadingMore = true;
            ll_commonError.setVisibility(View.GONE);
            mRefreshLayout.setVisibility(View.VISIBLE);
            MessageBean messageBean = (MessageBean) JsonUtil.getInstance().json2Obj(s, MessageBean.class);
            mMorePageNumber = messageBean.getResult().getPage();
            totalPageNumber = messageBean.getResult().getPageTotal();
            if (messageBean.getResult().getList() == null || messageBean.getResult().getList().size() == 0) {
                errorMsg(getString(R.string.serverReturnsDataNull), 0);
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
    public void errorMsg(String msg, int flag) {
        if (msg != null && msg.equals("" + NumericConstants.TOLINGIN)) {
            showActivity(aty, LoginActivity.class);
            return;
        }
        if (flag == 0) {
            isShowLoadingMore = false;
            mRefreshLayout.setVisibility(View.GONE);
            ll_commonError.setVisibility(View.VISIBLE);
            tv_hintText.setText(msg + getString(R.string.clickRefresh));
            if (mMorePageNumber == NumericConstants.START_PAGE_NUMBER) {
                mRefreshLayout.endRefreshing();
            } else {
                mRefreshLayout.endLoadingMore();
            }
        } else if (flag == 1) {
            ViewInject.toast(msg);
        }
        dismissLoadingDialog();

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
    public void setPresenter(SystemMessageContract.Presenter presenter) {
        mPresenter = presenter;
    }


    @Override
    protected void onResume() {
        super.onResume();
        boolean isRefreshMessageActivity = PreferenceHelper.readBoolean(this, StringConstants.FILENAME, "isRefreshMessageActivity", false);
        if (isRefreshMessageActivity) {
            PreferenceHelper.write(this, StringConstants.FILENAME, "isRefreshMessageCenterActivity", true);
            mRefreshLayout.beginRefreshing();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAdapter.clear();
        mAdapter = null;
    }
}
