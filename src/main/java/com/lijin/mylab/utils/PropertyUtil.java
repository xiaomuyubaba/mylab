package com.lijin.mylab.utils;

import java.util.Properties;

public final class PropertyUtil {

	public static Properties getProperties(String resource, Object caller) {
		Class<?> clazz = caller == null ? null : caller.getClass();
		return getProperties(resource, clazz);
	}

	public static Properties getProperties(String resource, Class<?> clazz) {
		ClassLoader loader = clazz == null ? null : clazz.getClassLoader();
		return ResourceUtil.getResourceAsProperty(resource, loader);
	}

	public static String readString(Properties props, String key) {
		return props.getProperty(key);
	}

	public static int readInteger(Properties props, String key) {
		return Integer.valueOf(readString(props, key));
	}

	public static boolean readBoolean(Properties props, String key) {
		return Boolean.valueOf(readString(props, key));
	}
	
	public static long readLong(Properties props, String key) {
		return Long.valueOf(readString(props, key));
	}
	
	public static float readFloat(Properties props, String key) {
		return Float.parseFloat(readString(props,key));
	}

}
