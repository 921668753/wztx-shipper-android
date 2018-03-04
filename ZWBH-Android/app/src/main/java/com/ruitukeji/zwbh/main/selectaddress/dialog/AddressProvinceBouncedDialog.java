package com.ruitukeji.zwbh.main.selectaddress.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.kymjs.common.StringUtils;
import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.adapter.main.selectaddress.dialog.AddressProvinceViewAdapter;
import com.ruitukeji.zwbh.common.BaseDialog;
import com.ruitukeji.zwbh.common.ViewInject;
import com.ruitukeji.zwbh.entity.main.selectaddress.dialog.AddressBean;
import com.ruitukeji.zwbh.entity.main.selectaddress.dialog.AddressBean.ResultBean;
import com.ruitukeji.zwbh.utils.JsonUtil;

import java.util.List;

/**
 * 始发地/目的地---添加路线省弹框
 * Created by Administrator on 2017/11/28.
 */

public abstract class AddressProvinceBouncedDialog extends BaseDialog implements AdapterView.OnItemClickListener, View.OnClickListener, AddressBouncedContract.View {

    private String provinceName = "";
    private int provinceId = 0;
    private Context context;

    private GridView gv_address;

    private AddressProvinceViewAdapter addressProvinceViewAdapter = null;

    private AddressBouncedContract.Presenter mPresenter;
    private ResultBean provinceBean;
    private List<ResultBean> provinceBeanlist = null;
    private AddressCityBouncedDialog addressCityBouncedDialog = null;

    public AddressProvinceBouncedDialog(Context context, String provinceName, int provinceId) {
        super(context, R.style.dialog);
        this.context = context;
        this.provinceName = provinceName;
        this.provinceId = provinceId;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_addressprovince);
        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        dialogWindow.setAttributes(lp);
        initView();
    }

    private void initView() {
        mPresenter = new AddressBouncedPresenter(this);
        addressProvinceViewAdapter = new AddressProvinceViewAdapter(context);
        gv_address = (GridView) findViewById(R.id.gv_address);
        gv_address.setOnItemClickListener(this);
        gv_address.setAdapter(addressProvinceViewAdapter);
        LinearLayout ll_back = (LinearLayout) findViewById(R.id.ll_back);
        ll_back.setOnClickListener(this);
        showLoadingDialog(context.getString(R.string.dataLoad));
        mPresenter.getAddress(0, 0);
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
        provinceBean = addressProvinceViewAdapter.getItem(position);
        selectProvince(provinceBean.getId());
        if (provinceBean.getName().startsWith(context.getString(R.string.all1))) {
            cancel();
            confirmProvince(provinceBean.getName(), provinceBean.getName(), provinceBean.getId(), 0);
            return;
        }
        if (addressCityBouncedDialog != null && !addressCityBouncedDialog.isShowing()) {
            addressCityBouncedDialog.show();
            addressCityBouncedDialog.setProvinceId(provinceBean.getName(), provinceBean.getId());
            return;
        }
        addressCityBouncedDialog = new AddressCityBouncedDialog(context, provinceBean.getName(), provinceBean.getId()) {
            @Override
            public void confirmCity(String cityName, String address, int cityId) {
                this.cancel();
                if (cityName.contains(provinceBean.getName())) {
                    confirmProvince(cityName, address, provinceBean.getId(), cityId);
                    return;
                }
                confirmProvince(provinceBean.getName() + cityName, address, provinceBean.getId(), cityId);
            }
        };
        addressCityBouncedDialog.show();
    }

    public abstract void confirmProvince(String provinceName, String addressName, int provinceId, int cityId);

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
        provinceBeanlist = list;
        selectProvince(provinceId);
        dismissLoadingDialog();
    }

    /**
     * 选择省
     */
    private void selectProvince(int provinceId) {
        for (int i = 0; i < provinceBeanlist.size(); i++) {
            if (provinceId == provinceBeanlist.get(i).getId() || provinceId == i && provinceId == 0 && StringUtils.isEmpty(provinceName)
                    || provinceId == 0 && !StringUtils.isEmpty(provinceName) && provinceName.contains(provinceBeanlist.get(i).getName())) {
                provinceBean = provinceBeanlist.get(i);
                provinceBean.setStatus(1);
                gv_address.setSelection(i);
                gv_address.smoothScrollToPosition(i);
            } else {
                provinceBeanlist.get(i).setStatus(0);
            }
        }
        addressProvinceViewAdapter.clear();
        addressProvinceViewAdapter.addMoreData(provinceBeanlist);
    }


    @Override
    public void errorMsg(String msg, int flag) {
        dismissLoadingDialog();
        ViewInject.toast(msg);
    }


    @Override
    public void setOnDismissListener(@Nullable DialogInterface.OnDismissListener listener) {
        if (addressCityBouncedDialog != null) {
            addressCityBouncedDialog.cancel();
        }
        addressProvinceViewAdapter.clear();
        addressProvinceViewAdapter = null;
        addressCityBouncedDialog = null;
        super.setOnDismissListener(listener);
    }
}
