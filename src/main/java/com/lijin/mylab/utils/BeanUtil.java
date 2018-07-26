package com.lijin.mylab.utils;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;

import com.lijin.mylab.exception.BizzException;

public class BeanUtil extends BeanUtils {
	
	static {
		ConvertUtils.register(new Converter() {
			@SuppressWarnings({ "unchecked", "rawtypes" })
			@Override
			public Object convert(Class clazz, Object value) {
				if (value == null) {
					return null;
				} else {
					if (clazz == java.lang.String.class) {
						if (value instanceof java.util.Date) {
							return value != null ? DateUtil.dateToStr19((Date) value) : "";
						} else {
							return value.toString();
						}
					} else {
						throw new RuntimeException("couldnot " + value + "to" + clazz.getName());
					}
				}
			}
		}, java.lang.String.class);
	}

	@SuppressWarnings("rawtypes")
	public static Map<String, String> desc(Object o, Set<String> ignoreFields, Set<String> containFields) {
		try {
			Map m = BeanUtils.describe(o);
			if (m != null && !m.isEmpty()) {
				Map<String, String> result = new HashMap<String, String>();
				for (Object k : m.keySet()) {
					String field = StringUtil.valueOf(k);
					if (containFields != null 
							&& containFields.size() > 0 
							&& !containFields.contains(field)) {
						continue;
					}
					if (ignoreFields != null 
							&& ignoreFields.size() > 0 
							&& ignoreFields.contains(field)) {
						continue;
					}
					if ("class".equals(field)) {
						continue;
					}
					result.put(field, StringUtil.valueOf(m.get(k)));
				}
				return result;
			}
			return Collections.emptyMap();
		} catch (Exception e) {
			throw new BizzException("转换对象失败", e);
		}
	}
	
	public static void cloneEntity(Object src, Object dest, String[] includeFields) {
		AssertUtil.objIsNull(src, "src obj is null.");
		AssertUtil.objIsNull(dest, "dest obj is null.");
		if (!src.getClass().equals(dest.getClass())) {
			throw new BizzException("src和dest不是同一种类.");
		}
		
		Set<String> includeFieldSet = new HashSet<String>();
		if (includeFields != null && includeFields.length > 0) {
			for (String f : includeFields) {
				includeFieldSet.add(f);
			}
		}
		
		Class<? extends Object> clazz = src.getClass();
		Method[] methods = clazz.getMethods();
		for (Method m : methods) {
			String mNm = m.getName();
			try {
				if (!mNm.equals("getClass") && mNm.startsWith("get") && mNm.length() > 3) {
					String field = mNm.substring(3, 4).toLowerCase() + (mNm.length() >= 4 ? mNm.substring(4) : "");
					boolean setFlg = false;
					if (includeFieldSet != null && includeFieldSet.size() > 0) {
						if (includeFieldSet.contains(field)) {
							setFlg = true;
						}
					} else {
						setFlg = true;
					}
					if (setFlg) {
						Object val = m.invoke(src, new Object[] {});
						copyProperty(dest, field, val);
					}
				} else {
					continue;
				}
			} catch (Exception e) {
				throw new BizzException("调用方法失败:" + mNm, e);
			}
		}
	}
	
	public static void main(String[] args) {
		TestBean tb = new TestBean();
		tb.setAge(100);
		tb.setBd(new BigDecimal("12.31"));
		tb.setDt(new Date());
		tb.setNm("xxx");
		System.out.println(desc(tb, null, null));
		
		TestBean tb2 = new TestBean();
		System.out.println(desc(tb2, null, null));
		cloneEntity(tb, tb2, null);
		System.out.println(desc(tb2, null, null));
	}
	
	public static class TestBean {
		String nm;
		Date dt;
		int age;
		BigDecimal bd;
		
		public String getNm() {
			return nm;
		}
		public void setNm(String nm) {
			this.nm = nm;
		}
		public Date getDt() {
			return dt;
		}
		public void setDt(Date dt) {
			this.dt = dt;
		}
		public BigDecimal getBd() {
			return bd;
		}
		public void setBd(BigDecimal bd) {
			this.bd = bd;
		}
		public int getAge() {
			return age;
		}
		public void setAge(int age) {
			this.age = age;
		}
	}
}
