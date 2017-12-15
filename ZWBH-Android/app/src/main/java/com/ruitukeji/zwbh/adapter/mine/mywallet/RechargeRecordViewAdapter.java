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
        super(context, R.layout.item_prepaidphonerecords);
    }

//    @Override
//    protected void setItemChildListener(BGAViewHolderHelper viewHolderHelper) {
//      //  viewHolderHelper.setItemChildClickListener(R.id.tv_item_normal_delete);
//     //   viewHolderHelper.setItemChildLongClickListener(R.id.tv_item_normal_delete);
//    }

    @Override
    public void fillData(BGAViewHolderHelper viewHolderHelper, int position, ListBean listBean) {
        /**
         * 名称
         */
        if (listBean.getPay_way() == 1) {
            viewHolderHelper.setText(R.id.tv_name, mContext.getString(R.string.alipayPay));
        } else if (listBean.getPay_way() == 2) {
            viewHolderHelper.setText(R.id.tv_name, mContext.getString(R.string.weChatPay));
        } else {
            viewHolderHelper.setText(R.id.tv_name, "其他");
        }

        /**
         * 金额
         */
        viewHolderHelper.setText(R.id.tv_money, listBean.getReal_amount());
        /**
         * 成功 失败
         */
        if (listBean.getPay_status() == 0) {
            viewHolderHelper.setTextColorRes(R.id.tv_money, R.color.lonincolors);
            viewHolderHelper.setText(R.id.tv_balance, "未支付");
        } else if (listBean.getPay_status() == 1) {
            viewHolderHelper.setTextColorRes(R.id.tv_money, R.color.certificationcolors1);
            viewHolderHelper.setText(R.id.tv_money, "+" + listBean.getReal_amount());
            viewHolderHelper.setText(R.id.tv_balance, "支付成功");
        } else if (listBean.getPay_status() == 1) {
            viewHolderHelper.setTextColorRes(R.id.tv_money, R.color.lonincolors);
            viewHolderHelper.setText(R.id.tv_balance, "支付失败");
        }
        /**
         *时间
         */
        viewHolderHelper.setText(R.id.tv_time, listBean.getPay_time().substring(0, 16));


        //  KjBitmapUtil.getInstance().getKjBitmap().display((ImageView) viewHolderHelper.getView(R.id.img_user), model.detail, 90, 90, R.mipmap.ic_launcher);
        // 这里不知道当前图片的尺寸，加载成功后会乱跳
//        Glide.with(mContext).load(model.icon).placeholder(R.mipmap.staggered_holder).error(R.mipmap.staggered_holder).dontAnimate().into((ImageView) viewHolderHelper.getView(R.id.iv_item_staggered_icon));

    }
}
