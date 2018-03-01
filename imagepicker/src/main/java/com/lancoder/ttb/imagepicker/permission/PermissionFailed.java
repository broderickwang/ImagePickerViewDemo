package com.lancoder.ttb.imagepicker.permission;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Broderick
 * User: Broderick
 * Date: 2017/5/11
 * Time: 14:36
 * Version: 1.0
 * Description:
 * Email:wangchengda1990@gmail.com
 **/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PermissionFailed {
	public int requestCode();
}
