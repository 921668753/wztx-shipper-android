package com.ruitukeji.zwbh.adapter;

import android.content.Context;

import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.common.GlideImageLoader;
import com.ruitukeji.zwbh.entity.QuotationListBean.ResultBean;

import cn.bingoogolapple.baseadapter.BGAAdapterViewAdapter;
import cn.bingoogolapple.baseadapter.BGAViewHolderHelper;

/**
 * 报价列表适配器
 * Created by Administrator on 2017/2/25.
 */

public class QuotationListViewAdapter extends BGAAdapterViewAdapter<ResultBean> {


    public QuotationListViewAdapter(Context context) {
        super(context, R.layout.item_quotationlist);
    }

    @Override
    public void fillData(BGAViewHolderHelper viewHolderHelper, int position, ResultBean listBean) {

        /**
         * 头像
         */
        GlideImageLoader.glideLoader(mContext, listBean.getAvatar(), viewHolderHelper.getImageView(R.id.img_avatar), 0);

        /**
         * 是否选中
         */
        if (listBean.getIs_selected() == 1) {
            viewHolderHelper.setImageResource(R.id.img_selected, R.mipmap.selected);
        } else {
            viewHolderHelper.setImageResource(R.id.img_selected, R.mipmap.selected1);
        }

        /**
         *姓名
         */
        viewHolderHelper.setText(R.id.tv_plateNumber, listBean.getDr_name() + "·" + listBean.getCard_number());

        /**
         *报价
         */
        viewHolderHelper.setText(R.id.tv_price, mContext.getString(R.string.symbol) + listBean.getDr_price());

        /**
         *车长车型
         */
        viewHolderHelper.setText(R.id.tv_sypeLength, listBean.getCar_style_length() + "/" + listBean.getCar_style_type());

        /**
         *总单数
         */
        viewHolderHelper.setText(R.id.tv_totalOrderNum, listBean.getGoods_num());

        /**
         *服务等级
         */
        viewHolderHelper.setText(R.id.tv_driverLevel, listBean.getDr_score());

        /**
         *司机等级
         */
        viewHolderHelper.setText(R.id.tv_serviceLevel, listBean.getDr_level());

        /**
         *投诉次数
         */
        viewHolderHelper.setText(R.id.tv_complaintsNum, listBean.getComplain_num());

    }
}
