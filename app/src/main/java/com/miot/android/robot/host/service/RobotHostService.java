package com.miot.android.robot.host.service;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.gson.Gson;
import com.miot.android.robot.host.CommunicationAIDL;
import com.miot.android.robot.host.base.BaseService;
import com.miot.android.robot.host.callback.CustomCallback;
import com.miot.android.robot.host.callback.RobotCallback;
import com.miot.android.robot.host.callback.RobotUpdateCallBack;
import com.miot.android.robot.host.db.SnCodeManager;
import com.miot.android.robot.host.entity.ROBOTINFI;
import com.miot.android.robot.host.entity.RobotVersion;
import com.miot.android.robot.host.entity.SnCode;
import com.miot.android.robot.host.mvp.udp.UDPContract;
import com.miot.android.robot.host.mvp.udp.UDPModel;
import com.miot.android.robot.host.mvp.udp.UDPPersenter;
import com.miot.android.robot.host.utils.FormatConsts;
import com.miot.android.robot.host.utils.JsonUtils;
import com.miot.android.robot.host.utils.NetUtil;
import com.miot.android.robot.host.utils.tcp.RobotTcpSocket;
import com.miot.android.robot.host.utils.udp.UDP_SmartCustomIReceiver;
import com.miot.android.robot.host.utils.udp.UDP_SmartIReceiver;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * 主机Service用于监听小智机器人的语音消息
 * 更新小智机器人的配置信息
 * Created by Administrator on 2016/11/22 0022.
 */
public class RobotHostService extends BaseService<UDPPersenter,UDPModel> implements RobotCallback,UDPContract.View,RobotUpdateCallBack,CustomCallback {
	private String snCode="";

	public static RobotHostService  instance=null;

	private MyServiceConnection myServiceConnection=null;

	private CommunicationAIDL myCommunicationAIDL=null;


	private String deviceData="";

	@Override
	public void onCreate() {
		super.onCreate();
		instance=this;
		myCommunicationAIDL=new MyCommunicationAIDL();
		if (myServiceConnection==null){
			myServiceConnection=new MyServiceConnection();
		}
		FormatConsts.MAC= NetUtil.getWifiMacAddress();
		FormatConsts.LOCALHOST_ADDRESS=NetUtil.getHostAddress(this);
		snCode=FormatConsts.getSnCode();
		RobotTcpSocket.getInstance().init();
		UDP_SmartCustomIReceiver.getInstance(this).init(FormatConsts.COMMAND_CONTROL_PORT);
		UDP_SmartIReceiver.getInstance().setRobotCallback(this);
        UDP_SmartIReceiver.getInstance().init(FormatConsts.ROBOT_SEND_PORT);
        RobotTcpSocket.getInstance().setRobotUpdateCallBack(this);
        UDP_SmartCustomIReceiver.getInstance(this).setRobotCallback(this);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Intent vspIntent=new Intent(RobotHostService.this,NetworkService.class);
		vspIntent.setAction(FormatConsts.VSP_ACTION_SERVICE);
		vspIntent.setPackage(context.getPackageName());
		RobotHostService.this.bindService(vspIntent, myServiceConnection, Context.BIND_IMPORTANT);
		return super.onStartCommand(intent, flags, startId);
	}

	private Handler handler=new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what){
				case FormatConsts.REBOT_INIT:
					try {
						String message=myCommunicationAIDL.rebotConfigMessage("getAddress","");
						if (message!=null){
							SnCode snCode=new Gson().fromJson(message,SnCode.class);
							if (snCode!=null){
								String platform=myCommunicationAIDL.rebotConfigMessage("platform","");
								if (platform.equals("")){
									JSONObject jsonObject = new JSONObject();
									JSONArray DeviceCtrlLists=new JSONArray();
									JSONObject senceModel=new JSONObject();
									senceModel.put("model","回家模式");
									senceModel.put("code","SA_10000_1");
									DeviceCtrlLists.put(senceModel);
									jsonObject.put("SenceModelList", DeviceCtrlLists);
									platform=jsonObject.toString();
									String msgText="主人,你好。请使用妙居APP同步数据到小智机器人可以语音控制妙居APP的场景和设备。";
									mPresenter.deviceExecute(snCode.getIpAddress(),FormatConsts.ROBOT_SERVER_PORT, JsonUtils.assembleVoiceCommands(msgText));
								}else{
									deviceData = platform;
								}
								RobotTcpSocket.getInstance().send(platform);
								mPresenter.robotSendUdpStartConnectedServer(snCode.getIpAddress(), snCode.getPort());

							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					break;
				case 5001:
					try {
						String json=msg.obj.toString();
						if (json.isEmpty()){
							try {
								myCommunicationAIDL.notifySuccess(false,"");
							} catch (RemoteException e) {
								e.printStackTrace();
							}
							return;
						}
						json=json.replaceAll("\\\\", "");
						json=json.replace("\\","");
						deviceData=json;
						Log.e("----",myCommunicationAIDL.rebotConfigMessage("platform",""));
						String message=myCommunicationAIDL.rebotConfigMessage("getAddress","");
						RobotTcpSocket.getInstance().send(json);
						if (message!=null) {
							SnCode snCode = new Gson().fromJson(message, SnCode.class);
							if (snCode!=null){
								if (snCode.getIpAddress().equals("")){
									snCode.setIpAddress(FormatConsts.LOCALHOST_ADDRESS);
									mPresenter.sendRobotStartUpdateDeviceConfig(snCode.getIpAddress(), snCode.getPort());
									mPresenter.robotSendUdpStartConnectedServer(FormatConsts.LOCALHOST_ADDRESS,FormatConsts.ROBOT_SEND_PORT);
								}
								return;
							}
							mPresenter.robotSendUdpStartConnectedServer(FormatConsts.LOCALHOST_ADDRESS,FormatConsts.ROBOT_SEND_PORT);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}

					break;
			}
		}
	};

	@Override
	public void Message(String message, String ipAddress, int port) {
		RobotVersion robotVersion=JsonUtils.getRobotInitStart(message);
		if (robotVersion!=null) {
			ROBOTINFI rebotInfo = robotVersion.getROBOTINFO();
			if (rebotInfo == null) {
				return;
			}
			if (rebotInfo.getSn().isEmpty()) {
				return;
			}
			if (!rebotInfo.getSn().equals(snCode)) {
				return;
			}
			SnCode sn=new SnCode();
			sn.setSys(false);
			sn.setIpAddress(ipAddress);
			sn.setPort(port);
			sn.setSn(snCode);
			try {
				myCommunicationAIDL.rebotConfigMessage("saveAddress",new Gson().toJson(sn));
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			Map<String,Object> map=new HashMap<>();
			map.put("message",message);
			map.put("host",ipAddress);
			map.put("port",port);
			Message msg=new Message();
			msg.obj=map;
			msg.what=FormatConsts.REBOT_INIT;
			handler.sendMessage(msg);
		}

	}

	@Override
	public void customReceiver(String message, String ipAddress, int port) {

		if (message.equals("{\"TTS_REQUEST\":\"OK\"}")){
			return;
		}
		if (myCommunicationAIDL!=null){
			try {
				myCommunicationAIDL.receiverRebotVoice(message);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}


	@Override
	public void onReceiverRobotUpdateUIListener(boolean statu) {
		if (statu){
			SnCode sn=SnCodeManager.getInstance(context).getSnCode(snCode);
			if (sn==null){
				sn=new SnCode();
				sn.setSys(true);
				sn.setSn(snCode);
				sn.setUpdateDate(deviceData);
			}else{
				sn.setSys(true);
				sn.setUpdateDate(deviceData);
			}
			SnCodeManager.getInstance(context).save(sn);
			if (myCommunicationAIDL!=null){
				try {
					myCommunicationAIDL.rebotConfigMessage("update",deviceData);
					myCommunicationAIDL.notifySuccess(statu,deviceData);
				} catch (RemoteException e) {
					e.printStackTrace();
				}
			}
		}

	}

	class  MyServiceConnection implements ServiceConnection{

		@Override
		public void onServiceConnected(ComponentName componentName, IBinder iBinder) {

			myCommunicationAIDL=MyCommunicationAIDL.Stub.asInterface(iBinder);
		}

		@Override
		public void onServiceDisconnected(ComponentName componentName) {
			RobotHostService.this.startService(new Intent(RobotHostService.this,NetworkService.class));
			RobotHostService.this.bindService(new Intent(RobotHostService.this,NetworkService.class),myServiceConnection, Context.BIND_IMPORTANT);
		}
	}

	@Nullable
	@Override
	public IBinder onBind(Intent intent) {

		return new MyCommunicationAIDL();
	}

	class  MyCommunicationAIDL extends CommunicationAIDL.Stub{

		@Override
		public void receiverRebotVoice(String voiceCode) throws RemoteException {
		}

		@Override
		public void receiverPlatform(boolean update, String json) throws RemoteException {
			if (update) {
				Message message = new Message();
				message.obj = json;
				message.what = 5001;
				handler.sendMessage(message);
				return;
			}
		}

		@Override
		public void notifySuccess(boolean isSuccess, String message) throws RemoteException {

		}



		@Override
		public void errorMessage(String message) throws RemoteException {
			SnCode sn= new Gson().fromJson(myCommunicationAIDL.rebotConfigMessage("getAddress",""),SnCode.class);
			if (sn!=null){
				mPresenter.deviceExecute(sn.getIpAddress(),FormatConsts.ROBOT_SERVER_PORT, JsonUtils.assembleVoiceCommands(message));
			}else{
				mPresenter.deviceExecute(FormatConsts.LOCALHOST_ADDRESS,FormatConsts.ROBOT_SERVER_PORT, JsonUtils.assembleVoiceCommands(message));
			}
		}

		@Override
		public String rebotConfigMessage(String type, String message) throws RemoteException {
			return null;
		}

	}

	@Override
	public void initPresenter() {
      this.mPresenter.setVM(this,mModel);
	}
}
