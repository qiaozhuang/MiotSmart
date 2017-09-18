package com.miot.android.robot.host.utils;

import android.content.Context;

import com.miot.android.robot.host.entity.Device;

/**
 * Created by Administrator on 2017/1/12 0012.
 */
public class TockenUtils {

	public static boolean isNewPuTocken(Context context, Device device){
		SharedPreferencesUtil sharedPreferencesUtil=SharedPreferencesUtil.getInstance(context);
		if (sharedPreferencesUtil.getPuTocken().equals(device.getPuTocken())){
			return false;
		}
		return true;
	}

	public static boolean isNewSceneTocken(Context context, Device device){
		SharedPreferencesUtil sharedPreferencesUtil=SharedPreferencesUtil.getInstance(context);
		if (sharedPreferencesUtil.getSceenTocken().equals(device.getSceenTocken())){
			return false;
		}
		return true;
	}

	public static boolean isAllNewTocken(Context context, Device device){
		if (isNewPuTocken(context,device)&&isNewSceneTocken(context,device)){
			return false;
		}
		return true;
	}
}
