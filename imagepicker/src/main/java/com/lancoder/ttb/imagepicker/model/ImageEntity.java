package com.lancoder.ttb.imagepicker.model;

import android.text.TextUtils;

/**
 * Created by Broderick
 * User: hannahxian
 * Date: 2018/3/1
 * Version: 1.0
 * Description:
 * Email:wangchengda1990@gmail.com
 **/

public class ImageEntity {
    public String path;
    public String name;
    public long time;

    public ImageEntity(String path, String name, long time) {
        this.path = path;
        this.name = name;
        this.time = time;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof ImageEntity){
            ImageEntity compare = (ImageEntity)obj;
            return TextUtils.equals(this.path,compare.path);
        }
        return false;
    }
}
