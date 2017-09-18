package com.miot.android.robot.host.base;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.miot.android.robot.host.base.mvp.BaseModel;
import com.miot.android.robot.host.base.mvp.BasePresenter;
import com.miot.android.robot.host.utils.RxManager;
import com.miot.android.robot.host.utils.SharedPreferencesUtil;

/**
 * Created by Administrator on 2016/11/22 0022.
 */
public abstract class BaseService<T extends BasePresenter,E extends BaseModel> extends Service {


	protected Context context=null;
	public T mPresenter = null;
	public E mModel = null;
	public SharedPreferencesUtil sharedPreferencesUtil=null;
	@Override
	public void onCreate() {
		super.onCreate();
		context=this;
		mPresenter = RxManager.getT(this, 0);
		mModel = RxManager.getT(this, 1);
		sharedPreferencesUtil=SharedPreferencesUtil.getInstance(this);
		this.initPresenter();
	}

	public abstract void initPresenter();

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		return super.onStartCommand(intent, flags, startId);
	}

	@Nullable
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
}
