package com.lijin.mylab.utils;



import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

public class IdentityCardHelper {



	private static final Pattern IDENTITY_CARD_NUMBER_PATTERN = Pattern.compile("^\\d{15}(\\d{2}[\\dx])?$",Pattern.CASE_INSENSITIVE);
	private static final Pattern IDENTITY_CARD_NUMBER_SUFFIX8_PATTERN = Pattern.compile("^\\d{7}[\\dx]$",Pattern.CASE_INSENSITIVE);

	private static final String[] validProvinceCodes = { "11", "12", "13", "14", "15", "21", "22", "23", "31", "32", "33", "34",
			"35", "36", "37", "41", "42", "43", "44", "45", "46", "50", "51", "52", "53", "54", "61", "62", "63", "64", "65",
			"71", "81", "82", "91" };

	private static final int weighing[] = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 };

	private static final String verifyCodes[] = { "1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2" };
	
	/**
	 * 检查是不是身份证号后8位。
	 * 
	 * @param credentialNumberSuffix8
	 *            身份证号后8位。
	 * @return
	 */
	public static boolean isValidIdentityCardNumberSuffix8(String credentialNumberSuffix8) {
		if (credentialNumberSuffix8 == null) {
			return false;
		}

		return IDENTITY_CARD_NUMBER_SUFFIX8_PATTERN.matcher(credentialNumberSuffix8).matches();
	}

	public static boolean isValidIdentityCardNumber(String credentialNumber) {
		if (credentialNumber == null) {
			return false;
		}

		if (!IDENTITY_CARD_NUMBER_PATTERN.matcher(credentialNumber).matches()) {
			return false;
		}

		String provinceCode = credentialNumber.substring(0, 2);
		if (!isValidProvinceCode(provinceCode)) {
			return false;
		}

		String birthday;
		if (credentialNumber.length() == 18) {
			birthday = credentialNumber.substring(6, 14);
		} else {
			birthday = "19" + credentialNumber.substring(6, 12);
		}
		if (!isValidBirthday(birthday)) {
			return false;
		}

		if (credentialNumber.length() == 18 && !isValidVerifyCode(credentialNumber)) {
			return false;
		}

		return true;
	}

	public static boolean isValidProvinceCode(String provinceCode) {
		for (String obj : validProvinceCodes) {
			if (provinceCode.equals(obj)) {
				return true;
			}
		}
		return false;
	}
	


	public static boolean isValidBirthday(String strBirthday) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		sdf.setLenient(false);
		try {
			Date birthday = sdf.parse(strBirthday);
			if (birthday.before(new Date())) {
				return true;
			}
		} catch (ParseException e) {
		}
		return false;
	}

	public static boolean isValidVerifyCode(String credentialNumber) {
		char[] chars = credentialNumber.substring(0, 17).toCharArray();
		int[] ints = new int[17];
		for (int i = 0; i < 17; i++) {
			ints[i] = Integer.parseInt(String.valueOf(chars[i]));
		}
		int weightSum = 0;
		for (int i = 0; i < 17; i++) {
			weightSum += ints[i] * weighing[i];
		}
		int remainder = weightSum % 11;
		String verifyCode = verifyCodes[remainder];
		return verifyCode.equalsIgnoreCase(credentialNumber.substring(17));
	}


    /**
     * 转换15位身份证号为18位
     * @param idCard 15位身份证号
     * @return 18位身份证号
     */
    public static String convert15to18(String idCard) {
        char[] strJY = { '1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2' };
        int[] intJQ = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2, 1 };
        String certcode = idCard.trim();// 获得身份证号码
        if (certcode.length() == 18)
            return certcode;
        else if (certcode.length() == 15) {// 如果是15位则转换
            String strTemp;
            int intTemp = 0;
            strTemp = certcode.substring(0, 6) + "19" + certcode.substring(6);
            for (int i = 0; i <= strTemp.length() - 1; i++) {
                intTemp = intTemp
                        + Integer.parseInt(strTemp.substring(i, i + 1))
                        * intJQ[i];
            }
            intTemp = intTemp % 11;
            return strTemp + strJY[intTemp];
        } else {
            return null;
        }
    }
    
    public static void main(String[] args){
      System.out.println(isValidIdentityCardNumber("310103199201011979"));	

    }
}
