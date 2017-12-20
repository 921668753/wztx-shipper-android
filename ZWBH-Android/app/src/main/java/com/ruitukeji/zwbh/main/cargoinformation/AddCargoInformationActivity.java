package com.ruitukeji.zwbh.main.cargoinformation;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.common.BaseActivity;
import com.ruitukeji.zwbh.common.BindView;
import com.ruitukeji.zwbh.utils.ActivityTitleUtils;

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

    @Override
    public void setRootView() {
        setContentView(R.layout.activity_addcargoinformation);
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

    }
}
