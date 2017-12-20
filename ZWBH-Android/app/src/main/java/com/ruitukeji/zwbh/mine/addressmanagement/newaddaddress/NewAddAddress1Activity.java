package com.ruitukeji.zwbh.mine.addressmanagement.newaddaddress;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.kymjs.common.StringUtils;
import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.common.BaseActivity;
import com.ruitukeji.zwbh.common.BindView;
import com.ruitukeji.zwbh.common.ViewInject;
import com.ruitukeji.zwbh.constant.NumericConstants;
import com.ruitukeji.zwbh.loginregister.LoginActivity;
import com.ruitukeji.zwbh.utils.ActivityTitleUtils;

/**
 * 新增地址 /修改地址  changeAddress
 * Created by Administrator on 2017/12/12.
 */

public class NewAddAddress1Activity extends BaseActivity implements NewAddAddress1Contract.View, TextWatcher {

    /**
     * 地址
     */
    @BindView(id = R.id.tv_address, click = true)
    private TextView tv_address;

    /**
     * 详细地址
     */
    @BindView(id = R.id.et_detailedAddress)
    private EditText et_detailedAddress;

    /**
     * 发货客户
     */
    @BindView(id = R.id.tv_deliveryCustomer)
    private TextView tv_deliveryCustomer;
    @BindView(id = R.id.et_deliveryCustomer)
    private EditText et_deliveryCustomer;

    /**
     * 发货人
     */
    @BindView(id = R.id.tv_shipper)
    private TextView tv_shipper;
    @BindView(id = R.id.et_shipper)
    private EditText et_shipper;

    /**
     * 手机号
     */
    @BindView(id = R.id.et_phone)
    private EditText et_phone;

    /**
     * 固定电话
     */
    @BindView(id = R.id.et_eixedTelephone)
    private EditText et_eixedTelephone;

    /**
     * 确定
     */
    @BindView(id = R.id.tv_determine, click = true)
    private TextView tv_determine;

    private String lat = "";
    private String longi = "";
    private String district = "";
    private String placeName = "";
    private int type = 0;
    private String title = "";
    private String detailedAddress = "";
    private String deliveryCustomer = "";
    private String shipper = "";
    private String phone = "";
    private String eixedTelephone = "";


    @Override
    public void setRootView() {
        setContentView(R.layout.activity_provenance);
    }

    @Override
    public void initData() {
        super.initData();
        lat = getIntent().getStringExtra("lat");
        longi = getIntent().getStringExtra("longi");
        district = getIntent().getStringExtra("district");
        placeName = getIntent().getStringExtra("placeName");
        type = getIntent().getIntExtra("type", 0);

        detailedAddress = getIntent().getStringExtra("detailedAddress");
        deliveryCustomer = getIntent().getStringExtra("deliveryCustomer");
        shipper = getIntent().getStringExtra("shipper");
        phone = getIntent().getStringExtra("phone");
        eixedTelephone = getIntent().getStringExtra("eixedTelephone");
        et_detailedAddress.addTextChangedListener(this);
        et_deliveryCustomer.addTextChangedListener(this);
        et_shipper.addTextChangedListener(this);
        et_phone.addTextChangedListener(this);
    }

    @Override
    public void initWidget() {
        super.initWidget();
        mPresenter = new NewAddAddress1Presenter(this);
        title = getIntent().getStringExtra("title");
        ActivityTitleUtils.initToolbar(aty, title, true, R.id.titlebar);
        tv_address.setText(district);
        setView();
        if (type == 0) {
            tv_deliveryCustomer.setText(getString(R.string.deliveryCustomer));
            tv_shipper.setText(getString(R.string.shipper));
        } else {
            tv_deliveryCustomer.setText(getString(R.string.receivingCustomer));
            tv_shipper.setText(getString(R.string.consignee));
        }
    }

    /**
     * 设置参数
     */
    public void setView() {

        if (!StringUtils.isEmpty(detailedAddress)) {
            et_detailedAddress.setText(detailedAddress);
        }
        if (!StringUtils.isEmpty(deliveryCustomer)) {
            et_deliveryCustomer.setText(deliveryCustomer);
        }
        if (!StringUtils.isEmpty(shipper)) {
            et_shipper.setText(shipper);
        }
        if (!StringUtils.isEmpty(phone)) {
            et_phone.setText(phone);
        }
        if (!StringUtils.isEmpty(eixedTelephone)) {
            et_eixedTelephone.setText(eixedTelephone);
        }
    }


    @Override
    public void widgetClick(View v) {
        super.widgetClick(v);
        switch (v.getId()) {
            case R.id.tv_address:
                Intent intent = new Intent(aty, NewAddAddressActivity.class);
                intent.putExtra("lat", lat);
                intent.putExtra("longi", longi);
                intent.putExtra("district", district);
                intent.putExtra("placeName", placeName);
                intent.putExtra("type", getIntent().getIntExtra("type", 0));
                intent.putExtra("title", title);

                intent.putExtra("detailedAddress", et_detailedAddress.getText().toString().trim());
                intent.putExtra("deliveryCustomer", et_deliveryCustomer.getText().toString().trim());
                intent.putExtra("shipper", et_shipper.getText().toString().trim());
                intent.putExtra("phone", et_phone.getText().toString().trim());
                intent.putExtra("eixedTelephone", et_eixedTelephone.getText().toString().trim());
                skipActivity(aty, intent);
                break;
            case R.id.tv_determine:



                break;
        }
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

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (et_detailedAddress.getText().length() > 0 && et_deliveryCustomer.getText().length() > 0 && et_shipper.getText().length() > 0 && et_phone.getText().length() > 0) {
            tv_determine.setClickable(true);
            tv_determine.setBackgroundResource(R.drawable.shape_login);
            tv_determine.setTextColor(getResources().getColor(R.color.mainColor));
        } else {
            tv_determine.setClickable(false);
            tv_determine.setBackgroundResource(R.drawable.shape_login1);
            tv_determine.setTextColor(getResources().getColor(R.color.mainColor));
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
