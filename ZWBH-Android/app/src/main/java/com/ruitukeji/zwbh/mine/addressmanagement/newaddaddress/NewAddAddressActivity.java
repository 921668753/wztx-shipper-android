package com.ruitukeji.zwbh.mine.addressmanagement.newaddaddress;

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
import com.kymjs.common.StringUtils;
import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.adapter.mine.addressmanagement.PioAddressViewAdapter;
import com.ruitukeji.zwbh.common.BaseActivity;
import com.ruitukeji.zwbh.common.BindView;
import com.ruitukeji.zwbh.common.ViewInject;
import com.ruitukeji.zwbh.main.selectaddress.selectioncity.SelectionCityActivity;
import com.ruitukeji.zwbh.utils.ActivityTitleUtils;

import cn.bingoogolapple.titlebar.BGATitleBar.SimpleDelegate;

import static com.ruitukeji.zwbh.constant.NumericConstants.REQUEST_CODE_CHOOSE_PHOTO;
import static com.ruitukeji.zwbh.constant.NumericConstants.REQUEST_CODE_PHOTO_PREVIEW;

/**
 * 新增地址
 * Created by Administrator on 2017/12/12.
 */

public class NewAddAddressActivity extends BaseActivity implements TextWatcher, AdapterView.OnItemClickListener, PoiSearch.OnPoiSearchListener {

    /**
     * 城市
     */
    @BindView(id = R.id.tv_city, click = true)
    private TextView tv_city;

    /**
     * 地址
     */
    @BindView(id = R.id.et_enterDeliveryLocation)
    private EditText et_enterDeliveryLocation;

    @BindView(id = R.id.tv_divider)
    private TextView tv_divider;

    @BindView(id = R.id.lv_selectAddress)
    private ListView lv_selectAddress;

    @BindView(id = R.id.tv_divider1)
    private TextView tv_divider1;

    private PioAddressViewAdapter pioAddressViewAdapter;
    private String title = null;
    private String lat;
    private String longi;
    private String district;
    private String placeName;
    private int type = 0;
    private String hintText = "";

    private PoiSearch.Query query;
    private PoiSearch poiSearch;

    @Override
    public void setRootView() {
        setContentView(R.layout.activity_newaddaddress);
    }

    @Override
    public void initData() {
        super.initData();
        pioAddressViewAdapter = new PioAddressViewAdapter(this);
        lv_selectAddress.setAdapter(pioAddressViewAdapter);
        lv_selectAddress.setOnItemClickListener(this);
        et_enterDeliveryLocation.addTextChangedListener(this);
        type = getIntent().getIntExtra("type", 0);
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
                et_enterDeliveryLocation.setText("");
            }
        };
        if (type == 0 || type == 1) {
            hintText = getString(R.string.pleaseEnterDeparturePoint);
        } else if (type == 2 || type == 3) {
            hintText = getString(R.string.enterDestination);
        }
        if (type == 0 || type == 2) {
            title = getString(R.string.newAddress);
        } else if (type == 1 || type == 3) {
            title = getString(R.string.changeAddress);
        }
        et_enterDeliveryLocation.setHint(hintText);
        ActivityTitleUtils.initToolbar(aty, title, getString(R.string.cancel), R.id.titlebar, simpleDelegate);
    }


    @Override
    public void widgetClick(View v) {
        super.widgetClick(v);
        switch (v.getId()) {
            case R.id.tv_city:
                Intent intent = new Intent(aty, SelectionCityActivity.class);
//                intent.putExtra("lat", lat);
//                intent.putExtra("longi", longi);
//                intent.putExtra("district", district);
                startActivityForResult(intent, REQUEST_CODE_PHOTO_PREVIEW);
                break;
        }

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
////第二个参数传入null或者“”代表在全国进行检索，否则按照传入的city进行检索
//        InputtipsQuery inputquery = new InputtipsQuery(s.toString(), tv_city.getText().toString());
//        inputquery.setCityLimit(true);//限制在当前城市
//        //   构造 Inputtips 对象，并设置监听。
//        Inputtips inputTips = new Inputtips(this, inputquery);
//        inputTips.setInputtipsListener(this);
//        //  调用 PoiSearch 的 requestInputtipsAsyn() 方法发送请求。
//        inputTips.requestInputtipsAsyn();
    }

    @Override
    public void afterTextChanged(Editable s) {
        if (StringUtils.isEmpty(et_enterDeliveryLocation.getText().toString().trim())) {
            return;
        }
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
        district = poiItem.getProvinceName() + poiItem.getCityName() + poiItem.getAdName();
        placeName = poiItem.getProvinceName() + poiItem.getCityName() + poiItem.getAdName() + poiItem.getSnippet() + poiItem.getTitle();
        if (type == 1 || type == 3) {
            Intent intent = new Intent();
            intent.putExtra("lat", String.valueOf(lat));
            intent.putExtra("longi", String.valueOf(longi));
            intent.putExtra("district", district);
            intent.putExtra("placeName", placeName);
            setResult(RESULT_OK, intent);
            // 结束该activity 结束之后，前面的activity才可以处理结果
            finish();
            return;
        }
        Intent intent = new Intent(aty, NewAddAddress1Activity.class);
        intent.putExtra("lat", String.valueOf(lat));
        intent.putExtra("longi", String.valueOf(longi));
        intent.putExtra("district", district);
        intent.putExtra("placeName", placeName);
        intent.putExtra("type", getIntent().getIntExtra("type", 0));
        startActivityForResult(intent, REQUEST_CODE_CHOOSE_PHOTO);
    }

//    @Override
//    public void onGetInputtips(List<Tip> list, int rCode) {
//        if (rCode == 1000) {
//            //通过tipList获取Tip信息
//            // ViewInject.toast(list.get(0).getAddress());
//            tv_divider.setVisibility(View.VISIBLE);
//            tv_divider1.setVisibility(View.VISIBLE);
//           // pioAddressViewAdapter.addNewData(list);
//
//        } else {
//            tv_divider.setVisibility(View.GONE);
//            tv_divider1.setVisibility(View.GONE);
////            ViewInject.toast(rCode + "");
//            pioAddressViewAdapter.clear();
//        }
//    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSE_PHOTO && resultCode == RESULT_OK) {
            /**
             * 地址管理页面返回
             */
//            lat = data.getStringExtra("lat");
//            longi = data.getStringExtra("longi");
//            district = data.getStringExtra("district");
//            placeName = data.getStringExtra("placeName");
//            type = data.getIntExtra("type", 0);
//            detailedAddress = data.getStringExtra("detailedAddress");
//            deliveryCustomer = data.getStringExtra("deliveryCustomer");
//            shipper = data.getStringExtra("shipper");
//            phone = data.getStringExtra("phone");
//            eixedTelephone = data.getStringExtra("eixedTelephone");
            Intent intent = new Intent();
//            intent.putExtra("lat", lat);
//            intent.putExtra("longi", longi);
//            intent.putExtra("district", district);
//            intent.putExtra("placeName", placeName);
//            intent.putExtra("type", getIntent().getIntExtra("type", 0));
//            intent.putExtra("title", title);
//            intent.putExtra("detailedAddress", detailedAddress);
//            intent.putExtra("deliveryCustomer", deliveryCustomer);
//            intent.putExtra("shipper", shipper);
//            intent.putExtra("phone", phone);
//            intent.putExtra("eixedTelephone", eixedTelephone);
            setResult(RESULT_OK, intent);
//            // 结束该activity 结束之后，前面的activity才可以处理结果
            finish();
        } else if (requestCode == REQUEST_CODE_PHOTO_PREVIEW && resultCode == RESULT_OK) {
            String selectCity = data.getStringExtra("selectCity");
            int selectCityId = data.getIntExtra("selectCityId", 0);
            String selectCountry = data.getStringExtra("selectCountry");
            int selectCountryId = data.getIntExtra("selectCountryId", 0);
            tv_city.setText(selectCity);
        }

    }


    @Override
    public void onPoiSearched(PoiResult poiResult, int rCode) {
        if (rCode == AMapException.CODE_AMAP_SUCCESS && poiResult != null && poiResult.getQuery() != null && poiResult.getQuery().equals(query) && poiResult.getPois() != null && poiResult.getPois().size() > 0) {
            // 搜索poi的结果
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
}
