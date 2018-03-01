package com.lancoder.ttb.imagepickerviewdemo;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.widget.ImageView;

import com.lancoder.ttb.imagepicker.interfaces.ImageLoaderInterface;

/**
 * Version: V1.0
 * Description:
 * Date: 2018/3/1
 * Created by wangcd
 */

class ImageShowLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, String path, ImageView imageView) {
        GlideUtils.loadImage(path, imageView);
    }

    @Override
    public void displayImage(Context context, @DrawableRes Integer resId, ImageView imageView) {
        GlideUtils.loadImage(resId, imageView);
    }
}
