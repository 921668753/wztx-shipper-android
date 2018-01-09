package com.ruitukeji.zwbh.mine.shippercertification;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kymjs.common.PreferenceHelper;
import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.common.BaseActivity;
import com.ruitukeji.zwbh.common.BaseFragment;
import com.ruitukeji.zwbh.common.BindView;
import com.ruitukeji.zwbh.constant.StringConstants;
import com.ruitukeji.zwbh.mine.shippercertification.certificationfragment.CompanyOwnerFragment;
import com.ruitukeji.zwbh.mine.shippercertification.certificationfragment.IndividualOwnersFragment;

/**
 * 货主认证
 * Created by Administrator on 2017/2/10.
 */

public class ShipperCertificationActivity extends BaseActivity {


    @BindView(id = R.id.img_back, click = true)
    private ImageView img_back;


    /**
     * 个人货主
     */
    @BindView(id = R.id.ll_individualOwners, click = true)
    private LinearLayout ll_individualOwners;
    @BindView(id = R.id.tv_individualOwners)
    private TextView tv_individualOwners;
    @BindView(id = R.id.tv_individualOwners1)
    private TextView tv_individualOwners1;

    /**
     * 公司货主
     */
    @BindView(id = R.id.ll_companyOwner, click = true)
    private LinearLayout ll_companyOwner;
    @BindView(id = R.id.tv_companyOwner)
    private TextView tv_companyOwner;
    @BindView(id = R.id.tv_companyOwner1)
    private TextView tv_companyOwner1;


    private BaseFragment contentFragment;
    private BaseFragment contentFragment1;

    /**
     * 用来表示移动的Fragment
     */
    private int chageIcon;
    private String type = "all";


    @Override
    public void setRootView() {
        setContentView(R.layout.activity_shippercertification);
    }

    @Override
    public void initData() {
        super.initData();
        contentFragment = new IndividualOwnersFragment();
        contentFragment1 = new CompanyOwnerFragment();
        type = getIntent().getStringExtra("type");
        if (type.equals("person")) {
            chageIcon = 0;
        } else if (type.equals("company")) {
            chageIcon = 1;
        }
    }

    @Override
    public void initWidget() {
        super.initWidget();
        if (chageIcon == 0) {
            chageIcon = 0;
            cleanColors(0);
            changeFragment(contentFragment);
        } else if (chageIcon == 1) {
            chageIcon = 1;
            cleanColors(1);
            changeFragment(contentFragment1);
        } else {
            chageIcon = 0;
            cleanColors(0);
            changeFragment(contentFragment);
        }
    }

    public void changeFragment(BaseFragment targetFragment) {
        super.changeFragment(R.id.main_content, targetFragment);
        //  transaction.commit();
    }

    @Override
    public void widgetClick(View v) {
        super.widgetClick(v);
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.ll_individualOwners:
                String auth_status = PreferenceHelper.readString(this, StringConstants.FILENAME, "auth_status", "init");
                if (auth_status != null && auth_status.equals("init") && !type.equals("company")) {
                    chageIcon = 0;
                    cleanColors(0);
                    changeFragment(contentFragment);
                }
                break;
            case R.id.ll_companyOwner:
                String auth_status1 = PreferenceHelper.readString(this, StringConstants.FILENAME, "auth_status", "init");
                if (auth_status1 != null && auth_status1.equals("init") && !type.equals("person")) {
                    chageIcon = 1;
                    cleanColors(1);
                    changeFragment(contentFragment1);
                }
                break;
            default:
                break;
        }
    }

    /**
     * 清除颜色，并添加颜色
     */
    public void cleanColors(int chageIcon) {
        tv_individualOwners.setTextColor(getResources().getColor(R.color.hintcolors));
        tv_individualOwners.getPaint().setFakeBoldText(false);
        tv_individualOwners1.setBackgroundResource(R.color.mainColor);
        tv_companyOwner.setTextColor(getResources().getColor(R.color.hintcolors));
        tv_companyOwner.getPaint().setFakeBoldText(false);
        tv_companyOwner1.setBackgroundResource(R.color.mainColor);
        if (chageIcon == 0) {
            tv_individualOwners.setTextColor(getResources().getColor(R.color.announcementCloseColors));
            tv_individualOwners.getPaint().setFakeBoldText(true);
            tv_individualOwners1.setBackgroundResource(R.color.announcementCloseColors);
        } else if (chageIcon == 1) {
            tv_companyOwner.setTextColor(getResources().getColor(R.color.announcementCloseColors));
            tv_companyOwner.getPaint().setFakeBoldText(true);
            tv_companyOwner1.setBackgroundResource(R.color.announcementCloseColors);
        } else {
            tv_individualOwners.setTextColor(getResources().getColor(R.color.announcementCloseColors));
            tv_individualOwners.getPaint().setFakeBoldText(true);
            tv_individualOwners1.setBackgroundResource(R.color.announcementCloseColors);
        }
    }
}
