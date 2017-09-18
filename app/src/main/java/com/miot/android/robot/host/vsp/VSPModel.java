package com.miot.android.robot.host.vsp;

import com.cncrit.qiaoqiao.VspOperation;
import com.google.gson.Gson;
import com.miot.android.robot.host.entity.Pu;
import com.miot.android.robot.host.webservice.WebServiceManager;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/11/15 0015.
 */
public class VSPModel implements VSPContract.Model{

	public String userReques(Map<String, Object> head, Map<String, Object> body) throws Exception {
		String json="";
		Gson gson=new Gson();
		Map<String,Object> request=new HashMap<>();
		if (head!=null){
			request.put("head",head);
		}
		head.put("reqTime",System.currentTimeMillis());
		if (body!=null){
			request.put("body",body);
		}
		json=gson.toJson(request);
		return json;
	}
	@Override
	public Observable<String> Login( final String username, final String password) {
		return Observable.create(new Observable.OnSubscribe<String>() {
			@Override
			public void call(Subscriber<? super String> subscriber) {
				VspOperation.LoginPu(username,password);
				subscriber.onNext("");
				subscriber.onCompleted();
			}

		}).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
	}

	@Override
	public Observable<String> ttContent( final int id, final String messgae) {
		return Observable.create(new Observable.OnSubscribe<String>() {
			@Override
			public void call(Subscriber<? super String> subscriber) {
				VspOperation.puToCuDevice(id, messgae, 1, 0);
				subscriber.onNext("");
				subscriber.onCompleted();
			}

		}).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
	}

	@Override
	public Observable<String> toPuttContent(final int id, final String messgae) {
		return Observable.create(new Observable.OnSubscribe<String>() {
			@Override
			public void call(Subscriber<? super String> subscriber) {
				VspOperation.puToPu(id, messgae, 1, 0);
				subscriber.onNext("");
				subscriber.onCompleted();
			}

		}).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
	}

	@Override
	public Observable<String> toCutContent(final int id,final String messgae) {

		return  Observable.create(new Observable.OnSubscribe<String>() {
			@Override
			public void call(Subscriber<? super String> subscriber) {
				VspOperation.puToCu(id, messgae, 1, 0);
				subscriber.onNext("");
				subscriber.onCompleted();
			}

		}).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
	}

	@Override
	public Observable<String> getHostAddress(final String url) {
		return Observable.create(new Observable.OnSubscribe<String>() {
			@Override
			public void call(Subscriber<? super String> subscriber) {
				try{
					InetAddress x = InetAddress.getByName(url);
					String ip_devdiv = x.getHostAddress();
					subscriber.onNext(ip_devdiv);
					subscriber.onCompleted();
				}
				catch (Exception e)
				{
					subscriber.onNext("");
					subscriber.onCompleted();
					e.printStackTrace();
				}
			}

		}).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
	}

	@Override
	public Observable<String> Logout() {
		return Observable.create(new Observable.OnSubscribe<String>() {
			@Override
			public void call(Subscriber<? super String> subscriber) {
				VspOperation.LogoutPu();
				subscriber.onNext("");
				subscriber.onCompleted();
			}

		}).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
	}

	@Override
	public Observable<String> webServiceResult(final String json) {
		return Observable.create(new Observable.OnSubscribe<String>() {
			@Override
			public void call(Subscriber<? super String> subscriber) {
				String result="";
				Map<String, Object> accessKey = new HashMap<>();
				accessKey.put("code", "checkUpdate");
				accessKey.put("accessKey", "");
				accessKey.put("accessToken", "");
				Map<String, Object> map2 = new HashMap<>();
				map2.put("baseFrameworkName", "807");
				map2.put("appFrameworkName", "0");
				map2.put("codecName", "0");

				map2.put("appFrameworkVersion", "0");
				map2.put("baseFrameworkVersion", json);
				map2.put("codecVersion", "0");
				try {
					 result=userReques(accessKey,map2);
				} catch (Exception e) {
					e.printStackTrace();
				}
//				String s=WebServiceApi.getInstance().getWebserviceResult(result);
//				subscriber.onNext(s);
				subscriber.onCompleted();
			}

		}).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
	}

	@Override
	public Observable<String> getPuList(final String puId,final String cuId) {
		return Observable.create(new Observable.OnSubscribe<String>() {
			@Override
			public void call(Subscriber<? super String> subscriber) {
				String result="";
				Map<String,Object> accessKey=new HashMap<String, Object>();
				accessKey.put("code", "reverseGetThings");
				accessKey.put("accessKey", "");
				accessKey.put("accessToken", "");
				Map<String,Object> body=new HashMap<>();
				body.put("puId", puId);
				body.put("cuId", cuId);
				try {
					result=userReques(accessKey,body);
				} catch (Exception e) {
					e.printStackTrace();
				}
				String s= WebServiceManager.getInstance().getWebserviceResult(result);
				subscriber.onNext(s);
				subscriber.onCompleted();
			}

		}).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
	}

	@Override
	public Observable<Map<String,Object>> getModelConfigUserData(final Pu pu, final String modeId, final String tocken) {
		return Observable.create(new Observable.OnSubscribe<Map<String,Object>>() {
			@Override
			public void call(Subscriber<? super Map<String,Object>> subscriber) {
				String result="";
				Map<String,Object> accessKey=new HashMap<String, Object>();
				accessKey.put("code", "getModelOperation");
				accessKey.put("accessKey", "");
				accessKey.put("accessToken", "");
				Map<String,Object> body=new HashMap<>();
				body.put("modelId", modeId);
				body.put("token", tocken);
				try {
					result=userReques(accessKey,body);
				} catch (Exception e) {
					e.printStackTrace();
				}
				Map<String,Object> s= WebServiceManager.getInstance().getModelConfigResult(pu,result);
				subscriber.onNext(s);
				subscriber.onCompleted();
			}

		}).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
	}

	@Override
	public Observable<String> getPuState(final String puId,final String cuId) {
		return Observable.create(new Observable.OnSubscribe<String>() {
			@Override
			public void call(Subscriber<? super String> subscriber) {
				String result="";
				Map<String,Object> accessKey=new HashMap<String, Object>();
				accessKey.put("code", "reverseGetThings");
				accessKey.put("accessKey", "");
				accessKey.put("accessToken", "");
				Map<String,Object> body=new HashMap<>();
				body.put("puId", puId);
				body.put("cuId", cuId);
				try {
					result=userReques(accessKey,body);
				} catch (Exception e) {
					e.printStackTrace();
				}
				String s= WebServiceManager.getInstance().getWebserviceResult(result);
				subscriber.onNext(s);
				subscriber.onCompleted();
			}

		}).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
	}

}
