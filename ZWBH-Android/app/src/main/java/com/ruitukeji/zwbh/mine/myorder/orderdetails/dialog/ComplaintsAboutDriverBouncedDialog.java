package com.ruitukeji.zwbh.mine.myorder.orderdetails.dialog;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.common.BaseDialog;
import com.ruitukeji.zwbh.common.ViewInject;
import com.ruitukeji.zwbh.constant.NumericConstants;
import com.ruitukeji.zwbh.loginregister.LoginActivity;

/**
 * 订单详情---投诉司机弹框
 * Created by Administrator on 2017/11/28.
 */

public class ComplaintsAboutDriverBouncedDialog extends BaseDialog implements View.OnClickListener, ComplaintsAboutDriverBouncedContract.View {

    private Context context;
    private int dr_id = 0;
    private ComplaintsAboutDriverBouncedContract.Presenter mPresenter;
    private EditText et_describeSpecificReasons;

    public ComplaintsAboutDriverBouncedDialog(Context context, int dr_id) {
        super(context, R.style.dialog);
        this.context = context;
        this.dr_id = dr_id;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_complaintsaboutdriverbounced);
        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        dialogWindow.setAttributes(lp);
        initView();
    }

    private void initView() {
        ImageView img_cancel = (ImageView) findViewById(R.id.img_cancel);
        img_cancel.setOnClickListener(this);
        TextView tv_submit = (TextView) findViewById(R.id.tv_submit);
        tv_submit.setOnClickListener(this);
        et_describeSpecificReasons = (EditText) findViewById(R.id.et_describeSpecificReasons);
        mPresenter = new ComplaintsAboutDriverBouncedPresenter(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_cancel:
                cancel();
                break;
            case R.id.tv_submit:
                showLoadingDialog(context.getString(R.string.sendingLoad));
                mPresenter.postComplaintsAboutDriver(dr_id, et_describeSpecificReasons.getText().toString().trim());
                break;
        }
    }

    @Override
    public void getSuccess(String s, int flag) {
        cancel();
        dismissLoadingDialog();
    }

    @Override
    public void setPresenter(ComplaintsAboutDriverBouncedContract.Presenter presenter) {
        mPresenter = presenter;
    }


    public void setDriverId(int dr_id) {
        this.dr_id = dr_id;
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
}
