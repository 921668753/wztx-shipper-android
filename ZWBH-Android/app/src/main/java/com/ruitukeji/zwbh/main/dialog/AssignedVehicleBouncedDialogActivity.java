package com.ruitukeji.zwbh.main.dialog;

import android.content.Intent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.common.BaseActivity;
import com.ruitukeji.zwbh.common.BindView;
import com.ruitukeji.zwbh.mine.drivermanagement.DriverManagementActivity;

import java.util.HashMap;

import static com.ruitukeji.zwbh.constant.NumericConstants.REQUEST_CODE_SELECT;

/**
 * 主界面---指派车辆弹框
 * Created by Administrator on 2017/11/28.
 */

public class AssignedVehicleBouncedDialogActivity extends BaseActivity implements AssignedVehicleBouncedContract.View {

    /**
     * 关闭
     */
    @BindView(id = R.id.img_cancel, click = true)
    private ImageView img_cancel;

    /**
     * 车牌号
     */
    @BindView(id = R.id.et_pleaseLicensePlateNumber)
    private EditText et_pleaseLicensePlateNumber;


    /**
     * 查看司机列表
     */
    @BindView(id = R.id.tv_checkDriver, click = true)
    private TextView tv_checkDriver;


    /**
     * 确认指派
     */
    @BindView(id = R.id.tv_confirmAssigned, click = true)
    private TextView tv_confirmAssigned;

    private HashMap<String, Object> httpParams = null;

    @Override
    public void setRootView() {
        setContentView(R.layout.dialog_assignedvehiclebounced);
        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        dialogWindow.setAttributes(lp);
    }


    @Override
    public void initData() {
        super.initData();
        httpParams =(HashMap<String, Object>) getIntent().getSerializableExtra("httpParams");
        mPresenter = new AssignedVehicleBouncedPresenter(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_cancel:
                finish();
                break;
            case R.id.tv_checkDriver:
                Intent intent = new Intent(aty, DriverManagementActivity.class);
                intent.putExtra("chageIcon", 2);
                startActivityForResult(intent, REQUEST_CODE_SELECT);
                break;
            case R.id.tv_confirmAssigned:
                showLoadingDialog(getString(R.string.submissionLoad));
                ((AssignedVehicleBouncedContract.Presenter) mPresenter).getAssignedVehicle(httpParams, et_pleaseLicensePlateNumber.getText().toString().trim());
                break;
        }
    }

    @Override
    public void getSuccess(String s, int flag) {
        dismissLoadingDialog();
        if (flag == 0) {
            Intent intent = new Intent();
            setResult(RESULT_OK, intent);
            // 结束该activity 结束之后，前面的activity才可以处理结果
            finish();
        }

    }

    @Override
    public void errorMsg(String msg, int flag) {
        dismissLoadingDialog();
        toLigon1(msg);
    }

    @Override
    public void setPresenter(AssignedVehicleBouncedContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SELECT && resultCode == RESULT_OK) {
            String licensePlateNumber = data.getStringExtra("licensePlateNumber");
            et_pleaseLicensePlateNumber.setText(licensePlateNumber);
        }
    }
}
