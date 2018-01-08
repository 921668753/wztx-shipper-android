package com.ruitukeji.zwbh.adapter.mine.abnormalrecords;

import android.content.Context;

import com.kymjs.common.StringUtils;
import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.common.GlideImageLoader;
import com.ruitukeji.zwbh.entity.mine.abnormalrecords.AbnormalRecordsBean.ResultBean.ListBean;

import cn.bingoogolapple.baseadapter.BGAAdapterViewAdapter;
import cn.bingoogolapple.baseadapter.BGAViewHolderHelper;
import cn.bingoogolapple.imageview.BGAImageView;


/**
 * 异常记录适配器
 * Created by Administrator on 2017/2/13.
 */

public class AbnormalRecordsViewAdapter extends BGAAdapterViewAdapter<ListBean> {
    public AbnormalRecordsViewAdapter(Context context) {
        super(context, R.layout.item_abnormalrecords);
    }

//    @Override
//    protected void setItemChildListener(BGAViewHolderHelper viewHolderHelper) {
//      //  viewHolderHelper.setItemChildClickListener(R.id.tv_item_normal_delete);
//     //   viewHolderHelper.setItemChildLongClickListener(R.id.tv_item_normal_delete);
//    }

    @Override
    public void fillData(BGAViewHolderHelper viewHolderHelper, int position, ListBean listBean) {
        /**
         * 姓名
         */
        viewHolderHelper.setText(R.id.tv_name, listBean.getDr_name());

        /**
         * 车牌号
         */
        viewHolderHelper.setText(R.id.tv_cardNumber, listBean.getCard_number());

        /**
         * 车长
         */
        viewHolderHelper.setText(R.id.tv_carLength, listBean.getCard_number());

        /**
         * 车型
         */
        viewHolderHelper.setText(R.id.tv_carType, listBean.getCar_type());

        /**
         * 标签
         */
        if (StringUtils.isEmpty(listBean.getG_type())) {
            viewHolderHelper.setImageDrawable(R.id.img_label, null);
        } else if (listBean.getG_type().equals("often")) {
            viewHolderHelper.setImageResource(R.id.img_label, R.mipmap.label_shishi);
        } else if (listBean.getG_type().equals("urgent")) {
            viewHolderHelper.setImageResource(R.id.img_label, R.mipmap.label_jiaji);
        } else if (listBean.getG_type().equals("appoint")) {
            viewHolderHelper.setImageResource(R.id.img_label, R.mipmap.label_yuyue);
        }

        /**
         * 时间
         */
        viewHolderHelper.setText(R.id.tv_time, listBean.getAbnormal_time());

        /**
         * 货物名称
         */
        viewHolderHelper.setText(R.id.tv_goodName, listBean.getGoods_name());

        /**
         * 快递单号
         */
        viewHolderHelper.setText(R.id.tv_orderNumber, listBean.getOrder_code());

        /**
         * 起运地
         */
        viewHolderHelper.setText(R.id.tv_thePlace, listBean.getStart_address());

        /**
         * 目的地
         */
        viewHolderHelper.setText(R.id.tv_destination, listBean.getEnd_address());


    }

}