package com.miot.android.robot.host.utils.udp;

import android.content.Context;
import android.util.Log;

import com.miot.android.robot.host.callback.CustomCallback;
import com.miot.android.robot.host.callback.IReceiver;

import java.io.UnsupportedEncodingException;

/**
 * Created by Administrator on 2016/11/9 0009.
 */
public class UDP_SmartCustomIReceiver implements IReceiver {

	private static UDP_SmartCustomIReceiver instance = null;

	private int port = 0;


	private UDP_CustomSocket udpSocket = null;

	public static UDP_SmartCustomIReceiver getInstance(Context context) {
		if (instance == null) {
			synchronized (UDP_SmartIReceiver.class) {
				if (instance == null) {
					instance = new UDP_SmartCustomIReceiver(context);
				}
			}
		}
		return instance;
	}

	private UDP_SmartCustomIReceiver(Context context) {

		if (udpSocket==null) {
			udpSocket = new UDP_CustomSocket(context);
		}
	}

	private CustomCallback robotCallback = null;

	public void setRobotCallback(CustomCallback robotCallback) {
		this.robotCallback = robotCallback;
	}

	/**
	 * 监听端口初始化
	 *
	 * @param port
	 */
	public void init(int port) {

		this.port = port;

		udpSocket.startRecv(port, this);
	}

	public static String Charset = "ISO-8859-1";
	public static String getMlccContent(byte[] bs, int len) {
		try {
			if (bs == null || len < 20) {
				return null;
			}
			return new String(bs, 20, len - 20,Charset);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}

	}
	@Override
	public void onReceive(int localPort, String host, int port, byte[] bs, int len) {
		try {
			String msg=new String(bs,"UTF-8");
//			String message= getMlccContent(VspContent.decodeMlccMsg(bs),len);
			Log.e("---",msg);
			if (robotCallback!=null){
				robotCallback.customReceiver(msg,host,port);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}



	}
}
