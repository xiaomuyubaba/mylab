package com.lijin.mylab.utils;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;

import com.lijin.mylab.exception.BizzException;

public class EnumUtil {

	@SuppressWarnings("rawtypes")
	public static String translate(Class clazz, String code, boolean withCode) {
		try {
			Enum[] enums = (Enum[]) clazz.getEnumConstants();
			for (Enum e : enums) {
				Method m = e.getClass().getMethod("getCode", new Class[]{});
				String cd = (String) m.invoke(e, new Object[]{});
				if (cd.equals(code)) {
					Method m2 = e.getClass().getMethod("getDesc", new Class[]{});
					Object val = m2.invoke(e, new Object[]{});
					if (withCode) {
						return val == null ? code : (code + "-" + String.valueOf(val));
					} else {
						return val == null ? code : String.valueOf(val);
					}
				}
			}
			return code;
		} catch (Exception e) {
			throw new BizzException("枚举值转义失败:" + code, e);
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T> T parseEnumByCode(Class clazz, String code) {
		AssertUtil.strIsNotBlank(code, "code is blank.");
		try {
			Enum[] enums = (Enum[]) clazz.getEnumConstants();
			for (Enum e : enums) {
				Method m = e.getClass().getMethod("getCode", new Class[]{});
				String cd = (String) m.invoke(e, new Object[]{});
				if (cd.equals(code)) {
					return (T) e;
				}
			}
		} catch (Exception e) {
			throw new BizzException("枚举类查找失败:" + code, e);
		}
		throw new BizzException("未找到枚举类:" + code);
	}
	
	@SuppressWarnings({ "rawtypes" })
	public static Map<String, String> enumMap(Class clazz, boolean withCode) {
		Map<String, String> mp = new LinkedHashMap<String, String>();
		try {
			Enum[] enums = (Enum[]) clazz.getEnumConstants();
			for (Enum e : enums) {
				Method m = e.getClass().getMethod("getCode", new Class[]{});
				String cd = (String) m.invoke(e, new Object[]{});
				Method m2 = e.getClass().getMethod("getDesc", new Class[]{});
				Object val = m2.invoke(e, new Object[]{});
				mp.put(cd, withCode ? cd + "-" + StringUtil.valueOf(val) : StringUtil.valueOf(val));
			}
		} catch (Exception e) {
			throw new BizzException("枚举转map失败", e);
		}
		return mp;
	}
	
	@SuppressWarnings({ "rawtypes" })
	public static int parseIndx(Class clazz, String code) {
		try {
			Enum[] enums = (Enum[]) clazz.getEnumConstants();
			for (int i = 0; i < enums.length; i ++) {
				Enum e = enums[i];
				Method m = e.getClass().getMethod("getCode", new Class[]{});
				String cd = (String) m.invoke(e, new Object[]{});
				if (cd.equals(code)) {
					return i;
				}
			}
		} catch (Exception e) {
			throw new BizzException("获取枚举序号失败:" + code, e);
		}
		return -1;
	}
	
	@SuppressWarnings({ "rawtypes" })
	public static Class findEnum(String enumPackage, String enumNm) {
		AssertUtil.strIsNotBlank(enumPackage, "enumPackage is blank.");
		AssertUtil.strIsNotBlank(enumNm, "enumNm is blank.");
		
		if (enumNm.contains(".")) {
			enumNm = enumNm.replace(".", "$");
		}
		if (!enumPackage.endsWith(".")) {
			enumPackage = enumPackage + ".";
		}
		
		String fullNm = enumPackage + enumNm;
		try {
			return Class.forName(fullNm);
		} catch (Exception e) {
			throw new BizzException("查找枚举类失败:" + fullNm, e);
		}
	}
	
	@SuppressWarnings({ "rawtypes" })
	public static String buildCtrlRule(Class clazz, String[] vals, String ctrlRuleDftVal) {
		if (vals == null || vals.length == 0) {
			return ctrlRuleDftVal;
		}
		char[] cs = ctrlRuleDftVal.toCharArray();
		for (String v : vals) {
			int idx = parseIndx(clazz, v);
			cs[idx] = '1';
		}
		return new String(cs);
	}
	
	/**
	 * 解析控制位字符串
	 * @param clazz
	 * @param ctrlRule
	 * @param withCode
	 * @return
	 */
	@SuppressWarnings({ "rawtypes" })
	public static String descCtrlRule(Class clazz, String ctrlRule, boolean withCode) {
		try {
			StringBuilder result = new StringBuilder();
			Enum[] enums = (Enum[]) clazz.getEnumConstants();
			char[] cs = ctrlRule.toCharArray();
			for (int i = 0; i < cs.length; i ++) {
				if (cs[i] == '1') {
					Enum e = enums[i];
					if (withCode) {
						Method m1 = e.getClass().getMethod("getCode", new Class[]{});
						String cd = (String) m1.invoke(e, new Object[]{});
						result.append(cd + "-");
					}
					Method m2 = e.getClass().getMethod("getDesc", new Class[]{});
					String desc = (String) m2.invoke(e, new Object[]{});
					result.append(desc + ",");
				}
			}
			String str = result.toString();
			if (str.endsWith(",")) {
				return str.substring(0, str.lastIndexOf(","));
			} else {
				return str;
			}
		} catch (Exception e) {
			throw new BizzException("解析控制位字符串失败");
		}
		
	}
}
