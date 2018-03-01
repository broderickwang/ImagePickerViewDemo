package com.lancoder.ttb.imagepicker.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.widget.LinearLayout;


import com.lancoder.ttb.imagepicker.R;
import com.lancoder.ttb.imagepicker.adapter.ImageShowPickerAdapter;
import com.lancoder.ttb.imagepicker.interfaces.ImageLoaderInterface;
import com.lancoder.ttb.imagepicker.layoutmanger.MyGridLayoutManager;
import com.lancoder.ttb.imagepicker.listner.ImageShowPickerListener;
import com.lancoder.ttb.imagepicker.model.ImageShowPickerBean;
import com.lancoder.ttb.imagepicker.utils.DeviceUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Version: V1.0
 * Description:
 * Date: 2018/2/24
 * Created by wangcd
 */

public class ImagePickerView extends LinearLayout {

    private RecyclerView mRecyclerView;

    /**
     * 图片加载接口
     */
    private ImageLoaderInterface mImageLoaderInterface;

    private ImageShowPickerListener mImageShowPickerListener;

    private Context mContext;

    private ImageShowPickerAdapter mImageShowPickerAdapter;

    private List<ImageShowPickerBean> mImageShowPickerBeans;

    /**
     * 默认单个大小
     */
    private static final int PIC_SIZE = 80;

    /**
     * 默认单行显示数量
     */
    private static final int ONE_LINE_SHOW_NUM = 4;
    /**
     * 默认单个大小
     */
    private static final int MAX_NUM = 9;
    /**
     * 单个item大小
     */
    private int mPicSize;
    /**
     * 添加图片
     */
    private int mAddLabel;
    /**
     * 删除图片
     */
    private int mDelLabel;
    /**
     * 是否显示删除
     */
    private boolean isShowDel;
    /**
     * 单行显示数量，默认4
     */
    private int oneLineShowNum;
    /**
     * 是否展示动画，默认false
     */
    private boolean isShowAnim;

    /**
     * 最大数量
     */
    private int maxNum;

    /**
     * 设置单个item大小
     *
     * @param mPicSize
     */
    public void setmPicSize(int mPicSize) {
        this.mPicSize = mPicSize;
    }

    /**
     * 设置增加图片
     *
     * @param mAddLabel
     */
    public void setmAddLabel(int mAddLabel) {
        this.mAddLabel = mAddLabel;
    }

    /**
     * 设置删除图片
     *
     * @param mDelLabel
     */
    public void setmDelLabel(int mDelLabel) {
        this.mDelLabel = mDelLabel;
    }

    /**
     * 设置是否显示删除
     *
     * @param showDel
     */
    public void setShowDel(boolean showDel) {
        isShowDel = showDel;
        /*if (adapter == null) {
            throw new NullPointerException("adapter is null");
        }
        adapter.setShowDel(showDel);
        adapter.notifyDataSetChanged();*/
    }

    /**
     * 设置单行显示数量
     *
     * @param oneLineShowNum
     */
    public void setOneLineShowNum(int oneLineShowNum) {
        this.oneLineShowNum = oneLineShowNum;
    }

    /**
     * 设置是否显示动画
     *
     * @param showAnim
     */
    public void setShowAnim(boolean showAnim) {
        isShowAnim = showAnim;
    }

    /**
     * 设置最大允许图片数量
     *
     * @param maxNum
     */
    public void setMaxNum(int maxNum) {
        this.maxNum = maxNum;
    }

    public ImagePickerView(Context context) {
        this(context,null);
    }

    public ImagePickerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ImagePickerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init(getContext(), attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        mImageShowPickerBeans = new ArrayList<>();
        viewTypedArray(context, attrs);
        mRecyclerView = new RecyclerView(context);
        addView(mRecyclerView);
    }
    private void viewTypedArray(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ImageShowPickerView);
        mPicSize = typedArray.getDimensionPixelSize(R.styleable.ImageShowPickerView_pic_size, DeviceUtils.dip2px(mContext,PIC_SIZE));
        isShowDel = typedArray.getBoolean(R.styleable.ImageShowPickerView_is_show_del, false);
        isShowAnim = typedArray.getBoolean(R.styleable.ImageShowPickerView_is_show_anim, false);
        mAddLabel = typedArray.getResourceId(R.styleable.ImageShowPickerView_add_label, R.mipmap.image_show_piceker_add);
        mDelLabel = typedArray.getResourceId(R.styleable.ImageShowPickerView_del_label, R.mipmap.image_show_piceker_del);
        oneLineShowNum = typedArray.getInt(R.styleable.ImageShowPickerView_one_line_show_num, ONE_LINE_SHOW_NUM);
        maxNum = typedArray.getInt(R.styleable.ImageShowPickerView_max_num, MAX_NUM);
        typedArray.recycle();
    }

    /**
     * 最后调用方法显示，必须最后调用
     */
    public void show() {
        MyGridLayoutManager layoutManager = new MyGridLayoutManager(mContext, oneLineShowNum);
        layoutManager.setAutoMeasureEnabled(true);
        mRecyclerView.setLayoutManager(layoutManager);

//        ViewGroup.LayoutParams layoutParams = recyclerView.getLayoutParams();
//        //计算行数
//        int lineNumber = list.size()%oneLineShowNum==0? list.size()/oneLineShowNum:(list.size()/oneLineShowNum) +1;
//        //计算高度=行数＊每行的高度 ＋(行数－1)＊10dp的margin ＋ 10dp（为了居中）
//        //高度的计算需要自己好好理解，否则会产生嵌套recyclerView可以滑动的现象
////        layoutParams.height =SizeUtils.getSizeUtils().dp2px(getContext(), lineNumber *mPicSize) ;
//        layoutParams.height =lineNumber *mPicSize ;
//
//        Log.e("lineNumber",""+lineNumber);
//        Log.e("height",""+layoutParams.height);
//
//        recyclerView.setLayoutParams(layoutParams);

        mImageShowPickerAdapter = new ImageShowPickerAdapter(maxNum, mContext, mImageShowPickerBeans, mImageLoaderInterface, mImageShowPickerListener);
        mImageShowPickerAdapter.setAddPicRes(mAddLabel);
        mImageShowPickerAdapter.setDelPicRes(mDelLabel);
        mImageShowPickerAdapter.setIconHeight(mPicSize);
        mImageShowPickerAdapter.setShowDel(isShowDel);
        mImageShowPickerAdapter.setShowAnim(isShowAnim);
        mRecyclerView.setAdapter(mImageShowPickerAdapter);
        mImageShowPickerAdapter.notifyDataSetChanged();
    }

    /**
     * 设置选择器监听
     *
     * @param pickerListener 选择器监听事件
     */
    public void setPickerListener(ImageShowPickerListener pickerListener) {
        this.mImageShowPickerListener = pickerListener;
    }

    /**
     * 图片加载器
     *
     * @param imageLoaderInterface
     */
    public void setImageLoaderInterface(ImageLoaderInterface imageLoaderInterface) {
        this.mImageLoaderInterface = imageLoaderInterface;

    }

    /**
     * 添加新数据
     *
     * @param bean
     * @param <T>
     */
    public <T extends ImageShowPickerBean> void addData(T bean) {
        if (bean == null) {
            return;
        }
        this.mImageShowPickerBeans.add(bean);
        if (isShowAnim) {
            if (mImageShowPickerAdapter != null) {
                mImageShowPickerAdapter.notifyItemChanged(mImageShowPickerBeans.size() - 1);
                mImageShowPickerAdapter.notifyItemChanged(mImageShowPickerBeans.size());
            }
        } else {
            mImageShowPickerAdapter.notifyDataSetChanged();
        }

    }

    /**
     * 添加新数据
     *
     * @param list
     * @param <T>
     */
    public <T extends ImageShowPickerBean> void addData(List<T> list) {
        if (list == null) {
            return;
        }
        this.mImageShowPickerBeans.addAll(list);

        if (isShowAnim) {
            if (mImageShowPickerAdapter != null)
                mImageShowPickerAdapter.notifyItemRangeChanged(this.mImageShowPickerBeans.size() - list.size(), list.size());

        } else {
            if (mImageShowPickerAdapter != null)
                mImageShowPickerAdapter.notifyDataSetChanged();
        }

    }

    /**
     * 首次添加数据
     *
     * @param list
     * @param <T>
     */
    public <T extends ImageShowPickerBean> void setNewData(List<T> list) {
        this.mImageShowPickerBeans = new ArrayList<>();
        this.mImageShowPickerBeans.addAll(list);

        if (isShowAnim) {
            if (mImageShowPickerAdapter != null)
                mImageShowPickerAdapter.notifyItemRangeChanged(this.mImageShowPickerBeans.size() - list.size(), list.size());

        } else {
            if (mImageShowPickerAdapter != null)
                mImageShowPickerAdapter.notifyDataSetChanged();
        }

    }

    /**
     * 获取picker的list数据集合
     *
     * @param <T>
     * @return
     */
    public <T extends ImageShowPickerBean> List<T> getDataList() {
        return (List<T>) mImageShowPickerBeans;
    }

    /**
     * 清除选择的图片
     */
    public void clearImage() {
        if (mImageShowPickerBeans != null && mImageShowPickerAdapter != null) {
            mImageShowPickerBeans.clear();
            mImageShowPickerAdapter.notifyDataSetChanged();
        }
    }
}
