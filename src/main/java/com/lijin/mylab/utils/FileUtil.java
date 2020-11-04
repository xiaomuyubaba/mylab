package com.lijin.mylab.utils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Collections;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lijin.mylab.exception.BizzException;

public final class FileUtil {

	private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);

	public static byte[] readFileAsBytes(String filePath) {
		AssertUtil.strIsNotBlank(filePath, "filePath is blank.");

		File f = new File(filePath);
		if (!f.exists()) {
			throw new BizzException("can't find file:" + filePath);
		}

		ByteArrayOutputStream bos = new ByteArrayOutputStream((int) f.length());
		BufferedInputStream in = null;
		try {
			in = new BufferedInputStream(new FileInputStream(f));
			int buf_size = 1024;
			byte[] buffer = new byte[buf_size];
			int len = 0;
			while (-1 != (len = in.read(buffer, 0, buf_size))) {
				bos.write(buffer, 0, len);
			}
			return bos.toByteArray();
		} catch (Exception e) {
			logger.error("read file as bytes error:" + filePath, e);
			throw new BizzException("read file as bytes error:" + filePath);
		} finally {
			IOUtil.close(in);
			IOUtil.close(bos);
		}
	}

	/**
	 * 读取文件，并逐行进行处理
	 * 
	 * @param filePath 文件路径
	 * @param charset  读取文件的编码，如果未指定，则默认用GBK
	 * @param handler  每行信息的处理类
	 */
	public static void readFileByLine(String filePath, String charset, FileLineHandler handler) {
		logger.info("开始读取文件:" + filePath);
		File f = new File(filePath);
		readFileByLine(f, charset, handler);
	}

	public static void readFileByLine(File file, String charset, FileLineHandler handler) {
		if (file.exists()) {
			InputStream in = null;
			BufferedReader reader = null;
			try {
				in = new FileInputStream(file);
				// 默认用GBK格式读取文件
				reader = new BufferedReader(
						new InputStreamReader(in, StringUtil.isBlank(charset) ? "GBK" : charset.trim()));
				int lineNo = 0;
				while (reader.ready()) {
					String line = reader.readLine();
					lineNo++;
					try {
						handler.handleLine(line, lineNo);
					} catch (Exception e) {
						logger.error("行数据处理出错[" + lineNo + "]:" + line, e);
						throw new BizzException("行数据处理出错[" + lineNo + "]:" + line);
					}
				}
			} catch (Exception e) {
				logger.error("读取文件出错", e);
			} finally {
				IOUtil.close(reader);
				IOUtil.close(in);
			}
		} else {
			logger.info("文件不存在");
		}
	}

	/**
	 * 读取文件，并逐行进行处理，针对不需要读取整个文件的情况
	 * 
	 * @param filePath 文件路径
	 * @param charset  读取文件的编码，如果未指定，则默认用GBK
	 * @param handler  每行信息的处理类
	 */
	public static void readPartFileByLine(String filePath, String charset, FilePartLineHandler handler) {
		logger.info("开始读取文件:" + filePath);
		File f = new File(filePath);
		readPartFileByLine(f, charset, handler);
	}

	public static void readPartFileByLine(File file, String charset, FilePartLineHandler handler) {
		if (file.exists()) {
			InputStream in = null;
			BufferedReader reader = null;
			try {
				in = new FileInputStream(file);
				// 默认用GBK格式读取文件
				reader = new BufferedReader(
						new InputStreamReader(in, StringUtil.isBlank(charset) ? "GBK" : charset.trim()));
				handler.handlePartLine(reader);
			} catch (Exception e) {
				logger.error("读取文件出错", e);
			} finally {
				IOUtil.close(reader);
				IOUtil.close(in);
			}
		} else {
			logger.info("文件不存在");
		}
	}

	public static boolean newFile(String filepath) throws Exception {
		// 校验父目录是否存在，不存在则创建目录
		File file = new File(filepath);
		if (file.exists())
			return false; // 文件已存在则不创建文件
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		// 创建文件
		return file.createNewFile();
	}

	public static void writeStrToFile(String filePath, String content, String charset) {
		// 编码默认为utf8
		if (charset == null || "".equals(charset.trim())) {
			charset = "UTF-8";
		}
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath), charset));
			writer.write(content);
			writer.flush();
		} catch (Exception e) {
			throw new RuntimeException("写入文件失败:" + filePath, e);
		} finally {
			IOUtil.close(writer);
		}
	}

	public static void writeStrsToFile(String filePath, List<String> content, String charset) {
		// 编码默认为utf8
		if (charset == null || "".equals(charset.trim())) {
			charset = "UTF-8";
		}
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath), charset));
			for (String str : content) {
				writer.write(str);
				writer.newLine();
			}
			writer.flush();
			logger.info("写入文件[" + filePath + "]成功");
		} catch (Exception e) {
			throw new RuntimeException("写入文件失败:" + filePath, e);
		} finally {
			IOUtil.close(writer);
		}
	}

	public static List<String> readStrFromFile(String filePath, String charset) {
		List<String> lines = Collections.emptyList();
		File f = new File(filePath);
		if (f.exists()) {
			InputStream in = null;
			try {
				in = new FileInputStream(f);
				// 默认用UTF-8格式读取文件
				lines = IOUtils.readLines(in, StringUtil.isEmpty(charset) ? "UTF-8" : charset);
			} catch (Exception e) {
				throw new RuntimeException("读取文件失败:" + filePath, e);
			} finally {
				if (in != null) {
					try {
						in.close();
					} catch (Exception e) {
						
					}
				}
			}
		} else {
			throw new RuntimeException("文件不存在:" + filePath);
		}
		return lines;
	}

	/**
	 * 拷贝文件工具
	 * 
	 * @param souFile 源文件
	 * @param desFile 目的文件
	 * @return
	 * @throws Exception
	 */
	public static void copyFileUtil(File souFile, File desFile) throws Exception {
		int length = 2097152;
		FileInputStream in = null;
		FileOutputStream out = null;
		try {
			in = new FileInputStream(souFile);
			out = new FileOutputStream(desFile);
			byte[] buffer = new byte[length];
			while (true) {
				int ins = in.read(buffer);
				if (ins == -1) {
					in.close();
					out.flush();
					out.close();
					break;
				} else {
					out.write(buffer, 0, ins);
				}
			}
		} catch (Exception e) {
			logger.error("拷贝文件工具出错", e);
			throw e;
		} finally {
			if (in != null) {
				in.close();
			}
			if (out != null) {
				out.close();
			}
		}
	}

	public static String readAllFile(File souFile) throws IOException {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(souFile));
			// 一次读入一行，直到读入null为文件结束
			String tempString;
			String returnString = "";
			while ((tempString = reader.readLine()) != null) {
				returnString = returnString.concat(tempString);
			}
			return returnString;
		} catch (IOException e) {
			logger.error("读取文件工具出错", e);
			throw e;
		} finally {
			if (reader != null) {
				reader.close();
			}
		}
	}

	public static String getFileName(String filePath) {
		AssertUtil.strIsNotBlank(filePath, "filePath is blank.");
		int index = filePath.lastIndexOf(File.separatorChar);
		if (index > 0) {
			return filePath.substring(index + 1);
		} else {
			return filePath;
		}
	}

	public static void main(String[] args) {
//		System.out.println(FileUtil.class.getClassLoader().getResource("column.conf"));
//		FileUtil.readFileByLine(FileUtil.class.getClassLoader().getResource("column.conf").toString(), "UTF-8", new FileLineHandler() {
//			@Override
//			public void handleLine(String line) {
//				System.out.println(line);
//			}
//		});

		System.out.println(getFileName("D:\\temp\\ddd\\asdf.s"));
	}
}
