package com.ruitukeji.zwbh.main.selectaddress.dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kymjs.common.StringUtils;
import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.adapter.main.selectaddress.dialog.AddressCityViewAdapter;
import com.ruitukeji.zwbh.common.BaseDialog;
import com.ruitukeji.zwbh.common.ViewInject;
import com.ruitukeji.zwbh.entity.main.selectaddress.dialog.AddressBean;
import com.ruitukeji.zwbh.entity.main.selectaddress.dialog.AddressBean.ResultBean;
import com.ruitukeji.zwbh.utils.JsonUtil;
import com.ruitukeji.zwbh.utils.rx.MsgEvent;
import com.ruitukeji.zwbh.utils.rx.RxBus;

import java.util.List;

/**
 * 始发地/目的地---添加路线市弹框
 * Created by Administrator on 2017/11/28.
 */

public abstract class AddressCityBouncedDialog extends BaseDialog implements AdapterView.OnItemClickListener, View.OnClickListener, AddressBouncedContract.View {

    private String provinceName = "";
    private int provinceId = 0;
    private Context context;

    private GridView gv_address;

    ResultBean cityBean;
    private String cityName = "";
    private int cityId = 0;

    private AddressBouncedContract.Presenter mPresenter;

    private List<ResultBean> cityBeanlist = null;
    private AddressCityViewAdapter cityViewAdapter = null;
    // private AddLineAreaBouncedDialog addLineAreaBouncedDialog = null;

    public AddressCityBouncedDialog(Context context, String provinceName, int provinceId) {
        super(context, R.style.dialog);
        this.context = context;
        this.provinceName = provinceName;
        this.provinceId = provinceId;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_addresscity);
        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        dialogWindow.setAttributes(lp);
        initView();
    }

    private void initView() {
        mPresenter = new AddressBouncedPresenter(this);
        cityViewAdapter = new AddressCityViewAdapter(context);
        gv_address = (GridView) findViewById(R.id.gv_address);
        gv_address.setOnItemClickListener(this);
        gv_address.setAdapter(cityViewAdapter);
        LinearLayout ll_back = (LinearLayout) findViewById(R.id.ll_back);
        ll_back.setOnClickListener(this);
        TextView tv_select = (TextView) findViewById(R.id.tv_select);
        tv_select.setText(provinceName);
        showLoadingDialog(context.getString(R.string.dataLoad));
        mPresenter.getAddress(provinceId, 0);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_back:
                cancel();
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        cityBean = cityViewAdapter.getItem(position);
        selectCity(cityBean.getId());
        cancel();
        if (cityBean.getName().startsWith(context.getString(R.string.all1))) {
            confirmCity("", provinceName, 0);
            return;
        }
        confirmCity(cityBean.getName(), cityBean.getName(), cityBean.getId());
    }


    public abstract void confirmCity(String cityName, String addressName, int cityId);

    @Override
    public void setPresenter(AddressBouncedContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void getSuccess(String success, int flag) {
        AddressBean addressBean = (AddressBean) JsonUtil.json2Obj(success, AddressBean.class);
        List<ResultBean> list = addressBean.getResult();
        if (list == null || list.size() == 0) {
            errorMsg(context.getString(R.string.serverReturnsDataNull), 0);
            return;
        }
        cityBeanlist = list;
        selectCity(cityId);
        dismissLoadingDialog();
    }

    /**
     * 选择城市
     */
    private void selectCity(int cityId) {
        for (int i = 0; i < cityBeanlist.size(); i++) {
            if (cityId == cityBeanlist.get(i).getId() || cityId == i && cityId == 0 && StringUtils.isEmpty(cityName)
                    || cityId == 0 && !StringUtils.isEmpty(cityName) && cityName.contains(cityBeanlist.get(i).getName())) {
                cityBean = cityBeanlist.get(i);
                cityBean.setStatus(1);
                gv_address.setSelection(i);
                gv_address.smoothScrollToPosition(i);
            } else {
                cityBeanlist.get(i).setStatus(0);
            }
        }
        cityViewAdapter.clear();
        cityViewAdapter.addMoreData(cityBeanlist);
    }

    @Override
    public void errorMsg(String msg, int flag) {
        dismissLoadingDialog();
        ViewInject.toast(msg);
    }

    public void setProvinceId(String provinceName, int provinceId) {
        this.provinceName = provinceName;
        this.provinceId = provinceId;
        TextView tv_select = (TextView) findViewById(R.id.tv_select);
        tv_select.setText(provinceName);
        showLoadingDialog(context.getString(R.string.dataLoad));
        mPresenter.getAddress(provinceId, 0);
    }

}
