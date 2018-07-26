package com.lijin.mylab.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Random;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang.StringEscapeUtils;

public class EncodeUtils {
  private static final String DEFAULT_URL_ENCODING = "UTF-8";

  /**
   * Hex编码.
   */
  public static String hexEncode(byte[] input) {
    return Hex.encodeHexString(input);
  }

  /**
   * Hex解码.
   */
  public static byte[] hexDecode(String input) {
    try {
      return Hex.decodeHex(input.toCharArray());
    } catch (DecoderException e) {
      throw new IllegalStateException("Hex Decoder exception", e);
    }
  }

  /**
   * Base64编码.
   */
  public static String base64Encode(byte[] input) {
    return new String(Base64.encodeBase64(input));
  }

  /**
   * Base64编码, URL安全(将Base64中的URL非法字符如+,/=转为其他字符, 见RFC3548).
   */
  public static String base64UrlSafeEncode(byte[] input) {
    return Base64.encodeBase64URLSafeString(input);
  }

  /**
   * Base64解码.
   */
  public static byte[] base64Decode(String input) {
    return Base64.decodeBase64(input);
  }

  /**
   * URL 编码, Encode默认为UTF-8.
   */
  public static String urlEncode(String input) {
    try {
      return URLEncoder.encode(input, DEFAULT_URL_ENCODING);
    } catch (UnsupportedEncodingException e) {
      throw new IllegalArgumentException("Unsupported Encoding Exception", e);
    }
  }

  /**
   * URL 解码, Encode默认为UTF-8.
   */
  public static String urlDecode(String input) {
    try {
      return URLDecoder.decode(input, DEFAULT_URL_ENCODING);
    } catch (UnsupportedEncodingException e) {
      throw new IllegalArgumentException("Unsupported Encoding Exception", e);
    }
  }

  /**
   * Html 转码.
   */
  public static String htmlEscape(String html) {
    return StringEscapeUtils.escapeHtml(html);
  }

  /**
   * Html 解码.
   */
  public static String htmlUnescape(String htmlEscaped) {
    return StringEscapeUtils.unescapeHtml(htmlEscaped);
  }

  /**
   * Xml 转码.
   */
  public static String xmlEscape(String xml) {
    return StringEscapeUtils.escapeXml(xml);
  }

  /**
   * Xml 解码.
   */
  public static String xmlUnescape(String xmlEscaped) {
    return StringEscapeUtils.unescapeXml(xmlEscaped);
  }


	public static byte[] genRawKey() {
		Random random = new Random();
		byte[] key = new byte[16];
		for (int i = 0; i < 16; i++) {
			key[i] = (byte) random.nextInt(256);
		}
		return key;

	}
	
	
	public static byte[] str2bcd(String numStr,boolean leftPad){
		if(numStr.length()%2==1){
			numStr = leftPad?0+numStr:numStr+0;
		}
		return hexDecode(numStr);
	}
  
  public static void main(String[] args) {
    try {
//    	System.out.println("icpay收单平台".getBytes("UTF-8").length);
//		System.out.println(hexEncode("icpay收单平台".getBytes("UTF-8")));
//		System.out.println("6963706179e694b6e58d95e5b9b3e58fb0".getBytes().length);
//		System.out.println(EncryptUtil.md5("XXX"));

	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
  }
}
