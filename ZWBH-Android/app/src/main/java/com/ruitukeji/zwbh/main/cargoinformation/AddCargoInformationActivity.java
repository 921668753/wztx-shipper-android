package com.ruitukeji.zwbh.main.cargoinformation;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kymjs.common.StringUtils;
import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.common.BaseActivity;
import com.ruitukeji.zwbh.common.BindView;
import com.ruitukeji.zwbh.common.ViewInject;
import com.ruitukeji.zwbh.dialog.InformationKeptBouncedDialog;
import com.ruitukeji.zwbh.entity.main.cargoinformation.DistanceBean;
import com.ruitukeji.zwbh.main.cargoinformation.selectvehicle.SelectVehicleActivity;
import com.ruitukeji.zwbh.main.dialog.AssignedVehicleBouncedDialog;
import com.ruitukeji.zwbh.main.dialog.SubmitOrdersBouncedDialog;
import com.ruitukeji.zwbh.utils.ActivityTitleUtils;
import com.ruitukeji.zwbh.utils.JsonUtil;
import com.ruitukeji.zwbh.utils.MathUtil;

import cn.bingoogolapple.titlebar.BGATitleBar;

import static com.ruitukeji.zwbh.constant.NumericConstants.REQUEST_CODE_CHOOSE_PHOTO;
import static com.ruitukeji.zwbh.constant.NumericConstants.REQUEST_CODE_PHOTO_PREVIEW;

/**
 * 添加货物信息
 * Created by Administrator on 2017/12/12.
 */

public class AddCargoInformationActivity extends BaseActivity implements TextWatcher, AddCargoInformationContract.View {

    /**
     * 货物名称
     */
    @BindView(id = R.id.et_descriptionGoods)
    private EditText et_descriptionGoods;

    /**
     * 货物重量
     */
    @BindView(id = R.id.et_goodsWeight)
    private EditText et_goodsWeight;

    /**
     * 货物体积
     */
    @BindView(id = R.id.et_volumeGoods)
    private EditText et_volumeGoods;

    /**
     * 货物签收单
     */
    @BindView(id = R.id.img_cargoReceipt, click = true)
    private ImageView img_cargoReceipt;
    private int cargoReceipt = 0;


    /**
     * 用车类型
     */
    @BindView(id = R.id.img_temporaryCar, click = true)
    private ImageView img_temporaryCar;

    private int isTemporaryCar = 0;

    @BindView(id = R.id.img_carLongTime, click = true)
    private ImageView img_carLongTime;

    /**
     * 选择车辆
     */
    @BindView(id = R.id.ll_selectVehicle, click = true)
    private LinearLayout ll_selectVehicle;
    @BindView(id = R.id.tv_selectVehicle)
    private TextView tv_selectVehicle;

    /**
     * 司机装卸车
     */
    @BindView(id = R.id.img_driverCargo, click = true)
    private ImageView img_driverCargo;

    /**
     * 运输时长
     */
    @BindView(id = R.id.et_hour)
    private EditText et_hour;
    @BindView(id = R.id.et_minute)
    private EditText et_minute;

    /**
     * 配送点
     */
    @BindView(id = R.id.et_peiSongDian)
    private EditText et_peiSongDian;

    /**
     * 配送点费用
     */
    @BindView(id = R.id.et_costDistribution)
    private EditText et_costDistribution;

    /**
     * 预计运输费用总额
     */
    @BindView(id = R.id.tv_transportationEstimated)
    private TextView tv_transportationEstimated;

    /**
     * 实际支付费用
     */
    @BindView(id = R.id.et_actualPayment)
    private EditText et_actualPayment;

    /**
     * 提交订单
     */
    @BindView(id = R.id.tv_submitOrders, click = true)
    private TextView tv_submitOrders;

    /**
     * 指派车辆
     */
    @BindView(id = R.id.tv_assignedVehicle, click = true)
    private TextView tv_assignedVehicle;

    private String contactPerson = "";
    private String contactInformation = "";
    private String inArea = "";
    private String detailedAddressInformation = "";
    private int expressDelivery = 0;

    private int vehicleModelId = 0;
    private int vehicleLengthId = 0;
    private String provenanceLat;
    private String provenanceLongi;
    private String provenanceDistrict;
    private String provenancePlaceName;
    private String provenanceDetailedAddress;
    private String provenanceDeliveryCustomer;
    private String provenanceShipper;
    private String provenancePhone;
    private String provenanceEixedTelephone;
    private String destinationLat;
    private String destinationLongi;
    private String destinationDistrict;
    private String destinationPlaceName;
    private String destinationDetailedAddress;
    private String destinationDeliveryCustomer;
    private String destinationShipper;
    private String destinationPhone;
    private String destinationEixedTelephone;

    private String card_number = "";

    private int driverCargo = 1;
    private String vehicleModel;
    private String vehicleLength;
    private String m;
    private String initiatePrice;
    private String kmFee;
    private String freight;
    private String kilometres = "";
    private int tran_type = 0;
    private String type = "often";
    private String appoint_at = "0";
    private AssignedVehicleBouncedDialog assignedVehicleBouncedDialog = null;
    private SubmitOrdersBouncedDialog submitOrdersBouncedDialog = null;
    private InformationKeptBouncedDialog informationKeptBouncedDialog = null;

    @Override
    public void setRootView() {
        setContentView(R.layout.activity_addcargoinformation);
    }

    @Override
    public void initData() {
        super.initData();
        tran_type = getIntent().getIntExtra("tran_type", 0);
        type = getIntent().getStringExtra("type");
        appoint_at = getIntent().getStringExtra("appoint_at");

        provenanceLat = getIntent().getStringExtra("provenanceLat");
        provenanceLongi = getIntent().getStringExtra("provenanceLongi");
        provenanceDistrict = getIntent().getStringExtra("provenanceDistrict");
        provenancePlaceName = getIntent().getStringExtra("provenancePlaceName");
        provenanceDetailedAddress = getIntent().getStringExtra("provenanceDetailedAddress");
        provenanceDeliveryCustomer = getIntent().getStringExtra("provenanceDeliveryCustomer");
        provenanceShipper = getIntent().getStringExtra("provenanceShipper");
        provenancePhone = getIntent().getStringExtra("provenancePhone");
        provenanceEixedTelephone = getIntent().getStringExtra("provenanceEixedTelephone");

        destinationLat = getIntent().getStringExtra("destinationLat");
        destinationLongi = getIntent().getStringExtra("destinationLongi");
        destinationDistrict = getIntent().getStringExtra("destinationDistrict");
        destinationPlaceName = getIntent().getStringExtra("destinationPlaceName");
        destinationDetailedAddress = getIntent().getStringExtra("destinationDetailedAddress");
        destinationDeliveryCustomer = getIntent().getStringExtra("destinationDeliveryCustomer");
        destinationShipper = getIntent().getStringExtra("destinationShipper");
        destinationPhone = getIntent().getStringExtra("destinationPhone");
        destinationEixedTelephone = getIntent().getStringExtra("destinationEixedTelephone");
        informationKeptBouncedDialog = new InformationKeptBouncedDialog(aty);
        mPresenter = new AddCargoInformationPresenter(this);
    }

    @Override
    public void initWidget() {
        super.initWidget();
        et_goodsWeight.addTextChangedListener(this);
        et_peiSongDian.addTextChangedListener(this);
        et_goodsWeight.addTextChangedListener(this);
        et_peiSongDian.addTextChangedListener(this);
        et_costDistribution.addTextChangedListener(this);
        et_costDistribution.addTextChangedListener(this);
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
                if (StringUtils.isEmpty(et_descriptionGoods.getText().toString().trim()) && StringUtils.isEmpty(et_goodsWeight.getText().toString().trim())
                        && StringUtils.isEmpty(et_volumeGoods.getText().toString().trim()) && StringUtils.isEmpty(et_hour.getText().toString().trim())
                        && StringUtils.isEmpty(et_minute.getText().toString().trim()) && StringUtils.isEmpty(et_peiSongDian.getText().toString().trim())
                        && StringUtils.isEmpty(et_costDistribution.getText().toString().trim()) && StringUtils.isEmpty(et_actualPayment.getText().toString().trim())) {
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
        ActivityTitleUtils.initToolbar(aty, getString(R.string.addCargoInformation), "", R.id.titlebar, simpleDelegate);
    }

    @Override
    public void widgetClick(View v) {
        super.widgetClick(v);
        switch (v.getId()) {
            case R.id.img_temporaryCar:
                isTemporaryCar = 0;
                img_temporaryCar.setImageResource(R.mipmap.ic_checkbox_select);
                img_carLongTime.setImageResource(R.mipmap.ic_checkbox_unselect);
                break;
            case R.id.img_carLongTime:
                isTemporaryCar = 1;
                img_carLongTime.setImageResource(R.mipmap.ic_checkbox_select);
                img_temporaryCar.setImageResource(R.mipmap.ic_checkbox_unselect);
                break;
            case R.id.img_cargoReceipt:
                Intent intent = new Intent(aty, FillCargoReceiptFormActivity.class);
                intent.putExtra("cargoReceipt", cargoReceipt);
                intent.putExtra("contactPerson", contactPerson);
                intent.putExtra("contactInformation", contactInformation);
                intent.putExtra("inArea", inArea);
                intent.putExtra("detailedAddressInformation", detailedAddressInformation);
                intent.putExtra("expressDelivery", expressDelivery);
                startActivityForResult(intent, REQUEST_CODE_CHOOSE_PHOTO);
                break;
            case R.id.ll_selectVehicle:
                Intent selectVehicleIntent = new Intent(aty, SelectVehicleActivity.class);
                selectVehicleIntent.putExtra("vehicleModelId", vehicleModelId);
                selectVehicleIntent.putExtra("vehicleLengthId", vehicleLengthId);
                startActivityForResult(selectVehicleIntent, REQUEST_CODE_PHOTO_PREVIEW);
                break;
            case R.id.img_driverCargo:
                if (driverCargo == 1) {
                    img_driverCargo.setImageResource(R.mipmap.switch_btn_off);
                    driverCargo = 0;
                } else {
                    driverCargo = 1;
                    img_driverCargo.setImageResource(R.mipmap.switch_btn_on);
                }
                break;
            case R.id.tv_submitOrders:
                if (StringUtils.toInt(et_minute.getText().toString().trim(), 0) >= 60 || StringUtils.toInt(et_minute.getText().toString().trim(), 0) < 0) {
                    ViewInject.toast(getString(R.string.correctNumberMinutes));
                    return;
                }
                ((AddCargoInformationContract.Presenter) mPresenter).postAddCargoInformation(submitOrdersBouncedDialog, type, appoint_at, "", "", (provenanceLongi + "," + provenanceLat),
                        provenanceDistrict, provenancePlaceName, provenanceDetailedAddress, provenanceDeliveryCustomer, provenanceShipper, provenancePhone, provenanceEixedTelephone,
                        (destinationLongi + "," + destinationLat), destinationDistrict, destinationPlaceName, destinationDetailedAddress, destinationDeliveryCustomer, destinationShipper, destinationPhone, destinationEixedTelephone,
                        et_descriptionGoods.getText().toString().trim(), et_volumeGoods.getText().toString().trim(), et_goodsWeight.getText().toString().trim(), vehicleModel, vehicleModelId, vehicleLength, vehicleLengthId,
                        StringUtils.toInt(et_hour.getText().toString().trim()) * 60 + StringUtils.toInt(et_minute.getText().toString().trim()), cargoReceipt, tv_transportationEstimated.getText().toString(),
                        tran_type, kilometres, StringUtils.toInt(et_peiSongDian.getText().toString().trim(), 0), StringUtils.toDouble(et_costDistribution.getText().toString().trim()), card_number,
                        driverCargo, et_actualPayment.getText().toString().trim(), cargoReceipt, contactPerson, contactInformation, inArea, detailedAddressInformation, expressDelivery, isTemporaryCar);
                break;
            case R.id.tv_assignedVehicle:
                if (StringUtils.toInt(et_minute.getText().toString().trim(), 0) >= 60 || StringUtils.toInt(et_minute.getText().toString().trim(), 0) < 0) {
                    ViewInject.toast(getString(R.string.correctNumberMinutes));
                    return;
                }
                assignedVehicleBouncedDialog = null;
                assignedVehicleBouncedDialog = new AssignedVehicleBouncedDialog(this) {
                    @Override
                    public void confirm(String pleaseLicensePlateNumber) {
                        card_number = pleaseLicensePlateNumber;
                        ((AddCargoInformationContract.Presenter) mPresenter).postAddCargoInformation(submitOrdersBouncedDialog, type, appoint_at, "", "", (provenanceLongi + "," + provenanceLat),
                                provenanceDistrict, provenancePlaceName, provenanceDetailedAddress, provenanceDeliveryCustomer, provenanceShipper, provenancePhone, provenanceEixedTelephone,
                                (destinationLongi + "," + destinationLat), destinationDistrict, destinationPlaceName, destinationDetailedAddress, destinationDeliveryCustomer, destinationShipper, destinationPhone, destinationEixedTelephone,
                                et_descriptionGoods.getText().toString().trim(), et_volumeGoods.getText().toString().trim(), et_goodsWeight.getText().toString().trim(), vehicleModel, vehicleModelId, vehicleLength, vehicleLengthId,
                                StringUtils.toInt(et_hour.getText().toString().trim()) * 60 + StringUtils.toInt(et_minute.getText().toString().trim()), cargoReceipt, tv_transportationEstimated.getText().toString(),
                                tran_type, kilometres, StringUtils.toInt(et_peiSongDian.getText().toString().trim(), 0), StringUtils.toDouble(et_costDistribution.getText().toString().trim()), card_number,
                                driverCargo, et_actualPayment.getText().toString().trim(), cargoReceipt, contactPerson, contactInformation, inArea, detailedAddressInformation, expressDelivery, isTemporaryCar);
                    }
                };
                assignedVehicleBouncedDialog.show();
                break;
        }
    }

    @Override
    public void setPresenter(AddCargoInformationContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void getSuccess(String success, int flag) {
        if (flag == 0) {
            DistanceBean distanceBean = (DistanceBean) JsonUtil.getInstance().json2Obj(success, DistanceBean.class);
            if (!(distanceBean.getStatus().equals("1"))) {
                ViewInject.toast(getString(R.string.distanceErr));
            }
            kilometres = String.valueOf(StringUtils.toDouble(distanceBean.getResults().get(0).getDistance()) / 1000);
            systemPrice(kilometres);
        } else if (flag == 1) {
            ViewInject.toast(getString(R.string.submittedSuccessfully));
            Intent intent = new Intent();
            setResult(RESULT_OK, intent);
            // 结束该activity 结束之后，前面的activity才可以处理结果
            finish();
        }
        dismissLoadingDialog();
    }

    @Override
    public void errorMsg(String msg, int flag) {
        dismissLoadingDialog();
        if(!toLigon1(msg)){
            return;
        }
        ViewInject.toast(msg);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSE_PHOTO && resultCode == RESULT_OK) {// 如果等于1
            // 说明是我们的那次请求
            // 目的：区分请求，不同的请求要做不同的处理
            cargoReceipt = data.getIntExtra("cargoReceipt", 0);
            switch (cargoReceipt) {
                case 0:
                    img_cargoReceipt.setImageResource(R.mipmap.switch_btn_off);
                    break;
                case 1:
                    contactPerson = data.getStringExtra("contactPerson");
                    contactInformation = data.getStringExtra("contactInformation");
                    inArea = data.getStringExtra("inArea");
                    detailedAddressInformation = data.getStringExtra("detailedAddressInformation");
                    expressDelivery = data.getIntExtra("expressDelivery", 0);
                    img_cargoReceipt.setImageResource(R.mipmap.switch_btn_on);
                    break;
            }
        } else if (requestCode == REQUEST_CODE_PHOTO_PREVIEW && resultCode == RESULT_OK) {
            vehicleModel = data.getStringExtra("vehicleModel");
            vehicleLength = data.getStringExtra("vehicleLength");
            tv_selectVehicle.setText(getString(R.string.vehicleModel) + vehicleModel + "  " + getString(R.string.vehicleLength) + vehicleLength);
            m = data.getStringExtra("init_kilometres");
            initiatePrice = data.getStringExtra("init_price");
            kmFee = data.getStringExtra("over_metres_price");
            freight = data.getStringExtra("weight_price");
            vehicleModelId = data.getIntExtra("vehicleModelId", 0);
            vehicleLengthId = data.getIntExtra("vehicleLengthId", 0);
            distance();
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (et_goodsWeight.getText().toString().trim().length() <= 0) {
            return;
        }
        distance();
    }


    /**
     * 计算距离
     */
    private void distance() {
        Log.d("tag", "distance");
        if (StringUtils.toDouble(kilometres) > 0) {
            systemPrice(kilometres);
            return;
        }
        tv_transportationEstimated.setText("0.00");
        if (StringUtils.isEmpty(provenanceLat) || StringUtils.isEmpty(provenanceLongi) || StringUtils.isEmpty(destinationLat) || StringUtils.isEmpty(destinationLongi)) {
            Log.d("tag", "distance1");
            return;
        }
        if (StringUtils.isEmpty(et_goodsWeight.getText().toString().trim()) || StringUtils.isEmpty(m) || StringUtils.isEmpty(initiatePrice) || StringUtils.isEmpty(kmFee) || StringUtils.isEmpty(freight)) {
            Log.d("tag", "distance2");
            return;
        }
        Log.d("tag", "distance3");
//        LatLng latLng1 = new LatLng(StringUtils.toDouble(originLat), StringUtils.toDouble(originLongi));
//        LatLng latLng2 = new LatLng(StringUtils.toDouble(originLat1), StringUtils.toDouble(originLongi1));
        //    double distance = (double) AMapUtils.calculateLineDistance(latLng1, latLng2);
        ((AddCargoInformationContract.Presenter) mPresenter).getDistance(provenanceLongi + "," + provenanceLat, destinationLongi + "," + destinationLat);
    }

    /**
     * 计算系统价
     */
    private void systemPrice(String distanc) {
        double distance = StringUtils.toDouble(distanc);
        if (distance <= 0) {
            ViewInject.toast(getString(R.string.distanceErr));
            return;
        }
        if (StringUtils.isEmpty(et_goodsWeight.getText().toString().trim()) || StringUtils.isEmpty(m) || StringUtils.isEmpty(initiatePrice) || StringUtils.isEmpty(kmFee) || StringUtils.isEmpty(freight)) {
            return;
        }
        double distance1 = distance - StringUtils.toDouble(m);
        if (distance1 <= 0) {
            distance1 = 0;
        }
        double weight = StringUtils.toDouble(et_goodsWeight.getText().toString().trim());
        if (weight <= 0) {
            return;
        }
        double systemPrice = StringUtils.toDouble(initiatePrice) + StringUtils.toDouble(kmFee) * (distance1) + StringUtils.toDouble(freight) * (distance1) * weight
                + StringUtils.toInt(et_peiSongDian.getText().toString().trim(), 0) * StringUtils.toDouble(et_costDistribution.getText().toString().trim());
        Log.d("tag", "initiatePrice" + initiatePrice);
        Log.d("tag", "kmFee" + kmFee);
        Log.d("tag", "distance1" + distance1);
        Log.d("tag", "freight" + freight);
        Log.d("tag", "weight" + weight);
        tv_transportationEstimated.setText(MathUtil.keepTwo(systemPrice));
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        informationKeptBouncedDialog = null;
    }
}
