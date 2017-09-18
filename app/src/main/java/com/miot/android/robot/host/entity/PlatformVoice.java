package com.miot.android.robot.host.entity;

import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;

/**
 * Created by Administrator on 2017/1/11 0011.
 */
@Table(name="rebot_voice")
public class PlatformVoice {

	private int id=0;
	@Id
	private String code="";

	private String puId="";

	private String sceenId="";

	private String state="";

	private String floor="";

	private String roomName="";

	/**
	 * 场景or 设备
	 */

	private String type="device/scene";

	private String deamonCuId="";

	private String parentId="";

	private String operation="";

	private String encode="";

	private String control="";

	private String name="";

	private String parameter="null";

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getControl() {
		return control;
	}

	public void setControl(String control) {
		this.control = control;
	}

	public String getEncode() {
		return encode;
	}

	public void setEncode(String encode) {
		this.encode = encode;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public String getDeamonCuId() {
		return deamonCuId;
	}

	public void setDeamonCuId(String deamonCuId) {
		this.deamonCuId = deamonCuId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPuId() {
		return puId;
	}

	public void setPuId(String puId) {
		this.puId = puId;
	}

	public String getCode() {
		return code;
	}


	public void setCode(String code) {
		this.code = code;
	}


	public String getSceenId() {
		return sceenId;
	}


	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getFloor() {
		return floor;
	}

	public void setFloor(String floor) {
		this.floor = floor;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public void setSceenId(String sceenId) {
		this.sceenId = sceenId;
	}

	@Override
	public String toString() {
		return "PlatformVoice{" +
				"id=" + id +
				", code='" + code + '\'' +
				", puId='" + puId + '\'' +
				", sceenId='" + sceenId + '\'' +
				", type='" + type + '\'' +
				", deamonCuId='" + deamonCuId + '\'' +
				", parentId='" + parentId + '\'' +
				", operation='" + operation + '\'' +
				", encode='" + encode + '\'' +
				", control='" + control + '\'' +
				", name='" + name + '\'' +
				'}';
	}
}
