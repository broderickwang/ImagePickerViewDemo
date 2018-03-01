package com.lancoder.ttb.imagepicker.listner;


import com.lancoder.ttb.imagepicker.model.ImageShowPickerBean;

import java.util.List;

/**
 * Version: V1.0
 * Description:
 * Date: 2018/2/24
 * Created by wangcd
 */

public interface ImageShowPickerListener {

    void addOnClickListner(int remainNum);

    void picOnClickListner(List<ImageShowPickerBean> list, int position, int remainNum);

    boolean picOnLongClickListner(List<ImageShowPickerBean> list, int position, int remainNum);

    void delOnClickListner(int position, int remainNum);
}
