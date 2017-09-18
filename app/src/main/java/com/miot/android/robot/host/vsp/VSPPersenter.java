package com.miot.android.robot.host.vsp;

import com.miot.android.robot.host.entity.Pu;

import java.util.Map;

import rx.functions.Action1;

/**
 * Created by Administrator on 2016/11/15 0015.
 */
public class VSPPersenter extends VSPContract.Persenter {
	@Override
	public void Login( String username, String password) {
		mModel.Login(username,password).subscribe(new Action1<String>() {
			@Override
			public void call(String s) {

			}
		});
	}
	@Override
	public void ttContent( int id, String messgae) {
		mModel.ttContent(id,messgae).subscribe(new Action1<String>() {
			@Override
			public void call(String s) {

			}
		});
	}

	@Override
	public void ttCuContent(int id, String messgae) {
		mModel.toCutContent(id,messgae).subscribe(new Action1<String>() {
			@Override
			public void call(String s) {

			}
		});
	}

	@Override
	public void getHostAddress(String url) {
		mModel.getHostAddress(url).subscribe(new Action1<String>() {
			@Override
			public void call(String s) {
				mView.hostAddressRes(s);
			}
		}, new Action1<Throwable>() {
			@Override
			public void call(Throwable throwable) {
				mView.hostAddressRes("");
			}
		});
	}

	@Override
	public void getPuList(String puId, String cuId) {

		mModel.getPuList(puId,cuId).subscribe(new Action1<String>() {
			@Override
			public void call(String s) {
				mView.getPuList(s);
			}
		}, new Action1<Throwable>() {
			@Override
			public void call(Throwable throwable) {
				mView.getPuList("");
			}
		});
	}

	@Override
	public void getPuState(String puId, String cuId) {
		mModel.getPuState(puId,cuId).subscribe(new Action1<String>() {
			@Override
			public void call(String s) {
				mView.getPuState(s);
			}
		}, new Action1<Throwable>() {
			@Override
			public void call(Throwable throwable) {
				mView.getPuList("");
			}
		});
	}

	@Override
	public void toPuttContent(int id, String messgae) {
		mModel.toPuttContent(id,messgae).subscribe(new Action1<String>() {
			@Override
			public void call(String s) {

			}
		}, new Action1<Throwable>() {
			@Override
			public void call(Throwable throwable) {
				mView.getPuList("");
			}
		});
	}

	@Override
	public void getModelConfigUserData(Pu pu, String modeId, String tocken) {
		mModel.getModelConfigUserData(pu,modeId,tocken).subscribe(new Action1<Map<String,Object>>() {
			@Override
			public void call(Map<String,Object> map) {
				try {
					mView.getModelConfigUserData((Pu)map.get("pu"),map.get("request").toString());
				}catch (Exception e){
					mView.getModelConfigUserData(null,"");
				}
			}
		}, new Action1<Throwable>() {
			@Override
			public void call(Throwable throwable) {
				mView.getModelConfigUserData(null,"");
			}
		});
	}


	@Override
	public void Logout() {
		mModel.Logout().subscribe(new Action1<String>() {
			@Override
			public void call(String s) {

			}
		});
	}
	@Override
	public void onStart() {

	}
}
