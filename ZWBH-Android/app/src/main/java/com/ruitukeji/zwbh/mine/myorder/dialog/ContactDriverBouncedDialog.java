package com.ruitukeji.zwbh.mine.myorder.dialog;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.common.BaseDialog;

/**
 * 联系司机
 * Created by Administrator on 2018/2/3.
 */

public class ContactDriverBouncedDialog extends BaseDialog implements View.OnClickListener {


    private String phone = "";
    private Context context;

    public ContactDriverBouncedDialog(Context context, String phone) {
        super(context, R.style.dialog);
        this.context = context;
        this.phone = phone;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_markedasreadbounced);
        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        dialogWindow.setAttributes(lp);
        initView();
    }

    private void initView() {
        TextView tv_determine = (TextView) findViewById(R.id.tv_determine);
        tv_determine.setText(context.getString(R.string.call));
        tv_determine.setOnClickListener(this);
        TextView tv_content = (TextView) findViewById(R.id.tv_content);
        tv_content.setText(context.getString(R.string.contactDriver1));
        TextView tv_cancel = (TextView) findViewById(R.id.tv_cancel);
        tv_cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_cancel:
                cancel();
                break;
            case R.id.tv_determine:
                setPhoneUri(phone);
                cancel();
                break;
        }
    }

    private void setPhoneUri(String phone) {
        Uri uri = Uri.parse("tel:" + phone);
        Intent intent = new Intent(Intent.ACTION_DIAL, uri);
        //     系统打电话界面：
        context.startActivity(intent);
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

}
