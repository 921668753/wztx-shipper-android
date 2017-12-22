package com.ruitukeji.zwbh.main.selectaddress;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.amap.api.services.help.Inputtips;
import com.amap.api.services.help.InputtipsQuery;
import com.amap.api.services.help.Tip;
import com.kymjs.common.StringUtils;
import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.adapter.mine.addressmanagement.PioAddressViewAdapter;
import com.ruitukeji.zwbh.common.BaseActivity;
import com.ruitukeji.zwbh.common.BindView;
import com.ruitukeji.zwbh.common.ViewInject;
import com.ruitukeji.zwbh.mine.addressmanagement.AddressManagementActivity;
import com.ruitukeji.zwbh.utils.ActivityTitleUtils;

import java.util.List;

import cn.bingoogolapple.titlebar.BGATitleBar.SimpleDelegate;

import static com.ruitukeji.zwbh.constant.NumericConstants.REQUEST_CODE_CHOOSE_PHOTO;
import static com.ruitukeji.zwbh.constant.NumericConstants.REQUEST_CODE_PHOTO_PREVIEW2;

/**
 * 选择地址
 * Created by Administrator on 2017/12/12.
 */

public class SelectAddressActivity extends BaseActivity implements TextWatcher, AdapterView.OnItemClickListener, Inputtips.InputtipsListener {

    /**
     * 城市
     */
    @BindView(id = R.id.tv_city, click = true)
    private TextView tv_city;

    /**
     * 发货地点
     */
    @BindView(id = R.id.et_enterDeliveryLocation)
    private EditText et_enterDeliveryLocation;

    @BindView(id = R.id.tv_divider)
    private TextView tv_divider;

    @BindView(id = R.id.lv_selectAddress)
    private ListView lv_selectAddress;

    private PioAddressViewAdapter pioAddressViewAdapter;

    @BindView(id = R.id.tv_divider1)
    private TextView tv_divider1;

    private String title = null;
    private String detailedAddress;
    private String deliveryCustomer;
    private String shipper;
    private String phone;
    private String eixedTelephone;
    private String lat;
    private String longi;
    private String district;
    private String placeName;
    private int type = 0;

    @Override
    public void setRootView() {
        setContentView(R.layout.activity_selectaddress);
    }

    @Override
    public void initData() {
        super.initData();
        pioAddressViewAdapter = new PioAddressViewAdapter(this);
        lv_selectAddress.setAdapter(pioAddressViewAdapter);
        type = getIntent().getIntExtra("type", 0);
        title = getIntent().getStringExtra("title");
        detailedAddress = getIntent().getStringExtra("detailedAddress");
        deliveryCustomer = getIntent().getStringExtra("deliveryCustomer");
        shipper = getIntent().getStringExtra("shipper");
        phone = getIntent().getStringExtra("phone");
        eixedTelephone = getIntent().getStringExtra("eixedTelephone");
        lat = getIntent().getStringExtra("lat");
        longi = getIntent().getStringExtra("longi");
        district = getIntent().getStringExtra("district");
        placeName = getIntent().getStringExtra("placeName");
    }

    @Override
    public void initWidget() {
        super.initWidget();
        lv_selectAddress.setOnItemClickListener(this);
        et_enterDeliveryLocation.addTextChangedListener(this);
        SimpleDelegate simpleDelegate = new SimpleDelegate() {
            @Override
            public void onClickLeftCtv() {
                super.onClickLeftCtv();
                aty.finish();
            }

            @Override
            public void onClickRightCtv() {
                super.onClickRightCtv();
                Intent intent = new Intent(aty, AddressManagementActivity.class);
                intent.putExtra("chageIcon", 0);
//                intent.putExtra("cargoReceipt", cargoReceipt);
                startActivityForResult(intent, REQUEST_CODE_CHOOSE_PHOTO);
            }
        };
        ActivityTitleUtils.initToolbar(aty, getString(R.string.selectAddress), getString(R.string.commonlyAddress), R.id.titlebar, simpleDelegate);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        //第二个参数传入null或者“”代表在全国进行检索，否则按照传入的city进行检索
        InputtipsQuery inputquery = new InputtipsQuery(s.toString().trim(), tv_city.getText().toString());
        inputquery.setCityLimit(true);//限制在当前城市
        //   构造 Inputtips 对象，并设置监听。
        Inputtips inputTips = new Inputtips(this, inputquery);
        inputTips.setInputtipsListener(this);
        //  调用 PoiSearch 的 requestInputtipsAsyn() 方法发送请求。
        inputTips.requestInputtipsAsyn();
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (StringUtils.isEmpty(pioAddressViewAdapter.getItem(position).getDistrict())) {
            ViewInject.toast(getString(R.string.enterDestination1));
            return;
        }
        Tip tip = pioAddressViewAdapter.getItem(position);
        lat = String.valueOf(tip.getPoint().getLatitude());
        longi = String.valueOf(tip.getPoint().getLongitude());
        district = tip.getDistrict();
        placeName = tip.getDistrict() + tip.getAddress() + tip.getName();

        if (getIntent().getIntExtra("isProvenance", 0) == 1) {
            Intent intent = new Intent();
            intent.putExtra("lat", lat);
            intent.putExtra("longi", longi);
            intent.putExtra("district", district);
            intent.putExtra("placeName", placeName);
            setResult(RESULT_OK, intent);
            // 结束该activity 结束之后，前面的activity才可以处理结果
            finish();
            return;
        }
        Intent intent = new Intent(aty, ProvenanceActivity.class);
        intent.putExtra("lat", lat);
        intent.putExtra("longi", longi);
        intent.putExtra("district", district);
        intent.putExtra("placeName", placeName);
        intent.putExtra("type", type);
        intent.putExtra("title", title);
        intent.putExtra("detailedAddress", detailedAddress);
        intent.putExtra("deliveryCustomer", deliveryCustomer);
        intent.putExtra("shipper", shipper);
        intent.putExtra("phone", phone);
        intent.putExtra("eixedTelephone", eixedTelephone);
        startActivityForResult(intent, REQUEST_CODE_CHOOSE_PHOTO);
    }

    @Override
    public void onGetInputtips(List<Tip> list, int rCode) {
        if (rCode == 1000) {
            //通过tipList获取Tip信息
            tv_divider.setVisibility(View.VISIBLE);
            tv_divider1.setVisibility(View.VISIBLE);
            pioAddressViewAdapter.addNewData(list);
        } else {
            tv_divider.setVisibility(View.GONE);
            tv_divider1.setVisibility(View.GONE);
            pioAddressViewAdapter.clear();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_CHOOSE_PHOTO && resultCode == RESULT_OK) {
            /**
             * 地址管理页面返回
             */
            // 如果等于1
            // 说明是我们的那次请求
            // 目的：区分请求，不同的请求要做不同的处理
            lat = data.getStringExtra("lat");
            longi = data.getStringExtra("longi");
            district = data.getStringExtra("district");
            placeName = data.getStringExtra("placeName");
            type = data.getIntExtra("type", 0);
            detailedAddress = data.getStringExtra("detailedAddress");
            deliveryCustomer = data.getStringExtra("deliveryCustomer");
            shipper = data.getStringExtra("shipper");
            phone = data.getStringExtra("phone");
            eixedTelephone = data.getStringExtra("eixedTelephone");
            Intent intent = new Intent();
            intent.putExtra("lat", lat);
            intent.putExtra("longi", longi);
            intent.putExtra("district", district);
            intent.putExtra("placeName", placeName);
            intent.putExtra("type", getIntent().getIntExtra("type", 0));
            intent.putExtra("title", title);
            intent.putExtra("detailedAddress", detailedAddress);
            intent.putExtra("deliveryCustomer", deliveryCustomer);
            intent.putExtra("shipper", shipper);
            intent.putExtra("phone", phone);
            intent.putExtra("eixedTelephone", eixedTelephone);
            setResult(RESULT_OK, intent);
            // 结束该activity 结束之后，前面的activity才可以处理结果
            finish();
        }

    }
}
