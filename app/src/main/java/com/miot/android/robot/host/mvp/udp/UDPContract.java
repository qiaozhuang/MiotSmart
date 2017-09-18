package com.miot.android.robot.host.mvp.udp;

import com.miot.android.robot.host.base.mvp.BaseModel;
import com.miot.android.robot.host.base.mvp.BasePresenter;
import com.miot.android.robot.host.base.mvp.BaseView;

import rx.Observable;

/**
 * Created by Administrator on 2016/11/8 0008.
 */
public interface UDPContract {

	public interface Modle extends BaseModel {
		Observable<String> robotSendUdpStartConnectedServer(String ipAddress, int port);
		Observable<String> sendRobotDeviceConfig(byte[] message);
		Observable<String> deviceExecute(String ip,int port,String json);
		Observable<String> sendRobotStartUpdateDeviceConfig(String ipAddress,int port);

	}
	public interface  View extends BaseView {


	}

	public abstract class Persenter extends BasePresenter<UDPContract.Modle,UDPContract.View>{

		public  abstract void robotSendUdpStartConnectedServer(String ipAddress,int port);
		public  abstract void deviceExecute(String ip,int port,String json);
		public  abstract void sendRobotDeviceConfig(byte[] message);
		public abstract void  sendRobotStartUpdateDeviceConfig(String ipAddress,int port);

		@Override
		public void onStart() {

		}
	}

}
