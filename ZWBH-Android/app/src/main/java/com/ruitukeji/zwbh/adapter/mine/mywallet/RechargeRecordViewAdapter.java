package com.ruitukeji.zwbh.adapter.mine.mywallet;

import android.content.Context;

import com.kymjs.common.StringUtils;
import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.entity.mine.mywallet.recharge.RechargeRecordBean.ResultBean.ListBean;

import cn.bingoogolapple.baseadapter.BGAAdapterViewAdapter;
import cn.bingoogolapple.baseadapter.BGAViewHolderHelper;

/**
 * 充值记录适配器
 * Created by Administrator on 2017/2/13.
 */

public class RechargeRecordViewAdapter extends BGAAdapterViewAdapter<ListBean> {


    public RechargeRecordViewAdapter(Context context) {
        super(context, R.layout.item_withdrawalrecord);
    }


    @Override
    public void fillData(BGAViewHolderHelper viewHolderHelper, int position, ListBean listBean) {

        /**
         * 名称
         */
        if (listBean.getPay_status() == 1) {
            viewHolderHelper.setText(R.id.tv_name, mContext.getString(R.string.rechargeSuccess));
            /**
             * 充值金额
             */
            viewHolderHelper.setText(R.id.tv_money, "+" + listBean.getReal_amount());
            viewHolderHelper.setTextColorRes(R.id.tv_name, R.color.titletextcolors);
        } else {
            viewHolderHelper.setText(R.id.tv_name, mContext.getString(R.string.rechargeFailure));
            /**
             * 充值金额
             */
            viewHolderHelper.setText(R.id.tv_money, listBean.getReal_amount());
            viewHolderHelper.setTextColorRes(R.id.tv_name, R.color.announcementCloseColors);
        }

        /**
         *时间
         */
        if (!StringUtils.isEmpty(listBean.getPay_time())) {
            viewHolderHelper.setText(R.id.tv_time, listBean.getPay_time());
        } else {
            viewHolderHelper.setText(R.id.tv_time, "");
        }


        /**
         * 余额
         */
        viewHolderHelper.setText(R.id.tv_balance, listBean.getBalance());

    }
}
