package com.ruitukeji.zwbh.mine.addressmanagement.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.kymjs.common.StringUtils;
import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.adapter.mine.addressmanagement.AddressViewAdapter;
import com.ruitukeji.zwbh.common.BaseFragment;
import com.ruitukeji.zwbh.common.BindView;
import com.ruitukeji.zwbh.common.ViewInject;
import com.ruitukeji.zwbh.constant.NumericConstants;
import com.ruitukeji.zwbh.entity.mine.addressmanagement.AddressBean;
import com.ruitukeji.zwbh.mine.addressmanagement.AddressManagementActivity;
import com.ruitukeji.zwbh.mine.addressmanagement.newaddaddress.NewAddAddress1Activity;
import com.ruitukeji.zwbh.mine.addressmanagement.newaddaddress.NewAddAddressActivity;
import com.ruitukeji.zwbh.utils.JsonUtil;
import com.ruitukeji.zwbh.utils.RefreshLayoutUtil;

import cn.bingoogolapple.baseadapter.BGAOnItemChildClickListener;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

import static android.app.Activity.RESULT_OK;
import static com.ruitukeji.zwbh.constant.NumericConstants.REQUEST_CODE_PHOTO_PREVIEW;

/**
 * 收货地址
 * Created by Administrator on 2017/12/15.
 */

public class ShippingAddressFragment extends BaseFragment implements AddressContract.View, AdapterView.OnItemClickListener, BGARefreshLayout.BGARefreshLayoutDelegate, BGAOnItemChildClickListener {


    private AddressManagementActivity aty;

    @BindView(id = R.id.mRefreshLayout)
    private BGARefreshLayout mRefreshLayout;

    private AddressViewAdapter mAdapter;

    @BindView(id = R.id.lv_address)
    private ListView lv_address;

    /**
     * 错误提示页
     */
    @BindView(id = R.id.ll_commonError)
    private LinearLayout ll_commonError;
    @BindView(id = R.id.tv_hintText, click = true)
    private TextView tv_hintText;

    /**
     * 新增地址
     */
    @BindView(id = R.id.tv_newAddress, click = true)
    private TextView tv_newAddress;


    /**
     * 当前页码
     */
    private int mMorePageNumber = NumericConstants.START_PAGE_NUMBER;

    /**
     * 总页码
     */
    private int totalPageNumber = NumericConstants.START_PAGE_NUMBER;

    /**
     * 是否加载更多
     */
    private boolean isShowLoadingMore = false;

    /**
     * 0 始发地 1目的地
     */
    private String type = "1";

    @Override
    protected View inflaterView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        aty = (AddressManagementActivity) getActivity();
        return View.inflate(aty, R.layout.fragment_address, null);
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter = new AddressPresenter(this);
        mAdapter = new AddressViewAdapter(getActivity());
    }

    @Override
    protected void initWidget(View parentView) {
        super.initWidget(parentView);
        RefreshLayoutUtil.initRefreshLayout(mRefreshLayout, this, getActivity(), true);
        lv_address.setAdapter(mAdapter);
        lv_address.setOnItemClickListener(this);
        mAdapter.setOnItemChildClickListener(this);
        mRefreshLayout.beginRefreshing();
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if (StringUtils.isEmpty(getActivity().getIntent().getStringExtra("cargoReceipt"))) {
            return;
        }
        AddressBean.ResultBean.ListBean listBean = mAdapter.getItem(i);
        Intent intent = new Intent();
        intent.putExtra("lat", listBean.getAddress_maps().split(",")[0]);
        intent.putExtra("longi", listBean.getAddress_maps().split(",")[1]);
        intent.putExtra("district", listBean.getCity());
        intent.putExtra("placeName", listBean.getAddress_name());
        intent.putExtra("detailedAddress", listBean.getAddress_detail());
        intent.putExtra("deliveryCustomer", listBean.getClient());
        intent.putExtra("shipper", listBean.getClient_name());
        intent.putExtra("phone", listBean.getPhone());
        intent.putExtra("eixedTelephone", listBean.getTelphone());
        aty.setResult(RESULT_OK, intent);
        aty.finish();
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        mMorePageNumber = NumericConstants.START_PAGE_NUMBER;
        mRefreshLayout.endRefreshing();
        showLoadingDialog(getString(R.string.dataLoad));
        ((AddressContract.Presenter) mPresenter).getAddress(mMorePageNumber, type);
    }


    /**
     * 控件监听事件
     */
    @Override
    public void widgetClick(View v) {
        super.widgetClick(v);
        switch (v.getId()) {
            case R.id.tv_hintText:
                mRefreshLayout.beginRefreshing();
                break;
            case R.id.tv_newAddress:
                Intent intent = new Intent(aty, NewAddAddressActivity.class);
                //  intent.putExtra("title", getString(R.string.newAddress));
                intent.putExtra("type", 2);
                //  intent.putExtra("hintText", getString(R.string.pleaseEnterDeliveryLocation));
                startActivityForResult(intent, REQUEST_CODE_PHOTO_PREVIEW);
                break;
        }
    }


    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        mRefreshLayout.endLoadingMore();
        if (!isShowLoadingMore) {
            return false;
        }
        mMorePageNumber++;
        if (mMorePageNumber > totalPageNumber) {
            ViewInject.toast(getString(R.string.noMoreData));
            return false;
        }
        showLoadingDialog(getString(R.string.dataLoad));
        ((AddressContract.Presenter) mPresenter).getAddress(mMorePageNumber, type);
        return true;
    }


    @Override
    public void setPresenter(AddressContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void getSuccess(String success, int flag) {
        if (flag == 0) {
            isShowLoadingMore = true;
            ll_commonError.setVisibility(View.GONE);
            mRefreshLayout.setVisibility(View.VISIBLE);
            AddressBean orderBean = (AddressBean) JsonUtil.getInstance().json2Obj(success, AddressBean.class);
            mMorePageNumber = orderBean.getResult().getPage();
            totalPageNumber = orderBean.getResult().getPageTotal();
            if (orderBean.getResult().getList() == null || orderBean.getResult().getList().size() == 0) {
                errorMsg(getString(R.string.serverReturnsDataNull), 0);
                return;
            }
            if (mMorePageNumber == NumericConstants.START_PAGE_NUMBER) {
                mRefreshLayout.endRefreshing();
                mAdapter.clear();
                mAdapter.addNewData(orderBean.getResult().getList());
            } else if (mMorePageNumber > NumericConstants.START_PAGE_NUMBER) {
                mRefreshLayout.endLoadingMore();
                mAdapter.addMoreData(orderBean.getResult().getList());
            }
            dismissLoadingDialog();
        } else if (flag == 1 || flag == 2) {
//            dismissLoadingDialog();
//        } else if () {
            mRefreshLayout.beginRefreshing();
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void errorMsg(String msg, int flag) {
        toLigon(msg);
        isShowLoadingMore = false;
        mRefreshLayout.setVisibility(View.GONE);
        ll_commonError.setVisibility(View.VISIBLE);
        tv_hintText.setText(msg + getString(R.string.clickRefresh));
        if (mMorePageNumber == NumericConstants.START_PAGE_NUMBER) {
            mRefreshLayout.endRefreshing();
        } else {
            mRefreshLayout.endLoadingMore();
        }
        dismissLoadingDialog();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mAdapter.clear();
        mAdapter = null;
    }

    @Override
    public void onItemChildClick(ViewGroup viewGroup, View view, int i) {
        if (view.getId() == R.id.img_setDefaultAddress) {
            ((AddressContract.Presenter) mPresenter).postSetDefaultAddress(mAdapter.getItem(i).getId());
        } else if (view.getId() == R.id.ll_edit) {
            Intent intent = new Intent(aty, NewAddAddress1Activity.class);
            intent.putExtra("address_id", mAdapter.getItem(i).getId());
            intent.putExtra("type", 3);
            startActivityForResult(intent, REQUEST_CODE_PHOTO_PREVIEW);
        } else if (view.getId() == R.id.ll_delete) {
            ((AddressContract.Presenter) mPresenter).postDeleteAddress(mAdapter.getItem(i).getId());
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_PHOTO_PREVIEW && resultCode == RESULT_OK) {
            mRefreshLayout.beginRefreshing();
        }
    }
}
