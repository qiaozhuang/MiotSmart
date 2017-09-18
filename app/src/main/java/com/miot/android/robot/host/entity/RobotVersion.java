package com.miot.android.robot.host.entity;

/**
 * Created by Administrator on 2016/11/7 0007.
 */
public class RobotVersion {


	private String VERSION="";
	private String SEARCH="";
	private ROBOTINFI ROBOTINFO=null;

	public ROBOTINFI getROBOTINFO() {
		return ROBOTINFO;
	}

	public void setROBOTINFO(ROBOTINFI ROBOTINFO) {
		this.ROBOTINFO = ROBOTINFO;
	}

	public String getSEARCH() {
		return SEARCH;
	}
	public void setVERSION(String VERSION) {
		this.VERSION = VERSION;
	}
	public String getVERSION() {
		return VERSION;
	}
	public void setSEARCH(String SEARCH) {
		this.SEARCH = SEARCH;
	}

	@Override
	public String toString() {
		return "RobotVersion{" +
				"VERSION='" + VERSION + '\'' +
				", SEARCH='" + SEARCH + '\'' +
				", ROBOTINFO=" + ROBOTINFO +
				'}';
	}
}
