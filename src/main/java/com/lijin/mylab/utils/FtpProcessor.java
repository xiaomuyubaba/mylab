package com.lijin.mylab.utils;

import com.lijin.mylab.exception.BizzException;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class FtpProcessor {

	private final Logger logger = LoggerFactory.getLogger(FtpProcessor.class);
	
	/**
	 * FTP客户端工具类
	 */
	private FTPClient ftp = new FTPClient();

	/**
	 *
	 * @param host
	 * @param port
	 * @param ftpUser
	 * @param ftpPassword
	 */
	public FtpProcessor(String host, int port, String ftpUser, String ftpPassword) {
		conn(host, port, ftpUser, ftpPassword);
	}

	public boolean getFile(String remoteDir, String localDir, String fileNm, boolean isTextFile, String charset) {
		AssertUtil.strIsNotBlank(remoteDir, "remoteDir is blank.");
		AssertUtil.strIsNotBlank(localDir, "localDir is blank.");
		AssertUtil.strIsNotBlank(fileNm, "fileNm is blank.");
		
		File dir = new File(localDir);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		String localFilePath = localDir + fileNm;
		FileOutputStream fos = null;
		BufferedOutputStream bufOut = null;
		try {
			logger.info("===Get File from " + remoteDir + fileNm + " => " + localFilePath + " started");
			//System.out.println(ftp.printWorkingDirectory());
			if (!ftp.changeWorkingDirectory(remoteDir)) {
				throw new BizzException("change to remoteDir failed:" + remoteDir);
			}
			logger.info("current path:"+ftp.printWorkingDirectory());
			ftp.enterLocalPassiveMode();
			String[] fileNms = ftp.listNames();
			if (ftp.listNames() == null
					|| !ArrayUtils.contains(fileNms, fileNm)) {
				return false;
			}
			if (isTextFile) {
				ftp.setFileType(FTPClient.ASCII_FILE_TYPE);
			} else {
				ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
			}
			logger.info("isTextFile:" + isTextFile);
			fos = new FileOutputStream(localFilePath);
			bufOut = new BufferedOutputStream(fos);
			if (StringUtil.isBlank(charset)) {
				ftp.setControlEncoding(charset);
			} else {
				ftp.setControlEncoding("GB2312");
			}
			logger.info("charset:" + charset);
			boolean isValidWrt = ftp.retrieveFile(fileNm, bufOut);
			if (!isValidWrt) {
				throw new BizzException("remote file or local file is not exist");
			}
			logger.info("===Get File from remoteDir + fileNm =>" + localFilePath + " done!");
			return true;
		} catch (Exception ex) {
			close();
			logger.error("\\== ERROR WHILE EXECUTE FTP ==// (File Name:"
					+ remoteDir + fileNm + " ==> " + localFilePath
					+ ") >>>>>>>>>>>>>" + ex.getLocalizedMessage(), ex);
			throw new BizzException("get ftp file exception");

		} finally {
			IOUtil.close(bufOut);
			IOUtil.close(fos);
		}
	}
	
	
	/**
	 * Download a file from the server
	 * 
	 * @param fileName
	 * @param isTextFile
	 * @return
	 */
	public String[] getFiles(String remoteDir, String localDir, String fileNmRegex) {
		AssertUtil.strIsNotBlank(remoteDir, "remoteDir is blank.");
		AssertUtil.strIsNotBlank(localDir, "localDir is blank.");
		AssertUtil.strIsNotBlank(fileNmRegex, "fileNmPrefix is blank.");
		
		File dir = new File(localDir);
		if (!dir.exists()) {
			dir.mkdirs();
		}

		try {
			logger.info("===Get files from " + remoteDir + fileNmRegex + " to local dir " + localDir + " started");
			if (!ftp.changeWorkingDirectory(remoteDir)) {
				throw new BizzException("change to remoteDir failed:" + remoteDir);
			}
			logger.info("current path:"+ftp.printWorkingDirectory());
			ftp.enterLocalPassiveMode();
			String[] fileNms = ftp.listNames(fileNmRegex);

			if (fileNms == null) {
				return new String[0];
			}
			List<String> fileNames = new ArrayList<String>();
			ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
			
			FileOutputStream fos = null;
			BufferedOutputStream bufOut = null;
			for(String fileNm: fileNms){

				String localFilePath = localDir + fileNm;
				try{
					fos = new FileOutputStream(localFilePath);
					bufOut = new BufferedOutputStream(fos);
					if (!ftp.retrieveFile(fileNm, bufOut)) {
						throw new BizzException("Retrieve file["+remoteDir + fileNm+"] to [" + localFilePath + "] failed.");
					}
					logger.info("Got File["+remoteDir + fileNm+"] to [" + localFilePath + "].");
					fileNames.add(fileNm);
				}finally{
					IOUtil.close(bufOut);
					IOUtil.close(fos);
				}
				
			}
			return fileNames.toArray(new String[0]);

		} catch (Exception ex) {
			logger.error("===Get files from " + remoteDir + fileNmRegex + " to local dir " + localDir + " catch exception",ex);
			throw new BizzException("get ftp file exception");

		} 
	}

	/**
	 * 
	 * @param host
	 * @param port
	 * @param ftpUser
	 * @param ftpPassword
	 * @throws FtpException
	 */
	private void conn(String host, int port, String ftpUser, String ftpPassword) {
		try {
			ftp.connect(host, port);

			boolean islogin = ftp.login(ftpUser, ftpPassword);
			if (!islogin) {
				close();
				throw new BizzException("Login Host Fail!");
			}
			logger.info(host + " Login Host Succ,Current Path[ "+ ftp.printWorkingDirectory() + "]");
		} catch (Throwable t) {
			close();
			logger.error("ftp init error!!" + host, t);
			throw new BizzException("ftp init error!!");
		}
	}


	public void close() {
		try {
			if (ftp != null) {
				ftp.logout();
				ftp.disconnect();
			}
		} catch (Exception ex) {
			logger.error("\\== ERROR WHILE EXECUTE FTP ==// quit error", ex);
		}
		ftp = null;
	}
	

}
