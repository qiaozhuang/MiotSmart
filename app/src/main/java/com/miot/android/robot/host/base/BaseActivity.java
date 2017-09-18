package com.miot.android.robot.host.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.google.gson.Gson;
import com.miot.android.robot.host.base.mvp.BaseModel;
import com.miot.android.robot.host.base.mvp.BasePresenter;
import com.miot.android.robot.host.utils.RxManager;

/**
 * Created by Administrator on 2016/11/7 0007.
 */
public abstract class BaseActivity<T extends BasePresenter,E extends BaseModel> extends AppCompatActivity{

	protected Context context=null;

	protected Gson gson=new Gson();

	public T mPresenter = null;
	public E mModel = null;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context=this;
		mPresenter = RxManager.getT(this, 0);
		mModel = RxManager.getT(this, 1);
		this.initPresenter();
	}

	public abstract void initPresenter();

}
