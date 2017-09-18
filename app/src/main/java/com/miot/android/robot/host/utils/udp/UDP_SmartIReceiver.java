package com.miot.android.robot.host.utils.udp;

import android.content.Context;
import android.util.Log;

import com.miot.android.robot.host.callback.IReceiver;
import com.miot.android.robot.host.callback.RobotCallback;
import com.miot.android.robot.host.service.RobotHostService;
import com.miot.android.robot.host.utils.FormatConsts;

import java.io.UnsupportedEncodingException;

/**
 * Created by Administrator on 2016/11/7 0007.
 */
public class UDP_SmartIReceiver implements IReceiver {

	private static UDP_SmartIReceiver instance = null;

	private int port = 0;

	private Context context;

	private UDPSocket udpSocket = null;

	public static UDP_SmartIReceiver getInstance() {
		if (instance == null) {
			synchronized (UDP_SmartIReceiver.class) {
				if (instance == null) {
					instance = new UDP_SmartIReceiver(RobotHostService.instance);
				}
			}
		}
		return instance;
	}

	private UDP_SmartIReceiver(Context context) {
		this.context = context;
	}

	private RobotCallback robotCallback = null;

	public void setRobotCallback(RobotCallback robotCallback) {
		this.robotCallback = robotCallback;
	}


	/**
	 * 监听端口初始化
	 *
	 * @param port
	 */
	public void init(int port) {
		this.port = port;
		udpSocket = new UDPSocket(context);
		udpSocket.startRecv(port, this);
	}
	public void sendRobotInfo(String ip,int port,String content) {
		byte[] bs = null;
		try {
			if (content.isEmpty()) {
				return;
			}
			bs = content.getBytes("UTF-8");
			sendUdp(ip, port, bs);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	private boolean sendUdp(String ip, int port, byte[] content) {
		try {
			if (udpSocket!=null) {
				udpSocket.send(ip, port, content, content.length);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean sendUdpVsp(byte[] content) {
		try {
			if (udpSocket!=null) {
				udpSocket.send("255.255.255.255", FormatConsts.VSP_SERVER_PORT, content, content.length);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public void onReceive(int localPort, String host, int port, byte[] bs, int len) {
		try {
			String msg = new String(bs, "UTF-8");
			Log.e("UDP_onReceive",msg);
			Log.e("UDP_localPort",localPort+"");
			Log.e("UDP_host",host);
			if (robotCallback != null) {
				robotCallback.Message(msg,host,port);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
}
