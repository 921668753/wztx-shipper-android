package com.ruitukeji.zwbh.mine.mypublishedorder.publishedorderfragment;

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
import com.ruitukeji.zwbh.utils.ActivityTitleUtils;
import com.ruitukeji.zwbh.utils.JsonUtil;

/**
 * 发布订单详情
 * Created by Administrator on 2017/2/16.
 */

public class GoodsDetailsActivity extends BaseActivity implements GoodsDetailsContract.View {

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

    @BindView(id = R.id.tv_ownerName1)
    private TextView tv_ownerName1;

    @BindView(id = R.id.tv_ownerName)
    private TextView tv_ownerName;

    /**
     * 联系电话
     */
    @BindView(id = R.id.ll_contactPhoneNumber)
    private LinearLayout ll_contactPhoneNumber;
    @BindView(id = R.id.tv_contactPhoneNumber1)
    private TextView tv_contactPhoneNumber1;
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

    private GoodsDetailsContract.Presenter mPresenter;
    private int goods_id;
    private int is_quote = 0;

    @Override
    public void setRootView() {
        setContentView(R.layout.activity_orderdetails);
    }

    @Override
    public void initData() {
        super.initData();
        mPresenter = new GoodsDetailsPresenter(this);
    }

    @Override
    public void initWidget() {
        super.initWidget();
        ActivityTitleUtils.initToolbar(aty, getString(R.string.quoteDetails), true, R.id.titlebar);
        ll_orderNumber.setVisibility(View.GONE);
        goods_id = getIntent().getIntExtra("goods_id", 0);
        is_quote = getIntent().getIntExtra("is_quote", 0);
        ((GoodsDetailsContract.Presenter) mPresenter).getGoodsDetails(goods_id);
    }

    @Override
    public void getSuccess(String s) {
        PreferenceHelper.write(aty, StringConstants.FILENAME, "isRefreshOrderDetailsActivity", false);
        OrderDetailsBean orderDetailsBean = (OrderDetailsBean) JsonUtil.getInstance().json2Obj(s, OrderDetailsBean.class);
        OrderDetailsBean.ResultBean result = orderDetailsBean.getResult();
        status = result.getStatus();
        getOrderStatus(status);
        tv_descriptionGoods.setText(result.getGoods_name());
        tv_weight.setText(result.getWeight() + "吨");
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
        tv_origin.setText(result.getOrg_city());
        tv_destination.setText(result.getDest_city());
        tv_consigneeName.setText(result.getDest_receive_name());
        if (StringUtils.isEmpty(result.getDest_telphone())) {
            tv_consigneeTelephone.setText(result.getDest_phone());
        } else {
            tv_consigneeTelephone.setText(result.getDest_phone() + "(" + result.getDest_telphone() + ")");
        }
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
        tv_ownerName.setText(result.getSystem_price() + "元");
        tv_contactPhoneNumber.setText(result.getMind_price() + "元");
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
     * 1.报价中  2.待发货
     *
     * @param status
     */
    public void getOrderStatus(String status) {
        if (status.equals("quote")) {
            tv_orderStatus.setText(getString(R.string.quoteOrder));
            tv_ownerName1.setText("系统价格");
            tv_contactPhoneNumber1.setText("心理价格");
            ll_deliveryTime1.setVisibility(View.GONE);
            ll_arrivalTime.setVisibility(View.GONE);
            ll_totalFreight.setVisibility(View.GONE);
            if (is_quote == 0) {
                ll_commonBottombar.setVisibility(View.GONE);
            } else {
                ll_commonBottombar.setVisibility(View.VISIBLE);
                tv_bottombar.setText(R.string.viewQuotation);
            }
        } else if (status.equals("quoted")) {
            tv_orderStatus.setText(getString(R.string.theBidOrder));
            tv_ownerName1.setText("系统价格");
            tv_contactPhoneNumber1.setText("心理价格");
            ll_deliveryTime1.setVisibility(View.GONE);
            ll_arrivalTime.setVisibility(View.GONE);
            ll_totalFreight.setVisibility(View.GONE);
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
                    intent.putExtra("goods_id", goods_id);
                    showActivity(aty, intent);
                } else if (status.equals("quoted")) {
                    //  showActivity(aty, CheckVoucherActivity.class);
                }
                break;
        }
    }

    @Override
    public void setPresenter(GoodsDetailsContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    protected void onResume() {
        super.onResume();
        boolean isRefreshGoodDetailsActivity = PreferenceHelper.readBoolean(aty, StringConstants.FILENAME, "isRefreshGoodDetailsActivity", false);
        if (isRefreshGoodDetailsActivity) {
            ((GoodsDetailsContract.Presenter) mPresenter).getGoodsDetails(goods_id);
        }
    }
}