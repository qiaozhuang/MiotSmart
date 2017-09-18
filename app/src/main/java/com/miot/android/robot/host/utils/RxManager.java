package com.miot.android.robot.host.utils;

import java.lang.reflect.ParameterizedType;

/**
 * Created by Administrator on 2016/11/8 0008.
 */
public class RxManager {

	public static <T> T getT(Object o, int i) {
		try {
			return ((Class<T>) ((ParameterizedType) (o.getClass()
					.getGenericSuperclass())).getActualTypeArguments()[i])
					.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassCastException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Class<?> forName(String className) {
		try {
			return Class.forName(className);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
}
