package com.lancoder.ttb.imagepickerviewdemo;

import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Version: V1.0
 * Description:
 * Date: 2018/3/1
 * Created by wangcd
 */

public class GlideUtils {
    public static void loadPlaceImage(Object url, ImageView imageView){
        Glide.with(imageView.getContext()).load(url)
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher).into(imageView);
    }

    public static void loadImage(Object url, ImageView imageView){
        Glide.with(imageView.getContext()).load(url).into(imageView);
    }

    public static void loadRoundImage(Object url, ImageView imageView){
        Glide.with(imageView.getContext()).load(url)
                .transform(new GlideRoundTransform(imageView.getContext()))
                .into(imageView);
    }

    public static void loadCircleImage(Object url, ImageView imageView){
        Glide.with(imageView.getContext()).load(url)
                .transform(new GlideCircleTransform(imageView.getContext()))
                .into(imageView);
    }
}
