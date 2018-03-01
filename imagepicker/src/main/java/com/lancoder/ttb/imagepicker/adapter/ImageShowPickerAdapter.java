package com.lancoder.ttb.imagepicker.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;


import com.lancoder.ttb.imagepicker.R;
import com.lancoder.ttb.imagepicker.interfaces.ImageLoaderInterface;
import com.lancoder.ttb.imagepicker.listner.ImageShowPickerListener;
import com.lancoder.ttb.imagepicker.listner.ImageShowPickerPicListener;
import com.lancoder.ttb.imagepicker.model.ImageShowPickerBean;
import com.lancoder.ttb.imagepicker.utils.DeviceUtils;

import java.util.List;

/**
 * Version: V1.0
 * Description:
 * Date: 2018/2/24
 * Created by wangcd
 */

public class ImageShowPickerAdapter extends RecyclerView.Adapter<ImageShowPickerAdapter.ViewHolder>
        implements ImageShowPickerPicListener {

    private int mMaxNum;
    private Context context;
    private List<ImageShowPickerBean> list;
    public ImageLoaderInterface imageLoaderInterface;
    private ImageShowPickerListener pickerListener;
    private static int MARGIN = 0;

    private static int iconHeight;

    private int delPicRes;

    private int addPicRes;

    private boolean isShowAnim;

    private boolean isShowDel;

    public void setShowDel(boolean showDel) {
        isShowDel = showDel;
    }

    public void setShowAnim(boolean showAnim) {
        isShowAnim = showAnim;
    }

    public void setIconHeight(int iconHeight) {
        this.iconHeight = iconHeight;
    }

    public void setDelPicRes(int delPicRes) {
        this.delPicRes = delPicRes;
    }

    public void setAddPicRes(int addPicRes) {
        this.addPicRes = addPicRes;
    }

    public ImageShowPickerAdapter(int mMaxNum, Context context, List<ImageShowPickerBean> list
            , ImageLoaderInterface imageLoaderInterface, ImageShowPickerListener pickerListener) {
        this.mMaxNum = mMaxNum;
        this.context = context;
        this.list = list;
        this.imageLoaderInterface = imageLoaderInterface;
        this.pickerListener = pickerListener;
        MARGIN = DeviceUtils.dip2px(context,5);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        FrameLayout frameLayout = new FrameLayout(context);
        parent.addView(frameLayout);
        frameLayout.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT
                , FrameLayout.LayoutParams.WRAP_CONTENT));
        ViewHolder vh = new ViewHolder(frameLayout, imageLoaderInterface, this);

        frameLayout.addView(vh.iv_pic);
        frameLayout.addView(vh.iv_del);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (list.size() == 0 || list.size() == position) {
            imageLoaderInterface.displayImage(context, addPicRes, holder.iv_pic);
            holder.iv_del.setVisibility(View.GONE);
        } else {
            if (null == list.get(position).getImageShowPickerUrl() || "".equals(list.get(position).getImageShowPickerUrl())) {
                imageLoaderInterface.displayImage(context, list.get(position).getImageShowPickerDelRes(), holder.iv_pic);
            } else {
                imageLoaderInterface.displayImage(context, list.get(position).getImageShowPickerUrl(), holder.iv_pic);
            }
            if (isShowDel) {
                holder.iv_del.setVisibility(View.VISIBLE);
                holder.iv_del.setImageResource(delPicRes);
            } else {
                holder.iv_del.setVisibility(View.GONE);
            }
        }

    }

    @Override
    public int getItemCount() {

        return list.size() < mMaxNum ? list.size() + 1 : list.size();

    }

    @Override
    public void onDelClickListener(int position) {
        list.remove(position);
        if (isShowAnim) {
            notifyItemRemoved(position);
            if (list.size() - 1 >= 0 && list.get(list.size() - 1) == null) {
                notifyItemChanged(list.size() - 1);
            } else if (list.size() - 1 == 0) {
                notifyItemChanged(0);
            }
        } else {
            notifyDataSetChanged();
        }
        pickerListener.delOnClickListner(position,mMaxNum-list.size());
    }

    @Override
    public void onPicClickListener(int position) {
        if (position == list.size()) {
            if (pickerListener != null) {
                pickerListener.addOnClickListner(mMaxNum - position - 1);
            }
        } else {
            if (pickerListener != null) {
                pickerListener.picOnClickListner(list, position, mMaxNum > list.size() ? mMaxNum - list.size() - 1 :
                        (list.get(mMaxNum - 1) == null ? 1 : 0));
            }
        }
    }

    @Override
    public boolean onPicLongClick(int position) {
        if (position == list.size()) {
            if (pickerListener != null) {
                pickerListener.addOnClickListner(mMaxNum - position - 1);
            }
        } else {
            if (pickerListener != null) {
                isShowDel = !isShowDel;
                notifyDataSetChanged();
                return pickerListener.picOnLongClickListner(list, position, mMaxNum > list.size() ? mMaxNum - list.size() - 1 :
                        (list.get(mMaxNum - 1) == null ? 1 : 0));
            }
        }
        return false;
    }


    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        //        public View iv_pic;
        public ImageView iv_pic;
        public ImageView iv_del;
        private ImageShowPickerPicListener picOnClickListener;


        public ViewHolder(View view, ImageLoaderInterface imageLoaderInterface, ImageShowPickerPicListener picOnClickListener) {
            super(view);
            this.picOnClickListener = picOnClickListener;
            iv_pic = (ImageView) imageLoaderInterface.createImageView(view.getContext());
            FrameLayout.LayoutParams pic_params = new FrameLayout.LayoutParams(iconHeight,
                    iconHeight);
            pic_params.setMargins(MARGIN, MARGIN, MARGIN, MARGIN);
            iv_pic.setScaleType(ImageView.ScaleType.FIT_XY);
            iv_pic.setLayoutParams(pic_params);
            iv_del = new ImageView(view.getContext());
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT
                    , FrameLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.gravity = Gravity.TOP | Gravity.END;
            iv_del.setPadding(5, 5, 5, 5);
            iv_del.setLayoutParams(layoutParams);
            iv_pic.setId(R.id.iv_image_show_picker_pic);
            iv_del.setId(R.id.iv_image_show_picker_del);
            iv_pic.setOnClickListener(this);
            iv_pic.setOnLongClickListener(this);
            iv_del.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int i = v.getId();
            if (i == R.id.iv_image_show_picker_pic) {
                picOnClickListener.onPicClickListener(getLayoutPosition());
            } else if (i == R.id.iv_image_show_picker_del) {
                picOnClickListener.onDelClickListener(getLayoutPosition());
            }
        }

        @Override
        public boolean onLongClick(View view) {

            return picOnClickListener.onPicLongClick(getLayoutPosition());
        }
    }
}
