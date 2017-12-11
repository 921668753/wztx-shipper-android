package com.ruitukeji.zwbh.adapter;

import android.content.Context;
import android.view.View;

import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.common.GlideImageLoader;
import com.ruitukeji.zwbh.entity.QuotationListBean.ResultBean.ListBean;

import cn.bingoogolapple.baseadapter.BGAAdapterViewAdapter;
import cn.bingoogolapple.baseadapter.BGAViewHolderHelper;
import cn.bingoogolapple.imageview.BGAImageView;


/**
 * 报价列表适配器
 * Created by Administrator on 2017/2/25.
 */

public class QuotationListViewAdapter extends BGAAdapterViewAdapter<ListBean> {


    public QuotationListViewAdapter(Context context) {
        super(context, R.layout.item_quotationlist);
    }
//    @Override
//    protected void setItemChildListener(BGAViewHolderHelper viewHolderHelper) {
//        viewHolderHelper.setItemChildClickListener(R.id.img_agreement);
//    }

    @Override
    public void fillData(BGAViewHolderHelper viewHolderHelper, int position, ListBean listBean) {
        if (listBean.is_selected()) {
            viewHolderHelper.setImageResource(R.id.img_agreement, R.mipmap.selected);
        } else {
            viewHolderHelper.setImageResource(R.id.img_agreement, R.mipmap.selected1);
        }
        /**
         * 头像
         */
        GlideImageLoader.glideLoader(mContext, listBean.getAvatar(), (BGAImageView) viewHolderHelper.getView(R.id.img_user), 0);

        // 这里不知道当前图片的尺寸，加载成功后会乱跳
//        Glide.with(mContext).load(model.icon).placeholder(R.mipmap.staggered_holder).error(R.mipmap.staggered_holder).dontAnimate().into((ImageView) viewHolderHelper.getView(R.id.iv_item_staggered_icon));
        /**
         *姓名
         */
        viewHolderHelper.setText(R.id.tv_name, listBean.getCar_style_length() + "/" + listBean.getCar_style_type() + "   " + listBean.getCard_number());
        /**
         *评分
         */
        viewHolderHelper.setText(R.id.tv_score, listBean.getScore() + "");
        viewHolderHelper.setVisibility(R.id.tv_score, View.GONE);
        /**
         *报价
         */
        viewHolderHelper.setText(R.id.tv_quote, "￥" + listBean.getDr_price());
    }
}
