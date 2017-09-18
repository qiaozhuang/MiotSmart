package com.miot.android.robot.host.entity;

/**
 * Created by Administrator on 2016/11/8 0008.
 */
public class RobotInfo {

	private String userName=null;

	private String passWord=null;

	private String serverName="";

	private String cmdPort="65510";

	private String tranPort="65511";

	private String deviceName="你好,杭州妙联";

	private String deviceID="";


	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public String getCmdPort() {
		return cmdPort;
	}

	public void setCmdPort(String cmdPort) {
		this.cmdPort = cmdPort;
	}

	public String getTranPort() {
		return tranPort;
	}

	public void setTranPort(String tranPort) {
		this.tranPort = tranPort;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public String getDeviceID() {
		return deviceID;
	}

	public void setDeviceID(String deviceID) {
		this.deviceID = deviceID;
	}
}
