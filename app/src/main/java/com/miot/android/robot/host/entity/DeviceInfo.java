package com.miot.android.robot.host.entity;

/**
 * Created by Administrator on 2016/11/8 0008.
 */
public class DeviceInfo {

	private String version="0001";

	private String deviceKey="";

	private String  type="0";

	private RobotInfo DEVICE_INFO=null;

	private String sign="";


	public RobotInfo getDEVICE_INFO() {
		return DEVICE_INFO;
	}

	public void setDEVICE_INFO(RobotInfo DEVICE_INFO) {
		this.DEVICE_INFO = DEVICE_INFO;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDeviceKey() {
		return deviceKey;
	}

	public void setDeviceKey(String deviceKey) {
		this.deviceKey = deviceKey;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
}
