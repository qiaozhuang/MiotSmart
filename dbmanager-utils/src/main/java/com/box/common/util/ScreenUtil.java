/*
 * 
 */

package com.box.common.util;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;

// TODO: Auto-generated Javadoc
/**
 * The Class ScreenUtil.
 * 
 * @ClassName: ScreenUtil
 * @Description:
 * @author 作者 E-mail <a href="mailto:yubo@51box.cn">禹波</a>
 * @version 创建时间：2013-12-25 14:11:34 Screen util.
 */
public class ScreenUtil {

	/** 屏幕的真实宽度. */
	public static int screenWidth;

	/** 屏幕的真实高度. */
	public static int screenHeight;

	/** 是否减去状态栏高度. */
	public static int statusBarHeight;

	/** UI提供高清图片宽度. */
	public static Integer originalImageWidth = 720;

	/** UI提供高清图片高度. */
	public static Integer originalImageHeight = 1280;

	/** 图片屏幕换算比例_宽度. */
	public static float screenWidthPercentage;//

	/** 图片屏幕换算比例_高度. */
	public static float screenHeightPercentage;

	/** The density. */
	public static float density;

	/** The scale density. */
	public static float scaleDensity;

	/** The xdpi. */
	public static float xdpi;

	/** The ydpi. */
	public static float ydpi;

	/** The density dpi. */
	public static int densityDpi;

	/** The screen min. */
	public static int screenMin; // 宽高中，最小的值

	/** 是否减去状态栏高度. */
	public static boolean isStatusBar = true;

	/** The current orientation. */
	private static int currentOrientation;

	/**
	 * 初始化屏幕相关参数，放在application里.
	 * 
	 * @param context
	 *            the context
	 * @param originalWidth
	 *            设计图宽度
	 * @param originalHeight
	 *            设计图高度
	 * @param isFullScreen
	 *            是否全屏
	 * @return the info
	 */
	public static void getInfo(Context context, Integer originalWidth,
			Integer originalHeight, boolean isFullScreen) {
		originalImageWidth = originalWidth;
		originalImageHeight = originalHeight;
		isStatusBar = !isFullScreen;
		getInfo(context);
	}

	/**
	 * Gets the info.
	 * 
	 * @param context
	 *            the context
	 * @return the info
	 */
	private static void getInfo(Context context) {
		if (null == context) {
			return;
		}
		if (originalImageHeight == null || originalImageWidth == null) {
			throw new IllegalArgumentException(
					"originalImageHeight or originalImageWidth is not null");
		}
		DisplayMetrics dm = new DisplayMetrics();
		dm = context.getResources().getDisplayMetrics();
		currentOrientation = context.getResources().getConfiguration().orientation;
		screenWidth = dm.widthPixels;
		screenHeight = dm.heightPixels;
		// 每英寸多少个像素点
		density = dm.density;
		scaleDensity = dm.scaledDensity;
		xdpi = dm.xdpi;
		ydpi = dm.ydpi;
		densityDpi = dm.densityDpi;
		if (isStatusBar) {
			screenHeight = screenHeight - getStatusBarHeight();
		}
		// // (高除宽)正常1.7,不正常2.0
		float coefficient = 1;
		// // (高除宽)正常1.7,不正常2.0
		// if (coefficient < 1.65 || coefficient > 1.8) {
		// coefficient = 1 / coefficient;
		// } else {
		// coefficient = 1;
		// }
		// coefficient = 0.93f;
		screenWidthPercentage = coefficient * screenWidth
				/ (originalImageWidth * 1f);
		screenHeightPercentage = coefficient * screenHeight
				/ (originalImageHeight * 1f);
		screenMin = (screenWidth > screenHeight) ? screenHeight : screenWidth;
	}

	/**
	 * 是否已经切换到不同的屏幕
	 * 
	 * @param context
	 *            the context
	 * @return true 不同，false相同
	 */
	public static boolean isDifferentOrientation(Context context) {
		if (ScreenUtil.screenWidth == 0) {
			return true;
		}
		boolean isDifferent = (currentOrientation == context.getResources()
				.getConfiguration().orientation);
		return !isDifferent;
	}

	/**
	 * 当发生切换屏幕时调用
	 * 
	 * @param context
	 */
	public static void swichDifferentOrientation(Context context) {
		getInfo(context, originalImageHeight, originalImageWidth, !isStatusBar);
	}

	/**
	 * dip转px.
	 * 
	 * @param dipValue
	 *            the dip value
	 * @return the int
	 */
	public static int dip2px(float dipValue) {
		final float scale = ScreenUtil.density;
		return (int) (dipValue * scale + 0.5f);
	}

	/**
	 * 获取通知栏高度
	 * 
	 * @return the status bar height
	 */
	public static int getStatusBarHeight() {
		if (statusBarHeight == 0) {
			statusBarHeight = Resources.getSystem().getDimensionPixelSize(
					Resources.getSystem().getIdentifier("status_bar_height",
							"dimen", "android"));
		}
		return statusBarHeight;
	}
}
