package com.ruitukeji.zwbh.mine.myorder.dialog;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.common.BaseDialog;
import com.ruitukeji.zwbh.common.ViewInject;
import com.ruitukeji.zwbh.constant.NumericConstants;
import com.ruitukeji.zwbh.loginregister.LoginActivity;

/**
 * 订单--再次发布弹框
 * Created by Administrator on 2018/3/8.
 */

public abstract class ReleaseAgainOrderBouncedDialog extends BaseDialog implements View.OnClickListener, ReleaseAgainOrderBouncedContract.View {

    private int orderId = 0;
    private Context context;

    private ReleaseAgainOrderBouncedContract.Presenter mPresenter;

    public ReleaseAgainOrderBouncedDialog(Context context, int orderId) {
        super(context, R.style.dialog);
        this.context = context;
        this.orderId = orderId;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_markedasreadbounced);
        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        dialogWindow.setAttributes(lp);
        initView();
    }

    private void initView() {
        mPresenter = new ReleaseAgainOrderBouncedPresenter(this);
        TextView tv_determine = (TextView) findViewById(R.id.tv_determine);
        tv_determine.setOnClickListener(this);
        TextView tv_content = (TextView) findViewById(R.id.tv_content);
        tv_content.setText(context.getString(R.string.releaseAgainOrder));
        TextView tv_cancel = (TextView) findViewById(R.id.tv_cancel);
        tv_cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_cancel:
                cancel();
                break;
            case R.id.tv_determine:
                showLoadingDialog(context.getString(R.string.submissionLoad));
                mPresenter.postReleaseAgain(orderId);
                break;
        }
    }

    @Override
    public void setPresenter(ReleaseAgainOrderBouncedContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void getSuccess(String s, int flag) {
        confirm();
        dismissLoadingDialog();
    }

    @Override
    public void errorMsg(String msg, int flag) {
        if (msg != null && msg.equals("" + NumericConstants.TOLINGIN)) {
            dismissLoadingDialog();
            Intent intent = new Intent(context, LoginActivity.class);
            context.startActivity(intent);
            return;
        }
        dismissLoadingDialog();
        ViewInject.toast(msg);
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public abstract void confirm();
}

