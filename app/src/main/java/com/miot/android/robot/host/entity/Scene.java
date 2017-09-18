package com.miot.android.robot.host.entity;

import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;

/**
 * Created by Administrator on 2017/1/9 0009.
 */
@Table(name="scene")
public class Scene {

	@Id
	private String id="SA_001";

	private String name="回家模式";

	private String deamonCuId="1";

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public String getDeamonCuId() {
		return deamonCuId;
	}

	public void setDeamonCuId(String deamonCuId) {
		this.deamonCuId = deamonCuId;
	}
}
