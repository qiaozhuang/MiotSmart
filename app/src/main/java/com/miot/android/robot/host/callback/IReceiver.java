package com.miot.android.robot.host.callback;

/**
 * Created by Administrator on 2016/11/7 0007.
 */
public interface IReceiver {
	public void onReceive(int localPort, String host, int port, byte[] bs,
						  int len);
}
