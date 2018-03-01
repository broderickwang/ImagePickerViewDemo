package com.lancoder.ttb.imagepicker.permission;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Broderick
 * User: Broderick
 * Date: 2017/5/11
 * Time: 13:05
 * Version: 1.0
 * Description:权限注解
 * Email:wangchengda1990@gmail.com
 **/

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PermissionSuccess {
	public int requestCode();//请求码
}
