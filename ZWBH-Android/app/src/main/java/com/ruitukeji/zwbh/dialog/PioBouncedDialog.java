package com.ruitukeji.zwbh.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.services.help.Inputtips;
import com.amap.api.services.help.InputtipsQuery;
import com.amap.api.services.help.Tip;
import com.kymjs.common.StringUtils;
import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.adapter.PioAddressViewAdapter;
import com.ruitukeji.zwbh.common.ViewInject;

import java.util.List;

/**
 * 地址搜索--------发货信息地址搜索弹框POI
 * Created by Administrator on 2017/2/21.
 */

public abstract class PioBouncedDialog extends Dialog implements TextWatcher, OnItemClickListener, Inputtips.InputtipsListener {


    private Context context;
    private PioAddressViewAdapter pioAddressViewAdapter;

    public PioBouncedDialog(Context context) {
        super(context, R.style.MyDialog);
        this.context = context;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_piobounced);
        getWindow().setLayout(RelativeLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.FILL_PARENT);
        initView();
    }

    private void initView() {
        TextView tv_cancel = (TextView) findViewById(R.id.tv_cancel);
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        EditText et_searchView = (EditText) findViewById(R.id.et_searchView);
        et_searchView.addTextChangedListener(this);

        ListView lv_search = (ListView) findViewById(R.id.lv_search);
        pioAddressViewAdapter = new PioAddressViewAdapter(context);
        lv_search.setAdapter(pioAddressViewAdapter);
        lv_search.setOnItemClickListener(this);
    }

    public abstract void getAddress(Tip tip);


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
//        if (RouteTask.getInstance(getApplicationContext()).getStartPoint() == null) {
//            Toast.makeText(getApplicationContext(), "检查网络，Key等问题", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        InputTipTask.getInstance(getApplicationContext(), mRecomandAdapter).searchTips(s.toString(),
//                RouteTask.getInstance(getApplicationContext()).getStartPoint().city);
//        PoiSearchTask poiSearchTask = new PoiSearchTask(getApplicationContext(), mRecomandAdapter);
//        poiSearchTask.search(mDestinaionText.getText().toString(), RouteTask.getInstance(getApplicationContext()).getStartPoint().city);
//第二个参数传入null或者“”代表在全国进行检索，否则按照传入的city进行检索
        InputtipsQuery inputquery = new InputtipsQuery(s.toString(), null);
        // inputquery.setCityLimit(true);//限制在当前城市
        //   构造 Inputtips 对象，并设置监听。
        Inputtips inputTips = new Inputtips(context, inputquery);
        inputTips.setInputtipsListener(this);
        //  调用 PoiSearch 的 requestInputtipsAsyn() 方法发送请求。
        inputTips.requestInputtipsAsyn();
    }

    @Override
    public void afterTextChanged(Editable s) {

    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        pioAddressViewAdapter.getItem(position).getAddress();
//        ViewInject.toast(pioAddressViewAdapter.getItem(position).getAddress());
//        ViewInject.toast(pioAddressViewAdapter.getItem(position).getDistrict());
        if (StringUtils.isEmpty(pioAddressViewAdapter.getItem(position).getDistrict())) {
            ViewInject.toast(context.getString(R.string.enterDestination1));
            return;
        }
        dismiss();
        getAddress(pioAddressViewAdapter.getItem(position));
    }

    @Override
    public void onGetInputtips(List<Tip> list, int rCode) {
        if (rCode == 1000) {
            //通过tipList获取Tip信息
            // ViewInject.toast(list.get(0).getAddress());
            pioAddressViewAdapter.addNewData(list);

        } else {
//            ViewInject.toast(rCode + "");
            pioAddressViewAdapter.clear();
        }
    }
}
