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
import com.ruitukeji.zwbh.mine.myorder.orderfragment.OrderDetailsContract;
import com.ruitukeji.zwbh.mine.myorder.orderfragment.OrderDetailsPresenter;
import com.ruitukeji.zwbh.mine.myorder.quotationlist.QuotationListActivity;
import com.ruitukeji.zwbh.utils.ActivityTitleUtils;
import com.ruitukeji.zwbh.utils.JsonUtil;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * 订单详情
 * Created by Administrator on 2017/2/16.
 */

public class OrderDetailsActivity extends BaseActivity implements OrderDetailsContract.View, EasyPermissions.PermissionCallbacks {

    private final int REQUEST_CODE_PERMISSION_CALL = 1;

    /**
     * 订单状态
     */
    @BindView(id = R.id.tv_orderStatus)
    private TextView tv_orderStatus;
    /**
     * 订单号
     */
    @BindView(id = R.id.ll_orderNumber)
    private LinearLayout ll_orderNumber;

    @BindView(id = R.id.tv_orderNumber)
    private TextView tv_orderNumber;
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
     * 货物回单
     */
    @BindView(id = R.id.tv_receiptGoods)
    private TextView tv_receiptGoods;

    /**
     * 货物在途时效
     */
    @BindView(id = R.id.ll_inTransitTime)
    private LinearLayout ll_inTransitTime;
    @BindView(id = R.id.tv_inTransitTime)
    private TextView tv_inTransitTime;


    /**
     * 保险单号
     */
    @BindView(id = R.id.ll_insurancePolicy)
    private LinearLayout ll_insurancePolicy;

    @BindView(id = R.id.tv_insurancePolicy)
    private TextView tv_insurancePolicy;

    @BindView(id = R.id.tv_checkDetails, click = true)
    private TextView tv_checkDetails;

    /**
     * 起始地
     */
    @BindView(id = R.id.tv_origin)
    private TextView tv_origin;
    /**
     * 目的地
     */
    @BindView(id = R.id.tv_destination)
    private TextView tv_destination;

    /**
     * 发货人信息
     */
    @BindView(id = R.id.ll_shipperInformation)
    private LinearLayout ll_shipperInformation;

    /**
     * 收货人姓名
     */
    @BindView(id = R.id.tv_consigneeName)
    private TextView tv_consigneeName;
    /**
     * 收货人电话
     */
    @BindView(id = R.id.tv_consigneeTelephone, click = true)
    private TextView tv_consigneeTelephone;
    /**
     * 收货人地址
     */
    @BindView(id = R.id.tv_consigneeAddress)
    private TextView tv_consigneeAddress;
    @BindView(id = R.id.tv_consigneeAddress1)
    private TextView tv_consigneeAddress1;
    /**
     * 寄件人姓名
     */
    @BindView(id = R.id.tv_senderName)
    private TextView tv_senderName;
    /**
     * 寄件人电话
     */
    @BindView(id = R.id.tv_senderPhone, click = true)
    private TextView tv_senderPhone;
    /**
     * 寄件人地址
     */
    @BindView(id = R.id.tv_senderAddress)
    private TextView tv_senderAddress;
    @BindView(id = R.id.tv_senderAddress1)
    private TextView tv_senderAddress1;

    /**
     * 用车时间
     */
    @BindView(id = R.id.tv_transportTime)
    private TextView tv_transportTime;

    /**
     * 发货时间
     */
    @BindView(id = R.id.ll_deliveryTime1)
    private LinearLayout ll_deliveryTime1;
    @BindView(id = R.id.tv_arrivalTime)
    private TextView tv_arrivalTime;


    /**
     * 到达时间
     */
    @BindView(id = R.id.ll_arrivalTime)
    private LinearLayout ll_arrivalTime;
    @BindView(id = R.id.tv_deliveryTime1)
    private TextView tv_deliveryTime1;

    /**
     * 车主姓名
     */
    @BindView(id = R.id.ll_ownerName)
    private LinearLayout ll_ownerName;
    @BindView(id = R.id.tv_ownerName)
    private TextView tv_ownerName;

    /**
     * 联系电话
     */
    @BindView(id = R.id.ll_contactPhoneNumber)
    private LinearLayout ll_contactPhoneNumber;
    @BindView(id = R.id.tv_contactPhoneNumber, click = true)
    private TextView tv_contactPhoneNumber;

    /**
     * 总运费
     */
    @BindView(id = R.id.ll_totalFreight)
    private LinearLayout ll_totalFreight;
    @BindView(id = R.id.tv_totalFreight)
    private TextView tv_totalFreight;


    /**
     * 备注
     */
    @BindView(id = R.id.tv_remark)
    private TextView tv_remark;

    /**
     * 查看凭证
     */
    @BindView(id = R.id.ll_commonBottombar)
    private LinearLayout ll_commonBottombar;


    /**
     * 查看报价
     */
    @BindView(id = R.id.tv_bottombar, click = true)
    private TextView tv_bottombar;

    /**
     * 订单状态
     */
    private String status = "";

    private OrderDetailsContract.Presenter mPresenter;
    private int orderId;
    private String avatar;
    private String card_number;
    private String total_amount = "";
    private String dest_address_maps;
    private String org_address_maps;
    private String map_code;
    private SweetAlertDialog sweetAlertDialog = null;
    private int goods_id;
    private String per_status;

    @Override
    public void setRootView() {
        setContentView(R.layout.activity_orderdetails1);
    }

    @Override
    public void initData() {
        super.initData();
        mPresenter = new OrderDetailsPresenter(this);
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        //  mPresenter.getOrderDetails();
//    }

    @Override
    public void initWidget() {
        super.initWidget();
        ActivityTitleUtils.initToolbar(aty, getString(R.string.orderDetails), true, R.id.titlebar);
        orderId = getIntent().getIntExtra("order_id", 0);
        goods_id = getIntent().getIntExtra("goods_id", 0);
        ((OrderDetailsContract.Presenter) mPresenter).getOrderDetails(orderId);
    }

    @Override
    public void getSuccess(String s) {
        PreferenceHelper.write(aty, StringConstants.FILENAME, "isRefreshOrderDetailsActivity", false);
        OrderDetailsBean orderDetailsBean = (OrderDetailsBean) JsonUtil.getInstance().json2Obj(s, OrderDetailsBean.class);
        OrderDetailsBean.ResultBean result = orderDetailsBean.getResult();
        status = result.getStatus();
        getOrderStatus(status);
        tv_orderNumber.setText(result.getOrder_code());
        tv_descriptionGoods.setText(result.getGoods_name());
        tv_weight.setText(result.getWeight() + "吨");
        avatar = result.getAvatar();
        card_number = result.getCard_number();
        if (result.getIs_receipt() == 1) {
            tv_receiptGoods.setText(getString(R.string.is));
        } else {
            tv_receiptGoods.setText(getString(R.string.no));
        }
        if (StringUtils.isEmpty(result.getPolicy_code())) {
            ll_insurancePolicy.setVisibility(View.GONE);
        } else {
            ll_insurancePolicy.setVisibility(View.VISIBLE);
            tv_insurancePolicy.setText(result.getPolicy_code());
        }
        if (result.getEffective_time() == 0) {
            ll_inTransitTime.setVisibility(View.GONE);
        } else {
            ll_inTransitTime.setVisibility(View.VISIBLE);
            int hour = result.getEffective_time() / 60;
            int minute = result.getEffective_time() % 60;
            if (hour > 0) {
                tv_inTransitTime.setText(hour + getString(R.string.hour) + minute + getString(R.string.minute));
            } else {
                tv_inTransitTime.setText(minute + getString(R.string.minute));
            }
        }
        dest_address_maps = result.getDest_address_maps();
        org_address_maps = result.getOrg_address_maps();
        map_code = result.getMap_code();
        tv_origin.setText(result.getOrg_city());
        tv_destination.setText(result.getDest_city());
        tv_consigneeName.setText(result.getDest_receive_name());
        if (StringUtils.isEmpty(result.getDest_telphone())) {
            tv_consigneeTelephone.setText(result.getDest_phone());
        } else {
            tv_consigneeTelephone.setText(result.getDest_phone() + "(" + result.getDest_telphone() + ")");
        }
        per_status = result.getPer_status();
        if (StringUtils.isEmpty(result.getDest_address_detail())) {
            tv_consigneeAddress.setText(result.getDest_city());
            tv_consigneeAddress1.setText(result.getDest_address_name().substring(result.getDest_city().length()));
        } else {
            tv_consigneeAddress.setText(result.getDest_address_name());
            tv_consigneeAddress1.setText(result.getDest_address_detail());
        }
        tv_senderName.setText(result.getOrg_send_name());
        if (StringUtils.isEmpty(result.getOrg_telphone())) {
            tv_senderPhone.setText(result.getOrg_phone());
        } else {
            tv_senderPhone.setText(result.getOrg_phone() + "(" + result.getOrg_telphone() + ")");
        }
        if (StringUtils.isEmpty(result.getOrg_address_detail())) {
            tv_senderAddress.setText(result.getOrg_city());
            tv_senderAddress1.setText(result.getOrg_address_name().substring(result.getOrg_city().length()));
        } else {
            tv_senderAddress.setText(result.getOrg_address_name());
            tv_senderAddress1.setText(result.getOrg_address_detail());
        }
        if (StringUtils.isEmpty(result.getUsecar_time()) || result.getUsecar_time().equals("0")) {
            tv_transportTime.setText("");
        } else {
            tv_transportTime.setText(result.getUsecar_time().substring(0, 16));
        }

        if (StringUtils.isEmpty(result.getSend_time()) || result.getSend_time().equals("0")) {
            tv_deliveryTime1.setText("");
        } else {
            tv_deliveryTime1.setText(result.getSend_time().substring(0, 16));
        }
        if (StringUtils.isEmpty(result.getArr_time()) || result.getArr_time().equals("0")) {
            tv_arrivalTime.setText("");
        } else {
            tv_arrivalTime.setText(result.getArr_time().substring(0, 16));
        }
        if (StringUtils.isEmpty(result.getRemark())) {
            tv_remark.setVisibility(View.GONE);
        } else {
            tv_remark.setVisibility(View.VISIBLE);
            tv_remark.setText(getString(R.string.note1) + result.getRemark());
        }
        tv_ownerName.setText(result.getReal_name());
        tv_contactPhoneNumber.setText(result.getPhone());
        total_amount = result.getFinal_price();
        tv_totalFreight.setText(result.getFinal_price() + "元");
        dismissLoadingDialog();
    }

    @Override
    public void error(String msg) {
        if (msg != null && msg.equals("" + NumericConstants.TOLINGIN)) {
            dismissLoadingDialog();
            skipActivity(aty, LoginActivity.class);
            return;
        }
        dismissLoadingDialog();
        ViewInject.toast(msg);
    }

    /**
     * 根据当前状态更改订单详情布局
     * 1.报价中  2.待发货 3.发货中 4.已完成  5.评价 6.分享
     *
     * @param status
     */
    public void getOrderStatus(String status) {
        if (status.equals("quote")) {
            tv_orderStatus.setText(getString(R.string.quoteOrder));
            ll_orderNumber.setVisibility(View.GONE);
//            ll_shipperInformation.setVisibility(View.GONE);
            ll_ownerName.setVisibility(View.GONE);
            ll_contactPhoneNumber.setVisibility(View.GONE);
            ll_totalFreight.setVisibility(View.GONE);
            ll_deliveryTime1.setVisibility(View.GONE);
            ll_arrivalTime.setVisibility(View.GONE);
            ll_commonBottombar.setVisibility(View.VISIBLE);
            tv_bottombar.setText(R.string.viewQuotation);
        } else if (status.equals("quoted")) {
            tv_orderStatus.setText(getString(R.string.toSendGoods));
            ll_orderNumber.setVisibility(View.VISIBLE);
            ll_deliveryTime1.setVisibility(View.GONE);
            ll_arrivalTime.setVisibility(View.GONE);
            ll_commonBottombar.setVisibility(View.GONE);
        } else if (status.equals("distribute")) {
            tv_orderStatus.setText(getString(R.string.delivery));
            ll_arrivalTime.setVisibility(View.GONE);
            ll_commonBottombar.setVisibility(View.VISIBLE);
            tv_bottombar.setText(R.string.lookMap);
        } else if (status.equals("photo")) {
            tv_orderStatus.setText(getString(R.string.completed));
            ll_commonBottombar.setVisibility(View.VISIBLE);
//            ll_ownerName.setVisibility(View.VISIBLE);
//            ll_contactPhoneNumber.setVisibility(View.GONE);
//            ll_totalFreight.setVisibility(View.GONE);
//            tv_check.setVisibility(View.GONE);
            tv_bottombar.setText(R.string.checkVoucher);
        } else if (status.equals("pay_success")) {
            tv_orderStatus.setText(getString(R.string.noEvaluation));
            ll_commonBottombar.setVisibility(View.VISIBLE);
            tv_bottombar.setText(R.string.evaluation);
        } else if (status.equals("comment")) {
            tv_orderStatus.setText(getString(R.string.haveEvaluation));
            ll_commonBottombar.setVisibility(View.VISIBLE);
            tv_bottombar.setText(R.string.checkeValuation);
        }else if (status.equals("hang")) {
            tv_orderStatus.setText(getString(R.string.hang));
            ll_deliveryTime1.setVisibility(View.GONE);
            ll_arrivalTime.setVisibility(View.GONE);
            ll_commonBottombar.setVisibility(View.GONE);
        }
    }

    @Override
    public void widgetClick(View v) {
        super.widgetClick(v);
        switch (v.getId()) {
            case R.id.tv_bottombar:
                if (status.equals("quote")) {
                    Intent intent = new Intent(aty, QuotationListActivity.class);
                    intent.putExtra("order_id", orderId);
                    intent.putExtra("goods_id", goods_id);
                    showActivity(aty, intent);
                } else if (status.equals("quoted")) {
                    //  showActivity(aty, CheckVoucherActivity.class);
                } else if (status.equals("distribute")) {
                    Intent intent = new Intent(aty, LogisticsPositioningActivity.class);
                    intent.putExtra("order_id", orderId);
                    intent.putExtra("real_name", tv_ownerName.getText().toString());
                    intent.putExtra("phone", tv_contactPhoneNumber.getText().toString());
                    intent.putExtra("card_number", card_number);
                    intent.putExtra("avatar", avatar);
                    intent.putExtra("org_address", tv_origin.getText().toString());
                    intent.putExtra("org_address_maps", org_address_maps);
                    intent.putExtra("dest_address_maps", dest_address_maps);
                    intent.putExtra("dest_address", tv_destination.getText().toString());
                    intent.putExtra("map_code", map_code);
                    showActivity(aty, intent);
                } else if (status.equals("photo") || status.equals("pay_failed")) {
                    Intent intent = new Intent(aty, CheckVoucherActivity.class);
                    intent.putExtra("order_id", orderId);
                    intent.putExtra("total_amount", total_amount);
                    intent.putExtra("per_status", per_status);
                    showActivity(aty, intent);
                } else if (status.equals("pay_success")) {
                    Intent intent = new Intent(aty, EvaluationShareActivity.class);
                    intent.putExtra("order_id", orderId);
                    intent.putExtra("status", "pay_success");
                    showActivity(aty, intent);
                } else if (status.equals("comment")) {
                    Intent intent = new Intent(aty, EvaluationShareActivity.class);
                    intent.putExtra("order_id", orderId);
                    intent.putExtra("status", "comment");
                    showActivity(aty, intent);
                }
                break;
            case R.id.tv_checkDetails:
                //保单详情

                break;
            case R.id.tv_senderPhone:
                choiceCallWrapper(getString(R.string.contactPhoneNumber), tv_senderPhone.getText().toString());
                break;

            case R.id.tv_consigneeTelephone:
                choiceCallWrapper(getString(R.string.contactPhoneNumber), tv_consigneeTelephone.getText().toString());
                break;

            case R.id.tv_contactPhoneNumber:
                choiceCallWrapper(getString(R.string.contactPhoneNumber), tv_contactPhoneNumber.getText().toString());
                break;

        }
    }

    @Override
    public void setPresenter(OrderDetailsContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    protected void onResume() {
        super.onResume();
        boolean isRefreshOrderDetailsActivity = PreferenceHelper.readBoolean(aty, StringConstants.FILENAME, "isRefreshOrderDetailsActivity", false);
        if (isRefreshOrderDetailsActivity) {
            ((OrderDetailsContract.Presenter) mPresenter).getOrderDetails(orderId);
        }
    }


    /**
     * 弹框设置
     */

    private void initDialog() {
        sweetAlertDialog = new SweetAlertDialog(this, SweetAlertDialog.CUSTOM_IMAGE_TYPE);
        sweetAlertDialog.setCancelable(false);
        sweetAlertDialog.setCancelText(getString(R.string.cancel1))
                .setConfirmText(getString(R.string.confirm))
                .showCancelButton(true)
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                    }
                });
    }


    @AfterPermissionGranted(REQUEST_CODE_PERMISSION_CALL)
    private void choiceCallWrapper(String title, String phone) {
        String[] perms = {Manifest.permission.CALL_PHONE};
        if (EasyPermissions.hasPermissions(this, perms)) {
            showDialog(title, phone);
        } else {
            EasyPermissions.requestPermissions(this, "拨打电话选择需要以下权限:\n\n访问设备上的电话拨打权限", REQUEST_CODE_PERMISSION_CALL, perms);
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
            ViewInject.toast("您拒绝了「拨打电话」所需要的相关权限!");
        }
    }


    public void showDialog(String title, String phone) {
        if (sweetAlertDialog == null) {
            initDialog();
        }
        sweetAlertDialog.setTitleText(title)
                .setContentText(phone)
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismiss();
                        sDialog = null;
                        Uri uri = Uri.parse("tel:" + phone);
                        Intent intent = new Intent(Intent.ACTION_DIAL, uri);
                        //     系统打电话界面：
                        startActivity(intent);
                    }
                }).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sweetAlertDialog = null;
    }
}