package com.lijin.mylab.utils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PublicKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.DSAPublicKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import java.util.Enumeration;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import com.lijin.mylab.exception.BizzException;

/** 
 *X509证书工具类
 */
public class X509CertUtil {
	private static Logger logger = Logger.getLogger(X509CertUtil.class);	  
	
	
	/**
	 * 获取证书对象内容（PEM格式）
	 */
	public static String getCertContent(X509Certificate cert) throws Exception {
		return  "-----BEGIN CERTIFICATE-----\n" 
				+ Base64.encodeBase64String(cert.getEncoded() ) 
				+ "\n-----END CERTIFICATE-----\n";
	}

	/**
	 * 使用证书内容（PEM格式）生成X509证书对象
	 */
	public static X509Certificate getX509Certificate(String certContent) throws Exception {
		if (certContent == null)
			throw new BizzException("证书内容对象为空");
		
		if (certContent.indexOf("BEGIN CERTIFICATE") < 0) {
			certContent =  "-----BEGIN CERTIFICATE-----\n" + certContent + "\n-----END CERTIFICATE-----\n";
		}
		
		CertificateFactory tcf ;
		try {
			tcf = CertificateFactory.getInstance("X.509");
		} catch(Exception e) {
			logger.error("系统不支持X.509类型证书", e);
			throw e;
		}
		
		X509Certificate tcer = null;	 	
		InputStream in = null;
		try {
			in = new ByteArrayInputStream(certContent.getBytes());
			tcer = (X509Certificate)tcf.generateCertificate(in);
		} catch (Exception e) {
			logger.error("证书文件解析错误，证书内容流无有效证书结束标志，证书内容：\n" + certContent, e);
			throw new BizzException("证书文件内容错误");
		} finally {
			try {
				if (in != null)
					in.close();
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			}
		}		
		
		return tcer;
	}
	
	
	/**
	 * 使用证书文件（.cer文件）生成X509证书对象
	 */
	public static X509Certificate genCertFromCer(File cerFile) throws Exception {
				
		  CertificateFactory cf  = null;
		  try {
			  cf = CertificateFactory.getInstance("X.509");
		  } catch (CertificateException ce) {
			  logger.error("系统不支持X.509类型证书", ce);
			  throw ce;
		  }
		 
		  FileInputStream fis = null;				
		  X509Certificate cer = null;
		  try {
			  fis = new FileInputStream(cerFile);
			  cer = (X509Certificate)cf.generateCertificate(fis);			  
		  } catch (CertificateException ce) {			
			  logger.error("证书文件解析错误，证书内容流无有效证书结束标志:" + cerFile, ce);
			  throw new BizzException("证书文件内容错误");
		  } finally {
			 if (fis != null)
				 fis.close(); 
		 }		 
		 return cer;				  
	}
	 
	/**
	 *  使用证书文件（.pfx文件）生成X509证书对象
	 *@param certFile	pkcs#12 格式证书文件，二进制形式，存放个人证书、私钥，通常包含保护密码
	 *@param psw		解锁证书密钥
	 *@return
	 *@throws Exception
	 */
	public static X509Certificate genCertFromPfx(File certFile, String psw) throws Exception {
		 
		KeyStore keyStore = null;		
		try {			 
			 if (-1 == java.security.Security.addProvider(new BouncyCastleProvider())) {    //注册加密算法提供商：RSA加密算法提供商BouncyCastleProvider
				 logger.info("BC Provider已存在，不再添加");
			 }
			 keyStore = KeyStore.getInstance("PKCS12", "BC"); 							 			//创建密钥库
		
		} catch (SecurityException se) {
			logger.error("无权限添加安全提供商", se);
			throw se; 			//系统错误，联系管理员处理
			
		} catch (NoSuchProviderException ex) {
		    logger.error("BC Provider未注册", ex);
			throw ex;
			
		} catch (KeyStoreException ex) {
			logger.error("BC Provider 不支持 PKCS12 密钥类型", ex);
			throw ex;
		} 
		
		//加载证书库
		FileInputStream fis =null; 		
		try {			
			
			fis = new FileInputStream(certFile);		
			keyStore.load(fis, StringUtils.isBlank(psw) ? null : psw.toCharArray() );			
		
		} catch (FileNotFoundException ex) {
			logger.error("文件读取异常", ex);
			throw ex;			
		} catch (SecurityException ex) {
			logger.error("无权限读取该证书文件", ex);
			throw ex;				
		} catch (NoSuchAlgorithmException ex) {
			logger.error("无校验证书完整性的算法", ex);
			throw ex;		
		} catch (CertificateException ex) {
			logger.error("密钥库中有证书无法加载", ex);
			throw ex;		
		} catch (Exception ex) {		 		
			if (ex instanceof UnrecoverableKeyException ) {	//证书密码错误
				logger.error("证书密钥密码错误", ex);
				throw new BizzException("证书密钥密码错误");
			} else if (ex instanceof IOException) {
				logger.error("读取或解析证书密钥数据异常", ex);
				throw ex;
			} else {
				logger.error("加载证书密钥数据异常", ex);
				throw ex;
			}	
		} finally {
			if (fis != null) {
				fis.close();
			}
		}
		
		
		//从证书库获取证书
		X509Certificate x509Cert = null;
		try {
			Enumeration<String> aliasenum = keyStore.aliases();
			String alias = null;
			if (aliasenum.hasMoreElements() ) {  //只读一个证书
				alias = aliasenum.nextElement();
			}
			x509Cert = (X509Certificate)keyStore.getCertificate(alias);
			
			return x509Cert;
			
		} catch (KeyStoreException ke) {
			logger.error("密钥库未初始化", ke);
			throw ke;
		}
		
	}
	
	/**
	 * 获取证书密钥长度
	 *@param cert
	 *@return
	 *@throws Exception
	 */
	public static int getCertKeyLength(X509Certificate cert) throws Exception {
		//取证书公钥
		PublicKey pubKey = cert.getPublicKey();
		
		//取公钥算法
		String sAlgorithm = pubKey.getAlgorithm();
	
		try {
			
			KeyFactory keyf= KeyFactory.getInstance(sAlgorithm);				
			
			//RSA算法
			if (sAlgorithm.equals("RSA") ) {							
				RSAPublicKeySpec keySpec = (RSAPublicKeySpec)keyf.getKeySpec(pubKey, RSAPublicKeySpec.class);		
				BigInteger modulus = keySpec.getModulus();
				return modulus.toString(2).length();						 
			} if (sAlgorithm.equals("DSA") ) {								 	
				DSAPublicKeySpec keySpec = (DSAPublicKeySpec)keyf.getKeySpec(pubKey, DSAPublicKeySpec.class);				
				BigInteger prime = keySpec.getP();				
				return prime.toString(2).length();						
			} else {			
				logger.info("未知算法类型：" + sAlgorithm +", 无法计算长度");	
				return 0;
			}
		} catch (NoSuchAlgorithmException e) {
			logger.error("不支持"+sAlgorithm +"算法", e);
			throw e;
		} catch (InvalidKeySpecException e) {
			logger.error("无效算法规则，算法类型：" + sAlgorithm, e);
			throw e;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}			
	}	
}
