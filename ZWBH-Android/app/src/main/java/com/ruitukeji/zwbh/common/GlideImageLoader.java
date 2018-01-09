package com.ruitukeji.zwbh.common;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.kymjs.common.StringUtils;
import com.lzy.imagepicker.loader.ImageLoader;
import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.utils.picturerelated.GlideCatchUtil;
import com.ruitukeji.zwbh.utils.picturerelated.GlideCircleTransform;
import com.ruitukeji.zwbh.utils.picturerelated.GlideRoundTransform;
import com.ruitukeji.zwbh.utils.picturerelated.RoundCornersTransformation;

import java.io.File;

/**
 * 加载图片工具类
 */
public class GlideImageLoader implements ImageLoader {

    @Override
    public void displayImage(Activity activity, String path, ImageView imageView, int width, int height) {
        //Uri.fromFile(new File(path))
        if (StringUtils.isEmpty(path)) {
            return;
        }
        if (path.startsWith("http")) {
            int index = path.indexOf("?");
            if (path.contains("?") || index != -1) {
                path = path.substring(0, index);
            }
            GlideApp.with(activity)                      //配置上下文
                    .load(path)//设置图片路径(fix #8,文件名包含%符号 无法识别和显示)
                    //    .error(R.mipmap.default_image)           //设置错误图片
                    .placeholder(R.mipmap.load)     //设置占位图片
                    .diskCacheStrategy(DiskCacheStrategy.ALL)//缓存全尺寸
                    .centerInside()
                    //  .transition(withCrossFade().crossFade())//应用在淡入淡出
                    //  .skipMemoryCache(true)//设置跳过内存缓存
                    .into(imageView);
        } else {
            GlideApp.with(activity)                      //配置上下文
                    .load(Uri.fromFile(new File(path)))//设置图片路径(fix #8,文件名包含%符号 无法识别和显示)
                    //   .error(R.mipmap.default_image)           //设置错误图片
                    .placeholder(R.mipmap.load)     //设置占位图片
                    .diskCacheStrategy(DiskCacheStrategy.ALL)//缓存全尺寸
                    .centerInside()
                    .into(imageView);
        }

    }

    @Override
    public void displayImagePreview(Activity activity, String path, ImageView imageView, int width, int height) {
        if (StringUtils.isEmpty(path)) {
            return;
        }
        if (path.startsWith("http")) {
//            int index = path.indexOf("?");
//            if (path.contains("?") || index != -1) {
//                path = path.substring(0, index);
//            }
            GlideApp.with(activity)                      //配置上下文
                    .load(path)//设置图片路径(fix #8,文件名包含%符号 无法识别和显示)
                    //    .error(R.mipmap.default_image)           //设置错误图片
                    .placeholder(R.mipmap.load)     //设置占位图片
                    .diskCacheStrategy(DiskCacheStrategy.ALL)//缓存全尺寸
                    .centerInside()
                    .into(imageView);
        } else {
            GlideApp.with(activity)                      //配置上下文
                    .load(Uri.fromFile(new File(path)))//设置图片路径(fix #8,文件名包含%符号 无法识别和显示)
                    //      .error(R.mipmap.default_image)           //设置错误图片
                    .placeholder(R.mipmap.load)     //设置占位图片
                    .diskCacheStrategy(DiskCacheStrategy.ALL)//缓存全尺寸
                    .centerInside()
                    .into(imageView);
        }

    }

    @Override
    public void clearMemoryCache() {
        // 必须在UI线程中调用
        GlideCatchUtil.getInstance().clearCacheMemory();
        // 必须在后台线程中调用，建议同时clearMemory()
        GlideCatchUtil.getInstance().clearCacheDiskSelf();
    }


    /**
     * @param context
     * @param url
     * @param imageView
     * @param tag       ==0为圆形  ==1为圆角
     */
    public static void glideLoader(Context context, Object url, ImageView imageView, int tag) {

        if (0 == tag) {
            GlideApp.with(context)
                    .load(url)
                    //    .skipMemoryCache(true)//设置跳过内存缓存
                    .placeholder(R.mipmap.loading)
                    .error(R.mipmap.headload)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .transform(new GlideCircleTransform(context))
                    .dontAnimate()//没有任何淡入淡出效果
                    //    .transition(withCrossFade().crossFade())//应用在淡入淡出
                    .into(imageView);
        } else if (1 == tag) {
            GlideApp.with(context)
                    .load(url)
                    .placeholder(R.mipmap.loading)
                    //   .error(R.mipmap.default_image)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .transform(new GlideRoundTransform(context, 10))
                    //   .skipMemoryCache(true)//设置跳过内存缓存
                    .dontAnimate()//没有任何淡入淡出效果
                    //   .transition(withCrossFade().crossFade())//应用在淡入淡出
                    .into(imageView);
        }
    }

    /**
     * 普通加载
     */
    public static void glideOrdinaryLoader(Context context, Object url, ImageView imageView) {
        GlideApp.with(context)
                .load(url)
                //  .skipMemoryCache(true)//设置跳过内存缓存
                .placeholder(R.mipmap.loading)
                //    .error(R.mipmap.default_image)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerInside()
                .dontAnimate()
//                .transition(withCrossFade())//应用在淡入淡出
                .into(imageView);
    }

    /**
     * @param context
     * @param url
     * @param imageView 圆角矩形
     */
    public static void glideRoundRectangleLoader(Context context, Object url, int radius, ImageView imageView, int default_image) {
        GlideApp.with(context)
                .load(url)
                .placeholder(default_image)
                .error(default_image)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .transform(new RoundCornersTransformation(context, radius, RoundCornersTransformation.CornerType.ALL))
                //   .skipMemoryCache(true)//设置跳过内存缓存
                .dontAnimate()//没有任何淡入淡出效果
                //   .transition(withCrossFade().crossFade())//应用在淡入淡出
                .into(imageView);
    }


    /**
     * 左上
     */
    public static void glideLeftTopLoader(Context context, Object url, int radius, ImageView imageView, int default_image) {
        GlideApp.with(context)
                .load(url)
                .placeholder(default_image)
                .error(default_image)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .transform(new RoundCornersTransformation(context, radius, RoundCornersTransformation.CornerType.LEFT_TOP))
                //   .skipMemoryCache(true)//设置跳过内存缓存
                .dontAnimate()//没有任何淡入淡出效果
                //   .transition(withCrossFade().crossFade())//应用在淡入淡出
                .into(imageView);
    }

    /**
     * 左下
     */
    public static void glideLeftBottomLoader(Context context, Object url, int radius, ImageView imageView, int default_image) {
        GlideApp.with(context)
                .load(url)
                .placeholder(default_image)
                .error(default_image)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .transform(new RoundCornersTransformation(context, radius, RoundCornersTransformation.CornerType.LEFT_BOTTOM))
                //   .skipMemoryCache(true)//设置跳过内存缓存
                .dontAnimate()//没有任何淡入淡出效果
                //   .transition(withCrossFade().crossFade())//应用在淡入淡出
                .into(imageView);
    }


    /**
     * 右上
     */
    public static void glideRightTopLoader(Context context, Object url, int radius, ImageView imageView, int default_image) {
        GlideApp.with(context)
                .load(url)
                .placeholder(default_image)
                .error(default_image)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .transform(new RoundCornersTransformation(context, radius, RoundCornersTransformation.CornerType.RIGHT_TOP))
                //   .skipMemoryCache(true)//设置跳过内存缓存
                .dontAnimate()//没有任何淡入淡出效果
                //   .transition(withCrossFade().crossFade())//应用在淡入淡出
                .into(imageView);
    }

    /**
     * 右下
     */
    public static void glideRightBottomLoader(Context context, Object url, int radius, ImageView imageView, int default_image) {
        GlideApp.with(context)
                .load(url)
                .placeholder(default_image)
                .error(default_image)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .transform(new RoundCornersTransformation(context, radius, RoundCornersTransformation.CornerType.RIGHT_BOTTOM))
                //   .skipMemoryCache(true)//设置跳过内存缓存
                .dontAnimate()//没有任何淡入淡出效果
                //   .transition(withCrossFade().crossFade())//应用在淡入淡出
                .into(imageView);
    }

    /**
     * 上侧
     */
    public static void glideTopLoader(Context context, Object url, int radius, ImageView imageView, int default_image) {
        GlideApp.with(context)
                .load(url)
                .placeholder(default_image)
                .error(default_image)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .transform(new RoundCornersTransformation(context, radius, RoundCornersTransformation.CornerType.TOP))
                //   .skipMemoryCache(true)//设置跳过内存缓存
                .dontAnimate()//没有任何淡入淡出效果
                //   .transition(withCrossFade().crossFade())//应用在淡入淡出
                .into(imageView);
    }

    /**
     * 下侧
     */
    public static void glideBottomLoader(Context context, Object url, int radius, ImageView imageView, int default_image) {
        GlideApp.with(context)
                .load(url)
                .placeholder(default_image)
                .error(default_image)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .transform(new RoundCornersTransformation(context, radius, RoundCornersTransformation.CornerType.BOTTOM))
                //   .skipMemoryCache(true)//设置跳过内存缓存
                .dontAnimate()//没有任何淡入淡出效果
                //   .transition(withCrossFade().crossFade())//应用在淡入淡出
                .into(imageView);
    }

    /**
     * 左侧
     */
    public static void glideLeftLoader(Context context, Object url, int radius, ImageView imageView, int default_image) {
        GlideApp.with(context)
                .load(url)
                .placeholder(default_image)
                .error(default_image)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .transform(new RoundCornersTransformation(context, radius, RoundCornersTransformation.CornerType.LEFT))
                //   .skipMemoryCache(true)//设置跳过内存缓存
                .dontAnimate()//没有任何淡入淡出效果
                //   .transition(withCrossFade().crossFade())//应用在淡入淡出
                .into(imageView);
    }

    /**
     * 右侧
     */
    public static void glideRightLoader(Context context, Object url, int radius, ImageView imageView, int default_image) {
        GlideApp.with(context)
                .load(url)
                .placeholder(default_image)
                .error(default_image)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .transform(new RoundCornersTransformation(context, radius, RoundCornersTransformation.CornerType.RIGHT))
                //   .skipMemoryCache(true)//设置跳过内存缓存
                .dontAnimate()//没有任何淡入淡出效果
                //   .transition(withCrossFade().crossFade())//应用在淡入淡出
                .into(imageView);
    }

}
