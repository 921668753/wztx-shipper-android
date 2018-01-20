package com.ruitukeji.zwbh.mine.personaldata;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kymjs.common.PreferenceHelper;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.adapter.ImageViewAdapter;
import com.ruitukeji.zwbh.application.MyApplication;
import com.ruitukeji.zwbh.common.BaseActivity;
import com.ruitukeji.zwbh.common.BindView;
import com.ruitukeji.zwbh.common.GlideImageLoader;
import com.ruitukeji.zwbh.common.ImagePreviewNoDelActivity;
import com.ruitukeji.zwbh.constant.NumericConstants;
import com.ruitukeji.zwbh.constant.StringConstants;
import com.ruitukeji.zwbh.entity.mine.shippercertification.CompanyOwnerBean;
import com.ruitukeji.zwbh.entity.mine.shippercertification.IndividualOwnersBean;
import com.ruitukeji.zwbh.utils.ActivityTitleUtils;
import com.ruitukeji.zwbh.utils.JsonUtil;
import com.ruitukeji.zwbh.utils.myview.ChildListView;

import java.util.ArrayList;

/**
 * 认证信息
 * Created by Administrator on 2017/2/14.
 */

public class AuthenticationInformationActivity extends BaseActivity implements AuthenticationInformationContract.View, AdapterView.OnItemClickListener {

    @BindView(id = R.id.ll_personal)
    private LinearLayout ll_personal;

    @BindView(id = R.id.tv_realName)
    private TextView tv_realName;

    @BindView(id = R.id.tv_bindingMobilePhone)
    private TextView tv_bindingMobilePhone;

    @BindView(id = R.id.tv_idNumber)
    private TextView tv_idNumber;

    @BindView(id = R.id.tv_sex)
    private TextView tv_sex;

    @BindView(id = R.id.ll_enterprise)
    private LinearLayout ll_enterprise;

    @BindView(id = R.id.tv_companyName)
    private TextView tv_companyName;

    @BindView(id = R.id.tv_registrationNumber)
    private TextView tv_registrationNumber;

    @BindView(id = R.id.tv_legalPersonName)
    private TextView tv_legalPersonName;

    @BindView(id = R.id.tv_legalPersonIdNumber)
    private TextView tv_legalPersonIdNumber;

    @BindView(id = R.id.tv_phoneNumber)
    private TextView tv_phoneNumber;

    @BindView(id = R.id.tv_address)
    private TextView tv_address;

    @BindView(id = R.id.tv_operationIdNumber)
    private TextView tv_operationIdNumber;


    @BindView(id = R.id.lv_img)
    private ChildListView lv_img;

    /**
     * 是否是个人
     */
    private boolean isPersonal = true;
    private ImageViewAdapter imageViewAdapter;

    private ImagePicker imagePicker;
    ArrayList<ImageItem> images = null;


    @Override
    public void setRootView() {
        setContentView(R.layout.activity_authenticationinformation);
    }

    @Override
    public void initData() {
        super.initData();
        mPresenter = new AuthenticationInformationPresenter(this);
        imageViewAdapter = new ImageViewAdapter(this);
        images = new ArrayList<ImageItem>();
        initImagePicker();
    }

    @Override
    public void initWidget() {
        super.initWidget();
        ActivityTitleUtils.initToolbar(aty, getString(R.string.authenticationInformation), true, R.id.titlebar);
        String type = PreferenceHelper.readString(MyApplication.getContext(), StringConstants.FILENAME, "type");
        if (type.equals("person")) {
            ll_enterprise.setVisibility(View.GONE);
            ll_personal.setFocusable(true);
            ll_personal.setFocusableInTouchMode(true);
            ll_personal.requestFocus();
            ll_personal.requestFocusFromTouch();
            ((AuthenticationInformationContract.Presenter) mPresenter).getPersonalCertificate();
        } else {
            ll_personal.setVisibility(View.GONE);
            ll_enterprise.setFocusable(true);
            ll_enterprise.setFocusableInTouchMode(true);
            ll_enterprise.requestFocus();
            ll_enterprise.requestFocusFromTouch();
            ((AuthenticationInformationContract.Presenter) mPresenter).getCompanyAuthInfo();
        }
        lv_img.setAdapter(imageViewAdapter);
        lv_img.setOnItemClickListener(this);
    }

    //打开预览
    @SuppressWarnings("unchecked")
    private void toImagePreviewDelActivity(View view, ArrayList<ImageItem> list) {
        Intent intentPreview = new Intent(this, ImagePreviewNoDelActivity.class);
        intentPreview.putExtra(ImagePicker.EXTRA_IMAGE_ITEMS, list);
        intentPreview.putExtra(ImagePicker.EXTRA_SELECTED_IMAGE_POSITION, 0);
        intentPreview.putExtra(ImagePicker.EXTRA_FROM_ITEMS, true);
        startActivityForResult(intentPreview, NumericConstants.REQUEST_CODE_PREVIEW);
    }

    @Override
    public void getSuccess(String s, int flag) {
        if (flag == 0) {
            IndividualOwnersBean individualOwnersBean = (IndividualOwnersBean) JsonUtil.getInstance().json2Obj(s, IndividualOwnersBean.class);
            tv_realName.setText(individualOwnersBean.getResult().getReal_name());
            tv_bindingMobilePhone.setText(individualOwnersBean.getResult().getPhone());
            tv_idNumber.setText(individualOwnersBean.getResult().getIdentity());
            if (individualOwnersBean.getResult().getSex() == 1) {
                tv_sex.setText(getString(R.string.man));
            } else if (individualOwnersBean.getResult().getSex() == 2) {
                tv_sex.setText(getString(R.string.woman));
            } else {
                tv_sex.setText(getString(R.string.unknown));
            }
            images.clear();
            imageViewAdapter.clear();
            ImageItem imageItem = new ImageItem();
            imageItem.path = individualOwnersBean.getResult().getFront_pic();
            images.add(imageItem);
            ImageItem imageItem1 = new ImageItem();
            imageItem1.path = individualOwnersBean.getResult().getBack_pic();
            images.add(imageItem1);
            imageViewAdapter.addMoreData(images);
        } else if (flag == 1) {
            CompanyOwnerBean companyOwnerBean = (CompanyOwnerBean) JsonUtil.getInstance().json2Obj(s, CompanyOwnerBean.class);
            tv_companyName.setText(companyOwnerBean.getResult().getCom_name());
            tv_registrationNumber.setText(companyOwnerBean.getResult().getCom_buss_num());
            tv_legalPersonName.setText(companyOwnerBean.getResult().getLaw_person());
            tv_legalPersonIdNumber.setText(companyOwnerBean.getResult().getLaw_identity());
            tv_phoneNumber.setText(companyOwnerBean.getResult().getCom_phone());
            tv_address.setText(companyOwnerBean.getResult().getAddress());
            tv_operationIdNumber.setText(companyOwnerBean.getResult().getIdentity());
            images.clear();
            imageViewAdapter.clear();
            ImageItem imageItem = new ImageItem();
            imageItem.path = companyOwnerBean.getResult().getLaw_front_pic();
            images.add(imageItem);
            ImageItem imageItem1 = new ImageItem();
            imageItem1.path = companyOwnerBean.getResult().getLaw_back_pic();
            images.add(imageItem1);
            ImageItem imageItem2 = new ImageItem();
            imageItem2.path = companyOwnerBean.getResult().getBuss_pic();
            images.add(imageItem2);
            ImageItem imageItem3 = new ImageItem();
            imageItem3.path = companyOwnerBean.getResult().getFront_pic();
            images.add(imageItem3);
            ImageItem imageItem4 = new ImageItem();
            imageItem4.path = companyOwnerBean.getResult().getBack_pic();
            images.add(imageItem4);
            imageViewAdapter.addMoreData(images);
        }
        dismissLoadingDialog();
    }

    @Override
    public void error(String msg) {
        toLigon(msg);
        dismissLoadingDialog();
        //  ViewInject.toast(msg);
    }

    @Override
    public void setPresenter(AuthenticationInformationContract.Presenter presenter) {
        mPresenter = presenter;
    }

    /**
     * 图片库设置
     */
    private void initImagePicker() {
        imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());
        imagePicker.setMultiMode(false);
        imagePicker.setShowCamera(true);
        imagePicker.setCrop(false);
        imagePicker.setSaveRectangle(false);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ImageView img_imgView = (ImageView) parent.findViewById(R.id.img_imgView);
        ImageItem listBean = imageViewAdapter.getItem(position);
        images.clear();
        images.add(listBean);
        //打开预览
        toImagePreviewDelActivity(img_imgView, images);
    }
}
