package com.miot.android.robot.host.entity;

import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;

/**
 * Created by Administrator on 2016/11/10 0010.
 */
@Table(name="miot_devicejson")
public class DeviceCtrlList {

	private String floor_name="";
	private String room_name="";
	private String device_name="";
	private String dev_class_type="";
	private String operation="";
	private String parameter="null";
	private String value="null";

	@Id
	private String code="";


	public String getFloor_name() {
		return floor_name;
	}

	public void setFloor_name(String floor_name) {
		this.floor_name = floor_name;
	}

	public String getRoom_name() {
		return room_name;
	}

	public void setRoom_name(String room_name) {
		this.room_name = room_name;
	}

	public String getDevice_name() {
		return device_name;
	}

	public void setDevice_name(String device_name) {
		this.device_name = device_name;
	}

	public String getDev_class_type() {
		return dev_class_type;
	}

	public void setDev_class_type(String dev_class_type) {
		this.dev_class_type = dev_class_type;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
