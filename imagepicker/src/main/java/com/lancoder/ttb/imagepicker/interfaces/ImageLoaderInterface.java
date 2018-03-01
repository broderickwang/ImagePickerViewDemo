package com.lancoder.ttb.imagepicker.interfaces;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.view.View;

import java.io.Serializable;

/**
 * Version: V1.0
 * Description:
 * Date: 2018/2/24
 * Created by wangcd
 */

public interface ImageLoaderInterface <T extends View> extends Serializable {

    void displayImage(Context context, String path, T imageView);

    void displayImage(Context context, @DrawableRes Integer resId, T imageView);

    T createImageView(Context context);
}