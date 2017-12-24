package com.ruitukeji.zwbh.mine.addressmanagement.newaddaddress;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.common.BaseActivity;
import com.ruitukeji.zwbh.common.BindView;
import com.ruitukeji.zwbh.common.ViewInject;
import com.ruitukeji.zwbh.constant.NumericConstants;
import com.ruitukeji.zwbh.loginregister.LoginActivity;
import com.ruitukeji.zwbh.utils.ActivityTitleUtils;

import static com.ruitukeji.zwbh.constant.NumericConstants.REQUEST_CODE_CHOOSE_PHOTO;

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
        setContentView(R.layout.activity_newaddaddress1);
    }

    @Override
    public void initData() {
        super.initData();
        mPresenter = new NewAddAddress1Presenter(this);
        lat = getIntent().getStringExtra("lat");
        longi = getIntent().getStringExtra("longi");
        district = getIntent().getStringExtra("district");
        placeName = getIntent().getStringExtra("placeName");
        type = getIntent().getIntExtra("type", 0);
        et_detailedAddress.addTextChangedListener(this);
        et_deliveryCustomer.addTextChangedListener(this);
        et_shipper.addTextChangedListener(this);
        et_phone.addTextChangedListener(this);
        if (type == 1 || type == 3) {
            //获取地址信息


        }
    }

    @Override
    public void initWidget() {
        super.initWidget();
        mPresenter = new NewAddAddress1Presenter(this);
        tv_address.setText(placeName);
        if (type == 0 || type == 1) {
            tv_deliveryCustomer.setText(getString(R.string.deliveryCustomer));
            tv_shipper.setText(getString(R.string.shipper));
        } else if (type == 2 || type == 3) {
            tv_deliveryCustomer.setText(getString(R.string.receivingCustomer));
            tv_shipper.setText(getString(R.string.consignee));
        }
        if (type == 0 || type == 2) {
            title = getString(R.string.newAddress);
        } else if (type == 1 || type == 3) {
            title = getString(R.string.changeAddress);
        }
        ActivityTitleUtils.initToolbar(aty, title, true, R.id.titlebar);
    }


    @Override
    public void widgetClick(View v) {
        super.widgetClick(v);
        switch (v.getId()) {
            case R.id.tv_address:
                if (type == 0 || type == 2) {
                    return;
                }
                Intent intent = new Intent(aty, NewAddAddressActivity.class);
                intent.putExtra("type", type);
                startActivityForResult(intent, REQUEST_CODE_CHOOSE_PHOTO);
                break;
            case R.id.tv_determine:
                ((NewAddAddress1Contract.Presenter) mPresenter).postAddress(longi, lat, district, placeName, et_detailedAddress.getText().toString().trim(),
                        et_deliveryCustomer.getText().toString().trim(), et_shipper.getText().toString().trim(), et_phone.getText().toString().trim(),
                        et_eixedTelephone.getText().toString().trim(), type);

                break;
        }
    }

    @Override
    public void setPresenter(NewAddAddress1Contract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void getSuccess(String success, int flag) {
        dismissLoadingDialog();
        if (flag == 0) {

        } else if (flag == 1) {
            Intent intent = new Intent();
//            intent.putExtra("lat", lat);
//            intent.putExtra("longi", longi);
//            intent.putExtra("district", district);
//            intent.putExtra("placeName", placeName);
//            intent.putExtra("type", getIntent().getIntExtra("type", 0));
//            intent.putExtra("title", title);
//            intent.putExtra("detailedAddress", detailedAddress);
//            intent.putExtra("deliveryCustomer", deliveryCustomer);
//            intent.putExtra("shipper", shipper);
//            intent.putExtra("phone", phone);
//            intent.putExtra("eixedTelephone", eixedTelephone);
            setResult(RESULT_OK, intent);
//            // 结束该activity 结束之后，前面的activity才可以处理结果
            finish();
        }
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSE_PHOTO && resultCode == RESULT_OK) {
            /**
             * 地址选择页面返回
             */
            lat = data.getStringExtra("lat");
            longi = data.getStringExtra("longi");
            district = data.getStringExtra("district");
            placeName = data.getStringExtra("placeName");
            tv_address.setText(placeName);
        }

    }


}
