package com.ruitukeji.zwbh.mine.mywallet.accountdetails;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.common.BaseActivity;
import com.ruitukeji.zwbh.common.BaseFragment;
import com.ruitukeji.zwbh.common.BindView;
import com.ruitukeji.zwbh.common.ViewInject;
import com.ruitukeji.zwbh.entity.mine.mywallet.accountdetails.ClassificationBouncedBean;
import com.ruitukeji.zwbh.mine.mywallet.accountdetails.dialog.ClassificationBouncedDialog;
import com.ruitukeji.zwbh.mine.mywallet.accountdetails.fragment.AccountDetailsContract;
import com.ruitukeji.zwbh.mine.mywallet.accountdetails.fragment.AccountDetailsPresenter;
import com.ruitukeji.zwbh.mine.mywallet.accountdetails.fragment.PaidFragment;
import com.ruitukeji.zwbh.mine.mywallet.accountdetails.fragment.UnpaidFragment;
import com.ruitukeji.zwbh.utils.JsonUtil;
import com.ruitukeji.zwbh.utils.rx.MsgEvent;
import com.ruitukeji.zwbh.utils.rx.RxBus;

import java.util.List;

/**
 * 账户明细
 * Created by Administrator on 2017/12/15.
 */

public class AccountDetailsActivity extends BaseActivity implements ClassificationBouncedDialog.ClassificationDialogCallBack, AccountDetailsContract.View {

    /**
     * 标题栏
     */
    @BindView(id = R.id.img_back, click = true)
    private ImageView img_back;

    /**
     * 分类
     */
    @BindView(id = R.id.ll_classification, click = true)
    private LinearLayout ll_classification;
    @BindView(id = R.id.tv_classification)
    private TextView tv_classification;
    @BindView(id = R.id.img_down)
    private ImageView img_down;


    /**
     * 已支付
     */
    @BindView(id = R.id.ll_paid, click = true)
    private LinearLayout ll_paid;
    @BindView(id = R.id.tv_paid)
    private TextView tv_paid;
    @BindView(id = R.id.tv_paid1)
    private TextView tv_paid1;

    /**
     * 未支付
     */
    @BindView(id = R.id.ll_unpaid, click = true)
    private LinearLayout ll_unpaid;
    @BindView(id = R.id.tv_unpaid)
    private TextView tv_unpaid;
    @BindView(id = R.id.tv_unpaid1)
    private TextView tv_unpaid1;

    private BaseFragment contentFragment;
    private BaseFragment contentFragment1;
    private int chageIcon = 0;
    private ClassificationBouncedDialog classificationBouncedDialog = null;

    private List<ClassificationBouncedBean.ResultBean> list;
    public int paidId = 0;

    @Override
    public void setRootView() {
        setContentView(R.layout.activity_accountdetails);
    }

    @Override
    public void initData() {
        super.initData();
        mPresenter = new AccountDetailsPresenter(this);
        contentFragment = new PaidFragment();
        contentFragment1 = new UnpaidFragment();
        chageIcon = getIntent().getIntExtra("chageIcon", 0);
        //  chooseClassification();
        //  showLoadingDialog(getString(R.string.dataLoad));
        ((AccountDetailsContract.Presenter) mPresenter).getTimeName();
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
    }

    @Override
    public void widgetClick(View v) {
        super.widgetClick(v);
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.ll_classification:
                //  img_down.setBackgroundResource(R.mipmap.icon_);
                if (classificationBouncedDialog == null && list != null && list.size() > 0) {
                    chooseClassification(list);
                } else if (classificationBouncedDialog != null && list != null && list.size() > 0) {
                    classificationBouncedDialog.show();
                }
                break;
            case R.id.ll_paid:
                chageIcon = 0;
                cleanColors(0);
                changeFragment(contentFragment);
                break;
            case R.id.ll_unpaid:
                chageIcon = 1;
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
        tv_paid.setTextColor(getResources().getColor(R.color.typecolors));
        tv_paid1.setBackgroundResource(R.color.mainColor);
        tv_unpaid.setTextColor(getResources().getColor(R.color.typecolors));
        tv_unpaid1.setBackgroundResource(R.color.mainColor);
        if (chageIcon == 0) {
            tv_paid.setTextColor(getResources().getColor(R.color.announcementCloseColors));
            tv_paid1.setBackgroundResource(R.color.announcementCloseColors);
        } else if (chageIcon == 1) {
            tv_unpaid.setTextColor(getResources().getColor(R.color.announcementCloseColors));
            tv_unpaid1.setBackgroundResource(R.color.announcementCloseColors);
        } else {
            tv_paid.setTextColor(getResources().getColor(R.color.announcementCloseColors));
            tv_paid1.setBackgroundResource(R.color.announcementCloseColors);
        }
    }

    /**
     * 选择分类
     */
    private void chooseClassification(List<ClassificationBouncedBean.ResultBean> list) {
        classificationBouncedDialog = new ClassificationBouncedDialog(this, list);
        classificationBouncedDialog.setCanceledOnTouchOutside(true);
        classificationBouncedDialog.setClassificationDialogCallBack(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        classificationBouncedDialog = null;
    }

    @Override
    public void confirm(int id, String name) {
        tv_classification.setText(name);
        paidId = id;
        if (chageIcon == 0) {
            RxBus.getInstance().post(new MsgEvent<String>("RxBusPaidFragmentEvent"));
            return;
        }
        RxBus.getInstance().post(new MsgEvent<String>("RxBusUnpaidFragmentEvent"));
    }

    @Override
    public void setPresenter(AccountDetailsContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void getSuccess(String success, int flag) {
        ClassificationBouncedBean classificationBouncedBean = (ClassificationBouncedBean) JsonUtil.json2Obj(success, ClassificationBouncedBean.class);
        if (classificationBouncedBean.getResult() != null && classificationBouncedBean.getResult().size() > 0) {
            list = classificationBouncedBean.getResult();
        }
//        classificationBouncedDialog = new ClassificationBouncedDialog(this, success);
//        classificationBouncedDialog.setClassificationDialogCallBack(this);
        //  dismissLoadingDialog();
    }

    @Override
    public void errorMsg(String msg, int flag) {
//        dismissLoadingDialog();
//        ViewInject.toast(msg);
    }
}