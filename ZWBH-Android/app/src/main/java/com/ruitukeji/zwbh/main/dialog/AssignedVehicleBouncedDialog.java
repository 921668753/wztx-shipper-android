package com.ruitukeji.zwbh.main.dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.common.BaseDialog;
import com.ruitukeji.zwbh.common.ViewInject;

/**
 * 主界面---指派车辆弹框
 * Created by Administrator on 2017/11/28.
 */

public abstract class AssignedVehicleBouncedDialog extends BaseDialog implements View.OnClickListener, AssignedVehicleBouncedContract.View {

    private Context context;
    private ImageView img_cancel;
    private TextView tv_confirmAssigned;
    private AssignedVehicleBouncedContract.Presenter mPresenter;
    private EditText et_pleaseLicensePlateNumber;

    public AssignedVehicleBouncedDialog(Context context) {
        super(context, R.style.MyDialog);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_assignedvehiclebounced);
        initView();
    }

    private void initView() {
        img_cancel = (ImageView) findViewById(R.id.img_cancel);
        img_cancel.setOnClickListener(this);
        et_pleaseLicensePlateNumber = (EditText) findViewById(R.id.et_pleaseLicensePlateNumber);
        tv_confirmAssigned = (TextView) findViewById(R.id.tv_confirmAssigned);
        tv_confirmAssigned.setOnClickListener(this);
        mPresenter = new AssignedVehicleBouncedPresenter(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_cancel:
                dismiss();
                break;
            case R.id.tv_confirmAssigned:
                mPresenter.getAssignedVehicle(et_pleaseLicensePlateNumber.getText().toString().trim());
                break;
        }
    }

    @Override
    public void getSuccess(String s, int flag) {
        if (flag == 0) {
            confirm(s);
        }
        dismissLoadingDialog();
    }

    @Override
    public void setPresenter(AssignedVehicleBouncedContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void error(String msg) {
//        if (msg != null && msg.equals("" + NumericConstants.TOLINGIN)) {
//            dismissLoadingDialog();
//            Intent intent = new Intent(context, LoginActivity.class);
//            context.startActivity(intent);
//            return;
//        }
        dismissLoadingDialog();
        ViewInject.toast(msg);
    }

    public abstract void confirm(String pleaseLicensePlateNumber);
}
