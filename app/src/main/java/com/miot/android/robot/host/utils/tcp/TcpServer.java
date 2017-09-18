package com.miot.android.robot.host.utils.tcp;

import android.util.Log;

import com.miot.android.robot.host.callback.TcpIReceiver;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * TCP Socket服务器端
 * 
 * @author jzj1993
 * @since 2015-2-22
 */
public class TcpServer implements Runnable {

	private int port;
	private boolean runFlag;
	private List<SocketTransceiver> clients = new ArrayList<SocketTransceiver>();

	private TcpIReceiver tcpIReceiver=null;

	public void setTcpIReceiver(TcpIReceiver tcpIReceiver) {
		this.tcpIReceiver = tcpIReceiver;
	}

	/**
	 * 实例化
	 * 
	 * @param port
	 *            监听的端口
	 */
	public TcpServer(int port) {
		this.port = port;
	}

	/**
	 * 启动服务器
	 * <p>
	 * 如果启动失败，会回调{@code onServerStop()}
	 */
	public void start() {
		runFlag = true;
		new Thread(this).start();
	}

	/**
	 * 停止服务器
	 * <p>
	 * 服务器停止后，会回调{@code onServerStop()}
	 */
	public void stop() {
		runFlag = false;
	}

	/**
	 * 监听端口，接受客户端连接(新线程中运行)
	 */
	@Override
	public void run() {
		try {
			 ServerSocket server = new ServerSocket();
			 server.bind(new InetSocketAddress(port));
			Log.e("---",port+"");
			while (runFlag) {
				try {
					Socket socket = server.accept();
					Log.e("---",socket.getInetAddress().getHostAddress());
					startClient(socket);
				} catch (IOException e) {
					e.printStackTrace();
					if (tcpIReceiver!=null){
						tcpIReceiver.onConnectFailed();
					}
				}
			}
			// 停止服务器，断开与每个客户端的连接
			try {
				for (SocketTransceiver client : clients) {
					client.stop();
				}
				clients.clear();
				server.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (tcpIReceiver!=null){
			tcpIReceiver.onServerStop();
		}
	}

	/**
	 * 启动客户端收发
	 * 
	 * @param socket
	 */

	SocketTransceiver socketTransceiver=null;
	private void startClient(final Socket socket) {
		SocketTransceiver client = new SocketTransceiver(socket) {

			@Override
			public void onReceive(InetAddress addr, String s) {
				if (tcpIReceiver!=null){
					tcpIReceiver.onReceive(this,s);
				}
			}
			@Override
			public void onDisconnect(InetAddress addr) {
				clients.remove(this);
				if (tcpIReceiver!=null){
					tcpIReceiver.onDisconnect(this);
				}
			}
		};
		this.socketTransceiver=client;
		client.start();
		clients.add(client);
		if (tcpIReceiver!=null){
			tcpIReceiver.onConnect(client);
		}

	}
}
