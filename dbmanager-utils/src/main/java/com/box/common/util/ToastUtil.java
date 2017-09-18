/*
 * 
 */

package com.box.common.util;

import android.content.Context;
import android.widget.Toast;

// TODO: Auto-generated Javadoc
/**
 * The Class ToastUtil.
 * 
 * @ClassName: ToastUtil
 * @Description:
 * @author 作者 E-mail <a href="mailto:yubo@51box.cn">禹波</a>
 * @version 创建时间：2014-1-7 11:11:56 Toast util.
 */
public class ToastUtil {
	private static Toast toast;

	/**
	 * Alert.
	 * 
	 * @param context
	 *            the context
	 * @param msg
	 *            the msg
	 */
	public static void alert(Context context, String msg) {
		if (toast == null) {
			toast = Toast.makeText(context, msg, Toast.LENGTH_LONG);
		}
		toast.setText(msg);
		toast.setDuration(Toast.LENGTH_LONG);
		toast.show();
	}

	/**
	 * Alert.
	 * 
	 * @param context
	 *            the context
	 * @param
	 *
	 */
	public static void alert(Context context, int msg) {
		if (toast == null) {
			toast = Toast.makeText(context, msg, Toast.LENGTH_LONG);
		}
		toast.setText(msg);
		toast.setDuration(Toast.LENGTH_LONG);
		toast.show();
	}

	/**
	 * Alert short.
	 * 
	 * @param context
	 *            the context
	 * @param msg
	 *            the msg
	 */
	public static void alertShort(Context context, String msg) {
		if (toast == null) {
			toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
		}
		toast.setText(msg);
		toast.setDuration(Toast.LENGTH_SHORT);
		toast.show();
	}

	/**
	 * Alert short.
	 * 
	 * @param context
	 *            the context
	 * @param
	 *
	 */
	public static void alertShort(Context context, int msg) {
		if (toast == null) {
			toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
		}
		toast.setText(msg);
		toast.setDuration(Toast.LENGTH_SHORT);
		toast.show();
	}
}
