package com.ruitukeji.zwbh.main.selectaddress;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.kymjs.common.Log;
import com.kymjs.common.PreferenceHelper;
import com.kymjs.common.StringUtils;
import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.adapter.mine.addressmanagement.PioAddressViewAdapter;
import com.ruitukeji.zwbh.common.BaseActivity;
import com.ruitukeji.zwbh.common.BindView;
import com.ruitukeji.zwbh.common.ViewInject;
import com.ruitukeji.zwbh.constant.StringConstants;
import com.ruitukeji.zwbh.main.selectaddress.dialog.AddressProvinceBouncedDialog;
import com.ruitukeji.zwbh.mine.addressmanagement.AddressManagementActivity;
import com.ruitukeji.zwbh.utils.ActivityTitleUtils;

import cn.bingoogolapple.titlebar.BGATitleBar.SimpleDelegate;

import static com.ruitukeji.zwbh.constant.NumericConstants.REQUEST_CODE_CHOOSE_PHOTO;
import static com.ruitukeji.zwbh.constant.NumericConstants.REQUEST_CODE_PHOTO_PREVIEW;
import static com.ruitukeji.zwbh.constant.NumericConstants.REQUEST_CODE_PHOTO_PREVIEW1;

/**
 * 选择地址
 * Created by Administrator on 2017/12/12.
 */

public class SelectAddressActivity extends BaseActivity implements TextWatcher, AdapterView.OnItemClickListener, PoiSearch.OnPoiSearchListener {

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
    private int tran_type = 0;
    private String city = "";
    private PoiSearch.Query query;
    private PoiSearch poiSearch;
    private AddressProvinceBouncedDialog addressProvinceBouncedDialog = null;
    private String startCity = null;

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
        tran_type = getIntent().getIntExtra("tran_type", 0);
        city = getIntent().getStringExtra("cityName");
        startCity = getIntent().getStringExtra("startCity");
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
                intent.putExtra("chageIcon", getIntent().getIntExtra("type", 0));
                intent.putExtra("cargoReceipt", "cargoReceipt");
                startActivityForResult(intent, REQUEST_CODE_PHOTO_PREVIEW1);
            }
        };
        ActivityTitleUtils.initToolbar(aty, getString(R.string.selectAddress), getString(R.string.commonlyAddress), R.id.titlebar, simpleDelegate);
        tv_city.setText(city);
    }

    @Override
    public void widgetClick(View v) {
        super.widgetClick(v);
        switch (v.getId()) {
            case R.id.tv_city:
                if (tran_type == 0 && type != 0 || tran_type == 0 && type == 0 && !StringUtils.isEmpty(startCity)) {
                    ViewInject.toast(getString(R.string.notChangeCity));
                    return;
                }
//                Intent intent = new Intent(aty, SelectionCityActivity.class);
//                intent.putExtra("lat", lat);
//                intent.putExtra("longi", longi);
//                intent.putExtra("district", district);
//                startActivityForResult(intent, REQUEST_CODE_PHOTO_PREVIEW);
                if (addressProvinceBouncedDialog != null && !addressProvinceBouncedDialog.isShowing()) {
                    addressProvinceBouncedDialog.show();
                    return;
                }
                addressProvinceBouncedDialog = new AddressProvinceBouncedDialog(aty, "", 0) {
                    @Override
                    public void confirmProvince(String provinceName, String addressName, int provinceId, int cityId) {
                        if (tran_type == 1 && !StringUtils.isEmpty(startCity) && addressName.contains(startCity)) {
                            ViewInject.toast(getString(R.string.enterIntercityAddress));
                            return;
                        }
                        this.cancel();
                        tv_city.setText(addressName);
                    }
                };
                addressProvinceBouncedDialog.show();
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
//        //第二个参数传入null或者“”代表在全国进行检索，否则按照传入的city进行检索
//        InputtipsQuery inputquery = new InputtipsQuery(s.toString().trim(), tv_city.getText().toString());
//        inputquery.setCityLimit(true);//限制在当前城市
//        //   构造 Inputtips 对象，并设置监听。
//        Inputtips inputTips = new Inputtips(this, inputquery);
//        inputTips.setInputtipsListener(this);
//        //  调用 PoiSearch 的 requestInputtipsAsyn() 方法发送请求。
//        inputTips.requestInputtipsAsyn();
    }

    @Override
    public void afterTextChanged(Editable s) {
        query = new PoiSearch.Query(et_enterDeliveryLocation.getText().toString().trim(), "", tv_city.getText().toString());
//keyWord表示搜索字符串，
//第二个参数表示POI搜索类型，二者选填其一，选用POI搜索类型时建议填写类型代码，码表可以参考下方（而非文字）
//cityCode表示POI搜索区域，可以是城市编码也可以是城市名称，也可以传空字符串，空字符串代表全国在全国范围内进行搜索
        query.setPageSize(10000);// 设置每页最多返回多少条poiitem
        query.setPageNum(1);//设置查询页码
        poiSearch = new PoiSearch(this, query);
        poiSearch.setOnPoiSearchListener(this);
        poiSearch.searchPOIAsyn();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (StringUtils.isEmpty(pioAddressViewAdapter.getItem(position).getSnippet())) {
            ViewInject.toast(getString(R.string.enterDestination1));
            return;
        }
        PoiItem poiItem = pioAddressViewAdapter.getItem(position);
        lat = String.valueOf(poiItem.getLatLonPoint().getLatitude());
        longi = String.valueOf(poiItem.getLatLonPoint().getLongitude());
        int orgprovince = poiItem.getProvinceName().indexOf("省");
        if (orgprovince == -1) {
            district = poiItem.getCityName() + poiItem.getAdName();
            placeName = poiItem.getCityName() + poiItem.getAdName() + poiItem.getSnippet() + poiItem.getTitle();
        } else {
            district = poiItem.getProvinceName() + poiItem.getCityName() + poiItem.getAdName();
            placeName = poiItem.getProvinceName() + poiItem.getCityName() + poiItem.getAdName() + poiItem.getSnippet() + poiItem.getTitle();
        }
        if (tran_type == 1 && !StringUtils.isEmpty(startCity) && poiItem.getCityName().contains(startCity)) {
            ViewInject.toast(getString(R.string.enterIntercityAddress));
            return;
        }
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

//    @Override
//    public void onGetInputtips(List<Tip> list, int rCode) {
//        if (rCode == 1000) {
//            //通过tipList获取Tip信息
//            tv_divider.setVisibility(View.VISIBLE);
//            tv_divider1.setVisibility(View.VISIBLE);
//            pioAddressViewAdapter.addNewData(list);
//        } else {
//            tv_divider.setVisibility(View.GONE);
//            tv_divider1.setVisibility(View.GONE);
//            pioAddressViewAdapter.clear();
//        }
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_CHOOSE_PHOTO && resultCode == RESULT_OK) {
            /**
             * 地区选择页面返回
             */
            // 如果等于1
            // 说明是我们的那次请求
            // 目的：区分请求，不同的请求要做不同的处理
            lat = data.getStringExtra("lat");
            longi = data.getStringExtra("longi");
            district = data.getStringExtra("district");
            placeName = data.getStringExtra("placeName");
//            type = data.getIntExtra("type", 0);
            detailedAddress = data.getStringExtra("detailedAddress");
            deliveryCustomer = data.getStringExtra("deliveryCustomer");
            shipper = data.getStringExtra("shipper");
            phone = data.getStringExtra("phone");
            eixedTelephone = data.getStringExtra("eixedTelephone");
            int isOff1 = data.getIntExtra("isOff1", 0);
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
            intent.putExtra("isOff1", isOff1);
            intent.putExtra("phone", phone);
            intent.putExtra("eixedTelephone", eixedTelephone);
            setResult(RESULT_OK, intent);
            // 结束该activity 结束之后，前面的activity才可以处理结果
            finish();
        } else if (requestCode == REQUEST_CODE_PHOTO_PREVIEW && resultCode == RESULT_OK) {
            String selectCity = data.getStringExtra("selectCity");
            int selectCityId = data.getIntExtra("selectCityId", 0);
            String selectCountry = data.getStringExtra("selectCountry");
            int selectCountryId = data.getIntExtra("selectCountryId", 0);
            tv_city.setText(selectCity);
        } else if (requestCode == REQUEST_CODE_PHOTO_PREVIEW1 && resultCode == RESULT_OK) {
            /**
             * 地址管理页面返回
             */
            // 如果等于1
            // 说明是我们的那次请求
            // 目的：区分请求，不同的请求要做不同的处理
            PreferenceHelper.write(aty, StringConstants.FILENAME, "isDefaultAddress", false);
            PreferenceHelper.write(aty, StringConstants.FILENAME, "isDefaultAddress1", false);
            placeName = data.getStringExtra("placeName");
            if (tran_type == 0 && type == 0 && !StringUtils.isEmpty(startCity) && !placeName.contains(startCity)) {
                ViewInject.toast(getString(R.string.enterAddressSameCity));
                return;
            }
            if (tran_type == 0 && !placeName.contains(city) && type == 1) {
                ViewInject.toast(getString(R.string.enterAddressSameCity));
                return;
            }
            if (tran_type == 1 && !StringUtils.isEmpty(startCity) && placeName.contains(startCity)) {
                ViewInject.toast(getString(R.string.enterIntercityAddress));
                return;
            }
            lat = data.getStringExtra("lat");
            longi = data.getStringExtra("longi");
            district = data.getStringExtra("district");
            //    type = data.getIntExtra("type", 0);
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
            intent.putExtra("isFinish", 1);
            intent.putExtra("detailedAddress", detailedAddress);
            intent.putExtra("deliveryCustomer", deliveryCustomer);
            intent.putExtra("shipper", shipper);
            intent.putExtra("phone", phone);
            intent.putExtra("isOff1", 1);
            intent.putExtra("eixedTelephone", eixedTelephone);
            setResult(RESULT_OK, intent);
            // 结束该activity 结束之后，前面的activity才可以处理结果
            finish();
        }
    }

    @Override
    public void onPoiSearched(PoiResult poiResult, int rCode) {
        //解析result获取POI信息
        if (rCode == AMapException.CODE_AMAP_SUCCESS && poiResult != null && poiResult.getQuery() != null && poiResult.getQuery().equals(query) && poiResult.getPois() != null && poiResult.getPois().size() > 0) {// 搜索poi的结果
            poiResult = poiResult;
            // 取得搜索到的poiitems有多少页
            // 取得第一页的poiitem数据，页数从数字0开始
            pioAddressViewAdapter.clear();
            pioAddressViewAdapter.addNewData(poiResult.getPois());
            tv_divider.setVisibility(View.VISIBLE);
            tv_divider1.setVisibility(View.VISIBLE);
        } else {
            ViewInject.toast(getString(R.string.no_result));
            tv_divider.setVisibility(View.GONE);
            tv_divider1.setVisibility(View.GONE);
            pioAddressViewAdapter.clear();
        }
    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (addressProvinceBouncedDialog != null) {
            addressProvinceBouncedDialog.cancel();
        }
        pioAddressViewAdapter.clear();
        pioAddressViewAdapter = null;
        addressProvinceBouncedDialog = null;
    }

}
