package com.lancoder.ttb.imagepicker.adapter;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lancoder.ttb.imagepicker.R;
import com.lancoder.ttb.imagepicker.listner.SelectImageListner;
import com.lancoder.ttb.imagepicker.model.ImageEntity;
import com.lancoder.ttb.imagepicker.permission.PermissionHelper;
import com.lancoder.ttb.imagepicker.ui.SelectImageActivity;

import java.util.ArrayList;
import java.util.List;

import marc.com.multrecycleadapter.CommonRecycleAdapter;
import marc.com.multrecycleadapter.MultiTypeSupport;
import marc.com.multrecycleadapter.ViewHolder;

/**
 * Created by Broderick
 * User: hannahxian
 * Date: 2018/3/1
 * Version: 1.0
 * Description:
 * Email:wangchengda1990@gmail.com
 **/

public class SelectImageListAdapter  extends CommonRecycleAdapter<ImageEntity> {

    private ArrayList<String> mResultImageList;

    private int mMax;

    private Context mContext;

    public SelectImageListAdapter(Context mContext, List mDatas) {
        super(mContext, mDatas, R.layout.select_image_adapter_layout);
    }

    public SelectImageListAdapter(Context context, List<ImageEntity> data,
                                  MultiTypeSupport<ImageEntity> multiTypeSupport,
                                  ArrayList<String> imageList,int max) {
        super(context, data, multiTypeSupport);
        this.mResultImageList = imageList;
        this.mMax = max;
        this.mContext = context;
    }

    @Override
    public void convert(final ViewHolder holder, final ImageEntity item) {
        if(item==null){
            //显示拍照
            holder.setOnItemClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PermissionHelper.with((Activity) mContext)
                            .requestPermission(Manifest.permission.CAMERA)
                            .requestCode(SelectImageActivity.REQUEST_CAMERA_CODE)
                            .request();

                }
            });
        }else{
            //显示图片
            ImageView imageView = holder.getView(R.id.adapter_img);

            CheckBox checkBox = holder.getView(R.id.img_checkBox);

            Glide.with(mContext).load(item.path)
                    .centerCrop().into(imageView);

            if(mResultImageList.contains(item.path)){
                checkBox.setChecked(true);
            }else{
                checkBox.setChecked(false);
            }

            holder.setOnItemClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(mResultImageList.contains(item.path)){
                        mResultImageList.remove(item.path);
                    }else{
                        if(mResultImageList.size()<mMax){
                            mResultImageList.add(item.path);
                        }else{
                            Toast.makeText(mContext, "最多选择"+mMax+"张图片", Toast.LENGTH_SHORT).show();
                        }
                    }

                    notifyDataSetChanged();

                    //通知显示布局的改变
                    if(mListner!=null)
                        mListner.select();
                }
            });

        }
    }

    @Override
    public int getLayoutId(Object item, int position) {
        return 0;
    }

    private SelectImageListner mListner;

    public void setListner(SelectImageListner mListner) {
        this.mListner = mListner;
    }
}
