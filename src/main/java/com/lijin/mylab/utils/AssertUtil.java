package com.lijin.mylab.utils;

import java.util.Collection;
import java.util.Map;

import com.lijin.mylab.exception.BizzException;


public class AssertUtil {
	

	/**
	 * 对象如果是null则抛运行时异常，异常信息可以自己指定
	 * @param o
	 * @param msg
	 */
	public static void objIsNotNull(Object o, String msg) {
		if (o == null) {
			if (StringUtil.isBlank(msg)) {
				msg = "对象为null";
			}
			throw new BizzException(msg);
		}
	}
	
	/**
	 * 对象如果不是null则抛运行时异常，异常信息可以自己指定
	 * @param o
	 * @param msg
	 */
	public static void objIsNull(Object o, String msg) {
		if (o != null) {
			if (StringUtil.isBlank(msg)) {
				msg = "对象不为null";
			}
			throw new BizzException(msg);
		}
	}
	
	/**
	 * 验证对象是否为null，如果是null，则抛运行时异常，异常信息可以自己指定
	 * @param o
	 * @param msg
	 */
	public static void strIsNotBlank(String str, String msg) {
		if (StringUtil.isBlank(str)) {
			if (StringUtil.isBlank(msg)) {
				msg = "字符串为空: " + str;
			}
			throw new BizzException(msg);
		}
	}
	
	public static void arrIsNotEmpty(Object[] arr, String msg) {
		objIsNull(arr, "obj is null");
		if (arr.length == 0) {
			if (StringUtil.isBlank(msg)) {
				msg = "数组为空";
			}
			throw new BizzException(msg);
		}
	}
	
	public static void collectionIsNotEmpty(Collection<?> lst, String msg) {
		if (lst == null || lst.size() == 0) {
			if (StringUtil.isBlank(msg)) {
				msg = "集合为空";
			}
			throw new BizzException(msg);
		}
	}
	
	public static void mapIsNotEmpty(Map<?, ?> mp, String msg) {
		if (mp == null || mp.size() == 0) {
			if (StringUtil.isBlank(msg)) {
				msg = "Map为空";
			}
			throw new BizzException(msg);
		}
	}
	
	/**
	 * 数组不包含
	 * @param arr
	 * @param ojb
	 * @param msg
	 */
	public static void arrNotContains(Object[] arr, Object obj, String msg) {
		objIsNull(arr, "arr is null");
		arrIsNotEmpty(arr, "arr is empty");
		objIsNull(obj, "obj is null");
		
		boolean b = false;
		for (Object o : arr) {
			if (o.equals(obj)) {
				b = true;
			}
		}
		if (!b) {
			if (StringUtil.isBlank(msg)) {
				msg = "数组不包含该对象:" + obj;
			}
			throw new BizzException(msg);
		}
	}
	
	/**
	 * 两个字符串不相等
	 * @param s1
	 * @param s2
	 * @param msg
	 */
	public static void strNotEquals(String s1, String s2, String msg) {
		strIsNotBlank(s1, "s1 is blank");
		strIsNotBlank(s2, "s2 is blank");
		if (!s1.equals(s2)) {
			if (StringUtil.isBlank(msg)) {
				throw new BizzException("s1 is not equals to s2");
			} else {
				throw new BizzException(msg);
			}
		}
	}
	
	/**
	 * 两个字符串相等
	 * @param s1
	 * @param s2
	 * @param msg
	 */
	public static void strEquals(String s1, String s2, String msg) {
		strIsNotBlank(s1, "s1 is blank");
		strIsNotBlank(s2, "s2 is blank");
		if (s1.equals(s2)) {
			if (StringUtil.isBlank(msg)) {
				throw new BizzException("s1 is not equals to s2");
			} else {
				throw new BizzException(msg);
			}
		}
	}
	
	/**
	 * 字符串不是月份
	 * @param mon
	 */
	public static void notMonStr(String mon) {
		strIsNotBlank(mon, "mon is blank.");
		try {
			if (mon.length() != 2 || Integer.parseInt(mon) < 1 || Integer.parseInt(mon) > 12) {
				throw new BizzException("mon is not corrent:" + mon);
			}
		} catch (Exception e) {
			throw new BizzException("mon is not corrent:" + mon);
		}
	}
	
	public static void notAmtStr(String amt, String msg) {
		strIsNotBlank(amt, "amt is blank.");
		if (!StringUtil.matchRegex(amt, StringUtil.AMT_REGEX)) {
			if (!StringUtil.isEmpty(msg)) {
				throw new BizzException(msg + ":" + amt);
			} else {
				throw new BizzException("请输入合法的金额字符串:" + amt);
			}
		}
	}
	
	public static void notDt8Str(String dt) {
		strIsNotBlank(dt, "dt is blank.");
		try {
			DateUtil.parseDate(dt, DateUtil.DATE_FORMAT_8);
		} catch (Exception e) {
			throw new BizzException("请输入合法的8位日期字符串:" + dt);
		}
	}
	
	public static void intIsZero(int i, String msg) {
		if (i == 0) {
			if (StringUtil.isBlank(msg)) {
				throw new BizzException("i is zero.");
			} else {
				throw new BizzException(msg);
			}
		}
	}
	
	public static void main(String[] args) {
//		String[] strs = new String[]{"01", "02"};
//		arrNotContains(strs, "01", "取值非法");
//		arrNotContains(strs, "02", "取值非法");
//		arrNotContains(strs, "03", "取值非法");
		
//		notMonStr("12");
		notAmtStr("123.1", "金额输入错误");
		notAmtStr("123.12", "金额输入错误");
		notAmtStr("123.123", "金额输入错误");
	}
}
