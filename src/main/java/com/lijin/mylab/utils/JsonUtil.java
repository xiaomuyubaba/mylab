package com.lijin.mylab.utils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;


public class JsonUtil {

	private static final Logger log = Logger.getLogger(JsonUtil.class);
	private static ObjectMapper mapper = new ObjectMapper();

	/**
	 * Object to JSON
	 * 
	 * @param obj
	 * @return String
	 */
	public static <T> String toJson(T obj) {
		try {
			return mapper.writeValueAsString(obj);
		} catch (Exception e) {
			log.error("write to json string error:" + obj, e);
			return null;
		}
	}

	/**
	 * JSON to Object
	 * 
	 * @param jsonString
	 * @param clazz
	 * @return <T> T
	 */
	public static <T> T fromJson(String jsonString, Class<T> clazz) {
		try {
			return mapper.readValue(jsonString, clazz);
		} catch (Exception e) {
			log.error("parse json string error:" + jsonString, e);
			return null;
		}
	}
	
	public static void main(String[] args) {
		System.out.println(fromJson("",Map.class));
//		TestBean tb = new TestBean();
//		tb.setAge(100);
//		tb.setBd(new BigDecimal("12.31"));
//		tb.setNm("xxx");
//		tb.setDt(new Date());
//		String jstr = toJson(tb);
//		System.out.println(BeanUtil.desc(tb, null, null));
//		
//		TestBean tb2 = new TestBean();
//		System.out.println(toJson(tb2));
//		tb2 = fromJson(jstr, TestBean.class);
//		tb2.setDt(new Date(Long.valueOf("1425004186272")));
//		System.out.println(toJson(tb2));
//		System.out.println(BeanUtil.desc(tb2, null, null));
//		
//		Map<String, Object> m = new LinkedHashMap<String, Object>();
//		m.put("oldObj", tb);
//		m.put("newObj", tb2);
//		String str = toJson(m);
//		System.out.println(str);
//		
//		Map<String, Object> m2 = fromJson(str, Map.class);
//		System.out.println(m2.get("oldObj").getClass());
//		System.out.println("m2-->" + m2.get("newObj").getClass());
//		Map<String, String> oldObj = (Map<String, String>) m2.get("oldObj");
//		TestBean t1 = fromJson(toJson(oldObj), TestBean.class);
//		System.out.println(BeanUtil.desc(t1, null, null));
//		Map<String, String> newObj = (Map<String, String>) m2.get("newObj");
//		TestBean t2 = fromJson(toJson(newObj), TestBean.class);
//		System.out.println(BeanUtil.desc(t2, null, null));
//		
//		AjaxResult rst = new AjaxResult();
//		rst.setRespCode(AjaxRespEnums.ERROR.getRespCode());
//		rst.setRespMsg(AjaxRespEnums.ERROR.getRespMsg());
//		System.out.println(toJson(rst));
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
