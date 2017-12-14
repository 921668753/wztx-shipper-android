package com.ruitukeji.zwbh.mine.myorder.orderdetails.dialog;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.common.BaseDialog;
import com.ruitukeji.zwbh.common.ViewInject;
import com.ruitukeji.zwbh.constant.NumericConstants;
import com.ruitukeji.zwbh.entity.MessageBean.ResultBean.ListBean;
import com.ruitukeji.zwbh.loginregister.LoginActivity;

/**
 * 订单详情---投诉司机弹框
 * Created by Administrator on 2017/11/28.
 */

public abstract class ComplaintsAboutDriverBouncedDialog extends BaseDialog implements View.OnClickListener, ComplaintsAboutDriverBouncedContract.View {

    private Context context;
    private ImageView img_cancel;
    private TextView tv_submit;
    private ListBean listBean;
    private ComplaintsAboutDriverBouncedContract.Presenter mPresenter;

    public ComplaintsAboutDriverBouncedDialog(Context context, ListBean listBean) {
        super(context, R.style.MyDialog);
        this.context = context;
        this.listBean = listBean;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_complaintsaboutdriverbounced);
        initView();
    }

    private void initView() {
        img_cancel = (ImageView) findViewById(R.id.img_cancel);
        img_cancel.setOnClickListener(this);
        tv_submit = (TextView) findViewById(R.id.tv_submit);
        tv_submit.setOnClickListener(this);
        mPresenter = new ComplaintsAboutDriverBouncedPresenter(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_cancel:
                dismiss();
                break;
            case R.id.tv_submit:
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
    public void setPresenter(ComplaintsAboutDriverBouncedContract.Presenter presenter) {
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
