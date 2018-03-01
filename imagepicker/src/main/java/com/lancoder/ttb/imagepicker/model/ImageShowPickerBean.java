package com.lancoder.ttb.imagepicker.model;

/**
 * Version: V1.0
 * Description:
 * Date: 2018/2/24
 * Created by wangcd
 */

public abstract class ImageShowPickerBean {
    public String getImageShowPickerUrl() {
        return setImageShowPickerUrl();
    }

    public int getImageShowPickerDelRes() {
        return setImageShowPickerDelRes();
    }

    /**
     * 为URL赋值，必须重写方法
     *
     * @return
     */
    public abstract String setImageShowPickerUrl();

    /**
     * 为删除label赋值，必须重写方法
     *
     * @return
     */
    public abstract int setImageShowPickerDelRes();
}
