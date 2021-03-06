package com.ruitukeji.zwbh.mine.myorder.orderdetails;

import android.Manifest;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kymjs.common.StringUtils;
import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.common.BaseActivity;
import com.ruitukeji.zwbh.common.BindView;
import com.ruitukeji.zwbh.common.ViewInject;
import com.ruitukeji.zwbh.entity.mine.myorder.orderdetails.OrderDetailsBean;
import com.ruitukeji.zwbh.main.dialog.AuthenticationBouncedDialog;
import com.ruitukeji.zwbh.mine.abnormalrecords.AbnormalRecordsActivity;
import com.ruitukeji.zwbh.mine.myorder.dialog.CancelOrderBouncedDialog;
import com.ruitukeji.zwbh.mine.myorder.dialog.ContactDriverBouncedDialog;
import com.ruitukeji.zwbh.mine.myorder.dialog.ReleaseAgainOrderBouncedDialog;
import com.ruitukeji.zwbh.mine.myorder.orderdetails.dialog.ComplaintsAboutDriverBouncedDialog;
import com.ruitukeji.zwbh.mine.myorder.logisticspositioning.LogisticsPositioningActivity;
import com.ruitukeji.zwbh.mine.myorder.payment.CheckVoucherActivity;
import com.ruitukeji.zwbh.mine.myorder.quotationlist.QuotationListActivity;
import com.ruitukeji.zwbh.utils.ActivityTitleUtils;
import com.ruitukeji.zwbh.utils.JsonUtil;
import com.ruitukeji.zwbh.utils.rx.MsgEvent;
import com.ruitukeji.zwbh.utils.rx.RxBus;

import java.util.List;

import cn.bingoogolapple.titlebar.BGATitleBar;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

import static com.ruitukeji.zwbh.constant.NumericConstants.REQUEST_CODE_CHOOSE_PHOTO;
import static com.ruitukeji.zwbh.constant.NumericConstants.REQUEST_CODE_PERMISSION_CALL;
import static com.ruitukeji.zwbh.constant.NumericConstants.REQUEST_CODE_PREVIEW;
import static com.ruitukeji.zwbh.constant.NumericConstants.REQUEST_CODE_SELECT;

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

    @BindView(id = R.id.tv_checkReceipt, click = true)
    private TextView tv_checkReceipt;

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
     * 再次发布
     */
    @BindView(id = R.id.tv_releaseAgain, click = true)
    private TextView tv_releaseAgain;

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
     * 拒绝取消
     */
    @BindView(id = R.id.tv_refusedLift, click = true)
    private TextView tv_refusedLift;

    /**
     * 同意取消
     */
    @BindView(id = R.id.tv_agreedCancel, click = true)
    private TextView tv_agreedCancel;

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

    private CancelOrderBouncedDialog cancelOrderBouncedDialog = null;
    private int is_cancel = 0;
    private int dr_id = 0;
    private ComplaintsAboutDriverBouncedDialog complaintsAboutDriverBouncedDialog = null;
    private String map_code = "";
    private String dr_name = "";
    private String dr_phone = "";
    private String card_number = "";
    private String org_address = "";
    private String dest_address = "";
    private String org_address_maps = "";
    private String dest_address_maps = "";
    private String avatar = "";
    private ContactDriverBouncedDialog contactDriverBouncedDialog = null;
    private String per_status = "init";
    private boolean isRefresh = false;
    private AuthenticationBouncedDialog authenticationBouncedDialog1 = null;
    private AuthenticationBouncedDialog authenticationBouncedDialog2 = null;
    private int is_refuse_order = 0;
    private ReleaseAgainOrderBouncedDialog releaseAgainOrderBouncedDialog = null;

    @Override
    public void setRootView() {
        setContentView(R.layout.activity_orderdetails);
    }

    @Override
    public void initData() {
        super.initData();
        mPresenter = new OrderDetailsPresenter(this);
        orderId = getIntent().getIntExtra("order_id", 0);
        is_refuse_order = getIntent().getIntExtra("is_refuse_order", 0);
    }

    @Override
    public void initWidget() {
        super.initWidget();
        //   ActivityTitleUtils.initToolbar(aty, , true, R.id.titlebar);

        BGATitleBar.SimpleDelegate simpleDelegate = new BGATitleBar.SimpleDelegate() {
            @Override
            public void onClickLeftCtv() {
                super.onClickLeftCtv();
                if (isRefresh) {
                    Intent intent = new Intent();
                    setResult(RESULT_OK, intent);
                }
                finish();
            }

            @Override
            public void onClickRightCtv() {
                super.onClickRightCtv();

            }
        };
        ActivityTitleUtils.initToolbar(aty, getString(R.string.orderDetails), "", R.id.titlebar, simpleDelegate);
        showLoadingDialog(getString(R.string.dataLoad));
        ((OrderDetailsContract.Presenter) mPresenter).getOrderDetails(orderId);
    }


    @Override
    public void widgetClick(View v) {
        super.widgetClick(v);
        switch (v.getId()) {
            case R.id.tv_contactInformation:
                choiceCallWrapper(tv_contactInformation.getText().toString());
                break;
            case R.id.tv_eixedTelephone:
                choiceCallWrapper(tv_eixedTelephone.getText().toString());
                break;
            case R.id.tv_contactInformation1:
                choiceCallWrapper(tv_contactInformation1.getText().toString());
                break;
            case R.id.tv_eixedTelephone1:
                choiceCallWrapper(tv_eixedTelephone1.getText().toString());
                break;
            case R.id.tv_checkReceipt:
                Intent checkReceiptIntent = new Intent(aty, CheckReceiptActivity.class);
                checkReceiptIntent.putExtra("order_id", orderId);
                showActivity(aty, checkReceiptIntent);
                break;
            case R.id.tv_collect:
                if (dr_id == 0) {
                    errorMsg(getString(R.string.serverReturnsDataError), 0);
                    return;
                }
                if (is_collect == 1) {
                    showLoadingDialog(getString(R.string.dataLoad));
                    ((OrderDetailsContract.Presenter) mPresenter).postDelCollectDriver(dr_id);
                    return;
                }
                showLoadingDialog(getString(R.string.dataLoad));
                ((OrderDetailsContract.Presenter) mPresenter).postCollectDriver(dr_id);
                break;
            case R.id.tv_complaints:
                if (dr_id == 0) {
                    errorMsg(getString(R.string.serverReturnsDataError), 0);
                    return;
                }
                if (complaintsAboutDriverBouncedDialog != null && !complaintsAboutDriverBouncedDialog.isShowing()) {
                    complaintsAboutDriverBouncedDialog.show();
                    complaintsAboutDriverBouncedDialog.setDriverId(dr_id);
                    break;
                }
                complaintsAboutDriverBouncedDialog = new ComplaintsAboutDriverBouncedDialog(aty, dr_id);
                complaintsAboutDriverBouncedDialog.show();
                break;
            case R.id.tv_checkAbnormal:
                Intent checkAbnormalIntent = new Intent(aty, AbnormalRecordsActivity.class);
                checkAbnormalIntent.putExtra("order_id", orderId);
                startActivity(checkAbnormalIntent);
                break;
            case R.id.tv_cancelOrder:
                int type = 0;
                if (!StringUtils.isEmpty(status) && status.equals("quote") && is_cancel == 0) {
                    type = 0;
                } else if (!StringUtils.isEmpty(status) && status.equals("quoted") && is_cancel == 0) {
                    type = 1;
                } else {
                    ViewInject.toast(getString(R.string.serverReturnsDataError));
                    return;
                }
                if (cancelOrderBouncedDialog != null && !cancelOrderBouncedDialog.isShowing()) {
                    cancelOrderBouncedDialog.show();
                    cancelOrderBouncedDialog.setOrderId(orderId, type);
                    return;
                }
                cancelOrderBouncedDialog = new CancelOrderBouncedDialog(aty, orderId, type) {
                    @Override
                    public void confirm() {
                        this.cancel();
                        showLoadingDialog(getString(R.string.dataLoad));
                        ((OrderDetailsContract.Presenter) mPresenter).getOrderDetails(orderId);
                    }
                };
                cancelOrderBouncedDialog.show();
                break;
            case R.id.tv_releaseAgain:
                if (is_refuse_order == 0) {
                    return;
                }
                if (releaseAgainOrderBouncedDialog != null && !releaseAgainOrderBouncedDialog.isShowing()) {
                    releaseAgainOrderBouncedDialog.show();
                    releaseAgainOrderBouncedDialog.setOrderId(orderId);
                    return;
                }
                releaseAgainOrderBouncedDialog = new ReleaseAgainOrderBouncedDialog(aty, orderId) {
                    @Override
                    public void confirm() {
                        this.cancel();
                        ((OrderDetailsContract.Presenter) mPresenter).getOrderDetails(orderId);
                        isRefresh = true;
                    }
                };
                releaseAgainOrderBouncedDialog.show();
                break;
            case R.id.tv_viewQuotation:
                Intent intent = new Intent(aty, QuotationListActivity.class);
                intent.putExtra("order_id", orderId);
                startActivityForResult(intent, REQUEST_CODE_SELECT);
                break;
            case R.id.tv_viewShippingTrack:
                Intent viewShippingTrackIntent = new Intent(aty, LogisticsPositioningActivity.class);
                viewShippingTrackIntent.putExtra("map_code", map_code);
                viewShippingTrackIntent.putExtra("real_name", dr_name);
                viewShippingTrackIntent.putExtra("card_number", card_number);
                viewShippingTrackIntent.putExtra("avatar", avatar);
                viewShippingTrackIntent.putExtra("phone", dr_phone);
                viewShippingTrackIntent.putExtra("org_address", org_address);
                viewShippingTrackIntent.putExtra("org_address_maps", org_address_maps);
                viewShippingTrackIntent.putExtra("dest_address_maps", dest_address_maps);
                viewShippingTrackIntent.putExtra("dest_address", dest_address);
                viewShippingTrackIntent.putExtra("status", status);
                startActivity(viewShippingTrackIntent);
                break;
            case R.id.tv_confirmPayment:
                Intent checkVoucherIntent = new Intent(aty, CheckVoucherActivity.class);
                checkVoucherIntent.putExtra("order_id", orderId);
                checkVoucherIntent.putExtra("total_amount", tv_actualPayment.getText().toString().trim());
                checkVoucherIntent.putExtra("per_status", per_status);
                startActivityForResult(checkVoucherIntent, REQUEST_CODE_PREVIEW);
                break;
            case R.id.tv_refusedLift:
                if (authenticationBouncedDialog1 != null && !authenticationBouncedDialog1.isShowing()) {
                    authenticationBouncedDialog1.show();
                    return;
                }
                //不同意
                authenticationBouncedDialog1 = new AuthenticationBouncedDialog(this, getString(R.string.confirmAgreeCancelOrder1)) {
                    @Override
                    public void confirm() {
                        this.cancel();
                        //不同意
                        // showLoadingDialog(getString(R.string.submissionLoad));
                        ((OrderDetailsContract.Presenter) mPresenter).postCancelGoodsComplete(orderId, 0);
                    }
                };
                authenticationBouncedDialog1.show();
                break;
            case R.id.tv_agreedCancel:
                if (authenticationBouncedDialog2 != null && !authenticationBouncedDialog2.isShowing()) {
                    authenticationBouncedDialog2.show();
                    return;
                }
                //同意
                authenticationBouncedDialog2 = new AuthenticationBouncedDialog(this, getString(R.string.confirmAgreeCancelOrder)) {
                    @Override
                    public void confirm() {
                        this.cancel();
                        //同意
                        //   showLoadingDialog(getString(R.string.submissionLoad));
                        ((OrderDetailsContract.Presenter) mPresenter).postCancelGoodsComplete(orderId, 1);
                    }
                };
                authenticationBouncedDialog2.show();
                break;

            case R.id.tv_contactDriver:
                choiceCallWrapper(dr_phone);
                break;
            case R.id.tv_evaluationDriver:
                Intent evaluationDriverIntent1 = new Intent(aty, EvaluationDriverActivity.class);
                evaluationDriverIntent1.putExtra("order_id", orderId);
                evaluationDriverIntent1.putExtra("status", status);
                startActivityForResult(evaluationDriverIntent1, REQUEST_CODE_CHOOSE_PHOTO);
                break;
            case R.id.tv_seeEvaluation:
                Intent evaluationDriverIntent = new Intent(aty, EvaluationDriverActivity.class);
                evaluationDriverIntent.putExtra("order_id", orderId);
                evaluationDriverIntent.putExtra("status", status);
                startActivity(evaluationDriverIntent);
                break;
        }
    }

    @Override
    public void setPresenter(OrderDetailsContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void getSuccess(String success, int flag) {
        if (flag == 0) {
            OrderDetailsBean orderDetailsBean = (OrderDetailsBean) JsonUtil.getInstance().json2Obj(success, OrderDetailsBean.class);
            OrderDetailsBean.ResultBean result = orderDetailsBean.getResult();
            if (result == null) {
                return;
            }
            status = result.getStatus();
            is_cancel = result.getIs_cancel();
            getOrderStatus(status);
            avatar = result.getAvatar();
            dr_id = StringUtils.toInt(result.getDr_id(), 0);
            map_code = result.getMap_code();
            dr_name = result.getDr_name();
            dr_phone = result.getDr_phone();
            card_number = result.getCard_number();
            tv_orderTime.setText(result.getCreate_at());
            org_address = result.getOrg_address_name() + result.getOrg_address_detail();
            dest_address = result.getDest_address_name() + result.getDest_address_detail();
            org_address_maps = result.getOrg_address_maps();
            dest_address_maps = result.getDest_address_maps();
            per_status = result.getPer_status();
            if (!StringUtils.isEmpty(result.getType()) && result.getType().equals("appoint")) {
                ll_appointmentTime.setVisibility(View.VISIBLE);
                tv_appointmentTime.setText(result.getAppoint_at());
            } else {
                ll_appointmentTime.setVisibility(View.GONE);
            }
            tv_orderNumber.setText(result.getOrder_code());
            tv_rderForecastPrice.setText(result.getSystem_price());
            if (!StringUtils.isEmpty(status) && status.equals("quote") && !StringUtils.isEmpty(result.getMind_price()) && StringUtils.toDouble(result.getMind_price()) > 0) {
                tv_actualPayment.setText(result.getMind_price());
            } else if (!StringUtils.isEmpty(result.getFinal_price()) && StringUtils.toDouble(result.getFinal_price()) > 0) {
                tv_actualPayment.setText(result.getFinal_price());
            } else {
                tv_actualPayment.setText(result.getSystem_price());
            }
            tv_descriptionGoods.setText(result.getGoods_name());
            tv_weight.setText(result.getWeight());
            tv_volume.setText(result.getVolume());
            if (result.getIs_cargo_receipt() == 1) {
                tv_cargoReceipt.setText(getString(R.string.require));
                tv_checkReceipt.setVisibility(View.VISIBLE);
            } else {
                tv_cargoReceipt.setText(getString(R.string.noRequire));
                tv_checkReceipt.setVisibility(View.GONE);
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
            tv_destination.setText(result.getDest_address_name() + result.getDest_address_detail());
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
            if (StringUtils.isEmpty(result.getSpot())) {
                tv_peiSongDian.setText("0");
            } else {
                tv_peiSongDian.setText(result.getSpot());
            }
            if (StringUtils.isEmpty(result.getSpot_cost())) {
                tv_costDistribution.setText("0.00");
            } else {
                tv_costDistribution.setText(result.getSpot_cost());
            }
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
            if (!StringUtils.isEmpty(status) && status.equals("quote") && result.getIs_quote() == 1) {
                tv_viewQuotation.setVisibility(View.VISIBLE);
            } else {
                tv_viewQuotation.setVisibility(View.GONE);
            }
            if (!StringUtils.isEmpty(status) && status.equals("quote") || !StringUtils.isEmpty(status) && status.equals("quoted") && result.getIs_cancel() == 0) {
                tv_cancelOrder.setVisibility(View.VISIBLE);
            } else {
                tv_cancelOrder.setVisibility(View.GONE);
            }

            if (!StringUtils.isEmpty(status) && status.equals("quoted") && is_cancel == 1) {
                tv_refusedLift.setVisibility(View.VISIBLE);
                tv_agreedCancel.setVisibility(View.VISIBLE);
            } else {
                tv_refusedLift.setVisibility(View.GONE);
                tv_agreedCancel.setVisibility(View.GONE);
            }

            if (result.getIs_abnormal() == 0) {
                tv_checkAbnormal.setVisibility(View.GONE);
            } else {
                tv_checkAbnormal.setVisibility(View.VISIBLE);
            }
            RxBus.getInstance().post(new MsgEvent<String>("RxBusOrderMessageDetailsEvent"));
            dismissLoadingDialog();
        } else if (flag == 1) {
            is_collect = 1;
            tv_collect.setText(getString(R.string.cancelCollection));
            dismissLoadingDialog();
        } else if (flag == 2) {
            is_collect = 0;
            tv_collect.setText(getString(R.string.collect));
            dismissLoadingDialog();
        } else if (flag == 3) {
            isRefresh = true;
            showLoadingDialog(getString(R.string.dataLoad));
            ((OrderDetailsContract.Presenter) mPresenter).getOrderDetails(orderId);
        }
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
        } else if (!StringUtils.isEmpty(status) && status.equals("quote") && is_refuse_order == 1) {
            tv_orderStatus.setText(getString(R.string.refused));
            ll_bottom.setVisibility(View.VISIBLE);
            tv_checkAbnormal.setVisibility(View.GONE);
            tv_viewShippingTrack.setVisibility(View.GONE);
            tv_confirmPayment.setVisibility(View.GONE);
            tv_releaseAgain.setVisibility(View.VISIBLE);
            tv_contactDriver.setVisibility(View.GONE);
            tv_evaluationDriver.setVisibility(View.GONE);
            tv_seeEvaluation.setVisibility(View.GONE);
            ll_driverInformation.setVisibility(View.GONE);
            ll_userName.setVisibility(View.GONE);
            ll_licensePlateNumber.setVisibility(View.GONE);
            ll_vehicleInformation.setVisibility(View.GONE);
        } else if (!StringUtils.isEmpty(status) && status.equals("quote")) {
            tv_orderStatus.setText(getString(R.string.pendingOrder));
            ll_bottom.setVisibility(View.VISIBLE);
            tv_checkAbnormal.setVisibility(View.GONE);
            tv_viewShippingTrack.setVisibility(View.GONE);
            tv_confirmPayment.setVisibility(View.GONE);
            tv_contactDriver.setVisibility(View.GONE);
            tv_releaseAgain.setVisibility(View.GONE);
            tv_evaluationDriver.setVisibility(View.GONE);
            tv_seeEvaluation.setVisibility(View.GONE);
            ll_driverInformation.setVisibility(View.GONE);
            ll_userName.setVisibility(View.GONE);
            ll_licensePlateNumber.setVisibility(View.GONE);
            ll_vehicleInformation.setVisibility(View.GONE);
        } else if (!StringUtils.isEmpty(status) && status.equals("quoted")) {
            tv_orderStatus.setText(getString(R.string.pendingDelivery));
            ll_bottom.setVisibility(View.VISIBLE);
            tv_viewQuotation.setVisibility(View.GONE);
            tv_viewShippingTrack.setVisibility(View.GONE);
            tv_confirmPayment.setVisibility(View.GONE);
            tv_contactDriver.setVisibility(View.VISIBLE);
            tv_releaseAgain.setVisibility(View.GONE);
            tv_evaluationDriver.setVisibility(View.GONE);
            tv_seeEvaluation.setVisibility(View.GONE);
            ll_userName.setVisibility(View.VISIBLE);
            ll_licensePlateNumber.setVisibility(View.VISIBLE);
            ll_vehicleInformation.setVisibility(View.VISIBLE);
        } else if (!StringUtils.isEmpty(status) && status.equals("distribute") || !StringUtils.isEmpty(status) && status.equals("arrive")) {
            tv_orderStatus.setText(getString(R.string.transportation));
            ll_bottom.setVisibility(View.VISIBLE);
            tv_viewQuotation.setVisibility(View.GONE);
            tv_releaseAgain.setVisibility(View.GONE);
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
            tv_viewQuotation.setVisibility(View.GONE);
            tv_viewShippingTrack.setVisibility(View.GONE);
            tv_contactDriver.setVisibility(View.GONE);
            tv_confirmPayment.setVisibility(View.VISIBLE);
            tv_releaseAgain.setVisibility(View.GONE);
            tv_evaluationDriver.setVisibility(View.GONE);
            tv_seeEvaluation.setVisibility(View.GONE);
            ll_userName.setVisibility(View.VISIBLE);
            ll_licensePlateNumber.setVisibility(View.VISIBLE);
            ll_vehicleInformation.setVisibility(View.VISIBLE);
        } else if (!StringUtils.isEmpty(status) && status.equals("pay_success")) {
            tv_orderStatus.setText(getString(R.string.pendingPayment));
            ll_bottom.setVisibility(View.VISIBLE);
            tv_viewQuotation.setVisibility(View.GONE);
            tv_viewShippingTrack.setVisibility(View.VISIBLE);
            tv_contactDriver.setVisibility(View.GONE);
            tv_releaseAgain.setVisibility(View.GONE);
            tv_confirmPayment.setVisibility(View.GONE);
            tv_evaluationDriver.setVisibility(View.VISIBLE);
            tv_seeEvaluation.setVisibility(View.GONE);
            ll_userName.setVisibility(View.VISIBLE);
            ll_licensePlateNumber.setVisibility(View.VISIBLE);
            ll_vehicleInformation.setVisibility(View.VISIBLE);
        } else if (!StringUtils.isEmpty(status) && status.equals("comment")) {
            tv_orderStatus.setText(getString(R.string.completed));
            ll_bottom.setVisibility(View.VISIBLE);
            tv_viewQuotation.setVisibility(View.GONE);
            tv_viewShippingTrack.setVisibility(View.VISIBLE);
            tv_contactDriver.setVisibility(View.GONE);
            tv_releaseAgain.setVisibility(View.GONE);
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
            ll_driverInformation.setVisibility(View.GONE);
            ll_licensePlateNumber.setVisibility(View.GONE);
            ll_vehicleInformation.setVisibility(View.GONE);
        }
    }


    @AfterPermissionGranted(REQUEST_CODE_PERMISSION_CALL)
    private void choiceCallWrapper(String phone) {
        String[] perms = {Manifest.permission.CALL_PHONE};
        if (EasyPermissions.hasPermissions(this, perms)) {
            if (contactDriverBouncedDialog != null && !contactDriverBouncedDialog.isShowing()) {
                contactDriverBouncedDialog.show();
                contactDriverBouncedDialog.setPhone(phone);
                return;
            }
            if (contactDriverBouncedDialog == null) {
                contactDriverBouncedDialog = new ContactDriverBouncedDialog(aty, phone);
            }
            contactDriverBouncedDialog.show();
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SELECT && resultCode == RESULT_OK) {
            showLoadingDialog(getString(R.string.dataLoad));
            ((OrderDetailsContract.Presenter) mPresenter).getOrderDetails(orderId);
        } else if (requestCode == REQUEST_CODE_PREVIEW && resultCode == RESULT_OK) {
            showLoadingDialog(getString(R.string.dataLoad));
            ((OrderDetailsContract.Presenter) mPresenter).getOrderDetails(orderId);
            isRefresh = true;
        } else if (requestCode == REQUEST_CODE_CHOOSE_PHOTO && resultCode == RESULT_OK) {
            showLoadingDialog(getString(R.string.dataLoad));
            ((OrderDetailsContract.Presenter) mPresenter).getOrderDetails(orderId);
            isRefresh = true;
        }
    }

    @Override
    public void callMsgEvent(MsgEvent msgEvent) {
        super.callMsgEvent(msgEvent);
        if (((String) msgEvent.getData()).equals("RxBusPaymentPaySuccessEvent")) {
            ((OrderDetailsContract.Presenter) mPresenter).getOrderDetails(orderId);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (cancelOrderBouncedDialog != null) {
            cancelOrderBouncedDialog.cancel();
        }
        cancelOrderBouncedDialog = null;
        if (releaseAgainOrderBouncedDialog != null) {
            releaseAgainOrderBouncedDialog.cancel();
        }
        releaseAgainOrderBouncedDialog = null;
        if (contactDriverBouncedDialog != null) {
            contactDriverBouncedDialog.cancel();
        }
        contactDriverBouncedDialog = null;
    }
}