package com.ruitukeji.zwbh.dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.common.BaseDialog;

/**
 * 信息还未保存弹框
 * Created by Administrator on 2017/11/28.
 */

public class InformationKeptBouncedDialog extends BaseDialog implements View.OnClickListener {

    private Context context;
    private TextView tv_content;
    private TextView tv_cancel;
    private TextView tv_determine;

    private InformationKeptDialogCallBack callBack;//回调

    public InformationKeptBouncedDialog(Context context) {
        super(context, R.style.dialog);
        this.context = context;
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
        tv_content = (TextView) findViewById(R.id.tv_content);
        tv_content.setText(context.getString(R.string.informationKept));
        tv_cancel = (TextView) findViewById(R.id.tv_cancel);
        tv_cancel.setOnClickListener(this);
        tv_determine = (TextView) findViewById(R.id.tv_determine);
        tv_determine.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_cancel:
                dismiss();
                break;
            case R.id.tv_determine:
                if (callBack != null) {
                    callBack.confirm();
                }
                break;
        }
    }


    public void setInformationKeptDialogCallBack(InformationKeptDialogCallBack callBack) {
        this.callBack = callBack;
    }


    public interface InformationKeptDialogCallBack {

        void confirm();

    }


}
