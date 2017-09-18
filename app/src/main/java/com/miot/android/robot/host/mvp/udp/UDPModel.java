package com.miot.android.robot.host.mvp.udp;

import com.miot.android.robot.host.entity.DeviceInfo;
import com.miot.android.robot.host.entity.RobotInfo;
import com.miot.android.robot.host.utils.FormatConsts;
import com.miot.android.robot.host.utils.udp.UDP_SmartIReceiver;

import org.json.JSONObject;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/11/8 0008.
 */
public class UDPModel implements UDPContract.Modle {
	@Override
	public Observable<String> robotSendUdpStartConnectedServer(final String ipAddress, final int port) {
			return  Observable.create(new Observable.OnSubscribe<String>() {
				@Override
				public void call(Subscriber<? super String> subscriber) {
							DeviceInfo deviceInfo = new DeviceInfo();
							RobotInfo robotInfo = new RobotInfo();
							robotInfo.setCmdPort(FormatConsts.COMMAND_CONTROL_PORT+"");
							robotInfo.setTranPort(FormatConsts.TRAN_SERVER_PORT+"");
							robotInfo.setUserName("null");
							robotInfo.setPassWord("null");
							robotInfo.setServerName(FormatConsts.LOCALHOST_ADDRESS);
							robotInfo.setDeviceName("杭州妙联服务器");
							robotInfo.setDeviceID(FormatConsts.MAC);
							deviceInfo.setDEVICE_INFO(robotInfo);
							deviceInfo.setSign("null");
							deviceInfo.setDeviceKey("null");
							String json = gson.toJson(deviceInfo);

							UDP_SmartIReceiver.getInsatance().sendRobotInfo(ipAddress, port, json);
						subscriber.onNext("");
						subscriber.onCompleted();
				}

			}).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
	   }

	@Override
	public Observable<String> sendRobotDeviceConfig(final byte[] message) {
		return  Observable.create(new Observable.OnSubscribe<String>() {
			@Override
			public void call(Subscriber<? super String> subscriber) {
				UDP_SmartIReceiver.getInsatance().sendUdpVsp(message);
				subscriber.onNext("");
				subscriber.onCompleted();
			}

		}).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
	}

	@Override
	public Observable<String> deviceExecute(final String ip,final int port,final String json) {
		return  Observable.create(new Observable.OnSubscribe<String>() {
			@Override
			public void call(Subscriber<? super String> subscriber) {
				UDP_SmartIReceiver.getInsatance().sendRobotInfo(ip, port, json);
			}

		}).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
	}

	@Override
	public Observable<String> sendRobotStartUpdateDeviceConfig(final String ipAddress,final int port) {
		return Observable.create(new Observable.OnSubscribe<String>() {
			@Override
			public void call(Subscriber<? super String> subscriber) {
				try{
					JSONObject jsonObject=new JSONObject();
					jsonObject.put("UPDATE","START");
					UDP_SmartIReceiver.getInsatance().sendRobotInfo(ipAddress, 37677, jsonObject.toString());

					subscriber.onNext("");
					subscriber.onCompleted();
				}catch (Exception e){
					e.printStackTrace();
				}
			}

		}).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
	}
}
