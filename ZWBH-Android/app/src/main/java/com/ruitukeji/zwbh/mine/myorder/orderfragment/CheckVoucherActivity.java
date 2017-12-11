package com.ruitukeji.zwbh.mine.myorder.orderfragment;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.adapter.ImageViewAdapter;
import com.ruitukeji.zwbh.application.MyApplication;
import com.ruitukeji.zwbh.common.BaseActivity;
import com.ruitukeji.zwbh.common.BindView;
import com.ruitukeji.zwbh.common.GlideImageLoader;
import com.ruitukeji.zwbh.common.ImagePreviewNoDelActivity;
import com.ruitukeji.zwbh.entity.CheckVoucherBean;
import com.ruitukeji.zwbh.utils.ActivityTitleUtils;
import com.ruitukeji.zwbh.utils.JsonUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * 查看凭证
 * Created by Administrator on 2017/2/16.
 */

public class CheckVoucherActivity extends BaseActivity implements CheckVoucherContract.View, AdapterView.OnItemClickListener {

    @BindView(id = R.id.lv_checkvoucher)
    private ListView lv_checkvoucher;

    @BindView(id = R.id.ll_bottombar)
    private LinearLayout ll_bottombar;

    @BindView(id = R.id.tv_nextType, click = true)
    private TextView tv_nextType;

    private ArrayList<ImageItem> imageList = null;

    private ImageViewAdapter mAdapter;
    private int order_id = 0;

    /**
     * 错误提示页
     */
    @BindView(id = R.id.ll_commonError)
    private LinearLayout ll_commonError;
    @BindView(id = R.id.tv_hintText, click = true)
    private TextView tv_hintText;
    private String total_amount = "";
    private String per_status;

    @Override
    public void setRootView() {
        setContentView(R.layout.activity_checkvoucher);
    }

    @Override
    public void initData() {
        super.initData();
        initImagePicker();
        mPresenter = new CheckVoucherPresenter(this);
        mAdapter = new ImageViewAdapter(this);
        imageList = new ArrayList<ImageItem>();
        order_id = getIntent().getIntExtra("order_id", 0);
        total_amount = getIntent().getStringExtra("total_amount");
        per_status = getIntent().getStringExtra("per_status");
        showLoadingDialog(MyApplication.getContext().getString(R.string.dataLoad));
        ((CheckVoucherContract.Presenter) mPresenter).getCheckVoucher(order_id);
    }

    @Override
    public void initWidget() {
        super.initWidget();
        ActivityTitleUtils.initToolbar(aty, getString(R.string.checkVoucher), true, R.id.titlebar);
        if (per_status != null && per_status.equals("check")) {
            tv_nextType.setText(getString(R.string.paymentVoucherAudit));
        } else if (per_status != null && per_status.equals("refuse")) {
            tv_nextType.setText(getString(R.string.payAgain));
        } else {
            tv_nextType.setText(getString(R.string.confirmPayment));
        }
        lv_checkvoucher.setAdapter(mAdapter);
        lv_checkvoucher.setOnItemClickListener(this);
    }

    /**
     * 图片库设置
     */
    private void initImagePicker() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());
        imagePicker.setMultiMode(false);
        imagePicker.setShowCamera(false);
        imagePicker.setCrop(false);
        imagePicker.setSaveRectangle(false);
    }

    @Override
    public void widgetClick(View v) {
        super.widgetClick(v);
        switch (v.getId()) {
            case R.id.tv_nextType:
                if (per_status != null && per_status.equals("check")) {
                    return;
                }
                Intent intent = new Intent(aty, PaymentActivity.class);
                intent.putExtra("order_id", order_id);
                intent.putExtra("total_amount", total_amount);
                showActivity(aty, intent);
                break;
            case R.id.tv_hintText:
                showLoadingDialog(MyApplication.getContext().getString(R.string.dataLoad));
                ((CheckVoucherContract.Presenter) mPresenter).getCheckVoucher(order_id);
                break;
        }
    }

    @Override
    public void getSuccess(String s) {
        ll_commonError.setVisibility(View.GONE);
        lv_checkvoucher.setVisibility(View.VISIBLE);
        ll_bottombar.setVisibility(View.VISIBLE);
        CheckVoucherBean checkVoucherBean = (CheckVoucherBean) JsonUtil.getInstance().json2Obj(s, CheckVoucherBean.class);
        List<String> imageList1 = checkVoucherBean.getResult().getList();
        if (imageList1 == null || imageList1.size() == 0) {
            error(getString(R.string.serverReturnsDataNull));
            return;
        }
        for (int i = 0; i < imageList1.size(); i++) {
            ImageItem imageItem = new ImageItem();
            imageItem.path = imageList1.get(i);
            imageList.add(imageItem);
        }
        mAdapter.clear();
        mAdapter.addMoreData(imageList);
        dismissLoadingDialog();
    }

    @Override
    public void error(String msg) {
        lv_checkvoucher.setVisibility(View.GONE);
        ll_bottombar.setVisibility(View.GONE);
        ll_commonError.setVisibility(View.VISIBLE);
        tv_hintText.setText(msg + getString(R.string.clickRefresh));
        dismissLoadingDialog();
    }

    @Override
    public void setPresenter(CheckVoucherContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //startActivity(newIntent(this, "", mAdapter.getItem(position).detail));
        //打开预览
        Intent intentPreview = new Intent(this, ImagePreviewNoDelActivity.class);
        intentPreview.putExtra(ImagePicker.EXTRA_IMAGE_ITEMS, imageList);
        intentPreview.putExtra(ImagePicker.EXTRA_SELECTED_IMAGE_POSITION, position);
        intentPreview.putExtra(ImagePicker.EXTRA_FROM_ITEMS, true);
        showActivity(aty, intentPreview);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAdapter.clear();
        mAdapter = null;
    }
}
