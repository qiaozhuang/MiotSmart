package com.miot.android.robot.host.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;

import com.miot.android.robot.host.callback.NetworkCallBack;

/**
 * Created by Administrator on 2016/11/14 0014.
 */
public class NetBrcastReceiver extends BroadcastReceiver{

	public static NetworkCallBack networkCallBack=null;

	@Override
	public void onReceive(Context context, Intent intent) {

		if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
//			ConnectivityManager connectivityManager = (ConnectivityManager) context
//					.getSystemService(Context.CONNECTIVITY_SERVICE);
//			NetworkInfo wifiNI = connectivityManager
//					.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
//
//			NetworkInfo mobileNI = connectivityManager
//					.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
//			String ssid="";
//			if (wifiNI.isConnected() || mobileNI.isConnected()) {
//				WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
//				WifiInfo wifiInfo = wifiManager.getConnectionInfo();
//				if (wifiNI.isConnected()) {
//					if (wifiInfo.getBSSID() != null) {
//						ssid = wifiInfo.getSSID();
//						ssid=ssid.replace("\"","");
//						int signalLevel = WifiManager.calculateSignalLevel(wifiInfo.getRssi(), 5);
//						int speed = wifiInfo.getLinkSpeed();
//						String units = WifiInfo.LINK_SPEED_UNITS;
//					}
//				}
//				if (networkCallBack!=null){
//					networkCallBack.networkConnect(wifiNI.isConnected(),mobileNI.isConnected());
//
//				}
//			}

		}
	}
}
