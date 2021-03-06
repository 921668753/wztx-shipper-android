package com.ruitukeji.zwbh.mine.drivermanagement;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.common.BaseActivity;
import com.ruitukeji.zwbh.common.BaseFragment;
import com.ruitukeji.zwbh.common.BindView;
import com.ruitukeji.zwbh.mine.drivermanagement.fragment.BlacklistFragment;
import com.ruitukeji.zwbh.mine.drivermanagement.fragment.CommonlyUsedDriverFragment;
import com.ruitukeji.zwbh.utils.ActivityTitleUtils;

/**
 * 司机管理
 * Created by Administrator on 2017/12/15.
 */

public class DriverManagementActivity extends BaseActivity {

    /**
     * 常用司机
     */
    @BindView(id = R.id.ll_deliveryAddress, click = true)
    private LinearLayout ll_deliveryAddress;
    @BindView(id = R.id.tv_deliveryAddress)
    private TextView tv_deliveryAddress;
    @BindView(id = R.id.tv_deliveryAddress1)
    private TextView tv_deliveryAddress1;

    /**
     * 黑名单
     */
    @BindView(id = R.id.ll_shippingAddress, click = true)
    private LinearLayout ll_shippingAddress;
    @BindView(id = R.id.tv_shippingAddress)
    private TextView tv_shippingAddress;
    @BindView(id = R.id.tv_shippingAddress1)
    private TextView tv_shippingAddress1;

    private BaseFragment contentFragment;
    private BaseFragment contentFragment1;
    private int chageIcon = 0;

    @Override
    public void setRootView() {
        setContentView(R.layout.activity_addressmanagement);
    }

    @Override
    public void initData() {
        super.initData();
        contentFragment = new CommonlyUsedDriverFragment();
        contentFragment1 = new BlacklistFragment();
        chageIcon = getIntent().getIntExtra("chageIcon", 0);
    }

    @Override
    public void initWidget() {
        super.initWidget();
        ActivityTitleUtils.initToolbar(aty, getString(R.string.driverManagement), true, R.id.titlebar);
        tv_deliveryAddress.setText(getText(R.string.commonlyUsedDriver));
        tv_shippingAddress.setText(getText(R.string.blacklist));
        if (chageIcon == 0) {
            cleanColors(0);
            changeFragment(contentFragment);
        } else if (chageIcon == 1) {
            cleanColors(1);
            changeFragment(contentFragment1);
        } else {
            cleanColors(0);
            changeFragment(contentFragment);
        }
    }

    public void changeFragment(BaseFragment targetFragment) {
        super.changeFragment(R.id.main_content, targetFragment);
    }

    @Override
    public void widgetClick(View v) {
        super.widgetClick(v);
        switch (v.getId()) {
            case R.id.ll_deliveryAddress:
                cleanColors(0);
                changeFragment(contentFragment);
                break;
            case R.id.ll_shippingAddress:
                if (chageIcon == 2) {
                    break;
                }
                cleanColors(1);
                changeFragment(contentFragment1);
                break;
            default:
                break;
        }
    }

    /**
     * 清除颜色，并添加颜色
     */
    public void cleanColors(int chageIcon) {
        tv_deliveryAddress.setTextColor(getResources().getColor(R.color.typecolors));
        tv_deliveryAddress1.setBackgroundResource(R.color.mainColor);
        tv_shippingAddress.setTextColor(getResources().getColor(R.color.typecolors));
        tv_shippingAddress1.setBackgroundResource(R.color.mainColor);
        if (chageIcon == 0) {
            tv_deliveryAddress.setTextColor(getResources().getColor(R.color.announcementCloseColors));
            tv_deliveryAddress1.setBackgroundResource(R.color.announcementCloseColors);
        } else if (chageIcon == 1) {
            tv_shippingAddress.setTextColor(getResources().getColor(R.color.announcementCloseColors));
            tv_shippingAddress1.setBackgroundResource(R.color.announcementCloseColors);
        } else {
            tv_deliveryAddress.setTextColor(getResources().getColor(R.color.announcementCloseColors));
            tv_deliveryAddress1.setBackgroundResource(R.color.announcementCloseColors);
        }
    }
}
