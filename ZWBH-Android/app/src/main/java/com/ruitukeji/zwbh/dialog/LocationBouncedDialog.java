package com.ruitukeji.zwbh.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.ruitukeji.zwbh.R;

/**
 * 定位搜索
 * Created by Administrator on 2017/2/21.
 */

public class LocationBouncedDialog extends Dialog {

    private int src;
    private Context context;

    public LocationBouncedDialog(int src, Context context) {
        super(context, R.style.MyDialog);
        this.context = context;
        this.src = src;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_locationbounced);
        getWindow().setLayout(RelativeLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.FILL_PARENT);
        getWindow().setWindowAnimations(0);
        ImageView img = (ImageView) findViewById(R.id.img);
        img.setImageResource(src);
    }


    public interface DialogOnKeyDownListener {

        void onKeyDownListener(int keyCode, KeyEvent event);
    }

    private DialogOnKeyDownListener dialogOnKeyDownListener;

    public void setDialogOnKeyDownListener(DialogOnKeyDownListener dialogOnKeyDownListener) {
        this.dialogOnKeyDownListener = dialogOnKeyDownListener;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (dialogOnKeyDownListener != null) {
            dialogOnKeyDownListener.onKeyDownListener(keyCode, event);
        }
        return super.onKeyDown(keyCode, event);
    }

}
