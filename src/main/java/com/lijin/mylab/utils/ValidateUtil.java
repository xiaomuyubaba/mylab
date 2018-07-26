package com.lijin.mylab.utils;

import com.lijin.mylab.constants.Constant;
import com.lijin.mylab.exception.BizzException;

public class ValidateUtil {

	/**
	 * 录入的字符串不能为空
	 * @param input
	 */
	public static void validateStrBlank(String input, String failMsg) {
		if (StringUtil.isBlank(input)) {
			if (StringUtil.isBlank(failMsg)) {
				throw new BizzException("输入字符串为空");
			} else {
				throw new BizzException(failMsg);
			}
		}
	}
	
	/**
	 * 校验指定的字符串长度
	 * @param input
	 * @param length
	 */
	public static void validateStrLen(String input, int length) {
		// 数据库编码为GBK
		if (StringUtil.strLen(input, Constant.GBK) > length) {
			throw new BizzException("录入的字符串\"" + input + "\"超过指定长度" + length);
		}
	}
	
	/**
	 * 录入的信息不允许包含中文
	 * @param input
	 */
	public static void validateNoZhCn(String input) {
		if (StringUtil.containsZhCn(input)) {
			throw new BizzException("输入字符串不允许包含中文:" + input);
		}
	}
	
	/**
	 * 传入的数组对象为空
	 * @param arr
	 */
	public static void validateArrNotNull(Object[] arr, String failMsg) {
		if (arr == null || arr.length == 0) {
			if (StringUtil.isBlank(failMsg)) {
				throw new BizzException("数组对象为空");
			} else {
				throw new BizzException(failMsg);
			}
		}
	}
	
	public static void main(String[] args) {
		validateStrLen("一二一二一", 12);
		validateNoZhCn("123123一二一二一333");
	}
}
