package com.miot.android.robot.host.vsp;


import com.miot.android.robot.host.base.mvp.BaseModel;
import com.miot.android.robot.host.base.mvp.BasePresenter;
import com.miot.android.robot.host.base.mvp.BaseView;
import com.miot.android.robot.host.entity.Pu;

import java.util.Map;

import rx.Observable;

/**
 * Created by Administrator on 2016/11/15 0015.
 */
public interface VSPContract {


	public interface Model extends BaseModel {
		public Observable<String> Login(String username, String password);
		public Observable<String> ttContent( int id, String messgae);
		public Observable<String> toPuttContent( int id, String messgae);
		public Observable<String> toCutContent( int id, String messgae);
		public Observable<String> getHostAddress(String url);
		public Observable<String> Logout();
		public Observable<String> webServiceResult(String json);
		public Observable<String> getPuList(String puId,String cuId);
		public Observable<Map<String,Object>> getModelConfigUserData(Pu pu, String modeId, String tocken);
		public Observable<String> getPuState(String puId,String cuId);

	}

	public interface View extends BaseView {
		public void hostAddressRes(String ip);
		public void getPuList(String json);
		public void getPuState(String json);
		public void getModelConfigUserData(Pu pu,String json);
	}

	public abstract class Persenter extends BasePresenter<Model,View> {
		public abstract void Login(String username,String password);
		public abstract void ttContent(int id,String messgae);
		public abstract void ttCuContent(int id,String messgae);
		public abstract void getHostAddress(String url);
		public abstract void getPuList(String puId,String cuId);
		public abstract void getPuState(String puId,String cuId);
		public abstract void toPuttContent( int id, String messgae);
		public abstract void getModelConfigUserData(Pu pu, String modeId, String tocken);
		public abstract void Logout();

	}
}
