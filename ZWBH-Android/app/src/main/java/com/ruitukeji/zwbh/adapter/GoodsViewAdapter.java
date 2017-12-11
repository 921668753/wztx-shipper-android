package com.ruitukeji.zwbh.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.common.KJActivityStack;
import com.ruitukeji.zwbh.entity.GoodsBean.ResultBean.ListBean;

import cn.bingoogolapple.baseadapter.BGAAdapterViewAdapter;
import cn.bingoogolapple.baseadapter.BGAViewHolderHelper;

/**
 * 货源适配器
 * Created by Administrator on 2017/2/13.
 */

public class GoodsViewAdapter extends BGAAdapterViewAdapter<ListBean> {
    public GoodsViewAdapter(Context context) {
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
        viewHolderHelper.setText(R.id.tv_goods, listBean.getGoods_name() + "/" + listBean.getWeight() + KJActivityStack.create().topActivity().getString(R.string.tonne));
        /**
         *订单状态
         */
        viewHolderHelper.setVisibility(R.id.tv_driverQuotation, View.GONE);
        TextView tv_orderStatus = viewHolderHelper.getTextView(R.id.tv_orderStatus);
        if (listBean.getStatus().equals("quote")) {
            viewHolderHelper.setText(R.id.tv_orderStatus, KJActivityStack.create().topActivity().getString(R.string.quoteOrder));
            if (listBean.getIs_quote() == 0) {
                tv_orderStatus.setTextSize(16);
            } else {
                tv_orderStatus.setTextSize(14);
                viewHolderHelper.setVisibility(R.id.tv_driverQuotation, View.VISIBLE);
                /**
                 *司机报价
                 */
                viewHolderHelper.setText(R.id.tv_driverQuotation, KJActivityStack.create().topActivity().getString(R.string.driverQuotation));
            }
        } else if (listBean.getStatus().equals("quoted")) {
            tv_orderStatus.setTextSize(16);
            viewHolderHelper.setText(R.id.tv_orderStatus, KJActivityStack.create().topActivity().getString(R.string.theBidOrder));
        }
    }

}
