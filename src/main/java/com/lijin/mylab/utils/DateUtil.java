package com.lijin.mylab.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.time.DateUtils;

import com.lijin.mylab.exception.BizzException;

public class DateUtil extends DateUtils {
 
	public static final String DATE_FORMAT_6 = "yyyyMM";
	public static final String DATE_FORMAT_8 = "yyyyMMdd";
	public static final String DATE_FORMAT_10 = "yyyy-MM-dd";
	public static final String DATE_FORMAT_14 = "yyyyMMddHHmmss";
	public static final String DATE_FORMAT_16 = "yyyy-MM-dd HH:mm";
	public static final String DATE_FORMAT_17 = "yyyyMMdd HH:mm:ss";
	public static final String DATE_FORMAT_19 = "yyyy-MM-dd HH:mm:ss";
	
	public static final String DATE_FORMAT_COOKIE = "EEE, dd-MMM-yyyy HH:mm:ss 'GMT'";
	private static final String TIME_STR_FMT_6 = "HHmmss";
	private static final String DATE_STR_FMT_14 = "yyyyMMddHHmmss";

	public static String dateToStr8(Date date) {
		AssertUtil.objIsNotNull(date, "日期参数为null");
		return formatDate(date, DATE_FORMAT_8);
	}
	
	public static String dateToStr17(Date date) {
		AssertUtil.objIsNotNull(date, "日期参数为null");
		return formatDate(date, DATE_FORMAT_17);
	}
	
	public static String dateToStr19(Date date) {
		AssertUtil.objIsNotNull(date, "日期参数为null");
		return formatDate(date, DATE_FORMAT_19);
	}

	public static String timeToStr6(Date date) {
		AssertUtil.objIsNotNull(date, "日期参数为null");
		DateFormat df = new SimpleDateFormat(TIME_STR_FMT_6);
		return df.format(date);
	}

	public static String dateToStr14(Date date) {
		AssertUtil.objIsNotNull(date, "日期参数为null");
		DateFormat df = new SimpleDateFormat(DATE_STR_FMT_14);
		return df.format(date);
	}
	
	public static String timeToStrMillionSeconds(Date date) {
		AssertUtil.objIsNotNull(date, "日期参数为null");
		DateFormat df = new SimpleDateFormat(TIME_STR_FMT_6);
		String returnStr = df.format(date).concat("_").concat(String.valueOf(date.getTime()));//毫秒		
		return returnStr;
	}

	public static String formatDateStr(String dateStr){
	    SimpleDateFormat sf1 = new SimpleDateFormat("yyyy-MM-dd");
	    SimpleDateFormat sf2 = new SimpleDateFormat("yyyyMMdd");
	    String parseStr = "";
	    try {
	    	parseStr = sf2.format(sf1.parse(dateStr));
		} catch (ParseException e) {
			throw new RuntimeException("日期格式转换出错", e);
		}
	    return parseStr;
	}
	
	public static Date str8ToDate(String dateStr) {
		AssertUtil.strIsNotBlank(dateStr, "日期字符串为空");
		Date dt = new Date();
		try {
			SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
			dt = sf.parse(dateStr);
		} catch (Exception e) {
			throw new IllegalArgumentException("解析日期字符串失败" + dateStr);
		}
		return dt;
	}
	
	public static String formatDate(Date date, String format) {
		AssertUtil.objIsNull(date, "日期对象为null");
		if (StringUtil.isEmpty(format)) {
			DateFormat df = new SimpleDateFormat(DATE_FORMAT_10);
			return df.format(date);
		} else {
			return new SimpleDateFormat(format).format(date);
		}		
	}
	
	public static String formatDate(Date date, DateFormat format) {
		AssertUtil.objIsNull(date, "日期对象为null");
		if (format == null) {
			DateFormat df = new SimpleDateFormat(DATE_FORMAT_10);
			return df.format(date);
		} else {
			return format.format(date);
		}
	}
	
	public static String now8() {
		return formatDate(new Date(), DATE_FORMAT_8);
	}
	
	public static String yesterday8() {
		return preDay(new Date());
	}
	
	public static String now17() {
		return formatDate(new Date(), DATE_FORMAT_17);
	}
	
	public static String now14() {
		return formatDate(new Date(), DATE_FORMAT_14);
	}
	
	public static String now19() {
		return formatDate(new Date(), DATE_FORMAT_19);
	}
	
	public static Date parseDate(String dateString, String format) {		
		try {
			DateFormat df = new SimpleDateFormat(format);
			return df.parse(dateString);
		} catch (ParseException e) {
			throw new BizzException(StringUtil.formatMessage("Parse date String \"{0}\" failed.", dateString), e);			 
		}
	}
	
	public static Date parseDate8(String dateStr8) {
		return parseDate(dateStr8, DATE_FORMAT_8);
	}
	
	/**
	 * 返回当前时间和传入时间的时间差（单位：分钟）
	 * @param lastTime
	 * @return
	 */
	public static long getDifferentMinute(Date lastTime){
		Date nowTime = new Date();
		long differentTime = nowTime.getTime() - lastTime.getTime();
		long min=differentTime/(1000*60);
		return min;
	}
	
	/**
	 * 求指定日期的下一天
	 * @param bh_date
	 * @return
	 */
	public static String nextDay(Date date) {
		AssertUtil.objIsNull(date, "参数不能为null");
		return changeDays(date, 1); 
	}

	/**
	 * 求指定日期的前一天
	 * @param date
	 * @return
	 */
	public static String preDay(Date date) {
		AssertUtil.objIsNull(date, "参数不能为null");
		return changeDays(date, -1);
	}
	

	
	/**
	 * 将制定的日期增加指定的天数
	 * @param date
	 * @param d
	 * @return
	 */
	public static String changeDays(Date date, int d) {
		SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT_8);
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DAY_OF_YEAR, d);
		return formatter.format(c.getTime());
	}
	
	public static int dateDiff(Date dt) {
		Date start = parseDate("20160101", "yyyyMMdd");
		return (int) ((dt.getTime() - start.getTime()) / (long) (1000 * 60 * 60 * 24));
	}
	
	public static void main(String[] args) {
//		System.out.println(parseDate8("20141112"));
//		Date now = new Date();
//		System.out.println(dateDiff(now) % 2);
//		for (int i = 0; i < 1000; i ++) {
//			Date d = parseDate(changeDays(now, i + 1), "yyyyMMdd");
//			if (dateDiff(d) % 2 > 0) {
//				System.out.println(dateDiff(d) % 2);
//			}
//			
//		}
//		
		System.out.println(formatDate(parseDate("20160101" + " 23:59:59", DATE_FORMAT_17), DATE_FORMAT_19));
	}
}
