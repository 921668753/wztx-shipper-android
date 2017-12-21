package com.ruitukeji.zwbh.main;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.kymjs.common.PreferenceHelper;
import com.kymjs.common.StringUtils;
import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.application.MyApplication;
import com.ruitukeji.zwbh.common.BaseActivity;
import com.ruitukeji.zwbh.common.BindView;
import com.ruitukeji.zwbh.common.KJActivityStack;
import com.ruitukeji.zwbh.common.ViewInject;
import com.ruitukeji.zwbh.constant.NumericConstants;
import com.ruitukeji.zwbh.constant.StringConstants;
import com.ruitukeji.zwbh.entity.DistanceBean;
import com.ruitukeji.zwbh.entity.UserInfoBean;
import com.ruitukeji.zwbh.loginregister.EnterpriseInformationActivity;
import com.ruitukeji.zwbh.loginregister.LoginActivity;
import com.ruitukeji.zwbh.loginregister.PersonalInformationActivity;
import com.ruitukeji.zwbh.main.cargoinformation.selectvehicle.SelectVehicleActivity;
import com.ruitukeji.zwbh.mine.setting.aboutus.AboutUsActivity;
import com.ruitukeji.zwbh.mine.onlineservice.OnlineServiceActivity;
import com.ruitukeji.zwbh.mine.personaldata.PayDepositActivity;
import com.ruitukeji.zwbh.utils.ActivityTitleUtils;
import com.ruitukeji.zwbh.utils.JsonUtil;
import com.ruitukeji.zwbh.utils.MathUtil;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * 同城配送  长途干线
 * Created by Administrator on 2017/2/17.
 */

public class LogisticsActivity extends BaseActivity implements LogisticsContract.View, TextWatcher {

    @BindView(id = R.id.tv_appointmentTime)
    private TextView tv_appointmentTime;
    /**
     * 选择用车时间
     */
    @BindView(id = R.id.ll_appointmentTime, click = true)
    private LinearLayout ll_appointmentTime;

    /**
     * 实时
     */
    @BindView(id = R.id.img_realTime, click = true)
    private ImageView img_realTime;
    /**
     * 加急
     */
    @BindView(id = R.id.img_urgent, click = true)
    private ImageView img_urgent;
    /**
     * 预约
     */
    @BindView(id = R.id.img_makeAppointment, click = true)
    private ImageView img_makeAppointment;

    private String type = "often";


    /**
     * 出发地
     */
    @BindView(id = R.id.ll_origin, click = true)
    private LinearLayout ll_origin;
    @BindView(id = R.id.tv_origin)
    private TextView tv_origin;
    @BindView(id = R.id.tv_originContact)
    private TextView tv_originContact;


    /**
     * 目的地
     */
    @BindView(id = R.id.ll_destination, click = true)
    private LinearLayout ll_destination;

    @BindView(id = R.id.tv_detailedAddress)
    private TextView tv_detailedAddress;
    @BindView(id = R.id.tv_nameAndphone)
    private TextView tv_nameAndphone;

    @BindView(id = R.id.et_descriptionGoods)
    private EditText et_descriptionGoods;

    @BindView(id = R.id.et_totalVolume)
    private EditText et_totalVolume;


    @BindView(id = R.id.et_hour1)
    private EditText et_hour1;

    @BindView(id = R.id.et_minute1)
    private EditText et_minute1;


    @BindView(id = R.id.ll_vehicleRequirements, click = true)
    private LinearLayout ll_vehicleRequirements;

    @BindView(id = R.id.ll_cargoInsurance, click = true)
    private LinearLayout ll_cargoInsurance;
    @BindView(id = R.id.tv_cargoInsurance)
    private TextView tv_cargoInsurance;

    @BindView(id = R.id.tv_nextType, click = true)
    private TextView tv_nextType;

    private TimePickerView pvTime;

    /**
     * 调转返回码   1 地址 2  地址 3 车长车型  4 货物保险
     */

    @BindView(id = R.id.img_receiptGoods, click = true)
    private ImageView img_receiptGoods;
    @BindView(id = R.id.img_receiptGoods1, click = true)
    private ImageView img_receiptGoods1;
    /**
     * 选择是否货物回单
     */
    private int isReceiptGoods = 1;


    @BindView(id = R.id.tv_totalMileage)
    private TextView tv_totalMileage;

    @BindView(id = R.id.et_weight)
    private EditText et_weight;


    @BindView(id = R.id.tv_vehicleRequirements)
    private TextView tv_vehicleRequirements;


    @BindView(id = R.id.tv_systemPrice)
    private TextView tv_systemPrice;

    @BindView(id = R.id.et_psychologicalPrice)
    private EditText et_psychologicalPrice;

    @BindView(id = R.id.et_note)
    private EditText et_note;

    /**
     * 协议图片
     */
    @BindView(id = R.id.img_agreement, click = true)
    private ImageView img_agreement;
    private boolean isClick = true;

    /**
     * 发布电子协议
     */
    @BindView(id = R.id.tv_agreement, click = true)
    private TextView tv_agreement;

    private String originProvince = null;
    private String originCity = null;
    private String originDistrict = null;
    private String originAoiName = null;


    private String originAddress = null;
    private String originDetailedAddress = null;
    private String originName = "";
    private String originPhone = null;
    private String originPhone1 = null;

    private String address = null;
    private String detailedAddress = null;
    private String name = null;
    private String phone = null;
    private String phone1 = null;
    private String originLat = null;
    private String originLongi = null;
    private String originLat1 = null;
    private String originLongi1 = null;

    private String m = null;
    private String initiatePrice = null;
    private String kmFee = null;
    private String freight = null;
    private SweetAlertDialog sweetAlertDialog = null;
    private String vehicleModel = null;
    private String vehicleLength = null;
    private String amountCover = null;
    private String cityAddress = null;
    private String cityAddress1 = null;
    private int tran_type;
    private long appoint_at = 0;
    private int vehicleModelId = 0;
    private int vehicleLengthId = 0;
    private String kilometres;
    private boolean isRefresh = true;
    private String insuredAmount = null;


    @Override
    public void setRootView() {
        setContentView(R.layout.activity_logistics);
    }

    @Override
    public void initData() {
        super.initData();
        initDialog();
        mPresenter = new LogisticsPresenter(this);
        tv_detailedAddress.setVisibility(View.GONE);
        tv_nameAndphone.setGravity(Gravity.CENTER | Gravity.LEFT);
        tran_type = getIntent().getIntExtra("tran_type", 0);
        originProvince = getIntent().getStringExtra("province");
        originCity = getIntent().getStringExtra("city");
        originDistrict = getIntent().getStringExtra("district");
        originAoiName = getIntent().getStringExtra("aoiName");
        originLat = getIntent().getStringExtra("lat");
        originLongi = getIntent().getStringExtra("longi");
        originPhone = PreferenceHelper.readString(this, StringConstants.FILENAME, "phone");
        if (StringUtils.isEmpty(originPhone)) {
            ViewInject.toast(getString(R.string.loginAg));
            PreferenceHelper.clean(this, StringConstants.FILENAME);
            PreferenceHelper.write(aty, StringConstants.FILENAME, "isAvatar", true);
            skipActivity(aty, LoginActivity.class);
            return;
        }
        originName = PreferenceHelper.readString(this, StringConstants.FILENAME, "real_name");
        if (StringUtils.isEmpty(originName)) {
            tv_originContact.setText(originPhone);
        } else {
            tv_originContact.setText(originName + "     " + originPhone);
        }
        if (StringUtils.isEmpty(originProvince)) {
            tv_origin.setText(getString(R.string.locationFailure1));
        } else {
            cityAddress = originProvince + originCity + originDistrict;
            originAddress = originProvince + originCity + originDistrict + originAoiName;
            tv_origin.setText(originAddress);
        }
    }

    @Override
    public void initWidget() {
        super.initWidget();
        String title = getIntent().getStringExtra(getString(R.string.logisticsName));
        ActivityTitleUtils.initToolbar(aty, title, true, R.id.titlebar);
        tv_nextType.setText(getString(R.string.submitOrders));
        ll_appointmentTime.setVisibility(View.GONE);
        et_weight.addTextChangedListener(this);
        showLoadingDialog(getString(R.string.dataLoad));
        ((LogisticsContract.Presenter) mPresenter).getInfo(3);
        //  pvTime = new TimePickerView(this, TimePickerView.Type.YEAR_MONTH_DAY);
    }

    /**
     * 弹框设置
     */
    private void initDialog() {
        sweetAlertDialog = null;
        sweetAlertDialog = new SweetAlertDialog(this, SweetAlertDialog.CUSTOM_IMAGE_TYPE);
        sweetAlertDialog.setCancelable(false);
        sweetAlertDialog.setCancelText(getString(R.string.cancel1))
                .setConfirmText(getString(R.string.confirm))
                .showCancelButton(true)
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        dismissLoadingDialog();
                        sweetAlertDialog.dismiss();
                    }
                });
    }

    @Override
    public void widgetClick(View v) {
        super.widgetClick(v);
        switch (v.getId()) {
            case R.id.img_realTime:
                img_realTime.setImageResource(R.mipmap.selected);
                img_urgent.setImageResource(R.mipmap.selected1);
                img_makeAppointment.setImageResource(R.mipmap.selected1);
                ll_appointmentTime.setVisibility(View.GONE);
                pvTime = null;
                type = "often";
                break;

            case R.id.img_urgent:
                img_realTime.setImageResource(R.mipmap.selected1);
                img_urgent.setImageResource(R.mipmap.selected);
                img_makeAppointment.setImageResource(R.mipmap.selected1);
                ll_appointmentTime.setVisibility(View.GONE);
                pvTime = null;
                type = "urgent";
                break;

            case R.id.img_makeAppointment:
                img_realTime.setImageResource(R.mipmap.selected1);
                img_urgent.setImageResource(R.mipmap.selected1);
                img_makeAppointment.setImageResource(R.mipmap.selected);
                ll_appointmentTime.setVisibility(View.VISIBLE);
                TimePicker();
                type = "appoint";
                break;

            case R.id.ll_appointmentTime:
                //弹出时间选择器
                pvTime.show();
                break;
            case R.id.ll_origin:
                Intent destination = new Intent();
                destination.putExtra("address", originAddress);
                destination.putExtra("detailedAddress", originDetailedAddress);
                destination.putExtra("cityAddress", cityAddress);
                destination.putExtra("name", originName);
                destination.putExtra("phone", originPhone);
                destination.putExtra("phone1", originPhone1);
                destination.putExtra("lat", originLat);
                destination.putExtra("longi", originLongi);
                destination.setClass(aty, DeliveryInformationActivity.class);
                startActivityForResult(destination, 1);
                break;
            case R.id.ll_destination:
                Intent destination1 = new Intent();
                destination1.putExtra("address", address);
                destination1.putExtra("detailedAddress", detailedAddress);
                destination1.putExtra("cityAddress", cityAddress1);
                destination1.putExtra("name", name);
                destination1.putExtra("phone", phone);
                destination1.putExtra("phone1", phone1);
                destination1.putExtra("lat", originLat1);
                destination1.putExtra("longi", originLongi1);
                destination1.setClass(aty, DeliveryInformationActivity.class);
                startActivityForResult(destination1, 2);
                break;
            case R.id.ll_vehicleRequirements:
                //   showActivity(aty, ConductorModelsActivity.class);
                Intent intent = new Intent();
                intent.putExtra("vehicleModelId", vehicleModelId);
                intent.putExtra("vehicleLengthId", vehicleLengthId);
                intent.setClass(aty, SelectVehicleActivity.class);
                startActivityForResult(intent, 3);
                break;
            case R.id.ll_cargoInsurance:
                // showActivity(aty, CargoInsuranceActivity.class);
                Intent intent1 = new Intent();
                intent1.setClass(aty, CargoInsuranceActivity.class);
                intent1.putExtra("amountCover", amountCover);
                startActivityForResult(intent1, 4);
                break;

            case R.id.img_receiptGoods:
                img_receiptGoods1.setImageResource(R.mipmap.selected1);
                img_receiptGoods.setImageResource(R.mipmap.selected);
                isReceiptGoods = 1;
                break;
            case R.id.img_receiptGoods1:
                img_receiptGoods.setImageResource(R.mipmap.selected1);
                img_receiptGoods1.setImageResource(R.mipmap.selected);
                isReceiptGoods = 2;
                break;

            case R.id.tv_nextType:
                if (!isClick) {
                    ViewInject.toast(getString(R.string.agreement3));
                    return;
                }
                if (isRefresh) {
                    showLoadingDialog(MyApplication.getContext().getString(R.string.submissionLoad));
                    ((LogisticsContract.Presenter) mPresenter).getInfo(4);
                } else {
                    nextType();
                }
                break;
            case R.id.tv_agreement:
                // 协议
                Intent agreementIntent = new Intent();
                agreementIntent.putExtra("type", "shipper_order_agreement");
                agreementIntent.setClass(aty, AboutUsActivity.class);
                showActivity(aty, agreementIntent);
                break;
            case R.id.img_agreement:
                if (isClick) {
                    img_agreement.setImageResource(R.mipmap.agreement1);
                    isClick = false;
                } else {
                    img_agreement.setImageResource(R.mipmap.agreement);
                    isClick = true;
                }
                break;
        }
    }

    @Override
    public void getSuccess(String s, int flag) {
        if (flag == 0) {
            DistanceBean distanceBean = (DistanceBean) JsonUtil.getInstance().json2Obj(s, DistanceBean.class);
            if (!(distanceBean.getStatus().equals("1"))) {
                ViewInject.toast(getString(R.string.distanceErr));
            }
            kilometres = String.valueOf(StringUtils.toDouble(distanceBean.getResults().get(0).getDistance()) / 1000);
            tv_totalMileage.setText(kilometres);
            systemPrice(distanceBean.getResults().get(0).getDistance());
            dismissLoadingDialog();
        } else if (flag == 1) {
//            LogisticsBean logisticsBean = (LogisticsBean) JsonUtil.getInstance().json2Obj(s, LogisticsBean.class);
//            ((LogisticsContract.Presenter) mPresenter).sendOrder(logisticsBean.getResult().getGoods_id(), (originLongi + "," + originLat));
//        } else if (flag == 2) {
            ViewInject.toast(getString(R.string.submittedSuccessfully));
            dismissLoadingDialog();
            finish();
        } else if (flag == 3) {
            UserInfoBean userInfoBean = (UserInfoBean) JsonUtil.getInstance().json2Obj(s, UserInfoBean.class);
            PreferenceHelper.write(this, StringConstants.FILENAME, "id", userInfoBean.getResult().getId());
            PreferenceHelper.write(this, StringConstants.FILENAME, "phone", userInfoBean.getResult().getPhone());
            PreferenceHelper.write(this, StringConstants.FILENAME, "sex", userInfoBean.getResult().getSex());
            PreferenceHelper.write(this, StringConstants.FILENAME, "avatar", userInfoBean.getResult().getAvatar());
            PreferenceHelper.write(this, StringConstants.FILENAME, "real_name", userInfoBean.getResult().getReal_name());
            PreferenceHelper.write(this, StringConstants.FILENAME, "auth_status", userInfoBean.getResult().getAuth_status());
            PreferenceHelper.write(this, StringConstants.FILENAME, "type", userInfoBean.getResult().getType());
            PreferenceHelper.write(this, StringConstants.FILENAME, "recomm_code", userInfoBean.getResult().getRecomm_code());
            PreferenceHelper.write(this, StringConstants.FILENAME, "bond_status", userInfoBean.getResult().getBond_status());
            PreferenceHelper.write(this, StringConstants.FILENAME, "bond", userInfoBean.getResult().getBond());
            dismissLoadingDialog();
            showDialog();
        } else if (flag == 4) {
            isRefresh = false;
            UserInfoBean userInfoBean = (UserInfoBean) JsonUtil.getInstance().json2Obj(s, UserInfoBean.class);
            PreferenceHelper.write(this, StringConstants.FILENAME, "id", userInfoBean.getResult().getId());
            PreferenceHelper.write(this, StringConstants.FILENAME, "phone", userInfoBean.getResult().getPhone());
            PreferenceHelper.write(this, StringConstants.FILENAME, "sex", userInfoBean.getResult().getSex());
            PreferenceHelper.write(this, StringConstants.FILENAME, "avatar", userInfoBean.getResult().getAvatar());
            PreferenceHelper.write(this, StringConstants.FILENAME, "real_name", userInfoBean.getResult().getReal_name());
            PreferenceHelper.write(this, StringConstants.FILENAME, "auth_status", userInfoBean.getResult().getAuth_status());
            PreferenceHelper.write(this, StringConstants.FILENAME, "type", userInfoBean.getResult().getType());
            PreferenceHelper.write(this, StringConstants.FILENAME, "recomm_code", userInfoBean.getResult().getRecomm_code());
            PreferenceHelper.write(this, StringConstants.FILENAME, "bond_status", userInfoBean.getResult().getBond_status());
            PreferenceHelper.write(this, StringConstants.FILENAME, "bond", userInfoBean.getResult().getBond());
            nextType();
        }
    }

    @Override
    public void error(String msg) {
        if (StringUtils.isEmpty(msg)) {
            msg = getString(R.string.otherError);
        }
        if (msg.equals("" + NumericConstants.TOLINGIN)) {
            dismissLoadingDialog();
            skipActivity(aty, LoginActivity.class);
            return;
        }
        dismissLoadingDialog();
        ViewInject.toast(msg);
    }


    private boolean showDialog() {
        if (sweetAlertDialog == null) {
            initDialog();
        }
        //认证
        String auth_status = PreferenceHelper.readString(MyApplication.getContext(), StringConstants.FILENAME, "auth_status");
        if (!(auth_status != null && auth_status.equals("pass"))) {
            if (auth_status != null && auth_status.equals("check")) {
                ViewInject.toast(getString(R.string.inAuthentication) + "," + getString(R.string.pleaseWait));
                return false;
            }
            sweetAlertDialog.setTitleText(KJActivityStack.create().topActivity().getString(R.string.unauthorized1));
            sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    sweetAlertDialog.dismiss();
                    String type = PreferenceHelper.readString(MyApplication.getContext(), StringConstants.FILENAME, "type");
                    if (type.equals("person")) {
                        showActivity(aty, PersonalInformationActivity.class);
                    } else {
                        showActivity(aty, EnterpriseInformationActivity.class);
                    }
                }
            }).show();
            return false;
        }
        //保证金
        String bond_status = PreferenceHelper.readString(MyApplication.getContext(), StringConstants.FILENAME, "bond_status");
        if (bond_status != null && bond_status.equals("frozen")) {
            sweetAlertDialog.setTitleText(KJActivityStack.create().topActivity().getString(R.string.payMargin1));
            sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    sweetAlertDialog.dismiss();
                    showActivity(aty, OnlineServiceActivity.class);
                }
            }).show();
            return false;
        }
        if (!(bond_status != null && bond_status.equals("checked"))) {
            sweetAlertDialog.setTitleText(KJActivityStack.create().topActivity().getString(R.string.payMargin));
            sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    sweetAlertDialog.dismiss();
                    showActivity(aty, PayDepositActivity.class);
                }
            }).show();
            return false;
        }
        return true;
    }


    private void nextType() {
        if (showDialog()) {
            if (!StringUtils.isEmpty(et_minute1.getText().toString().trim()) && StringUtils.toInt(et_minute1.getText().toString().trim()) < 0 || !StringUtils.isEmpty(et_minute1.getText().toString().trim()) && StringUtils.toInt(et_minute1.getText().toString().trim()) > 60) {
                ViewInject.toast(getString(R.string.correctNumberMinutes));
                return;
            } else {
                ((LogisticsContract.Presenter) mPresenter).getLogistics(sweetAlertDialog, type, appoint_at, (originLongi + "," + originLat), cityAddress, originAddress, originDetailedAddress, originName, originPhone, originPhone1, (originLongi1 + "," + originLat1), cityAddress1, address, detailedAddress, name, phone, phone1, et_descriptionGoods.getText().toString().trim(), et_weight.getText().toString().trim(), et_totalVolume.getText().toString().trim(), vehicleLength, vehicleLengthId, vehicleModel, vehicleModelId, amountCover, insuredAmount, StringUtils.toInt(et_hour1.getText().toString().trim()) * 60 + StringUtils.toInt(et_minute1.getText().toString().trim()), isReceiptGoods, tv_systemPrice.getText().toString().trim(), et_psychologicalPrice.getText().toString().trim(), et_note.getText().toString().trim(), tran_type, kilometres);
            }
        } else {
            dismissLoadingDialog();
        }
    }


    @Override
    public void setPresenter(LogisticsContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//跳转     private int req_code1 = 1;
//        Intent intent = new Intent(Modify_address.this, Address_pop.class);
//        startActivityForResult(intent, req_code1);
        if (requestCode == 1 && resultCode == RESULT_OK) {// 如果等于1
            // 说明是我们的那次请求
            // 目的：区分请求，不同的请求要做不同的处理
            originAddress = data.getStringExtra("address");
            originDetailedAddress = data.getStringExtra("detailedAddress");
            cityAddress = data.getStringExtra("cityAddress");
            originName = data.getStringExtra("name");
            originPhone = data.getStringExtra("phone");
            originPhone1 = data.getStringExtra("phone1");
            originLat = data.getStringExtra("lat");
            originLongi = data.getStringExtra("longi");
            tv_origin.setText(originAddress);
            tv_originContact.setText(originName + "     " + originPhone);
            distance();
        } else if (requestCode == 2 && resultCode == RESULT_OK) {// 如果等于1
            tv_detailedAddress.setVisibility(View.VISIBLE);
            tv_nameAndphone.setGravity(Gravity.TOP);
            tv_nameAndphone.setTextSize(15);
            address = data.getStringExtra("address");
            detailedAddress = data.getStringExtra("detailedAddress");
            cityAddress1 = data.getStringExtra("cityAddress");
            name = data.getStringExtra("name");
            phone = data.getStringExtra("phone");
            phone1 = data.getStringExtra("phone1");
            originLat1 = data.getStringExtra("lat");
            originLongi1 = data.getStringExtra("longi");
            tv_detailedAddress.setText(address);
            tv_nameAndphone.setText(name + "     " + phone);
            distance();
        } else if (requestCode == 3 && resultCode == RESULT_OK) {// 如果等于1
            vehicleModel = data.getStringExtra("vehicleModel");
            vehicleLength = data.getStringExtra("vehicleLength");
            tv_vehicleRequirements.setText(getString(R.string.vehicleModel) + vehicleModel + "  " + getString(R.string.vehicleLength) + vehicleLength);
            m = data.getStringExtra("init_kilometres");
            initiatePrice = data.getStringExtra("init_price");
            kmFee = data.getStringExtra("over_metres_price");
            freight = data.getStringExtra("weight_price");
            vehicleModelId = data.getIntExtra("vehicleModelId", 0);
            vehicleLengthId = data.getIntExtra("vehicleLengthId", 0);
            distance();
        } else if (requestCode == 4 && resultCode == RESULT_OK) {// 如果等于1
            amountCover = data.getStringExtra("amountCover");
            insuredAmount = data.getStringExtra("insuredAmount");
            tv_cargoInsurance.setText(getString(R.string.amountCover) + amountCover + getString(R.string.yuan));
        }
    }

    public void TimePicker() {
        //TimePicker
        //控制时间范围
        Calendar calendar = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        startDate.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        Calendar endDate = Calendar.getInstance();
        endDate.set(calendar.get(Calendar.YEAR) + 1, calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        pvTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                if (date.getTime() <= System.currentTimeMillis()) {
                    ViewInject.toast(getString(R.string.greateThanCurrentTime));
                    return;
                }
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                appoint_at = date.getTime() / 1000;
                tv_appointmentTime.setText(getString(R.string.appointmentTime1) + format.format(date));
            }
        })
              //  .setType(TimePickerView.Type.YEAR_MONTH_DAY_HOUR_MIN)
                .setRangDate(startDate, endDate).build();
    }

    /**
     * 计算系统价
     */
    private void systemPrice(String distanc) {
        //  tv_systemPrice.setText("0.00");
//        if (StringUtils.isEmpty(originLat) || StringUtils.isEmpty(originLongi) || StringUtils.isEmpty(originLat1) || StringUtils.isEmpty(originLongi1)) {
//            return;
//        }
        double distance = StringUtils.toDouble(distanc) / 1000;
        if (distance <= 0) {
            ViewInject.toast(getString(R.string.distanceErr));
            return;
        }
        if (StringUtils.isEmpty(et_weight.getText().toString().trim()) || StringUtils.isEmpty(m) || StringUtils.isEmpty(initiatePrice) || StringUtils.isEmpty(kmFee) || StringUtils.isEmpty(freight)) {
            return;
        }
        double distance1 = distance - StringUtils.toDouble(m);
        if (distance1 <= 0) {
            distance1 = 0;
        }
        double weight = StringUtils.toDouble(et_weight.getText().toString().trim());
        if (weight <= 0) {
            return;
        }
        double systemPrice = StringUtils.toDouble(initiatePrice) + StringUtils.toDouble(kmFee) * (distance1) + StringUtils.toDouble(freight) * (distance1) * weight;
        Log.d("tag", "initiatePrice" + initiatePrice);
        Log.d("tag", "kmFee" + kmFee);
        Log.d("tag", "distance1" + distance1);
        Log.d("tag", "freight" + freight);
        Log.d("tag", "weight" + weight);
        tv_systemPrice.setText(MathUtil.keepTwo(systemPrice));
    }


    /**
     * 计算距离
     */
    private void distance() {
        Log.d("tag", "distance");
        tv_totalMileage.setText("0.00");
        tv_systemPrice.setText("0.00");
        if (StringUtils.isEmpty(originLat) || StringUtils.isEmpty(originLongi) || StringUtils.isEmpty(originLat1) || StringUtils.isEmpty(originLongi1)) {
            Log.d("tag", "distance1");
            return;
        }
        if (StringUtils.isEmpty(et_weight.getText().toString().trim()) || StringUtils.isEmpty(m) || StringUtils.isEmpty(initiatePrice) || StringUtils.isEmpty(kmFee) || StringUtils.isEmpty(freight)) {
            Log.d("tag", "distance2");
            return;
        }
        Log.d("tag", "distance3");
//        LatLng latLng1 = new LatLng(StringUtils.toDouble(originLat), StringUtils.toDouble(originLongi));
//        LatLng latLng2 = new LatLng(StringUtils.toDouble(originLat1), StringUtils.toDouble(originLongi1));
        //    double distance = (double) AMapUtils.calculateLineDistance(latLng1, latLng2);
        ((LogisticsContract.Presenter) mPresenter).getDistance(originLongi + "," + originLat, originLongi1 + "," + originLat1);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sweetAlertDialog = null;
        pvTime = null;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        distance();
    }
}
