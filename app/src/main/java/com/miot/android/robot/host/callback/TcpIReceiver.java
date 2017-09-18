package com.miot.android.robot.host.callback;

import com.miot.android.robot.host.utils.tcp.SocketTransceiver;

/**
 * Created by Administrator on 2016/11/8 0008.
 */
public interface TcpIReceiver {

	/**
	 * 客户端：连接建立
	 * <p>
	 * 注意：此回调是在新线程中执行的
	 *
	 * @param client
	 *            SocketTransceiver对象
	 */
	public  void onConnect(SocketTransceiver client);

	/**
	 * 客户端：连接建立失败
	 * <p>
	 * 注意：此回调是在新线程中执行的
	 */
	public  void onConnectFailed();

	/**
	 * 客户端：收到字符串
	 * <p>
	 * 注意：此回调是在新线程中执行的
	 *
	 * @param client
	 *            SocketTransceiver对象
	 * @param s
	 *            字符串
	 */
	public void onReceive(SocketTransceiver client, String s);

	/**
	 * 客户端：连接断开
	 * <p>
	 * 注意：此回调是在新线程中执行的
	 *
	 * @param client
	 *            SocketTransceiver对象
	 */
	public void onDisconnect(SocketTransceiver client);

	/**
	 * 服务器停止
	 * <p>
	 * 注意：此回调是在新线程中执行的
	 */
	public  void onServerStop();
}
