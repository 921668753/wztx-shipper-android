package com.ruitukeji.zwbh.adapter.main.selectaddress;

import android.content.Context;

import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.entity.main.selectaddress.InlandBean.ResultBean;

import java.util.List;


/**
 * Created by zhangxutong .
 * Date: 16/08/28
 */

public class InlandViewAdapter extends CommonAdapter<ResultBean> {
    public InlandViewAdapter(Context context, int layoutId, List<ResultBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    public void convert(ViewHolder holder, final ResultBean cityBean) {
        holder.setText(R.id.tv_country, cityBean.getName());
        holder.setText(R.id.tv_areaCode, "");
    }
}