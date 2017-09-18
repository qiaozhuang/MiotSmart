package com.miot.android.robot.host.mvp.udp;



import rx.functions.Action1;

/**
 * Created by Administrator on 2016/11/8 0008.
 */
public class UDPPersenter extends UDPContract.Persenter {
	@Override
	public void robotSendUdpStartConnectedServer(String ipAddress, int port) {
		mModel.robotSendUdpStartConnectedServer(ipAddress,port).subscribe(new Action1<String>() {
			@Override
			public void call(String s) {

			}
		});
	}
	@Override
	public void deviceExecute( String ip, int port, String json) {
		mModel.deviceExecute(ip,port,json).subscribe(new Action1<String>() {
			@Override
			public void call(String s) {

			}
		});
	}
	@Override
	public void sendRobotDeviceConfig(byte[] message) {
		mModel.sendRobotDeviceConfig(message).subscribe(new Action1<String>() {
			@Override
			public void call(String s) {

			}
		});;
	}
	@Override
	public void sendRobotStartUpdateDeviceConfig(String ipAddress, int port) {
		mModel.sendRobotStartUpdateDeviceConfig(ipAddress,port).subscribe(new Action1<String>() {
			@Override
			public void call(String s) {

			}
		});
	}
}
