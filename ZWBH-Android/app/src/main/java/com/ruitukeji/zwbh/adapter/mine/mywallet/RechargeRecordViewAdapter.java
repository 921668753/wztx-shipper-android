package com.ruitukeji.zwbh.adapter.mine.mywallet;

import android.content.Context;

import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.entity.PrepaidPhoneRecordsBean.ResultBean.ListBean;

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
        if (listBean.getPay_way() == 1) {
            viewHolderHelper.setText(R.id.tv_name, mContext.getString(R.string.alipayPay));
            viewHolderHelper.setTextColorRes(R.id.tv_name, R.color.titletextcolors);
        } else if (listBean.getPay_way() == 2) {
            viewHolderHelper.setText(R.id.tv_name, mContext.getString(R.string.weChatPay));
            viewHolderHelper.setTextColorRes(R.id.tv_name, R.color.announcementCloseColors);
        } else {
            viewHolderHelper.setText(R.id.tv_name, "其他");
        }

        /**
         * 充值金额
         */
        viewHolderHelper.setText(R.id.tv_money, listBean.getReal_amount());

        /**
         *时间
         */
        viewHolderHelper.setText(R.id.tv_time, listBean.getPay_time().substring(0, 16));

        /**
         * 余额
         */
        viewHolderHelper.setText(R.id.tv_balance, "支付失败");

    }
}
