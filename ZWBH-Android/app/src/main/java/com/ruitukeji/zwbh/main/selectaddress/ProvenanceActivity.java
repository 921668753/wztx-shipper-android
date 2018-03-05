package com.ruitukeji.zwbh.main.selectaddress;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.kymjs.common.StringUtils;
import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.common.BaseActivity;
import com.ruitukeji.zwbh.common.BindView;
import com.ruitukeji.zwbh.dialog.InformationKeptBouncedDialog;
import com.ruitukeji.zwbh.utils.ActivityTitleUtils;

import cn.bingoogolapple.titlebar.BGATitleBar;

import static com.ruitukeji.zwbh.constant.NumericConstants.REQUEST_CODE_CHOOSE_PHOTO;

/**
 * 始发地/目的地
 * destination
 * Created by Administrator on 2017/12/12.
 */

public class ProvenanceActivity extends BaseActivity implements ProvenanceContract.View, TextWatcher {

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
     * 设为常用地址
     */
    @BindView(id = R.id.img_off, click = true)
    private ImageView img_off;

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

    private int isOff = 0;
    private int isProvenance = 0;
    private int tran_type = 0;
    private String city = "";
    private InformationKeptBouncedDialog informationKeptBouncedDialog = null;
    private int isOff1 = 0;
    private String placeName1 = "";

    private int isfinish = 0;


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
        isProvenance = getIntent().getIntExtra("isProvenance", 0);
        detailedAddress = getIntent().getStringExtra("detailedAddress");
        deliveryCustomer = getIntent().getStringExtra("deliveryCustomer");
        shipper = getIntent().getStringExtra("shipper");
        phone = getIntent().getStringExtra("phone");
        tran_type = getIntent().getIntExtra("tran_type", 0);
        eixedTelephone = getIntent().getStringExtra("eixedTelephone");
        city = getIntent().getStringExtra("cityName");
        title = getIntent().getStringExtra("title");
        if (StringUtils.isEmpty(detailedAddress)) {
            detailedAddress = "";
        }
        placeName1 = placeName;
        if (StringUtils.isEmpty(eixedTelephone)) {
            eixedTelephone = "";
        }
        mPresenter = new ProvenancePresenter(this);
        informationKeptBouncedDialog = new InformationKeptBouncedDialog(aty);
    }

    @Override
    public void initWidget() {
        super.initWidget();
        tv_determine.setClickable(false);
        et_detailedAddress.addTextChangedListener(this);
        et_deliveryCustomer.addTextChangedListener(this);
        et_shipper.addTextChangedListener(this);
        et_phone.addTextChangedListener(this);
        informationKeptBouncedDialog.setInformationKeptDialogCallBack(new InformationKeptBouncedDialog.InformationKeptDialogCallBack() {
            @Override
            public void confirm() {
                informationKeptBouncedDialog.cancel();
                aty.finish();
            }
        });

        BGATitleBar.SimpleDelegate simpleDelegate = new BGATitleBar.SimpleDelegate() {
            @Override
            public void onClickLeftCtv() {
                super.onClickLeftCtv();
                if (StringUtils.isEmpty(et_detailedAddress.getText().toString().trim()) && StringUtils.isEmpty(et_deliveryCustomer.getText().toString().trim())
                        && StringUtils.isEmpty(et_shipper.getText().toString().trim()) && StringUtils.isEmpty(et_phone.getText().toString().trim())
                        && StringUtils.isEmpty(et_eixedTelephone.getText().toString().trim())) {
                    aty.finish();
                    return;
                }

                if (tv_address.getText().toString().trim().equals(placeName1) && et_detailedAddress.getText().toString().trim().equals(detailedAddress)
                        && et_deliveryCustomer.getText().toString().trim().equals(deliveryCustomer) && et_shipper.getText().toString().trim().equals(shipper)
                        && et_phone.getText().toString().trim().equals(phone) && et_eixedTelephone.getText().toString().trim().equals(eixedTelephone) && isOff == isOff1) {
                    aty.finish();
                    return;
                }
                informationKeptBouncedDialog.show();
            }

            @Override
            public void onClickRightCtv() {
                super.onClickRightCtv();

            }
        };
        ActivityTitleUtils.initToolbar(aty, title, "", R.id.titlebar, simpleDelegate);
        tv_address.setText(placeName);
        setView();
        if (type == 0) {
            tv_deliveryCustomer.setText(getString(R.string.deliveryCustomer));
            tv_shipper.setText(getString(R.string.shipper));
        } else {
            tv_deliveryCustomer.setText(getString(R.string.receivingCustomer));
            tv_shipper.setText(getString(R.string.consignee));
        }
        isOff1 = getIntent().getIntExtra("isOff1", 0);
        if (isOff1 == 1) {
            img_off.setImageResource(R.mipmap.switch_btn_on);
            isOff = 1;
        } else {
            img_off.setImageResource(R.mipmap.switch_btn_off);
            isOff = 0;
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
                if (isProvenance != 1) {
                    break;
                }
                int orgprovince = placeName1.indexOf("省");
                int orgcity = placeName1.indexOf("市");
                if (orgprovince == -1 && orgcity != -1) {
                    city = placeName1.substring(0, orgcity + 1);
                } else if (orgprovince != -1 && orgcity != -1) {
                    city = placeName1.substring(orgprovince + 1, orgcity + 1);
                }
                Intent intent = new Intent(aty, SelectAddressActivity.class);
                intent.putExtra("isProvenance", isProvenance);
                intent.putExtra("tran_type", tran_type);
                intent.putExtra("type", type);
                intent.putExtra("cityName", city);
                startActivityForResult(intent, REQUEST_CODE_CHOOSE_PHOTO);
                break;
            case R.id.img_off:
                if (isOff == 0) {
                    img_off.setImageResource(R.mipmap.switch_btn_on);
                    isOff = 1;
                } else {
                    img_off.setImageResource(R.mipmap.switch_btn_off);
                    isOff = 0;
                }
                break;
            case R.id.tv_determine:
                if (tv_address.getText().toString().trim().equals(placeName1) && et_detailedAddress.getText().toString().trim().equals(detailedAddress)
                        && et_deliveryCustomer.getText().toString().trim().equals(deliveryCustomer) && et_shipper.getText().toString().trim().equals(shipper)
                        && et_phone.getText().toString().trim().equals(phone) && et_eixedTelephone.getText().toString().trim().equals(eixedTelephone) && isOff == isOff1 && isfinish == 0) {
                    aty.finish();
                    return;
                }
                ((ProvenanceContract.Presenter) mPresenter).postAddress(longi, lat, district, placeName, et_detailedAddress.getText().toString().trim(),
                        et_deliveryCustomer.getText().toString().trim(), et_shipper.getText().toString().trim(), et_phone.getText().toString().trim(),
                        et_eixedTelephone.getText().toString().trim(), isOff, type);
                break;
        }
    }

    @Override
    public void setPresenter(ProvenanceContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void getSuccess(String success, int flag) {
        dismissLoadingDialog();
        Intent intent = new Intent();
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
        intent.putExtra("isOff1", isOff);
        setResult(RESULT_OK, intent);
        finish();

//        if (flag == 0) {
//
//
//        } else if (flag == 1) {
//
//
//        }
        //    dismissLoadingDialog();
    }

    @Override
    public void errorMsg(String msg, int flag) {
//        if (msg != null && msg.equals("" + NumericConstants.TOLINGIN)) {
//            dismissLoadingDialog();
//            showActivity(aty, LoginActivity.class);
//            return;
//        }
        dismissLoadingDialog();
        if (toLigon1(msg)) {
            return;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (et_deliveryCustomer.getText().length() > 0 && et_shipper.getText().length() > 0 && et_phone.getText().length() > 0) {
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
            if (data.getIntExtra("isFinish", 0) == 1) {
                /**
                 * 地址管理页面返回
                 */
                // 如果等于1
                // 说明是我们的那次请求
                // 目的：区分请求，不同的请求要做不同的处理
                lat = data.getStringExtra("lat");
                longi = data.getStringExtra("longi");
                district = data.getStringExtra("district");
                placeName = data.getStringExtra("placeName");
                type = data.getIntExtra("type", 0);
                detailedAddress = data.getStringExtra("detailedAddress");
                deliveryCustomer = data.getStringExtra("deliveryCustomer");
                shipper = data.getStringExtra("shipper");
                phone = data.getStringExtra("phone");
                eixedTelephone = data.getStringExtra("eixedTelephone");
                Intent intent = new Intent();
                intent.putExtra("lat", lat);
                intent.putExtra("longi", longi);
                intent.putExtra("district", district);
                intent.putExtra("placeName", placeName);
                intent.putExtra("detailedAddress", detailedAddress);
                intent.putExtra("deliveryCustomer", deliveryCustomer);
                intent.putExtra("shipper", shipper);
                intent.putExtra("phone", phone);
                intent.putExtra("eixedTelephone", eixedTelephone);
                setResult(RESULT_OK, intent);
                // 结束该activity 结束之后，前面的activity才可以处理结果
                finish();
                return;
            }
            isfinish = 1;
            lat = data.getStringExtra("lat");
            longi = data.getStringExtra("longi");
            district = data.getStringExtra("district");
            placeName = data.getStringExtra("placeName");
            tv_address.setText(placeName);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (informationKeptBouncedDialog != null) {
            informationKeptBouncedDialog.cancel();
        }
        informationKeptBouncedDialog = null;
    }
}
