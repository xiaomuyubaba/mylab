package com.lijin.mylab.utils;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.lang.ArrayUtils;

import com.lijin.mylab.exception.BizzException;
 

public class EncryptUtil {

	static final String DEFAULT_ENCODING = "UTF-8";
	private static final Pattern md5Pattern = Pattern.compile("^([\\da-f]{32})$");
	private static final Pattern base64Pattern = Pattern
			.compile("^([A-Za-z0-9\\+\\/]{4})*([A-Za-z0-9\\+\\/]{2}==|[A-Za-z0-9\\+\\/]{3}=)?$");
	private static final DecimalFormat DEFAULT_DECIMAL_FORMATTER = new DecimalFormat("#.##");
	
	/***/
	public static String encryptPassword(String password, String loginName) {
		return md5(md5(password) + loginName);
	}

	public static String encryptPasswordWithSeed(String password, String loginName, String seed) {
		return md5(encryptPassword(password, loginName) + seed);
	}

	public static String encryptLoginPasswordWithSeed(String securePassword, String seed) {
		return md5(securePassword + seed);
	}

	/**
	 * get the md5 hash of a string
	 * @param str
	 * @return
	 */
	public static String md5(String str) {
		return md5(str, DEFAULT_ENCODING);
	}

	public static String md5(String str, String encoding) {

		if (str == null) {
			return null;
		}

		MessageDigest messageDigest = null;

		try {
			messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.reset();
			messageDigest.update(str.getBytes(encoding));
		} catch (Exception e) {
			throw new BizzException("MD5 algorithm not supported", e);
		}

		byte[] byteArray = messageDigest.digest();


		return EncodeUtils.hexEncode(byteArray);
	}

	/**
	 * detect if the given string represents a md5 hash
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isMd5String(String str) {
		return StringUtil.isNotBlank(str) && md5Pattern.matcher(str).matches();
	}

	public static boolean isBase64String(String str) {
		return StringUtil.isNotBlank(str) && base64Pattern.matcher(str).matches();
	}

	/**
	 * Get an escaped string for regular expression to use
	 * 
	 * @param input
	 *            the input string
	 * @return escaped pattern
	 */
	public static String escapeForRegex(String input) {
		if (StringUtil.isBlank(input)) {
			return input;
		}
		input = input.replaceAll("\\\\", "\\\\\\\\");
		input = input.replaceAll("\\(", "\\\\(");
		input = input.replaceAll("\\)", "\\\\)");
		input = input.replaceAll("\\[", "\\\\[");
		input = input.replaceAll("\\]", "\\\\]");
		input = input.replaceAll("\\{", "\\\\{");
		input = input.replaceAll("\\}", "\\\\}");
		input = input.replaceAll("\\|", "\\\\|");
		input = input.replaceAll("\\^", "\\\\^");
		input = input.replaceAll("\\$", "\\\\\\$");
		input = input.replaceAll("\\.", "\\\\.");
		input = input.replaceAll("\\?", "\\\\?");
		input = input.replaceAll("\\+", "\\\\+");
		input = input.replaceAll("\\*", "\\\\*");
		return input;
	}

	public static String formatDouble(double d) {
		return DEFAULT_DECIMAL_FORMATTER.format(d);
	}

	/**
	 * list all the enum values as a comma joined string, with value quoted.
	 * 
	 * @param <T>
	 * @param clazz
	 * @return
	 */
	public static <T> String listEnumAsQuotedString(Class<T> clazz) {
		T[] enums = clazz.getEnumConstants();
		List<String> quotedValues = new ArrayList<String>();
		for (T e : enums) {
			quotedValues.add("\"" + e.toString() + "\"");
		}
		return StringUtil.join(quotedValues, ",");
	}

	public static String join(int[] array, String separator) {
		if (array == null || array.length == 0) {
			return null;
		}
		StringBuffer buffer = new StringBuffer(array[0]);
		for (int i = 1; i < array.length; i++) {
			buffer.append(separator);
			buffer.append(array[i]);
		}
		return buffer.toString();
	}

	public static String formatMessage(String msg, Object... args) {
		if (StringUtil.isBlank(msg) || ArrayUtils.getLength(args) == 0) {
			return msg;
		} else {
			return MessageFormat.format(msg, args);
		}
	}

	/**
	 * Take the fixed length of chars from the given string, right pad if not enough length, or full pad if null.
	 * 
	 * @param str
	 * @param length
	 * @param padChar
	 * @return
	 */
	public static String toFixLenStr(String str, int length, char padChar) {
		if (length < 0) {
			throw new RuntimeException("Length can not be less than zero.");
		}
		char[] result = new char[length];
		if (str == null) {
			for (int i = 0; i < length; ++i) {
				result[i] = padChar;
			}
		} else {
			char[] tmp = str.toCharArray();
			int copyLen = Math.min(length, tmp.length);
			System.arraycopy(tmp, 0, result, 0, copyLen);
			if (copyLen < length) {
				for (int i = copyLen; i < length; ++i) {
					result[i] = padChar;
				}
			}
		}
		return new String(result);
	}

	public static String maskCardNumber(String cardNumber) {
		return addMask(cardNumber, 4, 4, 4);
	}

	public static String maskPhoneNumber(String phoneNumber) {
		return addMask(phoneNumber, 3, 3, 4);
	}

	public static String addMask(String str, int lenBeforeMask, int lenAfterMask, int maxMaskLength) {
		if (StringUtil.isEmpty(str)) {
			return str;
		}
		int len = str.length();
		if (len <= lenBeforeMask + lenAfterMask) {
			return str;
		}
		int x = Math.min(maxMaskLength, len - lenBeforeMask - lenAfterMask);
		return StringUtil.substring(str, 0, lenBeforeMask) + StringUtil.repeat("*", x) + StringUtil.substring(str, len - lenAfterMask);
	}
	
	
	/**
	 * 返回16进制MD5字符串。
	 * 
	 * @param str
	 *            需要计算MD5的字符串
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	public static String sha1(byte[] raw) {

		MessageDigest messageDigest;
		try {
			messageDigest = MessageDigest.getInstance("SHA-1");
			messageDigest.reset();
			messageDigest.update(raw);
			byte[] bytes = messageDigest.digest();
			return EncodeUtils.hexEncode(bytes);
		} catch (Exception e) {
			throw new BizzException("SHA-1 algorithm not supported", e);
		}
	}
	
	
	/**
	 * 返回16进制MD5字符串。
	 * 
	 * @param str
	 *            需要计算MD5的字符串
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	public static String sha256(byte[] raw) {

		MessageDigest messageDigest;
		try {
			messageDigest = MessageDigest.getInstance("SHA-256");
			messageDigest.reset();
			messageDigest.update(raw);
			byte[] bytes = messageDigest.digest();
			return EncodeUtils.hexEncode(bytes);
		} catch (Exception e) {
			throw new BizzException("SHA-256 algorithm not supported", e);
		}
	}
	
	private static Cipher tripleDESEncryptCipher;
	static{
		try {
			tripleDESEncryptCipher = Cipher.getInstance("DESede/ECB/NoPadding",
					new org.bouncycastle.jce.provider.BouncyCastleProvider());
		} catch (Exception e) {
			throw new BizzException("Init TripleDES Encrypt Cipher failed",e);
		} 
	}
	
	public static byte[] TripleDESEncrypt(byte[] key,byte[] data) throws NoSuchAlgorithmException, GeneralSecurityException{
        //生成密钥
        SecretKey deskey = new SecretKeySpec(key, "DESede");
        byte[] res = null;
        synchronized(tripleDESEncryptCipher){     
        tripleDESEncryptCipher.init(Cipher.ENCRYPT_MODE, deskey);
        res = tripleDESEncryptCipher.doFinal(data);
        }
        return res;
	}
	
	private static Cipher tripleDESDecryptCipher;
	static{
		try {
			tripleDESDecryptCipher = Cipher.getInstance("DESede/ECB/NoPadding",
					new org.bouncycastle.jce.provider.BouncyCastleProvider());
		} catch (Exception e) {
			throw new BizzException("Init TripleDES Decrypt Cipher failed",e);
		} 
	}

	public static byte[] TripleDESDecrypt(byte[] key, byte[] data)
			throws NoSuchAlgorithmException, GeneralSecurityException {
		SecretKey deskey = new SecretKeySpec(key, "DESede");

		byte[] res = null;
		synchronized (tripleDESDecryptCipher) {
			tripleDESDecryptCipher.init(Cipher.DECRYPT_MODE, deskey);
			res = tripleDESDecryptCipher.doFinal(data);
		}
		return res;
	}
	
	private static Cipher desEncryptCipher;
	static{
		try {
			desEncryptCipher = Cipher.getInstance("DES/ECB/NoPadding",
					new org.bouncycastle.jce.provider.BouncyCastleProvider());
		} catch (Exception e) {
			throw new BizzException("Init DES Encrypt Cipher failed",e);
		} 
	}
	
	public static byte[] DESEncrypt(byte[] key,byte[] data) throws NoSuchAlgorithmException, GeneralSecurityException{
        //生成密钥
        SecretKey deskey = new SecretKeySpec(key, "DES");
        byte[] res = null;
        synchronized(desEncryptCipher){
        	desEncryptCipher.init(Cipher.ENCRYPT_MODE, deskey);
            res = desEncryptCipher.doFinal(data);
        }
        return res;
	}
	
	
	private static Cipher desDecryptCipher;
	static{
		try {
			desDecryptCipher = Cipher.getInstance("DES/ECB/NoPadding",
					new org.bouncycastle.jce.provider.BouncyCastleProvider());
		} catch (Exception e) {
			throw new BizzException("Init DES Decrypt Cipher failed",e);
		} 
	}
	public static byte[] DESDecrypt(byte[] key,byte[] data) throws NoSuchAlgorithmException, GeneralSecurityException{
        SecretKey deskey = new SecretKeySpec(key, "DES");
        byte[] res = null;
        synchronized(desDecryptCipher){
        	desDecryptCipher.init(Cipher.DECRYPT_MODE, deskey);
            res = desDecryptCipher.doFinal(data);
        }
        
        return res;
	}

	public static void main(String[] args){
		byte[] key = new byte[]{0x01,0x02,0x03,0x04,(byte)0xf1,(byte)0xf2,(byte)0xf3,(byte)0xf4,
				0x01,0x02,0x03,0x04,(byte)0xf1,(byte)0xf2,(byte)0xf3,(byte)0xf4}; 
		
		try {
			new Thread() {
				public void run() {
					byte[] key1 = new byte[]{0x0F,0x0E,0x0A,0x0B,(byte)0xf1,(byte)0xf2,(byte)0xf3,(byte)0xf4,
							0x01,0x02,0x03,0x04,(byte)0xf1,(byte)0xf2,(byte)0xf3,(byte)0xf4}; 
					for(int i=0;i<50;i++){
						long t = System.currentTimeMillis();
						try {
							System.out.println("t2 "+new String(TripleDESDecrypt(key1,TripleDESEncrypt(key1,"12345678ABCDEFGHFFFFFFFFHHHHHHHH".getBytes()))));
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} 
						System.out.println("t2 cost "+(System.currentTimeMillis() -t));
					}
				}
			}.start();
			long tt = System.currentTimeMillis();
			for(int i=0;i<50;i++){
				long t = System.currentTimeMillis();
				System.out.println("t1 "+new String(TripleDESDecrypt(key,TripleDESEncrypt(key,"12345678ABCDEFGHFFFFFFFFHHHHHHHH".getBytes()))));
				System.out.println("t1 cost "+(System.currentTimeMillis() -t));
			}
			System.out.println("total cost "+(System.currentTimeMillis() -tt));
			
//			System.out.println(Arrays.toString("0".getBytes()));
//			System.out.println('0');
//			System.out.println((byte)0);
//			System.out.println((byte)0x00);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
	}
}
