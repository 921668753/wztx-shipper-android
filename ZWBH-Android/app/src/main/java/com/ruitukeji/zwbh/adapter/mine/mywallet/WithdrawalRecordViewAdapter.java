package com.ruitukeji.zwbh.adapter.mine.mywallet;

import android.content.Context;

import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.entity.WithdrawalRecordBean.ResultBean.ListBean;

import cn.bingoogolapple.baseadapter.BGAAdapterViewAdapter;
import cn.bingoogolapple.baseadapter.BGAViewHolderHelper;

/**
 * 提现记录适配器
 * Created by Administrator on 2017/2/13.
 */

public class WithdrawalRecordViewAdapter extends BGAAdapterViewAdapter<ListBean> {

    public WithdrawalRecordViewAdapter(Context context) {
        super(context, R.layout.item_withdrawalrecord);
    }

    @Override
    public void fillData(BGAViewHolderHelper viewHolderHelper, int position, ListBean listBean) {

        /**
         * 名称
         */
        if (listBean.getStatus() == "") {
            viewHolderHelper.setText(R.id.tv_name, mContext.getString(R.string.alipayPay));
            viewHolderHelper.setTextColorRes(R.id.tv_name, R.color.titletextcolors);
        } else if (listBean.getStatus() == "2") {
            viewHolderHelper.setText(R.id.tv_name, mContext.getString(R.string.weChatPay));
            viewHolderHelper.setTextColorRes(R.id.tv_name, R.color.announcementCloseColors);
        } else {
            viewHolderHelper.setText(R.id.tv_name, "其他");
        }

        /**
         * 提现金额
         */
        viewHolderHelper.setText(R.id.tv_money, listBean.getWithdrawal_amount());

        /**
         *时间
         */
        viewHolderHelper.setText(R.id.tv_time, listBean.getCreate_at().substring(0, 16));

        /**
         * 余额
         */
        viewHolderHelper.setText(R.id.tv_balance, "支付失败");

    }

}