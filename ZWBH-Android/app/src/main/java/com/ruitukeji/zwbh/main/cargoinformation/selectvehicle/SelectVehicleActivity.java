package com.ruitukeji.zwbh.main.cargoinformation.selectvehicle;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.adapter.LengthsViewAdapter;
import com.ruitukeji.zwbh.adapter.TypesViewAdapter;
import com.ruitukeji.zwbh.application.MyApplication;
import com.ruitukeji.zwbh.common.BaseActivity;
import com.ruitukeji.zwbh.common.BindView;
import com.ruitukeji.zwbh.common.ViewInject;
import com.ruitukeji.zwbh.entity.main.cargoinformation.selectvehicle.ConductorModelsBean;
import com.ruitukeji.zwbh.entity.main.cargoinformation.selectvehicle.ConductorModelsBean.ResultBean.LengthBean;
import com.ruitukeji.zwbh.entity.main.cargoinformation.selectvehicle.ConductorModelsBean.ResultBean.TypeBean;
import com.ruitukeji.zwbh.mine.setting.aboutus.AboutUsActivity;
import com.ruitukeji.zwbh.utils.ActivityTitleUtils;
import com.ruitukeji.zwbh.utils.JsonUtil;
import com.ruitukeji.zwbh.utils.myview.NoScrollGridView;

import java.util.List;

import cn.bingoogolapple.titlebar.BGATitleBar.SimpleDelegate;

/**
 * 选择车辆
 * Created by Administrator on 2017/2/21.
 */

public class SelectVehicleActivity extends BaseActivity implements SelectVehicleContract.View, AdapterView.OnItemClickListener {

    /**
     * 车长
     */
    @BindView(id = R.id.gv_vehiclelength)
    private NoScrollGridView gv_vehiclelength;
    private LengthsViewAdapter lengthsViewAdapter;
    List<LengthBean> lengthBeanlist;

    /**
     * 车型
     */
    @BindView(id = R.id.gv_vehiclemodel)
    private NoScrollGridView gv_vehiclemodel;
    private TypesViewAdapter typesViewAdapter;
    List<TypeBean> typeBeanlist;
    private LengthBean lengthBean;
    private TypeBean typeBean;
    private int vehicleModelId = 0;
    private int vehicleLengthId = 0;


    /**
     * 提交
     */
    @BindView(id = R.id.tv_submit, click = true)
    private TextView tv_submit;


    @Override
    public void setRootView() {
        setContentView(R.layout.activity_selectvehicle);
    }

    @Override
    public void initData() {
        super.initData();
        mPresenter = new SelectVehiclePresenter(this);
        lengthsViewAdapter = new LengthsViewAdapter(this);
        typesViewAdapter = new TypesViewAdapter(this);
        vehicleModelId = getIntent().getIntExtra("vehicleModelId", 0);
        vehicleLengthId = getIntent().getIntExtra("vehicleLengthId", 0);
    }

    @Override
    public void initWidget() {
        super.initWidget();
        SimpleDelegate simpleDelegate = new SimpleDelegate() {
            @Override
            public void onClickLeftCtv() {
                super.onClickLeftCtv();
                aty.finish();
            }

            @Override
            public void onClickRightCtv() {
                super.onClickRightCtv();
                Intent intent = new Intent(aty, AboutUsActivity.class);
                intent.putExtra("type", "type");
                showActivity(aty, intent);
            }
        };
        ActivityTitleUtils.initToolbar(aty, getString(R.string.selectVehicle), getString(R.string.costDescription), R.id.titlebar, simpleDelegate);
        showLoadingDialog(MyApplication.getContext().getString(R.string.dataLoad));
        ((SelectVehicleContract.Presenter) mPresenter).getConductorModels();
        gv_vehiclelength.setAdapter(lengthsViewAdapter);
        gv_vehiclelength.setOnItemClickListener(this);
        gv_vehiclemodel.setAdapter(typesViewAdapter);
        gv_vehiclemodel.setOnItemClickListener(this);
    }


    @Override
    public void widgetClick(View v) {
        super.widgetClick(v);
        switch (v.getId()) {
            case R.id.tv_submit:
                if (typeBean == null || lengthBean == null) {
                    ViewInject.toast(getString(R.string.pleaseSelect) + getString(R.string.vehicleLengthModel));
                    return;
                }
                Intent intent = new Intent();
                // 获取内容
                intent.putExtra("vehicleModelId", typeBean.getId());
                intent.putExtra("vehicleModel", typeBean.getName());
                intent.putExtra("vehicleLengthId", lengthBean.getId());
                intent.putExtra("vehicleLength", lengthBean.getName());
                intent.putExtra("over_metres_price", String.valueOf(lengthBean.getOver_metres_price()));
                intent.putExtra("weight_price", String.valueOf(lengthBean.getWeight_price()));
                intent.putExtra("init_kilometres", String.valueOf(lengthBean.getInit_kilometres()));
                intent.putExtra("init_price", String.valueOf(lengthBean.getInit_price()));
                // 设置结果 结果码，一个数据
                setResult(RESULT_OK, intent);
                // 结束该activity 结束之后，前面的activity才可以处理结果
                aty.finish();
                break;
        }
    }

    @Override
    public void getSuccess(String s) {
        ConductorModelsBean conductorModelsBean = (ConductorModelsBean) JsonUtil.getInstance().json2Obj(s, ConductorModelsBean.class);
        lengthBeanlist = conductorModelsBean.getResult().getLength();
        if (lengthBeanlist != null && lengthBeanlist.size() > 0) {
            selectedLength(vehicleLengthId);
        }
        typeBeanlist = conductorModelsBean.getResult().getType();
        if (typeBeanlist != null && typeBeanlist.size() > 0) {
            selectedType(vehicleModelId);
        }
        dismissLoadingDialog();
    }

    /**
     * 选中车长
     *
     * @param position
     */
    private void selectedLength(int position) {
        for (int i = 0; i < lengthBeanlist.size(); i++) {
            if (position == lengthBeanlist.get(i).getId() || position == i && position == 0) {
                lengthBean = lengthBeanlist.get(i);
                lengthBean.setStatus(1);
            } else {
                lengthBeanlist.get(i).setStatus(0);
            }
        }
        lengthsViewAdapter.clear();
        lengthsViewAdapter.addMoreData(lengthBeanlist);
    }

    /**
     * 选中车型
     *
     * @param position
     */
    private void selectedType(int position) {
        for (int i = 0; i < typeBeanlist.size(); i++) {
            if (position == typeBeanlist.get(i).getId() || position == i && position == 0) {
                typeBean = typeBeanlist.get(i);
                typeBean.setStatus(1);
            } else {
                typeBeanlist.get(i).setStatus(0);
            }
        }
        typesViewAdapter.clear();
        typesViewAdapter.addMoreData(typeBeanlist);
    }

    @Override
    public void error(String msg) {
        dismissLoadingDialog();
        ViewInject.toast(msg);
    }

    @Override
    public void setPresenter(SelectVehicleContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == R.id.gv_vehiclelength) {
            selectedLength(lengthsViewAdapter.getItem(position).getId());
        } else if (parent.getId() == R.id.gv_vehiclemodel) {
            selectedType(typesViewAdapter.getItem(position).getId());
        }
    }
}
