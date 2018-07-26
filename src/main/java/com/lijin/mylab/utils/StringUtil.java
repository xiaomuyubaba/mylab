package com.lijin.mylab.utils;

import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.ArrayUtils;

import com.lijin.mylab.exception.BizzException;

public class StringUtil extends org.apache.commons.lang.StringUtils {
	
	public static final String DEFAULT_SPLITER = ",";
	
	public static final String ZH_REGEX = "[\u4e00-\u9fa5]"; // 中文字符正则表达式
	public static final String URL_REGEX =	"^(http://){0,1}.+\\..+\\..+$"; // URL正则表达式
	public static final String EMAIL_REGEX = // EMAIL正则表达式 
		"\\b^['_a-z0-9-\\+]+(\\.['_a-z0-9-\\+]+)*@[a-z0-9-]+(\\.[a-z0-9-]+)*\\.([a-z]{2}|aero|arpa|asia|biz|com|coop|edu|gov|info|int|jobs|mil|mobi|museum|name|nato|net|org|pro|tel|travel|xxx)$\\b";
	public static final String NUM_REGEX = "^[0-9]+$"; // 整数正则表达式
	public static final String NUM_WORD_REGEX = "^[A-Za-z0-9]+$"; // 数字字符正则表达式
	public static final String MOBILE_REGEX = "^1\\d{10}$"; // 手机号正则表达式
	public static final String WORD_REGEX = "^[A-Za-z]+$"; // 字母正则表达式
	public static final String AMT_REGEX = "^(([1-9]\\d{0,9})|0)(\\.\\d{1,2})?$"; // 金额正则表达式
	public static final String HEX_REGEX = "^[0-9A-Fa-f]+$";
	public static final String PWD_REGEX = "^[A-Za-z0-9!@#$*()_+^&}{:?.]+$";
	public static final String UP_CHK_FILE_NM_REGEX = "^INN[0-9]{2}[0,1][0-9][0-3][0-9]{3}ZM_[0-9]{15}$";

	/**
	 * 将字符串数组trim后拼接成字符串，用默认的分隔符进行分隔
	 * @param strArray
	 * @return
	 */
	public static String strArrToString(String[] strArr) {
		AssertUtil.objIsNotNull(strArr, "参数strArr为null");
		return strArrToString(strArr, DEFAULT_SPLITER);
	}
	
	/**
	 * 将数组trim后拼接成字符串,用指定的分隔符进行分隔
	 * @param strArray
	 * @return
	 */
	public static String strArrToString(String[] strArr, String spliter) {
		AssertUtil.objIsNotNull(strArr, "参数strArr为null");
	
		// 如果未指定分隔符，或者指定的分隔符字符串为空，则用默认的分隔符进行分隔
		if (isBlank(spliter)) {
			spliter = DEFAULT_SPLITER;
		}
		
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < strArr.length; i ++) {
			sb.append(strArr[i].trim());
			if (i < strArr.length - 1) {
				sb.append(spliter);
			}
		}
		return sb.toString();
	}

	/**
	 * 将以下划线分隔的字符串按驼峰命令法进行转换
	 * @param strOrg
	 * @return
	 */
	public static String camelCase(String strOrg) {
		String[] list = StringUtil.lowerCase(strOrg).split("_");
		StringBuilder buf = new StringBuilder(strOrg.length());
		for(String word : list) {
			buf.append(word);
			int firstLetterIndex = buf.length()-word.length();
			if( firstLetterIndex != 0) {
				buf.setCharAt(firstLetterIndex, Character.toUpperCase(buf.charAt(firstLetterIndex)));
			}
		}
		return buf.toString();
	}

	/**
	 * 格式化输出msg
	 * @param msg
	 * @param args
	 * @return
	 */
	public static String formatMessage(String msg, Object... args) {
		if (isBlank(msg) || ArrayUtils.getLength(args) == 0) {
			return msg;
		} else {
			return MessageFormat.format(msg, args);
		}
	}
	
	/**
	 * 根据指定长度初始化CHAR_MAP
	 * @param length
	 * @return
	 */
	public static String initCharMap(int length) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length; i ++) {
			sb.append("0");
		}
		return sb.toString();
	}
	
	/**
	 * 将Set转成逗号分隔的字符串
	 * @param strSet
	 * @return
	 */
	public static String setToString(Set<String> strSet) {
		if (strSet != null && !strSet.isEmpty()) {
			return strArrToString(strSet.toArray(new String[] { }));
		}
		return "";
	}
	
	/**
	 * 将Set转成按指定字符分隔的字符串
	 * @param strSet
	 * @return
	 */
	public static String setToString(Set<String> strSet, String spliter) {
		if (strSet != null && !strSet.isEmpty()) {
			return strArrToString(strSet.toArray(new String[] { }), spliter);
		}
		return "";
	}
	
	/**
	 * 检查是否包含中文信息
	 * @param str
	 * @return
	 */
	public static boolean containsZhCn(String str) {
		if (isBlank(str)) {
			return false;
		}
		Matcher matcher = Pattern.compile(ZH_REGEX).matcher(str);
		return matcher.find();
	}
	
	/**
	 * 计算字符串长度，如果是中文，则要乘以3
	 * @param str
	 * @return
	 */
	public static int strLen(String str, String charSet) {
		int len = 0;
		if (containsZhCn(str)) {
			try {
				if (StringUtil.isBlank(charSet)) {
					charSet = "UTF-8";
				}
				len = str.getBytes(charSet).length;
			} catch (Exception e) {
				throw new IllegalStateException("获取字符串长度失败" + str);
			}
		} else {
			len = str.length();
		}
		return len;
	}
	
	
	/**
	 * 获取指定位值
	 * @param ctrl
	 * @param bitIdx
	 * @return
	 */
	public static String getValueOfBit(String ctrl, int bitIdx, String defaultBitValue) {
		if (StringUtil.isBlank(ctrl)) {
			return defaultBitValue;
		}
		if(bitIdx <0 || bitIdx >= ctrl.length() ){
			return defaultBitValue;
		}
		return ctrl.substring(bitIdx, bitIdx+1);
	}
	
	public static String strDefaultVal(String val, String defaultVal) {
		if (isBlank(defaultVal)) {
			defaultVal = "";
		}
		return isBlank(val) ? defaultVal : val;
	}
	
	public static Date strToDate(String str) {
		
		if(!isBlank(str)) {
			Date date = null;
			try
			{
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				date = sdf.parse(str);
			} catch (ParseException e){
				throw new IllegalStateException("日期字符串格式出错：" + str);
			}
			return date;
		}else {
			throw new IllegalStateException("日期字符串格式为空！");
		}
	}
	
	/**
	 * 将以元为单位的金额字符串转为以分为单位的bigdecimal
	 * @param str
	 * @return
	 */
	public static BigDecimal strToAmt(String str) {
		AssertUtil.strIsNotBlank(str, "str is blank.");
		BigDecimal bd = new BigDecimal(str);
		return bd.movePointRight(2);
	}
	
	/**
	 * Description: 将字符串由UTF-8转换成GBK编码格式
	 * @author QiTing  2014-5-12
	 * @param str
	 * @return
	 */
	public static String UTF8ToGBK(String str) {
		return new String(str.getBytes(), Charset.forName("GBK"));
	}
	
	/**
	 * 传入源字符串与期望截取的长度，按照GBK编码格式进行截取
	 * @param originStr
	 * @param truncateLength
	 * @return
	 */
	public static String truncateString(String originStr,int truncateLength){
		if(originStr.length() <= truncateLength/2) {
			return originStr;
		} else {
			int byteLength = originStr.getBytes(Charset.forName("GBK")).length;
			while(byteLength - truncateLength > 0) {
				
				int endPosition = originStr.length() - (int)Math.ceil(((float)(byteLength - truncateLength)/2));
				originStr = originStr.substring(0, endPosition);
				byteLength = originStr.getBytes(Charset.forName("GBK")).length;				
			}
			return originStr;
		}	
	}
	
	/**
	 * 字符串匹配正则表达式列表（20111102）
	 * @param srcStr
	 * @param regexArr
	 * @return 字符串能匹配一个正则表达式就返回true，否则返回false
	 */
	public static  boolean matchRegexs(String srcStr, String[] regexArr) {
		AssertUtil.strIsNotBlank(srcStr, "字符串为空");
		AssertUtil.objIsNull(regexArr, "正则表达式数组为null");
		for (String regex : regexArr) {
			if (srcStr.matches(regex)) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean matchRegex(String srcStr, String regex) {
		AssertUtil.strIsNotBlank(srcStr, "字符串为空");
		AssertUtil.strIsNotBlank(regex, "正则表达式为空");
		return srcStr.matches(regex);
	}
	
	/**
	 * 生成一个长度为length的随机密码，包含数字和字符
	 * @param length
	 * @return
	 */
	public static String randomPwd(int length) {
		if (length < 6) {
			throw new BizzException("密码长度不能小于六位");
		}
		StringBuilder pwd = new StringBuilder();
		String[] chars = new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
		Random rm = new Random(new Double(Math.random() * 10000 * 59).longValue());
		for (int i = 0; i < 4; i++) {
			String rand = chars[rm.nextInt(chars.length)];
			pwd.append(rand);
		}
		chars = new String[] { "a", "b", "c", "d", "e", "z", "w", "y", "o", "i", "q", "t",
				"A", "B", "D", "E", "F", "G", "H", "J", "K", "L", "M", "N",
				"P", "R", "S", "U", "V", "W", "X", "Y", "Z", "_" };
		for (int i = 0; i < length - 4; i++) {
			String rand = chars[rm.nextInt(chars.length)];
			pwd.append(rand);
		}
		return pwd.toString();
	}
	
	public static String concat(String spliter, String... strs) {
		StringBuilder sb = new StringBuilder();
		if (spliter == null) {
			spliter = ",";
		}
		if (strs != null && strs.length > 0) {
			for (int i = 0; i < strs.length; i ++) {
				sb.append(strs[i]);
				if (i < strs.length - 1) {
					sb.append(spliter);
				}
			}
		}
		return sb.toString();
	}
	
	public static String concatSet(String spliter, Set<String> st) {
		StringBuilder sb = new StringBuilder();
		if (spliter == null) {
			spliter = ",";
		}
		if (st != null && st.size() > 0) {
			int c = 0;
			for (String s : st) {
				sb.append(s);
				c ++;
				if (c < st.size()) {
					sb.append(spliter);
				}
			}
		}
		return sb.toString();
	}
	
	public static String mask(String sourceStr, int fromIndex, int toIndex, char val) {
		char[] sourceChars = sourceStr.toCharArray();
		Arrays.fill(sourceChars, fromIndex, toIndex, val);
		return new String(sourceChars);
	}
	
	public static String valueOf(Object o) {
		return o == null ? "" : String.valueOf(o);
	}
	
	public static String valueOf(Object o, String deftVal) {
		return o == null ? deftVal : String.valueOf(o);
	}
	
	public static String formateAmt(String amt) {
		if (StringUtil.isEmpty(amt)) {
			return "0.00";
		} else {
			BigDecimal bd = new BigDecimal(amt);
			return bd.movePointLeft(2).toString();
		}
	}
	
	public static String transferAmt(String amt) {
		if (StringUtil.isEmpty(amt)) {
			return "0";
		} else {
			BigDecimal bd = new BigDecimal(amt);
			return String.valueOf(bd.movePointRight(2));
		}
	}

	/**
	 * 指定长度随机数字字符串
	 * @param length
	 * @return
	 */
	public static String randomNumStr(int length) {
		if (length <= 0) {
			throw new BizzException("length is illegal.");
		}
		String[] chars = new String[] {"1", "9", "8", "7", "6", "5", "4", 
				"0", "1", "7", "2", "3", "4", "5", "6", "8", "0", "9"};
		Random rm = new Random(new Double(Math.random() * 10000 * 59).longValue());
		String strRand = "";
		for (int i = 0; i < length; i++) {
			String rand = chars[rm.nextInt(chars.length)];
			strRand += rand;
		}
		return strRand;
	}
	
	/**
	 * 根据正则表达式校验字符串
	 * @param val
	 * @param reg
	 * @param caseSensitive
	 * @return
	 */
	public static boolean validateReg(String val, String reg, boolean caseSensitive) {
		Pattern pattern = null;
		try {
			if (caseSensitive) {
	        	pattern = Pattern.compile(reg);
	        } else {
	        	pattern = Pattern.compile(reg, Pattern.CASE_INSENSITIVE);
	        }
	        Matcher matcher = pattern.matcher(val);
	        return matcher.matches();
		} catch (Exception e) {
			return false;
		}
	}
	
	public static String trimStr(String str) {
		return str == null ? "" : str.trim();
	}
	
	/**
	 * 字符串转换unicode
	 */
	public static String str2Unicode(String string) {
	 
	    StringBuilder unicode = new StringBuilder();
	 
	    for (int i = 0; i < string.length(); i++) {
	 
	        // 取出每一个字符
	        char c = string.charAt(i);
	 
	        // 转换为unicode
	        unicode.append("\\u" + Integer.toHexString(c));
	    }
	 
	    return unicode.toString();
	}
	
	/**
	 * unicode 转字符串
	 */
	public static String unicode2Str(String unicode) {
	 
	    StringBuffer string = new StringBuffer();
	 
	    String[] hex = unicode.split("\\\\u");
	 
	    for (int i = 1; i < hex.length; i++) {
	 
	        // 转换出每一个代码点
	        int data = Integer.parseInt(hex[i], 16);
	 
	        // 追加成string
	        string.append((char) data);
	    }
	 
	    return string.toString();
	}
	

}