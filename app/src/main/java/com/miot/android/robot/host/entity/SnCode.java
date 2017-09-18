package com.miot.android.robot.host.entity;

import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;

/**
 * Created by Administrator on 2017/1/24 0024.
 */
@Table(name="snCode_sql")
public class SnCode {

	private int id;

	@Id
	private String sn="";

	private String ipAddress="";

	private int  port= 38888;

	private String updateDate="";

	private boolean isSys=false;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isSys() {
		return isSys;
	}

	public void setSys(boolean sys) {
		isSys = sys;
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}


	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}
}
