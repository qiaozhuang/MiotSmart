package com.miot.android.robot.host.callback;

/**
 * Created by Administrator on 2016/6/21 0021.
 * 监测网络的状态接口
 */
public interface NetworkCallBack {
	public  void  networkConnect(boolean isMob, boolean isWifi);
}
