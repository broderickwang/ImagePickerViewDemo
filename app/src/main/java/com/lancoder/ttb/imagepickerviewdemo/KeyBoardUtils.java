package com.lancoder.ttb.imagepickerviewdemo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import static android.content.Context.INPUT_METHOD_SERVICE;

/**
 * Version: V1.0
 * Description:
 * Date: 2018/3/1
 * Created by wangcd
 */

public class KeyBoardUtils {
    public static void attach(final Activity activity, final KeyBoardListener keyBoardListener) {
        final ViewGroup contentView = (ViewGroup) activity.findViewById(android.R.id.content);
        final int statusBarHeight = getSystemComponentDimen(activity, "status_bar_height");

        contentView.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    public int fullDisplayHeight;
                    boolean isKeyboardVisible;

                    @Override
                    public void onGlobalLayout() {
                        ViewGroup contentView = (ViewGroup) activity.findViewById(android.R.id.content);
                        View userRootView = contentView.getChildAt(0);
                        // 计算userRootView的高度
                        Rect r = new Rect();
                        userRootView.getWindowVisibleDisplayFrame(r);
                        int displayHeight = r.bottom - r.top;

                        if (fullDisplayHeight == 0) {
                            fullDisplayHeight = displayHeight;
                            return;
                        }
                        int keyboardHeight = Math.abs(displayHeight - fullDisplayHeight);
                        if (keyboardHeight == 0) {
                            if (isKeyboardVisible) {
                                isKeyboardVisible = false;
                                if (keyBoardListener != null)
                                    keyBoardListener.keyboardShowingChanged(isKeyboardVisible);
                            }
                            return;
                        }


                        // 当前变化由，非全屏到全屏导致，此时应该更新fullDisplayHeight
                        if (keyboardHeight == statusBarHeight) {
                            fullDisplayHeight = displayHeight;
                            return;
                        }

                        if (keyBoardListener != null)
                            keyBoardListener.keyboardHeight(keyboardHeight);

                        if (!isKeyboardVisible) {
                            isKeyboardVisible = true;
                            if (keyBoardListener != null)
                                keyBoardListener.keyboardShowingChanged(isKeyboardVisible);
                        }
                    }
                });
    }

    public interface KeyBoardListener {
        void keyboardHeight(int keyboardHeight);

        void keyboardShowingChanged(boolean visible);

    }


    /**
     * 打卡软键盘
     *
     * @param mEditText 输入框
     * @param mContext  上下文
     */
    public static void openKeyboard(EditText mEditText) {
//        mEditText.setCursorVisible(true);
        Context mContext = mEditText.getContext();
        InputMethodManager imm = (InputMethodManager) mContext
                .getSystemService(INPUT_METHOD_SERVICE);
        imm.showSoftInput(mEditText, InputMethodManager.RESULT_SHOWN);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,
                InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    /**
     * 关闭软键盘
     *
     * @param mEditText 输入框
     * @param mContext  上下文
     */
    public static void closeKeyboard(EditText mEditText) {
        mEditText.setCursorVisible(false);
        Context mContext = mEditText.getContext();
        InputMethodManager imm = (InputMethodManager) mContext
                .getSystemService(INPUT_METHOD_SERVICE);

        imm.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
    }

    /**
     * des:隐藏软键盘,这种方式参数为activity
     *
     * @param activity
     */
    public static void hideInputForce(Activity activity) {
        if (activity == null || activity.getCurrentFocus() == null)
            return;

        ((InputMethodManager) activity.getSystemService(INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(activity.getCurrentFocus()
                        .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 是否显示软件盘
     *
     * @return
     */
    public boolean isSoftInputShown(Activity mContext) {
        return getSoftInputHeight(mContext) > 0;
    }

    /**
     * 获取软件盘的高度
     *
     * @return
     */
    public static int getSoftInputHeight(Activity mContext) {
        Rect r = new Rect();
        /**
         * decorView是window中的最顶层view，可以从window中通过getDecorView获取到decorView。
         * 通过decorView获取到程序显示的区域，包括标题栏，但不包括状态栏。
         */
        mContext.getWindow().getDecorView().getWindowVisibleDisplayFrame(r);
        //获取屏幕的高度
        int screenHeight = mContext.getWindow().getDecorView().getRootView().getHeight();
        //计算软件盘的高度
        int softInputHeight = screenHeight - r.bottom;//r.bottom是从屏幕顶端开始算的,因此已经包含了状态栏高度
        /**
         * 某些Android版本下，没有显示软键盘时减出来的高度总是144，而不是零，
         * 这是因为高度是包括了虚拟按键栏的(例如华为系列)，所以在API Level高于20时，
         * 我们需要减去底部虚拟按键栏的高度（如果有的话）
         */
//        if (Build.VERSION.SDK_INT >= 20) {
//            // When SDK Level >= 20 (Android L), the softInputHeight will contain the height of softButtonsBar (if has)
//            softInputHeight = softInputHeight - getNavigationBarHeight(mContext);
//        }

        if (hasNavigationBarShow(mContext.getWindowManager())) {
            softInputHeight = softInputHeight - getNavigationBarHeight(mContext);
        }
        return softInputHeight;
    }

    /**
     * 底部虚拟按键栏的高度
     *
     * @return
     */
    public static int getNavigationBarHeight(Context mContext) {
        /*Resources resources = mContext.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        //获取NavigationBar的高度
        int height = resources.getDimensionPixelSize(resourceId);*/
        return getSystemComponentDimen(mContext, "navigation_bar_height");
    }

    @SuppressLint("NewApi")
    public static boolean checkDeviceHasNavigationBar(Context activity) {
        //通过判断设备是否有返回键、菜单键(不是虚拟键,是手机屏幕外的按键)来确定是否有navigation bar
        boolean hasMenuKey = ViewConfiguration.get(activity)
                .hasPermanentMenuKey();
        boolean hasBackKey = KeyCharacterMap
                .deviceHasKey(KeyEvent.KEYCODE_BACK);
        if (!hasMenuKey && !hasBackKey) {
            // 做任何你需要做的,这个设备有一个导航栏
            return true;
        }
        return false;
    }

    public static boolean hasNavigationBarShow(WindowManager wm) {
        Display display = wm.getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        //获取整个屏幕的高度
        display.getRealMetrics(outMetrics);
        int heightPixels = outMetrics.heightPixels;
        int widthPixels = outMetrics.widthPixels;
        //获取内容展示部分的高度
        outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);
        int heightPixels2 = outMetrics.heightPixels;
        int widthPixels2 = outMetrics.widthPixels;
        int w = widthPixels - widthPixels2;
        int h = heightPixels - heightPixels2;
        System.out.println("~~~~~~~~~~~~~~~~h:" + h);
        return w > 0 || h > 0;//竖屏和横屏两种情况。
    }

    private static int getSystemComponentDimen(Context context, String dimenName) {
        // 反射手机运行的类：android.R.dimen.status_bar_height.
        int statusHeight = -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            String heightStr = clazz.getField(dimenName).get(object).toString();
            int height = Integer.parseInt(heightStr);
            //dp--->px
            statusHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }

    public static int getToolbarHeight(Context context) {
        final TypedArray styledAttributes = context.getTheme().obtainStyledAttributes(
                new int[]{R.attr.actionBarSize});
        int toolbarHeight = (int) styledAttributes.getDimension(0, 0);
        styledAttributes.recycle();

        return toolbarHeight;
    }
}
