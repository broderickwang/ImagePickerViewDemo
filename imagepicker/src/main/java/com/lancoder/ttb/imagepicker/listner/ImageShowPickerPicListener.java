package com.lancoder.ttb.imagepicker.listner;

/**
 * Version: V1.0
 * Description:
 * Date: 2018/2/24
 * Created by wangcd
 */

public interface ImageShowPickerPicListener {

    void onDelClickListener(int position);

    void onPicClickListener(int position);

    boolean onPicLongClick(int position);
}
