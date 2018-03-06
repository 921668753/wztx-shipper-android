package com.ruitukeji.zwbh.mine.myorder.orderfragment;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.kymjs.common.PreferenceHelper;
import com.kymjs.common.StringUtils;
import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.adapter.mine.myorder.orderfragment.OrderViewAdapter;
import com.ruitukeji.zwbh.common.BaseFragment;
import com.ruitukeji.zwbh.common.BindView;
import com.ruitukeji.zwbh.common.ViewInject;
import com.ruitukeji.zwbh.constant.NumericConstants;
import com.ruitukeji.zwbh.constant.StringConstants;
import com.ruitukeji.zwbh.entity.mine.myorder.orderfragment.OrderBean;
import com.ruitukeji.zwbh.mine.abnormalrecords.AbnormalRecordsActivity;
import com.ruitukeji.zwbh.mine.myorder.MyOrderActivity;
import com.ruitukeji.zwbh.mine.myorder.dialog.CancelOrderBouncedDialog;
import com.ruitukeji.zwbh.mine.myorder.dialog.CancelOrderNoticBouncedDialog;
import com.ruitukeji.zwbh.mine.myorder.dialog.ContactDriverBouncedDialog;
import com.ruitukeji.zwbh.mine.myorder.logisticspositioning.LogisticsPositioningActivity;
import com.ruitukeji.zwbh.mine.myorder.orderdetails.EvaluationDriverActivity;
import com.ruitukeji.zwbh.mine.myorder.orderdetails.OrderDetailsActivity;
import com.ruitukeji.zwbh.mine.myorder.payment.CheckVoucherActivity;
import com.ruitukeji.zwbh.mine.myorder.quotationlist.QuotationListActivity;
import com.ruitukeji.zwbh.utils.JsonUtil;
import com.ruitukeji.zwbh.utils.RefreshLayoutUtil;
import com.ruitukeji.zwbh.utils.rx.MsgEvent;


import java.util.List;

import cn.bingoogolapple.baseadapter.BGAOnItemChildClickListener;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

import static android.app.Activity.RESULT_OK;
import static com.ruitukeji.zwbh.constant.NumericConstants.REQUEST_CODE_CHOOSE_PHOTO;
import static com.ruitukeji.zwbh.constant.NumericConstants.REQUEST_CODE_PERMISSION_CALL;

/**
 * 全部
 * Created by Administrator on 2017/2/12.
 */

public class AllFragment extends BaseFragment implements EasyPermissions.PermissionCallbacks, OrderContract.View, AdapterView.OnItemClickListener, BGARefreshLayout.BGARefreshLayoutDelegate, BGAOnItemChildClickListener {

    @BindView(id = R.id.mRefreshLayout)
    private BGARefreshLayout mRefreshLayout;

    private OrderViewAdapter mAdapter;

    private MyOrderActivity aty;

    @BindView(id = R.id.lv_order)
    private ListView lv_order;

    /**
     * 错误提示页
     */
    @BindView(id = R.id.ll_commonError)
    private LinearLayout ll_commonError;
    @BindView(id = R.id.tv_hintText, click = true)
    private TextView tv_hintText;

    /**
     * 当前页码
     */
    private int mMorePageNumber = NumericConstants.START_PAGE_NUMBER;
    /**
     * 总页码
     */
    private int totalPageNumber = NumericConstants.START_PAGE_NUMBER;
    /**
     * 是否加载更多
     */
    private boolean isShowLoadingMore = false;

    /**
     * 订单状态（all全部状态， 待接订 quote quoted已报价，待发货 distribute配送中（在配送-未拍照）发货中 待支付 toPay success 完成
     */
    private String type = "all";
    private ContactDriverBouncedDialog contactDriverBouncedDialog = null;

    private CancelOrderBouncedDialog cancelOrderBouncedDialog = null;
    public int isShowingOrderNotic1 = 0;


    @Override
    protected View inflaterView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        aty = (MyOrderActivity) getActivity();
        return View.inflate(aty, R.layout.fragment_allorder1, null);
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter = new OrderPresenter(this);
        mAdapter = new OrderViewAdapter(getActivity());
    }

    @Override
    protected void initWidget(View parentView) {
        super.initWidget(parentView);
        RefreshLayoutUtil.initRefreshLayout(mRefreshLayout, this, getActivity(), true);
        lv_order.setAdapter(mAdapter);
        lv_order.setOnItemClickListener(this);
        mAdapter.setOnItemChildClickListener(this);
        mRefreshLayout.beginRefreshing();
    }


    /**
     * 控件监听事件
     */
    @Override
    public void widgetClick(View v) {
        super.widgetClick(v);
        switch (v.getId()) {
            case R.id.tv_hintText:
                mRefreshLayout.beginRefreshing();
                break;
        }
    }


    @Override
    public void onItemChildClick(ViewGroup viewGroup, View view, int position) {
        if (view.getId() == R.id.tv_checkAbnormal) {
            Intent intent = new Intent(aty, AbnormalRecordsActivity.class);
            intent.putExtra("order_id", mAdapter.getItem(position).getOrder_id());
            startActivity(intent);
        } else if (view.getId() == R.id.tv_cancelOrder) {
            int type = 0;
            if (!StringUtils.isEmpty(mAdapter.getItem(position).getStatus()) && mAdapter.getItem(position).getStatus().equals("quote") && mAdapter.getItem(position).getIs_cancel() == 0) {
                type = 0;
            } else if (!StringUtils.isEmpty(mAdapter.getItem(position).getStatus()) && mAdapter.getItem(position).getStatus().equals("quoted") && mAdapter.getItem(position).getIs_cancel() == 0) {
                type = 1;
            } else {
                ViewInject.toast(getString(R.string.serverReturnsDataError));
                return;
            }
            if (cancelOrderBouncedDialog != null && !cancelOrderBouncedDialog.isShowing()) {
                cancelOrderBouncedDialog.show();
                cancelOrderBouncedDialog.setOrderId(mAdapter.getItem(position).getOrder_id(), type);
                return;
            }
            cancelOrderBouncedDialog = new CancelOrderBouncedDialog(aty, mAdapter.getItem(position).getOrder_id(), type) {
                @Override
                public void confirm() {
                    this.cancel();
                    mRefreshLayout.beginRefreshing();
                }
            };
            cancelOrderBouncedDialog.show();
        } else if (view.getId() == R.id.tv_viewQuotation) {
            Intent intent = new Intent(aty, QuotationListActivity.class);
            intent.putExtra("order_id", mAdapter.getItem(position).getOrder_id());
            startActivityForResult(intent, REQUEST_CODE_CHOOSE_PHOTO);
        } else if (view.getId() == R.id.tv_viewShippingTrack) {
            Intent intent = new Intent(aty, LogisticsPositioningActivity.class);
            intent.putExtra("map_code", mAdapter.getItem(position).getMap_code());
            intent.putExtra("real_name", mAdapter.getItem(position).getDr_name());
            intent.putExtra("card_number", mAdapter.getItem(position).getCard_number());
            intent.putExtra("avatar", mAdapter.getItem(position).getAvatar());
            intent.putExtra("phone", mAdapter.getItem(position).getDr_phone());
            intent.putExtra("org_address", mAdapter.getItem(position).getOrg_city());
            intent.putExtra("org_address_maps", mAdapter.getItem(position).getOrg_address_maps());
            intent.putExtra("dest_address_maps", mAdapter.getItem(position).getDest_address_maps());
            intent.putExtra("dest_address", mAdapter.getItem(position).getDest_city());
            intent.putExtra("status", mAdapter.getItem(position).getStatus());
            startActivity(intent);
        } else if (view.getId() == R.id.tv_confirmPayment) {
            Intent intent = new Intent(aty, CheckVoucherActivity.class);
            intent.putExtra("order_id", mAdapter.getItem(position).getOrder_id());
            intent.putExtra("total_amount", mAdapter.getItem(position).getFinal_price());
            intent.putExtra("per_status", mAdapter.getItem(position).getPer_status());
            startActivityForResult(intent, REQUEST_CODE_CHOOSE_PHOTO);
        } else if (view.getId() == R.id.tv_contactDriver) {
            choiceCallWrapper(mAdapter.getItem(position).getDr_phone());
        } else if (view.getId() == R.id.tv_evaluationDriver) {
            Intent intent = new Intent(aty, EvaluationDriverActivity.class);
            intent.putExtra("order_id", mAdapter.getItem(position).getOrder_id());
            intent.putExtra("status", mAdapter.getItem(position).getStatus());
            startActivityForResult(intent, REQUEST_CODE_CHOOSE_PHOTO);
        } else if (view.getId() == R.id.tv_seeEvaluation) {
            Intent intent = new Intent(aty, EvaluationDriverActivity.class);
            intent.putExtra("order_id", mAdapter.getItem(position).getOrder_id());
            intent.putExtra("status", mAdapter.getItem(position).getStatus());
            startActivity(intent);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(aty, OrderDetailsActivity.class);
        intent.putExtra("order_id", mAdapter.getItem(i).getOrder_id());
        startActivityForResult(intent, REQUEST_CODE_CHOOSE_PHOTO);
    }


    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        mMorePageNumber = NumericConstants.START_PAGE_NUMBER;
        mRefreshLayout.endRefreshing();
        showLoadingDialog(getString(R.string.dataLoad));
        ((OrderContract.Presenter) mPresenter).getOrder(mMorePageNumber, type);
    }


    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        mRefreshLayout.endLoadingMore();
        if (!isShowLoadingMore) {
            return false;
        }
        mMorePageNumber++;
        if (mMorePageNumber > totalPageNumber) {
            ViewInject.toast(getString(R.string.noMoreData));
            return false;
        }
        showLoadingDialog(getString(R.string.dataLoad));
        ((OrderContract.Presenter) mPresenter).getOrder(mMorePageNumber, type);
        return true;
    }

    @Override
    public void getSuccess(String success, int flag) {
        isShowLoadingMore = true;
        ll_commonError.setVisibility(View.GONE);
        mRefreshLayout.setVisibility(View.VISIBLE);
        OrderBean orderBean = (OrderBean) JsonUtil.getInstance().json2Obj(success, OrderBean.class);
        mMorePageNumber = orderBean.getResult().getPage();
        totalPageNumber = orderBean.getResult().getPageTotal();
        if (orderBean.getResult().getList() == null || orderBean.getResult().getList().size() == 0) {
            errorMsg(getString(R.string.serverReturnsDataNull), 0);
            return;
        }
        for (int i = 0; i < orderBean.getResult().getList().size(); i++) {
            OrderBean.ResultBean.ListBean listBean = orderBean.getResult().getList().get(i);
            if (!StringUtils.isEmpty(listBean.getStatus()) && listBean.getStatus().equals("quoted") && listBean.getIs_cancel() == 1) {
                PreferenceHelper.write(aty, StringConstants.FILENAME, "isShowingOrderNotic", 1);
                PreferenceHelper.write(aty, StringConstants.FILENAME, "orderId", listBean.getOrder_id());
                PreferenceHelper.write(aty, StringConstants.FILENAME, "orderCode", listBean.getOrder_code());
            }
        }
        if (mMorePageNumber == NumericConstants.START_PAGE_NUMBER) {
            mRefreshLayout.endRefreshing();
            mAdapter.clear();
            mAdapter.addNewData(orderBean.getResult().getList());
        } else if (mMorePageNumber > NumericConstants.START_PAGE_NUMBER) {
            mRefreshLayout.endLoadingMore();
            mAdapter.addMoreData(orderBean.getResult().getList());
        }
        int isShowingOrderNotic = PreferenceHelper.readInt(aty, StringConstants.FILENAME, "isShowingOrderNotic", 0);
        if (isShowingOrderNotic == 1 && isShowingOrderNotic1 == 0) {
            if (aty.cancelOrderNoticBouncedDialog != null && !aty.cancelOrderNoticBouncedDialog.isShowing()) {
                aty.cancelOrderNoticBouncedDialog.show();
                PreferenceHelper.write(aty, StringConstants.FILENAME, "isShowingOrderNotic", 0);
                isShowingOrderNotic1 = 1;
                int orderId = PreferenceHelper.readInt(aty, StringConstants.FILENAME, "orderId", 0);
                String orderCode = PreferenceHelper.readString(aty, StringConstants.FILENAME, "orderCode", "");
                aty.cancelOrderNoticBouncedDialog.setOrderId(orderId, orderCode);
            }
        }
        dismissLoadingDialog();
    }

    @Override
    public void errorMsg(String msg, int flag) {
        toLigon(msg);
        isShowLoadingMore = false;
        mRefreshLayout.setVisibility(View.GONE);
        ll_commonError.setVisibility(View.VISIBLE);
        tv_hintText.setText(msg + getString(R.string.clickRefresh));
        if (mMorePageNumber == NumericConstants.START_PAGE_NUMBER) {
            mRefreshLayout.endRefreshing();
        } else {
            mRefreshLayout.endLoadingMore();
        }
        dismissLoadingDialog();
    }

    @Override
    public void setPresenter(OrderContract.Presenter presenter) {
        mPresenter = presenter;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSE_PHOTO && resultCode == RESULT_OK) {
            mRefreshLayout.beginRefreshing();
        }
    }


    @Override
    public void callMsgEvent(MsgEvent msgEvent) {
        super.callMsgEvent(msgEvent);
        if (((String) msgEvent.getData()).equals("RxBusAllOrderFragmentEvent") || ((String) msgEvent.getData()).equals("RxBusPaymentPaySuccessEvent")) {
            mMorePageNumber = NumericConstants.START_PAGE_NUMBER;
            ((OrderContract.Presenter) mPresenter).getOrder(mMorePageNumber, type);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (cancelOrderBouncedDialog != null) {
            cancelOrderBouncedDialog.cancel();
        }
        cancelOrderBouncedDialog = null;
        if (contactDriverBouncedDialog != null) {
            contactDriverBouncedDialog.cancel();
        }
        contactDriverBouncedDialog = null;
        mAdapter.clear();
        mAdapter = null;
    }

    @AfterPermissionGranted(REQUEST_CODE_PERMISSION_CALL)
    private void choiceCallWrapper(String phone) {
        String[] perms = {Manifest.permission.CALL_PHONE};
        if (EasyPermissions.hasPermissions(aty, perms)) {
            if (contactDriverBouncedDialog != null && !contactDriverBouncedDialog.isShowing()) {
                contactDriverBouncedDialog.show();
                contactDriverBouncedDialog.setPhone(phone);
                return;
            }
            if (contactDriverBouncedDialog == null) {
                contactDriverBouncedDialog = new ContactDriverBouncedDialog(getActivity(), phone);
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

    public void showCancelOrderNoticBouncedDialog() {
        isShowingOrderNotic1 = 0;
        mRefreshLayout.beginRefreshing();
    }
}
