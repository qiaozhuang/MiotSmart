package com.miot.android.robot.host.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * Created by Administrator on 2017/1/13 0013.
 */
public class CommonUtil {
	/**
	 * byte数组转换成16进制字符串
	 * @param src
	 * @return
	 */
	public static String bytesToHexString(byte[] src){
		StringBuilder stringBuilder = new StringBuilder();
		if (src == null || src.length <= 0) {
			return null;
		}
		for (int i = 0; i < src.length; i++) {
			int v = src[i] & 0xFF;
			String hv = Integer.toHexString(v);
			if (hv.length() < 2) {
				stringBuilder.append(0);
			}
			stringBuilder.append(hv);
		}
		return stringBuilder.toString();
	}

	/**
	 *
	 * @param num
	 * @return
	 */
	public static byte[] long2Bytes(long num) {
		byte[] byteNum = new byte[8];
		for (int ix = 0; ix < 8; ++ix) {
			int offset = 64 - (ix + 1) * 8;
			byteNum[ix] = (byte) ((num >> offset) & 0xff);
		}
		return byteNum;
	}
	/**
	 * byte数组转换成16进制字符数组
	 * @param src
	 * @return
	 */
	public static String[] bytesToHexStrings(byte[] src){
		if (src == null || src.length <= 0) {
			return null;
		}
		String[] str = new String[src.length];
		for (int i = 0; i < src.length; i++) {
			int v = src[i] & 0xFF;
			String hv = Integer.toHexString(v);
			if (hv.length() < 2) {
				str[i] = "0";
			}
			str[i] = hv;
		}
		return str;
	}

	public static int getVersionCode(Context context)//获取版本号(内部识别号)
	{
		try {
			PackageInfo pi=context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
			return pi.versionCode;
		} catch (PackageManager.NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}

}
