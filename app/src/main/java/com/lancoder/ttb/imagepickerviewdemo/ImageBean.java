package com.lancoder.ttb.imagepickerviewdemo;

import com.lancoder.ttb.imagepicker.model.ImageShowPickerBean;

/**
 * Version: V1.0
 * Description:
 * Date: 2018/3/1
 * Created by wangcd
 */

public class ImageBean extends ImageShowPickerBean {
    private String url;
    private int id;

    public ImageBean(String url){
        this.url = url;
    }

    public ImageBean(int id){
        this.id = id;
    }

    @Override
    public String setImageShowPickerUrl() {
        return url;
    }

    @Override
    public int setImageShowPickerDelRes() {
        return id;
    }
}
