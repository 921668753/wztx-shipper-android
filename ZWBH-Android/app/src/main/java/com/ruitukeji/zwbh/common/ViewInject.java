package com.ruitukeji.zwbh.common;


import android.content.Context;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;


/**
 * Created by Administrator on 2017/2/13.
 */

public class ViewInject {
    private ViewInject() {
    }

    private static class ClassHolder {
        private static final ViewInject instance = new ViewInject();
    }

    /**
     * 类对象创建方法
     *
     * @return 本类的对象
     */
    public static ViewInject create() {
        return ClassHolder.instance;
    }

    /**
     * 显示一个toast
     *
     * @param msg
     */
    public static void toast(String msg) {
        try {
            toast(KJActivityStack.create().topActivity(), msg);
        } catch (Exception e) {
        }
    }

    /**
     * 长时间显示一个toast
     *
     * @param msg
     */
    public static void longToast(String msg) {
        try {
            longToast(KJActivityStack.create().topActivity(), msg);
        } catch (Exception e) {
        }
    }

    /**
     * 自定义显示Toast时间
     *
     * @param duration
     */
    public static void toast(String msg, int duration) {
        try {
            toast(KJActivityStack.create().topActivity(), msg, duration);
        } catch (Exception e) {
        }
    }


    /**
     * 自定义图片Toat
     */
    public static void toast(String msg, int duration, int img) {
        try {
            showImg(KJActivityStack.create().topActivity(), msg, duration, img);
        } catch (Exception e) {
        }
    }


    /**
     * 长时间显示一个toast
     *
     * @param msg
     */
    public static void longToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }

    /**
     * 显示一个toast
     *
     * @param msg
     */
    public static void toast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 自定义显示Toast时间
     *
     * @param context
     * @param message
     * @param duration
     */
    public static void toast(Context context, String message, int duration) {

        Toast.makeText(context, message, duration).show();
    }


    /**
     * 自定义图片Toat
     */
    public static void showImg(Context context, String message, int duration, int img) {
        Toast toast = Toast.makeText(context, message, duration);
        toast.setGravity(Gravity.CENTER, 0, 0);
        LinearLayout toastView = (LinearLayout) toast.getView();
        ImageView imageCodeProject = new ImageView(context);
        imageCodeProject.setImageResource(img);
        toastView.addView(imageCodeProject, 0);
        toast.show();
    }


}
