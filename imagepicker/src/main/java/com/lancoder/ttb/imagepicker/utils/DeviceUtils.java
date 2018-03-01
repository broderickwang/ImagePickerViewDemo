package com.lancoder.ttb.imagepicker.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

/**
 * Version: V1.0
 * Description:
 * Date: 2018/2/24
 * Created by wangcd
 */

public class DeviceUtils {
    private static Context mContext;

    public DeviceUtils() {
    }

    public static void setContext(Context context) {
        mContext = context;
    }

    public static Context getContext() {
        return mContext;
    }

    public static int dip2px(Context context,float dipValue) {
        DisplayMetrics dm = getDisPlayMetrics(context);
        return (int)(dipValue * dm.density + 0.5F);
    }

    public static DisplayMetrics getDisPlayMetrics(Context context) {
        Display display = getDisplay(context);
        DisplayMetrics dm = new DisplayMetrics();
        display.getMetrics(dm);
        return dm;
    }

    public static Display getDisplay(Context context) {
        @SuppressLint("WrongConstant")
        Display display = ((WindowManager)context.getSystemService("window")).getDefaultDisplay();
        return display;
    }
}
