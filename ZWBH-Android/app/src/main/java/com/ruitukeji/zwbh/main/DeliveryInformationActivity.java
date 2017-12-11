package com.ruitukeji.zwbh.main;

import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.help.Tip;
import com.kymjs.common.StringUtils;
import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.common.BaseActivity;
import com.ruitukeji.zwbh.common.BindView;
import com.ruitukeji.zwbh.common.ViewInject;
import com.ruitukeji.zwbh.dialog.PioBouncedDialog;
import com.ruitukeji.zwbh.utils.ActivityTitleUtils;

import java.util.regex.Pattern;

/**
 * 发货信息
 * Created by Administrator on 2017/2/23.
 */

public class DeliveryInformationActivity extends BaseActivity {

    /**
     * 出发地
     */
    @BindView(id = R.id.tv_address, click = true)
    private TextView tv_address;
    private PioBouncedDialog pioBouncedDialog = null;

    @BindView(id = R.id.et_detailedAddress)
    private EditText et_detailedAddress;

    @BindView(id = R.id.et_name)
    private EditText et_name;
    @BindView(id = R.id.et_phone)
    private EditText et_phone;
    @BindView(id = R.id.et_phone1)
    private EditText et_phone1;

    @BindView(id = R.id.tv_determine, click = true)
    private TextView tv_determine;
    private String address = null;
    private String detailedAddress = null;
    private String name = null;
    private String phone = null;
    private String phone1 = null;
    private String lat = null;
    private String longi = null;
    private String cityAddress = null;

    @Override
    public void setRootView() {
        setContentView(R.layout.activity_deliveryinformation);
    }

    @Override
    public void initData() {
        super.initData();
        address = getIntent().getStringExtra("address");
        detailedAddress = getIntent().getStringExtra("detailedAddress");
        cityAddress = getIntent().getStringExtra("cityAddress");
        name = getIntent().getStringExtra("name");
        phone = getIntent().getStringExtra("phone");
        phone1 = getIntent().getStringExtra("phone1");
        lat = getIntent().getStringExtra("lat");
        longi = getIntent().getStringExtra("longi");
        if (StringUtils.isEmpty(address)) {
            tv_address.setTextColor(getResources().getColor(R.color.hintcolors));
            tv_address.setText(getString(R.string.pleaseEnter));
        } else {
            tv_address.setTextColor(getResources().getColor(R.color.textColor));
            tv_address.setText(address);
        }
        if (StringUtils.isEmpty(detailedAddress)) {
            et_detailedAddress.setText("");
        } else {
            et_detailedAddress.setText(detailedAddress);
        }
        if (StringUtils.isEmpty(name)) {
            et_name.setText("");
        } else {
            et_name.setText(name);
        }
        if (StringUtils.isEmpty(phone)) {
            et_phone.setText("");
        } else {
            et_phone.setText(phone);
        }
        if (StringUtils.isEmpty(phone1)) {
            et_phone1.setText("");
        } else {
            et_phone1.setText(phone1);
        }
//        if (StringUtils.isEmpty(lat)) {
//            lat = "0";
//        }
//        if (StringUtils.isEmpty(longi)) {
//            longi = "0";
//        }
    }

    @Override
    public void initWidget() {
        super.initWidget();
        ActivityTitleUtils.initToolbar(aty, getString(R.string.deliveryInformation), true, R.id.titlebar);
    }


    @Override
    public void widgetClick(View v) {
        super.widgetClick(v);
        switch (v.getId()) {
            case R.id.tv_address:
                pioBouncedDialog = new PioBouncedDialog(this) {
                    @Override
                    public void getAddress(Tip tip) {
                        LatLonPoint latLonPoint = tip.getPoint();
                        if (latLonPoint == null) {
                            latLonPoint = new LatLonPoint(0, 0);
                            tip.setPostion(latLonPoint);
                            //     ViewInject.toast(getString(R.string.latLongErr));
//                            pioBouncedDialog = null;
//                            return;
                        }
                        cityAddress = tip.getDistrict();
                        if (StringUtils.isEmpty(cityAddress)) {
                            ViewInject.toast(getString(R.string.enterDestination));
                            return;
                        }
                        lat = String.valueOf(tip.getPoint().getLatitude());
                        longi = String.valueOf(tip.getPoint().getLongitude());
                        tv_address.setTextColor(getResources().getColor(R.color.textColor));
                        tv_address.setText(tip.getDistrict() + tip.getName());
                        pioBouncedDialog = null;
                    }
                };
                pioBouncedDialog.show();
                //     pioBouncedDialog.setCanceledOnTouchOutside(false);点击外围不消失
                break;
            case R.id.tv_determine:
                setAddressResult();
                break;
        }
    }

    private void setAddressResult() {
        String address = tv_address.getText().toString().trim();
        if (StringUtils.isEmpty(address)) {
            ViewInject.toast(getString(R.string.enterDestination));
            return;
        }
//        String detailedAddress = et_detailedAddress.getText().toString().trim();
//        if (StringUtils.isEmpty(detailedAddress)) {
//            ViewInject.toast(getString(R.string.pleaseFillDetails));
//            return;
//        }
        String name = et_name.getText().toString().trim();
        if (StringUtils.isEmpty(name)) {
            ViewInject.toast(getString(R.string.pleaseFillOut) + getString(R.string.name));
            return;
        }


        String all = "^[A-Za-z\\u4e00-\\u9fa5]{2,10}";//{2,10}表示字符的长度是2-10
        Pattern pattern = Pattern.compile(all);
        boolean tf = Pattern.matches(all, name);
        if (!tf) {
            ViewInject.toast(getString(R.string.hintName1));
            return;
        }
        String phone = et_phone.getText().toString().trim();
        if (StringUtils.isEmpty(phone)) {
            ViewInject.toast(getString(R.string.pleaseFillOut) + getString(R.string.phone));
            return;
        }
        if (phone.length() != 11) {
            ViewInject.toast(getString(R.string.inputPhone));
            return;
        }
        String phone1 = et_phone1.getText().toString().trim();
        Intent intent = new Intent();
        // 获取内容
        intent.putExtra("address", address);
        intent.putExtra("detailedAddress", et_detailedAddress.getText().toString().trim());
        intent.putExtra("cityAddress", cityAddress);
        intent.putExtra("name", name);
        intent.putExtra("phone", phone);
        intent.putExtra("phone1", phone1);
        intent.putExtra("lat", lat);
        intent.putExtra("longi", longi);
        // 设置结果 结果码，一个数据
        setResult(RESULT_OK, intent);
        // 结束该activity 结束之后，前面的activity才可以处理结果
        aty.finish();
    }


    /**
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (pioBouncedDialog != null && pioBouncedDialog.isShowing()) {
                pioBouncedDialog.dismiss();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        pioBouncedDialog = null;
    }
}

