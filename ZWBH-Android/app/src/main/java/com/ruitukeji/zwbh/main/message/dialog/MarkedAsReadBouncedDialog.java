package com.ruitukeji.zwbh.main.message.dialog;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.common.BaseDialog;
import com.ruitukeji.zwbh.common.ViewInject;
import com.ruitukeji.zwbh.constant.NumericConstants;
import com.ruitukeji.zwbh.entity.MessageBean.ResultBean.ListBean;
import com.ruitukeji.zwbh.loginregister.LoginActivity;

/**
 * 消息---标为已读弹框
 * Created by Administrator on 2017/11/28.
 */

public abstract class MarkedAsReadBouncedDialog extends BaseDialog implements View.OnClickListener, MarkedAsReadBouncedContract.View {

    private Context context;
    private TextView tv_cancel;
    private TextView tv_determine;
    private ListBean listBean;
    private MarkedAsReadBouncedContract.Presenter mPresenter;

    public MarkedAsReadBouncedDialog(Context context, ListBean listBean) {
        super(context, R.style.MyDialog);
        this.context = context;
        this.listBean = listBean;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_markedasreadbounced);
        initView();
    }

    private void initView() {
        tv_cancel = (TextView) findViewById(R.id.tv_cancel);
        tv_cancel.setOnClickListener(this);
        tv_determine = (TextView) findViewById(R.id.tv_determine);
        tv_determine.setOnClickListener(this);
        mPresenter = new MarkedAsReadBouncedPresenter(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_cancel:
                dismiss();
                break;
            case R.id.tv_determine:
//                if (listBean.getMind_price() == null || listBean.getMind_price().equals("0.00")) {
//                    mPresenter.getQuoteAdd(listBean.getId(), listBean.getSystem_price(), 1);
//                    break;
//                }
//                mPresenter.getQuoteAdd(listBean.getId(), listBean.getMind_price(), 1);
                break;
        }
    }

    @Override
    public void getSuccess(String s, int flag) {
        if (flag == 0) {
            confirm();
        }
        dismissLoadingDialog();
    }

    @Override
    public void setPresenter(MarkedAsReadBouncedContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void error(String msg) {
        if (msg != null && msg.equals("" + NumericConstants.TOLINGIN)) {
            dismissLoadingDialog();
            Intent intent = new Intent(context, LoginActivity.class);
            context.startActivity(intent);
            return;
        }
        dismissLoadingDialog();
        ViewInject.toast(msg);
    }

    public abstract void confirm();
}
