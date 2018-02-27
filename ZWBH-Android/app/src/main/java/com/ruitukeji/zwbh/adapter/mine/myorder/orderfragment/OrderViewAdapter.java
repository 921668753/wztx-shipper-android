package com.ruitukeji.zwbh.adapter.mine.myorder.orderfragment;

import android.content.Context;
import android.view.View;

import com.kymjs.common.StringUtils;
import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.entity.OrderBean.ResultBean.ListBean;

import cn.bingoogolapple.baseadapter.BGAAdapterViewAdapter;
import cn.bingoogolapple.baseadapter.BGAViewHolderHelper;


/**
 * 订单适配器
 * Created by Administrator on 2017/2/13.
 */

public class OrderViewAdapter extends BGAAdapterViewAdapter<ListBean> {

    public OrderViewAdapter(Context context) {
        super(context, R.layout.item_order1);
    }

    @Override
    protected void setItemChildListener(BGAViewHolderHelper viewHolderHelper) {
        viewHolderHelper.setItemChildClickListener(R.id.tv_checkAbnormal);
        viewHolderHelper.setItemChildClickListener(R.id.tv_cancelOrder);
        viewHolderHelper.setItemChildClickListener(R.id.tv_viewQuotation);
        viewHolderHelper.setItemChildClickListener(R.id.tv_viewShippingTrack);
        viewHolderHelper.setItemChildClickListener(R.id.tv_confirmPayment);
        viewHolderHelper.setItemChildClickListener(R.id.tv_contactDriver);
        viewHolderHelper.setItemChildClickListener(R.id.tv_evaluationDriver);
        viewHolderHelper.setItemChildClickListener(R.id.tv_seeEvaluation);
    }

    @Override
    public void fillData(BGAViewHolderHelper viewHolderHelper, int position, ListBean listBean) {

        /**
         * 订单号
         */
        viewHolderHelper.setText(R.id.tv_orderNumber, listBean.getOrder_code());

        /**
         * 订单时间
         */
        viewHolderHelper.setText(R.id.tv_orderTime, listBean.getCreate_at());

        /**
         * 预约时间
         */
        viewHolderHelper.setText(R.id.tv_appointmentTime, listBean.getAppoint_at());

        /**
         * 标签
         */
        if (!StringUtils.isEmpty(listBean.getType()) && listBean.getType().equals("often")) {
            viewHolderHelper.setVisibility(R.id.img_label, View.VISIBLE);
            viewHolderHelper.setImageResource(R.id.img_label, R.mipmap.label_shishi);
            viewHolderHelper.setVisibility(R.id.ll_appointmentTime, View.GONE);
        } else if (!StringUtils.isEmpty(listBean.getType()) && listBean.getType().equals("urgent")) {
            viewHolderHelper.setVisibility(R.id.img_label, View.VISIBLE);
            viewHolderHelper.setImageResource(R.id.img_label, R.mipmap.label_jiaji);
            viewHolderHelper.setVisibility(R.id.ll_appointmentTime, View.GONE);
        } else if (!StringUtils.isEmpty(listBean.getType()) && listBean.getType().equals("appoint")) {
            viewHolderHelper.setVisibility(R.id.img_label, View.VISIBLE);
            viewHolderHelper.setImageResource(R.id.img_label, R.mipmap.label_yuyue);
            viewHolderHelper.setVisibility(R.id.ll_appointmentTime, View.VISIBLE);
        } else {
            viewHolderHelper.setVisibility(R.id.img_label, View.GONE);
        }

        if (listBean.getIs_assigned() == 0) {
            viewHolderHelper.setVisibility(R.id.img_zhipai, View.GONE);
        } else {
            viewHolderHelper.setVisibility(R.id.img_zhipai, View.VISIBLE);
        }

        if (listBean.getIs_quote() == 0) {
            viewHolderHelper.setVisibility(R.id.tv_viewQuotation, View.GONE);
            viewHolderHelper.setVisibility(R.id.img_zanwu, View.VISIBLE);
        } else {
            viewHolderHelper.setVisibility(R.id.tv_viewQuotation, View.VISIBLE);
            viewHolderHelper.setVisibility(R.id.img_zanwu, View.GONE);
        }
        if (listBean.getIs_abnormal() == 0) {
            viewHolderHelper.setVisibility(R.id.tv_checkAbnormal, View.GONE);
            viewHolderHelper.setVisibility(R.id.img_yichang, View.GONE);
        } else {
            viewHolderHelper.setVisibility(R.id.tv_checkAbnormal, View.VISIBLE);
            viewHolderHelper.setVisibility(R.id.img_yichang, View.VISIBLE);
        }

        /**
         *订单状态
         */
        if (!StringUtils.isEmpty(listBean.getStatus()) && listBean.getStatus().equals("hang")) {
            viewHolderHelper.setText(R.id.tv_orderStatus, mContext.getString(R.string.hang));
        } else if (!StringUtils.isEmpty(listBean.getStatus()) && listBean.getStatus().equals("quote")) {
            viewHolderHelper.setText(R.id.tv_orderStatus, mContext.getString(R.string.pendingOrder));
        } else if (!StringUtils.isEmpty(listBean.getStatus()) && listBean.getStatus().equals("quoted")) {
            viewHolderHelper.setText(R.id.tv_orderStatus, mContext.getString(R.string.pendingDelivery));
        } else if (!StringUtils.isEmpty(listBean.getStatus()) && listBean.getStatus().equals("distribute") || !StringUtils.isEmpty(listBean.getStatus()) && listBean.getStatus().equals("arrive")) {
            viewHolderHelper.setText(R.id.tv_orderStatus, mContext.getString(R.string.transportation));
        } else if (!StringUtils.isEmpty(listBean.getStatus()) && listBean.getStatus().equals("photo") || !StringUtils.isEmpty(listBean.getStatus()) && listBean.getStatus().equals("pay_failed")) {
            viewHolderHelper.setText(R.id.tv_orderStatus, mContext.getString(R.string.pendingPayment));
        } else if (!StringUtils.isEmpty(listBean.getStatus()) && listBean.getStatus().equals("pay_success") || !StringUtils.isEmpty(listBean.getStatus()) && listBean.getStatus().equals("comment")) {
            viewHolderHelper.setText(R.id.tv_orderStatus, mContext.getString(R.string.completed));
        } else if (!StringUtils.isEmpty(listBean.getStatus()) && listBean.getStatus().equals("cancel")) {
            viewHolderHelper.setText(R.id.tv_orderStatus, mContext.getString(R.string.canceledOrder));
        }

        /**
         * 系统预估
         */
        viewHolderHelper.setText(R.id.tv_systemForecast, listBean.getSystem_price());

        /**
         * 用户出价
         */
        if (!StringUtils.isEmpty(listBean.getStatus()) && listBean.getStatus().equals("quote") && !StringUtils.isEmpty(listBean.getMind_price()) && !(listBean.getMind_price().equals("0.00"))) {
            viewHolderHelper.setVisibility(R.id.ll_userOffer, View.VISIBLE);
            viewHolderHelper.setText(R.id.tv_userOffer, listBean.getMind_price());
        } else if (!StringUtils.isEmpty(listBean.getFinal_price())) {
            viewHolderHelper.setVisibility(R.id.ll_userOffer, View.VISIBLE);
            viewHolderHelper.setText(R.id.tv_userOffer, listBean.getFinal_price());
        } else {
            viewHolderHelper.setVisibility(R.id.ll_userOffer, View.GONE);
        }


        /**
         * 起运地
         */
        viewHolderHelper.setText(R.id.tv_place, listBean.getOrg_city());

        /**
         * 目的地
         */
        viewHolderHelper.setText(R.id.tv_destination, listBean.getDest_city());

        if (StringUtils.isEmpty(listBean.getArr_org_time())) {
            viewHolderHelper.setImageResource(R.id.img_start, R.mipmap.ic_start_unselected);
            viewHolderHelper.setVisibility(R.id.tv_onArrival, View.GONE);
        } else {
            viewHolderHelper.setImageResource(R.id.img_start, R.mipmap.ic_start_selected);
            viewHolderHelper.setText(R.id.tv_onArrival, listBean.getArr_org_time());
            viewHolderHelper.setVisibility(R.id.tv_onArrival, View.VISIBLE);
        }

        if (StringUtils.isEmpty(listBean.getSend_time())) {
            viewHolderHelper.setImageResource(R.id.img_car, R.mipmap.ic_car_unselected);
            viewHolderHelper.setVisibility(R.id.tv_start, View.GONE);
        } else {
            viewHolderHelper.setImageResource(R.id.img_car, R.mipmap.ic_car_selected);
            viewHolderHelper.setVisibility(R.id.tv_start, View.VISIBLE);
            viewHolderHelper.setText(R.id.tv_start, listBean.getSend_time());
        }

        if (StringUtils.isEmpty(listBean.getArr_time())) {
            viewHolderHelper.setImageResource(R.id.img_end, R.mipmap.ic_end_unselected);
            viewHolderHelper.setVisibility(R.id.tv_destination1, View.GONE);
        } else {
            viewHolderHelper.setImageResource(R.id.img_end, R.mipmap.ic_end_selected);
            viewHolderHelper.setVisibility(R.id.tv_destination1, View.VISIBLE);
            viewHolderHelper.setText(R.id.tv_destination1, listBean.getArr_time());
        }

        if (!StringUtils.isEmpty(listBean.getStatus()) && listBean.getStatus().equals("quote") ||
                !StringUtils.isEmpty(listBean.getStatus()) && listBean.getStatus().equals("quoted") && listBean.getIs_cancel() == 0) {
            viewHolderHelper.setVisibility(R.id.tv_cancelOrder, View.VISIBLE);
        } else {
            viewHolderHelper.setVisibility(R.id.tv_cancelOrder, View.GONE);
        }
        if (!StringUtils.isEmpty(listBean.getStatus()) && listBean.getStatus().equals("hang")) {
            viewHolderHelper.setVisibility(R.id.img_zanwu, View.GONE);
            viewHolderHelper.setVisibility(R.id.ll_bottom, View.GONE);
            viewHolderHelper.setVisibility(R.id.ll_startEnd, View.GONE);
            viewHolderHelper.setVisibility(R.id.ll_startEnd1, View.GONE);
            viewHolderHelper.setVisibility(R.id.tv_divider1, View.GONE);
            viewHolderHelper.setVisibility(R.id.tv_divider, View.GONE);
        } else if (!StringUtils.isEmpty(listBean.getStatus()) && listBean.getStatus().equals("quote")) {
            viewHolderHelper.setVisibility(R.id.ll_startEnd, View.GONE);
            viewHolderHelper.setVisibility(R.id.ll_startEnd1, View.GONE);
            viewHolderHelper.setVisibility(R.id.tv_divider, View.GONE);
            viewHolderHelper.setVisibility(R.id.tv_divider1, View.VISIBLE);
            viewHolderHelper.setVisibility(R.id.ll_bottom, View.VISIBLE);
            viewHolderHelper.setVisibility(R.id.tv_checkAbnormal, View.GONE);
            viewHolderHelper.setVisibility(R.id.tv_confirmPayment, View.GONE);
            viewHolderHelper.setVisibility(R.id.tv_viewShippingTrack, View.GONE);
            viewHolderHelper.setVisibility(R.id.tv_contactDriver, View.GONE);
            viewHolderHelper.setVisibility(R.id.tv_evaluationDriver, View.GONE);
            viewHolderHelper.setVisibility(R.id.tv_seeEvaluation, View.GONE);
        } else if (!StringUtils.isEmpty(listBean.getStatus()) && listBean.getStatus().equals("quoted")) {
            viewHolderHelper.setVisibility(R.id.ll_startEnd, View.GONE);
            viewHolderHelper.setVisibility(R.id.ll_startEnd1, View.GONE);
            viewHolderHelper.setVisibility(R.id.tv_divider, View.GONE);
            viewHolderHelper.setVisibility(R.id.tv_divider1, View.VISIBLE);
            viewHolderHelper.setVisibility(R.id.ll_bottom, View.VISIBLE);
            viewHolderHelper.setVisibility(R.id.tv_viewQuotation, View.GONE);
            viewHolderHelper.setVisibility(R.id.tv_viewShippingTrack, View.GONE);
            viewHolderHelper.setVisibility(R.id.tv_contactDriver, View.VISIBLE);
            viewHolderHelper.setVisibility(R.id.tv_confirmPayment, View.GONE);
            viewHolderHelper.setVisibility(R.id.tv_evaluationDriver, View.GONE);
            viewHolderHelper.setVisibility(R.id.tv_seeEvaluation, View.GONE);
            viewHolderHelper.setVisibility(R.id.img_zanwu, View.GONE);
        } else if (!StringUtils.isEmpty(listBean.getStatus()) && listBean.getStatus().equals("distribute") || !StringUtils.isEmpty(listBean.getStatus()) && listBean.getStatus().equals("arrive")) {
            viewHolderHelper.setVisibility(R.id.ll_startEnd, View.VISIBLE);
            viewHolderHelper.setVisibility(R.id.ll_startEnd1, View.VISIBLE);
            viewHolderHelper.setVisibility(R.id.tv_divider, View.VISIBLE);
            viewHolderHelper.setVisibility(R.id.tv_divider1, View.VISIBLE);
            viewHolderHelper.setVisibility(R.id.ll_bottom, View.VISIBLE);
            viewHolderHelper.setVisibility(R.id.tv_viewQuotation, View.GONE);
            viewHolderHelper.setVisibility(R.id.tv_viewShippingTrack, View.VISIBLE);
            viewHolderHelper.setVisibility(R.id.tv_contactDriver, View.VISIBLE);
            viewHolderHelper.setVisibility(R.id.tv_confirmPayment, View.GONE);
            viewHolderHelper.setVisibility(R.id.tv_evaluationDriver, View.GONE);
            viewHolderHelper.setVisibility(R.id.tv_seeEvaluation, View.GONE);
            viewHolderHelper.setVisibility(R.id.img_zanwu, View.GONE);
        } else if (!StringUtils.isEmpty(listBean.getStatus()) && listBean.getStatus().equals("photo") || !StringUtils.isEmpty(listBean.getStatus()) && listBean.getStatus().equals("pay_failed")) {
            viewHolderHelper.setVisibility(R.id.ll_startEnd, View.VISIBLE);
            viewHolderHelper.setVisibility(R.id.ll_startEnd1, View.VISIBLE);
            viewHolderHelper.setVisibility(R.id.tv_divider, View.VISIBLE);
            viewHolderHelper.setVisibility(R.id.tv_divider1, View.VISIBLE);
            viewHolderHelper.setVisibility(R.id.ll_bottom, View.VISIBLE);
            viewHolderHelper.setVisibility(R.id.tv_viewQuotation, View.GONE);
            viewHolderHelper.setVisibility(R.id.tv_viewShippingTrack, View.GONE);
            viewHolderHelper.setVisibility(R.id.tv_contactDriver, View.GONE);
            viewHolderHelper.setVisibility(R.id.tv_confirmPayment, View.VISIBLE);
            viewHolderHelper.setVisibility(R.id.tv_evaluationDriver, View.GONE);
            viewHolderHelper.setVisibility(R.id.tv_seeEvaluation, View.GONE);
            viewHolderHelper.setVisibility(R.id.img_zanwu, View.GONE);
        } else if (!StringUtils.isEmpty(listBean.getStatus()) && listBean.getStatus().equals("pay_success")) {
            viewHolderHelper.setVisibility(R.id.ll_startEnd, View.VISIBLE);
            viewHolderHelper.setVisibility(R.id.ll_startEnd1, View.VISIBLE);
            viewHolderHelper.setVisibility(R.id.tv_divider, View.VISIBLE);
            viewHolderHelper.setVisibility(R.id.tv_divider1, View.VISIBLE);
            viewHolderHelper.setVisibility(R.id.ll_bottom, View.VISIBLE);
            viewHolderHelper.setVisibility(R.id.tv_viewQuotation, View.GONE);
            viewHolderHelper.setVisibility(R.id.tv_viewShippingTrack, View.VISIBLE);
            viewHolderHelper.setVisibility(R.id.tv_contactDriver, View.GONE);
            viewHolderHelper.setVisibility(R.id.tv_confirmPayment, View.GONE);
            viewHolderHelper.setVisibility(R.id.tv_evaluationDriver, View.VISIBLE);
            viewHolderHelper.setVisibility(R.id.tv_seeEvaluation, View.GONE);
            viewHolderHelper.setVisibility(R.id.img_zanwu, View.GONE);
        } else if (!StringUtils.isEmpty(listBean.getStatus()) && listBean.getStatus().equals("comment")) {
            viewHolderHelper.setVisibility(R.id.ll_startEnd, View.VISIBLE);
            viewHolderHelper.setVisibility(R.id.ll_startEnd1, View.VISIBLE);
            viewHolderHelper.setVisibility(R.id.tv_divider, View.VISIBLE);
            viewHolderHelper.setVisibility(R.id.tv_divider1, View.VISIBLE);
            viewHolderHelper.setVisibility(R.id.ll_bottom, View.VISIBLE);
            viewHolderHelper.setVisibility(R.id.tv_viewQuotation, View.GONE);
            viewHolderHelper.setVisibility(R.id.tv_viewShippingTrack, View.VISIBLE);
            viewHolderHelper.setVisibility(R.id.tv_contactDriver, View.GONE);
            viewHolderHelper.setVisibility(R.id.tv_confirmPayment, View.GONE);
            viewHolderHelper.setVisibility(R.id.tv_evaluationDriver, View.GONE);
            viewHolderHelper.setVisibility(R.id.tv_seeEvaluation, View.VISIBLE);
            viewHolderHelper.setVisibility(R.id.img_zanwu, View.GONE);
        } else if (!StringUtils.isEmpty(listBean.getStatus()) && listBean.getStatus().equals("cancel")) {
            viewHolderHelper.setVisibility(R.id.img_zanwu, View.GONE);
            viewHolderHelper.setVisibility(R.id.ll_bottom, View.GONE);
            viewHolderHelper.setVisibility(R.id.ll_startEnd, View.GONE);
            viewHolderHelper.setVisibility(R.id.ll_startEnd1, View.GONE);
            viewHolderHelper.setVisibility(R.id.tv_divider1, View.GONE);
            viewHolderHelper.setVisibility(R.id.tv_divider, View.GONE);
        }
    }

}
