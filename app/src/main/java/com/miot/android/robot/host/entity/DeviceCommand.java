package com.miot.android.robot.host.entity;

import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;

/**
 * Created by Administrator on 2016/11/11 0011.
 */
@Table(name="device_command")
public class DeviceCommand {

	private int id=0;

	private String Mac="";

	private String value="";

	private String action="";

	private String type="";

	private String name="";

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Id
	private String code="";

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getMac() {
		return Mac;
	}

	public void setMac(String mac) {
		this.Mac = mac;
	}
}
