package com.lancoder.ttb.imagepickerviewdemo;

import android.content.Context;
import android.widget.ImageView;

import com.lancoder.ttb.imagepicker.interfaces.ImageLoaderInterface;

/**
 * Version: V1.0
 * Description:
 * Date: 2018/3/1
 * Created by wangcd
 */

public  abstract class ImageLoader implements ImageLoaderInterface<ImageView> {

    @Override
    public ImageView createImageView(Context context) {
        return new ImageView(context);
    }

}
