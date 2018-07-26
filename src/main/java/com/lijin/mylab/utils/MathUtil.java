package com.lijin.mylab.utils;

import java.math.BigDecimal;

public class MathUtil {
	
	/**
	 * 相差百分比 ((p1 - p2) / p1) * 100
	 * @param p1
	 * @param p2
	 * @return
	 */
	public static String differenceRate(int p1, int p2) {
		BigDecimal bd1 = new BigDecimal(p1);
		BigDecimal bd2 = new BigDecimal(p2);
		BigDecimal rate = (bd2.subtract(bd1).multiply(new BigDecimal(100))).divide(bd1, 2, BigDecimal.ROUND_HALF_EVEN);
		return rate.toPlainString() + "%";
	}

	public static boolean isNegative(BigDecimal val) {
		AssertUtil.objIsNull(val, "val is null.");
		return val.compareTo(BigDecimal.ZERO) < 0;
	}
	
	public static boolean isZero(BigDecimal val) {
		AssertUtil.objIsNull(val, "val is null.");
		return val.compareTo(BigDecimal.ZERO) == 0;
	}
	
	public static void main(String[] args) {
//		System.out.println(isNegative(new BigDecimal("0.01")));
//		System.out.println(isNegative(new BigDecimal("-0.01")));
//		
//		System.out.println(isZero(new BigDecimal("-0.01")));
//		System.out.println(isZero(new BigDecimal("0.00")));
//		System.out.println(isZero(new BigDecimal("0.0")));
//		System.out.println(isZero(new BigDecimal("0")));
//		System.out.println(isZero(new BigDecimal("0.")));
//		System.out.println(isZero(new BigDecimal("-0.00")));
		
		System.out.println(differenceRate(823, 846));
		System.out.println(differenceRate(1201, 1192));
	}
}
