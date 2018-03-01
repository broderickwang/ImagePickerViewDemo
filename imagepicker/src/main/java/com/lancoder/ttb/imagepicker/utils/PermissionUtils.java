package com.lancoder.ttb.imagepicker.utils;

import android.app.Activity;
import android.app.Fragment;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.content.ContextCompat;

import com.lancoder.ttb.imagepicker.permission.PermissionFailed;
import com.lancoder.ttb.imagepicker.permission.PermissionSuccess;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Broderick
 * User: hannahxian
 * Date: 2018/3/1
 * Version: 1.0
 * Description:
 * Email:wangchengda1990@gmail.com
 **/

public class PermissionUtils {
    private PermissionUtils(){
        throw new UnsupportedOperationException("cann't be instantiated!");
    }

    /**
     * 判断是否在6.0版本以上
     * @return
     */
    public static boolean isOverMarshmallow(){
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    /**
     * 执行成功方法
     * @param object 反射类
     * @param requestCode 请求码
     */
    public static void executeSuccessMethod(Object object , int requestCode) {
        //获取class中的所有方法
        Method[] methods = object.getClass().getDeclaredMethods();
        //找到打了标记的方法，并且请求码一样
        for (Method method : methods) {
            //获取该方法上面有没有注解
            PermissionSuccess success = method.getAnnotation(PermissionSuccess.class);
            if (success != null){
                //找到注解方法,判断请求码
                if(success.requestCode() == requestCode){
                    //反射执行方法
                    executeMethod(object,method);
                }
            }
        }

    }

    /**
     * 反射执行方法
     * @param object
     * @param method
     */
    private static void executeMethod(Object object,Method method) {
        //第一个 该方法在哪个类 第二个 参数
        try {
            // 允许执行私有方法
            method.setAccessible(true);
            method.invoke(object,new Object[]{});
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取未授权的权限
     * @param object  Activity / Fragement
     * @param permissions
     * @return
     */
    public static List<String> getDeniedPermissions(Object object, String[] permissions) {
        List<String> denieds = new ArrayList<>();
        for (String permission : permissions) {
            if(ContextCompat.checkSelfPermission(getActivity(object),permission) == PackageManager.PERMISSION_DENIED){
                denieds.add(permission);
            }
        }
        return denieds;
    }

    /**
     * 获取上下文
     * @param object
     * @return
     */
    public static Activity getActivity(Object object) {
        if(object instanceof Activity)
            return (Activity)object;
        return ((Fragment)object).getActivity();
    }

    /**
     * 执行失败的方法 （授权失败）
     * @param object
     * @param requestCode
     */
    public static void executeFailedMethod(Object object, int requestCode) {
        Method[] methods = object.getClass().getDeclaredMethods();
        for (Method method : methods) {
            PermissionFailed failed = method.getAnnotation(PermissionFailed.class);
            if(failed != null){
                if(failed.requestCode() == requestCode){
                    executeMethod(object,method);
                }
            }
        }
    }
}
