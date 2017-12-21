package com.ruitukeji.zwbh.main.cargoinformation;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.common.BaseActivity;
import com.ruitukeji.zwbh.common.BindView;
import com.ruitukeji.zwbh.common.ViewInject;
import com.ruitukeji.zwbh.main.cargoinformation.selectvehicle.SelectVehicleActivity;
import com.ruitukeji.zwbh.utils.ActivityTitleUtils;

import static com.ruitukeji.zwbh.constant.NumericConstants.REQUEST_CODE_CHOOSE_PHOTO;
import static com.ruitukeji.zwbh.constant.NumericConstants.REQUEST_CODE_PHOTO_PREVIEW;

/**
 * 添加货物信息
 * Created by Administrator on 2017/12/12.
 */

public class AddCargoInformationActivity extends BaseActivity implements AddCargoInformationContract.View {

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
     * 提交
     */
    @BindView(id = R.id.tv_submit, click = true)
    private TextView tv_submit;

    private String contactPerson = "";
    private String contactInformation = "";
    private String inArea = "";
    private String detailedAddressInformation = "";
    private int expressDelivery = 0;
    private int vehicleModelId = 0;
    private int vehicleLengthId = 0;
    private String totalMileage = "";

    @Override
    public void setRootView() {
        setContentView(R.layout.activity_addcargoinformation);
    }

    @Override
    public void initData() {
        super.initData();




        mPresenter = new AddCargoInformationPresenter(this);
    }

    @Override
    public void initWidget() {
        super.initWidget();
        ActivityTitleUtils.initToolbar(aty, getString(R.string.addCargoInformation), true, R.id.titlebar);
    }

    @Override
    public void widgetClick(View v) {
        super.widgetClick(v);
        switch (v.getId()) {
            case R.id.img_cargoReceipt:
                Intent intent = new Intent(aty, FillCargoReceiptFormActivity.class);
                intent.putExtra("cargoReceipt", cargoReceipt);
                startActivityForResult(intent, REQUEST_CODE_CHOOSE_PHOTO);
                break;
            case R.id.ll_selectVehicle:
                Intent selectVehicleIntent = new Intent(aty, SelectVehicleActivity.class);
                selectVehicleIntent.putExtra("vehicleModelId", vehicleModelId);
                selectVehicleIntent.putExtra("vehicleLengthId", vehicleLengthId);
                startActivityForResult(selectVehicleIntent, REQUEST_CODE_PHOTO_PREVIEW);
                break;
            case R.id.img_driverCargo:
                if (true) {
                    img_driverCargo.setImageResource(R.mipmap.switch_btn_off);
                } else {
                    img_driverCargo.setImageResource(R.mipmap.switch_btn_on);
                }
                break;
            case R.id.tv_submit:


                break;
        }
    }

    @Override
    public void setPresenter(AddCargoInformationContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void getSuccess(String success, int flag) {


    }

    @Override
    public void errorMsg(String msg, int flag) {
        ViewInject.toast(msg);
    }

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
//            vehicleModel = data.getStringExtra("vehicleModel");
//            vehicleLength = data.getStringExtra("vehicleLength");
//            tv_vehicleRequirements.setText(getString(R.string.vehicleModel) + vehicleModel + "  " + getString(R.string.vehicleLength) + vehicleLength);
//            m = data.getStringExtra("init_kilometres");
//            initiatePrice = data.getStringExtra("init_price");
//            kmFee = data.getStringExtra("over_metres_price");
//            freight = data.getStringExtra("weight_price");
            vehicleModelId = data.getIntExtra("vehicleModelId", 0);
            vehicleLengthId = data.getIntExtra("vehicleLengthId", 0);
        }
    }
}
