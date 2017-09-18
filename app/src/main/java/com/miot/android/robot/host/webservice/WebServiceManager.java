package com.miot.android.robot.host.webservice;

import android.util.Log;

import com.miot.android.robot.host.entity.Pu;
import com.miot.android.robot.host.utils.FormatConsts;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/1/9 0009.
 */
public class WebServiceManager {

	public static WebServiceManager instance=null;

	private WebService webService=null;

	public static WebServiceManager getInstance() {
		if (instance==null){
			synchronized (WebServiceManager.class){
				if (instance==null){
					instance=new WebServiceManager();
				}
			}
		}
		return instance;
	}


	private WebServiceManager(){
		webService=new WebService(FormatConsts.NAMESPACE,FormatConsts.ENDPOINT);
	}

	public String getWebserviceResult(String json){
		if (webService==null){
			webService=new WebService(FormatConsts.NAMESPACE,FormatConsts.ENDPOINT);
		}
		String methodName = "service";
		Object[][] params = new Object[][] {
				new Object[] {
						"request",
						json },
		};
		String s = webService.call(methodName, params);
		return s;
	}

	public Map<String,Object> getModelConfigResult(Pu pu, String json){
		Map<String,Object> map=new HashMap<>();
		if (webService==null){
			webService=new WebService(FormatConsts.NAMESPACE,FormatConsts.ENDPOINT);
		}
		Log.e("json",json);
		String methodName = "service";
		Object[][] params = new Object[][] {
				new Object[] {
						"request",
						json },
		};

		String s = webService.call(methodName, params);
		map.put("request",s);
		map.put("pu",pu);
		return map;
	}
}
