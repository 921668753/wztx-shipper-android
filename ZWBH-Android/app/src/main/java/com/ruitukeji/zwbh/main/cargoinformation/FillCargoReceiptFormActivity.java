package com.ruitukeji.zwbh.main.cargoinformation;

import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.common.BaseActivity;
import com.ruitukeji.zwbh.common.BindView;
import com.ruitukeji.zwbh.common.ViewInject;
import com.ruitukeji.zwbh.utils.ActivityTitleUtils;

import cn.bingoogolapple.titlebar.BGATitleBar.SimpleDelegate;

/**
 * 填写货物签收单
 * Created by Administrator on 2017/12/12.
 */

public class FillCargoReceiptFormActivity extends BaseActivity implements FillCargoReceiptFormContract.View {

    /**
     * 联系人
     */
    @BindView(id = R.id.et_contactPerson)
    private EditText et_contactPerson;

    /**
     * 联系方式
     */
    @BindView(id = R.id.et_contactInformation)
    private EditText et_contactInformation;

    /**
     * 所在地区
     */
    @BindView(id = R.id.ll_inArea, click = true)
    private LinearLayout ll_inArea;
    @BindView(id = R.id.tv_inArea)
    private TextView tv_inArea;

    /**
     * 请输入详细的地址信息
     */
    @BindView(id = R.id.et_detailedAddressInformation)
    private EditText et_detailedAddressInformation;

    /**
     * 快递费到付
     */
    @BindView(id = R.id.img_off, click = true)
    private ImageView img_off;

    /**
     * 确定
     */
    @BindView(id = R.id.tv_determine, click = true)
    private ImageView tv_determine;

    private int cargoReceipt = 0;
    private int expressDelivery = 0;

    @Override
    public void setRootView() {
        setContentView(R.layout.activity_fillcargoreceiptform);
    }

    @Override
    public void initData() {
        super.initData();
        cargoReceipt = getIntent().getIntExtra("cargoReceipt", 0);
        mPresenter = new FillCargoReceiptFormPresenter(this);
    }

    @Override
    public void initWidget() {
        super.initWidget();
        SimpleDelegate simpleDelegate = new SimpleDelegate() {
            @Override
            public void onClickLeftCtv() {
                super.onClickLeftCtv();
                backParameter();
            }

            @Override
            public void onClickRightCtv() {
                super.onClickRightCtv();

            }
        };
        ActivityTitleUtils.initToolbar(aty, getString(R.string.fillCargoReceipt), "", R.id.titlebar, simpleDelegate);
    }

    @Override
    public void widgetClick(View v) {
        super.widgetClick(v);
        switch (v.getId()) {
            case R.id.ll_inArea:


                break;
            case R.id.img_off:
                if (expressDelivery == 0) {
                    img_off.setImageResource(R.mipmap.switch_btn_on);
                    expressDelivery = 1;
                    break;
                }
                expressDelivery = 0;
                img_off.setImageResource(R.mipmap.switch_btn_off);
                break;
            case R.id.tv_determine:
                ((FillCargoReceiptFormContract.Presenter) mPresenter).postFillCargoReceiptForm(et_contactPerson.getText().toString().trim(),
                        et_contactInformation.getText().toString().trim(), tv_inArea.getText().toString().trim(), et_detailedAddressInformation.getText().toString().trim(), expressDelivery);
                break;
        }
    }

    @Override
    public void setPresenter(FillCargoReceiptFormContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void getSuccess(String success, int flag) {
        backParameter();
    }

    @Override
    public void errorMsg(String msg, int flag) {
        ViewInject.toast(msg);
    }

    /**
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            backParameter();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 返回参数
     */
    private void backParameter() {
        Intent intent = new Intent();
        // 获取内容
        intent.putExtra("contactPerson", et_contactPerson.getText().toString().trim());
        intent.putExtra("contactInformation", et_contactInformation.getText().toString().trim());
        intent.putExtra("inArea", tv_inArea.getText().toString().trim());
        intent.putExtra("detailedAddressInformation", et_detailedAddressInformation.getText().toString().trim());
        intent.putExtra("expressDelivery", expressDelivery);
        intent.putExtra("cargoReceipt", cargoReceipt);
        // 设置结果 结果码，一个数据
        setResult(RESULT_OK, intent);
        // 结束该activity 结束之后，前面的activity才可以处理结果
        aty.finish();
    }

}
