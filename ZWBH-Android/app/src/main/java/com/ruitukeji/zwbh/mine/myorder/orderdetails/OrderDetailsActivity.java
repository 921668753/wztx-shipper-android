package com.ruitukeji.zwbh.mine.myorder.orderdetails;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kymjs.common.PreferenceHelper;
import com.kymjs.common.StringUtils;
import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.common.BaseActivity;
import com.ruitukeji.zwbh.common.BindView;
import com.ruitukeji.zwbh.common.ViewInject;
import com.ruitukeji.zwbh.constant.NumericConstants;
import com.ruitukeji.zwbh.constant.StringConstants;
import com.ruitukeji.zwbh.entity.OrderDetailsBean;
import com.ruitukeji.zwbh.loginregister.LoginActivity;
import com.ruitukeji.zwbh.mine.myorder.orderfragment.CheckVoucherActivity;
import com.ruitukeji.zwbh.mine.myorder.orderfragment.EvaluationShareActivity;
import com.ruitukeji.zwbh.mine.myorder.orderfragment.LogisticsPositioningActivity;
import com.ruitukeji.zwbh.mine.myorder.quotationlist.QuotationListActivity;
import com.ruitukeji.zwbh.utils.ActivityTitleUtils;
import com.ruitukeji.zwbh.utils.JsonUtil;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

import static com.ruitukeji.zwbh.constant.NumericConstants.REQUEST_CODE_PERMISSION_CALL;

/**
 * 订单详情
 * Created by Administrator on 2017/2/16.
 */

public class OrderDetailsActivity extends BaseActivity implements OrderDetailsContract.View, EasyPermissions.PermissionCallbacks {


    /**
     * 订单状态
     */
    @BindView(id = R.id.tv_orderStatus)
    private TextView tv_orderStatus;

    /**
     * 订单时间
     */
    @BindView(id = R.id.tv_orderTime)
    private TextView tv_orderTime;

    /**
     * 订单预约时间
     */
    @BindView(id = R.id.ll_appointmentTime)
    private LinearLayout ll_appointmentTime;
    @BindView(id = R.id.tv_appointmentTime)
    private TextView tv_appointmentTime;

    /**
     * 订单号
     */
    @BindView(id = R.id.tv_orderNumber)
    private TextView tv_orderNumber;

    /**
     * 预估价格
     */
    @BindView(id = R.id.tv_rderForecastPrice)
    private TextView tv_rderForecastPrice;

    /**
     * 实际支付价格
     */
    @BindView(id = R.id.tv_actualPayment)
    private TextView tv_actualPayment;

    /**
     * 公司名称
     */
    @BindView(id = R.id.tv_companyName)
    private TextView tv_companyName;

    /**
     * 货物名称
     */
    @BindView(id = R.id.tv_descriptionGoods)
    private TextView tv_descriptionGoods;


    /**
     * 重量
     */
    @BindView(id = R.id.tv_weight)
    private TextView tv_weight;

    /**
     * 体积
     */
    @BindView(id = R.id.tv_volume)
    private TextView tv_volume;

    /**
     * 货物签收单
     */
    @BindView(id = R.id.tv_cargoReceipt)
    private TextView tv_cargoReceipt;

    /**
     * 起运地
     */
    @BindView(id = R.id.tv_place)
    private TextView tv_place;

    /**
     * 联系方式
     */
    @BindView(id = R.id.tv_contactInformation)
    private TextView tv_contactInformation;

    /**
     * 固定电话
     */
    @BindView(id = R.id.ll_eixedTelephone)
    private LinearLayout ll_eixedTelephone;
    @BindView(id = R.id.tv_eixedTelephone)
    private TextView tv_eixedTelephone;


    /**
     * 发货客户
     */
    @BindView(id = R.id.ll_deliveryCustomer)
    private LinearLayout ll_deliveryCustomer;
    @BindView(id = R.id.tv_deliveryCustomer)
    private TextView tv_deliveryCustomer;


    /**
     * 联系人
     */
    @BindView(id = R.id.tv_contactPerson)
    private TextView tv_contactPerson;

    /**
     * 目的地
     */
    @BindView(id = R.id.tv_destination)
    private TextView tv_destination;

    /**
     * 联系方式
     */
    @BindView(id = R.id.tv_contactInformation1)
    private TextView tv_contactInformation1;

    /**
     * 固定电话
     */
    @BindView(id = R.id.ll_eixedTelephone1)
    private LinearLayout ll_eixedTelephone1;
    @BindView(id = R.id.tv_eixedTelephone1)
    private TextView tv_eixedTelephone1;

    /**
     * 收货客户
     */
    @BindView(id = R.id.ll_receivingCustomer)
    private LinearLayout ll_receivingCustomer;
    @BindView(id = R.id.tv_receivingCustomer)
    private TextView tv_receivingCustomer;

    /**
     * 联系人
     */
    @BindView(id = R.id.tv_contactPerson1)
    private TextView tv_contactPerson1;

    /**
     * 车长
     */
    @BindView(id = R.id.tv_conductor)
    private TextView tv_conductor;

    /**
     * 车型
     */
    @BindView(id = R.id.tv_models)
    private TextView tv_models;

    /**
     * 司机装卸货
     */
    @BindView(id = R.id.tv_driverCargo)
    private TextView tv_driverCargo;

    /**
     * 运输时长
     */
    @BindView(id = R.id.ll_transportTime)
    private LinearLayout ll_transportTime;

    @BindView(id = R.id.tv_transportTime)
    private TextView tv_transportTime;

    /**
     * 配送点
     */
    @BindView(id = R.id.tv_peiSongDian)
    private TextView tv_peiSongDian;

    /**
     * 配送点费用
     */
    @BindView(id = R.id.tv_costDistribution)
    private TextView tv_costDistribution;

    /**
     * 收藏
     */
    @BindView(id = R.id.ll_driverInformation)
    private LinearLayout ll_driverInformation;
    @BindView(id = R.id.tv_collect, click = true)
    private TextView tv_collect;

    /**
     * 投诉
     */
    @BindView(id = R.id.tv_complaints, click = true)
    private TextView tv_complaints;

    /**
     * 司机姓名
     */
    @BindView(id = R.id.ll_userName)
    private LinearLayout ll_userName;
    @BindView(id = R.id.tv_userName)
    private TextView tv_userName;

    /**
     * 车牌号
     */
    @BindView(id = R.id.ll_licensePlateNumber)
    private LinearLayout ll_licensePlateNumber;
    @BindView(id = R.id.tv_licensePlateNumber)
    private TextView tv_licensePlateNumber;

    /**
     * 车长
     */
    @BindView(id = R.id.ll_vehicleInformation)
    private LinearLayout ll_vehicleInformation;
    @BindView(id = R.id.tv_conductor1)
    private TextView tv_conductor1;

    /**
     * 车型
     */
    @BindView(id = R.id.tv_models1)
    private TextView tv_models1;


    /**
     * 底部按钮
     */
    @BindView(id = R.id.ll_bottom)
    private LinearLayout ll_bottom;


    /**
     * 查看异常
     */
    @BindView(id = R.id.tv_checkAbnormal, click = true)
    private TextView tv_checkAbnormal;

    /**
     * 取消订单
     */
    @BindView(id = R.id.tv_cancelOrder, click = true)
    private TextView tv_cancelOrder;

    /**
     * 查看报价
     */
    @BindView(id = R.id.tv_viewQuotation, click = true)
    private TextView tv_viewQuotation;

    /**
     * 查看运输轨迹
     */
    @BindView(id = R.id.tv_viewShippingTrack, click = true)
    private TextView tv_viewShippingTrack;

    /**
     * 确认支付
     */
    @BindView(id = R.id.tv_confirmPayment, click = true)
    private TextView tv_confirmPayment;


    /**
     * 联系司机
     */
    @BindView(id = R.id.tv_contactDriver, click = true)
    private TextView tv_contactDriver;

    /**
     * 评价司机
     */
    @BindView(id = R.id.tv_evaluationDriver, click = true)
    private TextView tv_evaluationDriver;

    /**
     * 查看评价
     */
    @BindView(id = R.id.tv_seeEvaluation, click = true)
    private TextView tv_seeEvaluation;

    /**
     * 订单状态
     */
    private String status = "";

    private OrderDetailsContract.Presenter mPresenter;

    private int orderId = 0;
    private int is_collect = 0;

    @Override
    public void setRootView() {
        setContentView(R.layout.activity_orderdetails1);
    }

    @Override
    public void initData() {
        super.initData();
        mPresenter = new OrderDetailsPresenter(this);
        orderId = getIntent().getIntExtra("order_id", 0);
    }

    @Override
    public void initWidget() {
        super.initWidget();
        ActivityTitleUtils.initToolbar(aty, getString(R.string.orderDetails), true, R.id.titlebar);
        showLoadingDialog(getString(R.string.dataLoad));
        ((OrderDetailsContract.Presenter) mPresenter).getOrderDetails(orderId);
    }


    @Override
    public void widgetClick(View v) {
        super.widgetClick(v);
        switch (v.getId()) {
            case R.id.tv_contactInformation:
                choiceCallWrapper("", tv_contactInformation.getText().toString());
                break;
            case R.id.tv_eixedTelephone:
                choiceCallWrapper("", tv_eixedTelephone.getText().toString());
                break;
            case R.id.tv_contactInformation1:
                choiceCallWrapper("", tv_contactInformation1.getText().toString());
                break;
            case R.id.tv_eixedTelephone1:
                choiceCallWrapper("", tv_eixedTelephone1.getText().toString());
                break;
            case R.id.tv_collect:
                if (is_collect == 1) {

                    return;
                }


                break;
            case R.id.tv_complaints:


                break;
            case R.id.tv_checkAbnormal:

                break;
            case R.id.tv_cancelOrder:
                //保单详情

                break;
            case R.id.tv_viewQuotation:

                break;
            case R.id.tv_viewShippingTrack:

                break;
            case R.id.tv_confirmPayment:

                break;
            case R.id.tv_contactDriver:

                break;
            case R.id.tv_evaluationDriver:

                break;
            case R.id.tv_seeEvaluation:

                break;
        }
    }

    @Override
    public void setPresenter(OrderDetailsContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void getSuccess(String success, int flag) {
        OrderDetailsBean orderDetailsBean = (OrderDetailsBean) JsonUtil.getInstance().json2Obj(success, OrderDetailsBean.class);
        OrderDetailsBean.ResultBean result = orderDetailsBean.getResult();
        status = result.getStatus();
        getOrderStatus(status);
        tv_orderTime.setText(result.getCreate_at());
        if (!StringUtils.isEmpty(result.getType()) && result.getType().equals("appoint")) {
            ll_appointmentTime.setVisibility(View.VISIBLE);
            tv_appointmentTime.setText(result.getAppoint_at());
        } else {
            ll_appointmentTime.setVisibility(View.GONE);
        }
        tv_orderNumber.setText(result.getOrder_code());
        tv_rderForecastPrice.setText(result.getSystem_price());
        tv_actualPayment.setText(result.getFinal_price());
        tv_descriptionGoods.setText(result.getGoods_name());
        tv_weight.setText(result.getWeight());
        tv_volume.setText(result.getVolume());
        if (result.getIs_cargo_receipt() == 1) {
            tv_cargoReceipt.setText(getString(R.string.require));
        } else {
            tv_cargoReceipt.setText(getString(R.string.noRequire));
        }
        tv_place.setText(result.getOrg_address_name() + result.getOrg_address_detail());
        tv_contactInformation.setText(result.getOrg_phone());
        if (StringUtils.isEmpty(result.getOrg_telphone())) {
            ll_eixedTelephone.setVisibility(View.GONE);
        } else {
            ll_eixedTelephone.setVisibility(View.VISIBLE);
            tv_eixedTelephone.setText(result.getOrg_telphone());
        }
        if (StringUtils.isEmpty(result.getOrg_send_client())) {
            ll_deliveryCustomer.setVisibility(View.GONE);
        } else {
            ll_deliveryCustomer.setVisibility(View.VISIBLE);
            tv_deliveryCustomer.setText(result.getOrg_send_client());
        }
        tv_contactPerson.setText(result.getOrg_send_name());
        tv_destination.setText(result.getDest_address_name() + result.getOrg_address_detail());
        tv_contactInformation1.setText(result.getDest_phone());

        if (StringUtils.isEmpty(result.getDest_telphone())) {
            ll_eixedTelephone1.setVisibility(View.GONE);
        } else {
            ll_eixedTelephone1.setVisibility(View.VISIBLE);
            tv_eixedTelephone1.setText(result.getDest_telphone());
        }
        if (StringUtils.isEmpty(result.getDest_receive_client())) {
            ll_receivingCustomer.setVisibility(View.GONE);
        } else {
            ll_receivingCustomer.setVisibility(View.VISIBLE);
            tv_receivingCustomer.setText(result.getDest_receive_client());
        }
        tv_contactPerson1.setText(result.getDest_receive_name());
        tv_conductor.setText(result.getCar_style_length());
        tv_models.setText(result.getCar_style_type());
        if (result.getIs_driver_dock() == 1) {
            tv_driverCargo.setText(getString(R.string.require));
        } else {
            tv_driverCargo.setText(getString(R.string.noRequire));
        }
        if (StringUtils.isEmpty(result.getUsecar_time()) || result.getUsecar_time().equals("0")) {
            ll_transportTime.setVisibility(View.GONE);
        } else {
            ll_transportTime.setVisibility(View.VISIBLE);
            tv_transportTime.setText(result.getUsecar_time());
        }
        tv_peiSongDian.setText(result.getSpot());
        tv_costDistribution.setText(result.getSpot_cost());

        is_collect = result.getIs_collect();
        if (is_collect == 1) {
            tv_collect.setText(getString(R.string.cancelCollection));
        } else {
            tv_collect.setText(getString(R.string.collect));
        }
        tv_userName.setText(result.getDr_name());
        tv_licensePlateNumber.setText(result.getCard_number());
        tv_conductor1.setText(result.getCar_length_info());
        tv_models1.setText(result.getCar_style_info());
        if (result.getIs_quote() == 1) {
            tv_viewQuotation.setVisibility(View.VISIBLE);
        } else {
            tv_viewQuotation.setVisibility(View.GONE);
        }
        dismissLoadingDialog();
    }

    @Override
    public void errorMsg(String msg, int flag) {
        toLigon1(msg);
        dismissLoadingDialog();
    }

    /**
     * 根据当前状态更改订单详情布局
     * 1.报价中  2.待发货 3.发货中 4.已完成  5.评价 6.分享
     *
     * @param status
     */
    public void getOrderStatus(String status) {
        if (!StringUtils.isEmpty(status) && status.equals("hang")) {
            tv_orderStatus.setText(getString(R.string.hang));
            ll_bottom.setVisibility(View.GONE);
            ll_userName.setVisibility(View.GONE);
            ll_licensePlateNumber.setVisibility(View.GONE);
            ll_vehicleInformation.setVisibility(View.GONE);
        } else if (!StringUtils.isEmpty(status) && status.equals("quote")) {
            tv_orderStatus.setText(getString(R.string.pendingOrder));
            ll_bottom.setVisibility(View.VISIBLE);
            tv_checkAbnormal.setVisibility(View.GONE);
            tv_cancelOrder.setVisibility(View.VISIBLE);
            tv_viewShippingTrack.setVisibility(View.GONE);
            tv_confirmPayment.setVisibility(View.GONE);
            tv_contactDriver.setVisibility(View.GONE);
            tv_evaluationDriver.setVisibility(View.GONE);
            tv_seeEvaluation.setVisibility(View.GONE);
            ll_driverInformation.setVisibility(View.GONE);
            ll_userName.setVisibility(View.GONE);
            ll_licensePlateNumber.setVisibility(View.GONE);
            ll_vehicleInformation.setVisibility(View.GONE);
        } else if (!StringUtils.isEmpty(status) && status.equals("quoted")) {
            tv_orderStatus.setText(getString(R.string.pendingDelivery));
            ll_bottom.setVisibility(View.VISIBLE);
            tv_checkAbnormal.setVisibility(View.GONE);
            tv_cancelOrder.setVisibility(View.VISIBLE);
            tv_viewQuotation.setVisibility(View.GONE);
            tv_viewShippingTrack.setVisibility(View.GONE);
            tv_confirmPayment.setVisibility(View.GONE);
            tv_contactDriver.setVisibility(View.VISIBLE);
            tv_evaluationDriver.setVisibility(View.GONE);
            tv_seeEvaluation.setVisibility(View.GONE);
            ll_userName.setVisibility(View.VISIBLE);
            ll_licensePlateNumber.setVisibility(View.VISIBLE);
            ll_vehicleInformation.setVisibility(View.VISIBLE);
        } else if (!StringUtils.isEmpty(status) && status.equals("distribute") || !StringUtils.isEmpty(status) && status.equals("arrive")) {
            tv_orderStatus.setText(getString(R.string.transportation));
            ll_bottom.setVisibility(View.VISIBLE);
            tv_cancelOrder.setVisibility(View.GONE);
            tv_viewQuotation.setVisibility(View.GONE);
            tv_viewShippingTrack.setVisibility(View.VISIBLE);
            tv_contactDriver.setVisibility(View.VISIBLE);
            tv_confirmPayment.setVisibility(View.GONE);
            tv_evaluationDriver.setVisibility(View.GONE);
            tv_seeEvaluation.setVisibility(View.GONE);
            ll_userName.setVisibility(View.VISIBLE);
            ll_licensePlateNumber.setVisibility(View.VISIBLE);
            ll_vehicleInformation.setVisibility(View.VISIBLE);
        } else if (!StringUtils.isEmpty(status) && status.equals("photo") || !StringUtils.isEmpty(status) && status.equals("pay_failed")) {
            tv_orderStatus.setText(getString(R.string.pendingPayment));
            ll_bottom.setVisibility(View.VISIBLE);
            tv_cancelOrder.setVisibility(View.GONE);
            tv_viewQuotation.setVisibility(View.GONE);
            tv_viewShippingTrack.setVisibility(View.GONE);
            tv_contactDriver.setVisibility(View.GONE);
            tv_confirmPayment.setVisibility(View.VISIBLE);
            tv_evaluationDriver.setVisibility(View.GONE);
            tv_seeEvaluation.setVisibility(View.GONE);
            ll_userName.setVisibility(View.VISIBLE);
            ll_licensePlateNumber.setVisibility(View.VISIBLE);
            ll_vehicleInformation.setVisibility(View.VISIBLE);
        } else if (!StringUtils.isEmpty(status) && status.equals("pay_success")) {
            tv_orderStatus.setText(getString(R.string.pendingPayment));
            ll_bottom.setVisibility(View.VISIBLE);
            tv_cancelOrder.setVisibility(View.GONE);
            tv_viewQuotation.setVisibility(View.GONE);
            tv_viewShippingTrack.setVisibility(View.VISIBLE);
            tv_contactDriver.setVisibility(View.GONE);
            tv_confirmPayment.setVisibility(View.GONE);
            tv_evaluationDriver.setVisibility(View.VISIBLE);
            tv_seeEvaluation.setVisibility(View.GONE);
            ll_userName.setVisibility(View.VISIBLE);
            ll_licensePlateNumber.setVisibility(View.VISIBLE);
            ll_vehicleInformation.setVisibility(View.VISIBLE);
        } else if (!StringUtils.isEmpty(status) && status.equals("comment")) {
            tv_orderStatus.setText(getString(R.string.pendingPayment));
            ll_bottom.setVisibility(View.VISIBLE);
            tv_cancelOrder.setVisibility(View.GONE);
            tv_viewQuotation.setVisibility(View.GONE);
            tv_viewShippingTrack.setVisibility(View.VISIBLE);
            tv_contactDriver.setVisibility(View.GONE);
            tv_confirmPayment.setVisibility(View.GONE);
            tv_evaluationDriver.setVisibility(View.GONE);
            tv_seeEvaluation.setVisibility(View.VISIBLE);
            ll_userName.setVisibility(View.VISIBLE);
            ll_licensePlateNumber.setVisibility(View.VISIBLE);
            ll_vehicleInformation.setVisibility(View.VISIBLE);
        } else if (!StringUtils.isEmpty(status) && status.equals("cancel")) {
            tv_orderStatus.setText(getString(R.string.canceledOrder));
            ll_bottom.setVisibility(View.GONE);
            ll_userName.setVisibility(View.GONE);
            ll_licensePlateNumber.setVisibility(View.GONE);
            ll_vehicleInformation.setVisibility(View.GONE);
        }
    }


    @AfterPermissionGranted(REQUEST_CODE_PERMISSION_CALL)
    private void choiceCallWrapper(String title, String phone) {
        String[] perms = {Manifest.permission.CALL_PHONE};
        if (EasyPermissions.hasPermissions(this, perms)) {


        } else {
            EasyPermissions.requestPermissions(this, getString(R.string.phoneCallPermissions), REQUEST_CODE_PERMISSION_CALL, perms);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        if (requestCode == REQUEST_CODE_PERMISSION_CALL) {
            ViewInject.toast(getString(R.string.phoneCallPermissions1));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();


    }
}