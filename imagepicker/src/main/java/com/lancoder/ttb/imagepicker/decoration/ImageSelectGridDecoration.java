package com.lancoder.ttb.imagepicker.decoration;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Broderick
 * User: Broderick
 * Date: 2017/5/9
 * Time: 15:01
 * Version: 1.0
 * Description:
 * Email:wangchengda1990@gmail.com
 **/
public class ImageSelectGridDecoration extends RecyclerView.ItemDecoration {

	private int spanCount;
	private int spacing;
	private boolean includeEdge;

	public ImageSelectGridDecoration(int spanCount, int spacing, boolean includeEdge) {
		this.spanCount = spanCount;
		this.spacing = spacing;
		this.includeEdge = includeEdge;
	}

	@Override
	public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
		int position = parent.getChildAdapterPosition(view); // item position
		int column = position % spanCount; // item column

		if (includeEdge) {
			outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
			outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

			if (position < spanCount) { // top edge
				outRect.top = spacing;
			}
			outRect.bottom = spacing; // item bottom
		} else {
			outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
			outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
			if (position >= spanCount) {
				outRect.top = spacing; // item top
			}
		}
	}
}
