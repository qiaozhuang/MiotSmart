package com.miot.android.robot.host.entity;

import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;

/**
 * Created by Administrator on 2017/1/9 0009.
 */
@Table(name = "model")
public class Model {

	private int id=0;
	@Id
	private String modelId="";
	/**
	 *
	 */
	private String toRefresh="";
	/**
	 *
	 */
	private String operationListToken="";


	private String operationList="";


	public String getModelId() {
		return modelId;
	}

	public void setModelId(String modelId) {
		this.modelId = modelId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getOperationList() {
		return operationList;
	}

	public void setOperationList(String operationList) {
		this.operationList = operationList;
	}

	public String getOperationListToken() {
		return operationListToken;
	}

	public void setOperationListToken(String operationListToken) {
		this.operationListToken = operationListToken;
	}

	public String getToRefresh() {
		return toRefresh;
	}

	public void setToRefresh(String toRefresh) {
		this.toRefresh = toRefresh;
	}
}
