package com.miot.android.robot.host.entity;

import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;

/**
 * Created by Administrator on 2017/1/9 0009.
 */
@Table(name="pu")
public class Pu {

	@Id
	private String puId="";

	private String puName="";

	private String channel="";

	private String puNickname="";

	private String modelId="";

	private String state="";

	private String roomName="";

	private String roomId="";

	private String channels="";

	private String channelIndex="";

	private String parentId="";

	private String floorName="";

	public String getFloorName() {
		return floorName;
	}

	public void setFloorName(String floorName) {
		this.floorName = floorName;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getPuId() {
		return puId;
	}

	public void setPuId(String puId) {
		this.puId = puId;
	}

	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getModelId() {
		return modelId;
	}

	public void setModelId(String modelId) {
		this.modelId = modelId;
	}


	public String getPuNickname() {
		return puNickname;
	}

	public void setPuNickname(String puNickName) {
		this.puNickname = puNickName;
	}

	public void setPuName(String puName) {
		this.puName = puName;
	}

	public String getPuName() {
		return puName;
	}

	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getChannels() {
		return channels;
	}

	public void setChannels(String channels) {
		this.channels = channels;
	}

	public String getChannelIndex() {
		return channelIndex;
	}

	public void setChannelIndex(String channelIndex) {
		this.channelIndex = channelIndex;
	}

	@Override
	public String toString() {
		return "Pu{" +
				"puId='" + puId + '\'' +
				", puName='" + puName + '\'' +
				", channel='" + channel + '\'' +
				", puNickname='" + puNickname + '\'' +
				", modelId='" + modelId + '\'' +
				", state='" + state + '\'' +
				", roomName='" + roomName + '\'' +
				", roomId='" + roomId + '\'' +
				", parentId='" + parentId + '\'' +
				", floorName='" + floorName + '\'' +
				'}';
	}
}
