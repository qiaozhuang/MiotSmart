package com.miot.android.robot.host.base;

import android.app.Service;
import android.content.Context;
import android.content.Intent;

import com.google.gson.Gson;
import com.miot.android.robot.host.base.mvp.BaseModel;
import com.miot.android.robot.host.base.mvp.BasePresenter;
import com.miot.android.robot.host.utils.RxManager;

/**
 * Created by Administrator on 2017/1/6 0006.
 */
public abstract class BaseVspService <T extends BasePresenter,E extends BaseModel> extends Service {

	protected Context context=null;

	public T mPresenter = null;

	public Gson gson=new Gson();

	public E mModel = null;
	@Override
	public void onCreate() {
		super.onCreate();
		context=this;
		mPresenter = RxManager.getT(this, 0);
		mModel = RxManager.getT(this, 1);
		this.initPresenter();
	}

	public abstract void initPresenter();

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		return super.onStartCommand(intent, flags, startId);
	}


}

