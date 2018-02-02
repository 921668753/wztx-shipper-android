package com.ruitukeji.zwbh.adapter.mine.myorder;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.common.KJActivityStack;
import com.ruitukeji.zwbh.entity.OrderBean.ResultBean.ListBean;

import cn.bingoogolapple.baseadapter.BGAAdapterViewAdapter;
import cn.bingoogolapple.baseadapter.BGAViewHolderHelper;


/**
 * 订单适配器
 * Created by Administrator on 2017/2/13.
 */

public class OrderViewAdapter extends BGAAdapterViewAdapter<ListBean> {
    public OrderViewAdapter(Context context) {
        super(context, R.layout.item_order);
    }

    @Override
    protected void setItemChildListener(BGAViewHolderHelper viewHolderHelper) {
        viewHolderHelper.setItemChildClickListener(R.id.tv_driverQuotation);
        //   viewHolderHelper.setItemChildLongClickListener(R.id.tv_item_normal_delete);
    }

    @Override
    public void fillData(BGAViewHolderHelper viewHolderHelper, int position, ListBean listBean) {

        /**
         * 起点
         */
        int orgprovince = listBean.getOrg_city().indexOf("省");
        int orgcity = listBean.getOrg_city().indexOf("市");
        if (orgprovince == -1 && orgcity != -1) {
//            int orgarea = listBean.getOrg_city().indexOf("区");
//            viewHolderHelper.setText(R.id.tv_start, listBean.getOrg_city().substring(0, orgcity) + listBean.getOrg_city().substring(orgcity + 1, orgarea));
            viewHolderHelper.setText(R.id.tv_start, listBean.getOrg_city().substring(0, orgcity));
        } else if (orgprovince != -1 && orgcity != -1) {
            viewHolderHelper.setText(R.id.tv_start, listBean.getOrg_city().substring(0, orgprovince) + listBean.getOrg_city().substring(orgprovince + 1, orgcity));
        } else {
            viewHolderHelper.setText(R.id.tv_start, listBean.getOrg_city());
        }

        /**
         * 终点
         */
        int destprovince = listBean.getDest_city().indexOf("省");//("市")
        int destcity = listBean.getDest_city().indexOf("市");
        if (destprovince == -1 && destcity != -1) {
            // int destarea = listBean.getDest_city().indexOf("区");
            //   viewHolderHelper.setText(R.id.tv_stop, listBean.getDest_city().substring(0, destcity) + listBean.getDest_city().substring(destcity + 1, destarea));
            viewHolderHelper.setText(R.id.tv_stop, listBean.getDest_city().substring(0, destcity));
        } else if (destprovince != -1 && destcity != -1) {
            viewHolderHelper.setText(R.id.tv_stop, listBean.getDest_city().substring(0, destprovince) + listBean.getDest_city().substring(destprovince + 1, destcity));
        } else {
            viewHolderHelper.setText(R.id.tv_stop, listBean.getDest_city());
        }

        /**
         * 木材/312
         */
       // viewHolderHelper.setText(R.id.tv_goods, listBean.getGoods_name() + "/" + listBean.getWeight() + KJActivityStack.create().topActivity().getString(R.string.tonne));
        /**
         *订单状态
         */
        viewHolderHelper.setVisibility(R.id.tv_driverQuotation, View.GONE);
        if (listBean.getStatus().equals("init")) {
            TextView tv_orderStatus = viewHolderHelper.getTextView(R.id.tv_orderStatus);
            tv_orderStatus.setTextSize(16);
            viewHolderHelper.setText(R.id.tv_orderStatus, KJActivityStack.create().topActivity().getString(R.string.init));
        } else if (listBean.getStatus().equals("quote")) {
            viewHolderHelper.setVisibility(R.id.tv_driverQuotation, View.VISIBLE);
            viewHolderHelper.setText(R.id.tv_orderStatus, KJActivityStack.create().topActivity().getString(R.string.quoteOrder));
            /**
             *司机报价
             */
            viewHolderHelper.setText(R.id.tv_driverQuotation, KJActivityStack.create().topActivity().getString(R.string.driverQuotation));
        } else if (listBean.getStatus().equals("quoted")) {
            TextView tv_orderStatus = viewHolderHelper.getTextView(R.id.tv_orderStatus);
            tv_orderStatus.setTextSize(16);
            viewHolderHelper.setText(R.id.tv_orderStatus, KJActivityStack.create().topActivity().getString(R.string.toSendGoods));
        } else if (listBean.getStatus().equals("distribute")) {
            TextView tv_orderStatus = viewHolderHelper.getTextView(R.id.tv_orderStatus);
            tv_orderStatus.setTextSize(16);
            viewHolderHelper.setText(R.id.tv_orderStatus, KJActivityStack.create().topActivity().getString(R.string.delivery));

        } else if (listBean.getStatus().equals("photo")) {
            TextView tv_orderStatus = viewHolderHelper.getTextView(R.id.tv_orderStatus);
            tv_orderStatus.setTextSize(16);
            viewHolderHelper.setText(R.id.tv_orderStatus, KJActivityStack.create().topActivity().getString(R.string.completed));
        } else if (listBean.getStatus().equals("pay_success")) {
            TextView tv_orderStatus = viewHolderHelper.getTextView(R.id.tv_orderStatus);
            tv_orderStatus.setTextSize(16);
            viewHolderHelper.setText(R.id.tv_orderStatus, KJActivityStack.create().topActivity().getString(R.string.noEvaluation));
        } else if (listBean.getStatus().equals("comment")) {
            TextView tv_orderStatus = viewHolderHelper.getTextView(R.id.tv_orderStatus);
            tv_orderStatus.setTextSize(16);
            viewHolderHelper.setText(R.id.tv_orderStatus, KJActivityStack.create().topActivity().getString(R.string.haveEvaluation));
        } else if (listBean.getStatus().equals("hang")) {
            TextView tv_orderStatus = viewHolderHelper.getTextView(R.id.tv_orderStatus);
            tv_orderStatus.setTextSize(16);
            viewHolderHelper.setText(R.id.tv_orderStatus, KJActivityStack.create().topActivity().getString(R.string.hang));
        }

//        if (listBean.getStatus().equals("quote")) {
//            viewHolderHelper.setText(R.id.tv_driverQuotation, KJActivityStack.create().topActivity().getString(R.string.quote));
//        }
//        viewHolderHelper.setText(R.id.tv_driverQuotation, listBean.getWeight());
        //  KjBitmapUtil.getInstance().getKjBitmap().display((ImageView) viewHolderHelper.getView(R.id.img_user), model.detail, 90, 90, R.mipmap.ic_launcher);
        // 这里不知道当前图片的尺寸，加载成功后会乱跳
//        Glide.with(mContext).load(model.icon).placeholder(R.mipmap.staggered_holder).error(R.mipmap.staggered_holder).dontAnimate().into((ImageView) viewHolderHelper.getView(R.id.iv_item_staggered_icon));

    }

}
