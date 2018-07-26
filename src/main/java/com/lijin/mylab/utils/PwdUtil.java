package com.lijin.mylab.utils;

public class PwdUtil {

	private static int PWD_STR_LEN = 50;
	private static char PWD_STR_PADCHAR = 'P';
	
	/**
	 * 计算密码MD5值，为了防止破解，如果密码不够长，先扩长到50位再MD5加密
	 * @param oldPwdStr
	 * @return
	 */
	public static String computeMD5Pwd(String pwdStr) {
		AssertUtil.strIsNotBlank(pwdStr, "密码字符串不能为空");
		// 如果密码字符串长度小于50，则使用指定的字符'P'向右补足50位
		String newPwdStr = StringUtil.rightPad(pwdStr, PWD_STR_LEN, PWD_STR_PADCHAR);
		return EncryptUtil.md5(newPwdStr);
	}
	
	/**
	 * 校验密码是否正确
	 * @param oldPwd
	 * @param md5Str
	 * @return
	 */
	public static boolean validatePwd(String oldPwd, String md5Str) {
		AssertUtil.strIsNotBlank(oldPwd, "oldPwd is blank.");
		AssertUtil.strIsNotBlank(md5Str, "md5Str is blank.");
		
		// 如果密码字符串长度小于50，则使用指定的字符'P'向右补足50位
		String newPwdStr = StringUtil.rightPad(oldPwd, PWD_STR_LEN, PWD_STR_PADCHAR);
		return StringUtil.equals(EncryptUtil.md5(newPwdStr), md5Str);
	}
	
	public static void main(String[] args) {
		System.out.println(EncryptUtil.md5("pay_123"));
		System.out.println(computeMD5Pwd("pay_123"));
	}
}
