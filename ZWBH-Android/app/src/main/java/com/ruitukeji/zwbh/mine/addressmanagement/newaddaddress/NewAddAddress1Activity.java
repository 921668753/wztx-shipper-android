package com.ruitukeji.zwbh.mine.addressmanagement.newaddaddress;

import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.common.BaseActivity;
import com.ruitukeji.zwbh.common.ViewInject;
import com.ruitukeji.zwbh.constant.NumericConstants;
import com.ruitukeji.zwbh.loginregister.LoginActivity;
import com.ruitukeji.zwbh.utils.ActivityTitleUtils;

/**
 * 新增地址 /修改地址  changeAddress
 * Created by Administrator on 2017/12/12.
 */

public class NewAddAddress1Activity extends BaseActivity implements NewAddAddress1Contract.View {

    private String address_id = "";


    @Override
    public void setRootView() {
        setContentView(R.layout.activity_provenance);
    }

    @Override
    public void initData() {
        super.initData();
        address_id = getIntent().getStringExtra("address_id");
    }

    @Override
    public void initWidget() {
        super.initWidget();
        mPresenter = new NewAddAddress1Presenter(this);
        String title = getIntent().getStringExtra("title");
        ActivityTitleUtils.initToolbar(aty, title, true, R.id.titlebar);
    }

    @Override
    public void setPresenter(NewAddAddress1Contract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void getSuccess(String success, int flag) {
        if (flag == 0) {

        } else if (flag == 1) {

        } else if (flag == 2) {

        }
        dismissLoadingDialog();
    }

    @Override
    public void errorMsg(String msg, int flag) {
        if (msg != null && msg.equals("" + NumericConstants.TOLINGIN)) {
            dismissLoadingDialog();
            skipActivity(aty, LoginActivity.class);
            return;
        }
        dismissLoadingDialog();
        ViewInject.toast(msg);
    }

}
