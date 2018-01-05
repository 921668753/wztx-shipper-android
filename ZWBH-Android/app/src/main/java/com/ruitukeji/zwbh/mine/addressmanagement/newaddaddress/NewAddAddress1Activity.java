package com.ruitukeji.zwbh.mine.addressmanagement.newaddaddress;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.kymjs.common.PreferenceHelper;
import com.kymjs.common.StringUtils;
import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.common.BaseActivity;
import com.ruitukeji.zwbh.common.BindView;
import com.ruitukeji.zwbh.constant.StringConstants;
import com.ruitukeji.zwbh.dialog.InformationKeptBouncedDialog;
import com.ruitukeji.zwbh.entity.mine.addressmanagement.NewAddAddress1Bean;
import com.ruitukeji.zwbh.utils.ActivityTitleUtils;
import com.ruitukeji.zwbh.utils.JsonUtil;

import cn.bingoogolapple.titlebar.BGATitleBar.SimpleDelegate;

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
     * 固定电话
     */
    @BindView(id = R.id.img_on, click = true)
    private ImageView img_on;

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
    private int is_default = 1;
    private int id = 0;
    private InformationKeptBouncedDialog informationKeptBouncedDialog = null;
    private int is_default1 = 0;

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
        if (type == 1 || type == 3) {
            id = getIntent().getIntExtra("address_id", 0);
            //获取地址信息
            ((NewAddAddress1Contract.Presenter) mPresenter).getAddress(id);
        }
        informationKeptBouncedDialog = new InformationKeptBouncedDialog(aty);
    }

    @Override
    public void initWidget() {
        super.initWidget();
        et_detailedAddress.addTextChangedListener(this);
        et_deliveryCustomer.addTextChangedListener(this);
        et_shipper.addTextChangedListener(this);
        et_phone.addTextChangedListener(this);
        tv_determine.setClickable(false);
        informationKeptBouncedDialog.setInformationKeptDialogCallBack(new InformationKeptBouncedDialog.InformationKeptDialogCallBack() {
            @Override
            public void confirm() {
                informationKeptBouncedDialog.cancel();
                aty.finish();
            }
        });
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
        SimpleDelegate simpleDelegate = new SimpleDelegate() {
            @Override
            public void onClickLeftCtv() {
                super.onClickLeftCtv();
                if (StringUtils.isEmpty(et_detailedAddress.getText().toString().trim()) && StringUtils.isEmpty(et_deliveryCustomer.getText().toString().trim())
                        && StringUtils.isEmpty(et_shipper.getText().toString().trim()) && StringUtils.isEmpty(et_phone.getText().toString().trim())
                        && StringUtils.isEmpty(et_eixedTelephone.getText().toString().trim())) {
                    aty.finish();
                    return;
                }
                if (tv_address.getText().toString().trim().equals(placeName) && et_detailedAddress.getText().toString().trim().equals(detailedAddress)
                        && et_deliveryCustomer.getText().toString().trim().equals(deliveryCustomer) && et_shipper.getText().toString().trim().equals(shipper)
                        && et_phone.getText().toString().trim().equals(phone) && et_eixedTelephone.getText().toString().trim().equals(eixedTelephone) && is_default1 == is_default) {
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
            case R.id.img_on:
                if (is_default == 1) {
                    img_on.setImageResource(R.mipmap.switch_btn_off);
                    is_default = 0;
                } else {
                    img_on.setImageResource(R.mipmap.switch_btn_on);
                    is_default = 1;
                }
                break;
            case R.id.tv_determine:
                if (type == 1 || type == 3) {
                    ((NewAddAddress1Contract.Presenter) mPresenter).postUpdateAddress(longi, lat, district, placeName, et_detailedAddress.getText().toString().trim(),
                            et_deliveryCustomer.getText().toString().trim(), et_shipper.getText().toString().trim(), et_phone.getText().toString().trim(),
                            et_eixedTelephone.getText().toString().trim(), id, type, is_default);
                    break;
                }
                ((NewAddAddress1Contract.Presenter) mPresenter).postAddress(longi, lat, district, placeName, et_detailedAddress.getText().toString().trim(),
                        et_deliveryCustomer.getText().toString().trim(), et_shipper.getText().toString().trim(), et_phone.getText().toString().trim(),
                        et_eixedTelephone.getText().toString().trim(), type, is_default);
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
            NewAddAddress1Bean newAddAddress1Bean = (NewAddAddress1Bean) JsonUtil.getInstance().json2Obj(success, NewAddAddress1Bean.class);
            lat = newAddAddress1Bean.getResult().getAddress_maps().split(",")[0];
            longi = newAddAddress1Bean.getResult().getAddress_maps().split(",")[1];
            district = newAddAddress1Bean.getResult().getCity();
            placeName = newAddAddress1Bean.getResult().getAddress_name();
            tv_address.setText(placeName);
            detailedAddress = newAddAddress1Bean.getResult().getAddress_detail();
            if (StringUtils.isEmpty(detailedAddress)) {
                detailedAddress = "";
            }
            et_detailedAddress.setText(detailedAddress);
            deliveryCustomer = newAddAddress1Bean.getResult().getClient();
            et_deliveryCustomer.setText(deliveryCustomer);
            shipper = newAddAddress1Bean.getResult().getClient_name();
            et_shipper.setText(shipper);
            phone = newAddAddress1Bean.getResult().getPhone();
            et_phone.setText(phone);
            eixedTelephone = newAddAddress1Bean.getResult().getTelphone();
            if (StringUtils.isEmpty(eixedTelephone)) {
                eixedTelephone = "";
            }
            et_eixedTelephone.setText(eixedTelephone);
            id = newAddAddress1Bean.getResult().getId();
            is_default1 = newAddAddress1Bean.getResult().getIs_default();
            is_default = newAddAddress1Bean.getResult().getIs_default();
            if (is_default == 0) {
                img_on.setImageResource(R.mipmap.switch_btn_off);
            } else {
                img_on.setImageResource(R.mipmap.switch_btn_on);
            }
        } else if (flag == 1) {
            if (type == 0 && is_default == 1 || type == 1 && is_default == 1) {
                PreferenceHelper.write(this, StringConstants.FILENAME, "isDefaultAddress", true);
                PreferenceHelper.write(this, StringConstants.FILENAME, "provenanceLat", lat);
                PreferenceHelper.write(this, StringConstants.FILENAME, "provenanceLongi", longi);
                PreferenceHelper.write(this, StringConstants.FILENAME, "provenanceDistrict", district);
                PreferenceHelper.write(this, StringConstants.FILENAME, "provenancePlaceName", placeName);
                PreferenceHelper.write(this, StringConstants.FILENAME, "provenanceDetailedAddress", detailedAddress);
                PreferenceHelper.write(this, StringConstants.FILENAME, "provenanceDeliveryCustomer", deliveryCustomer);
                PreferenceHelper.write(this, StringConstants.FILENAME, "provenanceShipper", shipper);
                PreferenceHelper.write(this, StringConstants.FILENAME, "provenancePhone", phone);
                PreferenceHelper.write(this, StringConstants.FILENAME, "provenanceEixedTelephone", eixedTelephone);
            } else if (type == 2 && is_default == 1 || type == 3 && is_default == 1) {
                PreferenceHelper.write(this, StringConstants.FILENAME, "destinationLat", lat);
                PreferenceHelper.write(this, StringConstants.FILENAME, "destinationLongi", longi);
                PreferenceHelper.write(this, StringConstants.FILENAME, "destinationDistrict", district);
                PreferenceHelper.write(this, StringConstants.FILENAME, "destinationPlaceName", placeName);
                PreferenceHelper.write(this, StringConstants.FILENAME, "destinationDetailedAddress", detailedAddress);
                PreferenceHelper.write(this, StringConstants.FILENAME, "destinationDeliveryCustomer", deliveryCustomer);
                PreferenceHelper.write(this, StringConstants.FILENAME, "destinationShipper", shipper);
                PreferenceHelper.write(this, StringConstants.FILENAME, "destinationPhone", phone);
                PreferenceHelper.write(this, StringConstants.FILENAME, "destinationEixedTelephone", eixedTelephone);
            }
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
        } else if (flag == 2) {
            Intent intent = new Intent();
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    @Override
    public void errorMsg(String msg, int flag) {
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        informationKeptBouncedDialog = null;
    }
}
