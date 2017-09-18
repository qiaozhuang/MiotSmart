package com.miot.android.robot.host.utils.tcp;

import android.util.Log;

import com.miot.android.robot.host.callback.RobotUpdateCallBack;
import com.miot.android.robot.host.callback.TcpIReceiver;
import com.miot.android.robot.host.utils.FormatConsts;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/11/8 0008.
 */
public class RobotTcpSocket implements TcpIReceiver{

	private static RobotTcpSocket instance=null;

	private TcpServer tcpServer=null;

	private int port=0;

	private String message="";

	private byte[] bytes=null;

	public static RobotTcpSocket getInstance() {
		if (instance==null){
			synchronized (RobotTcpSocket.class){
				if (instance==null){
					instance=new RobotTcpSocket();
				}
			}
		}
		return instance;
	}
	public void init(){
		tcpServer=new TcpServer(FormatConsts.TRAN_SERVER_PORT);
		tcpServer.setTcpIReceiver(this);
		tcpServer.start();
	}

	private RobotUpdateCallBack robotUpdateCallBack=null;

	public void setRobotUpdateCallBack(RobotUpdateCallBack robotUpdateCallBack) {
		this.robotUpdateCallBack = robotUpdateCallBack;
	}

	ArrayList<String> jsonList=new ArrayList<String>();
	private int index=0;
	public void send(String  message){
		Log.e("--send-",message);
		index=0;
		this.message=message;
		jsonList.clear();
		if (message.length()>1024){
			for(int i=0;i<message.length()/1024;i++){
				if(i==message.length()/1024-1){
					jsonList.add(message.substring(i*1024,message.length()));
				}else{
					if(message.substring(i*1024, i*1024+1).equals("\"")&&
							message.substring((i+1)*1024-1, (i+1)*1024).equals("\"")){
						jsonList.add("$"+message.substring(i*1024,(i+1)*1024));
					}else{
						jsonList.add(message.substring(i*1024,(i+1)*1024));
					}
				}

			}
		}else{
			jsonList.add(message);
		}
	}


	public void onDistory(){
		if(tcpServer!=null){
			tcpServer.stop();
		}
	}

	@Override
	public void onConnect(SocketTransceiver client) {

	}

	@Override
	public void onConnectFailed() {

	}

	@Override
	public void onReceive(SocketTransceiver client, String s) {
		try{
			if (s.isEmpty()){
				return;
			}
			Log.e("onReceive",s);
			JSONObject clientDataObj = new JSONObject(s);
			String statusString=clientDataObj.getString("STATUS");
			if(statusString!=null){
				if(statusString.equals("UPDATE_START")||statusString.equals("OK")){
					if (index<jsonList.size()){
						JSONObject sendDataObj =new  JSONObject();
						sendDataObj.put("STATUS", "WORKING");
						sendDataObj.put("DEVICESINFO", jsonList.get(index));
						client.send(sendDataObj.toString());
						Log.e("json",sendDataObj.toString());
						index++;
					}else {
						JSONObject sendDataObj = new JSONObject();
						sendDataObj.put("STATUS", "FINISH");
						sendDataObj.put("DEVICESINFO", sendDataObj.toString());
						client.send(sendDataObj.toString());
						index=0;
					}
				}
				if (statusString.equals("UPDATE_GOOD")){
					if (robotUpdateCallBack!=null){
						robotUpdateCallBack.onReceiverRobotUpdateUIListener(true);
					}
				}
			}

		}catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void onDisconnect(SocketTransceiver client) {

	}

	@Override
	public void onServerStop() {

	}
}
