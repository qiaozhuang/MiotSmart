/*
 * 
 */

package com.box.common.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;

// TODO: Auto-generated Javadoc
/**
 * The Class ViewTransformUtil.
 * 
 * @Title: view适配操作类，所有的高宽需要传设计图的。
 * @Package com.box.llgj.util
 * @Description: TODO
 * @author yu_bo925@163.com
 * @date 2013-10-25 上午10:29:04
 * @version V1.0
 */
public class ViewTransformUtil {

	

	/**
	 * 计算适配的宽度.
	 * 
	 * @param context
	 *            the context
	 * @param width
	 *            the width 设计图宽
	 * @return int
	 * @Title: layoutWidth
	 * @Description: TODO
	 */
	public static int layoutWidth(Context context, int width) {
		// 是否切换了不同分辨率
		if (ScreenUtil.isDifferentOrientation(context)) {
			ScreenUtil.swichDifferentOrientation(context);
		}
		if (width == ScreenUtil.originalImageWidth) {
			return ScreenUtil.screenWidth;
		}
		width = (int) Math.ceil(ScreenUtil.screenWidthPercentage * width);
		return width > 0 ? width : 1;
	}

	/**
	 * 计算适配的高度.
	 * 
	 * @param context
	 *            the context
	 * @param height
	 *            the height 设计图高
	 * @return int
	 * @Title: layoutHeigt
	 * @Description: TODO
	 */
	public static int layoutHeigt(Context context, int height) {
		// 是否切换了不同分辨率
		if (ScreenUtil.isDifferentOrientation(context)) {
			ScreenUtil.swichDifferentOrientation(context);
		}
		if (height == ScreenUtil.originalImageHeight) {
			return ScreenUtil.screenHeight;
		}
		height = (int) Math.ceil(ScreenUtil.screenHeightPercentage * height);
		return height > 0 ? height : 1;
	}

	/**
	 * 适配布局，注意:高宽如果一致会被当成正方形对待。.
	 * 
	 * @param view
	 *            the view
	 * @param width
	 *            the width 设计图宽
	 * @param height
	 *            the height 设计图高
	 */
	public static void layoutParams(View view, int width, int height) {
		Context context = view.getContext();
		// 是否切换了不同分辨率
		if (ScreenUtil.isDifferentOrientation(context)) {
			ScreenUtil.swichDifferentOrientation(context);
		}
		android.view.ViewGroup.LayoutParams layoutParams = view
				.getLayoutParams();
		if (width == height && width > 0) {
			int length = (int) Math.ceil(ScreenUtil.screenWidthPercentage
					* width);
			layoutParams.width = length;
			layoutParams.height = length;
		} else {
			if (width == android.view.ViewGroup.LayoutParams.MATCH_PARENT
					|| width == android.view.ViewGroup.LayoutParams.WRAP_CONTENT) {
				layoutParams.width = width;
			} else if (width > 0) {
				layoutParams.width = (int) Math
						.ceil(ScreenUtil.screenWidthPercentage * width);
			}
			if (height == android.view.ViewGroup.LayoutParams.MATCH_PARENT
					|| height == android.view.ViewGroup.LayoutParams.WRAP_CONTENT) {
				layoutParams.height = height;
			} else if (height > 0) {
				layoutParams.height = (int) Math
						.ceil(ScreenUtil.screenHeightPercentage * height);
			}
		}
		view.setLayoutParams(layoutParams);
	}

	/**
	 * 适配布局，注意:高宽如果一致会被当成正方形对待。.
	 * 
	 * @param view
	 *            the view
	 * @param layoutParams
	 *            the layout params
	 * @param width
	 *            the width 设计图宽
	 * @param height
	 *            the height 设计图高
	 */
	public static void layoutParams(View view,
			android.view.ViewGroup.LayoutParams layoutParams, int width,
			int height) {
		Context context = view.getContext();
		// 是否切换了不同分辨率
		if (ScreenUtil.isDifferentOrientation(context)) {
			ScreenUtil.swichDifferentOrientation(context);
		}
		if (width == height && width > 0) {
			int length = (int) Math.ceil(ScreenUtil.screenWidthPercentage
					* width);
			layoutParams.width = length;
			layoutParams.height = length;
		} else {
			if (width == android.view.ViewGroup.LayoutParams.MATCH_PARENT
					|| width == android.view.ViewGroup.LayoutParams.WRAP_CONTENT) {
				layoutParams.width = width;
			} else if (width > 0) {
				layoutParams.width = (int) Math
						.ceil(ScreenUtil.screenWidthPercentage * width);
			}
			if (height == android.view.ViewGroup.LayoutParams.MATCH_PARENT
					|| height == android.view.ViewGroup.LayoutParams.WRAP_CONTENT) {
				layoutParams.height = height;
			} else if (height > 0) {
				layoutParams.height = (int) Math
						.ceil(ScreenUtil.screenHeightPercentage * height);
			}
		}
		view.setLayoutParams(layoutParams);
	}

	/**
	 * 适配布局，注意:高宽如果一致会被当成正方形对待。.
	 * 
	 * @param view
	 *            the view
	 * @param layoutParams
	 *            the layout params
	 * @param width
	 *            the width 设计图宽
	 * @param height
	 *            the height 设计图高
	 */
	public static void layoutParams(View view,
			android.widget.AbsListView.LayoutParams layoutParams, int width,
			int height) {
		Context context = view.getContext();
		// 是否切换了不同分辨率
		if (ScreenUtil.isDifferentOrientation(context)) {
			ScreenUtil.swichDifferentOrientation(context);
		}
		if (width == height && width > 0) {
			int length = (int) Math.ceil(ScreenUtil.screenWidthPercentage
					* width);
			layoutParams.width = length;
			layoutParams.height = length;
		} else {
			if (width == android.widget.AbsListView.LayoutParams.MATCH_PARENT
					|| width == android.widget.AbsListView.LayoutParams.WRAP_CONTENT) {
				layoutParams.width = width;
			} else if (width > 0) {
				layoutParams.width = (int) Math
						.ceil(ScreenUtil.screenWidthPercentage * width);
			}
			if (height == android.widget.AbsListView.LayoutParams.MATCH_PARENT
					|| height == android.widget.AbsListView.LayoutParams.WRAP_CONTENT) {
				layoutParams.height = height;
			} else if (height > 0) {

				layoutParams.height = (int) Math
						.ceil(ScreenUtil.screenHeightPercentage * height);
			}
		}
		view.setLayoutParams(layoutParams);
	}

	/**
	 * 适配布局，注意:高宽如果一致会被当成正方形对待。.
	 * 
	 * @param view
	 *            the view
	 * @param layoutParams
	 *            the layout params
	 * @param width
	 *            the width 设计图宽
	 * @param height
	 *            the height 设计图高
	 */
	public static void layoutParams(View view,
			android.widget.Gallery.LayoutParams layoutParams, int width,
			int height) {
		Context context = view.getContext();
		// 是否切换了不同分辨率
		if (ScreenUtil.isDifferentOrientation(context)) {
			ScreenUtil.swichDifferentOrientation(context);
		}
		if (width == height && width > 0) {
			int length = (int) Math.ceil(ScreenUtil.screenWidthPercentage
					* width);
			layoutParams.width = length;
			layoutParams.height = length;
		} else {
			if (width == android.widget.Gallery.LayoutParams.MATCH_PARENT
					|| width == android.widget.Gallery.LayoutParams.WRAP_CONTENT) {
				layoutParams.width = width;
			} else if (width > 0) {
				layoutParams.width = (int) Math
						.ceil(ScreenUtil.screenWidthPercentage * width);
			}
			if (height == android.widget.Gallery.LayoutParams.MATCH_PARENT
					|| height == android.widget.Gallery.LayoutParams.WRAP_CONTENT) {
				layoutParams.height = height;
			} else if (height > 0) {

				layoutParams.height = (int) Math
						.ceil(ScreenUtil.screenHeightPercentage * height);
			}
		}
		view.setLayoutParams(layoutParams);
	}

	/**
	 * Gets the transform bitmap.
	 * 
	 * @param context
	 *            the context
	 * @param resId
	 *            the res id
	 * @return the transform bitmap
	 */
	public static Bitmap getTransformBitmap(Context context, int resId) {
		// 是否切换了不同分辨率
		if (ScreenUtil.isDifferentOrientation(context)) {
			ScreenUtil.swichDifferentOrientation(context);
		}
		Bitmap bitmap = ImageTools
				.decodeResource(context.getResources(), resId);
		int mWidth = 0, mHeight = 0;
		if (bitmap.getWidth() == bitmap.getHeight()) {
			int length = (int) Math.ceil(ScreenUtil.screenWidthPercentage
					* bitmap.getWidth());
			mWidth = mHeight = length;
		} else {
			mWidth = layoutWidth(context, bitmap.getWidth());
			mHeight = layoutHeigt(context, bitmap.getHeight());
		}
		return isFrugalMemory(context, resId, mWidth, bitmap, mHeight,
				bitmap.getWidth() <= mWidth);
	}

	/**
	 * 适配Bitmap转换类.不会被剪切，可能会变形，适合应用布局适配。（圆形图片需保证高宽一样大小）
	 * 
	 * @param context
	 *            the context
	 * @param resId
	 *            the res id
	 * @param isFrugalMemory
	 *            是否省内存 true可能会导致图片剪切掉,false则不会
	 * @return Bitmap
	 * @Title: layoutImageView
	 * @Description: TODO
	 */
	public static Bitmap getTransformBitmap(Context context, int resId,
			boolean isFrugalMemory) {
		// 是否切换了不同分辨率
		 int mWidth=0;
		Bitmap bitmap = null;
		int mHeight=0;
		try {
		if (ScreenUtil.isDifferentOrientation(context)) {
			ScreenUtil.swichDifferentOrientation(context);
		}
		 bitmap = ImageTools
				.decodeResource(context.getResources(), resId);

		if (bitmap.getWidth() == bitmap.getHeight()) {
			int length = (int) Math.ceil(ScreenUtil.screenWidthPercentage
					* bitmap.getWidth());
			mWidth = mHeight = length;
		} else {
			mWidth = layoutWidth(context, bitmap.getWidth());
			mHeight = layoutHeigt(context, bitmap.getHeight());
		}
		  bitmap=isFrugalMemory(context, resId, mWidth, bitmap, mHeight,
					bitmap.getWidth() <= mWidth);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return bitmap;
	}

	/**
	 * 是否省内存
	 * 
	 * @param context
	 * @param resId
	 * @param mWidth
	 * @param bitmap
	 * @param mHeight
	 * @param isFrugalMemory
	 * @return
	 */
	private static Bitmap isFrugalMemory(Context context, int resId,
			int mWidth, Bitmap bitmap, int mHeight, boolean isFrugalMemory) {
		Bitmap useBitmap = null;
		if (isFrugalMemory) {
			useBitmap = ImageTools.decodeSampledBitmapFromResource(
					context.getResources(), resId, mWidth, mHeight);
		} else {
			double i = bitmap.getWidth() * 1.0 / mWidth;
			useBitmap = Bitmap.createScaledBitmap(bitmap, mWidth,
					(int) Math.floor(bitmap.getHeight() / i), false);
		}
		if (mWidth != bitmap.getWidth() || mHeight != bitmap.getHeight()) {
			bitmap.recycle();
		}
		return useBitmap;
	}

	/**
	 * 适配Bitmap转换类.根据自己的要求来
	 * 
	 * @param context
	 *            the context
	 * @param resId
	 *            the res id
	 * @param width
	 *            the width
	 * @param height
	 *            the height
	 * @param isFrugalMemory
	 *            the is frugal memory
	 * @return the transform bitmap
	 */
	public static Bitmap getTransformBitmap(Context context, int resId,
			int width, int height, boolean isFrugalMemory) {
		Bitmap bitmap = ImageTools
				.decodeResource(context.getResources(), resId);
		int mWidth = 0, mHeight = 0;
		if (width == height) {
			int length = (int) Math.ceil(ScreenUtil.screenWidthPercentage
					* width);
			mWidth = mHeight = length;
		} else {
			mWidth = layoutWidth(context, width);
			mHeight = layoutHeigt(context, height);
		}
		return isFrugalMemory(context, resId, mWidth, bitmap, mHeight,
				isFrugalMemory);
	}

	/**
	 * 适配Drawable转换类 .
	 * 
	 * @param context
	 *            the context
	 * @param resId
	 *            the res id
	 * @param isFrugalMemory
	 *            the is frugal memory
	 * @return the transform drawable
	 */
	public static Drawable getTransformDrawable(Context context, int resId,
			boolean isFrugalMemory) {
		return (new BitmapDrawable(context.getResources(), getTransformBitmap(
				context, resId, isFrugalMemory)));
	}

	public static Drawable getTransformDrawable(Context context, int resId) {
		return (new BitmapDrawable(context.getResources(), getTransformBitmap(
				context, resId)));
	}
}
