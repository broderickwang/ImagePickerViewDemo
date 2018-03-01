package com.lancoder.ttb.imagepicker.permission;

import android.app.Activity;
import android.app.Fragment;
import android.support.v4.app.ActivityCompat;

import com.lancoder.ttb.imagepicker.utils.PermissionUtils;

import java.util.List;

/**
 * Created by Broderick
 * User: Broderick
 * Date: 2017/5/11
 * Time: 10:49
 * Version: 1.0
 * Description:运行时权限
 * Email:wangchengda1990@gmail.com
 **/
public class PermissionHelper {
	private Object mObject;

	private int mRequestCode;

	private String[] mRequestPermission;

	private PermissionHelper(Object mObject) {
		this.mObject = mObject;
	}

	public static void  requestPermission(Activity activity, int requestCode, String[] permissions){
		PermissionHelper.with(activity).requestCode(requestCode).
				requestPermission(permissions).request();
	}

	public static void  requestPermission(Fragment fragment, int requestCode, String[] permissions){
		PermissionHelper.with(fragment).requestCode(requestCode).
				requestPermission(permissions).request();
	}

	//传Activity
	public static PermissionHelper with(Activity activity){
		return new PermissionHelper(activity);
	}

	//传Fragement
	public static PermissionHelper with(Fragment fragment){
		return  new PermissionHelper(fragment);
	}

	//添加一个请求吗
	public PermissionHelper requestCode(int requstCode){
		this.mRequestCode = requstCode;
		return this;
	}
	//添加请求权限数组
	public PermissionHelper requestPermission(String... permissions){
		this.mRequestPermission = permissions;
		return this;
	}

	/**
	 * 发起权限请求
	 */
	public void request(){
		//判断当前的版本是不是在6.0以上
		if(PermissionUtils.isOverMarshmallow()){
			//如果是6.0 以上，需要首先判断权限是否授予，
			//需要申请的权限中有没有 未授权的权限
			List<String> deniedPermissions = PermissionUtils.getDeniedPermissions(mObject,mRequestPermission);
			if(deniedPermissions.size() == 0) {
				//如果授予了 执行方法
				PermissionUtils.executeSuccessMethod(mObject,mRequestCode);
			}else {
				//如果没有授予，进行权限申请
				ActivityCompat.requestPermissions(PermissionUtils.getActivity(mObject),
						deniedPermissions.toArray(new String[deniedPermissions.size()]),mRequestCode);
			}
		}else{
			//如果不是6.0以上，那么直接执行方法  反射获取执行方法
			//执行什么方法不确定，只能采用注解的方法给方法打一个标记,然后通过反射执行
			PermissionUtils.executeSuccessMethod(mObject,mRequestCode);
			return;
		}

	}

	/**
	 * 处理申请权限的回调
	 * @param object
	 * @param requestCode
	 * @param permissions
	 */
	public static void requestPermissionResult(Object object, int requestCode, String[] permissions) {
		//再次获取没有授予的权限
		List<String> deniedPermissions = PermissionUtils.getDeniedPermissions(object,permissions);
		if(deniedPermissions.size() == 0){
			//权限用户已经全部授予
			PermissionUtils.executeSuccessMethod(object,requestCode);
		}else{
			//仍然有部分权限未被授予
			PermissionUtils.executeFailedMethod(object,requestCode);
		}
	}
}
